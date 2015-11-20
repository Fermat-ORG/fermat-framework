package unit.com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmectricCryptography;

import static org.fest.assertions.api.Assertions.*;

import java.math.BigInteger;

import org.junit.Test;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;

public class CreatePrivateKeyTest extends AsymmetricCryptographyUnitTest {

	@Test
	public void CreatePrivateKey_Default_NotNull() {
		assertThat(AsymmetricCryptography.createPrivateKey()).isNotNull();
	}
	
	@Test
	public void CreatePrivateKey_Default_BigIntegerValue(){		
		BigInteger privateKey = new BigInteger(AsymmetricCryptography.createPrivateKey(),16);
		assertThat(privateKey).isNotNull();
	}
	
	@Test(expected=NumberFormatException.class)
	public void CreatePrivateKey_Default_BigIntegerIsHexValue(){		
		new BigInteger(AsymmetricCryptography.createPrivateKey());
	}
	
	@Test
	public void CreatePrivateKey_Repeated_ValuesNotEquals(){		
		BigInteger privateKey1 = new BigInteger(AsymmetricCryptography.createPrivateKey(),16);
		BigInteger privateKey2 = new BigInteger(AsymmetricCryptography.createPrivateKey(),16);
		assertThat(privateKey1).isNotEqualTo(privateKey2);
	}

}
