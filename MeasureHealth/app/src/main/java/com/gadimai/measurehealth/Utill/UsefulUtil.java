package com.gadimai.measurehealth.Utill;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

/*********************************************************************************************************
*
*
*      만든놈 : 1701100586 박동수(prst2486)
*      2019/03/28 작성
*      2019/05/02 수정
*
*      자주 쓰는 기능(Log.d(...), Toast.makeText(...).show())를 사용할때 파라미터를 일일이 치려니까 귀찮다...
*      그래서 자주 쓰는 기능 편하게 쓰려고 만든 킄래스.
*
*      처음 클래스 초기화 할때 Activity 클래스 자기 자신(this)을 인수로 넣어줘.
*      토스트 메세지를 띄울때 Context 정보가 필요해!
*
*
**********************************************************************************************************/


public class UsefulUtil {


    private Context mContext;                       // 토스트 메세지를 위해 어쩔수 없었다...

    public UsefulUtil(@NonNull Context context) {
        this.mContext = context;                    // 해당 객체 선언 시 액티비티 클래스 자기 자신(this)를 인수로 넣어주자.
    }

    /***************************
    *
    *      로그용 메서드
    *
    ****************************/


    public void forDebugLog(String message) {        // 디버깅을 위해 로그를 띄울때 쓰는 메서드. message 에 내용을 적자. 태그는 WAKALIWOOD
        Log.d(KeyTagList.TagList.TAG_LOG, message);
    }

    public void forDebugLog(String message0, String message1) {        // 디버깅을 위해 로그를 띄울때 쓰는 메서드. message 에 내용을 적자. 태그는 WAKALIWOOD
        Log.d(KeyTagList.TagList.TAG_LOG, message0 + message1);
    }

    public void forDebugLog(String message, int num) {                 // 디버깅을 위해 로그를 띄울때 쓰는 메서드. message 에 내용을 적자. 태그는 WAKALIWOOD
        Log.d(KeyTagList.TagList.TAG_LOG, message + num);
    }

    public void forDebugLog(String message, float num) {               // 디버깅을 위해 로그를 띄울때 쓰는 메서드. message 에 내용을 적자. 태그는 WAKALIWOOD
        Log.d(KeyTagList.TagList.TAG_LOG, message + num);
    }

    public void forDebugLog(String message, double num) {              // 디버깅을 위해 로그를 띄울때 쓰는 메서드. message 에 내용을 적자. 태그는 WAKALIWOOD
        Log.d(KeyTagList.TagList.TAG_LOG, message + num);
    }

    public void forDebugLog(String message, char[] ch) {               // 디버깅을 위해 로그를 띄울때 쓰는 메서드. message 에 내용을 적자. 태그는 WAKALIWOOD
        Log.d(KeyTagList.TagList.TAG_LOG, message + ch);
    }


    /***********************************
    *
    *      토스트 출력용 메서드
    *
    ***********************************/


    public void showToast(String message) {                            //토스트 메세지를 띄을떼 쓰는 메서드 message 에 내용을 적자!
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    public void showToast(String message0, String message1) {          //토스트 메세지를 띄을떼 쓰는 메서드 message 에 내용을 적자!
        Toast.makeText(mContext, message0 + message1, Toast.LENGTH_SHORT).show();
    }

    public void showToast(String message, int num) {                   //토스트 메세지를 띄을떼 쓰는 메서드 message 에 내용을 적자!
        Toast.makeText(mContext, message + num, Toast.LENGTH_SHORT).show();
    }

    public void showToast(String message, float num) {                 //토스트 메세지를 띄을떼 쓰는 메서드 message 에 내용을 적자!
        Toast.makeText(mContext, message + num, Toast.LENGTH_SHORT).show();
    }

    public void showToast(String message, double num) {                //토스트 메세지를 띄을떼 쓰는 메서드 message 에 내용을 적자!
        Toast.makeText(mContext, message + num, Toast.LENGTH_SHORT).show();
    }

    public void showToast(String message, char[] ch) {                 //토스트 메세지를 띄을떼 쓰는 메서드 message 에 내용을 적자!
        Toast.makeText(mContext, message + ch, Toast.LENGTH_SHORT).show();
    }

    public void showToast(String message, boolean bool) {              //토스트 메세지를 띄을떼 쓰는 메서드 message 에 내용을 적자!
        Toast.makeText(mContext, message + bool, Toast.LENGTH_SHORT).show();
    }
}

