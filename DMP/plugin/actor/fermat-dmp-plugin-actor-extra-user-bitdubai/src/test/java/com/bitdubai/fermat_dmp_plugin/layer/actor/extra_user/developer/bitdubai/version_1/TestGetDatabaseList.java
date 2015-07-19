package com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.TestExtraUserDeveloperDatabaseFactory.ListData;
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
public class TestGetDatabaseList {

    @Mock
    private ExtraUserUserAddonRoot mockExtraUserAddonRoot = mock(ExtraUserUserAddonRoot.class);
    @Mock
    private List<DeveloperDatabase> mockListDatabases;
    @Mock
    private DeveloperObjectFactory mockDeveloperObjectFactory = mock(DeveloperObjectFactory.class);
    @Mock
    ListData testListData = new ListData();

    @Test
    public void TestGetDatabaselist_successful() throws Exception{

        when(this.mockDeveloperObjectFactory.
                        getNewDeveloperDatabase(Matchers.anyString(), Matchers.anyString())
        ).thenReturn(testListData.getDatabase());


        when(mockExtraUserAddonRoot.
                        getDatabaseList(Matchers.<DeveloperObjectFactory>any())
        ).thenReturn(testListData.getDatabaseList());

        mockListDatabases=mockExtraUserAddonRoot.getDatabaseList(mockDeveloperObjectFactory);

        Assertions.assertThat(mockListDatabases).isNotNull();


    }
}