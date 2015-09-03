package unit.com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.ExtraUserActorPluginRoot;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.ExtraUserActorPluginRoot;

import org.fest.assertions.api.Assertions;
import org.junit.Test;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by natalia on 03/09/15.
 */
public class GetLogLevelByClassTest {
    private ExtraUserActorPluginRoot pluginRoot;

    @Test
    public void getLogLevelByClassTest() throws CantStartPluginException {


        pluginRoot = new ExtraUserActorPluginRoot();
        assertThat(pluginRoot.getLogLevelByClass("com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.ExtraUserActorPluginRoot")).isNull();
       // Assertions.assertThat(logLevel).isNotNull();

    }
}

