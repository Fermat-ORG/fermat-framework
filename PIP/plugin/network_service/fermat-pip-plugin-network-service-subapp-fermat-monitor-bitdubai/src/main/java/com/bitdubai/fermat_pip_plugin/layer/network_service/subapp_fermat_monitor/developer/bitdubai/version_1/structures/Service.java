package com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_fermat_monitor.developer.bitdubai.version_1.structures;

//This is a representation of the table in the Database
public class Service {

    public String id = null;
    public String name = null;
    public String type = null;
    public String subType = null;

    public Service(String id, String name, String type, String subType) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.subType = subType;
    }


    public String getTransactionId() {
        return id;
    }
}