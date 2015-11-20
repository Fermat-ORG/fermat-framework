//package unit.com.bitdubai.fermat_dmp_plugin.layer.module.intra_user.developer.bitdubai.version_1.IntraUserModulePluginRoot;
//
//import com.bitdubai.fermat_api.layer.all_definition.IntraUsers.IntraUserSettings;
//import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
//import com.bitdubai.fermat_ccp_api.layer.actor.intra_wallet_user.interfaces.IntraWalletUserIdentityManager;
//
//<<<<<<< HEAD:DMP/plugin/module/fermat-dmp-plugin-module-intra-user-bitdubai/src/test/java/unit/com/bitdubai/fermat_dmp_plugin/layer/module/intra_user/developer/bitdubai/version_1/IntraUserModulePluginRoot/CreateIntraUserTest.java
//import com.bitdubai.fermat_api.layer.dmp_module.intra_user.exceptions.CouldNotCreateIntraUserException;
//import com.bitdubai.fermat_api.layer.dmp_module.intra_user.interfaces.IntraUserLoginIdentity;
//import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.interfaces.IntraUserManager;
//=======
//import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CouldNotCreateIntraUserException;
//import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserLoginIdentity;
//import com.bitdubai.fermat_ccp_api.layer.network_service.intra_user.interfaces.IntraUserManager;
//>>>>>>> 193a4ce563d3916b505332563ad81fe262f074f2:CCP/plugin/module/fermat-ccp-plugin-module-intra-user-bitdubai/src/test/java/unit/com/bitdubai/fermat_dmp_plugin/layer/module/intra_user/developer/bitdubai/version_1/IntraUserModulePluginRoot/CreateIntraUserTest.java
//import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
//import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
//import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
//import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
//import com.bitdubai.fermat_ccp_api.layer.identity.intra_wallet_user.interfaces.IntraWalletUser;
//import com.bitdubai.fermat_dmp_plugin.layer.module.intra_user.developer.bitdubai.version_1.IntraWalletUserModulePluginRoot;
//import ErrorManager;
//
//import junit.framework.TestCase;
//
//import org.fest.assertions.api.Assertions;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.runners.MockitoJUnitRunner;
//
//import java.util.UUID;
//
//import static com.googlecode.catchexception.CatchException.catchException;
//import static com.googlecode.catchexception.CatchException.caughtException;
//import static org.fest.assertions.api.Assertions.assertThat;
//import static org.mockito.Mockito.when;
//
///**
// * Created by natalia on 19/08/15.
// */
//
//@RunWith(MockitoJUnitRunner.class)
//public class CreateIntraUserTest extends TestCase {
//
//    /**
//     * DealsWithErrors interface Mocked
//     */
//    @Mock
//    private ErrorManager mockErrorManager;
//
//    /**
//     * UsesFileSystem Interface member variables.
//     */
//    @Mock
//    private PluginFileSystem mockPluginFileSystem;
//
//
//
//    /**
//     * DealWithIntraUserIdentityManager Interface member variables.
//     */
//    @Mock
//    private IntraWalletUserIdentityManager mockIntraUserIdentityManager;
//
//
//    /**
//     * DealWithActorIntraUserManager Interface member variables.
//     */
//    @Mock
//    private IntraWalletUserIdentityManager mockIntraWalletUserManager;
//
//
//    /**
//     * DealWithIntraUserNetworkServiceManager Interface member variables.
//     */
//    @Mock
//    private IntraUserManager mockIntraUserNetworkServiceManager;
//
//    @Mock
//    private PluginTextFile mockIntraUserLoginXml;
//
//
//    private IntraUserSettings intraUserSettings;
//
//    private IntraWalletUserModulePluginRoot testIntraUserModulePluginRoot;
//
//    private IntraUserLoginIdentity testIntraUser;
//
//
//    @Mock
//    IntraWalletUser mockIntraUserIdentity;
//
//
//    UUID pluginId;
//    private String intraUserAlias = "intraUserTest";
//
//    private byte[] intraUserImageProfile = new byte[10];
//
//    @Before
//    public void setUp() throws Exception{
//
//        MockitoAnnotations.initMocks(this);
//
//        pluginId= UUID.randomUUID();
//        testIntraUserModulePluginRoot = new IntraWalletUserModulePluginRoot();
//        testIntraUserModulePluginRoot.setPluginFileSystem(mockPluginFileSystem);
//
//        testIntraUserModulePluginRoot.setErrorManager(mockErrorManager);
//        testIntraUserModulePluginRoot.setIntraUserManager(mockIntraUserIdentityManager);
//
//        testIntraUserModulePluginRoot.setIntraWalletUserIdentityManager(mockIntraWalletUserManager);
//        testIntraUserModulePluginRoot.setIntraUserNetworkServiceManager(mockIntraUserNetworkServiceManager);
//
//        setUpMockitoRules();
//        testIntraUserModulePluginRoot.setId(pluginId);
//
//        testIntraUserModulePluginRoot.start();
//
//    }
//
//    public void setUpMockitoRules()  throws Exception{
//        intraUserSettings = new IntraUserSettings();
//        intraUserSettings.setLoggedInPublicKey(UUID.randomUUID().toString());
//        when(mockPluginFileSystem.getTextFile(pluginId, pluginId.toString(), "intraUsersLogin", FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT)).thenReturn(mockIntraUserLoginXml);
//         when(mockIntraUserIdentityManager.createNewIntraWalletUser(intraUserAlias, intraUserImageProfile)).thenReturn(mockIntraUserIdentity);
//        when(mockIntraUserLoginXml.getContent()).thenReturn(XMLParser.parseObject(intraUserSettings));
//        when(mockIntraUserIdentity.getAlias()).thenReturn(intraUserAlias);
//        when(mockIntraUserIdentity.getPublicKey()).thenReturn(UUID.randomUUID().toString());
//        when(mockIntraUserIdentity.getProfileImage()).thenReturn(intraUserImageProfile);
//    }
//
//
//    @Test
//    public void createIntraUserTest_CreateOk_throwsCouldNotCreateIntraUserException() throws Exception{
//
//
//        testIntraUser=testIntraUserModulePluginRoot.createIntraUser(intraUserAlias, intraUserImageProfile);
//
//        Assertions.assertThat(testIntraUser)
//                .isNotNull()
//                .isInstanceOf(IntraUserLoginIdentity.class);
//
//    }
//
//
//    @Test
//    public void createIntraUserTest_Exception_throwsCouldNotCreateIntraUserException() throws Exception{
//
//        when(mockIntraUserIdentityManager.createNewIntraWalletUser(intraUserAlias, null)).thenReturn(null);
//
//        catchException(testIntraUserModulePluginRoot).createIntraUser(intraUserAlias, null);
//
//        assertThat(caughtException())
//                .isNotNull()
//                .isInstanceOf(CouldNotCreateIntraUserException.class);
//
//
//    }
//}
