package com.gadimai.measurehealth.Utill.Archive;

import android.content.Context;

import com.gadimai.measurehealth.Utill.UsefulUtil;


public class ShowInfoArchive {
    private UsefulUtil mUsefulUtil;
    private HospitalSeoul mHospitalSeoul;


    public ShowInfoArchive(Context context){
        mUsefulUtil = new UsefulUtil(context);
        mHospitalSeoul = new HospitalSeoul();

        mUsefulUtil.forDebugLog("ShowInfoArchive() -> _INTI_");
    }

    public int getHospitalSeoulNameLength() {

        return mHospitalSeoul.HospitalNameSeoul.length;
    }

    public int getHospitalSeoulPHlength() {
        return mHospitalSeoul.HospitalPHseoul.length;
    }




    public String getHospitalSeoulNameByIndex(int index){
        try {

            return mHospitalSeoul.HospitalNameSeoul[index];

        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
            mUsefulUtil.forDebugLog("ShowInfoArchive.getHospitalSeoulByPos() -> ArrayOutOfIndex Exception ERROR Occur!!");

            return null;
        }
    }

    public String getHospitalSeoulPhByIndex(int index){
        try{

            return mHospitalSeoul.HospitalPHseoul[index];

        } catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
            mUsefulUtil.forDebugLog("ShowInfoArchive.getHospitalSeoulPhByIndex() -> ArrayOutOfIndex Exception ERROR Occur!!");

            return null;
        }

    }
}

