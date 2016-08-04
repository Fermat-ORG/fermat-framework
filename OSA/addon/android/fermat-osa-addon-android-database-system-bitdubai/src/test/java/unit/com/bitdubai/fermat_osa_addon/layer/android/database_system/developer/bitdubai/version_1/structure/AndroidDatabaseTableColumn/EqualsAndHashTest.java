package unit.com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidDatabaseTableColumn;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidDatabaseTableColumn;

import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.*;

/**
 * Created by jorgegonzalez on 2015.06.29..
 */
public class EqualsAndHashTest {

    private AndroidDatabaseTableColumn tableColumn1;
    private AndroidDatabaseTableColumn tableColumn2;

    @Before
    public void setUpTableColumn1() {
        tableColumn1 = constructDatabaseTableColumn(DatabaseDataType.STRING, 10, "TEST", false);
    }

    @Test
    public void SameValues_AreEquals_SameHash() {
        tableColumn2 = constructDatabaseTableColumn(DatabaseDataType.STRING, 10, "TEST", false);
        assertThat(tableColumn1).isEqualTo(tableColumn2);
        assertThat(tableColumn1.hashCode()).isEqualTo(tableColumn2.hashCode());
    }

    @Test
    public void DifferentType_NotEquals_DifferentHash() {
        tableColumn2 = constructDatabaseTableColumn(DatabaseDataType.INTEGER, 10, "TEST", false);
        assertThat(tableColumn1).isNotEqualTo(tableColumn2);
        assertThat(tableColumn1.hashCode()).isNotEqualTo(tableColumn2.hashCode());
    }

    @Test
    public void DifferentName_NotEquals_DifferentHash() {
        tableColumn2 = constructDatabaseTableColumn(DatabaseDataType.STRING, 10, "TEST_2", false);
        assertThat(tableColumn1).isNotEqualTo(tableColumn2);
        assertThat(tableColumn1.hashCode()).isNotEqualTo(tableColumn2.hashCode());
    }

    private AndroidDatabaseTableColumn constructDatabaseTableColumn(DatabaseDataType dataType, int typeSize, String name, boolean primaryKey) {
        AndroidDatabaseTableColumn tableColumn = new AndroidDatabaseTableColumn();
        tableColumn.setType(dataType);
        tableColumn.setDataTypeSize(typeSize);
        tableColumn.setName(name);
        tableColumn.setPrimaryKey(primaryKey);

        return tableColumn;
    }
}
