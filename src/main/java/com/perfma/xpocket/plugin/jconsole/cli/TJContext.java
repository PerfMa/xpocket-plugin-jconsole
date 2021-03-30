package com.perfma.xpocket.plugin.jconsole.cli;

import javax.management.*;
import java.io.IOException;
import java.util.*;

/**
 * Connection context.
 * What user is connected to (server, bean)
 *
 * @author xinxian
 */
public class TJContext extends Observable {
    private MBeanServerConnection serverConnection;
    private ObjectName objectName;
    private Object serverURL;
    private int exitCode = 0;

    public static final TJContext INSTANCE = new TJContext();

    public TJContext() {
    }

    public MBeanServerConnection getServer() {
        return serverConnection;
    }

    public void setServer(MBeanServerConnection serverConnection, String url) {
        this.serverConnection = serverConnection;
        this.serverURL = url;
        this.objectName = null;
    }

    public ObjectName getObjectName() {
        return objectName;
    }

    public void setObjectName(ObjectName objectName) {
        this.objectName = objectName;
    }

    public boolean isConnected() {
        return serverConnection != null;
    }

    public List<MBeanAttributeInfo> getAttributes() throws InstanceNotFoundException, IntrospectionException, ReflectionException, IOException {
        if (serverConnection == null || objectName == null) {
            return Collections.emptyList();
        }
        MBeanInfo beanInfo = serverConnection.getMBeanInfo(objectName);
        return Arrays.asList(beanInfo.getAttributes());
    }

    public boolean isBeanSelected() {
        return objectName != null;
    }


    public Object getServerURL() {
        return serverURL;
    }

    public void fail(int code) {
        if (exitCode == 0) {
            exitCode = code;
        }
    }

    public int getExitCode() {
        return exitCode;
    }
}


