package unit.com.bitdubai.fermat_dmp_plugin.layer.basic_wallet.bitcoin_wallet.developer.bitdubai.version_1.structure.mocks;

import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginTextFile;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantLoadFileException;
import com.bitdubai.fermat_api.layer.osa_android.file_system.exceptions.CantPersistFileException;

/**
 * Created by jorgegonzalez on 2015.07.10..
 */
public class MockWalletIdsFile implements PluginTextFile {



    @Override
    public String getContent() {
        return null;
    }

    @Override
    public void setContent(String content) {

    }

    @Override
    public void persistToMedia() throws CantPersistFileException {

    }

    @Override
    public void loadFromMedia() throws CantLoadFileException {

    }

    @Override
    public void delete() {
        
    }
}
