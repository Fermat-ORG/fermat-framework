package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.common;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.FactoryProjectState;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.ResourceType;

import ae.javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Created by lnacosta on 2015.07.17..
 */
public class FactoryProjectStateAdapter extends XmlAdapter<String, FactoryProjectState> {

    @Override
    public FactoryProjectState unmarshal(String v) throws Exception {
        return FactoryProjectState.fromValue(v.toString());
    }

    @Override
    public String marshal(FactoryProjectState v) throws Exception {
        return v.value();
    }
}
