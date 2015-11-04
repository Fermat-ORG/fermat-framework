package com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.utils;

import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoTransactionType;
import com.bitdubai.fermat_cry_api.layer.definition.enums.EventType;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.exceptions.CantSelectEventException;

/**
 * Created by lnacosta (laion.cj91@gmail.com) on 16/10/2015.

 * The purpose of this class is to indicate the correct
 * event to raise when a there is transactions to be notified.
 */
public class EventsSelector {

    // return the correct event to raise having in count the type of transaction and the crypto status.
    public static EventType getEventType(final CryptoTransactionType type        ,
                                         final CryptoStatus          cryptoStatus) throws CantSelectEventException {

        switch(type) {

            case INCOMING:

                switch(cryptoStatus){

                    case ON_BLOCKCHAIN             : return EventType.INCOMING_CRYPTO_ON_BLOCKCHAIN;
                    case ON_CRYPTO_NETWORK         : return EventType.INCOMING_CRYPTO_ON_CRYPTO_NETWORK;
                    case REVERSED_ON_BLOCKCHAIN    : return EventType.INCOMING_CRYPTO_REVERSED_ON_BLOCKCHAIN;
                    case REVERSED_ON_CRYPTO_NETWORK: return EventType.INCOMING_CRYPTO_REVERSED_ON_CRYPTO_NETWORK;
                    case IRREVERSIBLE              : return EventType.INCOMING_CRYPTO_IRREVERSIBLE;

                    default:
                        throw new CantSelectEventException(
                                "type: " + " with code " + type.getCode()+
                                        "\ncryptoStatus: " + " with code " + cryptoStatus.getCode(),
                                "cryptoStatus not considered in switch statement"
                        );
                }

            case OUTGOING:

                switch(cryptoStatus){

                    case ON_BLOCKCHAIN             : return null; // no event defined.
                    case ON_CRYPTO_NETWORK         : return null; // no event defined.
                    case REVERSED_ON_BLOCKCHAIN    : return null; // no event defined.
                    case REVERSED_ON_CRYPTO_NETWORK: return null; // no event defined.
                    case IRREVERSIBLE              : return null; // no event defined.

                    default:
                        throw new CantSelectEventException(
                                "type: " + " with code " + type.getCode()+
                                        "\ncryptoStatus: " + " with code " + cryptoStatus.getCode(),
                                "cryptoStatus not considered in switch statement"
                        );
                }

            default:
                // Here we have a serious problem
                throw new CantSelectEventException(
                        "type: " + " with code " + type.getCode()+
                        "\ncryptoStatus: " + " with code " + cryptoStatus.getCode(),
                        "type not considered in switch statement"
                );
        }
    }

}
