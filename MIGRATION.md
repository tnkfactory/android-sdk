# Migration Guide (for Android)

구 SDK를 사용 중이던 매체사에서는 해당 마이그레이션 가이드를 참고하면 쉽게 신규 SDK를 적용할 수 있습니다.

## 1. 공통 변경 사항

1) **App ID**의 명칭을 **Inventory ID**로 변경하였습니다.

2) **광고 로직 ID**의 명칭을 **Placement ID**로 변경하였습니다.

3) 신규 SDK에서 모든 광고 리스너는 **AdListener**로 통합되어 사용됩니다.

4) 광고 리스너의 onFailure에서 제공되는 **AdError 클래스**를 사용하여 에러의 원인 파악이 쉬워졌습니다.



## 2. 전면 광고 (Interstitial Ad) 마이그레이션

구 SDK에서 신규 SDK로 전면 광고를 마이그레이션 하기 위해 사용 방법을 비교해보면 아래와 같습니다.

#### 구 SDK 사용 방법

```java
TnkSession.prepareInterstitialAd(this, "Logic ID", new TnkAdListener() {
    @Override
    public void onClose(int type) {
        // 종료 버튼 선택 시 앱을 종료합니다.
        if (type == TnkAdListener.CLOSE_EXIT) {
            finish();
        }
    }

    @Override
    public void onFailure(int errCode) {
        Log.e("Test", "Error : " + errCode);
    }

    @Override
    public void onLoad() {

    }

    @Override
    public void onShow() {

    }

});

TnkSession.showInterstitialAd(this);
```

#### 신규 SDK 사용 방법

```java
final InterstitialAdItem interstitialAdItem = new InterstitialAdItem(this, "Placement ID");
interstitialAdItem.setListener(new AdListener() {
    @Override
    public void onClose(AdItem adItem, int type) {
        // 종료 버튼 선택 시 앱을 종료합니다.
        if (type == AdListener.CLOSE_EXIT) {
            finish();
        }
    }

    @Override
    public void onError(AdItem adItem, AdError error) {
        Log.e("Test", "Error : " + error.getMessage());
    }

    @Override
    public void onLoad(AdItem adItem) {
      	// 광고가 로드 완료된 후 show를 호출해주어야 합니다.
        interstitialAdItem.show();
    }

    @Override
    public void onShow(AdItem adItem) {

    }

    @Override
    public void onClick(AdItem adItem) {

    }
});

interstitialAdItem.load();
```

#### 차이점

1) **InterstitialAdItem** 클래스가 추가되어 광고를 로드하고 노출할 수 있게 변경되었습니다.

2) 광고 리스너에 광고 클릭을 감지할 수 있는 **onClick** 추가되었습니다.

3) **show** 호출 시점을 광고 로드 완료 후로 바꾸었습니다.



## 3. 배너광고 (Banner Ad) 마이그레이션

배너 광고를 사용하는 방법은 Xml 방식과 뷰 동적 생성 방식 두 가지가 있습니다.

구 SDK에서 신규 SDK로 전면 광고를 마이그레이션 하기 위해 사용 방법을 비교해보면 아래와 같습니다.

### XML 방식

첫 번째로 XML 방식을 비교해 봅시다.

#### 구 SDK 사용 방법

##### XML 뷰 삽입 방식

```xml
<com.tnkfactory.ad.BannerAdView
    android:id="@+id/banner_ad_view"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"/>
```

##### 배너 광고 로드

```java
BannerAdView bannerAdView = findViewById(R.id.banner_ad_view);
bannerAdView.setBannerAdListener(new BannerAdListener() {
    @Override
    public void onFailure(int errCode) {
        Log.d("Test", "errCode : " + errCode);
    }

    @Override
    public void onShow() {
    }

    @Override
    public void onClick() {
    }
});

bannerAdView.loadAd("Display Logic");
```

생명주기 관리

```

```



#### 신규 SDK 사용 방법

##### XML 뷰 삽입 방식

```xml
<com.tnkfactory.ad.BannerAdView
		android:id="@+id/banner_ad_view"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    app:placement_id="Placement_ID" />
```

##### 배너 광고 로드

```java
BannerAdView bannerAdView = findViewById(R.id.banner_ad_view);
bannerAdView.setListener(new AdListener() {
    @Override
    public void onError(AdItem adItem, AdError error) {
        Log.d("Test", "errCode : " + error.getMessage());
    }

    @Override
    public void onLoad(AdItem adItem) {
    }

    @Override
    public void onShow(AdItem adItem) {
    }

    @Override
    public void onClick(AdItem adItem) {
    }
});

bannerAdView.load();
```

#### 차이점

1) XML에 BannerAdView 옵션으로 **placement_id**가 추가되어 광고 로드 시점이 아닌 뷰 삽입 시점에 입력하도록 변경되었습니다.

2) 광고 리스너에 광고 로드 완료를 감지할 수 있는 **onLoad** 추가되었습니다.