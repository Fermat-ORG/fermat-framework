package platform.layer._5_license;



/**
 * Created by ciencias on 21.01.15.
 */
public interface LicenseSubsystem {
    public void start () throws CantStartSubsystemException;
    public LicenseManager getLicenseManager();
}
