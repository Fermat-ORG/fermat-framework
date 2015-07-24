package unit.com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.ExtraUserUserAddonRoot;

import com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.ExtraUserUserAddonRoot;

import org.junit.Test;

import java.util.UUID;

/**
 * Created by Manuel Perez on 23/07/15.
 */
public class SetId {

    ExtraUserUserAddonRoot testExtraUserUserAddonRoot=new ExtraUserUserAddonRoot();

    //@Ignore
    @Test
    public void setPluginId_setInvalidPluginId_throwsExceptions(){

        UUID pluginId=INVALIDPLUGIN
        testExtraUserUserAddonRoot.setPluginId(pluginId);

    }

    @Test
    public void setPluginId_setValidPluginId_throwsNoExceptions(){

        UUID pluginId=VALIDPLUGIN
        testExtraUserUserAddonRoot.setPluginId(pluginId);

    }
    @Ignore
    //@Test
    public void setPluginId_setNullPluginId_throwsExceptions(){

        testExtraUserUserAddonRoot.setPluginId(null);

    }

}
