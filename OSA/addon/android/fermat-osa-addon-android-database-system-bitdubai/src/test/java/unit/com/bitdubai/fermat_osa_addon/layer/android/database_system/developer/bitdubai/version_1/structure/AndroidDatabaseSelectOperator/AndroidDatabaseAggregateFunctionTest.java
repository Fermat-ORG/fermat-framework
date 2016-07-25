package unit.com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidDatabaseSelectOperator;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DataBaseAggregateFunctionType;
import com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidDatabaseAggregateFunction;

import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by angel on 23/7/15.
 */

public class AndroidDatabaseAggregateFunctionTest {

    String columna1 = "Columna_1",
            columna2 = "Columna_2";

    String alias1 = "Alias_1",
            alias2 = "Alias_2";

    AndroidDatabaseAggregateFunction selector1;
    AndroidDatabaseAggregateFunction selector2;

    @Before
    public void setUpSelectOperator1() {
        selector1 = constructAndroidDatabaseSelectOperator(
                columna1,
                alias1,
                DataBaseAggregateFunctionType.COUNT
        );
    }

    @Test
    public void SameValues_AreEquals_SameHash() {
        selector2 = constructAndroidDatabaseSelectOperator(
                columna1,
                alias1,
                DataBaseAggregateFunctionType.COUNT
        );

        assertThat(selector1.getAliasColumn()).isEqualTo(selector2.getAliasColumn());
        assertThat(selector1.getColumn()).isEqualTo(selector2.getColumn());
        assertThat(selector1.getType()).isEqualTo(selector2.getType());
    }

    @Test
    public void Variables_NotEquals() {
        selector2 = constructAndroidDatabaseSelectOperator(
                columna2,
                alias2,
                DataBaseAggregateFunctionType.SUM
        );

        assertThat(selector1.getAliasColumn()).isNotEqualTo(selector2.getAliasColumn());
        assertThat(selector1.getColumn()).isNotEqualTo(selector2.getColumn());
        assertThat(selector1.getType()).isNotEqualTo(selector2.getType());
    }


    private AndroidDatabaseAggregateFunction constructAndroidDatabaseSelectOperator(
            String column,
            String alias,
            DataBaseAggregateFunctionType type
    ) {
        AndroidDatabaseAggregateFunction selector = new AndroidDatabaseAggregateFunction(column, type, alias);

        selector.setColumn(column);
        selector.setAliasColumn(alias);
        selector.setType(type);

        return selector;
    }

}
