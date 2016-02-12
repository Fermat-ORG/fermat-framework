package com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces;

import com.bitdubai.fermat_api.layer.world.interfaces.Currency;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantAssociatePairException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantDisassociatePairException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.CantListEarningsPairsException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.PairAlreadyAssociatedException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.exceptions.PairNotFoundException;
import com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.utils.WalletReference;

import java.util.List;
import java.util.UUID;

/**
 * The interface <code>com.bitdubai.fermat_cbp_api.layer.middleware.matching_engine.interfaces.EarningsSettings</code>
 * contains all the necessary methods to manage the earnings pairs of an specific wallet.
 * <p/>
 * To get an instance of this object you must pass through constructor the information of the associated wallet.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 31/01/2016.
 */
public interface EarningsSettings {

    /**
     * Through the method <code>associatePair</code> you can associate an earnings pair to the wallet in which we're working.
     * Automatically will be generated an UUID to the earnings pair, with it, we can identify the same.
     *
     * @param earningCurrency   the currency which we decided to extract the earnings.
     * @param linkedCurrency    the currency that we're linking to the previous selected currency to conform the pair.
     * @param walletReference   the information of the wallet that we're associating and where we will deposit the earnings.
     *
     * @return an instance of the well formed associated EarningPair-
     *
     * @throws CantAssociatePairException      if something goes wrong.
     * @throws PairAlreadyAssociatedException  if the pair is already associated.
     */
    EarningsPair associatePair(

            Currency        earningCurrency,
            Currency        linkedCurrency ,
            WalletReference walletReference

    ) throws CantAssociatePairException    ,
             PairAlreadyAssociatedException;

    /**
     * Through the method <code>disassociateEarningsPair</code> you can disassociate an earnings pair from the wallet in which we're working.
     *
     * @param earningsPairID an UUID identifying the pair.
     *
     * @throws CantDisassociatePairException if something goes wrong.
     * @throws PairNotFoundException         if the id cannot be found in database.
     */
    void disassociateEarningsPair(

            UUID earningsPairID

    ) throws CantDisassociatePairException,
             PairNotFoundException        ;

    /**
     * Through the method <code>disassociateEarningsPair</code> you can list all the earnings pairs associated with the wallet in which we're working.
     *
     * @return a list of earnings pair object with the information of each one.
     *
     * @throws CantListEarningsPairsException if something goes wrong.
     */
    List<EarningsPair> listEarningPairs(

    ) throws CantListEarningsPairsException;

}
