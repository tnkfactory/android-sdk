# Tnk Publisher SDK (for Android)

## 1. SDK 설정하기

아래의 코드를 App Module의 build.gradle 파일에 추가해주세요.

```gradle
dependencies {
    implementation 'com.tnkfactory.ad:android-sdk:1.0.2'
}
```

AndroidManifest.xml 파일에 아래의 권한을 추가해주세요.

```xml
<uses-permission android:name="android.permission.INTERNET" />
```
Proguard 를 사용하시는 경우 Proguard 설정파일에 아래의 내용을 반드시 넣어주세요.

```proguard
 -keep class com.tnkfactory.** { *;}
```

### Test Flight

아래의 코드를 사용하어 간단하게 테스트 광고를 띄워보세요.
> SDK import
```java
import com.tnkfactory.ad.*;
```

> 전면삽입 광고 (Interstitial Ad)
```java
InterstitialAdItem adItem = new InterstitialAdItem(this,"TEST_INTERSTITIAL_V", new AdListener() {
        @Override
        public void onLoad(AdItem adItem) {
            adItem.show();
        }
    });

adItem.load(); 
```
> 배너 광고 (Banner Ad)

```xml
<com.tnkfactory.ad.BannerAdView
    android:id="@+id/banner_ad_view"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_alignParentBottom="true"
    android:background="#ffffffff"
    app:placement_id="TEST_BANNER_100"/>
```

```java
BannerAdView bannerAdView = findViewById(R.id.banner_ad_view);
bannerAdView.load();
```
### Publisher ID 등록하기

Test Flight 에서는 별도로 계정등록을 하지않아도 간단히 테스트를 진행할 수 있었습니다. 하지만 실제 광고를 받기 위해서는 우선 Tnk Publish Site 에서 Inventory를 등록하여 발급받은 ID 를 AndroidManifest.xml 파일에 추가하셔야합니다.
아래의 샘플을 참고하시어 실제 ID 를 등록하세요.

```xml
<application
        ...>
        
    <meta-data android:name="tnk_pub_id" android:value="YOUR-INVENTORY-ID-HERE" />
</application>
```

실제 ID 를 등록하면 위 Test Flight 코드에서는 더 이상 광고가 나타나지 않습니다. Tnk Publish Site 에서 광고 유형에 맞추어 Placement 를 등록하시고 등록한 Placement의 이름을 사용하셔야 실제 광고가 표시됩니다.

## 2. 전면 삽입 광고 (Interstitial Ad)

## 3. 배너광고 (Banner Ad)

## 4. 피드형 광고 (Feed Ad)


## 5. 네이티브 광고 (Native Ad)

## 6. 동영상 광고 (Video Ad)

## 7. 미디에이션 (Mediation)
