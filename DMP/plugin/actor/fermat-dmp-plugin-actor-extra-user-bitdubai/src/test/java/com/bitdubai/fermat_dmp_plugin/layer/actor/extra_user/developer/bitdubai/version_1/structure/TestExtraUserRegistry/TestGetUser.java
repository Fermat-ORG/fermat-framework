package com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.TestExtraUserRegistry;

import com.bitdubai.fermat_api.layer.pip_user.User;
import com.bitdubai.fermat_api.layer.pip_user.extra_user.exceptions.CantGetExtraUserRegistry;
import com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.ExtraUserRegistry;
import org.fest.assertions.api.Assertions;
import org.junit.Test;

import java.util.UUID;

/**
 * Created by francisco on 08/07/15.
 */
public class TestGetUser {
    ExtraUserRegistry extraUserRegistry = new ExtraUserRegistry();
    UUID User_Id;
    @Test
    public void testGetUser() throws Exception {
        User user;
        user=extraUserRegistry.getUser(User_Id);
        Assertions.assertThat(user).isNotNull();
    }
    @Test
    public  void testGetUser_Not_Exception() throws Exception{
        Exception exception = null;
        try {
            extraUserRegistry.getUser(User_Id);
        } catch (CantGetExtraUserRegistry cantGetExtraUserRegistry) {
            cantGetExtraUserRegistry.printStackTrace();
        exception=cantGetExtraUserRegistry;
        }
        Assertions.assertThat(exception).isNull();
    }
}