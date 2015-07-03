package unit.com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidDatabase;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by jorgegonzalez on 2015.06.29..
 */
public class FileStorageHelper {

    public static String getInternalPath(Context context){
        return context.getFilesDir().getAbsolutePath();
    }

    public static long getInternalFreeSpace(Context context){
        File file = new
                File(context.getFilesDir().getPath());
        return file.getFreeSpace();
    }

    public static String getExternalPath(Context context){
        File file = new
                File(context.getExternalFilesDir(null).getPath());

        //check if internal and external is the same stored drive
        if (file.getFreeSpace() ==
                getInternalFreeSpace(context))
            return null;

        return file.getAbsolutePath();
    }

    public static long getExternalFreeSpace(Context context){
        File file = new File(Environment.getExternalStorageDirectory().getPath());

        //check if internal and external is the same stored drive
        if (file.getFreeSpace() ==
                getInternalFreeSpace(context))
            return 0;

        return file.getFreeSpace();
    }

    public static String getAdditionalPath(Context context){
        File storage = new File("/storage/");
        if (!storage.exists())
            return null;

        for (File inFile : storage.listFiles()) {
            if (inFile.isDirectory() && !inFile.getName().contains("emulated")){
                long freeSpace = inFile.getFreeSpace();

                if (freeSpace != 0 && freeSpace !=
                        getInternalFreeSpace(context) && freeSpace !=
                        getExternalFreeSpace(context)){

                    File dir = new File(inFile.getAbsolutePath() + "/Android/data/");
                    if (!dir.exists()){//check for android data dir
                        dir = new File(inFile.getAbsolutePath() + "/Katalogi/");

                        if (!dir.exists())
                            dir.mkdir();

                        return inFile.getAbsolutePath() + "/Katalogi/";
                    }

                    String path = inFile.getAbsolutePath() + "/Android/data/" + context.getPackageName();
                    dir = new File(path);

                    if (!dir.exists())
                        dir.mkdir();

                    return path;
                }
            }
        }
        return null;
    }

    public static long getAdditionalFreeSpace(String path){
        File file = new File(path);
        return file.getFreeSpace();
    }

    public static String getMostFreeSpacePath(Context context){
        long internal = getInternalFreeSpace(context);
        long external = getExternalFreeSpace(context);
        long additional = 0;
        String additionalPath = getAdditionalPath(context);

        if (additionalPath != null){
            additional = getAdditionalFreeSpace(additionalPath);
        }

        long max = Math.max(internal, Math.max(external, additional));

        if (max == internal)
            return getInternalPath(context);
        else if (max == external)
            return getExternalPath(context);
        else
            return additionalPath;
    }

}
