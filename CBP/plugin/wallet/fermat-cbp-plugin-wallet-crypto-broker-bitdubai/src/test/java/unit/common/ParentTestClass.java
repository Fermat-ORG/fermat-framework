package unit.common;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.CryptoBrokerWalletPluginRoot;
import com.bitdubai.fermat_cbp_plugin.layer.wallet.crypto_broker.developer.bitdubai.version_1.database.CryptoBrokerWalletDatabaseDao;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by nelsonalfo on 25/07/16.
 */
public abstract class ParentTestClass {
    @Mock
    protected Database database;

    @Mock
    protected CryptoBrokerWalletPluginRoot pluginRoot;

    protected List<DatabaseTableRecord> availableSaleRecordsFromTable;
    protected List<DatabaseTableRecord> creditRecords;
    protected List<DatabaseTableRecord> debitRecords;

    protected CryptoBrokerWalletDatabaseDao cryptoBrokerWalletDatabaseDaoSpy;

    @Before
    public void setUp() {
        creditRecords = new ArrayList<>();
        debitRecords = new ArrayList<>();

        cryptoBrokerWalletDatabaseDaoSpy = Mockito.spy(new CryptoBrokerWalletDatabaseDao(database, pluginRoot));
        availableSaleRecordsFromTable = null;
    }
}
