package com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums;

/**
 * Created by Matias Furszyfer on 2015.08.06..
 */

public enum WalletInstalationProgress {

    INSTALATION_START("IS"),
    SKIN_DOWNLOADED ("SK"),
    LANGUAGE_DOWNLOADED("LD"),
    NAVIGATION_STRUCTURE_PORTRAIT_DOWNLOADED("NSPD"),
    NAVIGATION_STRUCTURE_LANDSCAPE_DOWNLOADED ("NSLD"),
    RESOURCES_DOWNLOADED ("RD"),
    LAYOUTS_PORTRAIT_DOWNLOADED ("LPD"),
    LAYOUTS_LANDSCAPE_DOWNLOADED ("LLD"),
    INSTALATION_FINISH("IF");


    private final String code;

    WalletInstalationProgress(String Code) {
        this.code = Code;
    }

    public String getCode()   { return this.code ; }

    public static WalletInstalationProgress getByCode(String code) {

        switch (code) {
            case "IS": return WalletInstalationProgress.INSTALATION_START;
            case "SK": return WalletInstalationProgress.SKIN_DOWNLOADED;
            case "NSPD": return WalletInstalationProgress.NAVIGATION_STRUCTURE_PORTRAIT_DOWNLOADED;
            case "RD": return WalletInstalationProgress.RESOURCES_DOWNLOADED;
            case "LPD": return WalletInstalationProgress.LAYOUTS_PORTRAIT_DOWNLOADED;
            case "LLD": return WalletInstalationProgress.LAYOUTS_LANDSCAPE_DOWNLOADED;
            case "IF": return WalletInstalationProgress.INSTALATION_FINISH;
            case "LD": return WalletInstalationProgress.LANGUAGE_DOWNLOADED;
        }

        /**
         * Return by default.
         */
        return WalletInstalationProgress.INSTALATION_START;
    }

}
