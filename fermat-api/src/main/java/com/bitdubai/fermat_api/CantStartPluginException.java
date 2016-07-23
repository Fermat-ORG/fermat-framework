package com.bitdubai.fermat_api;


import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginVersionReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;


public class CantStartPluginException extends CantStartException {


    private Plugins plugin;


    private static final long serialVersionUID = -4797409301346577158L;

    public static final String DEFAULT_MESSAGE = "CAN'T START PLUGIN";

    public CantStartPluginException(final String message, final Exception cause, final String context, final String possibleReason) {
        super(message, cause, context, possibleReason);
    }

    public CantStartPluginException(final String message, final Exception cause) {
        this(message, cause, "", "");
    }

    public CantStartPluginException(final Exception cause, final String context, final String possibleReason) {
        this(DEFAULT_MESSAGE, cause, context, possibleReason);
    }

    public CantStartPluginException(final String message) {
        this(message, null, null, null);
    }

    public CantStartPluginException(final String context, final String possibleReason) {
        this(DEFAULT_MESSAGE, null, context, possibleReason);
    }

    public CantStartPluginException(final Exception exception, final Plugins plugin) {
        this(new StringBuilder().append(plugin.toString()).append(" ").append(exception.getMessage()).toString());
        setStackTrace(exception.getStackTrace());
    }

    public CantStartPluginException(final Exception exception, final PluginVersionReference plugin) {
        this(new StringBuilder().append(plugin.toString()).append(" ").append(exception.getMessage()).toString());
        setStackTrace(exception.getStackTrace());
    }

    public CantStartPluginException(final Exception exception) {
        this(exception.getMessage());
        setStackTrace(exception.getStackTrace());
    }

    public CantStartPluginException(final Plugins plugin) {
        this(plugin.toString());
    }

    public CantStartPluginException() {
        this(DEFAULT_MESSAGE);
    }
}