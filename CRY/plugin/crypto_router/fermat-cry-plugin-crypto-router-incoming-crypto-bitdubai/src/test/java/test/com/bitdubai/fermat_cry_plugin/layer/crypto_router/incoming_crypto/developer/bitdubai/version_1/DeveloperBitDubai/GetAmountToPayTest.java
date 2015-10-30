package test.com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.DeveloperBitDubai;

import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.DeveloperBitDubaiOld;

import org.fest.assertions.api.Assertions;
import org.junit.Test;

/**
 * Created by Franklin Marcano 03/08/15.
 */
public class GetAmountToPayTest {
    @Test
    public void getAmountToPayTest_thisMethodIsCalled_returnsANumber() throws Exception{

        DeveloperBitDubaiOld testDeveloperBitDubaiOld =new DeveloperBitDubaiOld();
        Number resultNumber= testDeveloperBitDubaiOld.getAmountToPay();
        Assertions.assertThat(resultNumber)
                .isNotNull()
                .isInstanceOf(Number.class);

    }
}
