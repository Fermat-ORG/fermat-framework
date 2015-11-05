package unit.com.bitdubai.fermat_ccp_plugin.layer.identity.intra_wallet_user.developer.bitdubai.version_1.Database.IntraUserIdentityDao;

import com.bitdubai.fermat_api.layer.all_definition.enums.DeviceDirectory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.pip_Identity.developer.exceptions.CantCreateNewDeveloperException;
import com.bitdubai.fermat_ccp_plugin.layer.identity.intra_wallet_user.developer.bitdubai.version_1.database.IntraWalletUserIdentityDao;
import com.bitdubai.fermat_ccp_plugin.layer.identity.intra_wallet_user.developer.bitdubai.version_1.database.IntraWalletUserIdentityDatabaseConstants;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DeviceUser;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.UUID;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by natalia on 28/08/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class CreateNewUserTest {
    @Mock
    private IntraWalletUserIdentityDao identityDao;

    @Mock
    private Database mockDatabase;

    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;

    @Mock
    private DeviceUser mockDeviceUser;

    @Mock
    private PluginFileSystem mockPluginFileSystem;

    @Mock
    private DatabaseTable mockTable;
    @Mock
    private PluginTextFile  mockPluginTextFile;

    @Mock
    private PluginBinaryFile mockPluginBinaryFile;

    @Mock
    private List<DatabaseTableRecord> mockRecords;

    @Mock
    private DatabaseTableRecord mockRecord;

    private UUID testOwnerId1;

    private String intraUserPublicKey;
    @Before
    public void SetUp() throws Exception {
        testOwnerId1 = UUID.randomUUID();
        intraUserPublicKey = UUID.randomUUID().toString();
        when(mockPluginDatabaseSystem.openDatabase(any(UUID.class), anyString())).thenReturn(mockDatabase);
        when(mockDatabase.getTable(IntraWalletUserIdentityDatabaseConstants.INTRA_WALLET_USER_TABLE_NAME)).thenReturn(mockTable);
        when(mockTable.getRecords()).thenReturn(mockRecords);
        when(mockTable.getEmptyRecord()).thenReturn(mockRecord);

        when(mockPluginFileSystem.createTextFile(testOwnerId1, DeviceDirectory.LOCAL_USERS.getName(), "intraUserIdentityPrivateKey" + "_" + intraUserPublicKey, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT)).thenReturn(mockPluginTextFile);

        when(mockPluginFileSystem.createBinaryFile(testOwnerId1, DeviceDirectory.LOCAL_USERS.getName(), "intraUserIdentityProfileImage" + "_" + intraUserPublicKey, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT)).thenReturn(mockPluginBinaryFile);

        identityDao = new IntraWalletUserIdentityDao(mockPluginDatabaseSystem, mockPluginFileSystem, testOwnerId1);
    }

    @Test
    public void createNewUserTest_CreateOk_ThrowsCantCreateNewDeveloperException() throws Exception {
        identityDao.initializeDatabase();

        catchException(identityDao).createNewUser("alias", intraUserPublicKey, UUID.randomUUID().toString(), mockDeviceUser, new byte[10]);

        assertThat(caughtException()).isNull();
    }


    @Test
    public void createNewUserTest_CreateErrorTableNull_ThrowsCantCreateNewDeveloperException() throws Exception {
        identityDao.initializeDatabase();
        when(mockDatabase.getTable(IntraWalletUserIdentityDatabaseConstants.INTRA_WALLET_USER_TABLE_NAME)).thenReturn(null);

        catchException(identityDao).createNewUser("alias",intraUserPublicKey , UUID.randomUUID().toString(), mockDeviceUser, new byte[10]);

        assertThat(caughtException()).isNotNull().isInstanceOf(CantCreateNewDeveloperException.class);
    }

    @Test
    public void createNewUserTest_CreateError_CantCreateFile_ThrowsCantCreateNewDeveloperException() throws Exception {
        identityDao.initializeDatabase();
        when(mockPluginFileSystem.createBinaryFile(testOwnerId1, DeviceDirectory.LOCAL_USERS.getName(), "intraUserIdentityProfileImage" + "_" + intraUserPublicKey, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT)).thenReturn(null);

        catchException(identityDao).createNewUser("alias",intraUserPublicKey , UUID.randomUUID().toString(), mockDeviceUser, new byte[10]);

        assertThat(caughtException()).isNotNull().isInstanceOf(CantCreateNewDeveloperException.class);
    }

}
