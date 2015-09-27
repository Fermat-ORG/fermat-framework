package com.bitdubai.fermat_dmp_android_clone_reference_nich_wallet.common.enums;

/**
 * Created by nelson on 30/07/15. <br>
 *
 * Represent the type of sections that can be displayed in the PinnedHeaderListView and its Adapter
 */
public enum HeaderTypes {
    /** Numbers type. Code: "#" - Regex: "\d" */
    NUMBER("#", "\\d"),
    /** Symbols type. Code: "@" - Regex: "[^a-zA-Z0-9]" */
    SYMBOL("@", "[^a-zA-Z0-9]"),
    /** Letters type. Code: "a" - Regex: "[a-zA-Z]" */
    LETTER("a", "[a-zA-Z]");

    private final String regexString;
    private final String stringCode;

    HeaderTypes(String stringCode, String regexString){
        this.stringCode = stringCode;
        this.regexString = regexString;
    }

    /**
     * @return the Regex String that match the header type
     */
    public String getRegex(){
        return this.regexString;
    }

    /**
     * @return the Code String that match the header type
     */
    public String getCode() {
        return stringCode;
    }
}
