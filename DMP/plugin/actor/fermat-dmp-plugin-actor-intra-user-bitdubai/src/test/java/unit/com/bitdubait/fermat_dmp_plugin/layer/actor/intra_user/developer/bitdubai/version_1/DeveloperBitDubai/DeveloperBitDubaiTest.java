package unit.com.bitdubait.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.DeveloperBitDubai;

import com.bitdubai.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.DeveloperBitDubai;
import com.bitdubai.fermat_dmp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.IntraUserActorPluginRoot;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by natalia on 24/08/15.
 */
public class DeveloperBitDubaiTest extends TestCase {

    DeveloperBitDubai developTest;

    @Before
    public void setUpVariable1(){
        developTest = new DeveloperBitDubai();
    }

    @Test
    public void getPluging() {
        assertThat(developTest.getPlugin()).isInstanceOf(IntraUserActorPluginRoot.class);
    }
}
