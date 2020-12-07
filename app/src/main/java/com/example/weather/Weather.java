package com.example.weather;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSON;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;



public class Weather extends AppCompatActivity implements View.OnClickListener{
    TextView Textshow;
    String researchcitycode;
    Button Concern,shuaxin;
    String displayweather;

    private String city;
    private App.forecast prediction0,prediction1,prediction2,prediction3,prediction4;
    private String cityId;
    private String province;
    private String gengxin;
    private String time;
    private String date;
    private String info;
    private String status;
    private String humidity;
    private String pm25;
    private String pm10;
    private String quality;
    private String temperature;
    private String cold;
    private List<App.forecast> prediction;
    private String ymd;
    private String date_1;
    private String week;
    private String sunrise;
    private String high;
    private String low;
    private String sunset;
    private String aqi;
    private String wd;
    private String wp;
    private String type;
    private String notice;
    //今天和未来四天的天气情况
    private String riqi0,riqi1,riqi2,riqi3,riqi4;
    private String now0,now1,now2,now3,now4;
    private String zhou0,zhou1,zhou2,zhou3,zhou4;
    private String up0,up1,up2,up3,up4;
    private String high0,high1,high2,high3,high4;
    private String low0,low1,low2,low3,low4;
    private String down0,down1,down2,down3,down4;
    private String kqzs0,kqzs1,kqzs2,kqzs3,kqzs4;
    private String winddirection0,winddirection1,winddirection2,winddirection3,winddirection4;
    private String windpower0,windpower1,windpower2,windpower3,windpower4;
    private String type0,type1,type2,type3,type4;
    private String tz0,tz1,tz2,tz3,tz4;
    int databaseid;
    String databasedata;
    int sign = 1;
    @Override
    /*创建菜单
     */
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        Textshow = findViewById(R.id.TextView);
        Concern = findViewById(R.id.concern1);
        Concern.setOnClickListener(this);
        shuaxin = findViewById(R.id.refresh);
        shuaxin.setOnClickListener(this);


        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        researchcitycode = extras.getString("trancitycode");


        MyDataBaseHelper dbHelper = new MyDataBaseHelper(this,"Weather.db",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();     //同上，获得可写文件
        Cursor cursor  = db.query("Weather",new String[]{"id","data"},"id=?",new String[]{researchcitycode+""},null,null,null);

        if(cursor.moveToNext()) {       //逐行查找，得到匹配信息
            do {
                databaseid = cursor.getInt(cursor.getColumnIndex("id"));
                databasedata = cursor.getString(cursor.getColumnIndex("data"));
            } while (cursor.moveToNext());
        }
        int tranformat = 0;
        tranformat = Integer.parseInt(researchcitycode);

        if(databaseid ==  tranformat ){
            sign = 1;//优先从数据库查询
            showResponse(databasedata);
        }else {
            sign = 0;//如果没有查询再去服务器上查询
            sendRequestWithOkHttp();
        }

    }
/*
处理服务器响应
 */

    private void sendRequestWithOkHttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{

                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://t.weather.itboy.net/api/weather/city/"+researchcitycode)
                            .build();//传入请求地址，并注册一个回调来处理服务器响应
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    Log.d("data is", responseData);
                    showResponse(responseData);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        }).start();
    }
    /*
    解析和处理服务器返回的县级数据
     */
    private void parseJSONWithFastJSON(String jsonData){
        if(jsonData.length()<100){
            Log.d("M","城市ID不存在");
            Toast.makeText(this,"城市ID不存在，请重新输入",Toast.LENGTH_LONG).show();
            Weather.this.setResult(RESULT_OK,getIntent());
            Weather.this.finish();
        }
        else {
            App app = JSON.parseObject(jsonData, App.class);
            time = app.getTime();
            info = app.getMessage();
            status = app.getStatus();
            date = app.getDate();


            App.CityInfo cityInfo = app.getCityInfo();
            city = cityInfo.getCity();
            cityId = cityInfo.getCityId();
            province = cityInfo.getParent();
            gengxin = cityInfo.getUpdateTime();


            App.data data = app.getData();
            humidity = data.getShidu();
            pm10 = data.getPm10();
            pm25 = data.getPm25();
            quality = data.getQuality();
            cold = data.getGanmao();
            temperature = data.getWendu();
            prediction = data.getForecast();
//获取今天和未来四天的天气情况
//            昨天天气情况
            prediction0 = prediction.get(0);
            riqi0 = prediction0.getDate();
            high0 = prediction0.getHigh();
            low0 = prediction0.getLow();
            zhou0 = prediction0.getWeek();
            up0 = prediction0.getSunrise();
            now0 = prediction0.getYmd();
            down0 = prediction0.getSunset();
            kqzs0 = prediction0.getAqi();
            winddirection0 = prediction0.getFx();
            windpower0 = prediction0.getFl();
            tz0 = prediction0.getNotice();
            type0 = prediction0.getType();
//今天天气情况
            prediction1 = prediction.get(1);
            riqi1 = prediction1.getDate();
            high1 = prediction1.getHigh();
            low1 = prediction1.getLow();
            zhou1 = prediction1.getWeek();
            up1 = prediction1.getSunrise();
            now1 = prediction1.getYmd();
            down1 = prediction1.getSunset();
            kqzs1 = prediction1.getAqi();
            winddirection1 = prediction1.getFx();
            windpower1 = prediction1.getFl();
            tz1 = prediction1.getNotice();
            type1 = prediction1.getType();

//明天天气情况
            prediction2 = prediction.get(2);
            riqi2 = prediction2.getDate();
            high2 = prediction2.getHigh();
            low2 = prediction2.getLow();
            zhou2 = prediction2.getWeek();
            up2 = prediction2.getSunrise();
            now2 = prediction2.getYmd();
            down2 = prediction2.getSunset();
            kqzs2 = prediction2.getAqi();
            winddirection2 = prediction2.getFx();
            windpower2 = prediction2.getFl();
            tz2 = prediction2.getNotice();
            type2 = prediction2.getType();
//未来两日天气情况
            prediction3 = prediction.get(3);
            riqi3 = prediction3.getDate();
            high3 = prediction3.getHigh();
            low3 = prediction3.getLow();
            zhou3 = prediction3.getWeek();
            up3 = prediction3.getSunrise();
            now3 = prediction3.getYmd();
            down3 = prediction3.getSunset();
            kqzs3 = prediction3.getAqi();
            winddirection3 = prediction3.getFx();
            windpower3 = prediction3.getFl();
            tz3 = prediction3.getNotice();
            type3 = prediction3.getType();

//未来三日天气情况
            prediction4 = prediction.get(4);
            riqi4 = prediction4.getDate();
            high4 = prediction4.getHigh();
            low4 = prediction4.getLow();
            zhou4 = prediction4.getWeek();
            up4 = prediction4.getSunrise();
            now4 = prediction4.getYmd();
            down4 = prediction4.getSunset();
            kqzs4 = prediction4.getAqi();
            winddirection4 = prediction4.getFx();
            windpower4 = prediction4.getFl();
            tz4 = prediction4.getNotice();
            type4 = prediction4.getType();

//未来四日天气情况
            App.yesterday yesterday = data.getYesterday();
            ymd = yesterday.getYmd();
            week = yesterday.getWeek();
            sunrise = yesterday.getSunrise();
            high = yesterday.getHigh();
            low = yesterday.getLow();
            sunset = yesterday.getSunset();
            aqi = yesterday.getAqi();
            wp = yesterday.getFl();
            wd = yesterday.getFx();
            notice = yesterday.getNotice();
            type = yesterday.getType();
            date_1 = yesterday.getDate();

            if (sign == 0) {
                MyDataBaseHelper dbHelper = new MyDataBaseHelper(this, "Weather.db", null, 1);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("id", researchcitycode);
                values.put("data", jsonData);
                db.insert("Weather", null, values);
                Log.d("MainActivity", "数据库写入成功");
            } else if (sign == 1) {
                Log.d("数据库写入失败：", "数据已存在");

            } else {
                MyDataBaseHelper dbHelper = new MyDataBaseHelper(this, "Weather.db", null, 1);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("id", researchcitycode);
                values.put("data", jsonData);
                db.update("Weather", values, "id=?", new String[]{researchcitycode + ""});
                Log.d("MainActivity", "数据库更新成功");

            }

        }
    }

//显示天气信息

    private void showResponse(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                parseJSONWithFastJSON(response);
                displayweather = "数据更新时间:"+time+"\n"+"当前状态："+info+"\n"+"状态号:"+status+"\n"+"当前日期:"+date+ "\n"+"当前城市:"+city+"\n"+"城市ID:"+cityId+"\n"+"所在省:"+province+"\n"+"更新时间"+gengxin;
                displayweather = displayweather+"\n"+"空气湿度"+humidity+"\n"+"pm10:"+pm10+"\n"+"pm2.5:"+pm25+"\n"+"空气质量:"+quality+"\n"+"活动适宜群体:"+cold+"\n"+"当前温度"+temperature+"\n";
                displayweather = displayweather+"当前日期:"+now0+"\n"+zhou0+"\n"+"日出时间:"+up0+"\n"+"最高温度:"+high0+"\n"+"最低温度:"+low0+"\n"+"日落时间："+down0+"\n"+"空气指数："+kqzs0+"\n"+"风力："+windpower0+"\n"+"风向："+winddirection0+"\n"+"提示:"+tz0+"\n"+"天气:"+type0;
                Textshow.setText(displayweather);
            }
        });
    }
//点击事件
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.concern1:
                /*
                SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
                editor.putString("citycode",researchcitycode);
                editor.apply();
                Toast.makeText(this, "关注成功！", Toast.LENGTH_LONG).show();
                */
                MyDataBaseHelper dbHelper = new MyDataBaseHelper(this, "Concern.db", null, 1);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("city_code", researchcitycode);
                values.put("city_name", city);
                db.insert("Concern", null, values);
                Toast.makeText(this, "关注成功！", Toast.LENGTH_LONG).show();
                break;
            case  R.id.refresh:
                sign = 3;
                sendRequestWithOkHttp();
                Log.d("MainActivity","数据库刷新成功");
                break;
        }
    }
/*
显示天气情况
 */

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.yesterday:
                displayweather = "数据更新时间:"+time+"\n"+"当前状态："+info+"\n"+"状态号:"+status+"\n"+"当前日期:"+date+ "\n"+"当前城市:"+city+"\n"+"城市ID:"+cityId+"\n"+"所在省:"+province+"\n"+"更新时间"+gengxin;
                displayweather = displayweather+"\n"+"空气湿度"+humidity+"\n"+"pm10:"+pm10+"\n"+"pm2.5:"+pm25+"\n"+"空气质量:"+quality+"\n"+"活动适宜群体:"+cold+"\n"+"当前温度"+temperature+"\n";
                displayweather = displayweather+"当前日期:"+ymd+"\n"+week+"\n"+"日出时间:"+sunrise+"\n"+"最高温度:"+high+"\n"+"最低温度:"+low+"\n"+"日落时间："+sunset+"\n"+"空气指数："+aqi+"\n"+"风力："+wp+"\n"+"风向："+wd+"\n"+"提示:"+notice+"\n"+"天气:"+type;

                Textshow.setText(displayweather);
                break;
            case  R.id.today:
                displayweather = "数据更新时间:"+time+"\n"+"当前状态："+info+"\n"+"状态号:"+status+"\n"+"当前日期:"+date+ "\n"+"当前城市:"+city+"\n"+"城市ID:"+cityId+"\n"+"所在省:"+province+"\n"+"更新时间"+gengxin;
                displayweather = displayweather+"\n"+"空气湿度"+humidity+"\n"+"pm10:"+pm10+"\n"+"pm2.5:"+pm25+"\n"+"空气质量:"+quality+"\n"+"活动适宜群体:"+cold+"\n"+"当前温度"+temperature+"\n";
                displayweather = displayweather+"当前日期:"+now0+"\n"+zhou0+"\n"+"日出时间:"+up0+"\n"+"最高温度:"+high0+"\n"+"最低温度:"+low0+"\n"+"日落时间："+down0+"\n"+"空气指数："+kqzs0+"\n"+"风力："+windpower0+"\n"+"风向："+winddirection0+"\n"+"提示:"+tz0+"\n"+"天气:"+type0;

                Textshow.setText(displayweather);
                break;
            case R.id.forecast1:
                displayweather = "数据更新时间:"+time+"\n"+"当前状态："+info+"\n"+"状态号:"+status+"\n"+"当前日期:"+date+ "\n"+"当前城市:"+city+"\n"+"城市ID:"+cityId+"\n"+"所在省:"+province+"\n"+"更新时间"+gengxin;
                displayweather = displayweather+"\n"+"空气湿度"+humidity+"\n"+"pm10:"+pm10+"\n"+"pm2.5:"+pm25+"\n"+"空气质量:"+quality+"\n"+"活动适宜群体:"+cold+"\n"+"当前温度"+temperature+"\n";
                displayweather = displayweather+"当前日期:"+now1+"\n"+zhou1+"\n"+"日出时间:"+up1+"\n"+"最高温度:"+high1+"\n"+"最低温度:"+low1+"\n"+"日落时间："+down1+"\n"+"空气指数："+kqzs1+"\n"+"风力："+windpower1+"\n"+"风向："+winddirection1+"\n"+"提示:"+tz1+"\n"+"天气:"+type1;
                Textshow.setText(displayweather);
                break;
            case  R.id.forecast2:
                displayweather = "数据更新时间:"+time+"\n"+"当前状态："+info+"\n"+"状态号:"+status+"\n"+"当前日期:"+date+ "\n"+"当前城市:"+city+"\n"+"城市ID:"+cityId+"\n"+"所在省:"+province+"\n"+"更新时间"+gengxin;
                displayweather = displayweather+"\n"+"空气湿度"+humidity+"\n"+"pm10:"+pm10+"\n"+"pm2.5:"+pm25+"\n"+"空气质量:"+quality+"\n"+"活动适宜群体:"+cold+"\n"+"当前温度"+temperature+"\n";
                displayweather = displayweather+"当前日期:"+now2+"\n"+zhou2+"\n"+"日出时间:"+up2+"\n"+"最高温度:"+high2+"\n"+"最低温度:"+low2+"\n"+"日落时间："+down2+"\n"+"空气指数："+kqzs2+"\n"+"风力："+windpower2+"\n"+"风向："+winddirection2+"\n"+"提示:"+tz2+"\n"+"天气:"+type2;
                Textshow.setText(displayweather);
                break;
            case  R.id.forecast3:
                displayweather = "数据更新时间:"+time+"\n"+"当前状态："+info+"\n"+"状态号:"+status+"\n"+"当前日期:"+date+ "\n"+"当前城市:"+city+"\n"+"城市ID:"+cityId+"\n"+"所在省:"+province+"\n"+"更新时间"+gengxin;
                displayweather = displayweather+"\n"+"空气湿度"+humidity+"\n"+"pm10:"+pm10+"\n"+"pm2.5:"+pm25+"\n"+"空气质量:"+quality+"\n"+"活动适宜群体:"+cold+"\n"+"当前温度"+temperature+"\n";
                displayweather = displayweather+"当前日期:"+now3+"\n"+zhou3+"\n"+"日出时间:"+up3+"\n"+"最高温度:"+high3+"\n"+"最低温度:"+low3+"\n"+"日落时间："+down3+"\n"+"空气指数："+kqzs3+"\n"+"风力："+windpower3+"\n"+"风向："+winddirection3+"\n"+"提示:"+tz3+"\n"+"天气:"+type3;
                Textshow.setText(displayweather);
                break;
            case R.id.forecast4:
                displayweather = "数据更新时间:"+time+"\n"+"当前状态："+info+"\n"+"状态号:"+status+"\n"+"当前日期:"+date+ "\n"+"当前城市:"+city+"\n"+"城市ID:"+cityId+"\n"+"所在省:"+province+"\n"+"更新时间"+gengxin;
                displayweather = displayweather+"\n"+"空气湿度"+humidity+"\n"+"pm10:"+pm10+"\n"+"pm2.5:"+pm25+"\n"+"空气质量:"+quality+"\n"+"活动适宜群体:"+cold+"\n"+"当前温度"+temperature+"\n";
                displayweather =displayweather+"当前日期:"+now4+"\n"+zhou4+"\n"+"日出时间:"+up4+"\n"+"最高温度:"+high4+"\n"+"最低温度:"+low4+"\n"+"日落时间："+down4+"\n"+"空气指数："+kqzs4+"\n"+"风力："+windpower4+"\n"+"风向："+winddirection4+"\n"+"提示:"+tz4+"\n"+"天气:"+type4;
                Textshow.setText(displayweather);
                break;
            case R.id.cancel_concern:
                MyDataBaseHelper dbHelper = new MyDataBaseHelper(this,"Concern.db",null,1);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete("Concern","city_code=?",new String[]{researchcitycode+""});
                Toast.makeText(this,"取消关注成功",Toast.LENGTH_LONG).show();
                Weather.this.setResult(RESULT_OK,getIntent());
                Weather.this.finish();

                break;
        }
        return super.onOptionsItemSelected(item);
    }

}