package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.util;

/**
 * Created by rrequena on 26/04/16.
 */
public class HexadecimalConverter {

    /**
     * Convert a byte array into a hexadecimal format string
     * @param bytes
     * @return String
     * @throws Exception
     */
    public static String convertHexString(byte[] bytes) throws Exception {
        String result = "";
        for (int i=0; i < bytes.length; i++) {
            result += Integer.toString(( bytes[i] & 0xff ) + 0x100, 16).substring( 1 );
        }
        return result;
    }

    /**
     * Convert a hexadecimal format string into a byte array
     *
     * @param string
     * @return byte[]
     */
    public static byte[] convertHexStringToByteArray(String string) {
        int len = string.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(string.charAt(i), 16) << 4) + Character.digit(string.charAt(i+1), 16));
        }
        return data;
    }

}
