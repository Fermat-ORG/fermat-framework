package unit.com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.DeveloperBitDubai;

import com.bitdubai.fermat_api.layer.all_definition.enums.TimeFrequency;
import com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.DeveloperBitDubai;

import org.fest.assertions.api.Assertions;
import org.junit.Test;

/**
 * Created by Manuel Perez on 27/07/15.
 */
public class GetTimePeriodTest {

    DeveloperBitDubai testDeveloperBitDubai=new DeveloperBitDubai();

    @Test
    public void getTimePeriodTest_thisMethodIsCalled_returnsNull() throws Exception{

        TimeFrequency timeFrequency=testDeveloperBitDubai.getTimePeriod();
        Assertions.assertThat(timeFrequency)
                .isNull();

    }

}
