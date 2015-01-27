package platform.layer._9_network_service;

/**
 * Created by ciencias on 20.01.15.
 */
public interface NetworkSubsystem {
    public void start () throws CantStartSubsystemException;
    public NetworkService getNetworkService();
}
