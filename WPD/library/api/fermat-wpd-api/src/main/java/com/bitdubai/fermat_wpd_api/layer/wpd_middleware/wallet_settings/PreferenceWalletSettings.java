package com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_settings;

import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.modules.interfaces.FermatSettings;

/**
 * Created by Matias Furszyfer on 2015.08.27..
 */
public class PreferenceWalletSettings implements FermatSettings {

    public final String toXml() {
        return XMLParser.parseObject(this);
    }

    @Override
    public void setIsPresentationHelpEnabled(boolean b) {

    }
}
