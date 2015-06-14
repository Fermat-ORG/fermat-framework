package unit.com.bitdubai.fermat_api.layer._1_definition.crypto.asymmetric.AsymmectricCryptography;

import static org.fest.assertions.api.Assertions.*;

import org.junit.Test;

import com.bitdubai.fermat_api.layer._1_definition.crypto.asymmetric.AsymmectricCryptography;

public class DecryptMessagePrivateKey extends AsymmetricCryptographyUnitTest {

	@Test
	public void DecryptMessagePrivateKey_ValidParameters_NotNull() {
		assertThat(AsymmectricCryptography.decryptMessagePrivateKey(testEncryptedMessage, testPrivateKey)).isNotNull();
	}
	
	@Test
	public void DecryptMessagePrivateKey_ValidParameters_ReturnsPlainMessage() {
		String decryptedMessage = AsymmectricCryptography.decryptMessagePrivateKey(testEncryptedMessage, testPrivateKey);
		assertThat(decryptedMessage).isEqualTo(testPlainMessage);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void DecryptMessagePrivateKey_NullMessage_ThrowIllegalArgumentException() {
		AsymmectricCryptography.decryptMessagePrivateKey(null, testPrivateKey);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void DecryptMessagePrivateKey_EmptyMessage_ThrowIllegalArgumentException() {
		AsymmectricCryptography.decryptMessagePrivateKey("", testPrivateKey);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void DecryptMessagePrivateKey_NullPrivateKey_ThrowIllegalArgumentException() {
		AsymmectricCryptography.decryptMessagePrivateKey(testEncryptedMessage, null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void DecryptMessagePrivateKey_EmptyPrivateKey_ThrowIllegalArgumentException() {
		AsymmectricCryptography.decryptMessagePrivateKey(testEncryptedMessage, "");
	}

}
