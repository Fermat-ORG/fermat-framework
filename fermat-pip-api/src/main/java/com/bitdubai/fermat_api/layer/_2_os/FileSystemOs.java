package com.bitdubai.fermat_api.layer._2_os;


import com.bitdubai.fermat_api.layer._2_os.file_system.PlatformFileSystem;
import com.bitdubai.fermat_api.layer._2_os.file_system.PluginFileSystem;

/**
 * Created by Natalia on 19/05/2015.
 */
public interface FileSystemOs {

    public PluginFileSystem getPlugInFileSystem();

    public PlatformFileSystem getPlatformFileSystem();

    public void setContext (Object context);
}
