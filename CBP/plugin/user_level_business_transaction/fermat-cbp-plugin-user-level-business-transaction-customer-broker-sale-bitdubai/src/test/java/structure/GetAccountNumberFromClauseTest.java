package structure;

import com.bitdubai.fermat_cbp_api.all_definition.negotiation.Clause;
import com.bitdubai.fermat_cbp_api.all_definition.util.NegotiationClauseHelper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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
        assertThat(NegotiationClauseHelper.getAccountNumberFromClause(null)).isEmpty();
    }

    @Test
    public void onClauseValueEmptyOrNull_ReturnEmpty() {
        when(inputClause.getValue()).thenReturn("");
        assertThat(NegotiationClauseHelper.getAccountNumberFromClause(inputClause)).isEmpty();

        when(inputClause.getValue()).thenReturn(null);
        assertThat(NegotiationClauseHelper.getAccountNumberFromClause(inputClause)).isEmpty();
    }

    @Test
    public void onOnlyAccountNumber_ReturnAccountNumber() {
        when(inputClause.getValue()).thenReturn("3215654654654");
        assertThat(NegotiationClauseHelper.getAccountNumberFromClause(inputClause)).isEqualTo("3215654654654");

    }

    @Test
    public void onWellFormattedClauseValue_ReturnAccountNumber() {
        when(inputClause.getValue()).thenReturn("Bank: Banesco Account Number: 3215654654654\nAccount Type: Current");
        assertThat(NegotiationClauseHelper.getAccountNumberFromClause(inputClause)).isEqualTo("3215654654654");

        when(inputClause.getValue()).thenReturn("Bank: Banesco Account Number: 32-15654654654\nAccount Type: Current");
        assertThat(NegotiationClauseHelper.getAccountNumberFromClause(inputClause)).isEqualTo("32-15654654654");

        when(inputClause.getValue()).thenReturn("Bank: Banesco Account Number: 32-15654-654654\nAccount Type: Current");
        assertThat(NegotiationClauseHelper.getAccountNumberFromClause(inputClause)).isEqualTo("32-15654-654654");

        when(inputClause.getValue()).thenReturn("Bank: Banesco Account Number: 3215654654654 Account Type: Current");
        assertThat(NegotiationClauseHelper.getAccountNumberFromClause(inputClause)).isEqualTo("3215654654654");

        when(inputClause.getValue()).thenReturn("Bank: Banesco Account Number: 32-15654654654 Account Type: Current");
        assertThat(NegotiationClauseHelper.getAccountNumberFromClause(inputClause)).isEqualTo("32-15654654654");

        when(inputClause.getValue()).thenReturn("Bank: Banesco Account Number: 32-15654-654654 Account Type: Current");
        assertThat(NegotiationClauseHelper.getAccountNumberFromClause(inputClause)).isEqualTo("32-15654-654654");

        assertThat(NegotiationClauseHelper.getAccountNumberFromString("Bank: Banesco Account Number: 3215654654654\nAccount Type: Current")).isEqualTo("3215654654654");
    }

    @Test
    public void onNotWellFormattedClauseValue_ReturnEmpty() {
        when(inputClause.getValue()).thenReturn("Bank: Banesco\nAccount Number: \nAccount Type: Current");
        assertThat(NegotiationClauseHelper.getAccountNumberFromClause(inputClause)).isEmpty();

        when(inputClause.getValue()).thenReturn("Bank: Banesco\nAccount Number: 32-1565465s4654\nAccount Type: Current");
        assertThat(NegotiationClauseHelper.getAccountNumberFromClause(inputClause)).isEmpty();

        when(inputClause.getValue()).thenReturn("Bank: Banesco\nAccount Number: 321565465s4654\nAccount Type: Current");
        assertThat(NegotiationClauseHelper.getAccountNumberFromClause(inputClause)).isEmpty();

        when(inputClause.getValue()).thenReturn("Bank: Banesco Account Number: Account Type: Current");
        assertThat(NegotiationClauseHelper.getAccountNumberFromClause(inputClause)).isEmpty();

        when(inputClause.getValue()).thenReturn("Bank: Banesco Account Number: 32-1565465s4654 Account Type: Current");
        assertThat(NegotiationClauseHelper.getAccountNumberFromClause(inputClause)).isEmpty();

        when(inputClause.getValue()).thenReturn("Bank: Banesco Account Number: 321565465s4654 Account Type: Current");
        assertThat(NegotiationClauseHelper.getAccountNumberFromClause(inputClause)).isEmpty();

        when(inputClause.getValue()).thenReturn("Bank: Banesco Account Number: Account Type: Current");
        assertThat(NegotiationClauseHelper.getAccountNumberFromClause(inputClause)).isEmpty();

        when(inputClause.getValue()).thenReturn("Bank: BanescoAccount Number: 32-1565465s4654Account Type: Current");
        assertThat(NegotiationClauseHelper.getAccountNumberFromClause(inputClause)).isEmpty();

        when(inputClause.getValue()).thenReturn("Bank: BanescoAccount Number: 321565465s4654Account Type: Current");
        assertThat(NegotiationClauseHelper.getAccountNumberFromClause(inputClause)).isEmpty();

        assertThat(NegotiationClauseHelper.getAccountNumberFromString("Bank: BanescoAccount Number: 321565465s4654Account Type: Current")).isEmpty();
    }
}
