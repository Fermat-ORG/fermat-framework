package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.rest.services;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.context.NodeContext;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.context.NodeContextItem;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDeveloperDatabaseFactoryTemp;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.rest.RestFulServices;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.rest.DeveloperDatabaseResource</code>
 * <p/>
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 29/03/2016.
 *
 * @author lnacosta
 * @version 1.0
 * @since Java JDK 1.7
 */
@Path("/developerDatabase")
public class DeveloperDatabaseResource implements RestFulServices {

    private CommunicationsNetworkNodeP2PDeveloperDatabaseFactoryTemp developerDatabaseFactory;

    private CommunicationsNetworkNodeP2PDeveloperDatabaseFactoryTemp getDeveloperDatabaseFactory() {

        if (developerDatabaseFactory == null)
            developerDatabaseFactory = (CommunicationsNetworkNodeP2PDeveloperDatabaseFactoryTemp) NodeContext.get(NodeContextItem.DEVELOPER_DATABASE_FACTORY);

        return developerDatabaseFactory;
    }

    @GET
    @Produces(MediaType.TEXT_HTML)
    public String listTables(){

        CommunicationsNetworkNodeP2PDeveloperDatabaseFactoryTemp developerDatabaseFactory = getDeveloperDatabaseFactory();

        if (developerDatabaseFactory != null) {

            try {

                List<String> tableList = developerDatabaseFactory.getTableList();

                StringBuilder stringBuilder = new StringBuilder();

                for (String tableName : tableList) {
                    stringBuilder.append("<a href=\"");
                    stringBuilder.append("developerDatabase/" + tableName);
                    stringBuilder.append("\" target=\"_blank\" >"     );
                    stringBuilder.append(tableName);
                    stringBuilder.append("</a>");
                    stringBuilder.append("</br>");
                }

                return stringBuilder.toString();
            } catch (Exception e) {

                e.printStackTrace();
                return "Developer Database Restful Service says: \"There was an error trying to list tables!\".";
            }

        } else {

            return "Developer Database Restful Service says: \"Developer Database Factory instance is not available!\".";
        }
    }

    @GET
    @Path("/{tableName}")
    @Produces(MediaType.TEXT_PLAIN)
    public String listTables(@PathParam("tableName") String tableName){

        CommunicationsNetworkNodeP2PDeveloperDatabaseFactoryTemp developerDatabaseFactory = getDeveloperDatabaseFactory();

        if (developerDatabaseFactory != null) {

            try {

                List<DatabaseTableRecord> records = developerDatabaseFactory.getTableContent(tableName);

                if (!records.isEmpty()) {

                    StringBuilder stringBuilder = new StringBuilder();

                    for (DatabaseTableRecord record : records) {
                        stringBuilder.append(record.toString());
                        stringBuilder.append("\n");
                    }

                    return stringBuilder.toString();
                } else
                    return "Developer Database Restful Service says: \"Table has no content!\".";

            } catch (Exception e) {

                e.printStackTrace();
                return "Developer Database Restful Service says: \"There was an error trying to list content!\".";
            }

        } else {

            return "Developer Database Restful Service says: \"Developer Database Factory instance is not available!\".";
        }
    }
}