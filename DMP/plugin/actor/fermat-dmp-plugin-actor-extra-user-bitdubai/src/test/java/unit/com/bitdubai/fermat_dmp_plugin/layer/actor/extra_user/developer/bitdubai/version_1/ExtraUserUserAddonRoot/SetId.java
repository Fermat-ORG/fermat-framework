package unit.com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.ExtraUserUserAddonRoot;

import com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.ExtraUserUserAddonRoot;

import org.junit.Test;

import java.util.UUID;

/**
 * Created by Manuel Perez on 23/07/15.
 */
public class SetId {

    ExtraUserUserAddonRoot testExtraUserUserAddonRoot=new ExtraUserUserAddonRoot();

    @Test
    public void setPluginId_setValidRandomPluginId_throwsNoExceptions(){

        UUID pluginId=UUID.randomUUID();
        testExtraUserUserAddonRoot.setId(pluginId);

    }

}
