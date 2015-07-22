package unit.com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.ExtraUserUserAddonRoot;

import com.bitdubai.fermat_dmp_plugin.layer.actor.extra_user.developer.bitdubai.version_1.ExtraUserUserAddonRoot;

import org.fest.assertions.api.Assertions;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.when;

/**
 * Created by Manuel Perez on 21/07/15.
 */
public class GetClasesFullPathTest {

    ExtraUserUserAddonRoot extraUserUserAddonRoot = new ExtraUserUserAddonRoot();

    final ExtraUserUserAddonRoot testExtraUserUserAddonRoot= Mockito.spy(new ExtraUserUserAddonRoot());
    @Test
    public void getClassesFullPath_callingMethod_getFullPath() throws Exception {
        List<String> returnedClasses;
        returnedClasses = extraUserUserAddonRoot.getClassesFullPath();

        Assertions.assertThat(returnedClasses.isEmpty()).isEqualTo(false);
    }

    @Test
    public void getClassesFullPath_callingMethod_getEmptyList() throws Exception {
        List<String> returnedClasses;
        List<String> emptyTestList=new ArrayList<>();
        when(testExtraUserUserAddonRoot.getClassesFullPath()).thenReturn(emptyTestList);
        returnedClasses =testExtraUserUserAddonRoot.getClassesFullPath();
        Assertions.assertThat(returnedClasses).isEmpty();
    }

    @Test
    public void getClassesFullPath_callingMethod_getNull() throws Exception {
        List<String> returnedClasses;
        when(testExtraUserUserAddonRoot.getClassesFullPath()).thenReturn(null);
        returnedClasses =testExtraUserUserAddonRoot.getClassesFullPath();
        Assertions.assertThat(returnedClasses).isNull();
    }



}
