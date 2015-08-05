package test.com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareDeveloperDatabaseFactory;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTableRecord;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogLevel;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.exceptions.CantInitializeWalletContactsDatabaseException;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareDeveloperDatabaseFactory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

/**
 * Created by Nerio on 25/07/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetDatabaseTableContentTest {

    @Mock
    private DeveloperDatabaseTable developerDatabaseTable;
    @Mock
    private DeveloperObjectFactory developerObjectFactory;
    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;
    @Mock
    private Database mockDatabase;
    @Mock
    private DatabaseTableRecord mockDatabaseTableRecord;
    @Mock
    private DatabaseTable mockDatabaseTable;
    @Mock
    private DatabaseRecord mockDatabaseRecord;

    UUID pluginId;

    private WalletContactsMiddlewareDeveloperDatabaseFactory testDatabaseFactory;

    @Before
    public void setUp() throws Exception {
        pluginId = UUID.randomUUID();
        when(mockPluginDatabaseSystem.openDatabase(pluginId, pluginId.toString())).thenReturn(mockDatabase);
        testDatabaseFactory = new WalletContactsMiddlewareDeveloperDatabaseFactory(mockPluginDatabaseSystem,pluginId);
        testDatabaseFactory.initializeDatabase();

    }

    @Test
    public void getDatabaseTableContentTest_setValid_DeveloperDatabaseTableRecord() throws Exception{
        when(mockDatabase.getTable(anyString())).thenReturn(mockDatabaseTable);
        List<DatabaseTableRecord> databaseTableRecordList = new ArrayList<>();
        databaseTableRecordList.add(mockDatabaseTableRecord);
        List<DatabaseRecord> databaseRecordList = new ArrayList<>();
        databaseRecordList.add(mockDatabaseRecord);
        when(mockDatabaseTable.getRecords()).thenReturn(databaseTableRecordList);
        when(mockDatabaseTableRecord.getValues()).thenReturn(databaseRecordList);
        when(mockDatabaseRecord.getValue()).thenReturn(pluginId.toString());
        testDatabaseFactory.getDatabaseTableContent(developerObjectFactory, developerDatabaseTable);
    }
}
