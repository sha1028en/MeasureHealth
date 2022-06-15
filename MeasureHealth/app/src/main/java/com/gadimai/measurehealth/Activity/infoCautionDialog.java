package com.gadimai.measurehealth.Activity;

import androidx.annotation.NonNull;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gadimai.measurehealth.R;
import com.gadimai.measurehealth.Utill.UsefulUtil;

public class infoCautionDialog extends Dialog {

    private UsefulUtil mUsefulUtil;
    private Context mContext;
    private Button mButton;
    private InforCautionDialogEventListener mEventListener;

    public infoCautionDialog(@NonNull Context context, InforCautionDialogEventListener listener) {         // 생성자
        super(context);
        mUsefulUtil = new UsefulUtil(context);
        mContext = context;
        mEventListener = listener;                                                                          //이벤트 처리를 위한 인터페이스 선언

        mUsefulUtil.forDebugLog("InfoCautionDialog -> _INTI_");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {                                                    // 생성자 닳은 꼴.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_infor_caution);
        mButton = findViewById(R.id.inforCautionAcceptBtn);
        mUsefulUtil.forDebugLog("InfoCautionDialog -> onCreate() Initialized!");

        mButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {                                                                 // 확인 버튼이 눌리면 분기.
                mUsefulUtil.forDebugLog("infoCautionDialog.onClick() -> onClick");

                infoCautionDialog.this.cancel();                                                           // 다이얼로그 소멸
                mUsefulUtil.forDebugLog("InfoCautionDialog.onClick() -> this dialog has cancel()");

                mEventListener.onDialogIsAccept(true);                                               // 다이얼로그 이벤트 분기 실행.
                mUsefulUtil.forDebugLog("infoCautionDialog.onClick() -> onDialogCancel() Event");
            }
        });
    }

    interface InforCautionDialogEventListener {
        public abstract void onDialogIsAccept(boolean isEnd);                                               // 확인 버튼이 눌리면 분기되는 추상 메서드
    }
}
