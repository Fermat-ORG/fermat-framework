package unit.com.bitdubai.fermat_api.layer._1_definition.crypto.asymmetric.AsymmectricCryptography;

import static org.fest.assertions.api.Assertions.*;

import org.junit.Test;

import com.bitdubai.fermat_api.layer._1_definition.crypto.asymmetric.AsymmectricCryptography;

public class VerifyMessageSignatureTest extends AsymmetricCryptographyUnitTest {

	@Test
	public void VerifyMessageSignature_ValidParameters_ReturnsTrue() {
		assertThat(AsymmectricCryptography.verifyMessageSignature(testSignature, testEncryptedMessage, testPublicKey)).isTrue();
	}
	
	@Test
	public void VerifyMessageSignature_DifferentSignature_ReturnsFalse() {
		assertThat(AsymmectricCryptography.verifyMessageSignature(testSignature+"0", testEncryptedMessage, testPublicKey)).isFalse();
	}
	
	@Test
	public void VerifyMessageSignature_DifferentMessage_ReturnsFalse() {
		assertThat(AsymmectricCryptography.verifyMessageSignature(testSignature, testEncryptedMessage+"0", testPublicKey)).isFalse();
	}
	
	@Test
	public void VerifyMessageSignature_DifferentPublicKey_ReturnsFalse() {
		assertThat(AsymmectricCryptography.verifyMessageSignature(testSignature, testEncryptedMessage, testPublicKey+"0")).isFalse();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void VerifyMessageSignature_NullSignature_ThrowsIllegalArgumentException() {
		AsymmectricCryptography.verifyMessageSignature(null, testEncryptedMessage, testPublicKey);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void VerifyMessageSignature_EmptySignature_ThrowsIllegalArgumentException() {
		AsymmectricCryptography.verifyMessageSignature("", testEncryptedMessage, testPublicKey);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void VerifyMessageSignature_NullMessage_ThrowsIllegalArgumentException() {
		AsymmectricCryptography.verifyMessageSignature(testSignature, null, testPublicKey);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void VerifyMessageSignature_EmptyMessage_ThrowsIllegalArgumentException() {
		AsymmectricCryptography.verifyMessageSignature(testSignature, "", testPublicKey);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void VerifyMessageSignature_NullPublicKey_ThrowsIllegalArgumentException() {
		AsymmectricCryptography.verifyMessageSignature(testSignature, testEncryptedMessage, null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void VerifyMessageSignature_EmptyPublicKey_ThrowsIllegalArgumentException() {
		AsymmectricCryptography.verifyMessageSignature(testSignature, testEncryptedMessage, "");
	}

}
