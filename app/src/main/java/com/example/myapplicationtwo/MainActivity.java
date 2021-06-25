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

import com.example.myapplicationtwo.adapter.UnitListAdapter;
import com.example.myapplicationtwo.model.UnitModel;
import com.example.myapplicationtwo.util.CleanableEditText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Toolbar toolbar;
    private ArrayList<UnitModel.Unit> unitList = null;
    private Activity activity = null;
    private CleanableEditText search;
    private RecyclerView rvUnitList;
    private LinearLayoutManager layoutManager = null;
    private UnitListAdapter unitListAdapter=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = MainActivity.this;
        search = (CleanableEditText) findViewById(R.id.search);
        rvUnitList = (RecyclerView) findViewById(R.id.rvUnitList);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        layoutManager = new LinearLayoutManager(activity, layoutManager.VERTICAL, false);
        rvUnitList.setLayoutManager(layoutManager);

        try {
            StringBuilder buf = new StringBuilder();
            InputStream json = getAssets().open("jsondata.json");

            BufferedReader in =new BufferedReader(new InputStreamReader(json, "UTF-8"));
            String str;

            while ((str = in.readLine()) != null) {
                buf.append(str);
            }
            in.close();


            Gson gson = new GsonBuilder().serializeNulls().create();
            UnitModel gsonModel = gson.fromJson(in, UnitModel.class);
            unitList = gsonModel.getUnits();
            setRecyclerViewData();

        } catch (IOException e) {
            e.printStackTrace();
        }/* catch (JSONException e) {
            e.printStackTrace();
        }*/
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence query, int start, int before, int count) {
                if ( null != unitList) {
                    String text = search.getText().toString().toLowerCase(Locale.getDefault());
                    unitListAdapter.filters(text);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });

    }

    private void setRecyclerViewData() {
        unitListAdapter = new UnitListAdapter(new WeakReference<Context>(activity), unitList);
        rvUnitList.setAdapter(unitListAdapter);
    }
}