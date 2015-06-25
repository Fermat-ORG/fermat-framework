package DeveloperDatabase;

import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.DeveloperUtils.CryptoVaultDatabaseManager;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.DeveloperUtils.CryptoVaultDeveloperDatabaseFactory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

/**
 * Created by rodrigo on 2015.06.25..
 */
@RunWith(MockitoJUnitRunner.class)
public class GetDevelopersDatabases {

    @Test
    public void getDatabase(){
        CryptoVaultDeveloperDatabaseFactory factory = new CryptoVaultDeveloperDatabaseFactory();
        factory.getNewDeveloperDatabase("asdasd", "asd");


        CryptoVaultDatabaseManager db = new CryptoVaultDatabaseManager();

    }
}
