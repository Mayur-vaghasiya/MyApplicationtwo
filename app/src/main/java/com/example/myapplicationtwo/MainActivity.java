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
import java.util.jar.JarEntry;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Toolbar toolbar;
    private LinearLayoutManager layoutManager = null;
    private RecyclerView recyclerViewUnitList;
    private ArrayList<UnitModel.Unit> unitList = null;
    private Activity activity = null;
    private CleanableEditText search;
    private UnitListAdapter unitListAdapter=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = MainActivity.this;
        search = (CleanableEditText) findViewById(R.id.search);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerViewUnitList = (RecyclerView) findViewById(R.id.rvUnitList);
        layoutManager = new LinearLayoutManager(activity, layoutManager.VERTICAL, false);
        recyclerViewUnitList.setLayoutManager(layoutManager);
        setRecyclerViewData();
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

            StringBuilder buf = new StringBuilder();
            InputStream json = activity.getAssets().open("jsondata.json");

            BufferedReader in =new BufferedReader(new InputStreamReader(json, "UTF-8"));
            String str;

            while ((str = in.readLine()) != null) {
                buf.append(str);
            }
            in.close();

            JSONArray jsonArray=new JSONArray(buf.toString());
            Log.e("Response", jsonArray.toString());

            Gson gson = new GsonBuilder().serializeNulls().create();
            UnitModel gsonModel = gson.fromJson(jsonArray.toString(), UnitModel.class);
            unitList = gsonModel.getUnits();
            setRecyclerViewData();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

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
        recyclerViewUnitList.setAdapter(unitListAdapter);
    }
}