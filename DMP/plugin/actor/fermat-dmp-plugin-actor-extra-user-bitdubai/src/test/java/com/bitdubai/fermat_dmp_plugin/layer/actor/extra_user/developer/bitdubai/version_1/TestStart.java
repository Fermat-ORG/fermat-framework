package com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import org.fest.assertions.api.Assertions;
import org.junit.Test;

/**
 * Created by francisco on 09/07/15.
 */
public class TestStart {
    ExtraUserUserAddonRoot extraUserUserAddonRoot = new ExtraUserUserAddonRoot();
    @Test
    public void testStart() throws Exception {
        extraUserUserAddonRoot.start();
        Assertions.assertThat(extraUserUserAddonRoot.getStatus()).isEqualTo(ServiceStatus.STARTED);
    }
}