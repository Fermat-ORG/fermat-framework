package test.com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.DeveloperBitDubai;

import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.DeveloperBitDubai;

import org.fest.assertions.api.Assertions;
import org.junit.Ignore;
import org.junit.Assert;
import org.junit.Test;
/**
 * Created by Franklin Marcano on 03/08/15.
 */
public class GetAddressTest {
    @Ignore
    @Test
    public void constructorTest (){
        DeveloperBitDubai developerBitDubai = new DeveloperBitDubai();
        Assert.assertNotNull(developerBitDubai.getPlugin());
    }
    @Test
    public void getAddressTest_thisMethodIsCalledNoAddressSet_returnsNull() throws Exception{

        DeveloperBitDubai testDeveloperBitDubai=new DeveloperBitDubai();
        String address=testDeveloperBitDubai.getAddress();
        Assertions.assertThat(address)
                //.isEqualTo("13gpMizSNvQCbJzAPyGCUnfUGqFD8ryzcv");
                .isNotNull();
        Assert.assertEquals(testDeveloperBitDubai.getAddress(), "13gpMizSNvQCbJzAPyGCUnfUGqFD8ryzcv");
    }
}
