package unit.com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.ExtraUserDatabaseFactory;

import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.ExtraUserDatabaseFactory;

import org.junit.Test;
import org.mockito.Mock;

/**
 * Created by Manuel Perez on 30/07/15.
 */
public class SetPluginDatabaseSystemTest {

    @Mock
    PluginDatabaseSystem pluginDatabaseSystem;

    ExtraUserDatabaseFactory testExtraUserDatabaseFactory=new ExtraUserDatabaseFactory();

    @Test
    public void setPluginDatabaseSystemTest_setMockPluginDatabaseSystem_throwsNoExceptions(){

        testExtraUserDatabaseFactory.setPluginDatabaseSystem(pluginDatabaseSystem);

    }

}
