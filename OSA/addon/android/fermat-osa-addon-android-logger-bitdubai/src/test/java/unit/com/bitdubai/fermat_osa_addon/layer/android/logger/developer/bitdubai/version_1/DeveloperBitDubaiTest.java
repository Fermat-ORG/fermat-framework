package unit.com.bitdubai.fermat_osa_addon.layer.android.logger.developer.bitdubai.version_1;

import com.bitdubai.fermat_osa_addon.layer.android.logger.developer.bitdubai.DeveloperBitDubai;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Nerio on 05/08/15.
 */
public class DeveloperBitDubaiTest {

    @Test
    public void constructorTest (){
        DeveloperBitDubai developerBitDubai = new DeveloperBitDubai();
        Assert.assertNotNull(developerBitDubai.getAddon());
    }
}
