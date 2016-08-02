package com.bitdubai.fermat_android_api.engine;

import android.app.Notification;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.RelativeLayout;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;


/**
 * Created by Matias Furszyfer on 2015.09.01
 */

public interface PaintActivityFeatures {

    android.support.v7.widget.Toolbar getToolbar();

    RelativeLayout getToolbarHeader();

    void invalidate();

    void addCollapseAnimation(ElementsWithAnimation elementsWithAnimation);

    void removeCollapseAnimation(ElementsWithAnimation elementsWithAnimation);

    void setTabCustomImageView(int position,View view);

    void cancelNotification(String appPublicKey);

    void pushNotification(String appPublicKey,Notification notification);

    // TODO - This shouldn't be here
    void addDesktopCallBack(DesktopHolderClickCallback desktopHolderClickCallback);

    @Deprecated
    void setActivityBackgroundColor(Drawable drawable);

    void changeOptionMenuVisibility(int id,boolean isVisible,String appPublicKey) throws InvalidParameterException;

}
