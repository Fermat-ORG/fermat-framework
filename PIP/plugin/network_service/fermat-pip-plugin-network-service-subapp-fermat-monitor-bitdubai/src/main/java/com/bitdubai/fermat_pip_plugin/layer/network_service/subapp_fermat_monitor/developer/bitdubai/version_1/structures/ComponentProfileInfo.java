package com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_fermat_monitor.developer.bitdubai.version_1.structures;

/**
 * Created by mati on 2016.03.31..
 */
public class ComponentProfileInfo {

    String id;
    String name;
    String type;


    public ComponentProfileInfo(String id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
