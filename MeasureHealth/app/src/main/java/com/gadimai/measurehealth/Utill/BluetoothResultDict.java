package com.gadimai.measurehealth.Utill;

import java.io.Serializable;

/**********************************************************************************************************
 *
 *
 *            만든놈 : 1701100586 박동수(prst2486)
 *            2019/03/28 작성
 *            2019/04/04 수정
 *            2019/04/23 메서드 추가.
 *
 *            통신으로 데이터를 얻어왔으면 저장해야지!
 *            그러려고 이 클래스에 블루투스 통신 결과값을 담아서
 *            원래 작업하던 액티비티 클래스로 넘겨주자!
 *
 *            아. "implements Serializable"는 이 클래스를 인텐트에 담기 위해서 직렬화 하는 과정이야
 *            이거 안하면 오류나더라.
 *            그거 빼고는 큰 의미가 없으니까 신경쓰지 말고!
 *
 *            connectionResultData      실질적인 통신 결과 값...혈압, 키 이런거?
 *            connectionResultAddress   통신하려는 디바이스의 MAC 어드레스 주소.
 *            connectionResultName      통신대상 디바이스의 이름
 *            connectionResultMessage   통신 결과의 메세지...성공 실패 여부?
 *
 *            하지만 이런 맴버 변수들은 일반적인 방법으로는 접근이 제한되어 있으니까
 *
 *            setConnectionResult***(String)을 통해 데이터를 집어넣고
 *            getConnectionResult***()을 통해 데이터를 받고
 *            setMatchInsane(해당클래스)를 통해 2개의 동일 객체가 잇으면 내용을 일치시킨다. A = B 같은 느낌.
 *
 *
 *            아. 데이터 집어 넣지도 않고 요청하진 않겠지? 응?
 *            혹시 그럴수도 있을까봐 맴버 변수 기본값을 "None" 으로 초기화 했어.
 *
 *
 **********************************************************************************************************/



public class BluetoothResultDict implements Serializable {
    private String connectionResultData = "None";
    private String connectionResultAddress = "None";
    private String connectionResultName = "None";
    private String connectionResultMessage = "None";

    /*********************************
     *
     *       데이터 담는 메서드
     *
     ********************************/

    public void setConnectionResultData(String inputValue){ this.connectionResultData = inputValue; }           // 실직적인 통신 결과값(혈압, 키...) 담기

    public void setConnectionResultAddress(String inputValue){ this.connectionResultAddress = inputValue; }     // 통신 대상의 MAC 주소 담기

    public void setConnectionResultName(String inputValue) { this.connectionResultName = inputValue; }          // 통신 대상의 기기 이름 담기

    public void setConnectionResultMessage(String inputValue){ this.connectionResultMessage = inputValue; }     // 통신 결과의 메세지 담기



    public void setMatchInsane(BluetoothResultDict input) {                                                      // 동일 객체와 내용 일치 시키기
        this.connectionResultMessage = input.getConnectionResultMessage();                                      // 2019/04/23일 추가
        this.connectionResultData = input.getConnectionResultData();
        this.connectionResultAddress = input.getConnectionResultAddress();
        this.connectionResultName = input.getConnectionResultName();
    }


    /********************************
     *
     *      데이터 내보내는 메서드
     *
    ********************************/

    public String getConnectionResultData(){ return this.connectionResultData; }       //통신 결과값(키, 혈압...) 내보내기

    public String getConnectionResultAddress(){ return this.connectionResultAddress; }   //통신 대상의 MAC 주소 내보내기

    public String getConnectionResultName(){ return this.connectionResultName; }         //통신 대상의 기기 이름 내보내기

    public String getConnectionResultMessage(){ return this.connectionResultMessage; }   //통신 결과의 메세지 내보내기
}

