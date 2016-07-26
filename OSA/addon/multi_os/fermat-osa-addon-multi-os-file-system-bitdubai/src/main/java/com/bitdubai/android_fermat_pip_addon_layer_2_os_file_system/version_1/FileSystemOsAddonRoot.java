package com.bitdubai.android_fermat_pip_addon_layer_2_os_file_system.version_1;

import com.bitdubai.android_fermat_pip_addon_layer_2_os_file_system.version_1.structure.DesktopPlatformFileSystem;
import com.bitdubai.android_fermat_pip_addon_layer_2_os_file_system.version_1.structure.DesktopPluginFileSystem;
import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;


/**
 * Created by Natalia on 18/05/2015.
 */


/**
 * This addon handles a layer of file system representation.
 * Encapsulates all the necessary functions to manage files , text files and binary files.
 * For interfaces PluginFile the modules need to authenticate with their plugin ids
 * * * *
 */
public class FileSystemOsAddonRoot implements Addon, FileSystemOs, Service {

    /**
     * Os interface member variables.
     */

    PluginFileSystem pluginFileSystem;
    PlatformFileSystem platformFileSystem;


    public PluginFileSystem getPluginFileSystem() {
        return pluginFileSystem;
    }

    public void setPluginFileSystem(PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }

    public ServiceStatus getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(ServiceStatus serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public void setPlatformFileSystem(PlatformFileSystem platformFileSystem) {
        this.platformFileSystem = platformFileSystem;
    }


    /**
     * Service Interface member variables.
     */
    ServiceStatus serviceStatus = ServiceStatus.CREATED;

    // Public constructor declarations.

    /**
     * <p>Addon implementation constructor Constructor
     */
    public FileSystemOsAddonRoot() {
        //I initialize instances of the interfaces
        this.pluginFileSystem = new DesktopPluginFileSystem();
        this.platformFileSystem = new DesktopPlatformFileSystem();

    }

    /**
     * FileSystemOs interfaces implementation.
     */
    @Override
    public PluginFileSystem getPlugInFileSystem() {
        return this.pluginFileSystem;
    }

    @Override
    public PlatformFileSystem getPlatformFileSystem() {
        return this.platformFileSystem;
    }


    // This method is only for android OS
    @Override
    public void setContext(Object context) {
        //this.context = (Context)context;
        //this.pluginFileSystem.setContext(context);
        //this.platformFileSystem.setContext(context);
    }

    /**
     * Service Interface implementation.
     */
    @Override
    public void start() {

        this.serviceStatus = ServiceStatus.STARTED;

    }

    @Override
    public void pause() {

        this.serviceStatus = ServiceStatus.PAUSED;

    }

    @Override
    public void resume() {

        this.serviceStatus = ServiceStatus.STARTED;

    }

    @Override
    public void stop() {

        this.serviceStatus = ServiceStatus.STOPPED;

    }


    @Override
    public ServiceStatus getStatus() {
        return serviceStatus;
    }

}
