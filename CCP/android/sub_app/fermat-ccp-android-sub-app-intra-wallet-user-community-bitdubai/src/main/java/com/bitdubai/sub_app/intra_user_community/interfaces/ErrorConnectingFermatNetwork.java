package com.bitdubai.sub_app.intra_user_community.interfaces;

/**
 * Created by josemanueldsds on 12/01/16.
 */
public interface ErrorConnectingFermatNetwork {

    /**
     * Is called when user is not connected to fermat network
     *
     * @param fermatConnect boolean true when user is connected to network
     */
    void errorConnectingFermatNetwork(boolean fermatConnect);

    /**
     * Is called when user is not connected to network
     *
     * @param connect boolean true when user is connected to network
     */
    void errorConnectingNetwork(boolean connect);

}
