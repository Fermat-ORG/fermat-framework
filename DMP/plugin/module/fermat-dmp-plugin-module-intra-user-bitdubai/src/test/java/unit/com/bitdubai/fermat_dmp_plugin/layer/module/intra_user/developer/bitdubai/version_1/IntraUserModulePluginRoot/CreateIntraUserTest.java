package unit.com.bitdubai.fermat_dmp_plugin.layer.module.intra_user.developer.bitdubai.version_1.IntraUserModulePluginRoot;

import com.bitdubai.fermat_api.layer.dmp_actor.Actor;
import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.interfaces.ActorIntraUserManager;
import com.bitdubai.fermat_api.layer.dmp_identity.intra_user.interfaces.IntraUserIdentity;
import com.bitdubai.fermat_api.layer.dmp_identity.intra_user.interfaces.IntraUserIdentityManager;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.exceptions.CouldNotCreateIntraUserException;
import com.bitdubai.fermat_api.layer.dmp_module.intra_user.interfaces.IntraUserLoginIdentity;
import com.bitdubai.fermat_api.layer.dmp_network_service.intra_user.interfaces.IntraUserManager;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_dmp_plugin.layer.module.intra_user.developer.bitdubai.version_1.IntraUserModulePluginRoot;
import com.bitdubai.fermat_dmp_plugin.layer.module.intra_user.developer.bitdubai.version_1.structure.IntraUsersModuleLoginConstants;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DeviceUserManager;

import junit.framework.TestCase;

import org.fest.assertions.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.anyByte;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

/**
 * Created by natalia on 19/08/15.
 */

@RunWith(MockitoJUnitRunner.class)
public class CreateIntraUserTest extends TestCase {

    /**
     * DealsWithErrors interface Mocked
     */
    @Mock
    private ErrorManager mockErrorManager;

    /**
     * UsesFileSystem Interface member variables.
     */
    @Mock
    private PluginFileSystem mockPluginFileSystem;



    /**
     * DealWithIntraUserIdentityManager Interface member variables.
     */
    @Mock
    private IntraUserIdentityManager mockIntraUserIdentityManager;


    /**
     * DealWithActorIntraUserManager Interface member variables.
     */
    @Mock
    private ActorIntraUserManager mockActorIntraUserManager;


    /**
     * DealWithIntraUserNetworkServiceManager Interface member variables.
     */
    @Mock
    private IntraUserManager mockIntraUserNetworkServiceManager;

    @Mock
    private PluginTextFile mockIntraUserLoginXml;


    private IntraUserModulePluginRoot testIntraUserModulePluginRoot;

    private IntraUserLoginIdentity testIntraUser;


    @Mock
    IntraUserIdentity mockIntraUserIdentity;


    UUID pluginId;
    private String intraUserAlias = "intraUserTest";

    private byte[] intraUserImageProfile = new byte[0];

    @Before
    public void setUp() throws Exception{


        pluginId= UUID.randomUUID();

        MockitoAnnotations.initMocks(this);

        pluginId= UUID.randomUUID();
        testIntraUserModulePluginRoot = new IntraUserModulePluginRoot();
        testIntraUserModulePluginRoot.setPluginFileSystem(mockPluginFileSystem);

        testIntraUserModulePluginRoot.setErrorManager(mockErrorManager);
        testIntraUserModulePluginRoot.setIntraUserManager(mockIntraUserIdentityManager);

        testIntraUserModulePluginRoot.setActorIntraUserManager(mockActorIntraUserManager);
        testIntraUserModulePluginRoot.setIntraUserNetworkServiceManager(mockIntraUserNetworkServiceManager);

        setUpMockitoRules();
        testIntraUserModulePluginRoot.setId(pluginId);

        testIntraUserModulePluginRoot.start();

    }

    public void setUpMockitoRules()  throws Exception{
        StringBuffer strXml = new StringBuffer();

        strXml.append("<" + IntraUsersModuleLoginConstants.INTRA_USER_MODULE_XML_ROOT + ">");
        strXml.append("<" + IntraUsersModuleLoginConstants.INTRA_USER_MODULE_XML_ID_NODE + ">" + UUID.randomUUID().toString() + "</" + IntraUsersModuleLoginConstants.INTRA_USER_MODULE_XML_ID_NODE + ">");
        strXml.append("</" + IntraUsersModuleLoginConstants.INTRA_USER_MODULE_XML_ROOT + ">");

        when(mockPluginFileSystem.getTextFile(pluginId, pluginId.toString(), "intraUsersLogin", FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT)).thenReturn(mockIntraUserLoginXml);
        when(mockIntraUserLoginXml.getContent()).thenReturn(strXml.toString());

        when(mockIntraUserIdentityManager.createNewIntraUser(intraUserAlias,intraUserImageProfile)).thenReturn(mockIntraUserIdentity);


    }

    @Test
    public void createIntraUserTest_CreateOk_throwsCouldNotCreateIntraUserException() throws Exception{


        testIntraUser=testIntraUserModulePluginRoot.createIntraUser(intraUserAlias, intraUserImageProfile);

        Assertions.assertThat(testIntraUser)
                .isNotNull()
                .isInstanceOf(IntraUserLoginIdentity.class);

    }


    @Test
    public void createIntraUserTest_Exception_throwsCouldNotCreateIntraUserException() throws Exception{

        when(mockIntraUserIdentityManager.createNewIntraUser(intraUserAlias, null)).thenReturn(null);

        catchException(testIntraUserModulePluginRoot).createIntraUser(intraUserAlias, null);

        assertThat(caughtException())
                .isNotNull()
                .isInstanceOf(CouldNotCreateIntraUserException.class);


    }
}
