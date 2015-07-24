package unit.com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.ExtraUserUserAddonRoot;

import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.ExtraUserUserAddonRoot;
import java.util.Arrays;
import org.fest.assertions.api.Assertions;
import org.junit.Test;
import org.mockito.Mockito;



import static org.mockito.Mockito.when;

/**
 * Created by Manuel Perez on 23/07/15.
 */
public class StopTest {

    final ExtraUserUserAddonRoot extraUserUserAddonRoot= Mockito.spy(new ExtraUserUserAddonRoot());
    ExtraUserUserAddonRoot testExtraUserUserAddonRoot=new ExtraUserUserAddonRoot();

    /**
     * Create an int that false ServiceStatus in resumeTest_callingMethod_getServiceNonStartedStatus()
     * */
    private int getAnyInteger(int elementsInList, int indexRejected){

        int index= (int) (elementsInList*Math.random());
        if(index==indexRejected){

            return getAnyInteger(elementsInList,indexRejected);

        }
        return index;

    }

    @Test
    public void stopTest_callingMethod_getServiceNonStoppedStatus() throws Exception{

        int randomServiceStatusIndex;
        ServiceStatus[] serviceStatusArray=ServiceStatus.values();
        int pausedIndex= Arrays.binarySearch(serviceStatusArray, ServiceStatus.STOPPED);
        randomServiceStatusIndex=getAnyInteger(serviceStatusArray.length, pausedIndex);
        extraUserUserAddonRoot.stop();
        when(extraUserUserAddonRoot.getStatus()).thenReturn(serviceStatusArray[randomServiceStatusIndex]);
        ServiceStatus serviceStatus=extraUserUserAddonRoot.getStatus();
        Assertions.assertThat(serviceStatus).isNotEqualTo(ServiceStatus.STOPPED);

    }

    @Test
    public void stopTest_callingMethod_getServiceStatusPaused()throws Exception{

        testExtraUserUserAddonRoot.stop();
        ServiceStatus serviceStatus=testExtraUserUserAddonRoot.getStatus();
        Assertions.assertThat(serviceStatus).isEqualTo(ServiceStatus.STOPPED);

    }


}
