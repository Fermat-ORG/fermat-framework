package com.bitdubai.fermat_api.layer.all_definition.resources_structure.enums;

/**
 * Created by Matias Furszyfer on 2015.08.06..
 */

public enum InstalationProgress {

    INSTALATION_START("IS"),
    SKIN_DOWNLOADED("SK"),
    LANGUAGE_DOWNLOADED("LD"),
    NAVIGATION_STRUCTURE_PORTRAIT_DOWNLOADED("NSPD"),
    NAVIGATION_STRUCTURE_LANDSCAPE_DOWNLOADED("NSLD"),
    RESOURCES_DOWNLOADED("RD"),
    LAYOUTS_PORTRAIT_DOWNLOADED("LPD"),
    LAYOUTS_LANDSCAPE_DOWNLOADED("LLD"),
    INSTALATION_FINISH("IF");


    private final String code;

    InstalationProgress(String Code) {
        this.code = Code;
    }

    public String getCode() {
        return this.code;
    }

    public static InstalationProgress getByCode(String code) {

        switch (code) {
            case "IS":
                return InstalationProgress.INSTALATION_START;
            case "SK":
                return InstalationProgress.SKIN_DOWNLOADED;
            case "NSPD":
                return InstalationProgress.NAVIGATION_STRUCTURE_PORTRAIT_DOWNLOADED;
            case "RD":
                return InstalationProgress.RESOURCES_DOWNLOADED;
            case "LPD":
                return InstalationProgress.LAYOUTS_PORTRAIT_DOWNLOADED;
            case "LLD":
                return InstalationProgress.LAYOUTS_LANDSCAPE_DOWNLOADED;
            case "IF":
                return InstalationProgress.INSTALATION_FINISH;
            case "LD":
                return InstalationProgress.LANGUAGE_DOWNLOADED;
        }

        /**
         * Return by default.
         */
        return InstalationProgress.INSTALATION_START;
    }

}
