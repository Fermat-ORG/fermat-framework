package unit.com.bitdubai.fermat_pip_addon.layer.platform_service.error_manager.developer.bitdubai.version_1.functional.ErrorManager;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_pip_addon.layer.platform_service.error_manager.developer.bitdubai.version_1.functional.ErrorReport;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by jorgegonzalez on 2015.07.03..
 */
@RunWith(MockitoJUnitRunner.class)
public class GenerateReportTest {

    private FermatException testException;
    private FermatException testCause;
    private String testMessage;
    private String testContext;
    private String testPossibleReason;

    private ErrorReport testErrorReport;

    @Before
    public void setUp() {
        testCause = null;
        testMessage = "TEST MESSAGE";
        testContext = "A = 1" + FermatException.CONTEXT_CONTENT_SEPARATOR + "B = 2";
        testPossibleReason = "FERMAT IS WAY TOO COMPLEX FOR THAT";
    }

    @Test
    public void SimpleConstruction_WithACause_ReturnsCause() {
        testCause = new FermatException(testMessage + " Cause", null, "WE NEED TO TEST THE CHAIN OF CAUSES", "WE NEED TO TEST THE CHAIN OF CAUSES");
        testException = new FermatException(testMessage, testCause, testContext, testPossibleReason);
        testErrorReport = new ErrorReport("TEST", "HIGH", testException);
        assertThat(testErrorReport.generateReport()).contains("WE NEED TO TEST THE CHAIN OF CAUSES");
        System.out.println(testErrorReport.generateReport());
    }

}
