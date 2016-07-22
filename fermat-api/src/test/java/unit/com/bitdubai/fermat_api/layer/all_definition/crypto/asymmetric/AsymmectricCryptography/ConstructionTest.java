package unit.com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmectricCryptography;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;

import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class ConstructionTest {

    private String testPrivateKey = "18E14A7B6A307F426A94F8114701E7C8E774E7F9A47E2C2035DB29A206321725";
    private String testPublicKey = "0450863AD64A87AE8A2FE83C1AF1A8403CB53F53E486D8511DAD8A04887E5B23522CD470243453A299FA9E77237716103ABC11A1DF38855ED6F2EE187E9C582BA6";

    @Test
    public void Construction_Valid_NotNull() throws Exception {
        ECCKeyPair testPairDefault = new ECCKeyPair();
        ECCKeyPair testPairWithPrivate = new ECCKeyPair(testPrivateKey);
        ECCKeyPair testPairWithBoth = new ECCKeyPair(testPrivateKey, testPublicKey);

        assertThat(testPairDefault).isNotNull();
        assertThat(testPairWithPrivate).isNotNull();
        assertThat(testPairWithBoth).isNotNull();
    }

    @Test
    public void Construction_NoParameters_IsRandomized() throws Exception {
        ECCKeyPair testPairDefault1 = new ECCKeyPair();
        ECCKeyPair testPairDefault2 = new ECCKeyPair();
        assertThat(testPairDefault1).isNotEqualTo(testPairDefault2);
    }

    @Test
    public void Construction_Parametrized_IsDerivedFromPrivateKey() throws Exception {
        ECCKeyPair testPairDefault1 = new ECCKeyPair(testPrivateKey);
        ECCKeyPair testPairDefault2 = new ECCKeyPair(testPrivateKey);
        assertThat(testPairDefault1).isEqualTo(testPairDefault2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void Construction_NullPrivateKeyString_ThrowsIllegalArgumentException() {
        new ECCKeyPair(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void Construction_EmptyPrivateKeyString_ThrowsIllegalArgumentException() {
        new ECCKeyPair("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void Construction_NullPublicKeyString_ThrowsIllegalArgumentException() {
        new ECCKeyPair(testPrivateKey, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void Construction_EmptyPublicKeyString_ThrowsIllegalArgumentException() {
        new ECCKeyPair(testPrivateKey, "");
    }

    @Test(expected = IllegalArgumentException.class)
    public void Construction_PublicKeyStringISNotDerivedFromPrivateKey_ThrowsIllegalArgumentException() {
        new ECCKeyPair(testPrivateKey, "ABCDEF1234");
    }

    @Test
    public void Construction_NoParameters_whitAsymmectricCryptography() throws Exception {
        ECCKeyPair testPairDefault1 = AsymmetricCryptography.generateECCKeyPair();
        assertThat(testPairDefault1).isNotNull();
    }

}
