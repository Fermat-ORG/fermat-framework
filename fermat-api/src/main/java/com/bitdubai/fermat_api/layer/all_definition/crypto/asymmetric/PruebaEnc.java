/*
 * @#PruebaEnc.java - 2016
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric;

import com.google.common.base.Stopwatch;

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
            String msj = "You need to find a way to reduce compilation times when developing source code in our development environment (Android-Studio) to also have efficiency and effectiveness when working on the development and testing of code, there are currently members of fermat that have a high compilation time, the overall average is 2-5 minutes compilation about, some members have more time about 15-30 minutes and if this time is shared among all members of fermat it is a very large idle time when compiling and testing development.";
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
            System.out.println("");

            String sing = AsymmetricCryptography.createMessageSignature(eccKeyPair.getPrivateKey(), encryptedData);

            System.out.println(" sing : " + sing);
            System.out.println("");
            System.out.println(" valid sing : " + AsymmetricCryptography.verifyMessageSignature(sing, encryptedData, eccKeyPair.getPublicKey()));

            System.out.println(stopwatch.stop());

         /*     KeyPair keyPair = AsymmetricCryptography.createNewKeyPair();
            String hexPk = AsymmetricCryptography.getFermatCipher().convertHexString(keyPair.getPublic().getEncoded());

            System.out.println("HEX = "+hexPk);


            System.out.println("HEX2 = "+AsymmetricCryptography.getFermatCipher().getPublicKeyFromString(hexPk)); */


           // AsymmetricCryptography.getFermatCipher().getPublicKeyFromString(hexPk);


        /*    byte [] raw  = keyPair.getPrivate().getEncoded();

            Stopwatch stopwatch1 = Stopwatch.createStarted();
            AsymmetricCryptography.getFermatCipher().convertHexString(raw);
            System.out.println("method 1 = "+stopwatch1.stop());

            Stopwatch stopwatch2 = Stopwatch.createStarted();
            getHex(raw);
            System.out.println("method 2 = "+stopwatch2.stop());

            Stopwatch stopwatch3 = Stopwatch.createStarted();
            bytesToHex(raw);
            System.out.println("method 3 = "+stopwatch3.stop());

            Stopwatch stopwatch4 = Stopwatch.createStarted();
            BaseEncoding.base16().encode(raw);
            System.out.println("method 4 = "+stopwatch4.stop());

            String hex = "970D2F0A34BB179C9BD3A28C21F0CA3BE642E2DDA0C7F4FC5B6432E028451E1FA4CBBFCD90597F72704A66B77F19C102B02EEE5BE057F73458616350DDEAC50D65AC5D0032B3AAEE2083762182F892926E1B05241F5836FE9DBFA588852DD4B1CFACDCE25563939DB3DAB5DF964185A70AE23D546FB4D3FBC0C0C9F9E18D4DE1202155AC3D06E8E77D7228680DDBFD02B7C2EDF345DDC82C25B1E9A80876E6C839426B496560F77A2625FEB67EA796321C2632A312D7954E451C776E9E92BFB99D7D60A344B4A152A9E31B7EA007EEC99DD7D1AF8C35DC1CA263AC05FB5C437F0179E202EF730867ECE314C6E52D253386B553670B01262EE3814CBB5B7D4E14BDB49184689897A48B2B1390189D06696B8F6057AB573FF7B0A1E3B9252A264A4658F51656A43804C9FE8EE60EA83C932651A355F44427F2615B0CFA09A4215979DFDF78FFC90EC5233507E652255080EFBB8D26A06D872608F51ECC9915DF80970863D1CF6A971AF47F2DBAB029D6396704359FB73C73D9C7A4089C5EC79346A149D7386D54F930348BA09CB9A4759EBBEBD2F4EC0F759BB52152E972B697CA9F2EA15848F9CD72CF5F0CACD3F1CF0A2E0885CA775514EEACA5AABE9119AD5A0E4CEBA784E97D4C9C43F2454BD0F69285DB90046751828154BFC11B25873C8F8940D0352D9F98D0451AB927716E914E3745BB5FE72C7CB0377820708CB40E89094DE9853A5883AEAE1892C7BFAAB44BD585043972784F92A1C8847127082172EAA5FE0078EED0B9548E7D35BF0264F936E95D12EEB538548F3A2124F53FE17991686C826F0610D75832655CF0316F84E00002BCE89C1EB983D517A8982BFBD970DFD1DC7F2F5688EAEC6BB3C3726966138788BFA5F4984A372C8376E68A6A8564A375EB3657A44E3B9798361F1FA743A6B90805536149EE43ED66E5FEA4293D1FFE624AD62BE4D2B3ADFDACF58EACDB46967F747D7FF7154F69B80CE7D203596C25A2F578C5937933E6FEA09645CEB42F7B28959311D8AFD3792E43B36746C9FE088A23724B30E4689649DFC2C77935C474FF60051E50AD74CAD31BC62D0F00B2BAF5B591FE17235AC06A3F7D079B61190CDDA3E34DD21386F6655DBEEDD0E368330E8A50EE231FB32C573D75999ADFB20AD5A27BE681FE109A7EB04CB8D5A83839805CBF888E88B69D6B7986C2A9FBFE7387B49D0132D1EE37704AFFA47999A56676071E7D722F98642C9A840BFD5DA56BC0EFEBD6B2E402A168449CA346A70C5401DA9A482766822EF515087EA9E671833D64E897F92EBD370F458AF9CF42CEFAB586774A7F81A1E165516E01EC129DD77356B6454685A9DE4965002A1C746E2EEA325A4A4FAB2078C81FB8ED67A5DAB1A9442F26FC79CB6EF3F7DCC02C62070CA7F29DE882DC79037BA7501107409B20D79224D6A94A89BEA68018BA567D";

            Stopwatch stopwatch1 = Stopwatch.createStarted();
            AsymmetricCryptography.getFermatCipher().convertHexStringToByteArray(hex);
            System.out.println("method 1 = " + stopwatch1.stop());

            Stopwatch stopwatch2 = Stopwatch.createStarted();
            hexStringToByteArray(hex);
            System.out.println("method 2 = " + stopwatch2.stop());

            Stopwatch stopwatch3 = Stopwatch.createStarted();
            BaseEncoding.base16().decode(hex);
            System.out.println("method 3 = "+stopwatch3.stop());*/


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    static final String HEXES = "0123456789ABCDEF";


    public static String getHex( byte [] raw ) {
        if ( raw == null ) {
            return null;
        }
        final StringBuilder hex = new StringBuilder( 2 * raw.length );
        for ( final byte b : raw ) {
            hex.append(HEXES.charAt((b & 0xF0) >> 4)).append(HEXES.charAt((b & 0x0F)));
        }
        return hex.toString();
    }

    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

}
