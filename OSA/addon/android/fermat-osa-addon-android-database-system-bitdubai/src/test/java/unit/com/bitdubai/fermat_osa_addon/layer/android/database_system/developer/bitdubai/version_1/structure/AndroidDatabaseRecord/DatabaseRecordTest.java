package unit.com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidDatabaseRecord;

import com.bitdubai.fermat_osa_addon.layer.android.database_system.developer.bitdubai.version_1.structure.AndroidDatabaseRecord;

import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static org.fest.assertions.api.Assertions.*;

public class DatabaseRecordTest {

    AndroidDatabaseRecord record1;
    AndroidDatabaseRecord record2;
    AndroidDatabaseRecord record3;
    AndroidDatabaseRecord recordGeneral_1;
    AndroidDatabaseRecord recordGeneral_2;

    private UUID uuid1;
    private UUID uuid2;

    @Before
    public void setUpInicialize() {
        record1 = new AndroidDatabaseRecord();
        record2 = new AndroidDatabaseRecord();
        record3 = new AndroidDatabaseRecord();
        recordGeneral_1 = new AndroidDatabaseRecord();
        recordGeneral_2 = new AndroidDatabaseRecord();

        uuid1 = UUID.randomUUID();
        uuid2 = UUID.randomUUID();

    }

	/*	Comprobamos los setVariableValue y setSelectField   */

    @Test
    public void setVariableValue_setSelectField() {
        recordGeneral_1.setSelectField("Columna_General_1");
        recordGeneral_2.setVariableValue("Columna_General_2", "Valor_General");

        assertThat(recordGeneral_1.getStringValue("Columna_General_1")).isEqualTo(null);
        assertThat(recordGeneral_2.getStringValue("Columna_General_2")).isNotEqualTo("Valor_General_2");
    }

	/*	Comprobamos los metodos de tipo String */

    @Test
    public void String_AreEquals() {
        record1.setStringValue("String_Column_1", "Valor_1");
        record2.setStringValue("String_Column_1", "Valor_1");

        assertThat(record1.getStringValue("String_Column_1")).isEqualTo(record2.getStringValue("String_Column_1"));
        assertThat(record1.toString()).isEqualTo(record2.toString());
    }

    @Test
    public void String_NotEquals() {
        record1.setStringValue("String_Column_2", "Valor_1");
        record2.setStringValue("String_Column_2", "Valor_2");

        assertThat(record1.getStringValue("String_Column_2")).isNotEqualTo(record2.getStringValue("String_Column_2"));
        assertThat(record1.toString()).isNotEqualTo(record2.toString());
    }

	/*	Comprobamos los metodos de tipo Integer */

    @Test
    public void Integer_AreEquals() {
        record1.setIntegerValue("Integer_Column_1", 1);
        record2.setIntegerValue("Integer_Column_1", 1);

        assertThat(record1.getIntegerValue("Integer_Column_1")).isEqualTo(record2.getIntegerValue("Integer_Column_1"));
    }

    @Test
    public void Integer_NotEquals() {
        record1.setIntegerValue("Integer_Column_2", 1);
        record2.setIntegerValue("Integer_Column_2", 2);

        assertThat(record1.getIntegerValue("Integer_Column_2")).isNotEqualTo(record2.getIntegerValue("Integer_Column_2"));
    }
	
	/*	Comprobamos los metodos de tipo Long */

    @Test
    public void Long_AreEquals() {
        record1.setLongValue("Long_Column_1", 1234567890);
        record2.setLongValue("Long_Column_1", 1234567890);

        assertThat(record1.getLongValue("Long_Column_1")).isEqualTo(record2.getLongValue("Long_Column_1"));
    }

    @Test
    public void Long_NotEquals() {
        record1.setLongValue("Long_Column_2", 1234567890l);
        record2.setLongValue("Long_Column_2", 9876543210l);

        assertThat(record1.getLongValue("Long_Column_2")).isNotEqualTo(record2.getLongValue("Long_Column_2"));
    }
	
	/*	Comprobamos los metodos de tipo Float */

    @Test
    public void Float_AreEquals() {
        record1.setFloatValue("Float_Column_1", 1.12f);
        record2.setFloatValue("Float_Column_1", 1.12f);

        assertThat(record1.getFloatValue("Float_Column_1")).isEqualTo(record2.getFloatValue("Float_Column_1"));
    }

    @Test
    public void Float_NotEquals() {
        record1.setFloatValue("Float_Column_2", 1.123f);
        record2.setFloatValue("Float_Column_2", 2.345f);

        assertThat(record1.getFloatValue("Float_Column_2")).isNotEqualTo(record2.getFloatValue("Float_Column_2"));
    }
	
	/*	Comprobamos los metodos de tipo Double */

    @Test
    public void Double_AreEquals() {
        record1.setDoubleValue("Double_Column_1", 123456789.123);
        record2.setDoubleValue("Double_Column_1", 123456789.123);

        assertThat(record1.getDoubleValue("Double_Column_1")).isEqualTo(record2.getDoubleValue("Double_Column_1"));
    }

    @Test
    public void Double_NotEquals() {
        record1.setDoubleValue("Double_Column_2", 123456789.123);
        record2.setDoubleValue("Double_Column_2", 9876543210.345);

        assertThat(record1.getDoubleValue("Double_Column_2")).isNotEqualTo(record2.getDoubleValue("Double_Column_2"));
    }
	
	/*	Comprobamos los metodos de tipo UUID */

    @Test
    public void UUID_AreEquals() {
        record1.setUUIDValue("UUID_Column_1", uuid1);
        record2.setUUIDValue("UUID_Column_1", uuid1);

        assertThat(record1.getUUIDValue("UUID_Column_1")).isEqualTo(record2.getUUIDValue("UUID_Column_1"));
    }

    @Test
    public void UUID_NotEquals() {
        record1.setUUIDValue("UUID_Column_2", uuid1);
        record2.setUUIDValue("UUID_Column_2", uuid2);

        assertThat(record1.getUUIDValue("UUID_Column_2")).isNotEqualTo(record2.getUUIDValue("UUID_Column_2"));
    }
	
	/*	Comprobamos el get y set general */

    @Test
    public void Get_Set_AreEquals_and_NotEquals() {

        record3.setValues(record1.getValues());
        record2.setUUIDValue("UUID_Column_2", uuid2);

        assertThat(record1.getValues()).isEqualTo(record3.getValues());
        assertThat(record1.getValues()).isNotEqualTo(record2.getValues());
    }


	/*	Comprobamos los return por default  */

    @Test
    public void Default_AreEquals() {
        record1.setStringValue("Columna_z", "z");

        assertThat(record1.getStringValue("Column_x")).isEqualTo("");
        assertThat(record1.getUUIDValue("Column_x")).isEqualTo(null);
        assertThat(record1.getLongValue("Column_x")).isEqualTo(0);

        assertThat(record1.getIntegerValue("Column_x")).isEqualTo(0);
        assertThat(record1.getFloatValue("Column_x")).isEqualTo(0);

        assertThat(record1.getDoubleValue("Column_x")).isEqualTo(0);
    }
}
