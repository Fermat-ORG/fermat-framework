package unit.com.bitdubai.fermat_dmp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.structure.IntraUserIdentityIdentity;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.crypto.util.CryptoHasher;
import com.bitdubai.fermat_api.layer.dmp_identity.intra_user.exceptions.CantSingIntraUserMessageException;
import com.bitdubai.fermat_dmp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.structure.IntraUserIdentityIdentity;
import com.googlecode.catchexception.CatchException;

import org.junit.Test;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by angel on 14/8/15.
 */

public class createMessageSignatureTest{

    @Test
    public void createMessageSignature() throws CantSingIntraUserMessageException {
        ECCKeyPair eccKeyPair = new ECCKeyPair();
        String hashMessage = CryptoHasher.performSha256("prueba");
        IntraUserIdentityIdentity identity_1 = new IntraUserIdentityIdentity("alias_1", eccKeyPair.getPrivateKey(), eccKeyPair.getPublicKey(), new byte[10]);

        assertThat(identity_1.createMessageSignature(hashMessage)).isInstanceOf(String.class);
    }

    @Test
    public void createMessageSignatureExceptionTest() throws CantSingIntraUserMessageException {
        IntraUserIdentityIdentity identity_1 = new IntraUserIdentityIdentity("alias_1", "publicKey_1", "privateKey_1", new byte[10]);

        catchException(identity_1).createMessageSignature("Prueba");
        assertThat(CatchException.<Exception>caughtException()).isInstanceOf(CantSingIntraUserMessageException.class);
    }
}
