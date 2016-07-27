package com.bitdubai.fermat_android_api.engine;

import com.bitdubai.fermat_api.layer.all_definition.runtime.FermatApp;

import java.io.Serializable;

/**
 * Created by mati on 2016.03.11..
 */
public interface FermatRecentApp extends Serializable {

    String getPublicKey();

    FermatApp getFermatApp();

    int getTaskStackPosition();


}
