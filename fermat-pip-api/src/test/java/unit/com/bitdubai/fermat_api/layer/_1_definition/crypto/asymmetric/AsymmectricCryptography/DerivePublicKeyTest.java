package unit.com.bitdubai.fermat_api.layer._1_definition.crypto.asymmetric.AsymmectricCryptography;

import static org.fest.assertions.api.Assertions.*;

import java.math.BigInteger;

import org.junit.Test;

import com.bitdubai.fermat_api.layer._1_definition.crypto.asymmetric.AsymmectricCryptography;

public class DerivePublicKeyTest extends AsymmetricCryptographyUnitTest {
	
	
	@Test
	public void DerivePublicKey_ValidPrivateKey_NotNull() {
		assertThat(AsymmectricCryptography.derivePublicKey(testPrivateKey)).isNotNull();		
	}
	
	@Test
	public void DerivePublicKey_ValidPrivateKey_BigIntegerValue() {
		BigInteger publicKey = new BigInteger(AsymmectricCryptography.derivePublicKey(testPrivateKey),16);
		assertThat(publicKey).isNotNull();
	}	
	
	@Test(expected=NumberFormatException.class)
	public void DerivePublicKey_ValidPrivateKey_BigIntegerIsHexValue() {
		new BigInteger(AsymmectricCryptography.derivePublicKey(testPrivateKey));		
	}
	
	@Test
	public void DerivePublicKey_ValidPrivateKey_DerivedValue() {
		String publicKey = AsymmectricCryptography.derivePublicKey(testPrivateKey);
		assertThat(publicKey).isEqualTo(testPublicKey);		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void DerivePublicKey_NullPrivateKey_ThrowIllegalArgumentException(){
		AsymmectricCryptography.derivePublicKey(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void DerivePublicKey_EmptyPrivateKey_ThrowIllegalArgumentException(){
		AsymmectricCryptography.derivePublicKey("");
	}
}
