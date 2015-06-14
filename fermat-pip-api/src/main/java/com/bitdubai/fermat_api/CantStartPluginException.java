
package com.bitdubai.fermat_api;


import com.bitdubai.fermat_api.layer._1_definition.enums.Plugins;


public class CantStartPluginException extends Exception{
    

	private Plugins plugin;
	
	
	private static final long serialVersionUID = -4797409301346577158L;


    public  CantStartPluginException (Plugins plugin){
        this.plugin = plugin;
    }
    
    public Plugins getPlugin (){
        return this.plugin;
    }
}