package unit.com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.DeveloperBitDubai;

import com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.DeveloperBitDubai;

import org.fest.assertions.api.Assertions;
import org.junit.Test;

/**
 * Created by Manuel Perez on 27/07/15.
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
