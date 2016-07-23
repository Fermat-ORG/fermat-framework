package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.notifications;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.FermatDrawable;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.FermatView;

import java.io.Serializable;

/**
 * Created by Matias Furszyfer on 2016.06.20..
 */
public class BasicNotification implements FermatNotification, Serializable {

    private int id;
    private int priority;
    private String statusBarTitle;
    private String title;
    private String body;
    private boolean autoCancel;
    private long[] vibratePattern;
    private int lightColor;
    private int lightOnMs;
    private int lightOffMs;
    private FermatDrawable icon;


    /**
     * Remote custom view for the notification
     */
    private FermatView remoteCustomView;

    public BasicNotification() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getStatusBarTitle() {
        return statusBarTitle;
    }

    public void setStatusBarTitle(String statusBarTitle) {
        this.statusBarTitle = statusBarTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public boolean isAutoCancel() {
        return autoCancel;
    }

    public void setAutoCancel(boolean autoCancel) {
        this.autoCancel = autoCancel;
    }

    public long[] getVibratePattern() {
        return vibratePattern;
    }

    public void setVibratePattern(long[] vibratePattern) {
        this.vibratePattern = vibratePattern;
    }

    public int getLightColor() {
        return lightColor;
    }

    public void setLightColor(int lightColor) {
        this.lightColor = lightColor;
    }

    public int getLightOnMs() {
        return lightOnMs;
    }

    public void setLightOnMs(int lightOnMs) {
        this.lightOnMs = lightOnMs;
    }

    public int getLightOffMs() {
        return lightOffMs;
    }

    public void setLightOffMs(int lightOffMs) {
        this.lightOffMs = lightOffMs;
    }

    public FermatDrawable getIcon() {
        return icon;
    }

    public void setIcon(FermatDrawable icon) {
        this.icon = icon;
    }

    public FermatView getRemoteCustomView() {
        return remoteCustomView;
    }

    public void setRemoteCustomView(FermatView remoteCustomView) {
        this.remoteCustomView = remoteCustomView;
    }
}
