package unit.com.bitdubait.fermat_ccp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.IntraUserActorPluginRoot;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_ccp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.IntraUserActorPluginRoot;

import org.junit.Test;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by natalia on 24/08/15.
 */
public class ClassesFullPathTest {
    private IntraUserActorPluginRoot pluginRoot;

    @Test
    public void getClassTest() throws CantStartPluginException {
        pluginRoot = new IntraUserActorPluginRoot();
        assertThat(pluginRoot.getClassesFullPath()).isInstanceOf(List.class);
    }
}
