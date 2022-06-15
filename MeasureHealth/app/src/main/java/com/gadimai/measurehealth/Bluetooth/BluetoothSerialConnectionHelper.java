package com.gadimai.measurehealth.Bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;

import com.gadimai.measurehealth.Utill.BluetoothResultDict;
import com.gadimai.measurehealth.Utill.KeyTagList;
import com.gadimai.measurehealth.Utill.UsefulUtil;

import org.json.JSONObject;

import java.util.Set;

import androidx.annotation.NonNull;
import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;

import static com.gadimai.measurehealth.Utill.KeyTagList.Information.INFOR_CONNECTION_FAIL;
import static com.gadimai.measurehealth.Utill.KeyTagList.Information.INFOR_CONNECTION_SUCCESS;
import static com.gadimai.measurehealth.Utill.KeyTagList.Information.INFOR_LOST_CONNECTION;
import static com.gadimai.measurehealth.Utill.KeyTagList.KeyList.KEY_JSON_ADDRESS;
import static com.gadimai.measurehealth.Utill.KeyTagList.KeyList.KEY_JSON_NAME;

/********************************************************************************
 *
 *          작성한놈 : 1701100586 박동수(prst2486)
 *          작성 일지 : 2019/04/16 ~ 2019/05/07
 *
 *          ...아무래도 이 객체는 비동기 상태에서 작업해야 할 것 같다.
 *          메인 스레드가 중지되는걸 막고 실시간 처리를 위해 어쩔 수 없다.
 *
 *          일단 이 클래스는 외부 패키지에서의 사용을 막았다!
 *          BluetoothSerialAsyncConnectionHelper 를 사용하자!
 *
 ********************************************************************************
 *
 *         작성 일지 2차: 2019/08/26 ~ 2019/10/17
 *
 *         connect()메서드를 Auto/Manual 으로 분리함.
 *
 *         자동은 주소와 이름은 자동으로 찾아 통신을 시도...기존 방식과 동일
 *         수동은 주소와 이름음 setBluetooth...(String)을 통해 설정해야 함!!
 *
 *         페어링된 디바이스 들을 JSON 형식으로 반환하는 getDeviceJSON().
 *
 *         블루투스 활성화 요청을 보내는 requestToEnable().
 *
 *         블루투스 활성화 여부를 확인하는 checkStatue(). 등이 있다.
 *
 ********************************************************************************/


class BluetoothSerialConnectionHelper{
    private Context mContext = null;                                // 해당 앱의 정보를 담는 객체
    private BluetoothResultDict mBluetoothResultDict = null;        // 통신 결과값을 담는다.
    private BluetoothSPP mBluetoothSPP = null;                      // 블루투스 직렬통신 핵심.
    private UsefulUtil mUsefulUtil = null;                          // 로그, 토스트 출력용
    private String mOrderMsg = "None";                              // 통신 대상을 담는다,
    private BluetoothSerialAsyncConnectionHelper.onReceivedEventLister mEventLister;


    // 생성자
    protected BluetoothSerialConnectionHelper(@NonNull Context inputContext, String inputMsg) {
        this.mContext = inputContext;
        mUsefulUtil = new UsefulUtil(mContext);
        mBluetoothResultDict = new BluetoothResultDict();
        mOrderMsg = inputMsg;
        mBluetoothSPP = new BluetoothSPP(mContext);

        // 이벤트 리스너 초기화
        this.startStateListener();
        this.startConnectionListener();
        this.startAutoConnectionListener();

        mUsefulUtil.forDebugLog("BluetoothSerialConnectionHelper -> _INTI_");
    }




    /**********************************************************
     *
     *      자동으로 통신 대상을 설정하고 연결하는 메서드이다.
     *
     **********************************************************/
    protected void connectToAuto(){
        try {
            mUsefulUtil.forDebugLog("BluetoothSerialConnectionHelper.connectToAuto() Start!");

            if (this.getCheckStatue() == true) {
                this.mBluetoothSPP.setupService();
                mUsefulUtil.forDebugLog("BluetoothSerialConnectionHelper.connectToAuto() -> setupService() Finish");

                this.mBluetoothSPP.startService(BluetoothState.DEVICE_OTHER);
                mUsefulUtil.forDebugLog("BluetoothSerialConnectionHelper.connectToAuto() -> startService() Finish!");

                mBluetoothSPP.autoConnect(KeyTagList.Protocol.PROTOCOL_BLUETOOTHSPP);
                mUsefulUtil.forDebugLog("BluetoothSerialConnectionHelper.connectToAuto() -> autoConnect() Finish!");
                mUsefulUtil.forDebugLog("BluetoothSerialConnectionHelper.connectToAuto() ->target s Address : " + mBluetoothResultDict.getConnectionResultAddress());
                mUsefulUtil.forDebugLog("BluetoothSerialConnectionHelper.connectToAuto() ->target s Name : " + mBluetoothResultDict.getConnectionResultName());

                mBluetoothSPP.connect(mBluetoothResultDict.getConnectionResultAddress());
                mUsefulUtil.showToast("BluetoothSerialConnectionHelper.connectToAuto() -> success to connect");
            } else {
                mUsefulUtil.showToast("BluetoothSerialConnectionHelper.connectToAuto() -> Failed to connect! is it has correct connection?");
            }
        } catch (Exception e){
            mUsefulUtil.forDebugLog("BluetoothSerialConnectionHelper.connectToAuto() -> Exception! Error occur!!");
        }
    }




    /******************************************************************
     *
     *      mBluetoothResultDict에 통신 대상의 주소와 이름을 저장하고
     *      해당 객체의 값으로 통신 대상을 설정하고 통신하는 메서드 이다.
     *
     ******************************************************************/
    protected void connectToManual(){
        try {
            mUsefulUtil.forDebugLog("BluetoothSerialConnectionHelper.connectToManual() -> Start!");
            mUsefulUtil.forDebugLog("BluetoothSerialConnectionHelper.connectToManual() - > target s Address : " + mBluetoothResultDict.getConnectionResultAddress());
            mUsefulUtil.forDebugLog("BluetoothSerialConnectionHelper.connectToManual() - > target s Name : " + mBluetoothResultDict.getConnectionResultName());

            // 연결 상태가 true, 연결 대상 주소가 "None"이 아니라면.
            if (this.getCheckStatue() == true && !mBluetoothResultDict.getConnectionResultAddress().equals("None")) {
                this.mBluetoothSPP.setupService();
                mUsefulUtil.forDebugLog("BluetoothSerialConnectionHelper.connectToManual() setupService() Finish");

                // 연결 대상 : 기타 디바이스
                this.mBluetoothSPP.startService(BluetoothState.DEVICE_OTHER);
                mUsefulUtil.forDebugLog("BluetoothSerialConnectionHelper.connectToManual() startService() Finish!");

                // MAC 주소로 연결
                mUsefulUtil.forDebugLog("BluetoothSerialConnectionHelper.connectToManual() -> connect(address)");
                mBluetoothSPP.connect(mBluetoothResultDict.getConnectionResultAddress());
            }
        } catch (Exception e){
            mUsefulUtil.forDebugLog("BluetoothSerialConnectionHelper.connectToManual() -> Exception! Error occur!!");
        }
    }


    /********************************************
     *
     *      통신 대상에게 데이터를 송신한다.
     *
     ********************************************/
    protected void send(){
        try {

            switch (mOrderMsg) {
                case KeyTagList.KeyList.KEY_HEARTBEAT:

                    mUsefulUtil.forDebugLog("BluetoothHelper.send -> Send HEARTBEAT");
                    mBluetoothSPP.send(KeyTagList.Protocol.PROTOCOL_SEND_HEARTBEAT.getBytes(), false);
                    break;

                case KeyTagList.KeyList.KEY_LENGTH:

                    mUsefulUtil.forDebugLog("BluetoothHelper.send -> Send LENGTH");
                    mBluetoothSPP.send(KeyTagList.Protocol.PROTOCOL_SEND_LENGTH.getBytes(), false);
                    break;

                default:

                    mUsefulUtil.forDebugLog("BluetoothHelper.connect.send -> Mismatch Key, Key Value = " + mOrderMsg);
                    break;
            }

        } catch (Exception e) {
            mUsefulUtil.forDebugLog("BluetoothHelper.send() -> Exception! ERROR OCCUR!!");
        }
    }


    /************************************************************
     *
     *      해당 휴대전화(디바이스)가 블루투스 통신이 가능할지를
     *      반환하는 메서드.
     *
     *      통신 가능하면 true 불가능 시. false 리턴.
     *
     ***********************************************************/
    protected boolean getCheckStatue(){
        try {
            boolean result = false;

            if (mBluetoothSPP.isBluetoothAvailable()) {
                mUsefulUtil.forDebugLog("BluetoothSerialConnectionHelper.checkStatue() -> Bluetooth is Available");
                result = true;
            } else {
                mUsefulUtil.forDebugLog("BluetoothSerialConnectionHelper.checkStatue() -> Bluetooth is not Available");
            }


            if (mBluetoothSPP.isBluetoothEnabled()) {
                mUsefulUtil.forDebugLog("BluetoothSerialConnectionHelper.checkStatue() -> Bluetooth is Enabled");
            } else {
                mUsefulUtil.forDebugLog("BluetoothSerialConnectionHelper.checkStatue() -> Bluetooth is not Enabled");
                result = false;
            }

            return result;

        } catch (Exception e){
            mUsefulUtil.forDebugLog("BluetoothSerialConnectionHelper.checkStatue() -> Exception! ERROR OCCUR!!");
            return false;
        }
    }



    /****************************************************
     *
     *      페어링되어 통신이 가능한 디바이스 목록을
     *      JSON 객체로 리턴한다.
     *
     *      통신 대상의 MAC 주소와 디바이스 이름이
     *      담겨져 있다.
     *
     ****************************************************/
    protected JSONObject getDeviceListJSON() {

        try {
            mUsefulUtil.forDebugLog("BluetoothSerialConnectionHelper.getDeviceList() -> _INTI_");
            JSONObject deviceJson = new JSONObject();
            int deviceCount = 0;

            // 페어링 디바이스 목록을 가져오기 위한 객체
            BluetoothAdapter mBlurAdapter = BluetoothAdapter.getDefaultAdapter();
            mUsefulUtil.forDebugLog("BluetoothSerialConnectionHelper.getDeviceList() -> BluetoothAdapter _INTI_");

            Set<BluetoothDevice> pairedDevices = mBlurAdapter.getBondedDevices();
            mUsefulUtil.forDebugLog("BluetoothSerialConnectionHelper.getDeviceList() -> <BluetoothDevice> = getBondedDevices");

            // 페어링된 다바이스가 하나도 없으면!
            if (pairedDevices.isEmpty()) {
                mUsefulUtil.forDebugLog("BluetoothSerialConnectionHelper.getDeviceList() -> no paired device!!");
                return null;
            }

            for (BluetoothDevice devices : pairedDevices) {
                // JSON 객체에 MAC 주소 담기
                mUsefulUtil.forDebugLog("device address : ", devices.getAddress());
                deviceJson.put(KEY_JSON_ADDRESS + deviceCount, devices.getAddress());

                // JSON 객체에 디바이스 이름 담기
                mUsefulUtil.forDebugLog("device name : ", devices.getName());
                deviceJson.put(KEY_JSON_NAME + deviceCount, devices.getName());
                deviceCount++;
            }

            mUsefulUtil.forDebugLog("BluetoothSerialConnectionHelper.getDeviceList() -> no more Device!!, Device count : ", deviceCount);
            return deviceJson;

        } catch (Exception e){
            mUsefulUtil.forDebugLog("BluetoothSerialConnectionHelper.getDeviceList() -> Exception! Error occur!!");
            return null;
        }
    }






    /*****************************************************
    *
    *      블루투스 통신 권한을 요청하는 메서드 이다.
    *
    *****************************************************/
    protected void requestToBluetoothEnable(){
        try {
            if (mBluetoothSPP.isBluetoothEnabled() != true) {
                //블루투스가 비활성화 되어 있으면.

                mUsefulUtil.forDebugLog("BluetoothSerialConnectionHelper().requestToBluetooth() -> Request to enable Bluetooth");
                Intent enableBluetoothIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                mContext.startActivity(enableBluetoothIntent);
            }
        } catch (Exception e){
            mUsefulUtil.forDebugLog("BluetoothSerialConnectionHelper().requestToBluetooth() -> Exception! error occur.");
        }
    }







    protected BluetoothResultDict getResultBluetoothDict(){ return this.mBluetoothResultDict; }

    protected String getActionMsg() { return this.mOrderMsg; }



    protected void setAction(String msg) { this.mOrderMsg = msg; }

    protected void setBluetoothAddress(String address) { this.mBluetoothResultDict.setConnectionResultAddress(address); }

    protected void setBluetoothName(String name) { this.mBluetoothResultDict.setConnectionResultName(name); }

    protected void setEventLister(BluetoothSerialAsyncConnectionHelper.onReceivedEventLister lister) {  this.mEventLister = lister; }



    protected void startDiscovery() { mBluetoothSPP.startDiscovery(); }

    protected void stopHelper(){ this.mBluetoothSPP.stopService(); }








    // 데이터 수신 이벤트
    protected void startReceivedListener(){
        mBluetoothSPP.setOnDataReceivedListener(new BluetoothSPP.OnDataReceivedListener() {

            @Override
            public void onDataReceived(byte[] data, String message) {                           // 데이터가 수신된다면 분기됨

                try {
                    mUsefulUtil.forDebugLog("BluetoothSerialConnectionHelper.onDataReceived() -> on");
                    StringBuffer buffer = new StringBuffer();

                    // 데이터 정렬
                    for (int i = 0; i < data.length; i++) {
                        buffer.append(data[i]);
                    }



                    mUsefulUtil.forDebugLog("BluetoothSerialConnectionHelper.onDataReceived() -> Data : " + buffer.toString());
                    mUsefulUtil.forDebugLog("BluetoothSerialConnectionHelper.onDataReceived() -> Message : " + message);
                    mBluetoothResultDict.setConnectionResultMessage(message);
                    mBluetoothResultDict.setConnectionResultData(buffer.toString());

                    mUsefulUtil.forDebugLog("BluetoothSerialConnectionHelper.onDataReceived() -> start to onReceivedEvent(BluetoothDict)");
                    mEventLister.onReceivedEvent(mBluetoothResultDict);

                } catch (Exception e){
                    mUsefulUtil.forDebugLog("BluetoothSerialConnectionHelper.onDataReceived() -> Exception Error occur!!");
                }

            }
        });
    }




    protected void startAutoConnectionListener(){                                                 // 사용하지 않을것 같다.
        mBluetoothSPP.setAutoConnectionListener(new BluetoothSPP.AutoConnectionListener() {

            @Override
            public void onNewConnection(String name, String address) {

                try {
                    mUsefulUtil.forDebugLog("BluetoothSerialConnectionHelper.startAutoConnectionListener() -> onNewConnection()");
                    mBluetoothResultDict.setConnectionResultName(name);
                    mBluetoothResultDict.setConnectionResultAddress(address);

                    mUsefulUtil.forDebugLog("BluetoothSerialConnectionHelper.startAutoConnectionListener() -> name : " + name);
                    mUsefulUtil.forDebugLog("BluetoothSerialConnectionHelper.startAutoConnectionListener() -> address : " + address);
                    mUsefulUtil.forDebugLog("BluetoothSerialConnectionHelper.startAutoConnectionListener() -> onNewConnection() Finish");
                } catch (Exception e){
                    mUsefulUtil.forDebugLog("BluetoothSerialConnectionHelper.startAutoConnectionListener() Exception Error occur!!!");
                }
            }

            @Override
            public void onAutoConnectionStarted() {
                mUsefulUtil.forDebugLog("BluetoothSerialConnectionHelper.startAutoConnectionListener() -> onAutoConnectionStarted()");
            }
        });
    }




    /********************************************
     *
     *      블루투스 연결 여부를 알려주는
     *      이벤트 리스너.
     *
     ********************************************/
    protected void startConnectionListener() {
        mBluetoothSPP.setBluetoothConnectionListener(new BluetoothSPP.BluetoothConnectionListener() {

            @Override
            public void onDeviceConnected(String name, String address) {
                try {
                    mUsefulUtil.forDebugLog("BluetoothSerialConnectionHelper.onDeviceConnected() -> Connected()");
                    mUsefulUtil.showToast(INFOR_CONNECTION_SUCCESS);
                    mBluetoothResultDict.setConnectionResultAddress(address);
                    mBluetoothResultDict.setConnectionResultName(name);
                } catch (Exception e){
                    mUsefulUtil.forDebugLog("BluetoothSerialConnectionHelper.onDeviceConnected() -> Exception Error occur!!");
                }
            }

            @Override
            public void onDeviceDisconnected() {
                mUsefulUtil.showToast(INFOR_LOST_CONNECTION);
                mUsefulUtil.forDebugLog("BluetoothSerialConnectionHelper.onDeviceConnected() -> Disconnected()");
            }

            @Override
            public void onDeviceConnectionFailed() {
                mUsefulUtil.showToast(INFOR_CONNECTION_FAIL);
                mUsefulUtil.forDebugLog("BluetoothSerialConnectionHelper.onDeviceConnected() -> ConnectionFailed()");
            }
        });
    }



    /****************************************
     *
     *      연결 상태의 변화를 알려주는
     *      이벤트 메서드.
     *
     ****************************************/
    protected void startStateListener(){
        mBluetoothSPP.setBluetoothStateListener(new BluetoothSPP.BluetoothStateListener() {

            @Override
            public void onServiceStateChanged(int state) {                                      // 블루투스 상태가 변화되면 분기.

                if(state == BluetoothState.STATE_CONNECTED){
                    mUsefulUtil.forDebugLog("BluetoothSerialConnectionHelper.onServiceStateChanged() -> State : STATE_CONNECTED");
                }
                else if(state == BluetoothState.STATE_CONNECTING){
                    mUsefulUtil.forDebugLog("BluetoothSerialConnectionHelper.onServiceStateChanged() -> State : STATE_CONNECTING");
                }

                else if(state == BluetoothState.STATE_LISTEN){
                    mUsefulUtil.forDebugLog("BluetoothSerialConnectionHelper.onServiceStateChanged() -> State : STATE_LISTEN");
                }
                else if(state == BluetoothState.STATE_NONE){
                    mUsefulUtil.forDebugLog("BluetoothSerialConnectionHelper.onServiceStateChanged() -> State : STATE_NONE");
                }
            }
        });
    }
}