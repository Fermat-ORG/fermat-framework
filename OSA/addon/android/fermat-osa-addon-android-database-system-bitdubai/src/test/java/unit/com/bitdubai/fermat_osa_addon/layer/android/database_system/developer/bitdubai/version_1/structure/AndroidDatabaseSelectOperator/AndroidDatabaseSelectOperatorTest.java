package unit.com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidDatabaseSelectOperator;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DataBaseSelectOperatorType;
import com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidDatabaseSelectOperator;

import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by angel on 23/7/15.
 */

public class AndroidDatabaseSelectOperatorTest{

    String  columna1 = "Columna_1",
            columna2 = "Columna_2";

    String  alias1 = "Alias_1",
            alias2 = "Alias_2";

    AndroidDatabaseSelectOperator selector1;
    AndroidDatabaseSelectOperator selector2;

    @Before
    public void setUpSelectOperator1(){
        selector1 = constructAndroidDatabaseSelectOperator(
                columna1,
                alias1,
                DataBaseSelectOperatorType.COUNT
        );
    }

    @Test
    public void SameValues_AreEquals_SameHash(){
        selector2 = constructAndroidDatabaseSelectOperator(
                columna1,
                alias1,
                DataBaseSelectOperatorType.COUNT
        );

        assertThat(selector1.getAliasColumn()).isEqualTo(selector2.getAliasColumn());
        assertThat(selector1.getColumn()).isEqualTo(selector2.getColumn());
        assertThat(selector1.getType()).isEqualTo(selector2.getType());
    }

    @Test
    public void Variables_NotEquals(){
        selector2 = constructAndroidDatabaseSelectOperator(
                columna2,
                alias2,
                DataBaseSelectOperatorType.SUM
        );

        assertThat(selector1.getAliasColumn()).isNotEqualTo(selector2.getAliasColumn());
        assertThat(selector1.getColumn()).isNotEqualTo(selector2.getColumn());
        assertThat(selector1.getType()).isNotEqualTo(selector2.getType());
    }


    private AndroidDatabaseSelectOperator constructAndroidDatabaseSelectOperator(
            String column,
            String alias,
            DataBaseSelectOperatorType type
    ){
        AndroidDatabaseSelectOperator selector = new AndroidDatabaseSelectOperator( column, type, alias );

        selector.setColumn(column);
        selector.setAliasColumn(alias);
        selector.setType(type);

        return selector;
    }

}
