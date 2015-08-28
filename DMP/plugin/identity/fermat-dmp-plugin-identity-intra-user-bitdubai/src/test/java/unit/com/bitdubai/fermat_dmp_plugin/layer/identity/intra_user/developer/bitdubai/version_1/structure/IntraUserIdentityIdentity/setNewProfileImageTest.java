package unit.com.bitdubai.fermat_dmp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.structure.IntraUserIdentityIdentity;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.dmp_identity.intra_user.exceptions.CantSetNewProfileImageException;
import com.bitdubai.fermat_api.layer.dmp_identity.intra_user.exceptions.CantShowProfileImageException;
import com.bitdubai.fermat_api.layer.dmp_identity.intra_user.exceptions.CantSingIntraUserMessageException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_dmp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.structure.IntraUserIdentityIdentity;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;

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
    public void setNewProfileImageTest() throws CantSingIntraUserMessageException, CantSetNewProfileImageException, CantShowProfileImageException {
        byte[] ProfileImage = new byte[10];

        ECCKeyPair eccKeyPair = new ECCKeyPair();
        IntraUserIdentityIdentity identity_1 = new IntraUserIdentityIdentity("alias_1", eccKeyPair.getPrivateKey(), eccKeyPair.getPublicKey(), new byte[10], mockPluginFileSystem, pluginId);

        identity_1.setNewProfileImage(ProfileImage);

        assertThat(identity_1.getProfileImage()).isEqualTo(ProfileImage);
    }
}
