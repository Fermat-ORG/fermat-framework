/*
 * @(#ILogger.java 07/16/2015
 * Copyright 2015 bitDubai, Inc. All rights reserved.
 * BITDUBAI/CONFIDENTIAL
 * */

package com.bitdubai.fermat_api.layer.osa_android.logger_system;


/**
 *
 *  The abstract Class <code>com.bitdubai.fermat_api.layer.osa_android.logger_system.ILogger</code> is a interface that define the methods for management the logger information.
 *
 *
 *  @author  Raul Geomar Pena (raul.pena@gmail.com)
 *  @version 1.0.0
 *  @since   jdk 1.7
 *  @since   07/16/2015
 * */
public interface ILogger {


    // Method declarations.
    /**
     *
     *  <p>Method that execute the info logger information.
     *
     *  @param tag Target to print. (Only android use.)
     *  @param msg Message to print.
     * */
    void info (String tag, String msg);

    /**
     *
     *  <p>Method that execute the debug logger information.
     *
     *  @param tag Target to print. (Only android use.)
     *  @param msg Message to print.
     * */
    void debug (String tag, String msg);

    /**
     *
     *  <p>Method that execute the warn logger information.
     *
     *  @param tag Target to print. (Only android use.)
     *  @param msg Message to print.
     * */
    void warn (String tag, String msg);

    /**
     *
     *  <p>Method that execute the error logger information.
     *
     *  @param tag Target to print. (Only android use.)
     *  @param msg Message to print.
     * */
    void error (String tag, String msg);

    /**
     *
     *  <p>Method that execute the error logger information.
     *
     *  @param tag Target to print. (Only android use.)
     *  @param msg Message to print.
     *  @param e Failure.
     * */
    void error (String tag, String msg, Throwable e);
}