package com.bitdubai.fermat_api.layer.osa_android;


import com.bitdubai.fermat_api.layer.osa_android.file_system.PlatformFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;

/**
 * Created by Natalia on 19/05/2015.
 */
public interface FileSystemOs {

    public PluginFileSystem getPlugInFileSystem();

    public PlatformFileSystem getPlatformFileSystem();

}
