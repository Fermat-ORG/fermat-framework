package com.bitdubai.fermat_osa_addon.layer.android.file_system.developer.bitdubai.version_1;

import android.content.Context;

import com.bitdubai.fermat_osa_addon.layer.android.file_system.developer.bitdubai.version_1.structure.AndroidPlatformFileSystem;
import com.bitdubai.fermat_osa_addon.layer.android.file_system.developer.bitdubai.version_1.structure.AndroidPluginFileSystem;
import com.bitdubai.fermat_api.Addon;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;

import com.bitdubai.fermat_api.layer.osa_android.FileSystemOs;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PlatformFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;



/**
 * Created by Natalia on 18/05/2015.
 */


/**
 * This addon handles a layer of file system representation.
 * Encapsulates all the necessary functions to manage files , text files and binary files.
 * For interfaces PluginFile the modules need to authenticate with their plugin ids
 * * * *
 */
public class FileSystemOsAddonRoot implements Addon ,FileSystemOs,Service {

    private String contextPath;

    /**
     * Os interface member variables.
     */
    private PluginFileSystem pluginFileSystem;
    private PlatformFileSystem platformFileSystem;

    /**
     * Service Interface member variables.
     */
    private ServiceStatus serviceStatus = ServiceStatus.CREATED;



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

    // Public constructor declarations.
    /**
     *  <p>Addon implementation constructor Constructor
     */
    public FileSystemOsAddonRoot(String contextPath)
    {
        //I initialize instances of the interfaces
        this.pluginFileSystem = new AndroidPluginFileSystem(contextPath);
        this.platformFileSystem = new AndroidPlatformFileSystem(contextPath);

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
