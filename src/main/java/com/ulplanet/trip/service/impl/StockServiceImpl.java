package com.ulplanet.trip.service.impl;

import com.ulplanet.trip.bean.PhoneInfo;
import com.ulplanet.trip.bean.StockOrder;
import com.ulplanet.trip.constant.Constants;
import com.ulplanet.trip.dao.PhoneInfoDao;
import com.ulplanet.trip.dao.StockOrderDao;
import com.ulplanet.trip.service.StockService;
import com.ulplanet.trip.util.RSACoder;
import org.apache.http.client.utils.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by Administrator on 2015/10/19.
 */
@Service("stockService")
public class StockServiceImpl implements StockService{

    Logger logger = Logger.getLogger(StockServiceImpl.class);

    @Resource
    private StockOrderDao stockOrderDao;
    @Resource
    private PhoneInfoDao phoneInfoDao;


    /**
     * 校验请求是否超时
     * @param publicKey
     * @param sign
     * @return
     */

    private String checkKey(String publicKey,String sign){

        if(RSACoder.PUBLIC_Key.equals(publicKey)){
            try {
                TimeZone destTimeZone = TimeZone.getTimeZone("GMT+8");

                long now = Calendar.getInstance(destTimeZone).getTime().getTime();
                byte [] b = RSACoder.decryptByPrivateKey(sign.getBytes(),
                        RSACoder.PRIVATE_KEY);
                String str = new String(b);
                String time = str.substring(str.indexOf("timestamp=") + 10);
                long timestamp = Long.parseLong(time);

                if(now-timestamp > 600000){
                    logger.error("请求超时");
                    return "请求超时";
                }
                return null;
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("解码错误");
                return "解码错误";
            }
        }
        return "解码错误";
    }

    @Override
    public Map<String, Object> findOrderList(String secureKey, String encryptionKey) {
        Map<String, Object> result = new HashMap<>();
        String str = checkKey(secureKey,encryptionKey);
        if(str == null){
            StockOrder s = new StockOrder();
            s.setStatus(1);
            List<StockOrder> list = stockOrderDao.findListByParams(s);
            result.put(Constants.RETURN_FIELD_STATUS, Constants.STATUS_SUCCESS);
            result.put(Constants.RETURN_FIELD_DATA, list);
            return result;
        }
        result.put(Constants.RETURN_FIELD_STATUS, Constants.STATUS_FAILURE);
        result.put(Constants.RETURN_FIELD_MESSAGE, str);
        return result;
    }

    @Override
    public Map<String, Object> savePhoneInfo(String code, String stockOrderId, String secureKey, String encryptionKey) {
        Map<String, Object> result = new HashMap<>();
        String str = checkKey(secureKey,encryptionKey);
//        String str = null;
        if(str == null){

            PhoneInfo phoneInfo = new PhoneInfo();
            phoneInfo.setCode(code);
            List<PhoneInfo> l = phoneInfoDao.findListByParams(phoneInfo);
            if(l.size()>0){
                result.put(Constants.RETURN_FIELD_STATUS, Constants.STATUS_FAILURE);
                result.put(Constants.RETURN_FIELD_MESSAGE, "手机编码已存在");
                return result;
            }
            phoneInfo.setStockOrderId(stockOrderId);
            phoneInfo.setStatus(1);
            phoneInfo.preInsert();
            phoneInfoDao.insert(phoneInfo);
            result.put(Constants.RETURN_FIELD_STATUS, Constants.STATUS_SUCCESS);
            result.put(Constants.RETURN_FIELD_DATA, phoneInfo);
            return result;
        }
        result.put(Constants.RETURN_FIELD_STATUS, Constants.STATUS_FAILURE);
        result.put(Constants.RETURN_FIELD_MESSAGE, str);
        return result;
    }

    @Override
    public Map<String, Object> updatePhoneStatus(String code, String status, String secureKey, String encryptionKey) {
        Map<String, Object> result = new HashMap<>();
        String str = checkKey(secureKey,encryptionKey);
//        String str = null;
        if(str == null){
            PhoneInfo phoneInfo = new PhoneInfo();
            phoneInfo.setCode(code);
            phoneInfo.setStatus(Integer.parseInt(status));
            phoneInfo.preUpdate();
            if(phoneInfo.getStatus()==9999){
                PhoneInfo p = new PhoneInfo();
                p.setCode(phoneInfo.getCode());
                List<PhoneInfo> l = phoneInfoDao.findListByParams(p);
                if(l.size()>0) {
                    this.refund(l.get(0).getStockOrderId());
                }else{
                    result.put(Constants.RETURN_FIELD_STATUS, Constants.STATUS_FAILURE);
                    result.put(Constants.RETURN_FIELD_MESSAGE, "编号不存在");
                    return result;
                }
            }
            phoneInfoDao.update(phoneInfo);
            result.put(Constants.RETURN_FIELD_STATUS, Constants.STATUS_SUCCESS);
            result.put(Constants.RETURN_FIELD_DATA, phoneInfo);
            return result;
        }
        result.put(Constants.RETURN_FIELD_STATUS, Constants.STATUS_FAILURE);
        result.put(Constants.RETURN_FIELD_MESSAGE, str);
        return result;
    }

    private int refund(String id) {
        StockOrder stockOrder = stockOrderDao.getById(id);
        double totalPrice = stockOrder.getTotalPrice();
        int quantity = stockOrder.getQuantity();
        double unitPrice = stockOrder.getUnitPrice();
        stockOrder.setQuantity(--quantity);
        stockOrder.setTotalPrice(totalPrice - unitPrice);
        stockOrder.preUpdate();
        stockOrder.setComment(stockOrder.getComment() + "/n 手机退货 on "
                + DateUtils.formatDate(new Date()) + " ==id==" + stockOrder.getId());
        return stockOrderDao.update(stockOrder);
    }
}
