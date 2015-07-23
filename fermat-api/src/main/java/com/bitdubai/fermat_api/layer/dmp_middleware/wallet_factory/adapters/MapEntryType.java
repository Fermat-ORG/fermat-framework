package com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.adapters;

import java.util.Map;

import ae.javax.xml.bind.annotation.XmlAccessType;
import ae.javax.xml.bind.annotation.XmlAccessorType;
import ae.javax.xml.bind.annotation.XmlElement;

/**
 * Created by lnacosta on 2015.07.22..
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
public class MapEntryType<K, V> {

    private K key;
    private V value;

    public MapEntryType() {
    }

    public MapEntryType(Map.Entry<K, V> e) {
        key = e.getKey();
        value = e.getValue();
    }

    @XmlElement
    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    @XmlElement
    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }
}