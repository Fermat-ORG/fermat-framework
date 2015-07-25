package unit.com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.ExtraUserUserAddonRoot;

import com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.ExtraUserUserAddonRoot;
import java.util.UUID;
import org.junit.Test;


/**
 * Created by Manuel Perez on 23/07/15.
 */
public class SetIdTest {

    ExtraUserUserAddonRoot testExtraUserUserAddonRoot=new ExtraUserUserAddonRoot();

    @Test
    public void setPluginId_setValidRandomPluginId_throwsNoExceptions(){

        UUID pluginId=UUID.randomUUID();
        testExtraUserUserAddonRoot.setId(pluginId);

    }

}
