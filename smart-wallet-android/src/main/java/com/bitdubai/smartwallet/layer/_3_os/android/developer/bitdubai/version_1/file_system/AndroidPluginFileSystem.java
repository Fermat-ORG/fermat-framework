package com.bitdubai.smartwallet.layer._3_os.android.developer.bitdubai.version_1.file_system;

import android.content.Context;
import com.bitdubai.wallet_platform_api.layer._3_os.File_System.*;

import java.util.UUID;

/**
 * Created by ciencias on 20.01.15.
 */

/**
 * The Plugin File System is the implementation of the file system that is handled to external plugins. It differs
 * from the Platform File System in that this one requires the plug in to identify itself.
 */

public class AndroidPluginFileSystem implements PluginFileSystem {

    Context mContext;
    
    //TODO: Vamos a manejar las variables a nivel de modulo sin la m en el nombre para seguir los guidelines de java. en este caso mContext seria context y cuando lo usas dentro del codigo ponele siempre this.context para diferenciarlo de variables locales de los metodos o de los parametros. Anda gradualmente reemplazando todas las varialbes con m adelante con este criterio.

    @Override
    public PluginDataFile getDataFile(UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws FileNotFoundException {

        AndroidPluginDataFile newFile = new AndroidPluginDataFile(ownerId, mContext, fileName, privacyLevel, lifeSpan);

        try {
            newFile.loadFromMemory();
            return newFile;
        }
        catch (CantLoadFileException e){
            System.err.println("GetFailedException: " + e.getMessage());
            e.printStackTrace();
            throw new FileNotFoundException();
        }
    }

    @Override
    public PluginDataFile createDataFile(UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) {

        return new AndroidPluginDataFile(ownerId, mContext,fileName, privacyLevel, lifeSpan);
    }

    @Override
    public void setContext(Object context) {
        mContext = (Context) context;
    }
}
