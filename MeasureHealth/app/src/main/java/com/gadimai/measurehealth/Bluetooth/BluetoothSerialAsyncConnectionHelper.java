package com.gadimai.measurehealth.Bluetooth;

import android.content.Context;

import com.gadimai.measurehealth.Utill.BluetoothResultDict;
import com.gadimai.measurehealth.Utill.HealthInfoDict;
import com.gadimai.measurehealth.Utill.UsefulUtil;

import org.json.JSONObject;

import androidx.annotation.NonNull;




/***************************************************************************
 *
 *      만든놈 : 1701100586 박동수(prst2486)
 *      작업 기간 : 2019/04/24 ~ 2019/09/19
 *
 *      수신값을 실시간으로 갱신하면서 통신 하는 과정에서
 *      수긴값 대기를 위해 메인 스레드를 지연시키지 않으려면
 *      비동기 통신을 해야할것 같다...
 *
 *      일단 모체 클래스인 BluetoothSerialConnectionHelper 를 상속하여.
 *      부모 클래스에 있는 기능을 활용 해서 작업을 할 예정 이다....아마도?
 *
 *      BluetoothSerialConnectionHelper 의 규모가 너무 커지는것 같아서
 *      해당 클래스를 따로 팠다. 규모가 너무 커지면 작업하기 너무 힘들잖어~
 *
 ***************************************************************************/



public class BluetoothSerialAsyncConnectionHelper extends BluetoothSerialConnectionHelper{
    private Context mContext;
    private UsefulUtil mUsefulUtil;
    public onReceivedEventLister mReceivedEventLister;
    private HealthInfoDict mInfoDict;

    public interface onReceivedEventLister{     // 데이터 수신 이벤트
        public abstract void onReceivedEvent(BluetoothResultDict dict);
    }

    // 생성자
    public BluetoothSerialAsyncConnectionHelper(@NonNull Context inputContext, String inputMsg, BluetoothSerialAsyncConnectionHelper.onReceivedEventLister lister) {
        super(inputContext, inputMsg);
        mUsefulUtil = new UsefulUtil(inputContext);
        mContext = inputContext;
        mReceivedEventLister = lister;
        mInfoDict = new HealthInfoDict();

        this.startReceivedListener();
        mUsefulUtil.forDebugLog("BluetoothSerialAsyncConnectionHelper -> _INTI_");
    }

    // 동기식 자동 통신대상 연결.
    public void connectToAutoSync(){

        mUsefulUtil.forDebugLog("BluetoothSerialAsyncConnectionHelper.connectToAutoSync -> Start");
        connectToAuto();
        mUsefulUtil.forDebugLog("BluetoothSerialAsyncConnectionHelper.connectToAutoSync -> Finish");

    }

    // 동기식 수동 통신대상 연결.
    public void connectToManualSync(){
        mUsefulUtil.forDebugLog("BluetoothSerialAsyncConnectionHelper.connectToManualSync -> Start");
        connectToManual();
        mUsefulUtil.forDebugLog("BluetoothSerialAsyncConnectionHelper.connectToManualSync -> Finish");

    }


    // 비동기식 데이터 송신.
    public void sendToAsync(){

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                mUsefulUtil.forDebugLog("BluetoothSerialAsyncConnectionHelper.sendToAsync -> Start");
                send();
                mUsefulUtil.forDebugLog("BluetoothSerialAsyncConnectionHelper.sendToAsync -> Finish");
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }

    // 동기식 데이터 송신.
    public void sendToSync(){

        mUsefulUtil.forDebugLog("bluetoothHelper.sendToSync -> Start");
        send();
        mUsefulUtil.forDebugLog("bluetoothHelper.sendToSync -> Finish");
    }







    // 시력 리턴.
    public String getRightVa() { return this.mInfoDict.getRightVa(); }

    public String getLeftVa() { return this.mInfoDict.getLeftVa(); }

    // 신장 리턴.
    public String getHigh() { return this.mInfoDict.getHigh(); }

    // 심박수 리턴.
    public String getBPM() { return this.mInfoDict.getHeartBeat(); }

    // 무게 리턴.
    public String getWeight() { return this.mInfoDict.getWegiht(); }

    // 신체 정보 객체 리턴
    public HealthInfoDict getHealthInfoDict() { return this.mInfoDict.getHealthInfoDict(); }

    // 블루투스 대상의 정보 객체 리턴.
    @Override
    public BluetoothResultDict getResultBluetoothDict(){
        return super.getResultBluetoothDict();
    }

    // 현재 통신 대상의 목적을 리턴.
    @Override
    public String getActionMsg() { return super.getActionMsg(); }


    // 블루투스 사용 가능 여부를 리턴.
    @Override
    public boolean getCheckStatue() {
        mUsefulUtil.forDebugLog("BluetoothSerialAsyncConnectionHelper.checkStatue()");
        return super.getCheckStatue();
    }


    // 페어링 디바이스 리스트를 JSON 객체로 리턴.
    @Override
    public JSONObject getDeviceListJSON() {
        mUsefulUtil.forDebugLog("BluetoothSerialAsyncConnectionHelper.getDeviceListJSON()");
        return super.getDeviceListJSON();
    }



    // 시력 설정.
    public void setRightVa(String va) { this.mInfoDict.setRightVA(va); }

    public void setLeftVa(String va) { this.mInfoDict.setLeftVa(va); }

    // 신장 설정.
    public void setHigh(String high) { this.mInfoDict.setHigh(high); }

    // 심박수 설정.
    public void setBPM(String bpm) { this.mInfoDict.setHeartBeat(bpm); }

    // 무게 설정.
    public void setWeight(String weight) { this.mInfoDict.setWegiht(weight); }

    // 신체 정보 객체 설정.
    public void setHealthInfoDict(String rightVa, String leftVa, String high, String bpm, String weight) {
        this.mInfoDict.setHealthInfoDict(rightVa, leftVa, high, bpm, weight);
    }

    // 통신 목적 설정.
    @Override
    public void setAction(String msg){ super.setAction(msg); }

    // 블루투스 대상의 MAC주소 설정.
    @Override
    public void setBluetoothAddress(String address) { super.setBluetoothAddress(address); }

    // 블루투스 대상의 디바이스 이름 설정.
    @Override
    public void setBluetoothName(String name) { super.setBluetoothName(name); }

    // 페어링 디바이스 탐색
    @Override
    public void startDiscovery() { super.startDiscovery(); }




    // BluetoothSPP 사용 중단.
    @Override
    public void stopHelper() {
        mUsefulUtil.forDebugLog("BluetoothSerialAsyncConnectionHelper.stopHelper()");
        super.stopHelper();
    }




    // 데이터 수신 이벤트
    @Override
    public void requestToBluetoothEnable(){
        mUsefulUtil.forDebugLog("BluetoothSerialAsyncConnectionHelper.requestToBluetoothEnable()");
        super.requestToBluetoothEnable();
    }



    // 데이터 수신 이벤트 시작
    @Override
    protected void startReceivedListener(){
        setEventLister(mReceivedEventLister);
        super.startReceivedListener();
    }
}