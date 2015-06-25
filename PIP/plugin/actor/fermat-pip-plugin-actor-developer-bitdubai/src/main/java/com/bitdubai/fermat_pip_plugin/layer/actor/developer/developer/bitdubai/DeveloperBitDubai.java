package com.bitdubai.fermat_pip_plugin.layer.actor.developer.developer.bitdubai;



import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.PluginDeveloper;
import com.bitdubai.fermat_pip_plugin.layer.actor.developer.developer.bitdubai.version_1.ActorDeveloperPluginRoot;


/**
 * Created by ciencias on 22.01.15.
 */
public class DeveloperBitDubai implements PluginDeveloper {

    Plugin plugin;




    @Override
    public Plugin getPlugin() {
        return plugin;
    }





    public DeveloperBitDubai () {

        /**
         * I will choose from the different versions of my implementations which one to start. Now there is only one, so
         * it is easy to choose.
         */

        plugin = new ActorDeveloperPluginRoot();

    }


}
