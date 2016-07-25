package unit.com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidDatabaseTableFactory;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseDataType;
import com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidDatabaseTableFactory;

import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by angel on 22/7/15.
 */
public class AndroidDatabaseTableFactotyTest {

    private String tabla1 = "tabla_1";
    private String tabla2 = "tabla_2";

    private String index1 = "index_1";
    private String index2 = "index_2";

    private String columna1 = "columna_1";
    private String columna2 = "columna_2";

    AndroidDatabaseTableFactory tableFactory1;
    AndroidDatabaseTableFactory tableFactory2;

    @Before
    public void setUpTableFactory1() {
        tableFactory1 = constructDatabaseTableFactory(
                tabla1,
                index1,
                columna1, DatabaseDataType.STRING, 10, false,
                false
        );
    }

    @Test
    public void Indexs_AreEquals() {
        tableFactory2 = constructDatabaseTableFactory(
                tabla1,
                index1,
                columna1, DatabaseDataType.STRING, 10, false,
                false
        );

        assertThat(tableFactory1.listIndexes()).isEqualTo(tableFactory2.listIndexes());
    }

    @Test
    public void Indexs_NotEquals() {
        tableFactory2 = constructDatabaseTableFactory(
                tabla2,
                index2,
                columna2, DatabaseDataType.INTEGER, 10, false,
                false
        );

        assertThat(tableFactory1.listIndexes()).isNotEqualTo(tableFactory2.listIndexes());
    }

    @Test
    public void toString_AreEquals() {
        tableFactory2 = constructDatabaseTableFactory(
                tabla1,
                index1,
                columna1, DatabaseDataType.STRING, 10, false,
                false
        );

        assertThat(tableFactory1.toString()).isEqualTo(tableFactory2.toString());
    }

    @Test
    public void toString_NotEquals() {
        tableFactory2 = constructDatabaseTableFactory(
                tabla2,
                index2,
                columna2, DatabaseDataType.INTEGER, 10, false,
                true
        );

        assertThat(tableFactory1.toString()).isNotEqualTo(tableFactory2.toString());
    }

    private AndroidDatabaseTableFactory constructDatabaseTableFactory(
            String tablaName,
            String index,
            String columnName, DatabaseDataType dataType, int dataTypeSize, boolean primaryKey,
            boolean columns
    ) {
        AndroidDatabaseTableFactory tableFactory = new AndroidDatabaseTableFactory(tablaName);
        tableFactory.addIndex(index);
        tableFactory.addColumn(columnName, dataType, dataTypeSize, primaryKey);

        if (columns) {
            tableFactory.addColumn("extra" + columnName, dataType, dataTypeSize, primaryKey);
        }

        return tableFactory;
    }
}
