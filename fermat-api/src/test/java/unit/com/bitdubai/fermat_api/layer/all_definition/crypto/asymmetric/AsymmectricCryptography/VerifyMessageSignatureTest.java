package unit.com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmectricCryptography;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;

import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class VerifyMessageSignatureTest extends AsymmetricCryptographyUnitTest {

    @Test
    public void VerifyMessageSignature_ValidParameters_ReturnsTrue() {
        assertThat(AsymmetricCryptography.verifyMessageSignature(testSignature, testEncryptedMessage, testPublicKey)).isTrue();
    }

    @Test
    public void VerifyMessageSignature_DifferentSignature_ReturnsFalse() {
        assertThat(AsymmetricCryptography.verifyMessageSignature(new StringBuilder().append(testSignature).append("0").toString(), testEncryptedMessage, testPublicKey)).isFalse();
    }

    @Test
    public void VerifyMessageSignature_DifferentMessage_ReturnsFalse() {
        assertThat(AsymmetricCryptography.verifyMessageSignature(testSignature, new StringBuilder().append(testEncryptedMessage).append("0").toString(), testPublicKey)).isFalse();
    }

    @Test
    public void VerifyMessageSignature_DifferentPublicKey_ReturnsFalse() {
        assertThat(AsymmetricCryptography.verifyMessageSignature(testSignature, testEncryptedMessage, new StringBuilder().append(testPublicKey).append("0").toString())).isFalse();
    }

    @Test(expected = IllegalArgumentException.class)
    public void VerifyMessageSignature_NullSignature_ThrowsIllegalArgumentException() {
        AsymmetricCryptography.verifyMessageSignature(null, testEncryptedMessage, testPublicKey);
    }

    @Test(expected = IllegalArgumentException.class)
    public void VerifyMessageSignature_EmptySignature_ThrowsIllegalArgumentException() {
        AsymmetricCryptography.verifyMessageSignature("", testEncryptedMessage, testPublicKey);
    }

    @Test(expected = IllegalArgumentException.class)
    public void VerifyMessageSignature_NullMessage_ThrowsIllegalArgumentException() {
        AsymmetricCryptography.verifyMessageSignature(testSignature, null, testPublicKey);
    }

    @Test(expected = IllegalArgumentException.class)
    public void VerifyMessageSignature_EmptyMessage_ThrowsIllegalArgumentException() {
        AsymmetricCryptography.verifyMessageSignature(testSignature, "", testPublicKey);
    }

    @Test(expected = IllegalArgumentException.class)
    public void VerifyMessageSignature_NullPublicKey_ThrowsIllegalArgumentException() {
        AsymmetricCryptography.verifyMessageSignature(testSignature, testEncryptedMessage, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void VerifyMessageSignature_EmptyPublicKey_ThrowsIllegalArgumentException() {
        AsymmetricCryptography.verifyMessageSignature(testSignature, testEncryptedMessage, "");
    }

}
