package unit.com.bitdubai.fermat_api.layer._1_definition.crypto.asymmetric.AsymmectricCryptography;

import static org.fest.assertions.api.Assertions.*;

import org.junit.Test;

import com.bitdubai.fermat_api.layer._1_definition.crypto.asymmetric.AsymmectricCryptography;

public class CreateMessageSignatureTest extends AsymmetricCryptographyUnitTest {

	@Test
	public void CreateMessageSignature_ValidParameters_NotNull() {
		assertThat(AsymmectricCryptography.createMessageSignature(testEncryptedMessage, testPrivateKey)).isNotNull();
	}
	
	@Test
	public void CreateMessageSignature_ConsecutiveSignatures_NotEquals() {
		String signature1 = AsymmectricCryptography.createMessageSignature(testEncryptedMessage, testPrivateKey);
		String signature2 = AsymmectricCryptography.createMessageSignature(testEncryptedMessage, testPrivateKey);
		assertThat(signature1).isNotEqualTo(signature2);		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void CreateMessageSignature_NullMessage_ThrowIllegalArgumentException() {
		AsymmectricCryptography.createMessageSignature(null, testPrivateKey);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void CreateMessageSignature_EmptyMessage_ThrowIllegalArgumentException() {
		AsymmectricCryptography.createMessageSignature(null, testPrivateKey);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void CreateMessageSignature_NullPrivateKey_ThrowIllegalArgumentException() {
		AsymmectricCryptography.createMessageSignature(testEncryptedMessage, null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void CreateMessageSignature_EmptyPrivateKey_ThrowIllegalArgumentException() {
		AsymmectricCryptography.createMessageSignature(testEncryptedMessage, "");
	}
	
}
