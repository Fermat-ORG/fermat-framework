package unit.com.bitdubai.fermat_ccp_plugin.layer.identity.intra_wallet_user.developer.bitdubai.version_1.Database.IntraUserIdentityDao;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_api.layer.pip_Identity.developer.exceptions.CantCreateNewDeveloperException;
import com.bitdubai.fermat_ccp_plugin.layer.identity.intra_wallet_user.developer.bitdubai.version_1.database.IntraWalletUserIdentityDao;
import com.bitdubai.fermat_ccp_plugin.layer.identity.intra_wallet_user.developer.bitdubai.version_1.database.IntraWalletUserIdentityDatabaseConstants;
import com.bitdubai.fermat_ccp_plugin.layer.identity.intra_wallet_user.developer.bitdubai.version_1.exceptions.CantInitializeIntraWalletUserIdentityDatabaseException;
import com.bitdubai.fermat_ccp_plugin.layer.identity.intra_wallet_user.developer.bitdubai.version_1.exceptions.CantListIntraWalletUserIdentitiesException;
import com.bitdubai.fermat_ccp_plugin.layer.identity.intra_wallet_user.developer.bitdubai.version_1.exceptions.CantGetIntraWalletUserIdentityPrivateKeyException;
import com.bitdubai.fermat_ccp_plugin.layer.identity.intra_wallet_user.developer.bitdubai.version_1.exceptions.CantGetIntraWalletUserIdentityProfileImageException;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DeviceUser;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by angel on 25/8/15.
 */

@RunWith(MockitoJUnitRunner.class)
public class getIntraUserFromCurrentDeviceUserTest {

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

    private UUID testOwnerId1;

    @Before
    public void SetUp() throws CantCreateDatabaseException, CantInitializeIntraWalletUserIdentityDatabaseException, CantOpenDatabaseException, DatabaseNotFoundException, FileNotFoundException, CantCreateFileException {
        testOwnerId1 = UUID.randomUUID();

        when(mockPluginDatabaseSystem.openDatabase(any(UUID.class), anyString())).thenReturn(mockDatabase);
        when(mockDatabase.getTable(IntraWalletUserIdentityDatabaseConstants.INTRA_WALLET_USER_TABLE_NAME)).thenReturn(mockTable);
        when(mockDeviceUser.getPublicKey()).thenReturn(UUID.randomUUID().toString());
        identityDao = new IntraWalletUserIdentityDao(mockPluginDatabaseSystem, mockPluginFileSystem, testOwnerId1);
        identityDao.setPluginDatabaseSystem(mockPluginDatabaseSystem);
    }

    @Test
    public void getIntraUserFromCurrentDeviceUserTest_Getok() throws CantInitializeIntraWalletUserIdentityDatabaseException, CantOpenDatabaseException, DatabaseNotFoundException, CantGetIntraWalletUserIdentityPrivateKeyException, CantListIntraWalletUserIdentitiesException, CantCreateNewDeveloperException, CantGetIntraWalletUserIdentityProfileImageException, FileNotFoundException, CantCreateFileException {
        identityDao.initializeDatabase();
        identityDao.getAllIntraUserFromCurrentDeviceUser(mockDeviceUser);

        catchException(identityDao).getAllIntraUserFromCurrentDeviceUser(mockDeviceUser);
        assertThat(caughtException()).isNull();


    }

    @Test
    public void getIntraUserFromCurrentDeviceUserTest_GetError() throws CantInitializeIntraWalletUserIdentityDatabaseException, CantOpenDatabaseException, DatabaseNotFoundException, CantGetIntraWalletUserIdentityPrivateKeyException, CantListIntraWalletUserIdentitiesException, CantCreateNewDeveloperException, CantGetIntraWalletUserIdentityProfileImageException, FileNotFoundException, CantCreateFileException {
        identityDao.initializeDatabase();

        identityDao.getAllIntraUserFromCurrentDeviceUser(mockDeviceUser);

        when(mockDatabase.getTable(IntraWalletUserIdentityDatabaseConstants.INTRA_WALLET_USER_TABLE_NAME)).thenReturn(null);

        catchException(identityDao).getAllIntraUserFromCurrentDeviceUser(mockDeviceUser);
        assertThat(caughtException()).isNotNull().isInstanceOf(CantListIntraWalletUserIdentitiesException.class);


    }

}