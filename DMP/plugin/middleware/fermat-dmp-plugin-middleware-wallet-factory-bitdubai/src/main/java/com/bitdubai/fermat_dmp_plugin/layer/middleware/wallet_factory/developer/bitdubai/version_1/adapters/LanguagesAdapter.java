package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.adapters;

import com.bitdubai.fermat_api.layer.all_definition.enums.Languages;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.ResourceType;

import ae.javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Created by lnacosta on 2015.07.17..
 */
public class LanguagesAdapter extends XmlAdapter<String, Languages> {

    @Override
    public Languages unmarshal(String v) throws Exception {
        return Languages.fromValue(v);
    }

    @Override
    public String marshal(Languages v) throws Exception {
        return v.value();
    }
}
