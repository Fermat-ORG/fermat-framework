package com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.structures;

//This is a representation of the table in the Database
public class SystemData {
    
    public String systemID = null;
    public String nodeType = null;
    public String hardware = null;
    public String os = null;
    
    public SystemData() {}
    
    public SystemData(systemID, nodeType, hardware, os) {
        this.systemID = systemID;
        this.nodeType = nodeType;
        this.hardware = hardware;
        this.os = os;
    }
}