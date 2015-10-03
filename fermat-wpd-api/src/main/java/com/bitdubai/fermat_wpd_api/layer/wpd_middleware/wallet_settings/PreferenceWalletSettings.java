package com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings;

import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;

/**
 * Created by Matias Furszyfer on 2015.08.27..
 */
public class PreferenceWalletSettings {

    public final String toXml(){
        return XMLParser.parseObject(this);
    }

}
