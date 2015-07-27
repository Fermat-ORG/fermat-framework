package test.com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.mocks;

import com.bitdubai.fermat_api.layer.all_definition.crypto.util.CryptoHasher;
import com.bitdubai.fermat_api.layer.all_definition.enums.Actors;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareDatabaseConstants;

import java.util.List;
import java.util.UUID;

/**
 * Created by Nerio on 25/07/15.
 */
public class MockDatabaseTable  implements DatabaseTableRecord {

    private static String ANY_BITCOIN_PUBLIC_ADDRESS_FROM = "11NvDRnGsX57oNsmEWRqzv8QQuK1GU9g6Mb";
    private static String ANY_BITCOIN_PUBLIC_ADDRESS_TO = "156jBLBvtaDccomhbiWDoySKuzx1AbWUG8";

    @Override
    public String getStringValue(String columnName) {
        switch(columnName){
            case WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_NAME:
                return CryptoHasher.performSha256("MOCK RECORD");
            case WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_CONTACT_ID_COLUMN_NAME:
                return UUID.randomUUID().toString();
            case WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_WALLET_ID_COLUMN_NAME:
                return ANY_BITCOIN_PUBLIC_ADDRESS_FROM;
            case WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_ACTOR_ID_COLUMN_NAME:
                return ANY_BITCOIN_PUBLIC_ADDRESS_TO;
            case WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_ACTOR_NAME_COLUMN_NAME:
                return Actors.EXTRA_USER.getCode();
            case WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_ACTOR_TYPE_COLUMN_NAME:
                return Actors.INTRA_USER.getCode();
            case WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_RECEIVED_CRYPTO_ADDRESS_COLUMN_NAME:
                return ANY_BITCOIN_PUBLIC_ADDRESS_FROM;
            case WalletContactsMiddlewareDatabaseConstants.CRYPTO_WALLET_CONTACTS_ADDRESS_BOOK_TABLE_RECEIVED_ADDRESS_CRYPTO_CURRENCY_COLUMN_NAME:
                return ANY_BITCOIN_PUBLIC_ADDRESS_TO;
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
    public List<DatabaseRecord> getValues() {
        return null;
    }

    @Override
    public void setValues(List<DatabaseRecord> values) {

    }
}
