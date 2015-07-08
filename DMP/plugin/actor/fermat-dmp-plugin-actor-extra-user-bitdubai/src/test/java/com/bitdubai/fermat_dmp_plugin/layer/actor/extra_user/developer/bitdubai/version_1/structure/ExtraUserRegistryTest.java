package com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PlatformDatabaseSystem;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_user.User;
import org.fest.assertions.api.Assertions;
import org.junit.Test;

import java.util.UUID;


/**
 * Created by francisco on 06/07/15.
 */
public class ExtraUserRegistryTest {
    /**
     * DealsWithErrors Interface member variables.
     */
    ErrorManager errorManager;

    /**
     * DealsWithPlatformDatabaseSystem Interface member variables.
     */
    PlatformDatabaseSystem platformDatabaseSystem;


    /**
     * UserRegistry Interface member variables.
     */
    UUID userId;
    User user = new ExtraUser();

    private Database database;
    ExtraUserRegistry extraUserRegistry = new ExtraUserRegistry();
    @Test
    public void testInitialize() throws Exception {
        extraUserRegistry.initialize();

    }

    @Test
    public void testCreateUser() throws Exception {
        user = extraUserRegistry.createUser("ExtraUser");
        Assertions.assertThat(user).isNotNull();
    }

    @Test
    public void testGetUser() throws Exception {
        UUID user_id ;

        user = extraUserRegistry.getUser(userId);

        Assertions.assertThat(user).isNotNull();

    }
}