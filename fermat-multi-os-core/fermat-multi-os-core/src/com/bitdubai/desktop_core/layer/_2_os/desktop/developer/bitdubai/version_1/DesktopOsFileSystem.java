package com.bitdubai.desktop_core.layer._2_os.desktop.developer.bitdubai.version_1;

import com.bitdubai.android_fermat_pip_addon_layer_2_os_file_system.version_1.FileSystemOsAddonRoot;
import com.bitdubai.android_fermat_pip_addon_layer_2_os_file_system.version_1.structure.DesktopPlatformFileSystem;
import com.bitdubai.android_fermat_pip_addon_layer_2_os_file_system.version_1.structure.DesktopPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.FileSystemOs;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PlatformFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;


/**
 *
 * @author Matias
 */
public class DesktopOsFileSystem implements FileSystemOs{
    /**
     * FileSystemOs interface member variables.
     */


    PluginFileSystem pluginFileSystem;
    PlatformFileSystem platformFileSystem;
    FileSystemOs fileSystemOs;


    public DesktopOsFileSystem() {

        fileSystemOs = new FileSystemOsAddonRoot();


        this.pluginFileSystem = fileSystemOs.getPlugInFileSystem();
        this.platformFileSystem = fileSystemOs.getPlatformFileSystem();

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


    /**
     * Method not used in Desktop
     * @param context 
     */
    @Override
    public void setContext(Object context) {

        //this.pluginFileSystem.setContext(context);
        //this.platformFileSystem.setContext(context);


    }


}
