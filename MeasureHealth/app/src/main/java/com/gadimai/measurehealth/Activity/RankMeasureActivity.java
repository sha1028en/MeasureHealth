package com.gadimai.measurehealth.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.gadimai.measurehealth.Bluetooth.BluetoothSerialAsyncConnectionHelper;
import com.gadimai.measurehealth.R;
import com.gadimai.measurehealth.Utill.BluetoothResultDict;
import com.gadimai.measurehealth.Utill.KeyTagList;
import com.gadimai.measurehealth.Utill.UsefulUtil;

import org.json.JSONObject;

import static com.gadimai.measurehealth.Utill.KeyTagList.Information.INFOR_RANK_MEASURE_HEARTBEAT;
import static com.gadimai.measurehealth.Utill.KeyTagList.Information.INFOR_RANK_MEASURE_HEIGHT;
import static com.gadimai.measurehealth.Utill.KeyTagList.KeyList.KEY_HEALTH_DICT;
import static com.gadimai.measurehealth.Utill.KeyTagList.Protocol.PROTOCOL_SEND_HEARTBEAT;
import static com.gadimai.measurehealth.Utill.KeyTagList.Protocol.PROTOCOL_SEND_LENGTH;

public class RankMeasureActivity extends AppCompatActivity {

    private TextView mTitleTextView;
    private TextView mValueTextView;
    private TextView mKindTextView;
    private BluetoothSerialAsyncConnectionHelper mConnectionHelper;
    private UsefulUtil mUsefulUtil = new UsefulUtil(this);
    private int count = 0;
    private long mCurrentTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            mUsefulUtil.forDebugLog("RankMeasure.onCreate()");
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_rank_measure);

            mUsefulUtil.forDebugLog("RankMeasure.onCreate() -> inti AsyncConnectionHelper...");
            mConnectionHelper = new BluetoothSerialAsyncConnectionHelper(this, PROTOCOL_SEND_HEARTBEAT       // 블루투스 객체 초기화
                    ,                                                    new BluetoothSerialAsyncConnectionHelper.onReceivedEventLister() {

                /**********************************
                 *
                 *      데이터 수신 이벤트 처리
                 *
                 **********************************/
                @Override
                public void onReceivedEvent(BluetoothResultDict dict) {                             // 블루투스 데이터 수신시 분기...데이터 수신 뒤 처리할 작업.
                    mUsefulUtil.forDebugLog("RankMeasure.onCreate.onReceivedEvent(dict) -> " +
                            mConnectionHelper.getActionMsg() +
                            " : Action Msg");

                    // 받아온 데이터에 'CM' 키워드와 통신 타깃이 신장이면.
                    if(dict.getConnectionResultMessage().contains("CM") && mConnectionHelper.getActionMsg().equals(KeyTagList.Protocol.PROTOCOL_SEND_LENGTH)) {
                        mUsefulUtil.forDebugLog("RankMeasure.onCreate.onReceivedEvent(dict) -> cm receive!!");
                        mUsefulUtil.forDebugLog("RankMeasure.onCreate.onReceivedEvent(dict) -> " + dict.getConnectionResultMessage());
                        mValueTextView.setText(dict.getConnectionResultMessage());      //통신값을 표시한다
                        mConnectionHelper.setHigh(dict.getConnectionResultMessage());   //통신값을 보관한다

                    // 받이온 데이터에 'BPM' 키워드와 통신 타깃이 심박수면.
                    } if(dict.getConnectionResultMessage().contains("BPM") && mConnectionHelper.getActionMsg().equals(PROTOCOL_SEND_HEARTBEAT)){
                        mUsefulUtil.forDebugLog("RankMeasure.onCreate.onReceivedEvent(dict) -> BPM received!");
                        mUsefulUtil.forDebugLog("RankMeasure.onCreate.onReceivedEvent(dict) -> " + dict.getConnectionResultMessage());
                        mValueTextView.setText(dict.getConnectionResultMessage());      //통신값을 표시한다.
                        mConnectionHelper.setBPM(dict.getConnectionResultMessage());    //통신값을 보관한다
                    }

                }
            });
            mUsefulUtil.forDebugLog("RankMeasure.onCreate() -> inti onRcecivedEvent()");

            JSONObject jsonObject = mConnectionHelper.getDeviceListJSON();                          // JSON 객체 초기화...블루투스 디바이스 목록 저장!


            mTitleTextView = findViewById(R.id.rankMeasureTitleTextview);
            mValueTextView = findViewById(R.id.rankMeasureValueTextview);
            mKindTextView = findViewById(R.id.rankMeasureKindOfTextview);
            mTitleTextView.setText(INFOR_RANK_MEASURE_HEARTBEAT);
            mUsefulUtil.forDebugLog("RankMeasure.onCreate() -> set Title Text!");                   // 화면에 표시될 텍스트 초기화.


            mUsefulUtil.forDebugLog("RankMeasure.onCreate() -> before check to bluetooth is enable...");
            if (mConnectionHelper.getCheckStatue() == false) {                                      // 블루투스가 꺼져 있으면...
                mUsefulUtil.forDebugLog("RankMeasure.onCreate() -> request to bluetooth enable...");
                mConnectionHelper.requestToBluetoothEnable();                                       // 블루투스 활성화 요청.
                mConnectionHelper.startDiscovery();                                                 // 페어링 디바이스 목록 탐색.
            }

            // 디바이스 목록이 비어있지 않으면.... 디바이스 선택 다이얼로그 표시
            if(jsonObject != null) {
                final Dialog dialog = new DeviceListInfoDialog(this, new DeviceListInfoDialog.onDeviceSelectEventLister() {

                    /********************************************
                     *
                     *      리스트 뷰 아이템 클릭시 이벤트 처리
                     *
                     ********************************************/
                    @Override
                    public void onDeviceSelected(String deviceName, String deviceAddress) {              //연결 대상 선택시 여기로 분기.
                        mUsefulUtil.forDebugLog("RankMeasureActivity.onCreate().onDeviceSelected() -> jump!!!");
                        mUsefulUtil.forDebugLog("RankMeasureActivity.onCreate().onDeviceSelected() -> device name : " + deviceName);
                        mUsefulUtil.forDebugLog("RankMeasureActivity.onCreate().onDeviceSelected() -> device address : " + deviceAddress);

                        mConnectionHelper.setBluetoothAddress(deviceAddress);
                        mConnectionHelper.setBluetoothName(deviceName);

                        mConnectionHelper.connectToManualSync();
                    }
                    @Override
                    public void onErrorOccur(int e) {
                        mUsefulUtil.forDebugLog("RankMeasure.onCreate().onErrorOccur()");
                        mUsefulUtil.showToast(KeyTagList.Information.IFNOR_NO_PAIRED_DEVICE);
                    }
                });


                dialog.show();
            }
        mUsefulUtil.forDebugLog("RankMeasure.onCreate() ->  _INTI_ finish!!");

        } catch (Exception e) {                                                                             // 그 외의 오류 발생시 분기,

            mUsefulUtil.forDebugLog("RankMeasure.onCreate() -> Exception ERROR occur!!");
            e.printStackTrace();
        }
    }



    /****************************************
     *
     *      뒤로 가기 버튼 오버라이딩 함수
     *
     ****************************************/
    @Override
    public void onBackPressed(){
        mCurrentTime = System.currentTimeMillis();

        if(System.currentTimeMillis() - 2500 < mCurrentTime) {                                              // 2.5초 안에 뒤로가기 버튼이 여러번 눌리면
            Intent intent = new Intent(this, MainMenuActivity.class);                        // 매안매뉴 액티비티로 돌아감.
            startActivity(intent);
            finish();

        } else {
            mUsefulUtil.forDebugLog(KeyTagList.Information.INFOR_CLICKED_BEFORE);                             // 그렇디 않으면. 뒤로가기 안내문구 표시
        }

    }


    @Override
    public void onDestroy(){
        super.onDestroy();
        mConnectionHelper.stopHelper();
        mUsefulUtil.forDebugLog("RankMeasure.onDestroy()");
    }






    /**************************************
     *
     *      뷰 클릭 이벤트 처리 함수
     *
     **************************************/
    public void onClick(View view) {
        count++;


        if (count == 1) {

            mTitleTextView.setText(INFOR_RANK_MEASURE_HEIGHT);
            mConnectionHelper.setAction(PROTOCOL_SEND_LENGTH);
        } if (count > 1) {

            Bundle bundle = new Bundle();
            bundle.putSerializable(KEY_HEALTH_DICT, mConnectionHelper.getHealthInfoDict());

            Intent intent = new Intent(this, DetailRankMeasureActivity.class);
            intent.putExtras(bundle);

            startActivity(intent);
            mUsefulUtil.forDebugLog("RankMeasure -> finish()");
            finish();
        }
    }
}
