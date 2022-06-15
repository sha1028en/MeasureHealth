package com.gadimai.measurehealth.Activity;

import androidx.annotation.NonNull;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gadimai.measurehealth.R;
import com.gadimai.measurehealth.Utill.UsefulUtil;

import static com.gadimai.measurehealth.Utill.KeyTagList.KeyList.KEY_HEARTBEAT;
import static com.gadimai.measurehealth.Utill.KeyTagList.KeyList.KEY_LENGTH;

public class DebugValueDialog extends Dialog {
    private UsefulUtil mUsefulUtil;
    private Button mAceeptBtn;
    private EditText mBPMeditText;
    private EditText mHighEditText;

    public onDebugValueDialogEventListner mDialogEvent;

    public interface onDebugValueDialogEventListner{
        public abstract void onCallBack(@NonNull Bundle sendValue);
    }


    public DebugValueDialog(@NonNull Context context, @NonNull onDebugValueDialogEventListner dialogEvent){
        super(context);
        mUsefulUtil = new UsefulUtil(context);
        mDialogEvent = dialogEvent;
        mUsefulUtil.forDebugLog("DebugValueDialog _INTI_");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_debug_value);
        mUsefulUtil.forDebugLog("DebugValueDialog.onCreate()");

        mBPMeditText = findViewById(R.id.debugValueDialogBPMeditText);
        mHighEditText = findViewById(R.id.debugValueDialogHighEditText);
        mAceeptBtn = findViewById(R.id.debugValueAceeptButton);


        mAceeptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle = new Bundle();
                bundle.putString(KEY_HEARTBEAT, mBPMeditText.getText().toString());
                bundle.putString(KEY_LENGTH, mHighEditText.getText().toString());

                mUsefulUtil.forDebugLog("DebugValueDialog.onCreate.onClick() -> BPM : ", mBPMeditText.getText().toString());
                mUsefulUtil.forDebugLog("DebugValueDialog.onCreate.onClick() -> HIgh : ", mHighEditText.getText().toString());
                mUsefulUtil.forDebugLog("DebugValueDialog.onCreate.onClick() -> send Message.");
                mDialogEvent.onCallBack(bundle);

                DebugValueDialog.this.dismiss();

            }
        });
    }

    public void dismiss(){
        mUsefulUtil.forDebugLog("DebugValueDialog.dismiss()");
        super.dismiss();
    }

}