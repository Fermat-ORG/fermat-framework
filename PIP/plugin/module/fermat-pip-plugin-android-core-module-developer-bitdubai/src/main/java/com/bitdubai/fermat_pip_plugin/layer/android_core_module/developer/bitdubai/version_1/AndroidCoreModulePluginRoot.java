package com.bitdubai.fermat_pip_plugin.layer.android_core_module.developer.bitdubai.version_1;


import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;

import com.bitdubai.fermat_api.layer.all_definition.settings.structure.SettingsManager;
import com.bitdubai.fermat_api.layer.all_definition.util.Version;
import com.bitdubai.fermat_api.layer.modules.common_classes.ActiveActorIdentityInformation;
import com.bitdubai.fermat_api.layer.modules.exceptions.ActorIdentityNotSelectedException;
import com.bitdubai.fermat_api.layer.modules.exceptions.CantGetSelectedActorIdentityException;
import com.bitdubai.fermat_pip_api.all_definition.enums.NetworkStatus;
import com.bitdubai.fermat_pip_api.layer.module.android_core.interfaces.AndroidCoreManager;
import com.bitdubai.fermat_pip_api.layer.module.android_core.interfaces.AndroidCoreModule;
import com.bitdubai.fermat_pip_plugin.layer.android_core_module.developer.bitdubai.version_1.structure.AndroidCoreModuleManager;


/**
 * TODO: This plugin do .
 * <p/>
 * TODO: DETAIL...............................................
 * <p/>
 * <p/>
 * Created by Natalia   19/01/2016
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class AndroidCoreModulePluginRoot extends AbstractPlugin implements AndroidCoreModule {



    public AndroidCoreModulePluginRoot(){
        super(new PluginVersionReference(new Version()));
    }

    /**
     * Module Manager Implementation
     *
     */

    @Override
    public AndroidCoreManager getAndroidCoreManager() {
        return new AndroidCoreModuleManager();
    }

    @Override
    public SettingsManager getSettingsManager() {
        return null;
    }

    @Override
    public ActiveActorIdentityInformation getSelectedActorIdentity() throws CantGetSelectedActorIdentityException, ActorIdentityNotSelectedException {
        return null;
    }

    @Override
    public void createIdentity(String name, String phrase, byte[] profile_img) throws Exception {

    }

    @Override
    public void setAppPublicKey(String publicKey) {

    }

    @Override
    public int[] getMenuNotifications() {
        return new int[0];
    }
}
