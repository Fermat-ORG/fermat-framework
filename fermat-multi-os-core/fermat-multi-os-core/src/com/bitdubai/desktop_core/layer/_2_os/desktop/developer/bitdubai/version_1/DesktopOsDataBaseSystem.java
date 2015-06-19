package com.bitdubai.desktop_core.layer._2_os.desktop.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.layer.osa_android.DataBaseSystemOs;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PlatformDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_osa_addon.layer.desktop.database_system.developer.bitdubai.version_1.structure.DesktopPlatformDatabaseSystem;
import com.bitdubai.fermat_osa_addon.layer.desktop.database_system.developer.bitdubai.version_1.structure.DesktopPluginDatabaseSystem;




/**
 * Created by Matias
 */
public class DesktopOsDataBaseSystem implements DataBaseSystemOs {
    /**
     * DataBaseSystemOs interface member variables.
     */

    PlatformDatabaseSystem platformDatabaseSystem;
    PluginDatabaseSystem pluginDatabaseSystem;

    /**
     * Constructor
     */

    public DesktopOsDataBaseSystem()
    {
        this.pluginDatabaseSystem = new DesktopPluginDatabaseSystem();
        this.platformDatabaseSystem = new DesktopPlatformDatabaseSystem();
    }


    /**
     * DatBaseSystemOs Interface implementation.
     */
    @Override
    public PluginDatabaseSystem getPluginDatabaseSystem() {
        return this.pluginDatabaseSystem;
    }

    @Override
    public PlatformDatabaseSystem getPlatformDatabaseSystem(){
        return this.platformDatabaseSystem;
    }

    
    /**
     * Method unavailable from Desktop-core
     * @param context 
     */
    @Override
    public void setContext (Object context)
    {
        /*this.context = (Context)context;
        this.pluginDatabaseSystem.setContext(context);
        this.platformDatabaseSystem.setContext(context);
                */
    }

}
