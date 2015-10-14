package com.bitdubai.fermat_pip_api.layer.pip_module.notification.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;

/**
 * Created by Matias Furszyfer on 2015.08.18..
 */
public interface NotificationEvent {

    public String getAlertTitle();

    public String getTextTitle();

    public String getTextBody();

    public Activities getScreen();

    public String getWalletPublicKey();

    public String getNotificationType();

}
