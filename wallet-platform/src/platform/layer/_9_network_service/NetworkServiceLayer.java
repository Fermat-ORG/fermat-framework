package platform.layer._9_network_service;

import platform.layer.CantStartLayerException;
import platform.layer.PlatformLayer;
import platform.layer._9_network_service.user.UserSubsystem;

/**
 * Created by ciencias on 03.01.15.
 */
public class NetworkServiceLayer implements PlatformLayer {

    private NetworkService mUserService;

    public NetworkService getUserService() {
        return mUserService;
    }

    @Override
    public void start() throws CantStartLayerException {

        /**
         * The most essential service is the UserService. I start it now.
         */
        NetworkSubsystem userSubsystem = new UserSubsystem();

        try {
            userSubsystem.start();
            mUserService = ((UserSubsystem) userSubsystem).getNetworkService();

        } catch (CantStartSubsystemException e) {
            System.err.println("CantStartCryptoNetworkException: " + e.getMessage());

            /**
             * Since this is the only implementation, if this does not start, then the layer can't start either.
             */
            throw new CantStartLayerException();
        }
    }
}
