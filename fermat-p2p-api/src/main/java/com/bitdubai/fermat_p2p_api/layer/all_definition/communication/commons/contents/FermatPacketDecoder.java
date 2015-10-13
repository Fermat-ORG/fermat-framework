/*
 * @#FermatPacketDecoder.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmetricCryptography;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatPacket;

/**
 * The Class <code>com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.contents.FermatPacketDecoder</code> is
 * responsible of decode the fermat packet after received
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 03/09/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class FermatPacketDecoder {

    /**
     * Decode the fermat packet
     *
     * @param fermatPacketEncode
     * @param privateKey
     * @return FermatPacket
     */
    public static FermatPacket decode(String fermatPacketEncode, String privateKey)  {

        /*
        * Decode the string into a json string representation
        */
        String fermatPacketJsonDecode = AsymmetricCryptography.decryptMessagePrivateKey(fermatPacketEncode, privateKey);

        /**
         * Construct the fermat packet object with the decode json string
         */
        return  FermatPacketCommunicationFactory.constructFermatPacketFromJsonString(fermatPacketJsonDecode);

    }
}
