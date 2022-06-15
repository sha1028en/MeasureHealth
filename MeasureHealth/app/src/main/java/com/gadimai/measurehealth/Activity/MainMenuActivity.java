package com.gadimai.measurehealth.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.gadimai.measurehealth.R;
import com.gadimai.measurehealth.Utill.KeyTagList;
import com.gadimai.measurehealth.Utill.UsefulUtil;


public class MainMenuActivity extends AppCompatActivity implements View.OnClickListener {
    private UsefulUtil mUsefulUtil = new UsefulUtil(this);
    private long time = 0;
    private Context mContext;

    // 생성자
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);

        mContext = this.getApplicationContext();
        this.time = System.currentTimeMillis();
        mUsefulUtil.forDebugLog("MainActivity().onCreate -> _INTI_");
    }



    // 소멸자
    @Override
    public void onDestroy(){
        mUsefulUtil.forDebugLog("MainActivity().onDestroy() -> onDestroy()");
        super.onDestroy();
    }



    // 뒤로가기, 종료 가 눌리면.
    @Override
    public void onBackPressed(){
        mUsefulUtil.forDebugLog("MainActivity().onBackPressed() -> onBackPressed()");

        if(System.currentTimeMillis() - time < 2500 )
            super.onBackPressed();

        else {
            mUsefulUtil.showToast(KeyTagList.Information.INFOR_CLICKED_EXIT);
            time = System.currentTimeMillis();
        }
    }





    /******************************
     *
     *      뷰 클릭 이벤트 처리
     *
     ******************************/
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.MainmenuMeasureRankLayout:     // 등급 레이아웃이 눌리면,
                mUsefulUtil.forDebugLog("MainActivity().onClick() -> rankMeasureLayout Clicked!!");
                onDialogInti();
                break;

            case R.id.MainmenuInformationLayout:     // 관련 정보 레이아웃이 눌리면.
                mUsefulUtil.forDebugLog("MainActivity().onClick() -> selectInfoLayout Clicked!");
                Intent intent = new Intent(this, SelectInfoActivity.class);
                startActivity(intent);
                this.finish();
                break;

            case R.id.MainmenuExitAppLayout:         // 종료 레이아웃이 눌리면.
                mUsefulUtil.forDebugLog("MainActivity().onClick() -> exitAppLayout Clicked!!");
                onBackPressed();
                break;
        }
    }






    /****************************************
     *
     *      inforCautionDialog 초기화 함수
     *
     ****************************************/
    private void onDialogInti(){                                                                   // inforCautionDialog 전용 이벤트!!!!
        Dialog dialog = new infoCautionDialog(this,  new infoCautionDialog.InforCautionDialogEventListener() {


            /**********************************************
             *
             *      inforCautionDialog 전용 이벤트 처리
             *
             **********************************************/
            @Override
            public void onDialogIsAccept(boolean isEnd) {                                           // inforCautionDialog(다이얼로그) 확인 버튼이 눌리면 분기
                mUsefulUtil.forDebugLog("MainActivity.onDialogIsAccept() -> jump!");
                if(isEnd == true) {

                    mUsefulUtil.forDebugLog("MainActivity.onDialogIsAccept() -> startActivity()");
                    Intent intent = new Intent(mContext, RankMeasureActivity.class);                // RankMeasureActivity 를 실행
                    MainMenuActivity.this.startActivity(intent);

                    mUsefulUtil.forDebugLog("MainActivity.onDialogIsAccept() -> finish()");
                    MainMenuActivity.this.finish();                                                 // 해당 액티비티 종료
                }
            }
        });
        mUsefulUtil.forDebugLog("MainActivity.onDialogIsAccept() -> before to show InfoCautionDialog");
        dialog.show();                                                                              // 다이얼로그 표시.
    }
}
