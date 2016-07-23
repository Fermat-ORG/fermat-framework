package unit.com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_broker.developer.bitdubai.version_1.structure.CryptoBrokerIdentityImpl;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.interfaces.KeyPair;
import com.bitdubai.fermat_cbp_api.layer.identity.crypto_broker.interfaces.CryptoBrokerIdentity;
import com.bitdubai.fermat_cbp_plugin.layer.identity.crypto_broker.developer.bitdubai.version_1.structure.CryptoBrokerIdentityImpl;

import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by jorge on 28-09-2015.
 */
public class EqualsAndHashTest {

    private static final String TEST_PRIVATE_KEY = "18E14A7B6A307F426A94F8114701E7C8E774E7F9A47E2C2035DB29A206321725";
    private static final String TEST_PUBLIC_KEY = "0450863AD64A87AE8A2FE83C1AF1A8403CB53F53E486D8511DAD8A04887E5B23522CD470243453A299FA9E77237716103ABC11A1DF38855ED6F2EE187E9C582BA6";

    private String testAlias = "TEST";
    private KeyPair testKeyPair;
    private byte[] testProfileImage = new byte[0];
    private boolean testPublished = false;

    private CryptoBrokerIdentity testIdentity1, testIdentity2;

    @Before
    public void setUpIdentity() {
        testKeyPair = AsymmetricCryptography.createKeyPair(TEST_PRIVATE_KEY);
        testIdentity1 = new CryptoBrokerIdentityImpl(testAlias, testKeyPair, testProfileImage, testPublished);
    }

    @Test
    public void Equals_SameValues_True() {
        testIdentity2 = new CryptoBrokerIdentityImpl(testAlias, testKeyPair, testProfileImage, testPublished);
        assertThat(testIdentity1).isEqualTo(testIdentity2);
        assertThat(testIdentity1.hashCode()).isEqualTo(testIdentity2.hashCode());
    }

    @Test
    public void Equals_DifferentAlias_False() {
        testIdentity2 = new CryptoBrokerIdentityImpl("OTHER_TEST", testKeyPair, testProfileImage, testPublished);
        assertThat(testIdentity1).isNotEqualTo(testIdentity2);
        assertThat(testIdentity1.hashCode()).isNotEqualTo(testIdentity2.hashCode());
    }

    @Test
    public void Equals_DifferentKeyPair_False() {
        testIdentity2 = new CryptoBrokerIdentityImpl(testAlias, AsymmetricCryptography.generateECCKeyPair(), testProfileImage, testPublished);
        assertThat(testIdentity1).isNotEqualTo(testIdentity2);
        assertThat(testIdentity1.hashCode()).isNotEqualTo(testIdentity2.hashCode());
    }

}
