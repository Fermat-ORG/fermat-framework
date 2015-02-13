package com.bitdubai.wallet_platform_api;

import com.bitdubai.wallet_platform_api.layer._1_definition.enums.Addons;
import com.bitdubai.wallet_platform_api.layer._1_definition.enums.Plugins;
import com.bitdubai.wallet_platform_api.layer._4_user.User;

/**
 * Created by ciencias on 2/12/15.
 */
public interface PlatformContext {

    public void addAddon (Addon addon, Addons descriptor);


    public Addon getAddon (Addons descriptor);


    public void addPlugin (Plugin plugin, Plugins descriptor);


    public Plugin getPlugin (Plugins descriptor);

    
}
