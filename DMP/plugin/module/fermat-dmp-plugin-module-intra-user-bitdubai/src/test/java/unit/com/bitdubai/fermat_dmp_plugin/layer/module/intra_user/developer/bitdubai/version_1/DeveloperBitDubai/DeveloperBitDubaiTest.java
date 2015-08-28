package unit.com.bitdubai.fermat_dmp_plugin.layer.module.intra_user.developer.bitdubai.version_1.DeveloperBitDubai;

import org.junit.Before;
import org.junit.Test;

import com.bitdubai.fermat_dmp_plugin.layer.module.intra_user.developer.bitdubai.DeveloperBitDubai;
import com.bitdubai.fermat_dmp_plugin.layer.module.intra_user.developer.bitdubai.version_1.IntraUserModulePluginRoot;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by natalia on 28/08/15.
 */
public class DeveloperBitDubaiTest {

    DeveloperBitDubai developTest;

    @Before
    public void setUpVariable1(){
        developTest = new DeveloperBitDubai();
    }

    @Test
    public void testGetPlugin() {
        assertThat(developTest.getPlugin()).isInstanceOf(IntraUserModulePluginRoot.class);
    }
}
