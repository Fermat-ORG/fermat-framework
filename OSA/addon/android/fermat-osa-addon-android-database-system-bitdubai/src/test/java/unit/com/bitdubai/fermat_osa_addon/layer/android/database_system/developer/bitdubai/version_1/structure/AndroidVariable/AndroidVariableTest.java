package unit.com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidVariable;

import com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidVariable;

import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by angel on 23/7/15.
 */

public class AndroidVariableTest {

    AndroidVariable variable1;
    AndroidVariable variable2;

    String name1 = "Name_1";
    String name2 = "Name_2";

    String value1 = "Value_1";
    String value2 = "Value_2";

    @Before
    public void setUpVariable1() {
        variable1 = constructAndroidVariable(name1, value1);
    }

    @Test
    public void Variables_AreEquals() {
        variable2 = constructAndroidVariable(name1, value1);

        assertThat(variable1.getName()).isEqualTo(variable2.getName());
        assertThat(variable1.getValue()).isEqualTo(variable2.getValue());
        assertThat(variable1.toString()).isEqualTo(variable2.toString());
    }

    @Test
    public void Variables_NotEquals() {
        variable2 = constructAndroidVariable(name2, value2);

        assertThat(variable1.getName()).isNotEqualTo(variable2.getName());
        assertThat(variable1.getValue()).isNotEqualTo(variable2.getValue());
        assertThat(variable1.toString()).isNotEqualTo(variable2.toString());
    }

    private AndroidVariable constructAndroidVariable(
            String name,
            String value
    ) {
        AndroidVariable variable = new AndroidVariable();

        variable.setName(name);
        variable.setValue(value);

        return variable;
    }

}
