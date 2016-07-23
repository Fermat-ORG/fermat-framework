package com.bitdubai.android_core.app.common.version_1.builders;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.bitdubai.fermat_android_api.engine.FooterViewPainter;

/**
 * Created by Matias Furszyfer on 2016.01.09..
 */
public class FooterBuilder {

    private FrameLayout slide_container;
    private RelativeLayout footer_container;
    private FooterViewPainter footerViewPainter;
    private LayoutInflater inflater;


    public FooterBuilder(LayoutInflater layoutInflater, FrameLayout slide_container, RelativeLayout footer_container, FooterViewPainter footerViewPainter) {
        this.slide_container = slide_container;
        this.footer_container = footer_container;
        this.footerViewPainter = footerViewPainter;
        this.inflater = layoutInflater;
    }

    public void addSlideContainer() {
        footerViewPainter.addNavigationViewFooterElementVisible(inflater, slide_container);
    }

    public void addFooterViewContainer() {
        footerViewPainter.addFooterViewContainer(inflater, footer_container);
    }

    public void reset() {
        inflater = null;
        footer_container = null;
        footerViewPainter = null;
        slide_container = null;
    }

    public static class Builder {

        public static void build(LayoutInflater layoutInflater, @NonNull FrameLayout slide_container, @NonNull RelativeLayout footer_container, FooterViewPainter footerViewPainter) {
            FooterBuilder footerBuilder = new FooterBuilder(layoutInflater, slide_container, footer_container, footerViewPainter);
            if (slide_container != null) footerBuilder.addSlideContainer();
            if (footer_container != null) footerBuilder.addFooterViewContainer();
            footerBuilder.reset();
        }

    }
}
