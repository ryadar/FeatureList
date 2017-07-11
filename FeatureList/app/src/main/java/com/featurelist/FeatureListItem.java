package com.featurelist;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Comparator;

public class FeatureListItem implements Parcelable {

    private String featureTitle;
    private int featureStatus;
    private long timeLeft;
    private int featureCategory;

    public FeatureListItem(String featureTitle, @FeaturesResults.FeatureStatus int featureStatus, long timeLeft, @FeaturesResults.FeatureCategory int featureCategory) {
        this.featureTitle = featureTitle;
        this.featureStatus = featureStatus;
        this.timeLeft = timeLeft;
        this.featureCategory = featureCategory;
    }

    protected FeatureListItem(Parcel in) {
        featureTitle = in.readString();
        featureStatus = in.readInt();
        timeLeft = in.readLong();
        featureCategory = in.readInt();
    }

    public static final Creator<FeatureListItem> CREATOR = new Creator<FeatureListItem>() {
        @Override public FeatureListItem createFromParcel(Parcel in) {
            return new FeatureListItem(in);
        }

        @Override public FeatureListItem[] newArray(int size) {
            return new FeatureListItem[size];
        }
    };

    public String getFeatureTitle() {
        return featureTitle;
    }

    public void setFeatureTitle(String featureTitle) {
        this.featureTitle = featureTitle;
    }

    public @FeaturesResults.FeatureStatus int getFeatureStatus() {
        return featureStatus;
    }

    public void setFeatureStatus(int featureStatus) {
        this.featureStatus = featureStatus;
    }

    public long getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(long timeLeft) {
        this.timeLeft = timeLeft;
    }

    public int getFeatureCategory() {
        return featureCategory;
    }

    public void setFeatureCategory(int featureCategory) {
        this.featureCategory = featureCategory;
    }

    public static Comparator<FeatureListItem> featureListStatusTimeSort = new Comparator<FeatureListItem>() {
        @Override public int compare(FeatureListItem lhs, FeatureListItem rhs) {
            int statusSort = lhs.getFeatureStatus() - rhs.getFeatureStatus();
            if (statusSort == 0) {
                int timeLeftSort = (int) (lhs.getTimeLeft() - rhs.getTimeLeft());
                if (timeLeftSort == 0) {
                    return (int) (lhs.getTimeLeft() - rhs.getTimeLeft());
                }
                else { return timeLeftSort; }
            }
            else { return statusSort; }
        }
    };

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(featureTitle);
        dest.writeInt(featureStatus);
        dest.writeInt(featureCategory);
        dest.writeLong(timeLeft);
    }

}
