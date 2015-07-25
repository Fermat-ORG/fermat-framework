package com.bitdubai.fermat_api.layer.all_definition.resources_structure.adapters;

import com.bitdubai.fermat_api.layer.all_definition.enums.Languages;

import ae.javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.all_definition.resources_structure.adapters.LanguagesAdapter</code>
 * overrides marshal and unmarshal methods to convert the language types.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 24/07/15.
 * @version 1.0
 * @since Java JDK 1.7
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
