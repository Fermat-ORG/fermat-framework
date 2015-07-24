package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.adapters;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ae.javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Created by lnacosta on 2015.07.22..
 */
public class ActivitiesMapAdapter extends XmlAdapter<ActivitiesMapAdapter.AdaptedMap, Map<Activities, Activity>> {

    public static class AdaptedMap {

        public List<Activity> activity = new ArrayList<>();

    }
    public AdaptedMap marshal(Map<Activities, Activity> arg0) throws Exception {
        AdaptedMap adaptedMap = new AdaptedMap();
        for (Map.Entry<Activities, Activity> entry : arg0.entrySet())
            adaptedMap.activity.add(entry.getValue());
        return adaptedMap;
    }

    public Map<Activities, Activity> unmarshal(AdaptedMap arg0) throws Exception {
        Map<Activities, Activity> r = new HashMap<>();
        for (Activity mapelement : arg0.activity)
            r.put(mapelement.getType(), mapelement);
        return r;
    }
}