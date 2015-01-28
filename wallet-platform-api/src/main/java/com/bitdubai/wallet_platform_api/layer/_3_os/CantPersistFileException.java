package com.bitdubai.wallet_platform_api.layer._3_os;

/**
 * Created by ciencias on 22.01.15.
 */
public class CantPersistFileException extends Exception {

    String mFileName;

    public CantPersistFileException (String fileName) {
        mFileName = fileName;
    }

    @Override
    public String getMessage() {
        return "Cant persist to media the file " + mFileName;
    }

}
