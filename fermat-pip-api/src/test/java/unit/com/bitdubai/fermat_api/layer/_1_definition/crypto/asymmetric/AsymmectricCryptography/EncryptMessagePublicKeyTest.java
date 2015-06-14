package unit.com.bitdubai.fermat_api.layer._1_definition.crypto.asymmetric.AsymmectricCryptography;

import static org.fest.assertions.api.Assertions.*;

import java.math.BigInteger;

import org.junit.Test;

import com.bitdubai.fermat_api.layer._1_definition.crypto.asymmetric.AsymmectricCryptography;

public class EncryptMessagePublicKeyTest extends AsymmetricCryptographyUnitTest{
	
	@Test
	public void EncryptMessagePublicKey_ValidParameters_NotNull() {
		assertThat(AsymmectricCryptography.encryptMessagePublicKey(testPlainMessage, testPublicKey)).isNotNull();		
	}
	
	@Test
	public void EncryptMessagePublicKey_ValidParameters_BigIntegerBytesValue() {
		BigInteger encryptedMessage = new BigInteger(AsymmectricCryptography.encryptMessagePublicKey(testPlainMessage, testPublicKey),16);
		assertThat(encryptedMessage).isNotNull();
	}
	
	@Test
	public void EncryptMessagePublicKey_ValidParameters_DifferentThanPlainMessage() {
		String encryptedMessage = AsymmectricCryptography.encryptMessagePublicKey(testPlainMessage, testPublicKey);
		assertThat(encryptedMessage).isNotEqualTo(testPlainMessage);
	}
	
	@Test
	public void EncryptMessagePublicKey_MultipleExecutions_DifferentEncryptedMessages() {
		String encryptedMessage1 = AsymmectricCryptography.encryptMessagePublicKey(testPlainMessage, testPublicKey);
		String encryptedMessage2 = AsymmectricCryptography.encryptMessagePublicKey(testPlainMessage, testPublicKey);
		assertThat(encryptedMessage1).isNotEqualTo(encryptedMessage2);		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void EncryptMessagePublicKey_NullMessage_ThrowIllegalArgumentException() {
		AsymmectricCryptography.encryptMessagePublicKey(null, testPublicKey);		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void EncryptMessagePublicKey_EmptyMessage_ThrowIllegalArgumentException() {
		AsymmectricCryptography.encryptMessagePublicKey("", testPublicKey);		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void EncryptMessagePublicKey_NullPublicKey_ThrowIllegalArgumentException() {
		AsymmectricCryptography.encryptMessagePublicKey(testPlainMessage, null);		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void EncryptMessagePublicKey_EmptyPublicKey_ThrowIllegalArgumentException() {
		AsymmectricCryptography.encryptMessagePublicKey(testPlainMessage, "");		
	}

}
