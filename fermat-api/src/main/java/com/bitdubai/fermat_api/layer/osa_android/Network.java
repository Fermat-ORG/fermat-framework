package com.bitdubai.fermat_api.layer.osa_android;


/**
 * <p>The abstract class <code>Network</code> is a interface
 * that define the methods to gets and sets connection type.
 *
 * @author Natalia
 * @version 1.0.0
 * @since 06/05/15.
 */
public interface Network {

    ConnectionType getType();

    boolean getIsConnected();

    void setType(ConnectionType type);

    void setIsConnected(boolean connected);
}
