package unit.com.bitdubai.fermat_ccp_plugin.layer.identity.intra_wallet_user.developer.bitdubai.version_1.structure.IntraUserIdentityIdentity;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.crypto.util.CryptoHasher;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_wallet_user.exceptions.CantSignIntraWalletUserMessageException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_ccp_plugin.layer.identity.intra_wallet_user.developer.bitdubai.version_1.structure.IntraWalletUserIdentity;
import com.googlecode.catchexception.CatchException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.UUID;

import static com.googlecode.catchexception.CatchException.catchException;
import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by angel on 14/8/15.
 */

public class createMessageSignatureTest{

    @Mock
    private PluginFileSystem mockPluginFileSystem;

    private UUID pluginId;

    @Before
    public void setUp() throws Exception {
        pluginId = UUID.randomUUID();
    }

    @Test
    public void createMessageSignature() throws CantSignIntraWalletUserMessageException {
        ECCKeyPair eccKeyPair = new ECCKeyPair();
        String hashMessage = CryptoHasher.performSha256("prueba");
        IntraWalletUserIdentity identity_1 = new IntraWalletUserIdentity("alias_1", eccKeyPair.getPrivateKey(), eccKeyPair.getPublicKey(), new byte[10], mockPluginFileSystem, pluginId);

        assertThat(identity_1.createMessageSignature(hashMessage)).isInstanceOf(String.class);
    }

    @Test
    public void createMessageSignatureExceptionTest() throws CantSignIntraWalletUserMessageException {
        IntraWalletUserIdentity identity_1 = new IntraWalletUserIdentity("alias_1", "publicKey_1", "privateKey_1", new byte[10], mockPluginFileSystem, pluginId);

        catchException(identity_1).createMessageSignature("Prueba");
        assertThat(CatchException.<Exception>caughtException()).isInstanceOf(CantSignIntraWalletUserMessageException.class);
    }
}
