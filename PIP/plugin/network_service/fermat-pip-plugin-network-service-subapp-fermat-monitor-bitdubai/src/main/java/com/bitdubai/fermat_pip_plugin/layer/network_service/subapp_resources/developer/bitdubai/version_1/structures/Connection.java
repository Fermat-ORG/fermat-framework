package com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.structures;

//This is a representation of the table in the Database
public class Connection {
    
    public String connID = null;
    public String peerID = null;
    public String ipv4 = null;
    public String ipv6 = null;
    public String ns_name = null;
    
    public SystemData() {}
    
    public SystemData(connID, peerID, ipv4, ipv6, ns_name) {
        this.connID = connID;
        this.peerID = peerID;
        this.ipv4 = ipv4;
        this.ipv6 = ipv6;
        this.ns_name = ns_name;
    }
}