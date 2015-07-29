package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.adapters;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Wallets;

import ae.javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Created by lnacosta on 2015.07.17..
 */
public class ActivitiesAdapter extends XmlAdapter<String, Activities> {

    @Override
    public Activities unmarshal(String v) throws Exception {
        return Activities.getValueFromString(v.toString());
    }

    @Override
    public String marshal(Activities v) throws Exception {
        return v.getKey();
    }
}
