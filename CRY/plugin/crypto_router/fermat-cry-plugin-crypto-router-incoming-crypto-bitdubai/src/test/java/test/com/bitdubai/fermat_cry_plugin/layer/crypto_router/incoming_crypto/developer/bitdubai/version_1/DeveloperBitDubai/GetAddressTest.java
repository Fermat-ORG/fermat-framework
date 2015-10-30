package test.com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.DeveloperBitDubai;

import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.DeveloperBitDubaiOld;

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
        DeveloperBitDubaiOld developerBitDubaiOld = new DeveloperBitDubaiOld();
        Assert.assertNotNull(developerBitDubaiOld.getPlugin());
    }
    @Test
    public void getAddressTest_thisMethodIsCalledNoAddressSet_returnsNull() throws Exception{

        DeveloperBitDubaiOld testDeveloperBitDubaiOld =new DeveloperBitDubaiOld();
        String address= testDeveloperBitDubaiOld.getAddress();
        Assertions.assertThat(address)
                //.isEqualTo("13gpMizSNvQCbJzAPyGCUnfUGqFD8ryzcv");
                .isNotNull();
        Assert.assertEquals(testDeveloperBitDubaiOld.getAddress(), "13gpMizSNvQCbJzAPyGCUnfUGqFD8ryzcv");
    }
}
