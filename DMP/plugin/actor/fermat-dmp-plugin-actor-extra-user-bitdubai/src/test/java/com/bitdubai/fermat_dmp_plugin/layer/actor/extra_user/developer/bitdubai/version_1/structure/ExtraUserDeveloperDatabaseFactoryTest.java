package com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure;
//package com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PlatformDatabaseSystem;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import junit.framework.TestCase;
import org.fest.assertions.api.Assertions;
import org.junit.Test;

/**
 * Created by francisco on 06/07/15.
 */
public class ExtraUserDeveloperDatabaseFactoryTest extends TestCase {

     ErrorManager errorManager;
     PlatformDatabaseSystem platformDatabaseSystem;
     ExtraUserDeveloperDatabaseFactory extraUserDeveloperDatabaseFactory;
    DeveloperObjectFactory developerObjectFactory;
    DeveloperDatabaseTable developerDatabaseTable;

    public ExtraUserDeveloperDatabaseFactoryTest(ErrorManager errorManager, PlatformDatabaseSystem platformDatabaseSystem){
        this.errorManager=errorManager;
        this.platformDatabaseSystem=platformDatabaseSystem;

    }

        @Test
    public void testInitializeDatabase() throws Exception {
        extraUserDeveloperDatabaseFactory.initializeDatabase();

                    }

    @Test
    public void testGetDatabaseList() throws Exception {

        Assertions.assertThat(
                extraUserDeveloperDatabaseFactory.
                        getDatabaseList(developerObjectFactory)).isNotNull();
    }

    @Test
    public void testGetDatabaseTableList() throws Exception {
        Assertions.assertThat(
                extraUserDeveloperDatabaseFactory.
                        getDatabaseTableList(developerObjectFactory)).isNotNull();
    }

    @Test
    public void testGetDatabaseTableContent() throws Exception {
                Assertions.assertThat(extraUserDeveloperDatabaseFactory.
                        getDatabaseTableContent(developerObjectFactory,developerDatabaseTable)).isNotNull();
    }
    @Test
    public void testSetErrorManager() throws Exception {
        extraUserDeveloperDatabaseFactory = new ExtraUserDeveloperDatabaseFactory(errorManager,platformDatabaseSystem);
        extraUserDeveloperDatabaseFactory.setErrorManager(errorManager);
    }

    @Test
    public void testSetPlatformDatabaseSystem() throws Exception {
        extraUserDeveloperDatabaseFactory.setPlatformDatabaseSystem(platformDatabaseSystem);
    }

}