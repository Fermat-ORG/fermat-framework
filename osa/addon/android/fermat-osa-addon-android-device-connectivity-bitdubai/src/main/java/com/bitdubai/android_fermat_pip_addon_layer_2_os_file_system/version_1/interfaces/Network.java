package com.bitdubai.android_fermat_pip_addon_layer_2_os_file_system.version_1.interfaces;


/**
 *
 *  <p>The abstract class <code>com.bitdubai.android_fermat_pip_addon_layer_2_os_file_system.version_1.interfaces.Network</code> is a interface
 *     that define the methods to gets and sets connection type.
 *
 *
 *  @author  Natalia
 *  @version 1.0.0
 *  @since   06/05/15.
 * */
public interface Network {

    public ConnectionType getType();

    public boolean getIsConnected();

    public void setType(ConnectionType type);

    public void setIsConnected(boolean connected);
}
