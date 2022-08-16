# MeasureHealth / 신장심박측정기 및 신체검사 측정 앱
2019 Q4 College Final Team Project. Measure ur health and Predict Military svc Grade.<br>
한국폴리텍1대학 서울정수캠퍼스. 2019년 최종 졸업과제 팀 프로젝트( 박동수, 정진영, 이일성, 민병우, 김태원).<br><br>

__실제 시연 영상 링크__<br>
https://youtu.be/UBjyydt9fdc
<hr>

### 대략적인 구상! [ 프로젝트 기획 ]

![프로젝트 블록도](https://user-images.githubusercontent.com/100817401/173725242-fe50084c-b2be-4c3f-b06b-254b1a04ecea.png)
![강의계획서그림](https://user-images.githubusercontent.com/100817401/173725251-6bf0bd91-cd4f-477e-981e-65a3efb23fe7.png)

## 하드웨어 기능
  - HC-06모듈을 통해 초음파 / 혈압 센서의 값을 불루투스로 보낸다( 브로드 캐스트 ) 
  
## 안드로이드 앱 : 순서도 / 기능 개요
  - hw가 보내준 값을 수신해서 신검 등급을 계산한다.
<hr>

  - SplashActivity
    - 앱 실행시 처음으로 보여지는 화면
    - 앱 제목과 이미지를 2초 보여주고 MainMenuActivity로 분기.
    - ![photo_2022-07-07_22-40-39](https://user-images.githubusercontent.com/100817401/184803421-104f7c4f-385d-485b-b035-785813509903.jpg)

    

  - MainMenuActivity
    - 등급 측정 / 관련 정보 / 어플리케이션 종료 를 선택할 수 있는 화면.
    - ![photo_2022-08-16_14-22-38](https://user-images.githubusercontent.com/100817401/184803879-c044f7a4-b7cf-42fc-9e15-33633e38b7fd.jpg)



  - InforCautionDialog
    - "등급 측정"을 선택 시 보여지는 다이얼로그.
    - 안내문구를 표시
    - 확인 버튼 클릭시 RankMeasureActivity로 분기.
    - ![photo_2022-08-16_14-19-57](https://user-images.githubusercontent.com/100817401/184803750-185e033b-ed49-4b0f-8795-e0a016d679d7.jpg)



  
  <hr>
  
  - 등급 측정
    - RankMeasureActivity
      - 아두이노와 블루투스 직렬통신으로 센서값을 전달받는다.
      - 이후 DetailRankMeasureActivity로 분기.
      - ![image](https://user-images.githubusercontent.com/100817401/173727502-cc02179e-f671-40fe-abff-884b4cc49571.png)


    - DetailRankMeasureActivity
      - 시력과 체중을 입력받는 액티비티 
      - 이후 RankMeasureResultActivity로 분기.
      - ![image](https://user-images.githubusercontent.com/100817401/173727641-3dfb94e4-15c8-4347-bcd0-cc3b65f8f1c1.png)


    - RankMeasureResultActivity
      - 등급 결과를 보여주는 액티비티
      - 여기서 기준에 따라( 2019년도 기준) 계산하여 결과를 표시한다.
      - 직후 MainMenuActivity로 분기.
      - ![photo_2022-08-16_14-16-12](https://user-images.githubusercontent.com/100817401/184803386-1c1ec532-ce08-4664-b974-5826f963701a.jpg)


<hr>

  - 관련 정보 : InforUsefullActivity
    - 관련 정보를 누르면 보여주는 액티비티 이다.
  
  - 블루투스 모듈 : BluttoothSerialAsyncConnectionHelper
    - 비동기 통신을 처리
    - 깃허브의  BluetoothSPP 외부 라이브러리를 활용하여 통신 구현

