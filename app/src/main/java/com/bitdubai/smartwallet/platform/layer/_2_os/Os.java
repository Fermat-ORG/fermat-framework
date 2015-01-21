package com.bitdubai.smartwallet.platform.layer._2_os;

/**
 * Created by ciencias on 03.01.15.
 */
public interface Os {

    FileSystem getFileSystem();
    DatabaseSystem getDatabaseSystem();

}
