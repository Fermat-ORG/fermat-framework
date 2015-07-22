package unit.com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.ExtraUserUserAddonRoot;

import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.ExtraUserUserAddonRoot;

import org.fest.assertions.api.Assertions;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Random;

import static org.mockito.Mockito.when;

/**
 * Created by Manuel Perez on 21/07/15.
 */
public class ResumeTest {

    final ExtraUserUserAddonRoot extraUserUserAddonRoot= Mockito.spy(new ExtraUserUserAddonRoot());
    ExtraUserUserAddonRoot testExtraUserUserAddonRoot=new ExtraUserUserAddonRoot();

    @Test
    public void resumeTest_callingMethod_getServiceStatusStarted()throws Exception{

        testExtraUserUserAddonRoot.resume();
        ServiceStatus serviceStatus=testExtraUserUserAddonRoot.getStatus();
        Assertions.assertThat(serviceStatus).isEqualTo(ServiceStatus.STARTED);

    }

    @Test
    public void resumeTest_callingMethod_getServiceNonStartedStatus() throws Exception{

        int randomServiceStatusIndex=getAnyInteger();
        when(extraUserUserAddonRoot.getStatus()).thenReturn(ServiceStatus.values()[randomServiceStatusIndex]);
        ServiceStatus serviceStatus=extraUserUserAddonRoot.getStatus();
        Assertions.assertThat(serviceStatus).isNotEqualTo(ServiceStatus.STARTED);

    }

    /**
     * Create an int that false ServiceStatus in resumeTest_callingMethod_getServiceNonStartedStatus()
     * */
    private int getAnyInteger(){

        int index= (int) (4*Math.random());
        if(index==1){

            return getAnyInteger();

        }
        return index;

    }

}
