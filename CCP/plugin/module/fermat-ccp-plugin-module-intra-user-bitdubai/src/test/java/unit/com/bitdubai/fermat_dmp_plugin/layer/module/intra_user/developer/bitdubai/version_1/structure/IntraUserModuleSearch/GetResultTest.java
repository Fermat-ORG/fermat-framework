//package unit.com.bitdubai.fermat_dmp_plugin.layer.module.intra_user.developer.bitdubai.version_1.structure.IntraUserModuleSearch;
//
//import com.bitdubai.fermat_ccp_api.layer.actor.intra_wallet_user.interfaces.IntraWalletUserManager;
//import com.bitdubai.fermat_api.layer.dmp_identity.intra_user.interfaces.IntraUserIdentityManager;
//<<<<<<< HEAD:DMP/plugin/module/fermat-dmp-plugin-module-intra-user-bitdubai/src/test/java/unit/com/bitdubai/fermat_dmp_plugin/layer/module/intra_user/developer/bitdubai/version_1/structure/IntraUserModuleSearch/GetResultTest.java
//import com.bitdubai.fermat_api.layer.dmp_module.intra_user.exceptions.CantGetIntraUserSearchResult;
//import com.bitdubai.fermat_api.layer.dmp_module.intra_user.interfaces.IntraUserInformation;
//import com.bitdubai.fermat_ccp_api.layer.network_service.intra_actor.interfaces.IntraUserManager;
//=======
//import com.bitdubai.fermat_ccp_api.layer.module.intra_user.exceptions.CantGetIntraUserSearchResult;
//import com.bitdubai.fermat_ccp_api.layer.module.intra_user.interfaces.IntraUserInformation;
//import com.bitdubai.fermat_ccp_api.layer.network_service.intra_user.interfaces.IntraUserManager;
//>>>>>>> 193a4ce563d3916b505332563ad81fe262f074f2:CCP/plugin/module/fermat-ccp-plugin-module-intra-user-bitdubai/src/test/java/unit/com/bitdubai/fermat_dmp_plugin/layer/module/intra_user/developer/bitdubai/version_1/structure/IntraUserModuleSearch/GetResultTest.java
//import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
//import com.bitdubai.fermat_dmp_plugin.layer.module.intra_user.developer.bitdubai.version_1.structure.IntraUserModuleSearch;
//import ErrorManager;
//import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DeviceUserManager;
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
//import java.util.List;
//
//import static com.googlecode.catchexception.CatchException.catchException;
//import static com.googlecode.catchexception.CatchException.caughtException;
//import static org.fest.assertions.api.Assertions.assertThat;
//
///**
// * Created by natalia on 20/08/15.
// */
//
//@RunWith(MockitoJUnitRunner.class)
//public class GetResultTest extends TestCase {
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
//    /**
//     * DealWithDeviceUserManager Interface member variables.
//     */
//    @Mock
//    private DeviceUserManager mockDeviceUserManager;
//
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
//    private IntraWalletUserManager mockIntraWalletUserManager;
//
//
//    /**
//     * DealWithIntraUserNetworkServiceManager Interface member variables.
//     */
//    @Mock
//    private IntraUserManager mockIntraUserNetworkServiceManager;
//
//
//
//
//    private IntraUserModuleSearch testIntraUserModuleSearch;
//
//
//    private String intraUserAlias = "intraUserTest";
//
//    private List<IntraUserInformation> intraUserInformationList;
//
//
//
//    @Before
//    public void setUp() throws Exception{
//        MockitoAnnotations.initMocks(this);
//
//        setUpMockitoRules();
//
//        testIntraUserModuleSearch = new IntraUserModuleSearch(mockIntraUserNetworkServiceManager, mockIntraUserIdentityManager);
//
//
//    }
//
//    public void setUpMockitoRules()  throws Exception{
//
//    }
//
//
//    @Test
//    public void createIntraUserTest_CreateOk_throwsCantGetIntraUserSearchResult() throws Exception{
//
//        testIntraUserModuleSearch.setNameToSearch(intraUserAlias);
//
//        intraUserInformationList =testIntraUserModuleSearch.getResult();
//
//        Assertions.assertThat(intraUserInformationList)
//                .isNotNull();
//
//    }
//
//
//    @Test
//    public void createIntraUserTest_Exception_throwsCantGetIntraUserSearchResult() throws Exception{
//
//
//        testIntraUserModuleSearch = new IntraUserModuleSearch(null, mockIntraUserIdentityManager);
//
//        testIntraUserModuleSearch.setNameToSearch(intraUserAlias);
//
//        catchException(testIntraUserModuleSearch).getResult();
//
//        assertThat(caughtException())
//                .isNotNull()
//                .isInstanceOf(CantGetIntraUserSearchResult.class);
//
//
//    }
//}
