package unit.com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.AsymmectricCryptography;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;

import org.junit.Test;

/**
 * Created by rodrigo on 9/7/15.
 */
public class CreatePrivateKeyStressLoad {

    @Test
    public void createPrivateKeyTest(){
        int times = 101;
        long now = System.currentTimeMillis();
        for (int i=0; i < times; i++){
            ECCKeyPair keyPair = new ECCKeyPair();
            keyPair.getPrivateKey();
            keyPair.getPublicKey();
        }
        long after = System.currentTimeMillis();
        long diff = after - now;
        System.out.println("total: " + diff + ". Time each: " + diff / times);
        // Duration (ms) of previous implementation: 39 ms each. not too bad. do we need bitcoinJ?
    }
}
