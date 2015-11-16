package unit.com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.structure.IncomingExtraUserRegistry;

import com.bitdubai.fermat_api.layer.all_definition.enums.WalletFactoryProjectState;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by jorgegonzalez on 2015.07.02..
 */
public class MockDatabaseTableRecord implements DatabaseTableRecord {


    @Override
    public String getStringValue(String columnName) {
        return "THIS_IS_A_MOCK_COLUMN";
    }

    @Override
    public UUID getUUIDValue(String columnName) {
        return UUID.randomUUID();
    }

    @Override
    public long getLongValue(String columnName) {
        return 1L;
    }

    @Override
    public Integer getIntegerValue(String columnName) {
        return Integer.valueOf(1);
    }

    @Override
    public float getFloatValue(String columnName) {
        return 1.0f;
    }

    @Override
    public double getDoubleValue(String columnName) {
        return 1.0;
    }

    @Override
    public String getVariableName(String columnName) {
        return "THIS_IS_A_MOCK_VARIABLE";
    }

    @Override
    public void setStringValue(String columnName, String value) {

    }

    @Override
    public void setUUIDValue(String columnName, UUID value) {

    }

    @Override
    public void setLongValue(String columnName, long value) {

    }

    @Override
    public void setIntegerValue(String columnName, Integer value) {

    }

    @Override
    public void setFloatValue(String columnName, float value) {

    }

    @Override
    public void setDoubleValue(String columnName, double value) {

    }

    @Override
    public void setVariableValue(String columnName, String variableName) {

    }

    @Override
    public void setSelectField(String columnName) {

    }

    @Override
    public void setStateValue(String columnName, WalletFactoryProjectState state) {

    }

    @Override
    public List<DatabaseRecord> getValues() {
        return new ArrayList<>(0);
    }

    @Override
    public void setValues(List<DatabaseRecord> values) {

    }
}
