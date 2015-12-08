package unit.com.bitdubai.fermat_bch_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.BitcoinCryptoVault;

import com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin.BitcoinCryptoNetworkManager;
import com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin.exceptions.CantConnectToBitcoinNetwork;
import com.bitdubai.fermat_bch_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.BitcoinCryptoVault;

import org.bitcoinj.core.Wallet;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;


/**
 * Created by natalia on 27/08/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class ConnectionTest {


    @Mock
    BitcoinCryptoNetworkManager mockBitcoinCryptoNetworkManager;


    private BitcoinCryptoVault bitcoinCryptoVault;


    private String userPublicKey = "replace_user_public_key";

    @Before
    public void setUp() throws Exception{

        bitcoinCryptoVault = new BitcoinCryptoVault(userPublicKey);
        bitcoinCryptoVault.setBitcoinCryptoNetworkManager(mockBitcoinCryptoNetworkManager);



    }
    @Test
    public void connectVaultTest_ThrowsCantConnectToBitcoinNetwork() throws  Exception{

        catchException(bitcoinCryptoVault).connectVault();
        assertThat(caughtException())
                .isNull();
    }

    @Test
    public void connectVaultTest_Error_ThrowsCantConnectToBitcoinNetwork() throws  Exception{

        bitcoinCryptoVault.setBitcoinCryptoNetworkManager(null);
        catchException(bitcoinCryptoVault).connectVault();
        assertThat(caughtException())
                .isNotNull().isInstanceOf(CantConnectToBitcoinNetwork.class);

    }

    @Test
    public void disconnectVaultTest_ThrowsCantConnectToBitcoinNetwork()throws  Exception{
        catchException(bitcoinCryptoVault).disconnectVault();
        assertThat(caughtException())
                .isNull();
    }

    @Test
    public void disconnectVaultTest_Error_ThrowsCantConnectToBitcoinNetwork() throws  Exception{

        bitcoinCryptoVault.setBitcoinCryptoNetworkManager(null);
        catchException(bitcoinCryptoVault).disconnectVault();
        assertThat(caughtException())
                .isNotNull().isInstanceOf(CantConnectToBitcoinNetwork.class);

    }


}
