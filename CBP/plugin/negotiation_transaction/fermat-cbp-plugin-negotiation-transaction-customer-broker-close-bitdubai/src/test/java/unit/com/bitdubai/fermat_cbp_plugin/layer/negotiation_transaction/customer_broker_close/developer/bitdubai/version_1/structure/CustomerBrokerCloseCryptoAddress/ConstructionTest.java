package unit.com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.structure.CustomerBrokerCloseCryptoAddress;

import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededPluginReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_bch_api.layer.crypto_module.crypto_address_book.interfaces.CryptoAddressBookManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.utils.CryptoVaultSelector;
import com.bitdubai.fermat_cbp_api.layer.negotiation_transaction.customer_broker_close.utils.WalletManagerSelector;
import com.bitdubai.fermat_cbp_plugin.layer.negotiation_transaction.customer_broker_close.developer.bitdubai.version_1.structure.CustomerBrokerCloseCryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by Yordin Alayn on 02.01.16.
 */
@RunWith(MockitoJUnitRunner.class)
public class ConstructionTest {

    @NeededPluginReference(platform = Platforms.BLOCKCHAINS,                layer = Layers.CRYPTO_MODULE,       plugin = Plugins.CRYPTO_ADDRESS_BOOK)
    @Mock
    private CryptoAddressBookManager cryptoAddressBookManager;

    @NeededPluginReference(platform = Platforms.BLOCKCHAINS,                layer = Layers.CRYPTO_VAULT,        plugin = Plugins.BITCOIN_VAULT)
    @Mock
    private CryptoVaultSelector cryptoVaultSelector;

    @NeededPluginReference(platform = Platforms.CRYPTO_CURRENCY_PLATFORM,   layer = Layers.MIDDLEWARE,          plugin = Plugins.WALLET_MANAGER)
    @Mock
    private WalletManagerSelector walletManagerSelector;

    @Mock
    private ErrorManager errorManager;

    @Mock
    private PluginVersionReference pluginVersionReference;

    private CustomerBrokerCloseCryptoAddress testObj1;

    @Before
    public void setUp(){
        testObj1 = new CustomerBrokerCloseCryptoAddress(
                cryptoAddressBookManager,
                cryptoVaultSelector,
                walletManagerSelector,
                errorManager,
                pluginVersionReference
        );
    }

    @Test
    public void Construction_ValidParameters_NewObjectCreated() {
        assertThat(testObj1).isNotNull();
    }
}
