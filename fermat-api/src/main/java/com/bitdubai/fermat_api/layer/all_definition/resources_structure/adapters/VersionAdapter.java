package com.bitdubai.fermat_api.layer.all_definition.resources_structure.adapters;

import com.bitdubai.fermat_api.layer.all_definition.enums.Languages;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;

import ae.javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.all_definition.resources_structure.adapters.VersionAdapter</code>
 * overrides marshal and unmarshal methods to convert the versions.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 24/07/15.
 * @version 1.0
 * @since Java JDK 1.7
 */
public class VersionAdapter extends XmlAdapter<String, Version> {

    @Override
    public Version unmarshal(String v) throws Exception {
        return new Version(v);
    }

    @Override
    public String marshal(Version v) throws Exception {
        return v.toString();
    }
}
