package unit.com.bitdubai.fermat_dmp_plugin.layer.basic_wallet.bitcoin_wallet.developer.bitdubai.version_1.structure.mocks;

import com.bitdubai.fermat_api.layer.all_definition.crypto.util.CryptoHasher;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.BalanceType;
import com.bitdubai.fermat_ccp_api.layer.basic_wallet.common.enums.TransactionType;
import com.bitdubai.fermat_api.layer.all_definition.enums.WalletFactoryProjectState;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_ccp_plugin.layer.basic_wallet.bitcoin_wallet.developer.bitdubai.version_1.structure.BitcoinWalletDatabaseConstants;

import java.util.List;
import java.util.UUID;

/**
 * Created by jorgegonzalez on 2015.07.14..
 */
public class MockDatabaseTableRecord implements DatabaseTableRecord {

    private static String ANY_BITCOIN_PUBLIC_ADDRESS_FROM = "11NvDRnGsX57oNsmEWRqzv8QQuK1GU9g6Mb";
    private static String ANY_BITCOIN_PUBLIC_ADDRESS_TO = "156jBLBvtaDccomhbiWDoySKuzx1AbWUG8";

    @Override
    public String getStringValue(String columnName) {
        switch(columnName){
            case BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ID_COLUMN_NAME:
                return CryptoHasher.performSha256("MOCK RECORD");
            case BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_TYPE_COLUMN_NAME:
                return TransactionType.CREDIT.getCode();
            case BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ADDRESS_FROM_COLUMN_NAME:
                return ANY_BITCOIN_PUBLIC_ADDRESS_FROM;
            case BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ADDRESS_TO_COLUMN_NAME:
                return ANY_BITCOIN_PUBLIC_ADDRESS_TO;
            case BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ACTOR_FROM_TYPE_COLUMN_NAME:
                return Actors.EXTRA_USER.getCode();
            case BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_ACTOR_TO_TYPE_COLUMN_NAME:
                return Actors.INTRA_USER.getCode();
            case BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_BALANCE_TYPE_COLUMN_NAME:
                return BalanceType.BOOK.getCode();
            case BitcoinWalletDatabaseConstants.BITCOIN_WALLET_TABLE_MEMO_COLUMN_NAME:
                return "MOCK MEMO";
            default:
                return null;
        }
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
        return 1.0F;
    }

    @Override
    public double getDoubleValue(String columnName) {
        return 1.0;
    }

    @Override
    public String getVariableName(String columnName) {
        return "";
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
    public void setFermatEnum(String columnName, WalletFactoryProjectState state) {

    }

    @Override
    public List<DatabaseRecord> getValues() {
        return null;
    }

    @Override
    public void setValues(List<DatabaseRecord> values) {

    }
}
