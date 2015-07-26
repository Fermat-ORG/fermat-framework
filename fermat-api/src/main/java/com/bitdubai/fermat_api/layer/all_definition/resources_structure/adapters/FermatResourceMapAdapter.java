package com.bitdubai.fermat_api.layer.all_definition.resources_structure.adapters;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.interfaces.FermatResource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import ae.javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Created by lnacosta on 2015.07.22..
 */
public class FermatResourceMapAdapter extends XmlAdapter<FermatResourceMapAdapter.AdaptedMap, Map<UUID, Resource> > {

    public static class AdaptedMap {

        public List<Resource> resource = new ArrayList<>();

    }
    public AdaptedMap marshal(Map<UUID, Resource> arg0) throws Exception {
        AdaptedMap adaptedMap = new AdaptedMap();
        for (Map.Entry<UUID, Resource> entry : arg0.entrySet())
            adaptedMap.resource.add(entry.getValue());
        return adaptedMap;
    }

    public Map<UUID, Resource> unmarshal(AdaptedMap arg0) throws Exception {
        Map<UUID, Resource> r = new HashMap<>();
        for (Resource mapelement : arg0.resource)
            r.put(mapelement.getId(), mapelement);
        return r;
    }
}