package com.gadimai.measurehealth.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.gadimai.measurehealth.Activity.CustomListview.CustomAdapter;
import com.gadimai.measurehealth.R;
import com.gadimai.measurehealth.Utill.Archive.ShowInfoArchive;
import com.gadimai.measurehealth.Utill.UsefulUtil;

import static com.gadimai.measurehealth.Utill.KeyTagList.KeyList.KEY_DEVICE_INFO;
import static com.gadimai.measurehealth.Utill.KeyTagList.KeyList.KEY_DEVICE_INFO_HOSPITAL;
import static com.gadimai.measurehealth.Utill.KeyTagList.KeyList.KEY_DEVICE_INFO_STANDARD;
import static com.gadimai.measurehealth.Utill.KeyTagList.KeyList.KEY_VA;
import static com.gadimai.measurehealth.Utill.KeyTagList.KeyList.KEY_WEIGHT;
import static com.gadimai.measurehealth.Utill.KeyTagList.Title.TITLE_DEVICE_INFO_HOSPITAL;

import java.util.ArrayList;
import java.util.List;


public class ShowInfoListActivity extends AppCompatActivity {
    private TextView mTitleTextview;
    private UsefulUtil mUsefulUtil;
    private ListView mListView;
    private static List<String> item = new ArrayList<String>() {};
    private ShowInfoArchive archive;
    private String msg = new String();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_infor_list);
        mListView = findViewById(R.id.showInfoListview);
        archive  = new ShowInfoArchive(this.getApplicationContext());

        mUsefulUtil = new UsefulUtil(this);
        mUsefulUtil.forDebugLog("showInfoListActivity.onCreate() -> _INTI_");
        mTitleTextview = findViewById(R.id.showInfoListTitleTextview);


        Bundle bundle = this.getIntent().getExtras();
        mUsefulUtil.forDebugLog("showInfoListActivity.onCreate() -> bundle.getIntent().getExtras()");
        msg = bundle.getString(KEY_DEVICE_INFO);


        if(msg.equals(KEY_DEVICE_INFO_HOSPITAL)){

            mUsefulUtil.forDebugLog("ShowInfoListActivity.onCreate() -> hospital select!");
            setHospitalListview();
        }



        if(msg.equals(KEY_DEVICE_INFO_STANDARD)){
            mUsefulUtil.forDebugLog("ShowInfoListActivity.onCreate() -> standard select!");
            setStandardListview();
        }

        setOnItemClickListener();
    }



    private void setHospitalListview(){
        item.removeAll(item);
        mTitleTextview.setText(TITLE_DEVICE_INFO_HOSPITAL);
        CustomAdapter adapter = new CustomAdapter();

        for(int i=0; i < archive.getHospitalSeoulNameLength(); i++)
            adapter.addItem(archive.getHospitalSeoulNameByIndex(i), archive.getHospitalSeoulPhByIndex(i));
        mListView.setAdapter(adapter);
    }



    private void setStandardListview(){
        item.removeAll(item);
        mTitleTextview.setText("등급 판정 기준");

        CustomAdapter adapter = new CustomAdapter();

        adapter.addItem(getResources().getString(R.string.unit_BMI), getResources().getString(R.string.introduce_BMI));
        adapter.addItem(getResources().getString(R.string.unit_va), getResources().getString(R.string.introduce_va));

        mListView.setAdapter(adapter);

    }



    private void setOnItemClickListener(){
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    mUsefulUtil.forDebugLog("ShowInfoListActivity.setOnItemClickListener() -> jump!!!");

                    // 지정 병원의 경우 이벤트 처리
                    if(msg.equals(KEY_DEVICE_INFO_HOSPITAL)){

                    }



                    // 등급 판정 기준의 경우 이벤트 처리
                    if(msg.equals(KEY_DEVICE_INFO_STANDARD)){
                        if(position == 0){      // BMI 아이템 클릭
                            Intent bmiIntent = new Intent(ShowInfoListActivity.this.getApplicationContext(), ShowInfoActivity.class);
                            bmiIntent.putExtra(KEY_DEVICE_INFO, KEY_WEIGHT);
                            startActivity(bmiIntent);
                            ShowInfoListActivity.this.fileList();

                        } if(position == 1) {   // 시력 아이템 클릭
                            Intent bmiIntent = new Intent(ShowInfoListActivity.this.getApplicationContext(), ShowInfoActivity.class);
                            bmiIntent.putExtra(KEY_DEVICE_INFO, KEY_VA);
                            startActivity(bmiIntent);
                            ShowInfoListActivity.this.fileList();
                        }
                    }

                } catch (Exception e){

                    mUsefulUtil.forDebugLog("ShowInfoActivity.setOnItemClickListener() -> Exception ERROR occur!!!");
                    e.printStackTrace();
                }
            }
        });
    }
}