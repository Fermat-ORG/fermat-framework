/*
 * @#SimpleRunner.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmectricCryptography;
import com.bitdubai.fermat_api.layer.all_definition.enums.NetworkServices;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.FMPPacketFactory;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.cloud.CloudFMPPacket;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.fmp.FMPPacket;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communication.cloud_server.developer.bitdubai.version_1.CloudServerMainRunner</code> initialize
 * the plugin root that represent the cloud server and start it
 * <p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 08/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class CloudServerMainRunner {

    /**
     * Main method
     *
     * @param args
     */
    public static void main(String [ ] args){

        FMPPacket cloudFMPPacket = new CloudFMPPacket("Roberto", "David", FMPPacket.FMPPacketType.CONNECTION_ACCEPT, "Prueba", "cccccxxxx", NetworkServices.UNDEFINED);

        String json = cloudFMPPacket.toJson();
        System.out.println("cloudFMPPacket.toJson() = "+json);
        System.out.println(" ");
        System.out.println(" ");
        String encryptedJson = AsymmectricCryptography.encryptMessagePublicKey(json,  "04195304BEE8FA81246F23C119D8A294E481F1916B91112FFD402C72B157B934759B287C5654D510653136169495B2CFA0A72958C011D924A5AD651AAB23E0391A");

        System.out.println("encryptedJson = "+encryptedJson);
        System.out.println(" ");
        System.out.println(" ");
        String decryptedJson = AsymmectricCryptography.decryptMessagePrivateKey(encryptedJson, "9723c5ab03c0b73efa1a033fc481d8617a787af9aba240c955611240e8e8d343");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println("decryptedJson = "+decryptedJson);
        System.out.println(" ");
        System.out.println(" ");

        try {

            FMPPacket otherCloudFMPPacket = cloudFMPPacket.fromJson(decryptedJson);

            System.out.println("oTherCloudFMPPacket = "+otherCloudFMPPacket);

        } catch (Exception e) {
            e.printStackTrace();
        }

        /*
         * Create the plugin root
         */
        CloudServerCommunicationPluginRoot cloudServerCommunicationPluginRoot = new CloudServerCommunicationPluginRoot();

        /*
         * Start the process
         */
        cloudServerCommunicationPluginRoot.start();
    }
}
