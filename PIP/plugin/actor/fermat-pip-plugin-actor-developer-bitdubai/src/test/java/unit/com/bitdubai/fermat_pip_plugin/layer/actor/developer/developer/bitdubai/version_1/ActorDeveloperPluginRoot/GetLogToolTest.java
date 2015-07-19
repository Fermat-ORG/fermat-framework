package unit.com.bitdubai.fermat_pip_plugin.layer.actor.developer.developer.bitdubai.version_1.ActorDeveloperPluginRoot;

import com.bitdubai.fermat_pip_api.layer.pip_actor.developer.LogTool;
import com.bitdubai.fermat_pip_api.layer.pip_actor.exception.CantGetLogTool;
import com.bitdubai.fermat_pip_plugin.layer.actor.developer.developer.bitdubai.version_1.ActorDeveloperPluginRoot;

import org.fest.assertions.api.Assertions;
import org.junit.Test;

/**
 * Created by jorgegonzalez on 2015.07.03..
 */
public class GetLogToolTest {

    @Test
    public void GetLogTool_NothingSet_ReturnLogTool() throws Exception{
        ActorDeveloperPluginRoot testPluginRoot = new ActorDeveloperPluginRoot();

        LogTool check = testPluginRoot.getLogTool();

        Assertions.assertThat(check).isNotNull();

    }
}
