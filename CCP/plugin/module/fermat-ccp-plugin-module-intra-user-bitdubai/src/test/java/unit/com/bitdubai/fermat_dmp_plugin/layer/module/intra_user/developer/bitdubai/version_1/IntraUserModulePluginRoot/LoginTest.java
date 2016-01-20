//package unit.com.bitdubai.fermat_dmp_plugin.layer.module.intra_user.developer.bitdubai.version_1.IntraUserModulePluginRoot;
//
//import com.bitdubai.fermat_ccp_plugin.layer.module.intra_user.developer.bitdubai.version_1.utils.IntraUserSettings;
//import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
//import com.bitdubai.fermat_ccp_api.layer.actor.intra_wallet_user.interfaces.IntraWalletUserIdentityManager;
//import com.bitdubai.fermat_api.layer.dmp_identity.intra_user.interfaces.IntraUserIdentity;
//import com.bitdubai.fermat_api.layer.dmp_identity.intra_user.interfaces.IntraUserIdentityManager;
//import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantLoginIntraUserException;
//
//<<<<<<< HEAD:DMP/plugin/module/fermat-dmp-plugin-module-intra-user-bitdubai/src/test/java/unit/com/bitdubai/fermat_dmp_plugin/layer/module/intra_user/developer/bitdubai/version_1/IntraUserModulePluginRoot/LoginTest.java
//import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.interfaces.IntraUserManager;
//=======
//import com.bitdubai.fermat_ccp_api.layer.network_service.intra_user.interfaces.IntraUserManager;
//>>>>>>> 193a4ce563d3916b505332563ad81fe262f074f2:CCP/plugin/module/fermat-ccp-plugin-module-intra-user-bitdubai/src/test/java/unit/com/bitdubai/fermat_dmp_plugin/layer/module/intra_user/developer/bitdubai/version_1/IntraUserModulePluginRoot/LoginTest.java
//
//import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
//import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
//import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
//import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
//import com.bitdubai.fermat_dmp_plugin.layer.module.intra_user.developer.bitdubai.version_1.IntraWalletUserModulePluginRoot;
//import ErrorManager;
//
//
//import junit.framework.TestCase;
//
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
// * Created by natalia on 20/08/15.
// */
//
//@RunWith(MockitoJUnitRunner.class)
//public class LoginTest extends TestCase {
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
//    /**
//     * DealWithIntraUserIdentityManager Interface member variables.
//     */
//    @Mock
//    private IntraUserIdentityManager mockIntraUserIdentityManager;
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
//    private IntraWalletUserModulePluginRoot testIntraUserModulePluginRoot;
//
//    @Mock
//    IntraUserIdentity mockIntraUserIdentity;
//
//
//    IntraUserSettings intraUserSettings;
//    @Mock
//    XMLParser xmlParser;
//
//    private UUID pluginId;
//
//    private String intraUserPublicKey ;
//
//    @Before
//    public void setUp() throws Exception{
//
//
//        pluginId= UUID.randomUUID();
//        intraUserPublicKey = UUID.randomUUID().toString();
//
//        MockitoAnnotations.initMocks(this);
//
//        pluginId= UUID.randomUUID();
//        testIntraUserModulePluginRoot = new IntraWalletUserModulePluginRoot();
//        testIntraUserModulePluginRoot.setPluginFileSystem(mockPluginFileSystem);
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
//
//        when(mockPluginFileSystem.getTextFile(pluginId, pluginId.toString(), "intraUsersLogin", FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT)).thenReturn(mockIntraUserLoginXml);
//        when(mockIntraUserLoginXml.getContent()).thenReturn(XMLParser.parseObject(intraUserSettings));
//
//    }
//
//
//    @Test
//    public void loginIntraUserTest_LoggedOk_throwsCantLoginIntraUserException() throws Exception{
//
//
//        catchException(testIntraUserModulePluginRoot).login(intraUserPublicKey);
//
//        assertThat(caughtException()).isNull();
//
//    }
//
//
//    @Test
//    public void loginIntraUser_CantLogged_throwsCantLoginIntraUserException() throws Exception{
//
//        when(mockIntraUserLoginXml.getContent()).thenReturn(null);
//
//        catchException(testIntraUserModulePluginRoot).login(intraUserPublicKey);
//
//        assertThat(caughtException())
//                .isNotNull()
//                .isInstanceOf(CantLoginIntraUserException.class);
//
//
//    }
//}
//
