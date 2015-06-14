package com.bitdubai.android_fermat_pip_addon_layer_2_os_file_system.version_1.desktop.database.bridge;

import java.io.File;
import javax.swing.JOptionPane;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.filesystems.Repository;

/**
 *
 * @author Matias
 */
/**
 * The Environment class is an implementation of the File system API
 * to handle the location of the database files in windows OS
 *
 */

public class EnviromentVariables {


    /**
     * Get the path to databases folder
     * @return String path to databases folder
     **/
    public static Object getExternalStorageDirectory() {

        //User home directory
        String home = System.getProperty("user.home");
        File dir = new File(home+"/externalStorage/databases/");
        FileObject myfolder = FileUtil.toFileObject(dir);
        if(myfolder == null){
            //Testing
            //displayMessage("Creating folder "+dir.getPath());
            return null;
        }

        return myfolder;

    }

    /**
     * Method only for testing purpose
     * @param message
     */
    public static void displayMessage(String message){
        JOptionPane.showInputDialog(message);
    }


    /**
     * Get the path to private internal folders
     * @return String path to private internal folder
     **/
    // this method is for the private internal databases if we have to hidden in the future
    public static Object getInternalStorageDirectory() {

        FileObject root = Repository.getDefault().getDefaultFileSystem().getRoot();

        FileObject dir = root.getFileObject("Storage");

        return dir;

    }




}
