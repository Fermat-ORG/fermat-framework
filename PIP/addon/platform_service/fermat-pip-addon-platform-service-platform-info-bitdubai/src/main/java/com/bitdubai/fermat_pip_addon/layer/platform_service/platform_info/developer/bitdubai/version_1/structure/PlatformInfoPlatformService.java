package com.bitdubai.fermat_pip_addon.layer.platform_service.platform_info.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ScreenSize;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_api.layer.osa_android.file_system.DealsWithPlatformFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FileLifeSpan;
import com.bitdubai.fermat_api.layer.osa_android.file_system.FilePrivacy;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PlatformFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PlatformTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantCreateFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.FileNotFoundException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.platform_info.interfaces.exceptions.CantLoadPlatformInformationException;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by natalia on 29/07/15.
 */
public class PlatformInfoPlatformService implements DealsWithPlatformFileSystem,Serializable {
    PlatformInfo platformInfo;

    /**
     * DealsWithPlatformFileSystem interface member variables
     */
    PlatformFileSystem platformFileSystem;

    @Override
    public void setPlatformFileSystem(PlatformFileSystem platformFileSystem) {
        this.platformFileSystem = platformFileSystem;
    }


    private PlatformTextFile getPlatformInfoFile() throws CantLoadPlatformInformationException {
        PlatformTextFile xmlFile;
        try {
            xmlFile = platformFileSystem.getFile("PlatformInfoService", "PlaftformInfo.xml", FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
            xmlFile.loadFromMedia();

        } catch (FileNotFoundException e) {
            // if the file doesn't exists, I'll create it.
            try {
                xmlFile = platformFileSystem.createFile("PlatformInfoService", "PlaftformInfo.xml", FilePrivacy.PRIVATE, FileLifeSpan.PERMANENT);
                String xml = XMLParser.parseObject(new PlatformInfo());
                xmlFile.setContent(xml);
                xmlFile.persistToMedia();
            } catch (CantCreateFileException | CantPersistFileException e1) {
                throw new CantLoadPlatformInformationException(CantLoadPlatformInformationException.DEFAULT_MESSAGE, e1, "There was an error trying to create the Platform info xml file.", null);
            }
        } catch (CantCreateFileException | CantLoadFileException  e) {
            throw new CantLoadPlatformInformationException(CantLoadPlatformInformationException.DEFAULT_MESSAGE, e, "There was an error trying to load Platform info xml file.", null);
        }

        return xmlFile;
    }
    public PlatformInfo getPlatformInfo() throws CantLoadPlatformInformationException {
        platformInfo = (PlatformInfo) XMLParser.parseXML(getPlatformInfoFile().getContent(), platformInfo);
        return platformInfo;
    }

    private void saveInfoInFile(String xml) throws CantLoadPlatformInformationException {
        PlatformTextFile xmlFile = getPlatformInfoFile();
        xmlFile.setContent(xml);
        try {
            xmlFile.persistToMedia();
        } catch (CantPersistFileException e) {
            e.printStackTrace();
        }
    }

    public void setPlatformInfo(PlatformInfo platformInfo) throws CantLoadPlatformInformationException {
        String xml = XMLParser.parseObject(platformInfo);
        try {
            saveInfoInFile(xml);
        } catch (Exception e) {
            throw new CantLoadPlatformInformationException(CantLoadPlatformInformationException.DEFAULT_MESSAGE, e, "There was an error trying persist Platform info xml file." , null);
        }

    }

}
