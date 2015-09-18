package unit.com.bitdubai.fermat_pip_plugin.layer.actor.developer.developer.bitdubai.version_1.ActorDeveloperPluginRoot;

import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_pip_plugin.layer.actor.developer.developer.bitdubai.version_1.ActorDeveloperPluginRoot;


import org.fest.assertions.api.Assertions;
import org.junit.Test;

/**
 * Created by jorgegonzalez on 2015.07.03..
 */
public class StartTest {

    @Test
    public void Start_SuccessFully_GetStatusIsStarted(){
        ActorDeveloperPluginRoot testPluginRoot = new ActorDeveloperPluginRoot();

        testPluginRoot.start();

        Assertions.assertThat(testPluginRoot.getStatus()).isEqualTo(ServiceStatus.STARTED);

    }
}
