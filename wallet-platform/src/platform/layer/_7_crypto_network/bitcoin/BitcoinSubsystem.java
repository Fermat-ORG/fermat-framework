package platform.layer._7_crypto_network.bitcoin;

import platform.layer._7_crypto_network.CantStartSubsystemException;
import platform.layer._7_crypto_network.CryptoNetworkService;
import platform.layer._7_crypto_network.CryptoNetworkSubsystem;
import platform.layer._7_crypto_network.bitcoin.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by ciencias on 30.12.14.
 */
public class BitcoinSubsystem implements CryptoNetworkSubsystem {

    CryptoNetworkService mCryptoNetworkService;

    @Override
    public CryptoNetworkService getCryptoNetwork() {
        return mCryptoNetworkService;
    }

    @Override
    public void start() throws CantStartSubsystemException {

        /**
         * I will choose from the different Developers available which implementation to use. Right now there is only
         * one, so it is not difficult to choose.
         */

        try {
            DeveloperBitDubai developerBitDubai = new DeveloperBitDubai();
            mCryptoNetworkService = developerBitDubai.getCryptoNetwork();
        }
        catch (Exception e)
        {
            System.err.println("Exception: " + e.getMessage());
            throw new CantStartSubsystemException();
        }
    }


}
