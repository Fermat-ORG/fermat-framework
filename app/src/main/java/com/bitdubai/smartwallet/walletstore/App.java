package com.bitdubai.smartwallet.walletstore;

import java.io.Serializable;

/**
 * �A�v���P�[�V�������.
 */
public class App implements Serializable {

    /** �V���A���o�[�W����. */
    private static final long serialVersionUID = -8730067026050196758L;

    /** �A�v����. */
    public String title;
    /** �ڍ׏��. */
    public String description;
    /** ������. */
    public String company;
    /** �]��. */
    public float rate;
    /** ���i. */
    public int value;

}
