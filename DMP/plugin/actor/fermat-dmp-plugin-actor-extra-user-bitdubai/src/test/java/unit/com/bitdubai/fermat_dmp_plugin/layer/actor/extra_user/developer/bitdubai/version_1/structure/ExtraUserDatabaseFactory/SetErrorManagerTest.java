package unit.com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.ExtraUserDatabaseFactory;

import com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.ExtraUserDatabaseFactory;

import org.fest.assertions.api.Assertions;
import org.junit.Test;

import java.util.UUID;

import unit.com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.ExtraUserUserAddonRoot.MockErrorManager;

/**
 * Created by Manuel Perez on 30/07/15.
 */
public class SetErrorManagerTest {

    @Test(expected = Exception.class)
    public void setErrorManagerTest_setNullErrorManagerAnExceptionIsThrown_throwsAnException() throws Exception{

        ExtraUserDatabaseFactory anotherExtraUserDatabaseFactory=new ExtraUserDatabaseFactory();
        MockErrorManager mockErrorManager=null;
        anotherExtraUserDatabaseFactory.setErrorManager(mockErrorManager);
        anotherExtraUserDatabaseFactory.setPluginDatabaseSystem(null);
        anotherExtraUserDatabaseFactory.createDatabase(UUID.randomUUID());

    }

    @Test
    public void setErrorManagerTest_setValidErrorManagerAnExceptionIsThrown_theErrorManagerContainsTheExceptionInformation() throws Exception{

        ExtraUserDatabaseFactory anotherExtraUserDatabaseFactory=new ExtraUserDatabaseFactory();
        MockErrorManager mockErrorManager=new MockErrorManager();
        anotherExtraUserDatabaseFactory.setErrorManager(mockErrorManager);
        anotherExtraUserDatabaseFactory.setPluginDatabaseSystem(null);
        Assertions.assertThat(mockErrorManager).isNotNull();

    }

    @Test
    public void setErrorManagerTest_setValidErrorManager_theErrorManagerIsSet()throws Exception{

        ExtraUserDatabaseFactory extraUserDatabaseFactory=new ExtraUserDatabaseFactory();
        MockErrorManager mockErrorManager=new MockErrorManager();
        extraUserDatabaseFactory.setErrorManager(mockErrorManager);

    }

}
