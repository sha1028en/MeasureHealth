package com.gadimai.measurehealth.Utill;

import java.io.Serializable;

/************************************************************************************
 *
 *      만든 놈 : 박동수 1701100586(prst2486)
 *      작성 일지 : 2019.08.27 ~
 *
 *      측정하고 입력된 신체 정보의 값을 취급하는 객체.
 *
 *      신장(Height), 심박(HeartBeat), 체중(Weight), 시력(VA) 가 있다.
 *
 *      get***(), set***(String)을 사용하여 해당 대상의 값을
 *      가져오거나 설정할 수 있고
 *
 *      getHealthInfoDict() 를 통해 해당 객체를 가져올 수 있다.
 *
 ************************************************************************************/


public class HealthInfoDict implements Serializable {
    private String HeartBeat = "None";
    private String Wegiht = "None";
    private String rightVa = "None";
    private String leftVa = "None";
    private String High = "None";

    public void setHeartBeat(String input) { this.HeartBeat = input; }

    public void setWegiht(String input) { this.Wegiht = input; }

    public void setRightVA(String input) { this.rightVa = input; }

    public void setLeftVa(String input) { this.leftVa = input; }

    public void setHigh(String input) { this.High = input; }

    public void setHealthInfoDict(String rightVa, String leftVa, String high, String bpm, String weight){
        this.rightVa = rightVa;
        this.leftVa = leftVa;
        this.High = high;
        this.HeartBeat = bpm;
        this.Wegiht = weight;
    }


    public String getHeartBeat() { return this.HeartBeat; }

    public String getWegiht() { return this.Wegiht; }

    public String getLeftVa() { return this.leftVa; }

    public String getRightVa() { return this.rightVa; }

    public String getHigh() { return this.High; }

    public HealthInfoDict getHealthInfoDict() { return this; }
}
