package test.com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.DeveloperBitDubai;

import com.bitdubai.fermat_api.layer.all_definition.enums.TimeFrequency;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.DeveloperBitDubaiOld;

import org.fest.assertions.api.Assertions;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by Franklin Marcano 03/08/15.
 */
public class GetTimePeriodTest {
    DeveloperBitDubaiOld testDeveloperBitDubaiOld =new DeveloperBitDubaiOld();

    @Ignore
    @Test
    public void getTimePeriodTest_thisMethodIsCalled_returnsNull() throws Exception{

        TimeFrequency timeFrequency= testDeveloperBitDubaiOld.getTimePeriod();
        Assertions.assertThat(timeFrequency)
                //.isNull();
                .isNotNull();

    }
}
