package com.bitdubai.fermat_osa_addon.layer.android.device_conectivity.developer.bitdubai.version_1.interfaces;

import com.bitdubai.fermat_api.CantStartAgentException;

/**
 *
 *  <p>The abstract class <code>ConnectivityAgent</code> is a interface
 *     that define the methods to manage connectivity agent.
 *
 *
 *  @author  Natalia
 *  @version 1.0.0
 *  @since   08/05/15.
 * */
public interface ConnectivityAgent {

    void start() throws CantStartAgentException;

    void stop();

    Network getConnectionInfo();
}
