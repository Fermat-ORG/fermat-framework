package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.adapters;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.Activity;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.Activities;

import java.util.HashMap;
import java.util.Map;

import ae.javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Created by lnacosta on 2015.07.22..
 */
public class ActivitiesMapAdapter extends XmlAdapter<Activity[], Map<Activities, Activity>> {

    public Activity[] marshal(Map<Activities, Activity> arg0) throws Exception {
        Activity[] mapElements = new Activity[arg0.size()];
        int i = 0;
        for (Map.Entry<Activities, Activity> entry : arg0.entrySet()) {
            System.out.println("viene: "+i+" "+entry.getKey());
            System.out.println("viene: "+i+" "+entry.getValue().getType());
            mapElements[i] = entry.getValue();
            i++;
        }
        for (int x = 0; x < mapElements.length ; x++) {
            System.out.println("sale: "+mapElements[x].getType());
        }
        return mapElements;
    }

    public Map<Activities, Activity> unmarshal(Activity[] arg0) throws Exception {
        Map<Activities, Activity> r = new HashMap<>();
        for (Activity mapelement : arg0)
            r.put(mapelement.getType(), mapelement);
        return r;
    }
}
