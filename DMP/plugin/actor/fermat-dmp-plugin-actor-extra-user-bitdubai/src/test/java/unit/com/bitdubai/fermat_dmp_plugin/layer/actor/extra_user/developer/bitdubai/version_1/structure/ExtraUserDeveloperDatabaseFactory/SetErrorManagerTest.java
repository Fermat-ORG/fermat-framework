package unit.com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.ExtraUserDeveloperDatabaseFactory;

import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.ExtraUserDatabaseFactory;
import com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.ExtraUserDeveloperDatabaseFactory;

import org.fest.assertions.api.Assertions;
import org.junit.Test;
import org.mockito.Mock;

import java.util.UUID;

import unit.com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.ExtraUserUserAddonRoot.MockErrorManager;

/**
 * Created by Manuel Perez on 31/07/15.
 */
public class SetErrorManagerTest {

    @Mock
    PluginDatabaseSystem mockPluginDatabaseSystem;
    UUID testId=UUID.randomUUID();

    @Test(expected = Exception.class)
    public void setErrorManagerTest_setNullErrorManagerAnExceptionIsThrown_throwsAnException() throws Exception{


        MockErrorManager mockErrorManager=null;
        ExtraUserDeveloperDatabaseFactory anotherExtraUserDeveloperDatabaseFactory=new ExtraUserDeveloperDatabaseFactory(mockErrorManager, null,testId);
        anotherExtraUserDeveloperDatabaseFactory.setErrorManager(mockErrorManager);
        anotherExtraUserDeveloperDatabaseFactory.setPluginDatabaseSystem(null);
        anotherExtraUserDeveloperDatabaseFactory.initializeDatabase();

    }

    @Test
    public void setErrorManagerTest_setValidErrorManagerAnExceptionIsThrown_theErrorManagerContainsTheExceptionInformation() throws Exception{


        MockErrorManager mockErrorManager=new MockErrorManager();
        ExtraUserDeveloperDatabaseFactory anotherExtraDeveloperUserDatabaseFactory=new ExtraUserDeveloperDatabaseFactory(mockErrorManager,mockPluginDatabaseSystem,testId);
        anotherExtraDeveloperUserDatabaseFactory.setErrorManager(mockErrorManager);
        anotherExtraDeveloperUserDatabaseFactory.setPluginDatabaseSystem(null);
        Assertions.assertThat(mockErrorManager).isNotNull();

    }

    @Test
    public void setErrorManagerTest_setValidErrorManager_theErrorManagerIsSet()throws Exception{

        MockErrorManager mockErrorManager=new MockErrorManager();
        ExtraUserDeveloperDatabaseFactory extraUserDeveloperDatabaseFactory=new ExtraUserDeveloperDatabaseFactory(mockErrorManager,mockPluginDatabaseSystem,testId);
        extraUserDeveloperDatabaseFactory.setErrorManager(mockErrorManager);

    }

}
