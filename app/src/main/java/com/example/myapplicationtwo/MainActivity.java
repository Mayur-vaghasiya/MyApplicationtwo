package com.example.myapplicationtwo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.myapplicationtwo.adapter.UnitListAdapter;
import com.example.myapplicationtwo.model.UnitList;
import com.example.myapplicationtwo.model.UnitModel;
import com.example.myapplicationtwo.util.CleanableEditText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Toolbar toolbar;
    private ArrayList<UnitModel.Unit> unitList = null;
    private Activity activity = null;
    private CleanableEditText search;
    private RecyclerView rvUnitList = null;
    private LinearLayoutManager layoutManager = null;
    private UnitListAdapter unitListAdapter = null;
    private TextView myResponse;
    private ArrayList<UnitList> unitLists = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = MainActivity.this;
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        search = (CleanableEditText) findViewById(R.id.search);
        myResponse = findViewById(R.id.textResponse);
        rvUnitList = (RecyclerView) findViewById(R.id.rvUnitList);

        layoutManager = new LinearLayoutManager(activity, layoutManager.VERTICAL, false);
        rvUnitList.setLayoutManager(layoutManager);


        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_back);
        upArrow.setColorFilter(getResources().getColor(R.color.golden), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.left_in, R.anim.right_out);
            }
        });


        try {
            String json = null;
            InputStream inputStream = getAssets().open("data.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            // read values in the byte array
            inputStream.read(buffer);
            inputStream.close();
            // convert byte to string
            json = new String(buffer, "UTF-8");

            Log.e("Response", json.toString());

            Gson gson = new GsonBuilder().serializeNulls().create();
           /* UnitModel gsonModel = gson.fromJson(jsonArray.toString(), UnitModel.class);
            unitList = gsonModel.getUnits();*/
            Type listType = new TypeToken<ArrayList<UnitModel.Unit>>() {
            }.getType();
            unitList= gson.fromJson(json, listType);
            setRecyclerViewData();

            myResponse.setText(json);

            setRecyclerViewData();

        } catch (IOException e) {
            e.printStackTrace();
        } /*catch (JSONException jsonException) {
            jsonException.printStackTrace();
        }*/

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence query, int start, int before, int count) {
                if (null != unitList) {
                    String text = search.getText().toString().toLowerCase(Locale.getDefault());
                    unitListAdapter.filters(text);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

    }

    public void setRecyclerViewData() {
        unitListAdapter = new UnitListAdapter(new WeakReference<Context>(activity), unitList);
        rvUnitList.setAdapter(unitListAdapter);
    }
}