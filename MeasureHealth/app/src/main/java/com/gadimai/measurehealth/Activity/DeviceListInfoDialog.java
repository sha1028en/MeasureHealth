package com.gadimai.measurehealth.Activity;

import androidx.annotation.NonNull;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gadimai.measurehealth.Activity.CustomListview.CustomAdapter;
import com.gadimai.measurehealth.Bluetooth.BluetoothSerialAsyncConnectionHelper;
import com.gadimai.measurehealth.R;
import com.gadimai.measurehealth.Utill.KeyTagList;
import com.gadimai.measurehealth.Utill.UsefulUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.gadimai.measurehealth.Utill.KeyTagList.KeyList.KEY_JSON_ADDRESS;
import static com.gadimai.measurehealth.Utill.KeyTagList.KeyList.KEY_JSON_NAME;

public class DeviceListInfoDialog extends Dialog {
    private BluetoothSerialAsyncConnectionHelper mHelper;
    private UsefulUtil mUsefulUtil;
    private Context mContext;
    private ListView mListView;
    private JSONObject mJsonObject;
    private static List<String> item = new ArrayList<String>() {};
    public onDeviceSelectEventLister mDeviceSelectEventLister;

    public interface onDeviceSelectEventLister{
        public abstract void onDeviceSelected(String deviceName, String deviceAddress);

        public abstract void onErrorOccur(int error);
    }

    public DeviceListInfoDialog(@NonNull Context context, onDeviceSelectEventLister lister) {
        super(context);
        mContext = context;
        mUsefulUtil = new UsefulUtil(context);
        mDeviceSelectEventLister = lister;

        mUsefulUtil.forDebugLog("DeviceListInfoDialog _INTI_ -> before to use BluetoothHelper");
        mHelper = new BluetoothSerialAsyncConnectionHelper(context, "None", null);

        mUsefulUtil.forDebugLog("DeviceListInfoDialog _INTI_ -> finish!!");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_device_list_info);
        mListView = findViewById(R.id.deviceListInfoListview);


        mJsonObject = mHelper.getDeviceListJSON();
        mUsefulUtil.forDebugLog("DeviceListInfoDialog .onCreate() -> get JSON Obj from helper");

        if(mJsonObject != null) {

            mUsefulUtil.forDebugLog("DeviceListInfoDialog.onCreate() -> start to setAdapter()...");
            setListview();

        } else {
            mUsefulUtil.forDebugLog("DeviceListInfoDialog.onCreate() -> not enough to parsing JSON item!!");
            mDeviceSelectEventLister.onErrorOccur(1);
            DeviceListInfoDialog.super.cancel();
        }
        mUsefulUtil.forDebugLog("DeviceListInfoDialog.onCreate() finish!");
    }


    @Override
    public void dismiss(){
        super.dismiss();
        mHelper.stopHelper();
        mUsefulUtil.forDebugLog("DeviceListInfoDialog.dismiss()");
    }


    @Override
    public void cancel(){
        mUsefulUtil.forDebugLog("DeviceListInfoDialog.cancel() -> try to cancel dialog");
        mUsefulUtil.showToast(KeyTagList.Information.INFOR_CAN_NOT_EXIT);
    }



    private void setListview(){
        //리스트 뷰 초기화
        item.clear();
        CustomAdapter adapter = new CustomAdapter();
        adapter.addItem(item);
        mListView.setAdapter(adapter);


        //리스트 뷰 데이터 파싱
        item = getParsingToJSON(item);
        adapter = new CustomAdapter();
        adapter.addItem(item);
        mListView.setAdapter(adapter);

        /******************************************
         *
         *      리스트 뷰 아이템 클릭 이벤트 처리
         *
         ******************************************/
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    mUsefulUtil.forDebugLog("DeviceListInfoDialog .onCreate().onItemClick() -> Device selected name : " + mJsonObject.getString(KEY_JSON_NAME + position));
                    mUsefulUtil.forDebugLog("DeviceListInfoDialog .onCreate().onItemClick() -> Device selected address " + mJsonObject.getString(KEY_JSON_ADDRESS + position));

                    mDeviceSelectEventLister.onDeviceSelected(mJsonObject.getString(KEY_JSON_NAME + position), mJsonObject.getString(KEY_JSON_ADDRESS + position));

                    DeviceListInfoDialog.super.cancel();
                }
                catch (JSONException e){

                    mUsefulUtil.forDebugLog("DeviceListInfoDialog .onCreate().onItemClick() ->  JSON Exception ERROR occur!!!");
                    e.printStackTrace();
                } catch (Exception e){

                    mUsefulUtil.forDebugLog("DeviceListInfoDialog .onCreate().onItemClick() -> Exception ERROR occur!!!");
                    e.printStackTrace();
                }
            }
        });
    }



    /*****************************************************
     *
     *      JSON 형식으로 전달받은 디바이스 리스트를
     *      MAC주소와 디바이스 이름을 담아서
     *      List<String>으로 반환.
     *
     *****************************************************/
    private List<String> getParsingToJSON(List<String> item){
        mUsefulUtil.forDebugLog("DeviceListInfoDialog.getParsingToJSON() -> before parsing to JSON");
        try {
            if(mJsonObject.length() / 2 < 0)
                return null;

            for (int i = 0; i < (mJsonObject.length() / 2); i++) {
                item.add(mJsonObject.getString(KEY_JSON_NAME + i));
                item.add(mJsonObject.getString(KEY_JSON_ADDRESS + i));
                mUsefulUtil.forDebugLog("DeviceListInfoDialog.getParsingToJSON() -> Device item name : " + mJsonObject.getString(KEY_JSON_NAME + i));
                mUsefulUtil.forDebugLog("DeviceListInfoDialog.getParsingToJSON() -> Device item address : " + mJsonObject.getString(KEY_JSON_ADDRESS + i));
            }
        } catch (JSONException e){

            mUsefulUtil.forDebugLog("DeviceListInfoDialog.getParsingToJSON() -> JSON Exception occur!!");
            mDeviceSelectEventLister.onErrorOccur(0);
            return null;
        } catch (Exception e){

            mUsefulUtil.forDebugLog("DeviceListInfoDialog.getParsingToJSON() -> Exception ERROR occur!!!");
            return null;
        }

        mUsefulUtil.forDebugLog("DeviceListInfoDialog.getParsingToJSON() -> finish!!");
        return item;
    }
}
