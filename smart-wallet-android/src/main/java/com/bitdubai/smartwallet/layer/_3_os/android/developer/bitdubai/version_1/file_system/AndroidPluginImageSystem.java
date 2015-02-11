package com.bitdubai.smartwallet.layer._3_os.android.developer.bitdubai.version_1.file_system;

import android.content.Context;
import android.graphics.Bitmap;

import com.bitdubai.wallet_platform_api.layer._3_os.CantLoadFileException;
import com.bitdubai.wallet_platform_api.layer._3_os.FileLifeSpan;
import com.bitdubai.wallet_platform_api.layer._3_os.FileNotFoundException;
import com.bitdubai.wallet_platform_api.layer._3_os.FilePrivacy;

import com.bitdubai.wallet_platform_api.layer._3_os.PluginImageSystem;
import com.bitdubai.wallet_platform_api.layer._3_os.PluginImageFile;

import java.util.UUID;

/**
 * Created by toshiba on 11/02/2015.
 */
public class AndroidPluginImageSystem  implements PluginImageSystem {
    Context mContext;
    Bitmap mBitmapImage;
    @Override
    public PluginImageFile getFile (UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws FileNotFoundException
    {
        AndroidImageFile newFile = new AndroidImageFile(ownerId, mContext, fileName, privacyLevel, lifeSpan);

        newFile.setBitMap(mBitmapImage);

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

    public PluginImageFile createFile (UUID ownerId, String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan )
    {

        return new AndroidImageFile(ownerId, mContext, fileName, privacyLevel, lifeSpan);
    }

    public void setContext (Object context)
    {
        mContext = (Context) context;
    }


}
