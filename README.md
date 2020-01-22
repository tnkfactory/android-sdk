# Tnk Publisher SDK (for Android)

## SDK 설정하기

아래의 코드를 App Module의 build.gradle 파일에 추가해주세요.

```gradle
dependencies {
    implementation 'com.tnkfactory.ad:android-sdk:1.0.0'
}
```

AndroidManefest.xml 파일에 아래의 권한을 추가해주세요.

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

