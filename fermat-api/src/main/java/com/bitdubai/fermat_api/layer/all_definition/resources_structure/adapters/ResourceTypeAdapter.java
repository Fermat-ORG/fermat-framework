package com.bitdubai.fermat_api.layer.all_definition.resources_structure.adapters;

import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ResourceType;

import ae.javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.all_definition.resources_structure.adapters.ResourceTypeAdapter</code>
 * overrides marshal and unmarshal methods to convert the resource types.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 24/07/15.
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ResourceTypeAdapter extends XmlAdapter<String, ResourceType> {

    @Override
    public ResourceType unmarshal(String v) throws Exception {
        return ResourceType.fromValue(v);
    }

    @Override
    public String marshal(ResourceType v) throws Exception {
        return v.value();
    }
}
