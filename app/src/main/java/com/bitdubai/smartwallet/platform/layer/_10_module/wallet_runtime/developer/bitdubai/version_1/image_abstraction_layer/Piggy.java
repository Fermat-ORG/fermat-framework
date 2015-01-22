package com.bitdubai.smartwallet.platform.layer._10_module.wallet_runtime.developer.bitdubai.version_1.image_abstraction_layer;

import java.util.List;

/**
 * Created by ciencias on 15.01.15.
 */
public class Piggy implements Movable, Displayable, UserCreatable{
    List<BankNote> mBankNotes;
    List<Envelope> mEnvelopes;

    @Override
    public EnvelopeStatus getPosition() {
        return null;
    }


    @Override
    public Void moveTo(Position pPosition) {
        return null;
    }
}
