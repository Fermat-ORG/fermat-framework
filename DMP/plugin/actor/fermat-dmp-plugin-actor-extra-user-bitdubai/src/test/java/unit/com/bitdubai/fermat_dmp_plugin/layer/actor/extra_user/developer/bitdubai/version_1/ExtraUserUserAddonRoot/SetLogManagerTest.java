package unit.com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.ExtraUserUserAddonRoot;

import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.ExtraUserUserAddonRoot;

import org.junit.Test;


/**
 * Created by Manuel Perez on 24/07/15.
 */
public class SetLogManagerTest {

    LogManager mockLogManager;
    ExtraUserUserAddonRoot testExtraUserUserAddonRoot=new ExtraUserUserAddonRoot();

    @Test
    public void setLogManager_setValidLogManager_throwsNoExceptions(){

        testExtraUserUserAddonRoot.setLogManager(mockLogManager);

    }

}
