package unit.com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.ExtraUserActorPluginRoot;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_ccp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.ExtraUserActorPluginRoot;

import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by natalia on 03/09/15.
 */
public class GetLogLevelByClassTest {
    private ExtraUserActorPluginRoot pluginRoot;

    @Test
    public void getLogLevelByClassTest() throws CantStartPluginException {

        pluginRoot = new ExtraUserActorPluginRoot();

        assertThat(pluginRoot.getLogLevelByClass("ExtraUserActorPluginRoot")).isNull();
    }
}

