package com.ulplanet.trip.api.location;

import com.ulplanet.trip.common.utils.HttpClientUtils;
import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 地址获取坐标
 * Created by zhangxd on 15/8/18.
 */
public class LocationService {

    private static final String URL = "https://maps.googleapis.com/maps/api/geocode/json";
    private static final String KEY = "AIzaSyAObFcwI1lG4WjSFQkEsSz09d5nMlyrhj8";

    public static Geocode get(String address) {
        HttpClient client = HttpClientUtils.getConnection();

        Map<String, String> params = new HashMap<>();
        params.put("address", address);
        params.put("key", KEY);

        HttpUriRequest get = HttpClientUtils.getRequestMethod(params, URL, "get");
        HttpResponse response;
        try {
            response = client.execute(get);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                String message = EntityUtils.toString(entity, "utf-8");

                Gson gson = new Gson();
                Geocode info = gson.fromJson(message, Geocode.class);

                if ("OK".equals(info.getStatus())) {
                    System.out.println(info.getLat());
                    System.out.println(info.getLng());
                    return info;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return null;
    }

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

        public Position getPosition() {
            if (results != null && results.size() > 0) {
                Information info = results.get(0);
                Location geometry = info.getGeometry();
                return geometry.getLocation();
            }
            return null;
        }

        public double getLat() {
            Position position = getPosition();
            if (position != null) {
                return position.getLat();
            }
            return 0;
        }

        public double getLng() {
            Position position = getPosition();
            if (position != null) {
                return position.getLng();
            }
            return 0;
        }

    }

    private static class Information {
        Location geometry;

        public Location getGeometry() {
            return geometry;
        }

        public void setGeometry(Location geometry) {
            this.geometry = geometry;
        }
    }

    private static class Location {
        Position location;

        public Position getLocation() {
            return location;
        }

        public void setLocation(Position location) {
            this.location = location;
        }
    }

    private static class Position {
        double lat;
        double lng;

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }
    }

    public static void main(String[] args) {
        get("首尔特别市西大门区沧川洞57-8 (서울특별시 서대문구 창천동 57-8)");
    }
}
