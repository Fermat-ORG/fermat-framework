package unit.com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure.WalletFactoryMiddlewareProjectResource;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

public class GetDatabaseListTest extends TestCase {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testGetDatabaseListTest_NotNull() throws Exception {
        try {

            File file = new File("/home/lnacosta/Desktop/question.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(Question.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Question que= (Question) jaxbUnmarshaller.unmarshal(file);

            System.out.println(que.getId()+" "+que.getQuestionname());
            System.out.println("Answers:");
            List<WalletFactoryMiddlewareProjectResource> list=que.getResources();
            for(WalletFactoryMiddlewareProjectResource ans:list)
                System.out.println(ans.getName()+"  "+ans.getResourceType());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}