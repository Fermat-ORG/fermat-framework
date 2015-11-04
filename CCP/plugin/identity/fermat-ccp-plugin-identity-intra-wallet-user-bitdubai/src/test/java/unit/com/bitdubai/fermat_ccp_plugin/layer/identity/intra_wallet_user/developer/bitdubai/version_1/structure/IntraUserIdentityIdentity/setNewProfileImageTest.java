package unit.com.bitdubai.fermat_ccp_plugin.layer.identity.intra_wallet_user.developer.bitdubai.version_1.structure.IntraUserIdentityIdentity;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_wallet_user.exceptions.CantSetNewProfileImageException;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_wallet_user.exceptions.CantShowProfileImageException;
import com.bitdubai.fermat_ccp_api.layer.identity.intra_wallet_user.exceptions.CantSignIntraWalletUserMessageException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_ccp_plugin.layer.identity.intra_wallet_user.developer.bitdubai.version_1.structure.IntraWalletUserIdentity;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by angel on 19/8/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class setNewProfileImageTest extends TestCase {

    @Mock
    private PluginFileSystem mockPluginFileSystem;

    @Mock
    private PluginBinaryFile mockPluginBinaryFile;

    private UUID pluginId;

    @Before
    public void setUp() throws Exception {
        pluginId = UUID.randomUUID();
        when(mockPluginFileSystem.createBinaryFile(any(UUID.class), anyString(), anyString(), any(FilePrivacy.class), any(FileLifeSpan.class))).thenReturn(mockPluginBinaryFile);
    }

    @Test
    public void setNewProfileImageTest() throws CantSignIntraWalletUserMessageException, CantSetNewProfileImageException, CantShowProfileImageException {
        byte[] ProfileImage = new byte[10];

        ECCKeyPair eccKeyPair = new ECCKeyPair();
        IntraWalletUserIdentity identity_1 = new IntraWalletUserIdentity("alias_1", eccKeyPair.getPrivateKey(), eccKeyPair.getPublicKey(), new byte[10], mockPluginFileSystem, pluginId);

        identity_1.setPluginId(pluginId);
        identity_1.setPluginFileSystem(mockPluginFileSystem);

        identity_1.setNewProfileImage(ProfileImage);

        assertThat(identity_1.getProfileImage()).isEqualTo(ProfileImage);
    }
}
