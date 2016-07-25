package unit.com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidDatabaseTableOrder;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFilterOrder;
import com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidDatabaseTableOrder;

import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by angel on 22/7/15.
 */
public class AndroidDatabaseTableOrderTest {

    private String columnName_1 = "Column1";
    private String columnName_2 = "Column2";

    AndroidDatabaseTableOrder order1;
    AndroidDatabaseTableOrder order2;

    @Before
    public void setUpTableFilter1() {
        order1 = constructDatabaseTableColumn(
                columnName_1,
                DatabaseFilterOrder.ASCENDING
        );
    }

    @Test
    public void Variables_AreEquals() {
        order2 = constructDatabaseTableColumn(
                columnName_1,
                DatabaseFilterOrder.ASCENDING
        );

        assertThat(order1.getColumnName()).isEqualTo(order2.getColumnName());
        assertThat(order1.getDirection()).isEqualTo(order2.getDirection());
    }

    @Test
    public void Variables_NotEquals() {
        order2 = constructDatabaseTableColumn(
                columnName_2,
                DatabaseFilterOrder.DESCENDING
        );

        assertThat(order1.getColumnName()).isNotEqualTo(order2.getColumnName());
        assertThat(order1.getDirection()).isNotEqualTo(order2.getDirection());
    }

    private AndroidDatabaseTableOrder constructDatabaseTableColumn(
            String columnName,
            DatabaseFilterOrder direction
    ) {
        AndroidDatabaseTableOrder tableOrder = new AndroidDatabaseTableOrder(columnName, direction);
        return tableOrder;
    }
}