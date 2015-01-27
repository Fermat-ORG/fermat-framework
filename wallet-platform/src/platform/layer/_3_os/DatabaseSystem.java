package platform.layer._3_os;

/**
 * Created by ciencias on 20.01.15.
 */

/**
 * The Database System knows which databases are already accessible on this device.
 */
public interface DatabaseSystem {
    public Database getDatabase (String databaseName);

    public Database createDatabase (String databaseName, String databaseSchema);

    void setContext (Object context);

}
