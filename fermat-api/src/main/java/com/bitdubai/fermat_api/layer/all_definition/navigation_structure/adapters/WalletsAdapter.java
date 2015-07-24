package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.adapters;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;
import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.FactoryProjectState;

import ae.javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Created by lnacosta on 2015.07.17..
 */
public class WalletsAdapter extends XmlAdapter<String, Wallets> {

    @Override
    public Wallets unmarshal(String v) throws Exception {
        return Wallets.getByCode(v.toString());
    }

    @Override
    public String marshal(Wallets v) throws Exception {
        return v.getCode();
    }
}
