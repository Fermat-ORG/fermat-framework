package LoggerTests;

import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.BitcoinCryptoVaultPluginRoot;

import junit.framework.Assert;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by rodrigo on 2015.07.04..
 */

/**
 * This Test will make sure that all Classes included in getClassesFullPath exists and are real clases
 */
public class GenerateClassTest {
    @Test
    public void generateClassesTree() throws ClassNotFoundException {
        List<Class<?>> classes = ClassFinder.find(BitcoinCryptoVaultPluginRoot.class.getPackage().getName());
        BitcoinCryptoVaultPluginRoot root = new BitcoinCryptoVaultPluginRoot();

        for (String myClass : root.getClassesFullPath()){
            /**
             * True if it exists
             */
           assertTrue(classes.contains(Class.forName(myClass)));
        }



    }
}
