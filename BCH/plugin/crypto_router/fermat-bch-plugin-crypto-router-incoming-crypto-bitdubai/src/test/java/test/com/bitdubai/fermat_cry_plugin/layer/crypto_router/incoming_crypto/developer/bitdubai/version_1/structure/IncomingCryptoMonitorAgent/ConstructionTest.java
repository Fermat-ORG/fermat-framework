package test.com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.structure.IncomingCryptoMonitorAgent;

import com.bitdubai.fermat_bch_api.layer.crypto_vault.currency_vault.CryptoVaultManager;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.structure.IncomingCryptoMonitorAgent;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.structure.IncomingCryptoRegistry;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import static org.fest.assertions.api.Assertions.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
/**
 * Created by Franklin Marcano 04/08/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class ConstructionTest {
    @Mock
    private ErrorManager mockErrorManager;

    @Mock
    private CryptoVaultManager mockIncomingCryptoManager;

    @Mock
    private IncomingCryptoRegistry mockRegistry;

    @Mock
    private IncomingCryptoMonitorAgent testMonitorAgent;

    @Test
    public void Construction_ValidParameters_NewObjectCreated() {
        testMonitorAgent = new IncomingCryptoMonitorAgent();
        testMonitorAgent.setCryptoVaultManager(mockIncomingCryptoManager);
        testMonitorAgent.setErrorManager(mockErrorManager);
        testMonitorAgent.setRegistry(mockRegistry);
        assertThat(testMonitorAgent).isNotNull();
    }

}
