package test.com.bitdubai.fermat_dmp_plugin.layer.module.wallet_store.developer.bitdubai.version_1;

import com.bitdubai.fermat_dmp_plugin.layer.module.wallet_store.developer.bitdubai.DeveloperBitDubai;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Nerio on 20/08/15.
 */
public class DeveloperBitDubaiTest {

    @Test
    public void constructorTest (){
        DeveloperBitDubai developerBitDubai = new DeveloperBitDubai();
        Assert.assertNotNull(developerBitDubai.getPlugin());
    }
}