/*
 * @#FermatPacketEncoder.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatPacket;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatPacketEncoder</code> is
 * responsible of encode the fermat packet before is transmit
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 03/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class FermatPacketEncoder {

    /**
     * Encode the fermat packet
     *
     * @param fermatPacket
     * @return string encode
     */
    public static String encode(FermatPacket fermatPacket){

        /*
         * Convert the fermatPacket to json string representation and encrypted whit the public key of the receiver
         */
        return AsymmetricCryptography.encryptMessagePublicKey(fermatPacket.toJson(), fermatPacket.getDestination());

    }
}
