package unit.com.bitdubai.fermat_api.FermatException;

import static org.fest.assertions.api.Assertions.*;

import com.bitdubai.fermat_api.FermatException;

import org.junit.Before;
import org.junit.Test;

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
        testContext = "A = 1; B = 2";
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
        FermatException testCause1 = new FermatException(testMessage+ " Cause 1", null, "WE NEED TO TEST THE CHAIN OF CAUSES", "WE NEED TO TEST THE CHAIN OF CAUSES");
        FermatException testCause2 = new FermatException(testMessage+ " Cause 2", testCause1, "WE NEED TO TEST THE CHAIN OF CAUSES", "WE NEED TO TEST THE CHAIN OF CAUSES");
        testCause = new FermatException(testMessage+ " Cause", testCause2, "WE NEED TO TEST THE CHAIN OF CAUSES", "WE NEED TO TEST THE CHAIN OF CAUSES");
        testException = new FermatException(testMessage, testCause, testContext, testPossibleReason);
        System.out.println("--------------------------------------------------------------------\n" +
                "Fermat Error Manager - Unexpected Exception Report\n" +
                "--------------------------------------------------------------------\n");
        printException(testException, 1);
    }

    private int printException(final FermatException exception, final int depth){
        int printDepth;
        if(exception.getCause() != null)
            printDepth = printException(exception.getCause(),depth);
        else
            printDepth = depth;
        System.out.println("Exception #: " + printDepth);
        System.out.println(exception.toString());
        System.out.println("--------------------------------------------------------------------");
        return ++printDepth;
    }

}
