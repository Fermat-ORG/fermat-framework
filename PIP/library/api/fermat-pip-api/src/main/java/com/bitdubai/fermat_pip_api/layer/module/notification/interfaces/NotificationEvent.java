package com.bitdubai.fermat_pip_api.layer.module.notification.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;

/**
 * Created by Matias Furszyfer on 2015.08.18..
 */
public interface NotificationEvent {

    String getAlertTitle();

    String getTextTitle();

    String getTextBody();

    Activities getScreen();

    String getWalletPublicKey();

    String getNotificationType();

}
