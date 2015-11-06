package unit.com.bitdubai.fermat_ccp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.IntraWalletUserActorPluginRoot;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_ccp_plugin.layer.actor.intra_user.developer.bitdubai.version_1.IntraWalletUserActorPluginRoot;

import org.junit.Test;

import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by natalia on 24/08/15.
 */
public class ClassesFullPathTest {
    private IntraWalletUserActorPluginRoot pluginRoot;

    @Test
    public void getClassTest() throws CantStartPluginException {
        pluginRoot = new IntraWalletUserActorPluginRoot();
        assertThat(pluginRoot.getClassesFullPath()).isInstanceOf(List.class);
    }
}
