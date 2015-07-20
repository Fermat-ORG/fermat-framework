package com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.TestExtraUserRegistry;

import com.bitdubai.fermat_api.layer.dmp_actor.extra_user.exceptions.CantInitializeExtraUserRegistryException;
import com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.structure.ExtraUserRegistry;
import org.fest.assertions.api.Assertions;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by francisco on 08/07/15.
 */
public class TestInitialize {
    ExtraUserRegistry extraUserRegistry = new ExtraUserRegistry();
    CantInitializeExtraUserRegistryException cantInitializeExtraUserRegistryException;
    @Ignore
    @Test
    public void testInitialize() throws Exception {
        cantInitializeExtraUserRegistryException=null;
        try {
            extraUserRegistry.initialize();
        } catch (CantInitializeExtraUserRegistryException e) {
            e.printStackTrace();
            cantInitializeExtraUserRegistryException=e;
        }
        Assertions.assertThat(cantInitializeExtraUserRegistryException).isNull();
    }
}