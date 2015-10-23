package com.bitdubai.fermat_core;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantGetModuleManagerException;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.CantStartSystemException;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.ModuleManagerNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.exceptions.PluginNotFoundException;
import com.bitdubai.fermat_api.layer.all_definition.common.utils.PluginReference;
import com.bitdubai.fermat_api.layer.modules.ModuleManager;

/**
 * The class <code>com.bitdubai.fermat_core.FermatSystem</code>
 * starts all the component of the platform and manage it.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 22/10/2015.
 */
public class FermatSystem {

    private final FermatSystemContext fermatSystemContext;

    public FermatSystem() {
        fermatSystemContext = new FermatSystemContext();
    }

    public final void start() throws CantStartSystemException {

    }

    /**
     * Throw the method <code>getModuleManager</code> the graphic interface can access to the modules of
     * its sub-apps and wallets.
     *
     * @param pluginReference plugin reference data.
     *
     * @return an instance of the requested module manager.
     *
     * @throws CantGetModuleManagerException if something goes wrong.
     */
    public final ModuleManager getModuleManager(final PluginReference pluginReference) throws CantGetModuleManagerException  ,
                                                                                              ModuleManagerNotFoundException {

        try {

            Plugin moduleManager = fermatSystemContext.getPlugin(pluginReference);

            if (moduleManager instanceof ModuleManager)
                return (ModuleManager) moduleManager;
            else
                throw new CantGetModuleManagerException(pluginReference.toString(), "The plugin requested not implements module manager interface.");

        } catch(PluginNotFoundException e) {

            throw new ModuleManagerNotFoundException(e, pluginReference.toString(), "The module manager cannot be found.");
        } catch (Exception e) {

            throw new CantGetModuleManagerException(e, pluginReference.toString(), "Unhandled error.");
        }
    }

}
