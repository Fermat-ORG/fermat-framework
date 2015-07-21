package unit.com.bitdubai.fermat_p2p_api.layer.all_definition.communication.util.DataCompressor;

import static org.fest.assertions.api.Assertions.*;

import org.junit.Before;
import org.junit.Test;

import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.util.DataCompressor;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPPacket;

public class EncodeDecodeTest {
	
	private String testString;
	
	@Before
	public void prepareString(){
		StringBuffer buffer = new StringBuffer();
		buffer.append("04D397A340C0B530579EF9BA6C87E22F0F05A2F957DD33E2D684358D2413CCA008924F76C0CECB3C7B2898819EA35366F009AC5DCE3A80F379AA69EC78C10D443F");
		buffer.append("047F9F57BDE5771B4A9DA604B1CA138AA3B593B7EFBC3C890E384CF8B3EB3080E70DDA5130A26768DDEF30379AA6F9B924339407B0D429964503372CB71D3328D0");
		buffer.append("CONNECTION_REQUEST");
		buffer.append("0432ef4a0ca9f0ec3e081d5e842bd3f88d59287281843f5816520f3bbeffc4c72867ea1cffb6c435263cc693b1a598c8e3148c2f0c756d97c66145a9673537917bc8152ff75a3a436f55cabbb6989c4a20c799bc52b8282bee69149e119f9e");
		buffer.append("e0a717243fe6be8751a73ecd220a39950f4682eed8cb8311de1a566c2397c660 248d6dd1027feeb9ea429894b42a76f78f844cb14f8ef11fe373d9d6e7a2f79");
		testString = buffer.toString();
	}
	
	@Test
	public void Encode_ConverStringToByteArray() throws Exception {
		byte[] testEncode = DataCompressor.encode(testString);
		assertThat(testEncode).isNotNull();
		//assertThat(testEncode.length).isLessThan(testString.getBytes().length);
	}
	
	@Test
	public void	Decode_ConverByteArrayToString() throws Exception {
		byte[] testEncode = DataCompressor.encode(testString);
		String testDecode = DataCompressor.decode(testEncode);
		assertThat(testDecode).isEqualTo(testString);
	}

}
