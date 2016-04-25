package com.bitdubai.sub_app.chat_community.interfaces;

/**
 * ErrorConnectingFermatNetwork
 *
 * @author Jose Cardozo josejcb (josejcb89@gmail.com) on 13/04/16.
 * @version 1.0
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
