package unit.com.bitdubai.fermat_dmp_plugin.layer.identity.intra_user.developer.bitdubai.DeveloperBitDubai;

import com.bitdubai.fermat_dmp_plugin.layer.identity.intra_user.developer.bitdubai.DeveloperBitDubai;
import com.bitdubai.fermat_dmp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.IntraUserIdentityPluginRoot;

import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by angel on 18/8/15.
 */

public class DeveloperBitDubaiTest {
    DeveloperBitDubai developTest;

    @Before
    public void setUpVariable1(){
        developTest = new DeveloperBitDubai();
    }

    @Test
    public void GetPluging() {
        assertThat(developTest.getPlugin()).isInstanceOf(IntraUserIdentityPluginRoot.class);
    }
}
