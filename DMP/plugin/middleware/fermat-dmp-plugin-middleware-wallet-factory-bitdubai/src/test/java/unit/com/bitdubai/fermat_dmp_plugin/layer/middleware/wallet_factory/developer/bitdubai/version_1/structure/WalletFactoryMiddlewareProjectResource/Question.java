package unit.com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure.WalletFactoryMiddlewareProjectResource;

import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by lnacosta on 2015.07.17..
 */
@XmlRootElement
public class Question {
    private int id;
    private String questionname;
    private List<WalletFactoryMiddlewareProjectResource> resources;

    public Question() {
    }

    public Question(int id, String questionname, List<WalletFactoryMiddlewareProjectResource> resources) {
        super();
        this.id = id;
        this.questionname = questionname;
        this.resources = resources;
    }

    @XmlAttribute
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @XmlElement
    public String getQuestionname() {
        return questionname;
    }

    public void setQuestionname(String questionname) {
        this.questionname = questionname;
    }

    @XmlElement
    public List<WalletFactoryMiddlewareProjectResource> getResources() {
        return resources;
    }

    public void setResources(List<WalletFactoryMiddlewareProjectResource> resources) {
        this.resources = resources;
    }
}