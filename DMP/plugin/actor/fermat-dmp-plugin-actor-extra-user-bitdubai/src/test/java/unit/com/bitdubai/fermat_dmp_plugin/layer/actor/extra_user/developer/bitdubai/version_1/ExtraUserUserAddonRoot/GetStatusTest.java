package unit.com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.ExtraUserUserAddonRoot;

import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.ExtraUserUserAddonRoot;

import org.fest.assertions.api.Assertions;
import org.junit.Test;


/**
 * Created by Manuel Perez on 23/07/15.
 */
public class GetStatusTest {

    ServiceStatus[] serviceStatusArray=ServiceStatus.values();
    ExtraUserUserAddonRoot testExtraUserUserAddonRoot=new ExtraUserUserAddonRoot();

    @Test
    public void getStatusTest_verifyExtraUserUserAddonRootServiceStatus_getServiceStatus(){

        ServiceStatus serviceStatus=testExtraUserUserAddonRoot.getStatus();
        Assertions.assertThat(serviceStatusArray).contains(serviceStatus);

    }

}
