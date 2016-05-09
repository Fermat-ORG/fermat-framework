package com.bitdubai.fermat_wpd_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure;

import com.bitdubai.fermat_api.layer.all_definition.github.GitHubConnection;
import com.bitdubai.fermat_wpd_api.all_definition.AppNavigationStructure;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Language;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Layout;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Resource;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.Skin;
import com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums.ResourceType;
import com.bitdubai.fermat_api.layer.all_definition.util.XMLParser;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.exceptions.CantSaveWalletFactoryProyect;
import com.bitdubai.fermat_wpd_api.layer.wpd_middleware.wallet_factory.interfaces.WalletFactoryProject;
import com.bitdubai.fermat_api.layer.all_definition.github.exceptions.GitHubNotAuthorizedException;
import com.bitdubai.fermat_api.layer.all_definition.github.exceptions.GitHubRepositoryNotFoundException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

/**
 * Created by rodrigo on 8/22/15.
 */
public class GithubManager {
    GitHubConnection gitHubConnection;
    final String ROOT_PATH = "seed-resources/wallet_resources/bitDubai/reference_wallet/";
    String walletPath;

    /**
     * Default Constructor
     * @throws GitHubRepositoryNotFoundException
     * @throws GitHubNotAuthorizedException
     */
    public GithubManager(String repository, String user, String password) throws GitHubRepositoryNotFoundException, GitHubNotAuthorizedException {
        gitHubConnection = new GitHubConnection(repository, user, password);
    }

    /**
     * Will create the paths and folder in git hub and upload the appropiate files
     * @param walletFactoryProject
     * @throws CantSaveWalletFactoryProyect
     */
    public void saveWalletFactoryProject(WalletFactoryProject walletFactoryProject) throws CantSaveWalletFactoryProyect {
        this.walletPath = ROOT_PATH + walletFactoryProject.getName() + "/";

        try{
            //save the navigation structure if any
            if (walletFactoryProject.getNavigationStructure() != null)
                saveNavigationStructure(walletFactoryProject.getNavigationStructure());

            //save the languages if any
            if (walletFactoryProject.getDefaultLanguage() != null)
                saveLanguage(walletFactoryProject.getDefaultLanguage(), true);
            for (Language language : walletFactoryProject.getLanguages()){
                saveLanguage(language, false);
            }

            //save the skins if any
            if (walletFactoryProject.getDefaultSkin() != null)
                saveSkin(walletFactoryProject.getDefaultSkin(), true);
            for (Skin skin : walletFactoryProject.getSkins()){
                saveSkin(skin, false);
            }
        } catch (Exception exception){
            throw new CantSaveWalletFactoryProyect("There was an error trying to upload project items to GitHub.", exception, null, null);
        }


    }

    private void saveLanguage(Language language, boolean isDefault){
        String languageFileName = language.getType().getCode() + ".xml";


        String savingPath;
        if (isDefault)
            savingPath = walletPath + "languages/default/" + languageFileName;
        else
            savingPath = walletPath + "languages/" + languageFileName;

        gitHubConnection.createGitHubTextFile(savingPath, XMLParser.parseObject(language), "new language");
    }

    private void saveNavigationStructure (AppNavigationStructure navigationStructure){
        String savingPath = walletPath + "navigation_structure/navigation_structure.xml";
        String content = XMLParser.parseObject(navigationStructure);

        gitHubConnection.createGitHubTextFile(savingPath, content, "new navigation structure");
    }

    private void saveSkin(Skin skin, boolean isDefault) throws IOException {
        String skinFileName = skin.getName() + ".xml";

        String savingPath;
        if (isDefault)
            savingPath = walletPath + "skins/default/";
        else
            savingPath = walletPath + "skins/";

        //I save all the image resources
        saveSkinResources(savingPath, skin.getResources());

        // I save the skin.xml file

        gitHubConnection.createGitHubTextFile(savingPath + skinFileName, XMLParser.parseObject(skin), "new skin added");

        //will save both layouts
        saveLandscapeLayouts(savingPath, skin.getLandscapeLayouts());
        savePortraitLayouts(savingPath, skin.getPortraitLayouts());
    }

    private void savePortraitLayouts(String savingPath, Map<String, Layout> portraitLayouts) {
        savingPath = savingPath + "portrait/layouts/";
        for (Layout layout : portraitLayouts.values()){
            gitHubConnection.createGitHubTextFile(savingPath + layout.getFilename(), "", "new layout");
        }
    }

    private void saveLandscapeLayouts(String savingPath, Map<String, Layout> landscapeLayouts) {
        savingPath = savingPath + "landscape/layouts/";
        for (Layout layout : landscapeLayouts.values()){
            gitHubConnection.createGitHubTextFile(savingPath + layout.getFilename(), "", "new layout");
        }
    }

    private void saveSkinResources(String savingPath, Map<String, Resource> resources) throws IOException {
        savingPath = savingPath + "resources/";
        for (Resource resource : resources.values()){
            //right now I can only support images types to upload to github
            if (resource.getResourceType() == ResourceType.IMAGE){
                gitHubConnection.createGitHubImageFile(savingPath + resource.getResourceDensity().toString() + "drawables/" + resource.getFileName(), getGithubImage(resource.getResourceFile()), "new image uploaded.");
            }
        }
    }

    private byte[] getGithubImage(File imageFile) throws IOException {
        FileInputStream fis = new FileInputStream(imageFile);
        //create FileInputStream which obtains input bytes from a file in a file system
        //FileInputStream is meant for reading streams of raw bytes such as image data. For reading streams of characters, consider using FileReader.

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];

        for (int readNum; (readNum = fis.read(buf)) != -1;) {
            //Writes to this byte array output stream
            bos.write(buf, 0, readNum);
            System.out.println("read " + readNum + " bytes,");
        }
        byte[] bytes = bos.toByteArray();

        return bytes;
    }
}
