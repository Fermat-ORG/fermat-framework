package com.bitdubai.fermat_api;

import com.bitdubai.fermat_api.layer._1_definition.enums.Plugins;

/**
 * Created by ciencias on 3/23/15.
 */
public class CantStartPluginException extends Exception{
    
    Plugins plugin;


    public  CantStartPluginException (Plugins plugin){
        this.plugin = plugin;
    }
    
    public Plugins getPlugin (){
        return this.plugin;
    }
    
}
