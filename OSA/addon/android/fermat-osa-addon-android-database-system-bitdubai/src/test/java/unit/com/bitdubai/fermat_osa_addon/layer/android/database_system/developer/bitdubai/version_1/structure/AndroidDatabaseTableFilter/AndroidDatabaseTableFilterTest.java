package unit.com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidDatabaseTableFilter;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterType;
import com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidDatabaseTableFilter;

import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by angel on 22/7/15.
 */

public class AndroidDatabaseTableFilterTest {

    private String value1 = "index_1";
    private String value2 = "index_2";

    private String columna1 = "columna_1";
    private String columna2 = "columna_2";

    AndroidDatabaseTableFilter filter1;
    AndroidDatabaseTableFilter filter2;

    @Before
    public void setUpTableFilter1() {
        filter1 = constructDatabaseTableColumn(
                columna1,
                DatabaseFilterType.EQUAL,
                value1
        );
    }

    @Test
    public void Variables_AreEquals() {
        filter2 = constructDatabaseTableColumn(
                columna1,
                DatabaseFilterType.EQUAL,
                value1
        );

        assertThat(filter1.getColumn()).isEqualTo(filter2.getColumn());
        assertThat(filter1.getType()).isEqualTo(filter2.getType());
        assertThat(filter1.getValue()).isEqualTo(filter2.getValue());
    }

    @Test
    public void Variables_NotEquals() {
        filter2 = constructDatabaseTableColumn(
                columna2,
                DatabaseFilterType.LIKE,
                value2
        );

        assertThat(filter1.getColumn()).isNotEqualTo(filter2.getColumn());
        assertThat(filter1.getType()).isNotEqualTo(filter2.getType());
        assertThat(filter1.getValue()).isNotEqualTo(filter2.getValue());
    }

    private AndroidDatabaseTableFilter constructDatabaseTableColumn(
            String column,
            DatabaseFilterType type,
            String value
    ) {
        AndroidDatabaseTableFilter tableFilter = new AndroidDatabaseTableFilter();
        tableFilter.setColumn(column);
        tableFilter.setType(type);
        tableFilter.setValue(value);

        return tableFilter;
    }


}
