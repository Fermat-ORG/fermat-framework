package unit.com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.DeveloperBitDubai;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.DeveloperBitDubai;

import org.fest.assertions.api.Assertions;
import org.junit.Test;

/**
 * Created by Manuel Perez on 27/07/15.
 */
public class GetPluginTest {

    @Test
    public void getPluginTest_objectInstantiated_returnsValidPlugin() throws Exception{

        DeveloperBitDubai testDeveloperBitDubai=new DeveloperBitDubai();
        Assertions.assertThat(testDeveloperBitDubai.getPlugin())
                .isNotNull()
                .isInstanceOf(Plugin.class);

    }

}
