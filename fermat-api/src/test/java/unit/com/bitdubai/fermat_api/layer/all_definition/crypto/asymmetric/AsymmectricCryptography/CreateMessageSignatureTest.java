package unit.com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmectricCryptography;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;

import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

public class CreateMessageSignatureTest extends AsymmetricCryptographyUnitTest {

	@Test
	public void CreateMessageSignature_ValidParameters_NotNull() {
		assertThat(AsymmetricCryptography.createMessageSignature(testEncryptedMessage, testPrivateKey)).isNotNull();
	}
	
	@Test
	public void CreateMessageSignature_ConsecutiveSignatures_NotEquals() {
		String signature1 = AsymmetricCryptography.createMessageSignature(testEncryptedMessage, testPrivateKey);
		String signature2 = AsymmetricCryptography.createMessageSignature(testEncryptedMessage, testPrivateKey);
		assertThat(signature1).isNotEqualTo(signature2);		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void CreateMessageSignature_NullMessage_ThrowIllegalArgumentException() {
		AsymmetricCryptography.createMessageSignature(null, testPrivateKey);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void CreateMessageSignature_EmptyMessage_ThrowIllegalArgumentException() {
		AsymmetricCryptography.createMessageSignature(null, testPrivateKey);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void CreateMessageSignature_NullPrivateKey_ThrowIllegalArgumentException() {
		AsymmetricCryptography.createMessageSignature(testEncryptedMessage, null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void CreateMessageSignature_EmptyPrivateKey_ThrowIllegalArgumentException() {
		AsymmetricCryptography.createMessageSignature(testEncryptedMessage, "");
	}
	
}
