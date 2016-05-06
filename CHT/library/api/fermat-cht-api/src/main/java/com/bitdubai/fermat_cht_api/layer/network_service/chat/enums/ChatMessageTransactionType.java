/*
 * @#DigitalAssetMetadataTransactionType.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_cht_api.layer.network_service.chat.enums;

import com.bitdubai.fermat_api.layer.all_definition.exceptions.InvalidParameterException;

/**
 * The Class <code>com.bitdubai.fermat_dap_api.layer.dap_network_services.asset_transmission.enums.DigitalAssetMetadataTransactionType</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 15/10/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public enum ChatMessageTransactionType {

    /**
     * Definition types
     */
    CHAT_METADATA_TRASMIT("CMDT"),
    MESSAGE_METADATA_TRANSMIT("MMDT"),
    TRANSACTION_STATUS_UPDATE ("TSU"),
    TRANSACTION_WRITING_STATUS ("TWS"),
    TRANSACTION_ONLINE_STATUS ("TOS");

    /**
     * Represent the code of the message status
     */
    private final String code;

    /**
     * Constructor whit parameter
     *
     * @param code the valid code
     */
    ChatMessageTransactionType(String code) {
        this.code = code;
    }

    /**
     * Return a string code
     *
     * @return String that represent of the message status
     */
    public String getCode()   { return this.code ; }

    /**
     * Return the enum by the code
     *
     * @param code the valid code
     * @return MessagesStatus enum
     * @throws InvalidParameterException error with is no a valid code
     */
    public static ChatMessageTransactionType getByCode(String code) throws InvalidParameterException {

        switch (code) {
            case "MDT":
                return ChatMessageTransactionType.CHAT_METADATA_TRASMIT;
            case "TSU":
                return ChatMessageTransactionType.TRANSACTION_STATUS_UPDATE;
        }

        /**
         * If we try to convert am invalid string.
         */
        throw new InvalidParameterException(code);
    }

}
