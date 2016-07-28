package com.bitdubai.fermat_osa_addon.layer.linux.file_system.developer.bitdubai.version_1.structure;


import java.io.File;


/**
 * Created by Roberto Requena - (rart3001@gmail.com) on 08/12/2015.
 */
public class EnvironmentVariables {


    /**
     * Get the path to file system folder
     *
     * @return String path to file folder
     **/
    public static Object getExternalStorageDirectory() {

        //User home directory
        String home = System.getProperty("user.home");
        File dir = new File(home + "/externalStorage/files/");
        dir.mkdirs();
        return dir.getAbsolutePath();

    }

    /**
     * Get the path to private internal folders
     *
     * @return String path to private internal folder
     **/
    // this method is for the private internal files if we have to hidden in the future 
    public static Object getInternalStorageDirectory() {

        //User home directory
        String home = System.getProperty("user.home");
        File dir = new File(home + "/externalStorage/files/internal/");
        return dir.getAbsolutePath();
    }


}
