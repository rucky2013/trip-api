package com.ulplanet.trip.service;

import java.util.Map;

/**
 * Created by Administrator on 2015/10/19.
 */
public interface StockService {
    Map<String,Object> findOrderList(String secureKey, String encryptionKey);
    Map<String,Object> savePhoneInfo(String code, String stockOrderId, String secureKey, String encryptionKey);
    Map<String,Object> updatePhoneStatus(String code, String status, String secureKey, String encryptionKey);
}
