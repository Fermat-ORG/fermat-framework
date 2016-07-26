package unit.com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.structure.CustomerBrokerCloseCryptoAddressRequestImpl;

import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.structure.CustomerBrokerCloseCryptoAddressRequestImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by Yordin Alayn on 02.01.16.
 */
@RunWith(MockitoJUnitRunner.class)
public class ConstructionTest {

    private final Actors identityTypeRequesting = Actors.CBP_CRYPTO_CUSTOMER;

    private final Actors identityTypeResponding = Actors.CBP_CRYPTO_BROKER;

    private final String identityPublicKeyRequesting = "identityPublicKeyRequesting";

    private final String identityPublicKeyResponding = "identityPublicKeyResponding";

    private final CryptoCurrency cryptoCurrency = CryptoCurrency.BITCOIN;

    private final BlockchainNetworkType blockchainNetworkType = BlockchainNetworkType.getDefaultBlockchainNetworkType();

    private CustomerBrokerCloseCryptoAddressRequestImpl testObj1;

    @Before
    public void setUp() {

        testObj1 = new CustomerBrokerCloseCryptoAddressRequestImpl(
                identityTypeRequesting,
                identityTypeResponding,
                identityPublicKeyRequesting,
                identityPublicKeyResponding,
                cryptoCurrency,
                blockchainNetworkType
        );
    }

    @Test
    public void Construction_ValidParameters_NewObjectCreated() {
        assertThat(testObj1).isNotNull();
    }
}
