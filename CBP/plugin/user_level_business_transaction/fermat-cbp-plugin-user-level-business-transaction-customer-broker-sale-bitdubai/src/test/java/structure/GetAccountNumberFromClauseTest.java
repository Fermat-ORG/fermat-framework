package structure;

import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.regex.Pattern;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;


/**
 * Created by nelsonalfo on 05/04/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetAccountNumberFromClauseTest {

    @Mock
    private Clause inputClause;

    @Test
    public void onNullParam_ReturnEmpty() {
        assertThat(getAccountNumberFromClause(null)).isEmpty();
    }

    @Test
    public void onClauseValueEmptyOrNull_ReturnEmpty() {
        when(inputClause.getValue()).thenReturn("");
        assertThat(getAccountNumberFromClause(inputClause)).isEmpty();

        when(inputClause.getValue()).thenReturn(null);
        assertThat(getAccountNumberFromClause(inputClause)).isEmpty();
    }

    @Test
    public void onOnlyAccountNumber_ReturnAccountNumber() {
        when(inputClause.getValue()).thenReturn("3215654654654");
        assertThat(getAccountNumberFromClause(inputClause)).isEqualTo("3215654654654");

    }

    @Test
    public void onWellFormattedClauseValue_ReturnAccountNumber() {
        when(inputClause.getValue()).thenReturn("Bank: Banesco Account Number: 3215654654654\nAccount Type: Current");
        assertThat(getAccountNumberFromClause(inputClause)).isEqualTo("3215654654654");

        when(inputClause.getValue()).thenReturn("Bank: Banesco Account Number: 32-15654654654\nAccount Type: Current");
        assertThat(getAccountNumberFromClause(inputClause)).isEqualTo("32-15654654654");

        when(inputClause.getValue()).thenReturn("Bank: Banesco Account Number: 32-15654-654654\nAccount Type: Current");
        assertThat(getAccountNumberFromClause(inputClause)).isEqualTo("32-15654-654654");

        when(inputClause.getValue()).thenReturn("Bank: Banesco Account Number: 3215654654654 Account Type: Current");
        assertThat(getAccountNumberFromClause(inputClause)).isEqualTo("3215654654654");

        when(inputClause.getValue()).thenReturn("Bank: Banesco Account Number: 32-15654654654 Account Type: Current");
        assertThat(getAccountNumberFromClause(inputClause)).isEqualTo("32-15654654654");

        when(inputClause.getValue()).thenReturn("Bank: Banesco Account Number: 32-15654-654654 Account Type: Current");
        assertThat(getAccountNumberFromClause(inputClause)).isEqualTo("32-15654-654654");
    }

    @Test
    public void onNotWellFormattedClauseValue_ReturnEmpty() {
        when(inputClause.getValue()).thenReturn("Bank: Banesco\nAccount Number: \nAccount Type: Current");
        assertThat(getAccountNumberFromClause(inputClause)).isEmpty();

        when(inputClause.getValue()).thenReturn("Bank: Banesco\nAccount Number: 32-1565465s4654\nAccount Type: Current");
        assertThat(getAccountNumberFromClause(inputClause)).isEmpty();

        when(inputClause.getValue()).thenReturn("Bank: Banesco\nAccount Number: 321565465s4654\nAccount Type: Current");
        assertThat(getAccountNumberFromClause(inputClause)).isEmpty();

        when(inputClause.getValue()).thenReturn("Bank: Banesco Account Number: Account Type: Current");
        assertThat(getAccountNumberFromClause(inputClause)).isEmpty();

        when(inputClause.getValue()).thenReturn("Bank: Banesco Account Number: 32-1565465s4654 Account Type: Current");
        assertThat(getAccountNumberFromClause(inputClause)).isEmpty();

        when(inputClause.getValue()).thenReturn("Bank: Banesco Account Number: 321565465s4654 Account Type: Current");
        assertThat(getAccountNumberFromClause(inputClause)).isEmpty();

        when(inputClause.getValue()).thenReturn("Bank: Banesco Account Number: Account Type: Current");
        assertThat(getAccountNumberFromClause(inputClause)).isEmpty();

        when(inputClause.getValue()).thenReturn("Bank: BanescoAccount Number: 32-1565465s4654Account Type: Current");
        assertThat(getAccountNumberFromClause(inputClause)).isEmpty();

        when(inputClause.getValue()).thenReturn("Bank: BanescoAccount Number: 321565465s4654Account Type: Current");
        assertThat(getAccountNumberFromClause(inputClause)).isEmpty();
    }


    private String getAccountNumberFromClause(Clause clause) {
        String account = "";

        if (clause != null) {
            final String value = clause.getValue();

            if (value != null && !value.isEmpty()) {
                String clauseValue = clause.getValue();
                String[] split = clauseValue.split("\\D+:\\s*");
                account = split.length == 1 ? split[0] : split[1];

                return Pattern.matches("(\\d-?)+", account) ? account : "";
            }
        }

        return account;
    }
}
