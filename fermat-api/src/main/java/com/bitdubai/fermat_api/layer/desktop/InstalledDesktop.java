package com.bitdubai.fermat_api.layer.desktop;

import com.bitdubai.fermat_api.layer.all_definition.runtime.FermatApp;

/**
 * Created by mati on 2015.11.20..
 */
public class InstalledDesktop implements FermatApp {

    String publicKey;

    @Override
    public String getAppPublicKey() {
        return publicKey;
    }
}
