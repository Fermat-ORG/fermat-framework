package test.com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.DeveloperBitDubai;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.DeveloperBitDubai;

import org.fest.assertions.api.Assertions;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by Franklin Marcano 03/08/15.
 */
public class GetCryptoCurrencyTest {
    DeveloperBitDubai testDeveloperBitDubai=new DeveloperBitDubai();

    @Ignore
    @Test
    public void getCryptoCurrencyTest_thisMethodIsCalled_returnsNull() throws Exception{

        CryptoCurrency cryptoCurrency=testDeveloperBitDubai.getCryptoCurrency();
        Assertions.assertThat(cryptoCurrency)
                //.isNull();
                .isNotNull();

    }
}
