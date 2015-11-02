package com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.abstract_classes;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractPlugin;
import com.bitdubai.fermat_api.layer.all_definition.common.system.annotations.NeededAddonReference;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.components.enums.PlatformComponentType;
import com.bitdubai.fermat_api.layer.all_definition.components.interfaces.PlatformComponentProfile;
import com.bitdubai.fermat_api.layer.all_definition.crypto.asymmetric.ECCKeyPair;
import com.bitdubai.fermat_api.layer.all_definition.enums.Addons;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_api.layer.all_definition.enums.Platforms;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.network_service.enums.NetworkServiceType;
import com.bitdubai.fermat_api.layer.all_definition.network_service.interfaces.NetworkService;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PlatformTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_p2p_api.layer.all_definition.common.network_services.exceptions.CantLoadKeyPairException;
import com.bitdubai.fermat_p2p_api.layer.p2p_communication.commons.contents.FermatMessage;

import java.util.regex.Pattern;

/**
 * The class <code>AbstractNetworkService</code>
 * contains the basic functionality of a Fermat Network Service.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 29/10/2015.
 */
public abstract class AbstractNetworkService extends AbstractPlugin implements NetworkService, DealsWithPluginFileSystem {

    // Necessary References
    @NeededAddonReference(platform = Platforms.OPERATIVE_SYSTEM_API, layer = Layers.ANDROID, addon = Addons.PLUGIN_FILE_SYSTEM)
    protected     PluginFileSystem         pluginFileSystem        ;

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

    @Override
    public void start() throws CantStartPluginException {

        try {
            loadKeyPair();

            startNetworkService();
            this.serviceStatus = ServiceStatus.STARTED;
        } catch (CantLoadKeyPairException e) {
            throw new CantStartPluginException(e, "", "error trying to load the key pair.");
        }
    }

    public abstract void startNetworkService() throws CantStartPluginException;

    public abstract void initializeCommunicationNetworkServiceConnectionManager();

    private void loadKeyPair() throws CantLoadKeyPairException {

        final String PLUGIN_IDS_DIRECTORY_NAME = "security";
        final String PLUGIN_IDS_FILE_NAME      = "keyPair";
        final String PAIR_SEPARATOR            = "|";

        this.identity = new ECCKeyPair();

        try {
            try {

                PluginTextFile identityFile = pluginFileSystem.getTextFile(
                        pluginId,
                        PLUGIN_IDS_DIRECTORY_NAME,
                        PLUGIN_IDS_FILE_NAME,
                        FilePrivacy.PRIVATE,
                        FileLifeSpan.PERMANENT
                );

                final String identityKeyPairString = identityFile.getContent();
                final String[] identityKeyPairSplit = identityKeyPairString.split(Pattern.quote(PAIR_SEPARATOR));

                if (identityKeyPairSplit.length == 2)
                    this.identity = new ECCKeyPair(identityKeyPairSplit[0], identityKeyPairSplit[1]);
                else
                    throw new CantLoadKeyPairException("identityKeyPairSplit: "+identityKeyPairString, "ErrorTrying to load the key pair, the string is not valid.");

            } catch(final CantCreateFileException e) {

                throw new CantLoadKeyPairException(e, "", "Problem with network service identity file.");
            }
        } catch (final FileNotFoundException fileNotFoundException) {

            try {

                final ECCKeyPair eccKeyPair = new ECCKeyPair();

                final PluginTextFile platformTextFile = pluginFileSystem.createTextFile(pluginId, PLUGIN_IDS_DIRECTORY_NAME, PLUGIN_IDS_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);

                String fileContent = eccKeyPair.getPrivateKey() + PAIR_SEPARATOR + eccKeyPair.getPublicKey();

                platformTextFile.setContent(fileContent);
                platformTextFile.persistToMedia();

                this.identity = eccKeyPair;

            } catch (final CantCreateFileException  |
                    CantPersistFileException e) {

                throw new CantLoadKeyPairException(e, "", "Problem with network service identity file.");
            }
        }
    }

// todo delete
    @Override
    public void setPluginFileSystem(final PluginFileSystem pluginFileSystem) {
        this.pluginFileSystem = pluginFileSystem;
    }

}
