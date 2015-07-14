package com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.pip_user.User;
import org.fest.assertions.api.Assertions;
import org.junit.Ignore;
import org.junit.Test;

import java.util.UUID;

/**
 * Created by francisco on 08/07/15.
 */
public class TestGetUser {
    ExtraUserUserAddonRoot extraUserUserAddonRoot = new ExtraUserUserAddonRoot();
    DeveloperObjectFactory developerObjectFactory;
    DeveloperDatabase developerDatabase;
    DeveloperDatabaseTable developerDatabaseTable;
    UUID User_Id;
    @Ignore
    @Test
    public void testGetUser() throws Exception {
        User user;
        user=extraUserUserAddonRoot.getUser(User_Id);
        Assertions.assertThat(user).isNotNull();
    }
    @Ignore
    @Test
    public void testGetUser_Not_Exceptions(){
        Exception exception=null;
        try {
            extraUserUserAddonRoot.getUser(User_Id);
        } catch (Exception e) {
            e.printStackTrace();
            exception=e;
        }
        Assertions.assertThat(exception).isNull();
    }
}