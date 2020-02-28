package com.tnkfactory.pub.guide;

public enum MainListItem {

    HEADER_01("Basic", true),
    BANNER("Banner AD"),
    INTERSTITIAL("Interstitial AD"),
    NATIVE("Native AD"),
    FEED("Feed AD"),
    VIDEO_REWARD("Reward Video AD"),

    HEADER_02("Custom", true),
    FEED_RECYCLERVIEW("Feed AD - RecyclerView")
    ;

    private String value;
    private Boolean isHeader = false;

    MainListItem(String value) {
        this.value = value;
    }

    MainListItem(String value, boolean isHeader) {
        this.value = value;
        this.isHeader = isHeader;
    }

    public String getValue() {
        return value;
    }

    public boolean isHeader() {
        return isHeader;
    }
}
