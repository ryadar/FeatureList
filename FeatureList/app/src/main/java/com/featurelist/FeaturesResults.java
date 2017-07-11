package com.featurelist;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;


public class FeaturesResults {

    @Retention(SOURCE)
    @IntDef({STATUS_PERMANENT, STATUS_DEMO, STATUS_NOT_ACTIVATED})
    public @interface FeatureStatus {}
    public static final int STATUS_PERMANENT = 0;
    public static final int STATUS_DEMO = 1;
    public static final int STATUS_NOT_ACTIVATED = 2;

    @Retention(SOURCE)
    @IntDef({PRIMARY_FEATURE})
    public @interface FeatureCategory {}
    public static final int PRIMARY_FEATURE=1;


}
