package com.bitdubai.android_fermat_pip_addon_layer_2_os_file_system.version_1.interfaces;

import com.bitdubai.fermat_api.layer._12_world.wallet.exceptions.CantStartAgentException;

/**
 *
 *  <p>The abstract class <code>com.bitdubai.android_fermat_pip_addon_layer_2_os_file_system.version_1.interfaces.ConnectivityAgent</code> is a interface
 *     that define the methods to manage connectivity agent.
 *
 *
 *  @author  Natalia
 *  @version 1.0.0
 *  @since   08/05/15.
 * */
public interface ConnectivityAgent {

    public void start() throws CantStartAgentException;

    public void stop();

    public Network getConnectionInfo();
}
