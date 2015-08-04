package test.com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.DeveloperBitDubai;

import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.DeveloperBitDubai;

import org.fest.assertions.api.Assertions;
import org.junit.Test;
/**
 * Created by Franklin Marcano on 03/08/15.
 */
public class GetAddressTest {
    @Test
    public void getAddressTest_thisMethodIsCalledNoAddressSet_returnsNull() throws Exception{

        DeveloperBitDubai testDeveloperBitDubai=new DeveloperBitDubai();
        String address=testDeveloperBitDubai.getAddress();
        Assertions.assertThat(address)
                .isNull();

    }
}
