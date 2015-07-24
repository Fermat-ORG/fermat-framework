package unit.com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.ExtraUserUserAddonRoot;

import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.ExtraUserUserAddonRoot;

import org.junit.Test;

/**
 * Created by Manuel Perez on 23/07/15.
 */
public class SetPluginDatabaseSystemTest {

    ExtraUserUserAddonRoot testExtraUserUserAddonRoot=new ExtraUserUserAddonRoot();
    PluginDatabaseSystem testPluginDatabaseSystem;

    @Test
    public void setPluginDatabasSystemTest_setValidPluginDatabaseSystem_throwsNoExceptions() throws Exception {

        testExtraUserUserAddonRoot.setPluginDatabaseSystem(testPluginDatabaseSystem);
    }

}
