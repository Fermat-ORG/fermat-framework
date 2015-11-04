package unit.com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmectricCryptography;

import static org.fest.assertions.api.Assertions.*;

import org.junit.Test;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;

public class DecryptMessagePrivateKey extends AsymmetricCryptographyUnitTest {

	@Test
	public void DecryptMessagePrivateKey_ValidParameters_NotNull() {
		assertThat(AsymmetricCryptography.decryptMessagePrivateKey(testEncryptedMessage, testPrivateKey)).isNotNull();
	}
	
	@Test
	public void DecryptMessagePrivateKey_ValidParameters_ReturnsPlainMessage() {
		String decryptedMessage = AsymmetricCryptography.decryptMessagePrivateKey(testEncryptedMessage, testPrivateKey);
		assertThat(decryptedMessage).isEqualTo(testPlainMessage);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void DecryptMessagePrivateKey_NullMessage_ThrowIllegalArgumentException() {
		AsymmetricCryptography.decryptMessagePrivateKey(null, testPrivateKey);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void DecryptMessagePrivateKey_EmptyMessage_ThrowIllegalArgumentException() {
		AsymmetricCryptography.decryptMessagePrivateKey("", testPrivateKey);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void DecryptMessagePrivateKey_NullPrivateKey_ThrowIllegalArgumentException() {
		AsymmetricCryptography.decryptMessagePrivateKey(testEncryptedMessage, null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void DecryptMessagePrivateKey_EmptyPrivateKey_ThrowIllegalArgumentException() {
		AsymmetricCryptography.decryptMessagePrivateKey(testEncryptedMessage, "");
	}

}
