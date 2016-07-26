package unit.CryptoBrokerWalletImpl;

import com.bitdubai.fermat_api.layer.osa_android.broadcaster.Broadcaster;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.CryptoBrokerWalletPluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.structure.util.CryptoBrokerWalletImpl;
import com.bitdubai.fermat_cer_api.layer.search.interfaces.CurrencyExchangeProviderFilterManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by Jose Vilchez on 18/01/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class ConstructionTest {

    private UUID pluginId = UUID.randomUUID();

    @Mock
    private PluginDatabaseSystem pluginDatabaseSystem;

    @Mock
    private PluginFileSystem pluginFileSystem;

    @Mock
    private CurrencyExchangeProviderFilterManager providerFilter;

    @Mock
    private CryptoBrokerWalletPluginRoot pluginRoot;

    @Mock
    private Broadcaster broadcaster;


    @Test
    public void Construction_ValidParameters_NewObjectCreated() {
        CryptoBrokerWalletImpl cryptoBrokerWallet = new CryptoBrokerWalletImpl(
                this.pluginRoot,
                this.pluginDatabaseSystem,
                this.pluginFileSystem,
                this.pluginId,
                this.providerFilter,
                this.broadcaster
        );
        assertThat(cryptoBrokerWallet).isNotNull();
    }
}
