package com.gadimai.measurehealth.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.gadimai.measurehealth.R;

import com.gadimai.measurehealth.Utill.UsefulUtil;

import static com.gadimai.measurehealth.Utill.KeyTagList.KeyList.KEY_DEVICE_INFO;
import static com.gadimai.measurehealth.Utill.KeyTagList.KeyList.KEY_DEVICE_INFO_HOSPITAL;
import static com.gadimai.measurehealth.Utill.KeyTagList.KeyList.KEY_DEVICE_INFO_STANDARD;
import static com.gadimai.measurehealth.Utill.KeyTagList.KeyList.KEY_DEVICE_INFO_ETC_EXEMPTION;

public class SelectInfoActivity extends AppCompatActivity {
    private UsefulUtil mUsefulUtil = new UsefulUtil(this);

    // 생성자
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_info);
        mUsefulUtil.forDebugLog("SelectInfoActivity.onCreate() -> _INTI_");
    }

    // 소멸자
    @Override
    public void onDestroy(){
        mUsefulUtil.forDebugLog("SelectInfoActivity.onDestroy()");
        super.onDestroy();
    }

    // 뒤로가기 이벤트 처리
    @Override
    public void onBackPressed(){
        mUsefulUtil.forDebugLog("SelectInfoActivity.onBackPressed()");
        Intent intent = new Intent(this, MainMenuActivity.class);
        startActivity(intent);
        this.finish();
    }



    public void onClick(View view) {
        switch (view.getId()){

            case R.id.selectInfoStandardLayout:             // 등급 관련 정보가 눌리면
                mUsefulUtil.forDebugLog("SelectInfoActivity().onClick() -> selectInfoStandardLayout Clicked!!");
                Intent stdIntent = new Intent(this.getApplicationContext(), ShowInfoListActivity.class);
                stdIntent.putExtra(KEY_DEVICE_INFO, KEY_DEVICE_INFO_STANDARD);

                startActivity(stdIntent);
                mUsefulUtil.forDebugLog("SelectInfoActivity().onClick() -> selectInfoStandardLayout finish");
                break;


            case R.id.selectInfoHospitalLayout:             // 지정 병원 목록이 눌리면
                mUsefulUtil.forDebugLog("SelectInfoActivity().onClick() -> selectInfoHospitalLayout Clicked!");
                Intent hosIntent = new Intent(this, ShowInfoListActivity.class);
                hosIntent.putExtra(KEY_DEVICE_INFO, KEY_DEVICE_INFO_HOSPITAL);
                startActivity(hosIntent);
                break;


            case R.id.selectInfoEtcOfexemptionLayout:        // 기타 결격 사유가 눌리면.
                mUsefulUtil.forDebugLog("SelectInfoActivity().onClick() -> selectInfoEtcOfexemptionLayout Clicked!!");
                Intent etcIntent = new Intent(this, ShowInfoActivity.class);
                etcIntent.putExtra(KEY_DEVICE_INFO, KEY_DEVICE_INFO_ETC_EXEMPTION);

                startActivity(etcIntent);
                break;
        }
    }
}
