/*
* @#EnvironmentVariables.java - 2015
* Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
* BITDUBAI/CONFIDENTIAL
*/
package com.bitdubai.fermat_osa_addon.layer.linux.database_system.developer.bitdubai.version_1.desktop.database.bridge;

import java.io.File;

/**
 * The Class <code>com.bitdubai.fermat_osa_addon.layer.linux.database_system.developer.bitdubai.version_1.desktop.database.bridge.EnvironmentVariables</code>
 * <p/>
 * Created by Hendry Rodriguez - (elnegroevaristo@gmail.com) on 10/12/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
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
        File dir = new File(home + "/externalStorage/databases/");

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
        File dir = new File(home + "/externalStorage/databases/");

        return dir.getAbsolutePath();
    }


}
