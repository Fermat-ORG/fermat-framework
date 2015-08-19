package unit.com.bitdubai.fermat_dmp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.structure.IntraUserIdentityIdentity;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.dmp_identity.intra_user.exceptions.CantSetNewProfileImageException;
import com.bitdubai.fermat_api.layer.dmp_identity.intra_user.exceptions.CantShowProfileImageException;
import com.bitdubai.fermat_api.layer.dmp_identity.intra_user.exceptions.CantSingIntraUserMessageException;
import com.bitdubai.fermat_dmp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.structure.IntraUserIdentityIdentity;

import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by angel on 19/8/15.
 */

public class setNewProfileImageTest {
    @Test
    public void setNewProfileImageTest() throws CantSingIntraUserMessageException, CantSetNewProfileImageException, CantShowProfileImageException {
        byte[] ProfileImage = new byte[10];

        ECCKeyPair eccKeyPair = new ECCKeyPair();
        IntraUserIdentityIdentity identity_1 = new IntraUserIdentityIdentity("alias_1", eccKeyPair.getPrivateKey(), eccKeyPair.getPublicKey(), new byte[10]);

        identity_1.setNewProfileImage(ProfileImage);

        assertThat(identity_1.getProfileImage()).isEqualTo(ProfileImage);
    }
}
