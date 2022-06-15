package com.gadimai.measurehealth.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.gadimai.measurehealth.R;
import com.gadimai.measurehealth.Utill.KeyTagList;
import com.gadimai.measurehealth.Utill.UsefulUtil;


public class SplashActivity extends AppCompatActivity {

    private Handler mHandler = new Handler();            //핸들러 초기화
    private UsefulUtil mUsefulUtil = new UsefulUtil(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mUsefulUtil.forDebugLog("SplashActivity onCreate");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHandler.postDelayed(mRunnable, 2000); // 2초 대기
    }

    @Override
    protected void onStop() {
        super.onStop();
        mHandler.removeCallbacks(mRunnable);    //핸들러 사용했으면 반납헤야지.
    }

    @Override
    protected void onDestroy() {
        Log.d(KeyTagList.TagList.TAG_LOG,  getLocalClassName() + " onDestroy()");
        super.onDestroy();
    }

    Runnable mRunnable = new Runnable() {   //핸들러를 쓰려면 런에이블을 써야한다.
        @Override
        public void run() {
            startMainMenuActivity();        //..2초 뒤에 run()함수 안에 있는 기능 실행
            finish();
        }
    };

    //MainMenuActivity로 전환
    private void startMainMenuActivity() {
        Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed(){
        Toast.makeText(this.getApplicationContext(), KeyTagList.Information.INFOR_CAN_NOT_EXIT, Toast.LENGTH_SHORT).show();
    }
}
