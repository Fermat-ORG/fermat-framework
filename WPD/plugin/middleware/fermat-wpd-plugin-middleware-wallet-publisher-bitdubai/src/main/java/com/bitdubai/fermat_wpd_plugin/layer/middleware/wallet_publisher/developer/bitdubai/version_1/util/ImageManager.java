/*
 * @#ImageManager.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.util;

import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.structure.ImageMiddlewareImpl;

import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.util.ImageManager</code> is
 * responsible to persist the images into a .xml file in the file system
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 13/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ImageManager {

    public static final String PATH_DIRECTORY = "publisher/images";

    /**
     * Represent the pluginFileSystem
     */
    private PluginFileSystem pluginFileSystem;

    /**
     * Represent the pluginOwnerId
     */
    private UUID pluginOwnerId;

    /**
     * Constructor with parameters
     *
     * @param pluginOwnerId
     * @param pluginFileSystem
     */
    public ImageManager(UUID pluginOwnerId, PluginFileSystem pluginFileSystem){
        super();
        this.pluginOwnerId = pluginOwnerId;
        this.pluginFileSystem = pluginFileSystem;
    }

    /**
     * Save the image into a file in the file system
     *
     * @param image
     * @throws CantCreateFileException
     * @throws CantPersistFileException
     */
    public void saveImageFile(ImageMiddlewareImpl image) throws CantCreateFileException, CantPersistFileException {

        PluginBinaryFile imageFile = pluginFileSystem.createBinaryFile(pluginOwnerId, ImageManager.PATH_DIRECTORY, image.getFileId().toString(), FilePrivacy.PUBLIC, FileLifeSpan.PERMANENT);
        imageFile.setContent(image.getData());
        imageFile.persistToMedia();

    }

    /**
     * Load the image file from the file system
     *
     * @param fileId
     * @return byte[]
     * @throws FileNotFoundException
     * @throws CantCreateFileException
     */
    public byte[] loadImageFile(String fileId) throws FileNotFoundException, CantCreateFileException {

        PluginBinaryFile imageFile = pluginFileSystem.getBinaryFile(pluginOwnerId, ImageManager.PATH_DIRECTORY, fileId, FilePrivacy.PUBLIC, FileLifeSpan.PERMANENT);
        return imageFile.getContent();
    }


}
