/*
 * @#ImageManager.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_publisher.developer.bitdubai.version_1.util;

import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.dmp_module.wallet_publisher.interfaces.Image;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;

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
     * Method that save the imageMiddleware object pass a parameter into a .xml file representation in
     * the file system
     *
     * @param imageMiddleware
     */
    public void persist(Image imageMiddleware) throws CantCreateFileException, CantPersistFileException {

        /*
         * Obtain the xml representation of the object
         */
        String xmlContent =XMLParser.parseObject(imageMiddleware);

        /*
         * Create the file
         */
        PluginTextFile xmlFile = pluginFileSystem.createTextFile(pluginOwnerId, ImageManager.PATH_DIRECTORY, imageMiddleware.getFileId().toString(), FilePrivacy.PUBLIC, FileLifeSpan.PERMANENT);

        /*
         * Set the content
         */
        xmlFile.setContent(xmlContent);

        /*
         * Persist the file
         */
        xmlFile.persistToMedia();

    }

    /**
     * Method that load a image from a .xml file saved in the file system
     *
     *   file_id = file name
     *
     * @return Image
     */
    public Image load(String file_id) throws FileNotFoundException, CantCreateFileException {

        /*
         * Load the file
         */
        PluginTextFile xmlFile = pluginFileSystem.getTextFile(pluginOwnerId, ImageManager.PATH_DIRECTORY, file_id, FilePrivacy.PUBLIC, FileLifeSpan.PERMANENT);

        /**
         * Parse the xml into the object
         */
        Image imageMiddleware = (Image) XMLParser.parseXML(xmlFile.getContent(), Image.class);

        /**
         * Return the imageMiddleware
         */
        return imageMiddleware;
    }

}
