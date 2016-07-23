package com.bitdubai.android_fermat_pip_addon_layer_2_os_file_system.version_1.structure;

//import org.openide.filesystems.FileUtil;

import java.io.File;

import javax.swing.JOptionPane;
//import org.openide.filesystems.FileObject;
//import org.openide.filesystems.Repository;

/**
 * Created by mati on 12/06/15.
 */
public class EnviromentVariables {


    /**
     * Get the path to file system folder
     *
     * @return String path to file folder
     **/
    public static Object getExternalStorageDirectory() {

        //User home directory
        String home = System.getProperty("user.home");
        File dir = new File(home + "/externalStorage/files/");
        //FileObject myfolder = FileUtil.toFileObject(dir);
        dir.mkdir();
        /*if(myfolder == null){
            //Testing
            //displayMessage("Creating folder "+dir.getPath());
            
            return null;
        }*/

        return dir.getAbsolutePath();

    }

    /**
     * Method only for testing purpose
     *
     * @param message
     */
    public static void displayMessage(String message) {
        JOptionPane.showInputDialog(message);
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
        File dir = new File(home + "/internalStorage/files/");
        //FileObject myfolder = FileUtil.toFileObject(dir);
        dir.mkdir();
        /*if(myfolder == null){
            //Testing
            //displayMessage("Creating folder "+dir.getPath());
            
            return null;
        }*/

        return dir.getAbsolutePath();
    }


}
