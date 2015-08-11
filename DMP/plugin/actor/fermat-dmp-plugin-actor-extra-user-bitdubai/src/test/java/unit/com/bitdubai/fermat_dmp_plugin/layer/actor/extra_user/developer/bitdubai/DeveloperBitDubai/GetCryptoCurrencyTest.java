package unit.com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.DeveloperBitDubai;

import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.DeveloperBitDubai;

import org.fest.assertions.api.Assertions;
import org.junit.Test;

/**
 * Created by Manuel Perez on 27/07/15.
 */
public class GetCryptoCurrencyTest {

    DeveloperBitDubai testDeveloperBitDubai=new DeveloperBitDubai();

    @Test
    public void getCryptoCurrencyTest_thisMethodIsCalled_returnsNull() throws Exception{

        CryptoCurrency cryptoCurrency=testDeveloperBitDubai.getCryptoCurrency();
        Assertions.assertThat(cryptoCurrency)
                .isNull();

    }

}
