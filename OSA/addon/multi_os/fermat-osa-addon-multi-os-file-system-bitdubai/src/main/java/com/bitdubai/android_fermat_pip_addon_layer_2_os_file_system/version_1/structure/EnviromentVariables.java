package com.bitdubai.fermat_osa_addon.layer.android.file_system.developer.bitdubai.version_1.structure;

import org.openide.filesystems.FileUtil;

import java.io.File;

import javax.swing.JOptionPane;

/**
 * Created by mati on 12/06/15.
 */
public class EnviromentVariables {


    /**
     * Get the path to public folders
     * @return String path to public folder
     */
    public static Object getExternalStorageDirectory() {

        //User home directory
        String home = System.getProperty("user.home");
        File dir = new File(home+"/externalStorage");
        org.openide.filesystems.FileObject myfolder = FileUtil.toFileObject(dir);
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
    public static Object getInternalStorageDirectory() {

        org.openide.filesystems.FileObject root = FileUtil.getConfigRoot();

        org.openide.filesystems.FileObject dir = root.getFileObject("Storage");

        return dir;

    }


    // Testing code to get the internal Storage and subfolders

    //FileObject root = Repository.getDefault().getDefaultFileSystem().getRoot();

    //FileObject dir = root.getFileObject("Storage");

    //return dir;

        /*FileObject[] kids = dir.getChildren();

        for (int i = 0; i < kids.length; i++) {
            FileObject fileObject = kids[i];
            String name = fileObject.getName();
            JOptionPane.showMessageDialog(null, name);
        }
        */

}
