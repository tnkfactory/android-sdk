# Tnk Publisher SDK (for Android)

## 1. SDK 설정하기

아래의 코드를 App Module의 build.gradle 파일에 추가해주세요.

```gradle
dependencies {
    implementation 'com.tnkfactory.ad:android-sdk:1.0.0'
}
```

AndroidManifest.xml 파일에 아래의 권한을 추가해주세요.

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

### Test Flight

아래의 코드를 사용하어 간단하게 테스트 광고를 띄워보세요.

```java
InterstitialAdItem adItem = new InterstitialAdItem(this,"TEST_INTERSTITIAL_V", new AdListener() {
        @Override
        public void onLoad(AdItem adItem) {
            adItem.show();
        }
    });

adItem.load();
        
```

### Publisher ID 등록하기

Test Flight 에서는 별도로 계정등록을 하지않아도 간단히 테스트를 진행할 수 있었습니다. 하지만 실제 광고를 받기 위해서는 우선 Tnk Publish Site 에 계정을 생성하시고 Inventory를 등록하여 발급받은 ID 를 AndroidManifest.xml 파일에 추가하셔야합니다.
아래의 샘플을 참고하시어 실제 ID 를 등록하세요.

```xml
<application
        ...>
        
        <meta-data android:name="tnk_pub_id" android:value="YOUR-INVENTORY-ID-HERE" />
    </application>
```

ID  
