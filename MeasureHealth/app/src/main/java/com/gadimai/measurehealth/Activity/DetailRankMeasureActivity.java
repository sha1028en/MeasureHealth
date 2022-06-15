package com.gadimai.measurehealth.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.gadimai.measurehealth.Activity.DebugValueDialog.onDebugValueDialogEventListner;
import com.gadimai.measurehealth.R;
import com.gadimai.measurehealth.Utill.HealthInfoDict;
import com.gadimai.measurehealth.Utill.UsefulUtil;

import static com.gadimai.measurehealth.Utill.KeyTagList.KeyList.KEY_HEALTH_DICT;
import static com.gadimai.measurehealth.Utill.KeyTagList.KeyList.KEY_HEARTBEAT;
import static com.gadimai.measurehealth.Utill.KeyTagList.KeyList.KEY_LENGTH;

public class DetailRankMeasureActivity extends AppCompatActivity {
    private EditText mVaLeftEditText;
    private EditText mVaRightEditText;
    private EditText mWeightEditText;
    private TextView mBPMtextview;
    private TextView mHighTextview;

    private UsefulUtil mUsefulUtil;
    private HealthInfoDict mInfoDict;

    private boolean mDebugWeight = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_rank_measure);

        mUsefulUtil = new UsefulUtil(this.getApplicationContext());
        mUsefulUtil.forDebugLog("DetailRankMeasureActivity.onCreate() -> _INTI_");

        // 뷰 초기화
        mVaLeftEditText = findViewById(R.id.vaDetailLeftEditText);
        mVaRightEditText = findViewById(R.id.vaDetailRightEditText);
        mWeightEditText = findViewById(R.id.weightDetailEditText);
        mBPMtextview = findViewById(R.id.inforDetailResultHeartbeatTextview);
        mHighTextview = findViewById(R.id.inforResultDetailHighTextview);

        // RankMeasureActivity 에서 보낸 값 받아오기
        Bundle bundle =  this.getIntent().getExtras();
        mInfoDict = new HealthInfoDict();
        mInfoDict = (HealthInfoDict) bundle.getSerializable(KEY_HEALTH_DICT);

        // 받아온 값을 뷰에 표시
        mBPMtextview.setText(mInfoDict.getHeartBeat());
        mHighTextview.setText(mInfoDict.getHigh());

        mUsefulUtil.forDebugLog("DetailRankMeasureActivity.onCreate() -> _INTI_ finish");
    }



    public void onClick(View view) {

        switch (view.getId()){

            // 확인 버튼이 눌리면
            case R.id.detailRankMeasureResultBtn:
                Intent intent = new Intent(this.getApplicationContext(), ResultMeasureActivity.class);

                // 입력된 텍스트를 객체에 담기
                mInfoDict.setRightVA(mVaRightEditText.getText().toString());
                mInfoDict.setLeftVa(mVaLeftEditText.getText().toString());
                mInfoDict.setWegiht(mWeightEditText.getText().toString());

                // 객체를 인텐트에 담기
                intent.putExtra(KEY_HEALTH_DICT, mInfoDict);

                // ResultMeasureActivity 액티비티 시작
                startActivity(intent);
                finish();
                break;
            default:
        }


    }

    // 뒤로가기 메서드 오버라리딩. MainMenuActivity 로 전환
    public void onBackPressed(){

        Intent intent = new Intent(this.getApplicationContext(), MainMenuActivity.class);
        startActivity(intent);
        this.finish();
    }


    /******************************************************
     *
     *      측정기가 당장 없어도 등급 계산을 할 수 있도록
     *      값을 수동으로 입력하게끔 하는 다이얼로그 호출
     *
     *      1. "체중" 안내문구 터치
     *      2. 제목 터치
     *
     ******************************************************/
    public void titleOnClick(View view) {

        if(mDebugWeight == true){

            final Dialog debugDialog = new DebugValueDialog(this, new onDebugValueDialogEventListner() {


                /**********************************************************
                 *
                 *      다이얼로그 에서 입력받은 값을 처리(저장)하는 이벤트
                 *
                 **********************************************************/
                @Override
                public void onCallBack(Bundle sendValue) {
                    mUsefulUtil.forDebugLog("DetailRankMeasure.titleOnClick.onCallBack() -> send BPM : ",
                            sendValue.getString(KEY_HEARTBEAT));
                    mUsefulUtil.forDebugLog("DetailRankMeasure.titleOnClick.onCallBack() -> send High : ",
                            sendValue.getString(KEY_LENGTH));

                    mInfoDict.setHeartBeat(sendValue.getString(KEY_HEARTBEAT));
                    mInfoDict.setHigh(sendValue.getString(KEY_LENGTH));

                    mHighTextview.setText(mInfoDict.getHigh() + " cm");
                    mBPMtextview.setText(mInfoDict.getHeartBeat() + " BPM");
                }
            });

            mUsefulUtil.forDebugLog("DetailRankMeasure.titleOnClick() -> DebugValueDialog.show()");
            debugDialog.show();     // 다이얼로그 표시
            mDebugWeight = false;
        }
    }

    public void weightOnClick(View view) {
        mDebugWeight = true;
    }
}
