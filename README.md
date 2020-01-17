# android-sdk
Tnk Publisher SDK

## Sample code

```java
InterstitialAdItem adItem = new InterstitialAdItem(this,"TEST_INTERSTITIAL_V", new AdListener() {
        @Override
        public void onLoad(AdItem adItem) {
            adItem.show();
        }
    });

adItem.load();
        
```
