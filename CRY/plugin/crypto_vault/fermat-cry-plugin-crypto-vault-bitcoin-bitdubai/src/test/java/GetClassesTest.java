import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.BitcoinCryptoVaultPluginRoot;

import org.junit.Test;

/**
 * Created by rodrigo on 2015.07.01..
 */
public class GetClassesTest {

    @Test
    public void getClasses(){
        BitcoinCryptoVaultPluginRoot root = new BitcoinCryptoVaultPluginRoot();
        for (String c : root.getClassesFullPath()){
            System.out.println(c.toString());
        }

    }
}
