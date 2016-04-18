/*
 * @#PruebaEnc.java - 2016
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric;

import com.google.common.base.Stopwatch;

import java.security.Key;
import java.security.KeyPair;

/**
 * The Class <code>com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.PruebaEnc</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 17/04/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class PruebaEnc {

    public static void main(String [] args){


        try {

            Stopwatch stopwatch = Stopwatch.createStarted();

            //String msj = "Hello world!";
            String msj = "Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!Hello world!";
            //System.out.println("msj = " + msj);

            ECCKeyPair eccKeyPair = new ECCKeyPair();
            System.out.println("publicKey : " + eccKeyPair.getPublicKey());
            System.out.println(" ----------------------------------------------- ");
            System.out.println("privateKey : " + eccKeyPair.getPrivateKey());

            String encryptedData = AsymmetricCryptography.encryptMessagePublicKey(msj, eccKeyPair.getPublicKey());

            System.out.println(" ----------------------------------------------- ");
            System.out.println(" encryptedData : " + encryptedData);
            System.out.println(" ----------------------------------------------- ");

            String decryptedData = AsymmetricCryptography.decryptMessagePrivateKey(encryptedData, eccKeyPair.getPrivateKey());
            System.out.println(" decryptedData : " + decryptedData);

            System.out.println(stopwatch.stop());

        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
