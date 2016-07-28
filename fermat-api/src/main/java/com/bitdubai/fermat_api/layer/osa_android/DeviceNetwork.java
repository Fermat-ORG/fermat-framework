package com.bitdubai.fermat_api.layer.osa_android;

/**
 * Created by Natalia on 06/05/2015.
 */

/**
 * The DeviceNetwork class is the implementation of Network interfaces that is handled to DeviceManager.
 * That class sets and gets connection type.
 */
public class DeviceNetwork implements Network {

    /**
     * Network Interface member variables.
     */

    private ConnectionType type;
    private boolean isconnected;

    public DeviceNetwork(ConnectionType type, boolean isconnected) {
        this.type = type;
        this.isconnected = isconnected;
    }

    /**
     * Network Interface implementation.
     */

    /**
     * <p> This method gets the network connection type.
     *
     * @return ConnectionType enum
     */
    @Override
    public ConnectionType getType() {
        return this.type;
    }

    /**
     * <p> This method gets if the device is connected to the network.
     *
     * @return boolean if connected
     */
    @Override
    public boolean getIsConnected() {
        return this.isconnected;
    }

    /**
     * <p> This method sets the network connection type.
     *
     * @param type ConnectionType enum
     */
    @Override
    public void setType(ConnectionType type) {

        this.type = type;
    }


    /**
     * <p> This method sets if the device is connected to the network.
     *
     * @param connected boolean if connected
     */
    @Override
    public void setIsConnected(boolean connected) {
        this.isconnected = connected;
    }
}
