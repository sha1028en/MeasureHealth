package com.gadimai.measurehealth.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gadimai.measurehealth.R;
import com.gadimai.measurehealth.Utill.HealthInfoDict;
import com.gadimai.measurehealth.Utill.UsefulUtil;

import static com.gadimai.measurehealth.Utill.KeyTagList.KeyList.KEY_HEALTH_DICT;



public class ResultMeasureActivity extends AppCompatActivity {
    private UsefulUtil mUsefulUtil;
    private HealthInfoDict mInfoDict;
    private Handler mHandler;
    private int mGrade = 0;
    private TextView mResultTextview;
    private TextView mResultInfoTextview;
    private TextView mResultGradeTextview;
    private ImageView mKopoIconImgView;
    private int count = 0;

    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {

            if(mResultTextview.getVisibility() == View.VISIBLE)
                mResultTextview.setVisibility(View.INVISIBLE);
            else
                mResultTextview.setVisibility(View.VISIBLE);

            mHandler.postDelayed(this, 500);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StringBuffer str = new StringBuffer();              // 심박과 신장을 int로 변환하기 위한 임시 변수
        String buffer =  new String();                      // 심박과 신장을 int로 변환하기 위한 임시 변수
        int [] value = new int[2];                          // 심박과 신장을 담고있는 변수

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_measure);

        mUsefulUtil = new UsefulUtil(this.getApplicationContext());
        mUsefulUtil.forDebugLog("ResultMeasureActivity.onCreate() _INTI_");
        mHandler = new Handler();

        mResultTextview = findViewById(R.id.resultRankMeasureRankTextview);             // 등급(급) 텍스트뷰 초기화
        mResultInfoTextview = findViewById(R.id.resultRankMeasureInfoTextTitle);        // 등급 안내 텍스트뷰 초기화
        mResultGradeTextview = findViewById(R.id.resultRankMeasureGradeTextview);       // 등급(직위) 텍스트뷰 초기화
        mKopoIconImgView = findViewById(R.id.resultRankMeasureKopoImgview);

        // 인텐트를 통해 통신했던 값과 입력받은 값을 전달받는다...
        // 좌&우 시력, 체중, 신장, 심박수가 들어있다.
        mUsefulUtil.forDebugLog("ResultMeasureActivity.onCreate() start to getBundle");
        Bundle bundle = this.getIntent().getExtras();
        mInfoDict = new HealthInfoDict();
        mInfoDict = (HealthInfoDict) bundle.getSerializable(KEY_HEALTH_DICT);
        mUsefulUtil.forDebugLog("ResultMeasureActivity.onCreate() -> bundle.getIntent().getExtras()");



        // 받아온 데이터에서 값읋 수출하는 구간
        try {
            mUsefulUtil.forDebugLog("ResultMeasureActivity.onCreate() ->  parsing to BPM & High");
            for (int i = 0; i < value.length; i++) {

                switch (i) {
                    case 0: // 신장
                        buffer = mInfoDict.getHigh(); break;

                    case 1: // 맥박
                        buffer = mInfoDict.getHeartBeat(); break;
                }


                // 통신 받은 값에서 숫자 이외의 값을 제거.
                for(int ㅣ = 0; ㅣ < buffer.length(); ㅣ++) {

                    // 숫자 0 ~ 9의 값만을 Str에 저장.
                    // 한 단어씩 읽어서 ASCII 48 ~ 57 의 값만 담는다.
                    if(48 <= buffer.charAt(ㅣ) && buffer.charAt(ㅣ) <= 57){  // 숫자 0 ~ 9의 문자만 통과
                        str.append(buffer.charAt(ㅣ));
                    }
                }

                mUsefulUtil.forDebugLog("ResultMeasureActivity.onCreate() -> str:" + str);
                value[i] = Integer.valueOf(str.toString());     // 문자열을 숫자(int)로 변환하여 저장.
                mUsefulUtil.forDebugLog("ResultMeasureActivity.onCreate() -> value[" +
                        i +
                        "]" +
                        value[i]);

                str.delete(0, str.length());                               // 스트링버퍼 값 초기화
            }

            mUsefulUtil.forDebugLog("ResultMeasureActivity.onCreate() ->  before to call getGrade()");
            mGrade = getGrade(Integer.valueOf(mInfoDict.getWegiht()),       // 파리미터 : 체중.
                    Double.valueOf(mInfoDict.getRightVa()),                 // 파라미터 : 오른쪽 시력.
                    Double.valueOf(mInfoDict.getLeftVa()),                  // 파리미터 : 왼쪽 시력.
                    value[0], value[1]);                                    // 파라미터 : [0]신장, [1]맥박.
                                                                            // 해당 파라미터 값으로 등급을 판정 받아온다.

        }catch (Exception e){
            e.printStackTrace();
            mUsefulUtil.forDebugLog("ResultMeasureActivity.onCreate() -> Exception ERROR occur!!");
        }

        // 텍스트 뷰에 등급을 표시한다.
        mResultTextview.setText(mGrade + "급");
        mResultGradeTextview.setText(getGrade(mGrade));

        // 등급의 깜박임
        setShowFlash();
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(this.getApplicationContext(), MainMenuActivity.class);
        startActivity(intent);
        this.finish();
    }

    @Override
    public void onDestroy(){
        mUsefulUtil.forDebugLog("ResultMeasureActivity.onDestroy");
        super.onDestroy();
        mHandler.removeCallbacks(mRunnable);
    }


    @Override
    public void onPause(){
        super.onPause();
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.resultRankMeasureQuitButton:
                Intent intent = new Intent(this.getApplicationContext(), MainMenuActivity.class);
                startActivity(intent);
                this.finish();
                break;
            case R.id.resultRankMeasureKopoImgview:
                count++;

                if(count >= 4){
                    mKopoIconImgView.setImageResource(R.mipmap.ic_easter_egg);
                }

        }
    }








    /******************************************
     *
     *  좌우 시력, 체중, 신장, 심박수를 받아서
     *  신체검사 등급을 판정하는 메서드 이다.
     *  등급은 int 타입의 숫자로 리턴.
     *
     ******************************************/
    private int getGrade(int weight, double rightVa, double leftVa, int high, int bpm){
        mUsefulUtil.forDebugLog("ResultMeasure.getGrade() -> before to call getBMI(high, weight)");
        double bmi = getBMI(high, weight);    // BMI 수치를 구하는 메서드


        // 신장 파트
        if (high <= 140)                      // 키 140 이하 : 6급
            return 6;

        if(high > 140 && high <146)           // 키 140초과, 146미만 : 5급
            return 5;


        // 시력 파트
        if(rightVa <= 0.2 || leftVa <= 0.2) // 가장 좋은 눈의 시력이 0.2 이하라면 : 5급
            return 5;

        if(rightVa <= 0.1 || leftVa <= 0.1) // 오른쪽 또는 왼쪽 눈의 시력이 0.1 이하라면 : 5급
            return 5;

        if(rightVa <= 0.6 || leftVa <= 0.6)  // 오른쪽 또는 왼쪽 눈의 시력이 0.6 이하라면. : 4급
            return 4;


        // BMI 파트
        if (high >= 159 && high < 161) {      // 키가 159이상. 161미만
            if(bmi >= 17 && bmi <= 32.9)      // BMI 수치가 17이상, 32.9이하
                return 3;
            else
                return 4;
        }

        if(high >= 161 && high < 204){        // 키다 161이상 204 미만
            if(bmi >= 20 && bmi <= 24.9)      // BMI 수치가 20 이상. 24.9 이하
                return 1;

            if((bmi >= 18.5 && bmi <= 19.9) || (bmi >= 25 && bmi <= 29.9))
                return 2;                     // BMI 수치가 18.5 ~ 19.9, 25 ~ 29.9 : 2급

            if((bmi >= 17 && bmi <= 18.4) || (bmi >= 30 && bmi <= 32.9))
                return 3;                     // BMI 수치가 17 ~ 18.4, 30 ~ 32.9 : 3급

            return 4;                         // BMI 수치가 17미만, 33이상 : 4급

        } else
            return 5;                         // 키가 204 초과 : 5급
    }





    /****************************************************************
     *
     *      신장과 몸무계를 이용하여 BMI 수치를 구하는 메서드 이다.
     *
     *      BMI = 몸무계 / 신장 X 신장
     *
     *      BMI 수치는 double 타입으로 리턴
     *
     *****************************************************************/
    private double getBMI(int high, int weight){
        double bmi;
        double m;

        m = ((double)high) / 100;

        bmi = (weight / (m * m));
        mUsefulUtil.forDebugLog("ResultMeasure.getBMI -> bmi value : " + bmi);

        return bmi;
    }






    /******************************************
     *
     *      신검 등급을 통해
     *      현역, 공익, 면제, 재검을 판단하여
     *      String 타입으로 리턴한다
     *
     ******************************************/
    private String getGrade(int rank){
        String grade;

        mUsefulUtil.forDebugLog("ResultMeasureActivity.getGrade(int rank) : " + rank);
        if(rank <= 3)
            grade = "현역";

        else if(rank == 4) {
            mResultGradeTextview.setTextSize(20);
            grade = "사회복무요원";
        }

        else if(rank <= 6)
            grade = "면제";

        else {
            mResultGradeTextview.setTextSize(20);
            grade = "ERROR OCCUR";
        }

        if (rank == 0) {
            mResultGradeTextview.setTextSize(20);
            grade = "ERROR OCCUR";
        }

        return grade;
    }


    private void setShowFlash(){
            mHandler.post(mRunnable);
    }
}

