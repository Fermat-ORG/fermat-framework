package platform.layer._8_communication;

/**
 * Created by ciencias on 20.01.15.
 */
public interface CommunicationSubsystem {
    public void start () throws CantStartSubsystemException;
    public CommunicationChannel getCommunicationChannel();
}
