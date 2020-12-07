package com.example.weather;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
public class SecondActivity extends AppCompatActivity {
    ListView CityList;
    int tranid = 0;
    ArrayAdapter simpleAdapter;
    private List<Integer> csid = new ArrayList<>();
    private List<Integer> sid = new ArrayList<>();
    private List<String> csming = new ArrayList<>();
    private List<String> csma = new ArrayList<>();
    /*
    解析和处理服务器返回的市级数据
     */
    private void parseJSONWithJSONObject(String jsonData){
        try{
            JSONArray jsonArray = new JSONArray(jsonData);
            for(int i = 0;i<jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Integer id = jsonObject.getInt("id");
                Integer pid = jsonObject.getInt("pid");
                String city_code = jsonObject.getString("city_code");
                String city_name = jsonObject.getString("city_name");
                if(pid == tranid ) {
                    csid.add(id);
                    sid.add(pid);
                    csma.add(city_code);
                    csming.add(city_name);
                }
            }
            if(tranid == 33){
                csid.add(33);
                sid.add(0);
                csma.add("101330101");
                csming.add("澳门");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
//    获取Jason
    public static String getJson(String fileName, Context context) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            InputStream is = context.getAssets().open(fileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
//    获取控件实例
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        CityList = findViewById(R.id.citylist);

        Intent intent = getIntent();
        tranid = intent.getIntExtra("tran",-1);

        String responseData = getJson("city.json",this);
        parseJSONWithJSONObject(responseData);


        simpleAdapter = new ArrayAdapter(SecondActivity.this,android.R.layout.simple_list_item_1,csming);
        //初始化ArrayAdapter，将它设置为ListViiew适配器
        CityList.setAdapter(simpleAdapter);
        CityList = findViewById(R.id.citylist);
        CityList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void  onItemClick(AdapterView<?> parent, View view , int position , long id){
                String trancode = csma.get(position);
                Intent intent = new Intent(SecondActivity.this, com.example.weather.Weather.class);
                intent.putExtra("trancitycode",trancode);
                startActivity(intent);
            }
        });
    }
}