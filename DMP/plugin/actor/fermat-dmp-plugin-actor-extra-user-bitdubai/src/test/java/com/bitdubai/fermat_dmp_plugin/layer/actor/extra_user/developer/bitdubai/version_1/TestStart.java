package com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PlatformDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PlatformFileSystem;
import org.fest.assertions.api.Assertions;
import org.junit.Test;
import org.mockito.Mock;

/**
 * Created by francisco on 09/07/15.
 */
public class TestStart {

    @Mock
    private PlatformDatabaseSystem platformDatabaseSystem;
    @Mock
    private PlatformFileSystem platformFileSystem;
    @Mock
   private ExtraUserUserAddonRoot extraUserUserAddonRoot = new ExtraUserUserAddonRoot();

    @Mock
   private ServiceStatus MockServiceStatusStarted = ServiceStatus.CREATED;
    @Mock
    private ServiceStatus MockServiceStatusControl= ServiceStatus.CREATED;




    @Test
    public void testStart() throws Exception {
        MockServiceStatusStarted=ServiceStatus.STARTED;

        MockServiceStatusControl=extraUserUserAddonRoot.getStatus();
        System.out.println(MockServiceStatusControl + "" +MockServiceStatusStarted);
       Assertions.assertThat(MockServiceStatusControl).isEqualTo(ServiceStatus.CREATED);

           }
}