package com.ulplanet.trip.util;

import com.ulplanet.trip.bean.ResultInfo;
import com.ulplanet.trip.common.utils.HttpClientUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Administrator on 2015/9/21.
 */
public class HttpUtils {
    public static ResultInfo httpGet(Map<String,String> params,String url) throws IOException {
        ResultInfo resultInfo = new ResultInfo();
        HttpClient client = HttpClientUtils.getConnection();
        HttpUriRequest get = HttpClientUtils.getRequestMethod(params, url, "get");
        HttpResponse response = client.execute(get);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 200) {
            HttpEntity entity = response.getEntity();
            String message =  EntityUtils.toString(entity, "utf-8");
            resultInfo.setStatus(statusCode);
            resultInfo.setMessage(message);
            return resultInfo;
        }else{
            resultInfo.setStatus(statusCode);
            return resultInfo;
        }
    }


//    public static void main(String [] args){
//              WeatherInfo info = WeatherGetter.get(151.213275, -33.874872);
//                if(info!=null){
//                       System.out.print(JSON.toJSONString(info));
//                 }
//           }
}
