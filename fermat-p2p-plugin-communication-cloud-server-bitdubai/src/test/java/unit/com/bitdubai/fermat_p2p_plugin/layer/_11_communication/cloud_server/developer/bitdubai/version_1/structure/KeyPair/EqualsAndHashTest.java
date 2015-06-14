package unit.com.bitdubai.fermat_p2p_plugin.layer._11_communication.cloud_server.developer.bitdubai.version_1.structure.KeyPair;

import static org.fest.assertions.api.Assertions.*;

import org.junit.Test;

import com.bitdubai.fermat_p2p_plugin.layer._11_communication.cloud_server.developer.bitdubai.version_1.structure.ECCKeyPair;

public class EqualsAndHashTest {
	
	private String testPrivateKey = "18E14A7B6A307F426A94F8114701E7C8E774E7F9A47E2C2035DB29A206321725";

	@Test
	public void SameValues_IsEqual_SameHash(){
		ECCKeyPair testPair1 = new ECCKeyPair(testPrivateKey);
		ECCKeyPair testPair2 = new ECCKeyPair(testPrivateKey);
		
		assertThat(testPair1).isEqualTo(testPair2);
		assertThat(testPair1.hashCode()).isEqualTo(testPair2.hashCode());
	}
	
	@Test
	public void DifferentValues_NotEqual_DifferentHash(){
		ECCKeyPair testPair1 = new ECCKeyPair(testPrivateKey);
		ECCKeyPair testPair2 = new ECCKeyPair(testPrivateKey.replace("C", "A"));
		
		assertThat(testPair1).isNotEqualTo(testPair2);
		assertThat(testPair1.hashCode()).isNotEqualTo(testPair2.hashCode());
	}
	
	@Test
	public void NullValue_NotEquals(){
		ECCKeyPair testPair1 = new ECCKeyPair(testPrivateKey);
		ECCKeyPair testPair2 = null;
		assertThat(testPair1).isNotEqualTo(testPair2);
	}

}
