# MeasureHealth / 신장심박측정기 및 신체검사 측정 앱
2019 Q4 College Final Team Project. Measure ur health and Predict Military svc Grade.<br>
한국폴리텍1대학 서울정수캠퍼스. 2019년 최종 졸업과제 팀 프로젝트( 박동수, 정진영, 이일성, 민병우, 김태원).<br><br>
![2-1 grade](https://user-images.githubusercontent.com/100817401/173728511-6ba388a9-3dc8-4df5-b139-528a6ca66596.jpg)
![2-2 grade](https://user-images.githubusercontent.com/100817401/173728516-9291ee76-e912-4efc-93e7-dae9a93fd578.jpg)

<hr>

20대 남자면 모두 받아야 하는 군 신체검사는 정해진 기간에만 받을 수 있으며, 특수한 병이나 증상이 있으면, <br>
최종결과가 나오는 기간이 길어져 등급판정이 늦게 나올 수 있어, 검사 과정이 불친절하다. <br>
이런 신체검사 등급을 간단하고 빠르게 측정하여, 신체등급을 미리 예상 할 수 있는 신체검사 측정기와 연동하여 <br>
예상등급을 예측 할 수 있는 앱을 개발 하는 것 이 목적이다. <br><br>

또한, 이 어플리케이션은 아두이노Uno와 블루투스 무선 직렬통신을 통하여 값을 받아온다.
![CmDataReceived](https://user-images.githubusercontent.com/100817401/173727863-4e6ef560-ae25-4dca-8501-70a7d8683af9.jpg)

<hr>

### 대략적인 구상! [ 프로젝트 기획 ]

![프로젝트 블록도](https://user-images.githubusercontent.com/100817401/173725242-fe50084c-b2be-4c3f-b06b-254b1a04ecea.png)
![강의계획서그림](https://user-images.githubusercontent.com/100817401/173725251-6bf0bd91-cd4f-477e-981e-65a3efb23fe7.png)



## 하드웨어 기능
  - setup()
    - 맴버 변수 init
    - 보드 전원 인가, ReSet버튼 눌렀을 경우 분기.

  - loop()
    - HC-06모듈을 통해 초음파 / 혈압 센서의 값을 보낸다( 브로드 캐스트 ) 
    - ![센서측정](https://user-images.githubusercontent.com/100817401/173728083-c2911349-d57b-4a2a-9c3f-1b10f946d4b2.png)



## 안드로이드 앱 : 순서도 / 기능 개요
![image](https://user-images.githubusercontent.com/100817401/173725940-16ed6eb3-c232-47dc-bd1a-9d6362386207.png)

<hr>

  - SplashActivity
    - 앱 실행시 처음으로 보여지는 화면
    - 앱 제목과 이미지를 2초 보여주고 MainMenuActivity로 분기.
    - ![image](https://user-images.githubusercontent.com/100817401/173727429-b13ba3a2-9c49-4ce4-a82b-31cfd378f1db.png)


  - MainMenuActivity
    - 등급 측정 / 관련 정보 / 어플리케이션 종료 를 선택할 수 있는 화면.
    - ![image](https://user-images.githubusercontent.com/100817401/173727468-ac7f0ae5-8154-488d-9216-3e34c5f8038d.png)


  - InforCautionDialog
    - "등급 측정"을 선택 시 보여지는 다이얼로그.
    - 안내문구를 표시
    - 확인 버튼 클릭시 RankMeasureActivity로 분기.
    - ![image](https://user-images.githubusercontent.com/100817401/173727485-ad8e1a03-d5d1-464f-9df1-c9b29e787117.png)

  
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


  <hr>
  
  - 관련 정보
    - InforUsefullActivity
      - 관련 정보를 누르면 보여주는 액티비티 이다.
  
  <hr>
  
  - 블루투스 모듈
    - BluetoothSerialSyncConnectionHelper
      - 블루투스 직렬통신을 위한 클래스.
      - 깃허브의  BluetoothSPP 외부 라이브러리를 활용하여 통신 구현

    - BluttoothSerialAsyncConnectionHelper
      - 비동기 통신을 처리
      - BluetoothSerialSyncConnectionHelper의 자식으로, 상위 클래스의 작업을 다른 스레드로 처리.
    
    - BluetoothResultDict
      - 블루투스 통신의 결과값을 담을 데이터 클래스.
      - MAC주소, 디바이스 이름, 통신 메세지, 통신 데이터를 담는다.

  <hr>
