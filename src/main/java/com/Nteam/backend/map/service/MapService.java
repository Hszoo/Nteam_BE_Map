package com.Nteam.backend.map.service;
import com.Nteam.backend.map.dto.MapDto;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

@Service
@RequiredArgsConstructor
public class MapService {
    @Value("${KAKAO_API_KEY}")
    String apiKey;
    @Value("${KAKAO_URL}")
    String apiUrl;

    public String getKakaoApiFromAddress(String fullAddr) {
        String jsonString = null;
        try {
            fullAddr = URLEncoder.encode(fullAddr, "UTF-8");
            String addr = apiUrl + "?query=" + fullAddr;
            URL url = new URL(addr);
            URLConnection conn = url.openConnection();
            conn.setRequestProperty("Authorization", "KakaoAK " + apiKey);
            BufferedReader json = null;
            json = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuffer docJson = new StringBuffer();
            String line;
            while ((line = json.readLine()) != null) {
                docJson.append(line);
            }
            jsonString = docJson.toString();
            json.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    public MapDto changeToJSON(String jsonString) {
        MapDto coordinate = new MapDto();
        JSONParser parser = new JSONParser();
        try {
            JSONObject document = (JSONObject) parser.parse(jsonString);
            JSONArray jsonArray = (JSONArray) document.get("documents");
            JSONObject position = (JSONObject) jsonArray.get(0);
            String addr_name = (String) position.get("address_name"); // "address_name" 가져오기
            String xString = (String) position.get("x");
            String yString = (String) position.get("y");

            if (isValidDouble(xString) && isValidDouble(yString)) {
                double lon = Double.parseDouble(xString);
                double lat = Double.parseDouble(yString);

                coordinate.setAddress_name(addr_name); // "address_name" 설정
                coordinate.setLongitude(lon);
                coordinate.setLatitude(lat);

            } else {
                // Handle invalid coordinates
                // Maybe set default values or throw an exception
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return coordinate;
    }

    private boolean isValidDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}