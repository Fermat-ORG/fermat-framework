package com.bitdubai.fermat_api.layer.all_definition.navigation_structure;

import com.bitdubai.fermat_api.layer.all_definition.navigation_structure.interfaces.FermatComboBox;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matias Furszyfer on 2015.09.01..
 */
public class RuntimeFernatComboBox implements FermatComboBox, Serializable {

    List<String> values;

    public RuntimeFernatComboBox() {
        values = new ArrayList<String>();
    }

    public void addValue(String value) {
        this.values.add(value);
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }
}
