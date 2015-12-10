package com.bitdubai.fermat_dap_api.layer.dap_transaction.appropriation_stats.interfaces;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_dap_api.layer.all_definition.digital_asset.DigitalAsset;
import com.bitdubai.fermat_dap_api.layer.dap_actor.asset_user.interfaces.ActorAssetUser;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.appropriation_stats.exceptions.CantCheckAppropriationStatsException;
import com.bitdubai.fermat_dap_api.layer.dap_transaction.appropriation_stats.exceptions.CantStartAppropriationStatsException;

import java.util.List;
import java.util.Map;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 17/10/15.
 */
public interface AppropriationStatsManager extends FermatManager {
    /**
     * This method is mainly called from the Asset Appropriation plugin. Which mean that its usage
     * is only in the user side, and not in the Issuer side.
     * This method HAS to be called when an asset has been successfully appropriated. So the Issuer can keep track
     * what happened with his assets.
     *
     * @param assetAppropriated    The {@link DigitalAsset} that was appropriated.
     * @param userThatAppropriated The {@link ActorAssetUser} that appropriated that asset.
     * @throws CantStartAppropriationStatsException In case something bad happen while starting
     *                                              the appropriation stats data flow.
     */
    void assetAppropriated(DigitalAsset assetAppropriated, ActorAssetUser userThatAppropriated) throws CantStartAppropriationStatsException;

    /**
     * This method search for all the assets appropriated by a specific user.
     *
     * @param userThatAppropriated The {@link ActorAssetUser} that appropriate the assets.
     * @return {@link List} instance filled with all the {@link DigitalAsset} that had been appropriated by
     * that user, or a {@link java.util.Collections.EmptyList} if there were none.
     * @throws CantCheckAppropriationStatsException In case something bad happen while trying to query
     *                                              the appropriation stats.
     */
    List<DigitalAsset> assetsAppropriatedByUser(ActorAssetUser userThatAppropriated) throws CantCheckAppropriationStatsException;

    /**
     * This method search for all the users that have appropriated a specific asset.
     *
     * @param assetAppropriated The {@link DigitalAsset} that was appropriated
     * @return {@link List} instance filled with all the {@link ActorAssetUser} that had appropriated
     * that specific asset.
     * @throws CantCheckAppropriationStatsException In case something bad happen while trying to query
     *                                              the appropriation stats.
     */
    List<ActorAssetUser> usersThatAppropriatedAsset(DigitalAsset assetAppropriated) throws CantCheckAppropriationStatsException;

    /**
     * This method search for all the records registered of the assets that has been
     * appropriated and the users that did so.
     *
     * @return {@link Map} instance filled with all the {@link ActorAssetUser} and the {@link DigitalAsset} that they appropriate.
     * Or an {@link java.util.Collections.EmptyMap} if the database is empty.
     * @throws CantCheckAppropriationStatsException In case something bad happen while trying to query
     *                                              the appropriation stats.
     */
    Map<ActorAssetUser, DigitalAsset> allAppropriationStats() throws CantCheckAppropriationStatsException;
}
