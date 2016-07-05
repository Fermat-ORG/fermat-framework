package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.rest.services;

import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_p2p_api.layer.all_definition.communication.commons.util.GsonProvider;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.context.NodeContext;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.context.NodeContextItem;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.CommunicationsNetworkNodeP2PDeveloperDatabaseFactoryTemp;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.daos.DaoFactory;
import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.rest.RestFulServices;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.apache.commons.lang.ClassUtils;
import org.apache.log4j.Logger;
import org.jboss.resteasy.annotations.GZIP;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.rest.Databases</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 26/06/2016.
 *
 * @author lnacosta
 * @version 1.0
 * @since Java JDK 1.7
 */
@Path("/admin/databases")
public class Databases implements RestFulServices {

    /**
     * Represent the logger instance
     */
    private Logger LOG = Logger.getLogger(ClassUtils.getShortClassName(Databases.class));

    /**
     * Represent the gson
     */
    private Gson gson;

    /**
     * Represent the developerDatabaseFactory
     */
    private CommunicationsNetworkNodeP2PDeveloperDatabaseFactoryTemp developerDatabaseFactory;

    /**
     * Represent the daoFactory
     */
    private DaoFactory daoFactory;

    /**
     * Constructor
     */
    public Databases(){
        super();
        this.gson = GsonProvider.getGson();
        this.daoFactory  = (DaoFactory) NodeContext.get(NodeContextItem.DAO_FACTORY);
        this.developerDatabaseFactory = (CommunicationsNetworkNodeP2PDeveloperDatabaseFactoryTemp) NodeContext.get(NodeContextItem.DEVELOPER_DATABASE_FACTORY);
    }

    @GET
    public String isActive() {
        return "The Databases WebService is running ...";
    }

    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    @GZIP
    public Response listTables(){

        JsonObject result = new JsonObject();

        try {

            List<String> tableList = developerDatabaseFactory.getTableList();
            result.addProperty("list", gson.toJson(tableList));
            result.addProperty("success", Boolean.TRUE);

        } catch (Exception e) {

            LOG.error(e);
            result = new JsonObject();
            result.addProperty("success", Boolean.FALSE);
            result.addProperty("description", e.getMessage());
        }

        return Response.status(200).entity(gson.toJson(result)).build();
    }

    @GET
    @Path("/data")
    @Produces(MediaType.APPLICATION_JSON)
    @GZIP
    public Response getTableData(@QueryParam("tableName") String tableName, @QueryParam("offSet") Integer offSet, @QueryParam("max") Integer max){

        LOG.info("Executing getTableData");
        LOG.info("tableName = "+tableName);
        LOG.info("offset = "+offSet);
        LOG.info("max = "+max);

        JsonObject result = new JsonObject();

        try {

            List<DatabaseTableRecord> records = developerDatabaseFactory.getTableContent(tableName, offSet, max);
            List<String> columnsName = new ArrayList<>();
            List<List<String>> rows = new ArrayList<>();

            LOG.info("records = "+records.size());

            if (!records.isEmpty()) {

                for (DatabaseRecord databaseRecord : records.get(0).getValues()) {
                    columnsName.add(databaseRecord.getName());
                }

                for (DatabaseTableRecord record : records) {

                    List<String> row = new ArrayList<>();

                    for (DatabaseRecord databaseRecord : record.getValues()) {
                        row.add(databaseRecord.getValue());
                    }

                    rows.add(row);
                }

                result.addProperty("columns", gson.toJson(columnsName));
                result.addProperty("rows",    gson.toJson(rows));
                result.addProperty("total",   developerDatabaseFactory.count(tableName));
                result.addProperty("success", Boolean.TRUE);

            } else {

                result = new JsonObject();
                result.addProperty("success", Boolean.FALSE);
                result.addProperty("description", "Database is empty");
            }

        } catch (Exception e) {

            LOG.error(e);
            result = new JsonObject();
            result.addProperty("success", Boolean.FALSE);
            result.addProperty("description", e.getMessage());
        }

        return Response.status(200).entity(gson.toJson(result)).build();

    }
}