package com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.structures;

//This is a representation of the table in the Database
public class Service {
    
    public String id = null;
    public String name = null;
    public String type = null;
    public String subType = null;
    
    public SystemData() {}
    
    public SystemData(id, name, type, subType) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.subType = subType;
    }
}