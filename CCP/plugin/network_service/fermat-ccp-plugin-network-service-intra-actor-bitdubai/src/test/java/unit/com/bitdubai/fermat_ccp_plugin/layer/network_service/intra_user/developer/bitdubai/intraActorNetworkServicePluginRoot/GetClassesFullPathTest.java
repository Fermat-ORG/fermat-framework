package unit.com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.intraActorNetworkServicePluginRoot;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_ccp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.IntraActorNetworkServicePluginRoot;

import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by Joaquin C. on 18/12/15.
 */
public class GetClassesFullPathTest {

    private static final char DOT = '.';
    private static final char SLASH = '/';
    private static final String CLASS_SUFFIX = ".class";
    private static final String BAD_PACKAGE_ERROR = "Unable to get resources from path '%s'. Are you sure the package '%s' exists?";

    @Test
    public void generateClassesTree() throws ClassNotFoundException, FermatException {

        List<Class<?>> classes = find(IntraActorNetworkServicePluginRoot.class.getPackage().getName());
        IntraActorNetworkServicePluginRoot root = new IntraActorNetworkServicePluginRoot();
        for (String myClass : root.getClassesFullPath()) {
            /**
             * True if it exists
             */
            assertTrue(classes.contains(Class.forName(myClass)));
        }

    }

    private List<Class<?>> find(String scannedPackage) throws FermatException {
        String scannedPath = scannedPackage.replace(DOT, SLASH);
        URL scannedUrl = Thread.currentThread().getContextClassLoader().getResource(scannedPath);
        if (scannedUrl == null) {
            throw new IllegalArgumentException(String.format(BAD_PACKAGE_ERROR, scannedPath, scannedPackage));
        }
        File scannedDir = new File(scannedUrl.getFile());

        File[] scannedFiles = scannedDir.listFiles();
        List<Class<?>> classes = new ArrayList<>();

        if (scannedFiles != null) {
            for (File file : scannedFiles) {
                classes.addAll(find(file, scannedPackage));
            }
        }
        return classes;
    }

    private List<Class<?>> find(File file, String scannedPackage) throws FermatException {
        List<Class<?>> classes = new ArrayList<>();
        String resource = scannedPackage + DOT + file.getName();
        if (file.isDirectory()) {
            File[] scannedFiles = file.listFiles();
            if (scannedFiles != null) {
                for (File child : scannedFiles) {
                    classes.addAll(find(child, resource));
                }
            }
        } else if (resource.endsWith(CLASS_SUFFIX)) {
            int endIndex = resource.length() - CLASS_SUFFIX.length();
            String className = resource.substring(0, endIndex);
            try {
                classes.add(Class.forName(className));
            } catch (ClassNotFoundException ignore) {
                throw new FermatException("GetClassesFullPathTest", ignore, null, null);
            }
        }
        return classes;
    }

}
