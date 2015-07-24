package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.adapters;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Fragments;

import ae.javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Created by lnacosta on 2015.07.17..
 */
public class FragmentsAdapter extends XmlAdapter<String, Fragments> {

    @Override
    public Fragments unmarshal(String v) throws Exception {
        return Fragments.getValueFromString(v.toString());
    }

    @Override
    public String marshal(Fragments v) throws Exception {
        return v.getKey();
    }
}
