package unit.com.bitdubai.fermat_ccp_plugin.layer.identity.intra_wallet_user.developer.bitdubai.version_1.structure.IntraUserIdentityIdentity;

import com.bitdubai.fermat_ccp_api.layer.identity.intra_wallet_user.exceptions.CantShowProfileImageException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_ccp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.structure.IntraWalletUserIdentity;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;


/**
 * Created by angel on 13/8/15.
 */
public class Getters_Identity_Test{

    IntraWalletUserIdentity identity_1;

    String alias_1;
    String publicKey_1;
    String privateKey_1;
    byte[] profileImage_1;

    @Mock
    private PluginFileSystem mockPluginFileSystem;

    private UUID pluginId;

    @Before
    public void setUpVariable1(){
        pluginId = UUID.randomUUID();

        alias_1 = "alias_1";
        publicKey_1 = "publicKey_1";
        privateKey_1 = "privateKey_1";
        profileImage_1 = new byte[10];

        identity_1 = new IntraWalletUserIdentity(alias_1, publicKey_1, privateKey_1, profileImage_1, mockPluginFileSystem, pluginId);

    }

    @Test
    public void Get_Alias_AreEquals(){
        assertThat(identity_1.getAlias()).isEqualTo(alias_1);
    }

    @Test
    public void Get_PublicKey_AreEquals(){
        assertThat(identity_1.getPublicKey()).isEqualTo(publicKey_1);
    }

    @Test
    public void Get_ProfileImage_AreEquals() throws CantShowProfileImageException{
        assertThat(identity_1.getProfileImage()).isEqualTo(profileImage_1);
    }
}
