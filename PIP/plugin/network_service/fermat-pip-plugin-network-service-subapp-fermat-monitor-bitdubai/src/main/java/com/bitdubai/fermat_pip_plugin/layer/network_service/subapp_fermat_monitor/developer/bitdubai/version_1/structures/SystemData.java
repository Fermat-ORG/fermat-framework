package com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_fermat_monitor.developer.bitdubai.version_1.structures;

//This is a representation of the table in the Database
public class SystemData {

    public String systemID = null;
    public String nodeType = null;
    public String hardware = null;
    public String os = null;


    public SystemData(String systemID, String nodeType, String hardware, String os) {
        this.systemID = systemID;
        this.nodeType = nodeType;
        this.hardware = hardware;
        this.os = os;
    }

    public String getTransactionId() {
        return systemID;
    }
}