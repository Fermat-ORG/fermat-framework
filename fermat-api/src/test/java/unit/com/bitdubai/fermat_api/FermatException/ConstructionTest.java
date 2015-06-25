package unit.com.bitdubai.fermat_api.FermatException;

import static org.fest.assertions.api.Assertions.*;

import com.bitdubai.fermat_api.FermatException;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

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
    public void setUp(){
        testCause = null;
        testMessage = "TEST MESSAGE";
        testContext = "A = 1" + FermatException.CONTEXT_CONTENT_SEPARATOR +  "B = 2";
        testPossibleReason = "FERMAT IS WAY TOO COMPLEX FOR THAT";
    }

    @Test
    public void SimpleConstruction_ValidParameters_NotNull(){
        testException = new FermatException(testMessage, testCause, testContext, testPossibleReason);
        assertThat(testException).isNotNull();
    }

    @Test
    public void SimpleConstruction_ValidParameters_SameValues(){
        testException = new FermatException(testMessage, testCause, testContext, testPossibleReason);
        assertThat(testException.getMessage()).contains(testMessage);
        assertThat(testException.getContext()).isEqualTo(testContext);
        assertThat(testException.getPossibleReason()).isEqualTo(testPossibleReason);
        assertThat(testException.toString()).isNotEmpty();
    }

    @Test
    public void SimpleConstruction_WithACause_ReturnsCause(){
        testCause = new FermatException(testMessage+ " Cause", null, "WE NEED TO TEST THE CHAIN OF CAUSES", "WE NEED TO TEST THE CHAIN OF CAUSES");
        testException = new FermatException(testMessage, testCause, testContext, testPossibleReason);
        assertThat(testException.getCause()).isNotNull();
        assertThat(testException.getCause().toString()).isNotEmpty();
    }

    @Test
    public void SimpleConstruction_Printing(){
        FermatException testCause1 = FermatException.wrapException(new IOException("THIS IS A TEST"));
        FermatException testCause2 = new FermatException(testMessage+ " Cause 2", testCause1, "WE NEED TO TEST " + FermatException.CONTEXT_CONTENT_SEPARATOR + "THE CHAIN OF CAUSES", "WE NEED TO TEST THE CHAIN OF CAUSES");
        testCause = new FermatException(testMessage+ " Cause", testCause2, "WE NEED TO TEST " + FermatException.CONTEXT_CONTENT_SEPARATOR + "THE CHAIN OF CAUSES", "WE NEED TO TEST THE CHAIN OF CAUSES");
        testException = new FermatException(testMessage, testCause, testContext, testPossibleReason);
        System.out.println(constructErrorReport(testException));
        //countExceptionDepth(testException);

    }

    private String constructErrorReport(final FermatException exception){
        StringBuffer buffer = new StringBuffer("========================================================================================================================================================\n" +
                "Fermat Error Manager * Unexpected Exception Report\n" +
                "========================================================================================================================================================\n");
        buffer.append(constructExceptionReport(exception, 1));
        buffer.append("Exceptions Processed: " + exception.getDepth() + "\n");
        buffer.append("========================================================================================================================================================\n");
        return buffer.toString();
    }

    private String constructExceptionReport(final FermatException exception, final int depth){
        StringBuffer buffer = new StringBuffer();
        if (exception.getCause() != null) {
            buffer.append(constructExceptionReport(exception.getCause(), depth));
            exception.setDepth(exception.getCause().getDepth()+1);
        } else {
            exception.setDepth(depth);
        }
        buffer.append("********************************************************************************************************************************************************\n");
        buffer.append("Exception Number: " + exception.getDepth() + "\n");
        buffer.append(exception.toString());
        buffer.append("********************************************************************************************************************************************************\n");
        return buffer.toString();
    }

}
