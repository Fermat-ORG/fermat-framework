package com.bitdubai.smartwallet.platform.layer._7_crypto_network;

import com.bitdubai.smartwallet.platform.layer._1_definition.enums.ServiceStatus;

import java.util.UUID;

/**
 * Created by ciencias on 20.01.15.
 */
public interface CryptoNetworkService {

    public void run();

    public void pause();

    public void stop();

    public ServiceStatus getStatus();

}
