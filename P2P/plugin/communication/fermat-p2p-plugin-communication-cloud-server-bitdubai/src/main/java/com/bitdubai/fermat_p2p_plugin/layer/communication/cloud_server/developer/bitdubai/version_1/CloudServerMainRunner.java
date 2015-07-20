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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

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

       /* FMPPacket cloudFMPPacket = null;

        try {

            cloudFMPPacket = FMPPacketFactory.constructCloudFMPPacketEncryptedAndSinged("04195304BEE8FA81246F23C119D8A294E481F1916B91112FFD402C72B157B934759B287C5654D510653136169495B2CFA0A72958C011D924A5AD651AAB23E0391A", //Sender
                                                                                        "04496132A11A5699EEAFA2346433C0A51AFFE50AA6F97272E91397D011413F6E3708AA911C761AFCBE61DCEE581EA7E660B2C03834F892DAA72575DB082D916A7D", //Destination
                                                                                        "Prueba de contenido de msj", //message
                                                                                        FMPPacket.FMPPacketType.CONNECTION_ACCEPT,
                                                                                        NetworkServices.UNDEFINED,
                                                                                        "9723c5ab03c0b73efa1a033fc481d8617a787af9aba240c955611240e8e8d343");


        String json = cloudFMPPacket.toJson();
        System.out.println("cloudFMPPacket.toJson() = "+json);
        System.out.println("json.length() " + json.length());


        String compressJson =  CloudServerMainRunner.compress(json);
        System.out.println("compressJson = "+compressJson);
        System.out.println("compressJson.length() "+compressJson.length());




        String encryptedJson = AsymmectricCryptography.encryptMessagePublicKey(json, "04195304BEE8FA81246F23C119D8A294E481F1916B91112FFD402C72B157B934759B287C5654D510653136169495B2CFA0A72958C011D924A5AD651AAB23E0391A");

        System.out.println("encryptedJson = "+encryptedJson);


        String decryptedJson = AsymmectricCryptography.decryptMessagePrivateKey(encryptedJson, "9723c5ab03c0b73efa1a033fc481d8617a787af9aba240c955611240e8e8d343");
        System.out.println(" ");
        System.out.println("decryptedJson = "+decryptedJson);
        System.out.println(" ");
        System.out.println(" ");

        boolean result = AsymmectricCryptography.verifyMessageSignature(cloudFMPPacket.getSignature(), cloudFMPPacket.getMessage(), cloudFMPPacket.getSender());

        System.out.println(" result = "+result);

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


    public static String compress(String str) throws IOException {

        if (str == null || str.length() == 0) {
            return str;
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        gzip.write(str.getBytes());
        gzip.close();

        return out.toString("UTF-8");
    }
}
