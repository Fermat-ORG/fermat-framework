package com.bitdubai.fermat_android_api.layer.definition.wallet.interfaces;

import com.bitdubai.fermat_api.AppsStatus;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatRuntime;

import java.io.Serializable;

/**
 * Created by mati on 2016.02.13..
 */
public interface FermatActivityManager extends Serializable {


    FermatRuntime getRuntimeManager();

    //esto no va acá
    void reportError(String userTo) throws Exception;

    void sendMailExternal(String userTo, String bodyText) throws Exception;

    void goHome();

    AppsStatus getAppStatus();

    void selectApp(String appPublicKey) throws Exception;

}
