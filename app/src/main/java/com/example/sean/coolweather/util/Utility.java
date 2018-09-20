package com.example.sean.coolweather.util;

import android.text.TextUtils;
import com.example.sean.coolweather.db.City;
import com.example.sean.coolweather.db.County;
import com.example.sean.coolweather.db.Province;
import com.example.sean.coolweather.gson.Weather;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Utility {

    /*
    * 解析和处理服务器返回的省级数据
    * */
    public static boolean handleProvinceResponse(String response) {
        if(!TextUtils.isEmpty(response)) {
            try{
                JSONArray allProvinces = new JSONArray(response);
                for(int i = 0; i < allProvinces.length(); i++){
                    JSONObject provinceObject = allProvinces.getJSONObject(i);
                    Province province = new Province();
                    province.setProvinceName(provinceObject.getString("name"));
                    province.setProvinceCode(provinceObject.getInt("id"));
                    province.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return  false;
    }

    /*
    * 解析和处理服务器返回的市级数据
    * */
    public static boolean handleCityResponse(String response, int provinceId) {
        if(!TextUtils.isEmpty(response)) {
            try{
                JSONArray allCites = new JSONArray(response);
                for(int i = 0; i < allCites.length(); i++) {
                    JSONObject cityObject = allCites.getJSONObject(i);
                    City city = new City();
                    city.setCityName(cityObject.getString("name"));
                    city.setCityCode(cityObject.getInt("id"));
                    city.setProvinceId(provinceId);
                    city.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /*
    * 解析和处理服务器返回的县级数据
    * */
    public static boolean handleCountyResponse(String response, int cityId)  {
        if(!TextUtils.isEmpty(response)) {
            try{
                JSONArray allCounties = new JSONArray(response);
                for(int i = 0; i < allCounties.length(); i++) {
                    JSONObject countyObject = allCounties.getJSONObject(i);
                    County county = new County();
                    county.setCountyName(countyObject.getString("name"));
                    county.setWeatherId(countyObject.getString("weather_id"));
                    county.setCityId(cityId);
                    county.save();
                }
                return true;
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return false;
    }

    /*
    * 将返回的JSON数据解析成Weather实体类
    * */
    public static Weather handleWeatherResponse(String response) {
        try{
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather");
            String weatherContent = jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(weatherContent,Weather.class);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

/*
 *智能语音注册一个广播监听器
 publicclassmyBroadcastReceiverextendsBroadcastReceiver{
 Toast.makeText(context,"安装成功),Toast.LENGTH_SHORT).show();
 abortBroadcast();//中断广播，减少资源消耗
 }

 在AndroidManifest.xml中进行注册
 <intent-filterandroid:priority="100">//设定优先级，保证智能语音线接收到广播
 <actionandroid:name="com.example.类名.DOWNLOAD_SUCCESS"/>
 <intent-filter>

 在比亚迪应用市场发送广播,如果安装成功，就发送广播
 Intentintent=newIntent("com.example.类名.DOWNLOAD_SUCCESS");
 *sendOrderedBroadcast(intent);//发送有序广播，使得一个app接收到广播之后，其他的app才能接着接收广播

 *
 **/
