package com.bitdubai.fermat_api.layer.all_definition.common.system.utils;

import com.bitdubai.fermat_api.layer.all_definition.common.system.enums.DeveloperUtils;

/**
 * The class <code>DevelopersUtilReference</code>
 * haves all the information of a Developers Util Reference.
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/10/2015.
 */
public class DevelopersUtilReference {

    private static final int HASH_PRIME_NUMBER_PRODUCT = 1523;
    private static final int HASH_PRIME_NUMBER_ADD = 2819;

    private DeveloperUtils developerUtil;

    public DevelopersUtilReference(final DeveloperUtils developerUtil) {

        this.developerUtil = developerUtil;
    }

    public DevelopersUtilReference() {
    }

    public final DeveloperUtils getDeveloperUtil() {
        return developerUtil;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DevelopersUtilReference)) return false;

        DevelopersUtilReference that = (DevelopersUtilReference) o;

        return developerUtil.equals(that.getDeveloperUtil());
    }

    @Override
    public final int hashCode() {
        int c = 0;
        if (developerUtil != null)
            c += developerUtil.hashCode();
        return HASH_PRIME_NUMBER_PRODUCT * HASH_PRIME_NUMBER_ADD + c;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("DevelopersUtilReference{")
                .append("developerUtil=").append(developerUtil)
                .append('}').toString();
    }

}
