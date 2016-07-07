package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.clients.interfaces;

/**
 * Created by Matias Furszyfer on 2016.07.06..
 */
public interface NetworkChannel {

    void connect();

    void disconnect();

    boolean isConnected();


}
