package com.bitdubai.fermat_api.layer.all_definition.common.utils;

import com.bitdubai.fermat_api.layer.all_definition.common.interfaces.FermatManager;

import java.lang.reflect.Field;

/**
 * The class <code>com.bitdubai.fermat_api.layer.all_definition.common.utils.AssignableField</code>
 * haves all the funcionality of a fermat assignable field.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 28/10/2015.
 */
public final class AssignableField<T extends FermatManager> {

    private final Field              field   ;
    private final Class<? extends T> refClass;

    public AssignableField(final Field              field     ,
                           final Class<? extends T> refClass) {

        this.field    = field   ;
        this.refClass = refClass;
    }

    public final Field getField() {
        return field;
    }

    public final Class<? extends T> getRefClass() {
        return refClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AssignableField<?> that = (AssignableField<?>) o;

        return !(field != null ? !field.equals(that.field) : that.field != null) &&
               !(refClass != null ? !refClass.equals(that.refClass) : that.refClass != null);
    }

    @Override
    public int hashCode() {
        int result = field != null ? field.hashCode() : 0;
        result = 31 * result + (refClass != null ? refClass.hashCode() : 0);
        return result;
    }
}
