package com.perfma.xpocket.plugin.jconsole.cli.command.definition;

import com.perfma.xpocket.plugin.jconsole.cli.TJContext;
import com.perfma.xpocket.plugin.jconsole.cli.console.ParseInputCommandCreationException;
import com.perfma.xlab.xpocket.spi.command.AbstractXPocketCommand;
import com.perfma.xlab.xpocket.spi.command.CommandInfo;
import com.perfma.xlab.xpocket.spi.process.XPocketProcess;
import com.perfma.xpocket.plugin.jconsole.cli.JconsolePlugin;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * Command to connect to server
 *
 * @author xinxian
 */
//@CommandInfo(name = "connect", usage = "connect <host>:<port> or <user>:<password>@<host>:<port>")
public class ConnectCommandDefinition extends AbstractXPocketCommand {
    private final Logger logger = Logger.getLogger(getClass().getName());

    private static  final String lineSeparator = System.getProperty("line.separator");


    private final List<String> remoteConnectionHistory;
    private final Preferences prefs;


    public ConnectCommandDefinition() throws BackingStoreException {
        remoteConnectionHistory = new ArrayList<String>();
        prefs = Preferences.userNodeForPackage(getClass());
        for (String key : prefs.keys()) {
            String rName = prefs.get(key, null);
            if (rName != null) {
                addRemote(rName, false);
            }
        }
    }

    private void addRemote(String rName, boolean b) {
        if (remoteConnectionHistory.contains(rName)) {
            return;
        }
        remoteConnectionHistory.add(rName);
        if (b) {
            try {
                prefs.put("RNAME_" + (prefs.keys().length + 1), rName);
            } catch (BackingStoreException e) {
                logger.throwing(getClass().getName(), "addRemote - Error saving data to user preferences", e);
            }
        }
    }

    @Override
    public void invoke(XPocketProcess xpocketProcess) throws Throwable {
        final String url = xpocketProcess.getArgs()[0];
        String result = null;
        String messageURL;
        Map<String, Object> env = null;
        if (url.length() == 0) {  // current state
            if (TJContext.INSTANCE.getServer() == null) {
                result = "Not connected to any JMX server." + lineSeparator;
                TJContext.INSTANCE.fail(10);
            } else {
                result = "Connected to " + TJContext.INSTANCE.getServer().getDefaultDomain() + lineSeparator;
            }
            xpocketProcess.output(result);
            return;
        }
        boolean saveURL = false;
        MBeanServerConnection serverConnection;
        JMXServiceURL serviceURL;
        String hostPort;
        int lm = url.lastIndexOf('@');
        if (lm > 0) { // user and password
            hostPort = url.substring(lm + 1);
            String passwordAndUser = url.substring(0, lm);
            env = new HashMap<String, Object>();
            int semicolon = passwordAndUser.indexOf(':');
            String user, password;
            if (semicolon <= 0) {
                password = "";
                user = passwordAndUser;
            } else {
                user = passwordAndUser.substring(0, semicolon);
                password = passwordAndUser.substring(semicolon + 1);
            }
            env.put(JMXConnector.CREDENTIALS, new String[]{user, password});
        } else {
            hostPort = url;
        }
        String host;
        int port;
        String[] urlParts = hostPort.split(":", 2);
        host = urlParts[0];
        if (urlParts.length == 2) {
            try {
                port = Integer.parseInt(urlParts[1].trim());
            } catch (NumberFormatException ex) {
                throw new ParseInputCommandCreationException("Invalid port number '" + urlParts[1].trim() + "'", ex);
            }
        } else {
            throw new IllegalArgumentException("Invalid connection URL (expected host:port) but found '" + hostPort + "'");
        }
        serviceURL = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://" + host + ":" + port + "/jmxrmi");
        messageURL = host + ":" + port;
        saveURL = lm <= 0;
        JMXConnector connector = env == null ? JMXConnectorFactory.connect(serviceURL) : JMXConnectorFactory.connect(serviceURL, env);
        serverConnection = connector.getMBeanServerConnection();
        if (serverConnection != null) {
            // xpocket 状态改变
//                    XpocketStatusContext.getInstance().setPid(Integer.valueOf(messageURL));
            TJContext.INSTANCE.setServer(serverConnection, url);
            if (saveURL) {
                addRemote(url, true);
            }

            result = "attach to " + messageURL + " success " + lineSeparator;
        }
        xpocketProcess.output(result);
        xpocketProcess.end();
    }
    
    @Override
    public boolean isAvailableNow(String cmd) {
        return !JconsolePlugin.attachStatus;
    }

}

