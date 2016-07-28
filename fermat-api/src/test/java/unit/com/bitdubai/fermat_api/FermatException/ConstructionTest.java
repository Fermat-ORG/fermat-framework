package unit.com.bitdubai.fermat_api.FermatException;

import com.bitdubai.fermat_api.FermatException;

import org.junit.Before;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by jorgegonzalez on 2015.06.24..
 */
public class ConstructionTest {

    private FermatException testException;
    private FermatException testCause;
    private String testMessage;
    private String testContext;
    private String testPossibleReason;

    @Before
    public void setUp() {
        testCause = null;
        testMessage = "TEST MESSAGE";
        testContext = new StringBuilder().append("A = 1").append(FermatException.CONTEXT_CONTENT_SEPARATOR).append("B = 2").toString();
        testPossibleReason = "FERMAT IS WAY TOO COMPLEX FOR THAT";
    }

    @Test
    public void SimpleConstruction_ValidParameters_NotNull() {
        testException = new FermatException(testMessage, testCause, testContext, testPossibleReason);
        assertThat(testException).isNotNull();
    }

    @Test
    public void SimpleConstruction_ValidParameters_SameValues() {
        testException = new FermatException(testMessage, testCause, testContext, testPossibleReason);
        assertThat(testException.getMessage()).contains(testMessage);
        assertThat(testException.getContext()).isEqualTo(testContext);
        assertThat(testException.getPossibleReason()).isEqualTo(testPossibleReason);
        assertThat(testException.toString()).isNotEmpty();
        System.out.println(constructErrorReport("SimpleConstruction_ValidParameters_SameValues", testException));
    }

    @Test
    public void SimpleConstruction_NullContextOrReason_ReturnNA() {
        testException = new FermatException(testMessage, testCause, null, null);
        assertThat(testException.getContext()).isEqualTo("N/A");
        assertThat(testException.getPossibleReason()).isEqualTo("N/A");
        System.out.println(constructErrorReport("SimpleConstruction_NullContextOrReason_ReturnNA", testException));
    }

    @Test
    public void SimpleConstruction_EmptyContextOrReason_ReturnNA() {
        testException = new FermatException(testMessage, testCause, "", "");
        assertThat(testException.getContext()).isEqualTo("N/A");
        assertThat(testException.getPossibleReason()).isEqualTo("N/A");
        System.out.println(constructErrorReport("SimpleConstruction_EmptyContextOrReason_ReturnNA", testException));
    }

    @Test
    public void SimpleConstruction_WithACause_ReturnsCause() {
        testCause = new FermatException(new StringBuilder().append(testMessage).append(" Cause").toString(), null, "WE NEED TO TEST THE CHAIN OF CAUSES", "WE NEED TO TEST THE CHAIN OF CAUSES");
        testException = new FermatException(testMessage, testCause, testContext, testPossibleReason);
        assertThat(testException.getCause()).isNotNull();
        assertThat(testException.getCause().toString()).isNotEmpty();
        System.out.println(constructErrorReport("SimpleConstruction_WithACause_ReturnsCause", testException));
    }

    private String constructErrorReport(final String method, final FermatException exception) {
        StringBuffer buffer = new StringBuffer("========================================================================================================================================================\n");
        buffer.append("Fermat Error Manager * Unexpected Exception Report\n");
        buffer.append("========================================================================================================================================================\n");
        buffer.append(method).append("\n");
        buffer.append(constructExceptionReport(exception));
        buffer.append("Exceptions Processed: ").append(exception.getDepth()).append("\n");
        buffer.append("========================================================================================================================================================\n");
        return buffer.toString();
    }

    private String constructExceptionReport(final FermatException exception) {
        if (exception == null)
            return "";
        StringBuffer buffer = new StringBuffer(constructExceptionReport(exception.getCause()));
        buffer.append("********************************************************************************************************************************************************\n");
        buffer.append(exception.toString());
        buffer.append("********************************************************************************************************************************************************\n");
        return buffer.toString();
    }

}
