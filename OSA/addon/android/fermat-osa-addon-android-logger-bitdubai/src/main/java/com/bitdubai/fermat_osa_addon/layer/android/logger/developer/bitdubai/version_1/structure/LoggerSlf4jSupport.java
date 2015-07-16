/*
 * @(#LoggerSlf4jSupport.java 07/16/2015
 * Copyright 2015 bitDubai, Inc. All rights reserved.
 * BITDUBAI/CONFIDENTIAL
 * */

package com.bitdubai.fermat_osa_addon.layer.android.logger.developer.bitdubai.version_1.structure;


// Packages and classes to import of fermat api.
import com.bitdubai.fermat_api.layer.osa_android.logger_system.ILogger;

// Packages and classes to import of apache commons api.
import static org.apache.commons.lang3.StringUtils.isEmpty;

// Packages and classes to import of Slf4j api.
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 *  The Class <code>com.bitdubai.fermat_osa_addon.layer.android.logger.developer.bitdubai.version_1.structure.LoggerSlf4jSupport</code> is a class that implements the methods for management the logger information.
 *
 *
 *  @author  Raul Geomar Pena (raul.pena@gmail.com)
 *  @version 1.0.0
 *  @since   jdk 1.7
 *  @since   07/16/2015
 *  @see     {@link com.bitdubai.fermat_api.layer.osa_android.logger_system.ILogger}
 * */
public class LoggerSlf4jSupport implements ILogger {


    // Private instance fields declarations.
    // Logger object.
    private final Logger logger;


    // Public constructor declarations.
    /**
     *
     *  <p>Unique constructor with arguments.
     *
     *  @param _class
     * */
    public LoggerSlf4jSupport (Class _class) {

        // Call to super class.
        super ();

        // Set internal values.
        this.logger = LoggerFactory.getLogger (_class);
    }


    /**
     * <p>Method that execute the info logger information.
     *
     * @param tag Target to print. (Only android use.)
     * @param msg Message to print.
     */
    @Override
    public void info (String tag, String msg) {

        // Check the message.
        if (isEmpty (msg)) {
            return;
        }

        // Print the message.
        this.logger.info (msg.trim ());
    }

    /**
     * <p>Method that execute the debug logger information.
     *
     * @param tag Target to print. (Only android use.)
     * @param msg Message to print.
     */
    @Override
    public void debug (String tag, String msg) {

        // Check the message.
        if (isEmpty (msg)) {
            return;
        }

        // Print the message.
        this.logger.debug(msg.trim());
    }

    /**
     * <p>Method that execute the warn logger information.
     *
     * @param tag Target to print. (Only android use.)
     * @param msg Message to print.
     */
    @Override
    public void warn (String tag, String msg) {

        // Check the message.
        if (isEmpty (msg)) {
            return;
        }

        // Print the message.
        this.logger.warn(msg.trim());
    }

    /**
     * <p>Method that execute the error logger information.
     *
     * @param tag Target to print. (Only android use.)
     * @param msg Message to print.
     */
    @Override
    public void error (String tag, String msg) {

        // Check the message.
        if (isEmpty (msg)) {
            return;
        }

        // Print the message.
        this.logger.error(msg.trim());
    }

    /**
     * <p>Method that execute the error logger information.
     *
     * @param tag Target to print. (Only android use.)
     * @param msg Message to print.
     * @param e   Failure.
     */
    @Override
    public void error(String tag, String msg, Throwable e) {

        // Check the message.
        if (isEmpty (msg) && e == null) {
            return;
        }

        // Print the message.
        this.logger.error (msg.trim (), e);
    }
}