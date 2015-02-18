package com.bitdubai.wallet_platform_api.layer._12_module.wallet_manager;

import java.util.List;
import java.util.UUID;

/**
 * Created by ciencias on 25.01.15.
 */
public interface WalletManager {

    public List<Wallet> getUserWallets();

    public void loadUserWallets (UUID userId) throws CantLoadWalletsException;

    public void createDefaultWallets (UUID userId) throws CantCreateDefaultWalletsException;
}

// LOUI TODO: Fijate que los Event Handlers normalmente usan la interfaz que el plugin root implementa que normalmente se termina en MANAGER. Asegurate por favor que esto sea asi en todos los plugins, porque ayer me parece que te dije de manera equivocada que debian llamarlo por la interfaz de la clase, y parece que no es asi como lo estuvimos haciendo.