package com.bitdubai.reference_niche_wallet.bitcoin_wallet.common.enums;

/**
 * Created by Matis Furszyfer on 2015.07.21..
 */
public enum FontSize {

    LARGE_FONT (16),
    MEDIUM_FONT   (12),
    SMALL_FONT (8);

    private final int code;

    FontSize(int Code) {
        this.code = Code;
    }

    public int getCode()   { return this.code ; }

    public static FontSize getByCode(int code) {

        switch (code) {
            case 16: return FontSize.LARGE_FONT;
            case 12: return FontSize.MEDIUM_FONT;
            case 8: return FontSize.SMALL_FONT;

        }

        /**
         * Return by default.
         */
        return FontSize.MEDIUM_FONT;
    }
}
