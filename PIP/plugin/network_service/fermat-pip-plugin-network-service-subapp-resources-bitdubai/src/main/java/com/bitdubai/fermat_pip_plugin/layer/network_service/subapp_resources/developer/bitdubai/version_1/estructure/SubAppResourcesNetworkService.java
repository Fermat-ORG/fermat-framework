package com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.estructure;

import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by natalia on 28/07/15.
 */
public class SubAppResourcesNetworkService {

    String REPOSITORY_LINK = "https://raw.githubusercontent.com/bitDubai/";
    private UUID resourcesId;

    /**
     * Constructor
     */

    public SubAppResourcesNetworkService(UUID resourcesId) {

        this.resourcesId = resourcesId;
    }

    /**
     * SubAppResources Implementation
     */

    /**
     * This method returns the resourcesId
     *
     * @return the Id of resources being represented
     */
//    @Override
//    public UUID getResourcesId() {
//        return this.resourcesId;
//    }
//
//    /**
//     * This method gives us the manifest file of the resources
//     *
//     * @return string that contains the resources manifest (a file describing the resources)
//     * @throws CantGetManifestException
//     */
//
//    @Override
//    public String getManifest() throws CantGetManifestException {
//        return "Method: getManifest - NO TIENE valor ASIGNADO para RETURN";
//    }
//
//    /**
//     * This method let us get an skin file referenced by its name
//     *
//     * @param fileName the name of the Skin file (without the path structure).
//     * @return The content of the file
//     * @throws CantGetSkinFileException
//     */
//
//    @Override
//    public String getSkinFile(String fileName) throws CantGetSkinFileException {
//        return "Method: getSkinFile - NO TIENE valor ASIGNADO para RETURN";
//    }
//
//    /**
//     * This method let us get a language file referenced by a name
//     *
//     * @param fileName the name of the Language file (without the path structure).
//     * @return The content of the file
//     * @throws CantGetLanguageFileException
//     */
//
//    @Override
//    public String getLanguageFile(String fileName) throws CantGetLanguageFileException {
//        return "Method: getLanguageFile - NO TIENE valor ASIGNADO para RETURN";
//    }
//
//    /**
//     * This method let us get an image referenced by a name
//     *
//     * @param imageName the name of the image resource found in the skin file
//     * @return the image represented as a byte array
//     * @throws CantGetResourcesException
//     */
//
//
//    @Override
//    public byte[] getImageResource(String imageName) throws CantGetResourcesException {
//        return new byte[0];
//    }
//
//    @Override
//    public byte[] getVideoResource(String videoName) throws CantGetResourcesException {
//        return new byte[0];
//    }
//
//    @Override
//    public byte[] getSoundResource(String soundName) throws CantGetResourcesException {
//        return new byte[0];
//    }
//
//    @Override
//    public String getFontStyle(String styleName) {
//        return "Method: getFontStyle - NO TIENE valor ASIGNADO para RETURN";
//    }
//
//    @Override
//    public String getLayoutResource(String layoutName) throws CantGetResourcesException {
//        return "Method: getLayoutResource - NO TIENE valor ASIGNADO para RETURN";
//    }


    // Private instances methods declarations.

    /**
     * <p>This method connects to the repository and download string file resource for wallet on byte (Private Method)
     *
     * @param repoResource name of repository where wallet files resources are stored
     * @param fileName     Name of resource file
     * @return string resource object
     * @throws MalformedURLException
     * @throws IOException
     * @throws FileNotFoundException
     */
    private String getRepositoryStringFile(String repoResource, String fileName) throws IOException, FileNotFoundException {
        String link = REPOSITORY_LINK + repoResource + "/master/" + fileName;

        URL url = new URL(link);
        HttpURLConnection http = (HttpURLConnection) url.openConnection();

        Map<String, List<String>> headerFields = http.getHeaderFields();
        // If URL is getting 301 and 302 redirection HTTP code then get new URL link.
        // This below for loop is totally optional if you are sure that your URL is not getting redirected to anywhere
        for (String header : headerFields.get(null)) {
            if (header.contains(" 302 ") || header.contains(" 301 ")) {
                link = headerFields.get("Location").get(0);
                url = new URL(link);
                http = (HttpURLConnection) url.openConnection();
                headerFields = http.getHeaderFields();
            }
        }

        InputStream crunchifyStream = http.getInputStream();
        String response = getStringFromStream(crunchifyStream);

        return response;

    }

    /**
     * <p>This method connects to the repository and download resource image file for wallet on byte
     *
     * @param repoResource name of repository where wallet files resources are stored
     * @param fileName     Name of resource file
     * @return byte image object
     * @throws MalformedURLException
     * @throws IOException
     * @throws FileNotFoundException
     */

    private byte[] getRepositoryImageFile(String repoResource, String fileName) throws IOException, FileNotFoundException {

        String link = REPOSITORY_LINK + repoResource + "/master/" + fileName;

        URL url = new URL(link);
        HttpURLConnection http = (HttpURLConnection) url.openConnection();
        BufferedInputStream in = new BufferedInputStream(http.getInputStream());
        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
        int c;
        while ((c = in.read()) != -1) {
            byteArrayOut.write(c);
        }

        in.close();
        return byteArrayOut.toByteArray();

    }

    /**
     * <p> Return the string content from a Stream
     *
     * @param stream
     * @return String Stream Object
     * @throws IOException
     */

    private String getStringFromStream(InputStream stream) throws IOException {
        if (stream != null) {
            Writer writer = new StringWriter();

            char[] buffer = new char[2048];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
                int counter;
                while ((counter = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, counter);
                }
            } finally {
                stream.close();
            }
            return writer.toString();
        } else {
            return "No Contents";
        }
    }


}
