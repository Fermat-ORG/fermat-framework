package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.util;

import org.apache.commons.lang.ClassUtils;
import org.bitlet.weupnp.GatewayDevice;
import org.bitlet.weupnp.GatewayDiscover;
import org.bitlet.weupnp.PortMappingEntry;
import org.jboss.logging.Logger;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by rrequena on 11/05/16.
 */
public final class UPNPService {

    /**
     * Represent the LOG
     */
    private static final Logger LOG = Logger.getLogger(ClassUtils.getShortClassName(UPNPService.class));

    /**
     * Represent the TCP_PROTOCOL
     */
    private static final String TCP_PROTOCOL = "TCP";
    
    /**
     * Represent the instance
     */
    private static final UPNPService instance = new UPNPService();

    /**
     * Represent the gatewayDiscover
     */
    private GatewayDiscover gatewayDiscover;

    /**
     * Represent the gateways list
     */
    private Map<InetAddress, GatewayDevice> gateways;

    /**
     * Represent the activeGatewayDevice
     */
    private GatewayDevice activeGatewayDevice;

    /**
     * Represent the localAddress
     */
    private InetAddress localAddress;

    /**
     * Represent the externalIPAddress
     */
    private String externalIPAddress;

    /**
     * Constructor
     */
    private UPNPService(){
        gatewayDiscover = new GatewayDiscover();
    }

    /**
     * Try to Port forwarding in to the active Gateway Device if it
     * is compatible or available
     *
     * @param port number
     * @param description text
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     */
    public static void portForwarding(int port, String description) throws IOException, SAXException, ParserConfigurationException {

        LOG.info("Starting port forwarding ...");
        LOG.info("Looking for Gateway Devices...");
        instance.gateways = instance.gatewayDiscover.discover();

        if (instance.gateways.isEmpty()) {
            LOG.warn("*************************************************************************** ");
            LOG.warn(" No compatible gateways found, your node may not be accessible externally,  ");
            LOG.warn(" you most open the port " + port + " manually ");
            LOG.warn("*************************************************************************** ");
            return;
        }

        LOG.info(instance.gateways.size()+" gateway(s) found");

        int counter = 1;
        for (GatewayDevice gw: instance.gateways.values()) {

            LOG.info("Listing gateway details of device #" + counter);
            LOG.info("\tFriendly name: " + gw.getFriendlyName() );
            LOG.info("\tPresentation URL: " + gw.getPresentationURL());
            LOG.info("\tModel name: " + gw.getModelName() );
            LOG.info("\tModel number: " + gw.getModelNumber() );
            LOG.info("\tLocal interface address: " + gw.getLocalAddress().getHostAddress()+"\n");
            counter++;
        }

        instance.activeGatewayDevice = instance.gatewayDiscover.getValidGateway();

        if (instance.activeGatewayDevice != null) {
            LOG.info("Using the first active gateway: " + instance.activeGatewayDevice.getFriendlyName());
        } else {
            LOG.warn("No active gateway device found");
            return;
        }

        instance.localAddress = instance.activeGatewayDevice.getLocalAddress();
        LOG.info("Using local address: " + instance.localAddress.getHostAddress());

        instance.externalIPAddress = instance.activeGatewayDevice.getExternalIPAddress();
        LOG.info("External address: " + instance.externalIPAddress);

        LOG.info("Querying device to see if a port mapping already exists for port " + port);
        PortMappingEntry portMapping = new PortMappingEntry();

        if (instance.activeGatewayDevice.getSpecificPortMappingEntry(port, TCP_PROTOCOL , portMapping)) {
            
            LOG.warn("Port " + port + " is already mapped. Aborting to forwarding.");
            return;
            
        } else {

            LOG.info("Mapping free. Sending port mapping request for port " + port);
            boolean portForwardingSuccess = instance.activeGatewayDevice.addPortMapping(port, port, instance.localAddress.getHostAddress(), TCP_PROTOCOL, description);
            LOG.info("Port forwarding complete = " + portForwardingSuccess);
        }

    }

    /**
     * Remove a port from the active Gateway Device
     * @param port number
     * @throws IOException
     * @throws SAXException
     */
    public static void removePortForwarding(int port) throws IOException, SAXException {

        LOG.info("Removing port forwarding for port = "+port);

        if (instance.activeGatewayDevice != null){

            PortMappingEntry portMapping = new PortMappingEntry();
            if (instance.activeGatewayDevice.getSpecificPortMappingEntry(port, TCP_PROTOCOL, portMapping)){

                if (instance.activeGatewayDevice.deletePortMapping(port, TCP_PROTOCOL)) {
                    LOG.info("Port mapping removed, SUCCESSFUL");
                } else {
                    LOG.error("Port mapping removal FAILED");
                }
                
            }
                        
        }
        
    }

    /**
     * Get the Local Address
     * @return InetAddress
     */
    public InetAddress getLocalAddress() {
        return instance.localAddress;
    }

    /**
     * Get the External IP Address
     * @return String
     */
    public String getExternalIPAddress() {
        return instance.externalIPAddress;
    }
}
