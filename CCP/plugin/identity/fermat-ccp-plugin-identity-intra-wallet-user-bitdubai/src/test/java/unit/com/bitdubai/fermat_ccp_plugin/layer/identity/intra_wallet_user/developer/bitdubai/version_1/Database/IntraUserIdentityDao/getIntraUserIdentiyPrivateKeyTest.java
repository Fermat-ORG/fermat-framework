/*
package unit.com.bitdubai.fermat_ccp_plugin.layer.identity.intra_wallet_user.developer.bitdubai.version_1.Database.IntraUserIdentityDao;

import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.DeviceDirectory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginBinaryFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_api.layer.pip_Identity.developer.exceptions.CantCreateNewDeveloperException;
import com.bitdubai.fermat_ccp_plugin.layer.identity.intra_wallet_user.developer.bitdubai.version_1.IntraWalletUserIdentityPluginRoot;
import com.bitdubai.fermat_ccp_plugin.layer.identity.intra_wallet_user.developer.bitdubai.version_1.database.IntraWalletUserIdentityDao;
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

*/
/**
 * Created by angel on 25/8/15.
 *//*


@RunWith(MockitoJUnitRunner.class)
public class getIntraUserIdentiyPrivateKeyTest {

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
    PluginTextFile file;

    @Mock
    PluginBinaryFile fileImage;

    private UUID testOwnerId1;

    String publicKey;

    @Before
    public void SetUp() throws CantCreateDatabaseException, CantInitializeIntraWalletUserIdentityDatabaseException, CantOpenDatabaseException, DatabaseNotFoundException, FileNotFoundException, CantCreateFileException {
        testOwnerId1 = UUID.randomUUID();

        ECCKeyPair eccKeyPair = new ECCKeyPair();
        publicKey = eccKeyPair.getPublicKey();

        when(mockPluginDatabaseSystem.openDatabase(any(UUID.class), anyString())).thenReturn(mockDatabase);

        when(mockPluginFileSystem.getTextFile(testOwnerId1,
                DeviceDirectory.LOCAL_USERS.getName(),
                IntraWalletUserIdentityPluginRoot.INTRA_WALLET_USERS_PRIVATE_KEYS_FILE_NAME + "_" + publicKey,
                FilePrivacy.PRIVATE,
                FileLifeSpan.PERMANENT
        )).thenReturn(file);


        identityDao = new IntraWalletUserIdentityDao(mockPluginDatabaseSystem, mockPluginFileSystem, testOwnerId1);
        identityDao.setPluginDatabaseSystem(mockPluginDatabaseSystem);
    }

    @Test
    public void getIntraUserProfileImagePrivateKeyTest_GetOk() throws CantInitializeIntraWalletUserIdentityDatabaseException, CantOpenDatabaseException, DatabaseNotFoundException, CantGetIntraWalletUserIdentityPrivateKeyException, CantListIntraWalletUserIdentitiesException, CantCreateNewDeveloperException, CantGetIntraWalletUserIdentityProfileImageException, FileNotFoundException, CantCreateFileException {
        identityDao.initializeDatabase();
        identityDao.getIntraUserIdentiyPrivateKey(publicKey);

        when(mockPluginFileSystem.getBinaryFile(testOwnerId1,
                DeviceDirectory.LOCAL_USERS.getName(),
                IntraWalletUserIdentityPluginRoot.INTRA_WALLET_USERS_PROFILE_IMAGE_FILE_NAME + "_" + publicKey,
                FilePrivacy.PRIVATE,
                FileLifeSpan.PERMANENT
        )).thenReturn(fileImage);

        identityDao.getIntraUserProfileImagePrivateKey(publicKey);

    }

    @Test
    public void getIntraUserProfileImagePrivateKeyTest_GetError() throws Exception{
        identityDao.initializeDatabase();
        identityDao.getIntraUserIdentiyPrivateKey(publicKey);

        when(mockPluginFileSystem.getBinaryFile(testOwnerId1,
                DeviceDirectory.LOCAL_USERS.getName(),
                IntraWalletUserIdentityPluginRoot.INTRA_WALLET_USERS_PROFILE_IMAGE_FILE_NAME + "_" + publicKey,
                FilePrivacy.PRIVATE,
                FileLifeSpan.PERMANENT
        )).thenReturn(null);

        catchException(identityDao).getIntraUserProfileImagePrivateKey(publicKey);

        assertThat(caughtException()).isNotNull().isInstanceOf(CantGetIntraWalletUserIdentityProfileImageException.class);
    }
}
*/
