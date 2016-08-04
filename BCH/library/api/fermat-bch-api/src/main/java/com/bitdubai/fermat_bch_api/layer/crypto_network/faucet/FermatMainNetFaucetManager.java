package com.bitdubai.fermat_bch_api.layer.crypto_network.faucet;

import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by rodrigo on 7/26/16.
 */
public class FermatMainNetFaucetManager {
    private final String FAUCET_IP = "52.27.68.19";
    private final int FAUCET_PORT = 4499;


    /**
     *
     * @param cryptoAddress
     * @param amount
     * @throws CantGetCoinsFromFaucetException
     */
    public void giveMeCoins(CryptoAddress cryptoAddress, long amount) throws CantGetCoinsFromFaucetException {
        if (cryptoAddress == null)
            throw new CantGetCoinsFromFaucetException(null, "Address can't be null", "invalid parameters");


        Socket faucetSocket = null;
        DataOutputStream os = null;
        DataInputStream is = null;

        try {
            SocketAddress faucetServer = new InetSocketAddress(FAUCET_IP, FAUCET_PORT);
            faucetSocket = new Socket(FAUCET_IP, FAUCET_PORT);

            os = new DataOutputStream(faucetSocket.getOutputStream());
            is = new DataInputStream(faucetSocket.getInputStream());

            if (faucetSocket != null && os != null && is != null) {

                os.writeBytes(cryptoAddress.getAddress() + "\n");
                os.writeBytes(String.valueOf(amount) + "\n");

                os.close();
                is.close();
                faucetSocket.close();
            }
        } catch (Exception e) {
            throw new CantGetCoinsFromFaucetException(e, "error requesting coins to faucet." , "cant connect.");
        }
    }
}
