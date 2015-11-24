package com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.abstract_classes;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkService;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.exceptions.CantLoadKeyPairException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;

/**
 * The class <code>AbstractNetworkService</code>
 * contains the basic functionality of a Fermat Network Service.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 29/10/2015.
 */
public abstract class AbstractNetworkService extends AbstractPlugin implements NetworkService {

    /**
     * Network Service details.
     */
    private final PlatformComponentType    platformComponentType   ;
    private final NetworkServiceType       networkServiceType      ;
    private final String                   name                    ;
    private final String                   alias                   ;
    private final String                   extraData               ;

    private final EventSource              eventSource             ;

    private       PlatformComponentProfile platformComponentProfile;

    protected     ECCKeyPair               identity                ;
    protected     boolean                  register                ;

    public AbstractNetworkService(final PluginVersionReference pluginVersionReference,
                                  final PlatformComponentType  platformComponentType ,
                                  final NetworkServiceType     networkServiceType    ,
                                  final String                 name                  ,
                                  final String                 alias                 ,
                                  final String                 extraData             ,
                                  final EventSource            eventSource           ) {

        super(pluginVersionReference);

        this.platformComponentType = platformComponentType;
        this.networkServiceType    = networkServiceType   ;
        this.name                  = name                 ;
        this.alias                 = alias                ;
        this.extraData             = extraData            ;
        this.eventSource           = eventSource          ;
    }

    public final boolean isRegister() {
        return register;
    }

    public abstract String getIdentityPublicKey();

    public final String getName() {
        return name;
    }

    public final String getAlias() {
        return alias;
    }

    public final String getExtraData() {
        return extraData;
    }

    public final PlatformComponentType getPlatformComponentType() {
        return platformComponentType;
    }

    public final NetworkServiceType getNetworkServiceType() {
        return networkServiceType;
    }

    public final EventSource getEventSource() {
        return eventSource;
    }

    public final PlatformComponentProfile getPlatformComponentProfilePluginRoot() {
        return platformComponentProfile;
    }

    public void setPlatformComponentProfilePluginRoot(PlatformComponentProfile platformComponentProfile) {
        this.platformComponentProfile = platformComponentProfile;
    }

    public abstract void initializeCommunicationNetworkServiceConnectionManager();


    private static final String PLUGIN_IDS_DIRECTORY_NAME = "security";
    private static final String PLUGIN_IDS_FILE_NAME      = "networkServiceKeyPairsFile";
    private static final String PAIR_SEPARATOR            = ";"         ;

    protected final void loadKeyPair(final PluginFileSystem pluginFileSystem) throws CantLoadKeyPairException {

        try {
            final PluginTextFile identityFile = pluginFileSystem.getTextFile(pluginId, PLUGIN_IDS_DIRECTORY_NAME, buildNetworkServiceKeyPairFileName(), FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);

            final String identityFileContent = identityFile.getContent();

            final String[] identityKeyPairSplit = identityFileContent.split(PAIR_SEPARATOR);

            if (identityKeyPairSplit.length == 2)
                this.identity = new ECCKeyPair(identityKeyPairSplit[0], identityKeyPairSplit[1]);
            else
                throw new CantLoadKeyPairException("identityKeyPairSplit: " + identityFileContent, "ErrorTrying to load the key pair, the string is not valid.");

        } catch (CantCreateFileException e) {

            throw new CantLoadKeyPairException(e, "", "Cant create ns identity file exception.");
        } catch( FileNotFoundException e) {

            try {

                ECCKeyPair identity = new ECCKeyPair();

                PluginTextFile pluginTextFile = pluginFileSystem.createTextFile(pluginId, PLUGIN_IDS_DIRECTORY_NAME, buildNetworkServiceKeyPairFileName(), FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);

                String fileContent = identity.getPrivateKey() + PAIR_SEPARATOR + identity.getPublicKey();

                pluginTextFile.setContent(fileContent);
                pluginTextFile.persistToMedia();

                this.identity = identity;

            } catch (CantCreateFileException | CantPersistFileException z) {

                throw new CantLoadKeyPairException(z, "", "Cant create, persist or who knows what....");
            }
        }
    }

    private String buildNetworkServiceKeyPairFileName() {
        return PLUGIN_IDS_FILE_NAME + "_" + pluginId;
    }

    public abstract void handleNewMessages(final FermatMessage message);

}
