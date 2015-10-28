package unit.com.bitdubai.fermat_pip_plugin.layer.actor.developer.developer.bitdubai.version_1.ActorDeveloperPluginRoot;

import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_plugin.layer.actor.developer.developer.bitdubai.version_1.ActorDeveloperPluginRoot;
import com.googlecode.catchexception.CatchException;

import org.fest.assertions.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Created by jorgegonzalez on 2015.07.03..
 */
@RunWith(MockitoJUnitRunner.class)
public class SetErrorManagerTest {

    @Mock
    private ErrorManager errorManager;


    @Test
    public void Start_SuccessFully_GetStatusIsStarted(){
        ActorDeveloperPluginRoot testPluginRoot = new ActorDeveloperPluginRoot();

        CatchException.catchException(testPluginRoot).setErrorManager(errorManager);

        Assertions.assertThat(CatchException.caughtException()).isNull();

    }

    @Test
    public void Set_Null_ThrowException(){
        ActorDeveloperPluginRoot testPluginRoot = new ActorDeveloperPluginRoot();

        CatchException.catchException(testPluginRoot).setErrorManager(null);

        Assertions.assertThat(CatchException.caughtException()).isInstanceOf(IllegalArgumentException.class);

    }
}
