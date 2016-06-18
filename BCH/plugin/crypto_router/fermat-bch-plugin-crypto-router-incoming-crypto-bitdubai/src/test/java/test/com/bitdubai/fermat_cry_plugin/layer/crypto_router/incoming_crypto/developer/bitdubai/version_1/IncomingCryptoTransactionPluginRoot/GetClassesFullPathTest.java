package test.com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.IncomingCryptoTransactionPluginRoot;

import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by rodrigo on 2015.07.04..
 */
public class GetClassesFullPathTest {
    static final char DOT = '.';
    static final char SLASH = '/';
    static final String CLASS_SUFFIX = ".class";
    static final String BAD_PACKAGE_ERROR = "Unable to get resources from path '%s'. Are you sure the package '%s' exists?";

    @Test
    public void generateClassesTree() throws ClassNotFoundException,Exception {

//        String scannedPackage = IncomingCryptoTransactionPluginRoot.class.getPackage().getName();
//        List<Class<?>> classes = find(scannedPackage);
//        IncomingCryptoTransactionPluginRoot root = new IncomingCryptoTransactionPluginRoot();
//
//
//        for (String myClass : root.getClassesFullPath()) {
//            /**
//             * True if it exists
//             */
//            assertTrue(classes.contains(Class.forName(myClass)));
//        }

    }

    private  List<Class<?>> find (String scannedPackage) throws Exception {
        String scannedPath = scannedPackage.replace(DOT, SLASH);
        URL scannedUrl = Thread.currentThread().getContextClassLoader().getResource(scannedPath);
        if (scannedUrl == null) {
            throw new IllegalArgumentException(String.format(BAD_PACKAGE_ERROR, scannedPath, scannedPackage));
        }
        File scannedDir = new File(scannedUrl.getFile());

        List<Class<?>> classes = new ArrayList<Class<?>>();
        for (File file : scannedDir.listFiles()) {
            classes.addAll(find(file, scannedPackage));
        }
        return classes;
    }

    private List<Class<?>> find(File file, String scannedPackage) throws Exception{
        List<Class<?>> classes = new ArrayList<Class<?>>();
        String resource = scannedPackage + DOT + file.getName();
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                classes.addAll(find(child, resource));
            }
        } else if (resource.endsWith(CLASS_SUFFIX)) {
            int endIndex = resource.length() - CLASS_SUFFIX.length();
            String className = resource.substring(0, endIndex);
           // try {
                classes.add(Class.forName(className));
           // } catch (ClassNotFoundException ignore) {
           // }
        }
        return classes;
    }

}
