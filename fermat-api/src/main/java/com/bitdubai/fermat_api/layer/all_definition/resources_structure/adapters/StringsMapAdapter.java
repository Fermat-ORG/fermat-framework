package com.bitdubai.fermat_api.layer.all_definition.resources_structure.adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ae.javax.xml.bind.annotation.XmlElement;
import ae.javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.all_definition.resources_structure.adapters.StringsMapAdapter</code>
 * overrides marshal and unmarshal methods to convert the strings list.
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 24/07/15.
 * @version 1.0
 * @since Java JDK 1.7
 */
public class StringsMapAdapter extends XmlAdapter<StringsMapAdapter.AdaptedMap, Map<String, String>> {

    public static class AdaptedMap {

        public List<Entry> string = new ArrayList<>();

    }

    public static class Entry {
        @XmlElement
        public String name;
        @XmlElement
        public String value;

        Entry() {
        }

        Entry(String name, String value) {
            this.name = name;
            this.value = value;
        }
    }

    public AdaptedMap marshal(Map<String, String> arg0) throws Exception {
        AdaptedMap adaptedMap = new AdaptedMap();
        for (Map.Entry<String, String> entry : arg0.entrySet())
            adaptedMap.string.add(new Entry(entry.getKey(), entry.getValue()));
        return adaptedMap;
    }

    public Map<String, String> unmarshal(AdaptedMap arg0) throws Exception {
        Map<String, String> r = new HashMap<>();
        for (Entry mapelement : arg0.string)
            r.put(mapelement.name, mapelement.value);
        return r;
    }
}