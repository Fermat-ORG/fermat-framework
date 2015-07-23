package com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_factory.developer.bitdubai.version_1.structure.WalletFactoryMiddlewareDatabaseConstants</code>
 * keeps constants the column names of the database.<p/>
 * <p/>
 *
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 23/07/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class WalletFactoryMiddlewareDatabaseConstants {

    /**
     * Project database table definition.
     */
    static final String PROJECT_TABLE_NAME = "project";

    static final String PROJECT_ID_COLUMN_NAME = "id";
    static final String PROJECT_DEVELOPER_PUBLIC_KEY_COLUMN_NAME = "developer_public_key";
    static final String PROJECT_NAME_COLUMN_NAME = "name";

    static final String PROJECT_FIRST_KEY_COLUMN = "id";

    /**
     * Project Proposal database table definition.
     */
    static final String PROJECT_PROPOSAL_TABLE_NAME = "project_proposal";

    static final String PROJECT_PROPOSAL_ID_COLUMN_NAME = "id";
    static final String PROJECT_PROPOSAL_ALIAS_COLUMN_NAME = "alias";
    static final String PROJECT_PROPOSAL_FACTORY_PROJECT_STATE_COLUMN_NAME = "factory_project_state";
    static final String PROJECT_PROPOSAL_PROJECT_ID_COLUMN_NAME = "project_id";

    static final String PROJECT_PROPOSAL_FIRST_KEY_COLUMN = "id";

}