/*
 * @#ImageData.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_wpd_api.layer.wpd_sub_app_module.wallet_publisher.interfaces;

import java.util.UUID;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.dmp_module.wallet_publisher.interfaces.ImageMiddleware</code> this wrap a image
 * to be persist into the file system ike a xml file
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 11/08/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public interface Image {

    /**
     * Get the data
     *
     * @return byte []
     */
    public byte [] getData();

}
