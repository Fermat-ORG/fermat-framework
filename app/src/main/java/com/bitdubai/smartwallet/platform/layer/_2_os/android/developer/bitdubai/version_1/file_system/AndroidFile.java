package com.bitdubai.smartwallet.platform.layer._2_os.android.developer.bitdubai.version_1.file_system;

import android.content.Context;
import com.bitdubai.smartwallet.platform.layer._2_os.PlatformFile;
import com.bitdubai.smartwallet.platform.layer._2_os.FileLifeSpan;
import com.bitdubai.smartwallet.platform.layer._2_os.FilePrivacy;

import java.io.File;

/**
 * Created by ciencias on 22.01.15.
 */
public class AndroidFile implements PlatformFile {

    Context mContext;

    public AndroidFile (Context context, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan){

        mContext = context;

        File file = new File(context.getFilesDir(), fileName);


    }

}
