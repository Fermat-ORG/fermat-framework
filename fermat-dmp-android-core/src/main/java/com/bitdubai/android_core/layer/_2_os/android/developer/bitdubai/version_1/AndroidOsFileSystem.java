package com.bitdubai.android_core.layer._2_os.android.developer.bitdubai.version_1;

import android.content.Context;
import com.bitdubai.android_fermat_pip_addon_layer_2_os_file_system.version_1.FileSystemOsAddonRoot;
import com.bitdubai.fermat_api.layer._2_os.FileSystemOs;
import com.bitdubai.fermat_api.layer._2_os.file_system.PlatformFileSystem;
import com.bitdubai.fermat_api.layer._2_os.file_system.PluginFileSystem;


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


    Context context;

    public AndroidOsFileSystem() {

        fileSystemOs = new FileSystemOsAddonRoot();


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



    @Override
    public void setContext(Object context) {

        this.context = (Context) context;
        this.pluginFileSystem.setContext(context);
        this.platformFileSystem.setContext(context);


    }



}
