package com.example.samachar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NewsItemClicked {
    RecyclerView recycleView;
    MyAdapter myAdapter;
    String category;
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent=getIntent();
        category=intent.getStringExtra("category");
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        recycleView=(RecyclerView)findViewById(R.id.recycleView);
        recycleView.setLayoutManager(new LinearLayoutManager(this));
        fetchData();
        myAdapter=new MyAdapter(MainActivity.this);
        recycleView.setAdapter(myAdapter);
    }
//    private ArrayList<String> fetchData(){      //Because now it does not return List
//        ArrayList<String>list=new ArrayList<>();
//        for(int i=0;i<100;i++){
//            list.add("Items "+i);
//        }
//        return  list;
//    }
private void fetchData(){

    String url="https://api.currentsapi.services/v1/latest-news?language=en&country=in&category="+category+"&apiKey=z0zlMiLEODWI4AmCCeM27FbYQSIq-zbQ7N5MQSI5dRxK1DoJ";
    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
            (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    try {

                        JSONArray newsJsonArray=response.getJSONArray("news");
                       ArrayList<News>newsArray =new ArrayList<>();
                       for(int i=0;i<newsJsonArray.length();i++){
                           JSONObject newsJsonObject=newsJsonArray.getJSONObject(i);
                           News news=new News(newsJsonObject.getString("title"),newsJsonObject.getString("author"),
                                   newsJsonObject.getString("url"),newsJsonObject.getString("image"));

                           newsArray.add(news);
                       }
                        myAdapter.updateNews(newsArray);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this,"Something is wrong" , Toast.LENGTH_LONG).show();

                }
            });
    // Add the request to the RequestQueue.
    MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
}
    @Override
    public void onItemClicked(News items) {
        //Using Chrome Custom Tab Functionality
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(items.url));
    }
}