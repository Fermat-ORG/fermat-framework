package com.bitdubai.fermat_pip_addon.layer.platform_service.platform_info.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.DeviceDirectory;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PlatformFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PlatformTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_pip_api.layer.platform_service.platform_info.exceptions.CantLoadPlatformInformationException;
import com.bitdubai.fermat_pip_api.layer.platform_service.platform_info.exceptions.CantSetPlatformInformationException;
import com.bitdubai.fermat_pip_api.layer.platform_service.platform_info.interfaces.PlatformInfo;

/**
 * Created by natalia on 29/07/15.
 * Modified by lnacosta (laion.cj91@gmail.com) on 26/10/2015.
 */
public class PlatformInfoPlatformService {

    private static final String PLATFORM_INFO_DIRECTORY = DeviceDirectory.SYSTEM.getName();
    private static final String PLATFORM_INFO_FILE_NAME = "platform_info.xml";

    private final PlatformFileSystem platformFileSystem;

    private PlatformInfoPlatformServiceFileData platformInfo;

    public PlatformInfoPlatformService(final PlatformFileSystem platformFileSystem) {

        this.platformFileSystem = platformFileSystem;
    }

    private PlatformTextFile getPlatformInfoFile() throws CantLoadPlatformInformationException {
        PlatformTextFile xmlFile;
        try {
            xmlFile = platformFileSystem.getFile(PLATFORM_INFO_DIRECTORY, PLATFORM_INFO_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            xmlFile.loadFromMedia();

        } catch (final FileNotFoundException e) {
            // if the file doesn't exists, I'll create it.
            try {
                xmlFile = platformFileSystem.createFile(PLATFORM_INFO_DIRECTORY, PLATFORM_INFO_FILE_NAME, FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
                String xml = XMLParser.parseObject(new PlatformInfoPlatformServiceFileData());
                xmlFile.setContent(xml);
                xmlFile.persistToMedia();
            } catch (CantCreateFileException | CantPersistFileException e1) {
                throw new CantLoadPlatformInformationException(CantLoadPlatformInformationException.DEFAULT_MESSAGE, e1, "There was an error trying to create the Platform info xml file.", null);
            }
        } catch (CantCreateFileException | CantLoadFileException e) {
            throw new CantLoadPlatformInformationException(CantLoadPlatformInformationException.DEFAULT_MESSAGE, e, "There was an error trying to load Platform info xml file.", null);
        }

        return xmlFile;
    }

    public final PlatformInfoPlatformServiceFileData getPlatformInfo() throws CantLoadPlatformInformationException {

        platformInfo = (PlatformInfoPlatformServiceFileData) XMLParser.parseXML(getPlatformInfoFile().getContent(), platformInfo);
        return platformInfo;
    }

    private void saveInfoInFile(String xml) throws CantLoadPlatformInformationException {

        try {

            PlatformTextFile xmlFile = getPlatformInfoFile();
            xmlFile.setContent(xml);
            xmlFile.persistToMedia();

        } catch (CantPersistFileException e) {
            e.printStackTrace();
        }
    }

    public void setPlatformInfo(final PlatformInfo platformInfo) throws CantSetPlatformInformationException {

        try {
            String xml = XMLParser.parseObject(platformInfo);

            saveInfoInFile(xml);

        } catch (Exception e) {

            throw new CantSetPlatformInformationException(e, "There was an error trying persist Platform info xml file.", null);
        }

    }

}
