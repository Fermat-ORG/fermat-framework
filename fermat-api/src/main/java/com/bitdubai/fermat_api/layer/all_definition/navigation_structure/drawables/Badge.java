package com.bitdubai.fermat_api.layer.all_definition.navigation_structure.drawables;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.FermatDrawable;
import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.enums.SourceLocation;

import java.io.Serializable;

/**
 * Created by Matias Furszyfer on 2016.07.02..
 */
public class Badge extends FermatDrawable implements Serializable {

    public enum Position {
        TOP_RIGHT, RIGHT, LEFT, TOP_LEFT, CENTER
    }

    private int size;
    private int testSize;
    private String hexaColor;
    private Position position;
    private int number;


    public Badge(String resName) {
        super(1001, resName, SourceLocation.FERMAT_FRAMEWORK);
    }

    public int getSize() {
        return size;
    }

    public int getTestSize() {
        return testSize;
    }

    public void setTestSize(int testSize) {
        this.testSize = testSize;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getHexaColor() {
        return hexaColor;
    }

    public void setHexaColor(String hexaColor) {
        this.hexaColor = hexaColor;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
