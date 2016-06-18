/*
 * @#TemplateNetworkServiceDatabaseConstants.java - 2015
 * Copyright bitDubai.com., All rights reserved.
  * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package org.fermat.fermat_dap_plugin.layer.network.service.asset.transmission.developer.version_2.database.communications;

/**
 * The Class <code>com.bitdubai.fermat_dap_plugin.layer.actor.network.service.asset.user.developer.bitdubai.version_2.database.communications.CommunicationNetworkServiceDatabaseConstants</code> is
 * throw when error occurred updating new record in a table of the data base
 * <p/>
 * Created by Created by Roberto Requena - (rart3001@gmail.com) on 15/10/2015. - Jose Briceño - (josebricenor@gmail.com) on 18/02/2016
 *
 * @version 2.0
 * @since Java JDK 1.7
 */
public class CommunicationNetworkServiceDatabaseConstants {

    public static final String DATA_BASE_NAME = "TransmissionServiceDataBase";
    /**
     * DAPMessage database table definition.
     */
    public static final String DAP_MESSAGE_TABLE_NAME = "dap_message";

    public static final String DAP_MESSAGE_ID_COLUMN_NAME = "dap_message_id";
    public static final String DAP_MESSAGE_TYPE_COLUMN_NAME = "dap_message_type";
    public static final String DAP_MESSAGE_SUBJECT_COLUMN_NAME = "dap_message_subject";
    public static final String DAP_MESSAGE_STATUS_COLUMN_NAME = "dap_message_status"; //UNREAD or READ
    public static final String DAP_MESSAGE_DATA_COLUMN_NAME = "dap_message_data"; //Here we'll save our DAPMessage as a json object

    public static final String DAP_MESSAGE_FIRST_KEY_COLUMN = DAP_MESSAGE_ID_COLUMN_NAME;

}
