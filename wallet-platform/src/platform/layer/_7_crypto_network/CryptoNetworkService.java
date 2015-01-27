package platform.layer._7_crypto_network;

import platform.layer._1_definition.enums.ServiceStatus;

/**
 * Created by ciencias on 20.01.15.
 */
public interface CryptoNetworkService {

    public void run();

    public void pause();

    public void stop();

    public ServiceStatus getStatus();

}
