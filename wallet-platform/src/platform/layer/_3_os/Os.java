package platform.layer._3_os;

/**
 * Created by ciencias on 03.01.15.
 */
public interface Os {

    FileSystem getFileSystem();
    DatabaseSystem getDatabaseSystem();
    void setContext (Object context);

}
