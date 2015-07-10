package unit.com.bitdubai.fermat_dmp_plugin.layer.basic_wallet.bitcoin_wallet.developer.bitdubai.version_1.structure.BitcoinWalletBasicWallet;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_dmp_plugin.layer.basic_wallet.bitcoin_wallet.developer.bitdubai.version_1.structure.BitcoinWalletBasicWallet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.fest.assertions.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Created by jorgegonzalez on 2015.07.10..
 */
@RunWith(MockitoJUnitRunner.class)
public class CreateTest {

    @Mock
    private PluginFileSystem mockPluginFileSystem;
    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;
    @Mock
    private DatabaseFactory mockDatabaseFactory;
    @Mock
    private DatabaseTableFactory mockTableFactory;
    @Mock
    private Database mockDatabase;
    @Mock
    private DatabaseTable mockTable;
    @Mock
    private DatabaseTableRecord mockTableRecord;

    private ErrorManager mockErrorManager;

    private PluginTextFile mockWalletIdsFile;
    private BitcoinWalletBasicWallet testBasicWallet;
    private UUID testBasicWalletId;

    @Before
    public void setUpBasicWallet(){
        mockErrorManager = new MockErrorManager();

        testBasicWalletId = UUID.randomUUID();
        testBasicWallet = new BitcoinWalletBasicWallet(testBasicWalletId);
        testBasicWallet.setErrorManager(mockErrorManager);
        testBasicWallet.setPluginDatabaseSystem(mockPluginDatabaseSystem);
        testBasicWallet.setPluginFileSystem(mockPluginFileSystem);
    }

    @Before
    public void setUpWalletIdsFile(){
        mockWalletIdsFile = new MockWalletIdsFile();
    }

    @Test
    public void Create_ValidWalletId_ReturnsInternalWalletId() throws Exception{
        when(mockPluginDatabaseSystem.createDatabase(any(UUID.class), anyString())).thenReturn(mockDatabase);
        when(mockDatabase.getDatabaseFactory()).thenReturn(mockDatabaseFactory);
        when(mockDatabaseFactory.newTableFactory(any(UUID.class), anyString())).thenReturn(mockTableFactory);
        when(mockDatabase.getTable(anyString())).thenReturn(mockTable);
        when(mockTable.getEmptyRecord()).thenReturn(mockTableRecord);
        when(mockPluginFileSystem.createTextFile(any(UUID.class), anyString(), anyString(), any(FilePrivacy.class), any(FileLifeSpan.class))).thenReturn(mockWalletIdsFile);

        UUID internalWalletId = testBasicWallet.create(UUID.randomUUID());
        assertThat(internalWalletId).isNotNull();
    }

}
