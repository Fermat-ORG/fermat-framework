package unit.com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.version_1.structure.CryptoIndexDatabaseFactory;

import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.database.CryptoIndexDao;
import com.bitdubai.fermat_dmp_plugin.layer.world.crypto_index.developer.bitdubai.version_1.database.CryptoIndexDatabaseConstants;

import org.junit.Test;

import java.util.UUID;

/**
 * Created by francisco on 11/09/15.
 */
public class test2 {

    private PluginDatabaseSystem mockPluginDatabaseSystem;
    private UUID testOwnerId;
    CryptoIndexDao cryptoIndexDao;


    @Test
    public void test1() throws Exception{
        testOwnerId = UUID.randomUUID();
        mockPluginDatabaseSystem.createDatabase(testOwnerId,"Crypto_Index");
        mockPluginDatabaseSystem.openDatabase(testOwnerId, CryptoIndexDatabaseConstants.CRYPTO_INDEX_DATABASE_NAME);
        cryptoIndexDao = new CryptoIndexDao(mockPluginDatabaseSystem, testOwnerId);
        cryptoIndexDao.initializeDatabase();
        cryptoIndexDao.saveLastRateExchange("BTC","USD",0);
    }
}
