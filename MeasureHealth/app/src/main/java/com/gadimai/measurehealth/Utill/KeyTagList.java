package com.gadimai.measurehealth.Utill;


public class KeyTagList {

    /**************************************************************
     *
     *      만든놈 : 박동수 1701100586(prst2486)
     *      생성 일자 : 2018/03/?? ~ 04/?? 사이 추정
     *
     *      해당 객체는 사용되는 키, 태그, 프로토콜...등에 사용할
     *      여러 키워드를 한데 모아서 관리하고 사용하는 객체이다.
     *
     *      즉 관리를 용이하게 하고 하드코딩을 방지하기 위함...
     *
     **************************************************************/



    public class TagList {
        public static final String TAG_LOG = "GADIMAI";                                 // 로그 태그 값
        public static final String TAG_JSON_NAME = "JSON_NAME";
        public static final String TAG_JSON_ADDRESS = "JSON_ADDRESS";
        public static final String TAG_JSON_DEVICE_COUNT = "JSON_DEVICE_COUNT";
    }

    public static class KeyList {
        public static final String KEY_LENGTH = "LENGTH_KEY";                           // 적외선 센서 길이 키값(인텐트)
        public static final String KEY_HEARTBEAT = "HEARTBEAT_KEY";                     // 혈압 센서 키값(인텐트)
        public static final String KEY_VA = "VA_KEY";
        public static final String KEY_WEIGHT = "WEIGHT_KEY";

        public static final String KEY_HANDLER = "HANDLER_KEY";
        public static final String KEY_BUNDLE = "BUNDLE_KEY";
        public static final String KEY_HEALTH_DICT = "healthInfoDict_key";
        public static final String KEY_JSON_NAME = "JSON_NAME";                         // JSON 키값, 디바이스 이름
        public static final String KEY_JSON_ADDRESS = "JSON_ADDRESS";                   // JSON 키값. 디바이스 MAC 주소
        public static final String KEY_JSON_DEVICE_COUNT = "JSON_DEVICE_COUNT";
        public static final String KEY_DEVICE_TARGET_NAME = "HC-06";

        public static final String KEY_DEVICE_INFO_ETC_EXEMPTION = "device info Etc exemption";
        public static final String KEY_DEVICE_INFO_STANDARD = "device info standard";
        public static final String KEY_DEVICE_INFO_HOSPITAL = "device info hospital";

        public static final String KEY_DEVICE_INFO = "device info key";
    }

    public static class Protocol{
        public static final String PROTOCOL_SEND_LENGTH = "L";             // 아두이노 한테 보내는 초음파 센서값 요청 코드
        public static final String PROTOCOL_SEND_HEARTBEAT = "H";          // 아두이노 한테 보내는 심박수 센서값 요청 코드

        public static final String PROTOCOL_SEND_SOT = "T";                // 아두이노 한테 보내는 톤신 시작 요청 코드
        public static final String PROTOCOL_SEND_EOT = "\n";               // 아두이노 한테 보내는 통신 종료 요청 코드

        public static final String PROTOCOL_BLUETOOTHSPP = "1028";         // 넌 안쓸꺼 같다...영원히?
        public static final int REQUEST_ENABLE_BLUETOOTH = 1028;
    }

    public static class Information{
        public static final String INFOR_CLICKED_EXIT = "한번 더 누르면 종료할께요";
        public static final String INFOR_CLICKED_BEFORE = "한번 더 누리면 뒤로 돌아갈께요";
        public static final String INFOR_CAN_NOT_EXIT = "지금은 종료 하실수 없어요!";
        public static final String INFOR_RANK_MEASURE_HEIGHT = "등급 측정 -신장-";
        public static final String INFOR_RANK_MEASURE_HEARTBEAT = "등급 측정 -심박-";
        public static final String IFNOR_NO_PAIRED_DEVICE = "요류 : 페어링된 디바이스가 없네요!";
        public static final String INFOR_LOST_CONNECTION ="해당 기기와 연결이 끊어졌습니다.";
        public static final String INFOR_CONNECTION_FAIL = "해당 기기와 연결할수 없습니다.";
        public static final String INFOR_CONNECTION_SUCCESS ="해당 기기와 연결 되었습니다.";
        public static final String INFOR_ERROR_OCCUR = "오류 발생!!!";
    }

    public static class Title{
        public static final String TITLE_DEVICE_INFO_STANDARD = "-등급 판정 기준-";
        public static final String TITLE_DEVICE_INFO_ETC_EXEMPTION = "-기타 결격 사유-";
        public static final String TITLE_DEVICE_INFO_HOSPITAL = "-지정 병원 목록-";


    }

    public static class Value{
        public static final String VALUE_INFOR_DIALOG = "InforCautionDialog agreeBtn Pushed!";
    }
}
