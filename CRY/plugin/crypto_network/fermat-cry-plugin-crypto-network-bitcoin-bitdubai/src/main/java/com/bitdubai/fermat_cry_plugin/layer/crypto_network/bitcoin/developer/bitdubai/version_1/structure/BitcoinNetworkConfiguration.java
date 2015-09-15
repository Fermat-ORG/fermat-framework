package com.bitdubai.fermat_cry_plugin.layer.crypto_network.bitcoin.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin.BitcoinManager;

import org.bitcoinj.core.NetworkParameters;
import org.bitcoinj.params.MainNetParams;
import org.bitcoinj.params.RegTestParams;
import org.bitcoinj.params.TestNet3Params;

import javax.annotation.Nullable;

/**
 * Used to get the configuration of the Bitcoin network. One static method witch retrieves the network parameters
 * that we are using in all the plug in objects.
 */
public class BitcoinNetworkConfiguration  implements BitcoinManager{
    /**
     * Static method.
     * @return the nerwork parameters for the network specified in BitcoinManager's interface
     */
    public static NetworkParameters getNetworkConfiguration(){
        switch (BitcoinManager.NETWORK_CONNECTION){
            case Test3_NET:{
                return TestNet3Params.get();
            }
            case REGTEST_NET:{
                return RegTestParams.get();
            }
            case MAIN_NET:{
                return MainNetParams.get();
            }
            case FERMAT_MAIN:
                //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
                return null;
            case FERMAT_TEST:
                //TODO METODO CON RETURN NULL - OJO: solo INFORMATIVO de ayuda VISUAL para DEBUG - Eliminar si molesta
                return null;
            default:{
                return TestNet3Params.get();
            }
        }
    }
}
