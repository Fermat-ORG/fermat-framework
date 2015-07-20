package com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.TestExtraUserDeveloperDatabaseFactory;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PlatformDatabaseSystem;
import com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.ExtraUserDeveloperDatabaseFactory;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;

import junit.framework.TestCase;
import org.fest.assertions.api.Assertions;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mock;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by francisco on 08/07/15.
 */
public class TestGetDatabaseList extends TestCase {
    @Mock
    private  ExtraUserDeveloperDatabaseFactory mockExtraUserDeveloperDatabaseFactory=mock(ExtraUserDeveloperDatabaseFactory.class);
    @Mock
    private ErrorManager mockErrorManager;
    @Mock
    private PlatformDatabaseSystem mockPlatformDatabaseSystem=mock(PlatformDatabaseSystem.class);
    @Mock
    private DeveloperObjectFactory mockDeveloperObjectFactory=mock(DeveloperObjectFactory.class);
    @Mock
    private DeveloperDatabase mockDeveloperDatabase=mock(DeveloperDatabase.class);
    @Mock
    private List<DeveloperDatabase> mockListDatabases;


    ListData testListData = new ListData();
    @Test
    public void testGetDatabaseList_successful() throws Exception {

     when(this.mockDeveloperObjectFactory.
      getNewDeveloperDatabase(Matchers.anyString(), Matchers.anyString())
      ).thenReturn(testListData.getDatabase());


        when(mockExtraUserDeveloperDatabaseFactory.
                        getDatabaseList(Matchers.<DeveloperObjectFactory>any())
        ).thenReturn(testListData.getDatabaseList());

        mockListDatabases=mockExtraUserDeveloperDatabaseFactory.getDatabaseList(this.mockDeveloperObjectFactory);

        Assertions.assertThat(mockListDatabases).isNotNull();
    }


}