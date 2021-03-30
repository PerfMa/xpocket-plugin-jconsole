package com.perfma.xpocket.plugin.jconsole.cli.command.definition;

import com.perfma.xpocket.plugin.jconsole.cli.JconsolePlugin;
import com.perfma.xpocket.plugin.jconsole.cli.TJContext;
import com.perfma.xpocket.plugin.jconsole.cli.localjvm.ProcessListManager;
import com.perfma.xlab.xpocket.spi.command.AbstractXPocketCommand;
import com.perfma.xlab.xpocket.spi.command.CommandInfo;
import com.perfma.xlab.xpocket.spi.process.XPocketProcess;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

/**
 * @author xinxian
 * @create 2020-10-14 19:07
 **/
@CommandInfo(name = "attach", usage = "attach <local jvm pid>")
public class AttachCommandDefinition extends AbstractXPocketCommand {

    @Override
    public void invoke(XPocketProcess xpocketProcess) throws Throwable {
        final String pid = xpocketProcess.getArgs()[0];
        JMXServiceURL serviceUrl = new ProcessListManager().getLocalServiceURL(pid);
        JMXConnector connector = JMXConnectorFactory.connect(serviceUrl);
        MBeanServerConnection serverConnection = connector.getMBeanServerConnection();
        if (serverConnection != null) {
            TJContext.INSTANCE.setServer(serverConnection, pid);
            String result = "attach to " + pid + " success " + System.getProperty("line.separator");
            xpocketProcess.output(result);
            JconsolePlugin.attachOn(Integer.parseInt(pid));
        } else {
            xpocketProcess.output("attach to pid failed");
        }
        xpocketProcess.end();
    }

    @Override
    public boolean isAvailableNow(String cmd) {
        return !JconsolePlugin.attachStatus;
    }
    
    
}
