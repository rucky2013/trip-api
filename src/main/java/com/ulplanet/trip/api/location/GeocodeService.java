package com.ulplanet.trip.api.location;

import com.google.gson.Gson;
import com.ulplanet.trip.common.config.Global;
import com.ulplanet.trip.common.utils.HttpClientUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 坐标获取国家城市
 * Created by zhangxd on 15/8/18.
 */
public class GeocodeService {

    private static final Logger logger = LoggerFactory.getLogger(GeocodeService.class);

    private static final String URL = "https://maps.googleapis.com/maps/api/geocode/json";
    private static final String KEY = Global.getConfig("google.key");

    private static final Map<String, String> CITY_NODE_MAP = new HashMap<>();
    private static final Map<String, String> CITY_REVERT_MAP = new HashMap<>();
    private static final String DEFAULT_CITY_NODE = "locality";

    static {
        CITY_NODE_MAP.put("中国", "locality");
        CITY_NODE_MAP.put("韩国", "locality");
        CITY_NODE_MAP.put("日本", "locality");
        CITY_NODE_MAP.put("芬兰", "administrative_area_level_3");
        CITY_NODE_MAP.put("澳大利亚", "locality");
        CITY_NODE_MAP.put("德国", "locality");
        CITY_NODE_MAP.put("泰国", "locality");

        // 韩国
        CITY_REVERT_MAP.put("汉城", "首尔");
        CITY_REVERT_MAP.put("南济州郡", "济州岛");
        CITY_REVERT_MAP.put("济州市", "济州岛");
        CITY_REVERT_MAP.put("平昌郡", "江原道");
        CITY_REVERT_MAP.put("华川郡", "江原道");
        CITY_REVERT_MAP.put("春川市", "江原道");
        CITY_REVERT_MAP.put("束草市", "江原道");
        CITY_REVERT_MAP.put("广州市", "京畿道");
        CITY_REVERT_MAP.put("龙仁市", "京畿道");
        CITY_REVERT_MAP.put("加平郡", "京畿道");
    }

    public static Geocode get(double longitude, double latitude) {
        HttpClient client = HttpClientUtils.getConnection();

        Map<String, String> params = new HashMap<>();
        params.put("latlng", String.format("%s,%s", latitude, longitude));
        params.put("language", "zh-CN");
        params.put("key", KEY);

        HttpUriRequest get = HttpClientUtils.getRequestMethod(params, URL, "get");
        HttpResponse response;
        try {
            response = client.execute(get);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                String message = EntityUtils.toString(entity, "utf-8");

                Gson gson = new Gson();
                Geocode geocode = gson.fromJson(message, Geocode.class);

                String status = geocode.getStatus();
                if ("OK".equals(status)) {
                    return geocode;
                } else {
                    logger.error("地理位置接口错误:" + status + ";经度:" + longitude + ";纬度:" + latitude);
                    return null;
                }
            } else {
                logger.error("地理位置接口请求失败:" + statusCode + ";经度:" + longitude + ";纬度:" + latitude);
                return null;
            }
        } catch (IOException e) {
            logger.error("PrintStackTrace:", e);
            return null;
        }
    }


    private static final String TYPE_COUNTRY = "country";

    public static class Geocode {
        List<Information> results;
        String status;

        public List<Information> getResults() {
            return results;
        }

        public void setResults(List<Information> results) {
            this.results = results;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Information getCountryInformation() {
            for (Information info : results) {
                if (TYPE_COUNTRY.equals(info.getType())) {
                    return info;
                }
            }
            return null;
        }

        public Information getCityInformation() {
            String country = getCountry();
            for (Information info : results) {
                if (Objects.toString(CITY_NODE_MAP.get(country), DEFAULT_CITY_NODE).equals(info.getType())) {
                    return info;
                }
            }
            return null;
        }

        public String getCountry() {
            Information info = getCountryInformation();
            if (info != null) {
                return info.getFormatted_address();
            }
            return null;
        }

        public String getCity() {
            Information info = getCityInformation();
            String country = getCountry();
            if (info != null) {
                String city = info.getCity(country);
                return Objects.toString(CITY_REVERT_MAP.get(city), city);
            }
            return null;
        }

    }

    static class Information {
        List<Components> address_components;
        String formatted_address;
        List<String> types;


        public String getFormatted_address() {
            return formatted_address;
        }

        public void setFormatted_address(String formatted_address) {
            this.formatted_address = formatted_address;
        }

        public List<Components> getAddress_components() {
            return address_components;
        }

        public void setAddress_components(List<Components> address_components) {
            this.address_components = address_components;
        }

        public String getType() {
            if (types != null && types.size() > 0) {
                return types.get(0);
            }
            return "";
        }

        public List<String> getTypes() {
            return types;
        }

        public void setTypes(List<String> types) {
            this.types = types;
        }

        public String getCity(String country) {
            for (Components components : address_components) {
                if (Objects.toString(CITY_NODE_MAP.get(country), DEFAULT_CITY_NODE).equals(components.getType())) {
                    return components.getLong_name();
                }
            }
            return null;
        }
    }

    static class Components {
        String long_name;
        String short_name;
        List<String> types;

        public String getType() {
            if (types != null && types.size() > 0) {
                return types.get(0);
            }
            return "";
        }

        public String getLong_name() {
            return long_name;
        }

        public void setLong_name(String long_name) {
            this.long_name = long_name;
        }

        public String getShort_name() {
            return short_name;
        }

        public void setShort_name(String short_name) {
            this.short_name = short_name;
        }

        public List<String> getTypes() {
            return types;
        }

        public void setTypes(List<String> types) {
            this.types = types;
        }
    }

    public static void main(String[] args) {
//        Geocode g1 = get(116.451114, 39.940012); //北京
//        System.out.println(g1.getCountry());
//        System.out.println(g1.getCity());
//        Geocode g2 = get(127.031188, 37.514612); //首尔
//        System.out.println(g2.getCountry());
//        System.out.println(g2.getCity());
//        Geocode g3 = get(135.780482, 35.000261); //京都市
//        System.out.println(g3.getCountry());
//        System.out.println(g3.getCity());
//        Geocode g4 = get(24.944801, 60.169583); //赫尔辛基
//        System.out.println(g4.getCountry());
//        System.out.println(g4.getCity());
        Geocode g5 = get(171.3312192, -42.180188); //新西兰
        if (g5 != null) {
            System.out.println(g5.getCountry());
            System.out.println(g5.getCity());
        }
    }
}
