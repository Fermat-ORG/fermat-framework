package unit.com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidRecord;

import com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidRecord;

import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by angel on 22/7/15.
 */
public class AndroidRecordTest {

    private String name1 = "name1";
    private String name2 = "name2";

    private String value1 = "value1";
    private String value2 = "value2";

    AndroidRecord record1;
    AndroidRecord record2;

    @Before
    public void setUpAndroidRecord1() {
        record1 = constructAndroidRecord(
                name1,
                value1,
                true,
                true
        );
    }

    @Test
    public void Variables_AreEquals() {
        record2 = constructAndroidRecord(
                name1,
                value1,
                true,
                true
        );

        assertThat(record1.isChange()).isEqualTo(record2.isChange());
        assertThat(record1.getValue()).isEqualTo(record2.getValue());
        assertThat(record1.isUseOfVariable()).isEqualTo(record2.isUseOfVariable());
        assertThat(record1.getName()).isEqualTo(record2.getName());

        assertThat(record1.toString()).isEqualTo(record2.toString());
    }

    @Test
    public void Variables_NotEquals() {
        record2 = constructAndroidRecord(
                name2,
                value2,
                false,
                false
        );

        assertThat(record1.isChange()).isNotEqualTo(record2.isChange());
        assertThat(record1.getValue()).isNotEqualTo(record2.getValue());
        assertThat(record1.isUseOfVariable()).isNotEqualTo(record2.isUseOfVariable());
        assertThat(record1.getName()).isNotEqualTo(record2.getName());

        assertThat(record1.toString()).isNotEqualTo(record2.toString());
    }

    private AndroidRecord constructAndroidRecord(
            String name,
            String value,
            boolean change,
            boolean ValueofVariable
    ) {
        AndroidRecord record = new AndroidRecord();
        record.setName(name);
        record.setChange(change);
        record.setUseValueofVariable(ValueofVariable);
        record.setValue(value);

        return record;
    }
}
