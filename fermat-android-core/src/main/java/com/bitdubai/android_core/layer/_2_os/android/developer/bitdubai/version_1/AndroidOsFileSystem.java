package com.bitdubai.android_core.layer._2_os.android.developer.bitdubai.version_1;

import android.content.Context;
import com.bitdubai.fermat_osa_addon.layer.android.file_system.developer.bitdubai.version_1.FileSystemOsAddonRoot;
import com.bitdubai.fermat_api.layer.osa_android.FileSystemOs;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PlatformFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;


/**
 * Created by toshiba on 20/05/2015.
 */
public class AndroidOsFileSystem  implements FileSystemOs {

    /**
     * FileSystemOs interface member variables.
     */


    PluginFileSystem pluginFileSystem;
    PlatformFileSystem platformFileSystem;
    FileSystemOs fileSystemOs;

    public AndroidOsFileSystem(String contextPath) {

        fileSystemOs = new FileSystemOsAddonRoot(contextPath);


        this.pluginFileSystem = fileSystemOs.getPlugInFileSystem();
        this.platformFileSystem =fileSystemOs.getPlatformFileSystem();

    }

    /**
     * FileSystemOs interface implementation.
     */
    @Override
    public PluginFileSystem getPlugInFileSystem() {
        return this.pluginFileSystem;
    }

    @Override
    public PlatformFileSystem getPlatformFileSystem() {
        return this.platformFileSystem;
    }
}
