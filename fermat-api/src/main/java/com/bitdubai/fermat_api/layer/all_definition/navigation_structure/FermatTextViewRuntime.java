package com.bitdubai.fermat_api.layer.all_definition.navigation_structure;

import com.bitdubai.fermat_api.layer.all_definition.enums.FontType;

import java.io.Serializable;

/**
 * Created by Matias Furszyfer on 2016.07.06..
 */
public class FermatTextViewRuntime extends FermatView implements Serializable {

    private float titleSize = 24;
    private String title;
    private FontType fontType;

    public FermatTextViewRuntime() {
    }

    public float getTitleSize() {
        return titleSize;
    }

    public void setTitleSize(float titleSize) {
        this.titleSize = titleSize;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public FontType getFontType() {
        return fontType;
    }

    public void setFontType(FontType fontType) {
        this.fontType = fontType;
    }
}
