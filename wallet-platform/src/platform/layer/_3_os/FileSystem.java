package platform.layer._3_os;

/**
 * Created by ciencias on 20.01.15.
 */
public interface FileSystem {

    public PlatformFile getFile (String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan) throws FileNotFoundException;

    public PlatformFile createFile (String directoryName, String fileName, FilePrivacy privacyLevel, FileLifeSpan lifeSpan );

    void setContext (Object context);

}
