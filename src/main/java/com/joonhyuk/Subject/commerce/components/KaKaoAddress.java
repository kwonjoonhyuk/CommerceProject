package com.joonhyuk.Subject.commerce.components;

import com.joonhyuk.Subject.commerce.exception.CustomException;
import com.joonhyuk.Subject.commerce.exception.ErrorCode;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;


public class KaKaoAddress {

  private static final String GEOCODE_URL = "http://dapi.kakao.com/v2/local/search/address.json?query=";
  private static final String GEOCODE_USER_INFO = "KakaoAK key";
  ArrayList<String> locationList = new ArrayList<>();
  URL url;
  String addressName, roadAddressName, zone_no;

  public ArrayList<String> getLocation(String location) {
    try {
      String address = URLEncoder.encode(location, StandardCharsets.UTF_8);
      url = new URL(GEOCODE_URL + address);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      connection.setRequestProperty("Authorization", GEOCODE_USER_INFO);
      connection.setRequestProperty("content-type", "application/json");
      connection.setDoOutput(true);
      connection.setUseCaches(false);
      connection.setDefaultUseCaches(false);

      Charset charset = Charset.forName("UTF-8");
      BufferedReader in = new BufferedReader(
          new InputStreamReader(connection.getInputStream(), charset));

      String inputLine;
      StringBuffer stringBuffer = new StringBuffer();

      while ((inputLine = in.readLine()) != null) {
        stringBuffer.append(inputLine);
      }

      JSONObject jsonObject = new JSONObject(stringBuffer.toString());
      JSONArray jsonArray = jsonObject.getJSONArray("documents");

      for (int i = 0; i < jsonArray.length(); i++) {
        JSONObject object = jsonArray.getJSONObject(i);
        JSONObject jsonObject1 = object.getJSONObject("address");
        JSONObject jsonObject2 = object.getJSONObject("road_address");
        addressName = jsonObject1.getString("address_name");
        roadAddressName = jsonObject2.getString("address_name");
        zone_no = jsonObject2.getString("zone_no");
        locationList.add(addressName);
        locationList.add(roadAddressName);
        locationList.add(zone_no);
      }
      System.out.println(locationList.get(0));
      System.out.println(locationList.get(1));
      System.out.println(locationList.get(2));

    } catch (
        Exception e) {
      throw new CustomException(ErrorCode.NOT_INPUT_ADDRESS);
    }
    return locationList;
  }
}
