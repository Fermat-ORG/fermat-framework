package unit.com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.structure.IncomingExtraUserRelayAgent;

import com.bitdubai.fermat_api.layer.dmp_basic_wallet.bitcoin_wallet.interfaces.BitcoinWalletManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.interfaces.WalletAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_router.incoming_crypto.IncomingCryptoManager;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.structure.IncomingExtraUserMonitorAgent;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.structure.IncomingExtraUserRegistry;
import com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.structure.IncomingExtraUserRelayAgent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by jorgegonzalez on 2015.07.02..
 */
@RunWith(MockitoJUnitRunner.class)
public class ConstructionTest {

    @Mock
    private ErrorManager mockErrorManager;
    @Mock
    private BitcoinWalletManager mockBitcoinWalletManager;
    @Mock
    private IncomingExtraUserRegistry mockRegistry;
    @Mock
    private WalletAddressBookManager mockWalletAddressBookManager;

    private IncomingExtraUserRelayAgent testRelayAgent;

    @Test
    public void Construction_ValidParameters_NewObjectCreated() {
        testRelayAgent = new IncomingExtraUserRelayAgent(mockBitcoinWalletManager, mockErrorManager, mockRegistry, mockWalletAddressBookManager);
        assertThat(testRelayAgent).isNotNull();
    }
}
