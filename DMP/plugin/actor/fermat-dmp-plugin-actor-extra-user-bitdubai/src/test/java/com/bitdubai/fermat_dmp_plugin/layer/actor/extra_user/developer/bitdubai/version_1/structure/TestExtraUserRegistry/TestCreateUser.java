package com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.TestExtraUserRegistry;

import com.bitdubai.fermat_api.layer.pip_user.User;
import com.bitdubai.fermat_api.layer.pip_user.extra_user.exceptions.CantCreateExtraUserRegistry;
import com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.ExtraUserRegistry;
import org.fest.assertions.api.Assertions;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by francisco on 08/07/15.
 */
public class TestCreateUser {
    ExtraUserRegistry extraUserRegistry = new ExtraUserRegistry();
    @Ignore
    @Test
    public void testCreateUser() throws Exception {
        User user;
        user = extraUserRegistry.createUser("ExtraUser");
        Assertions.assertThat(user).isNotNull();
    }

    @Ignore
    @Test
    public void testCreateUser_Not_Exceptions() throws Exception{
        Exception exception=null;
        try {
            extraUserRegistry.createUser("ExtraUser");
        } catch (CantCreateExtraUserRegistry cantCreateExtraUserRegistry) {
            cantCreateExtraUserRegistry.printStackTrace();
        exception=cantCreateExtraUserRegistry;
        }

        Assertions.assertThat(exception).isNull();
    }
}