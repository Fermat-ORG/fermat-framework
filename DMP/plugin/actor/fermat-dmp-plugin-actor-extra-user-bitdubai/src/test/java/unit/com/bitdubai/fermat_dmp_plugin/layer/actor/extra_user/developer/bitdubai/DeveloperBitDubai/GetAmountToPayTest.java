package unit.com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.DeveloperBitDubai;

import com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.DeveloperBitDubai;

import org.fest.assertions.api.Assertions;
import org.junit.Test;

/**
 * Created by Manuel Perez on 27/07/15.
 */
public class GetAmountToPayTest {

    @Test
    public void getAmountToPayTest_thisMethodIsCalled_returnsANumber() throws Exception{

        DeveloperBitDubai testDeveloperBitDubai=new DeveloperBitDubai();
        Number resultNumber=testDeveloperBitDubai.getAmountToPay();
        Assertions.assertThat(resultNumber)
                .isNotNull()
                .isInstanceOf(Number.class);

    }

}
