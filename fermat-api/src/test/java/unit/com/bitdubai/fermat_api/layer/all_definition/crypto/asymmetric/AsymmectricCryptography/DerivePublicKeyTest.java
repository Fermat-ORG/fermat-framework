package unit.com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmectricCryptography;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;

import org.junit.Test;

import java.math.BigInteger;

import static org.fest.assertions.api.Assertions.assertThat;

public class DerivePublicKeyTest extends AsymmetricCryptographyUnitTest {
	
	
	@Test
	public void DerivePublicKey_ValidPrivateKey_NotNull() {
		assertThat(AsymmetricCryptography.derivePublicKey(testPrivateKey)).isNotNull();
	}
	
	@Test
	public void DerivePublicKey_ValidPrivateKey_BigIntegerValue() {
		BigInteger publicKey = new BigInteger(AsymmetricCryptography.derivePublicKey(testPrivateKey),16);
		assertThat(publicKey).isNotNull();
	}	
	
	@Test(expected=NumberFormatException.class)
	public void DerivePublicKey_ValidPrivateKey_BigIntegerIsHexValue() {
		new BigInteger(AsymmetricCryptography.derivePublicKey(testPrivateKey));
	}
	
	@Test
	public void DerivePublicKey_ValidPrivateKey_DerivedValue() {
		String publicKey = AsymmetricCryptography.derivePublicKey(testPrivateKey);
		assertThat(publicKey).isEqualTo(testPublicKey);		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void DerivePublicKey_NullPrivateKey_ThrowIllegalArgumentException(){
		AsymmetricCryptography.derivePublicKey(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void DerivePublicKey_EmptyPrivateKey_ThrowIllegalArgumentException(){
		AsymmetricCryptography.derivePublicKey("");
	}
}
