package unit.com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure.WalletFactoryMiddlewareProjectResource;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_factory.enums.ResourceType;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Created by lnacosta on 2015.07.17..
 */
public class ResourceTypeAdapter extends XmlAdapter {
    @Override
    public ResourceType unmarshal(Object v) throws Exception {
        return ResourceType.fromValue(v.toString());
    }

    @Override
    public String marshal(Object v) throws Exception {
        return ((ResourceType) v).value();
    }
}
