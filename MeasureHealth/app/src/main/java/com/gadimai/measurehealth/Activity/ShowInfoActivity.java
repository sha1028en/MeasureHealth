package com.gadimai.measurehealth.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.gadimai.measurehealth.R;
import com.gadimai.measurehealth.Utill.UsefulUtil;

import static com.gadimai.measurehealth.Utill.KeyTagList.KeyList.KEY_DEVICE_INFO;
import static com.gadimai.measurehealth.Utill.KeyTagList.KeyList.KEY_DEVICE_INFO_ETC_EXEMPTION;
import static com.gadimai.measurehealth.Utill.KeyTagList.KeyList.KEY_VA;
import static com.gadimai.measurehealth.Utill.KeyTagList.KeyList.KEY_WEIGHT;

public class ShowInfoActivity extends AppCompatActivity {
    private UsefulUtil mUsefulUtil;
    private TextView mTitleTextview;
    private TextView mInfoTextview;
    private String msg = new String();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_info);

        mTitleTextview = findViewById(R.id.showInfoTitleTextView);
        mInfoTextview = findViewById(R.id.showInfoTextView);
        mUsefulUtil = new UsefulUtil(this.getApplicationContext());

        Bundle bundle = this.getIntent().getExtras();
        mUsefulUtil.forDebugLog("showInforShowActivity.onCreate() -> bundle.getIntent().getExtras()");
        msg = bundle.getString(KEY_DEVICE_INFO);

        switch (msg) {

            case KEY_WEIGHT:
                mUsefulUtil.forDebugLog("ShowInforActivity.onCreate() -> Etc exemption select!");
                mTitleTextview.setText(getResources().getString(R.string.introduce_BMI));
                setBMIview();
                break;

            case KEY_VA:
                mUsefulUtil.forDebugLog("ShowInforActivity.onCreate() -> standard select!");
                mTitleTextview.setText(getResources().getString(R.string.introduce_va));
                setVaview();
                break;

            case KEY_DEVICE_INFO_ETC_EXEMPTION:
                mUsefulUtil.forDebugLog("ShowInfoActivity.onCreate() -> ETC_Exemption selected");
                mTitleTextview.setTextSize((float) 28.0);
                mTitleTextview.setText(getResources().getString(R.string.introduce_etc_exemption));
                setEtcView();
            }
        }

        private void setBMIview() {
            mInfoTextview.setText(getResources().getString(R.string.info_measure_bmi));
        }

        private void setVaview(){
            mInfoTextview.setTextSize((float) 20.0);
            mInfoTextview.setText(getResources().getString(R.string.info_measure_va));
        }

        private void setEtcView(){
            mInfoTextview.setTextSize((float )15.0);
            mInfoTextview.setText(getResources().getString(R.string.info_etc_exemption));
        }
}
