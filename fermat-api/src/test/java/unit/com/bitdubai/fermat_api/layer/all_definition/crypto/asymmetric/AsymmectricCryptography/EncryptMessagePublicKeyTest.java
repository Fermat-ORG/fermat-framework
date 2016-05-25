package unit.com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmectricCryptography;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;

import org.junit.Test;

import java.math.BigInteger;

import static org.fest.assertions.api.Assertions.assertThat;

public class EncryptMessagePublicKeyTest extends AsymmetricCryptographyUnitTest{
	
	@Test
	public void EncryptMessagePublicKey_ValidParameters_NotNull() {
		assertThat(AsymmetricCryptography.encryptMessagePublicKey(testPlainMessage, testPublicKey)).isNotNull();
	}

	@Test
	public void EncryptJsonMessagePublicKey_ValidParameters_NotNull() {
		assertThat(AsymmetricCryptography.encryptMessagePublicKey(testJsonStringMessage, testPublicKey)).isNotNull();
	}
	
	@Test
	public void EncryptMessagePublicKey_ValidParameters_BigIntegerBytesValue() {
		BigInteger encryptedMessage = new BigInteger(AsymmetricCryptography.encryptMessagePublicKey(testPlainMessage, testPublicKey),16);
		assertThat(encryptedMessage).isNotNull();
	}
	
	@Test
	public void EncryptMessagePublicKey_ValidParameters_DifferentThanPlainMessage() {
		String encryptedMessage = AsymmetricCryptography.encryptMessagePublicKey(testPlainMessage, testPublicKey);
		assertThat(encryptedMessage).isNotEqualTo(testPlainMessage);
	}
	
	@Test
	public void EncryptMessagePublicKey_MultipleExecutions_DifferentEncryptedMessages() {
		String encryptedMessage1 = AsymmetricCryptography.encryptMessagePublicKey(testPlainMessage, testPublicKey);
		String encryptedMessage2 = AsymmetricCryptography.encryptMessagePublicKey(testPlainMessage, testPublicKey);
		assertThat(encryptedMessage1).isNotEqualTo(encryptedMessage2);		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void EncryptMessagePublicKey_NullMessage_ThrowIllegalArgumentException() {
		AsymmetricCryptography.encryptMessagePublicKey(null, testPublicKey);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void EncryptMessagePublicKey_EmptyMessage_ThrowIllegalArgumentException() {
		AsymmetricCryptography.encryptMessagePublicKey("", testPublicKey);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void EncryptMessagePublicKey_NullPublicKey_ThrowIllegalArgumentException() {
		AsymmetricCryptography.encryptMessagePublicKey(testPlainMessage, null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void EncryptMessagePublicKey_EmptyPublicKey_ThrowIllegalArgumentException() {
		AsymmetricCryptography.encryptMessagePublicKey(testPlainMessage, "");
	}

}
