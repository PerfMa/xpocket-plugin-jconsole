package com.perfma.xpocket.plugin.jconsole.cli.command.definition;

import com.perfma.xpocket.plugin.jconsole.cli.JconsolePlugin;
import com.perfma.xpocket.plugin.jconsole.cli.TJContext;
import com.perfma.xlab.xpocket.spi.command.AbstractXPocketCommand;
import com.perfma.xlab.xpocket.spi.command.CommandInfo;
import com.perfma.xlab.xpocket.spi.process.XPocketProcess;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.ObjectName;

/**
 * @author xinxian
 * @create 2020-09-16 17:03
 *
 */
@CommandInfo(name = "mbeans", usage = "mbeans -list mbeans,mbeans objectName -list mbeans info,mbeans objectName attrName -get attribute value.")
public class MbeansCommandDefinition extends AbstractXPocketCommand {

    private static String lineSeparator = System.getProperty("line.separator");

    private Collection<String> names(TJContext ctx) throws IOException {
        ArrayList<String> l = new ArrayList<>();
        for (ObjectName on : ctx.getServer().queryNames(null, null)) {
            l.add(on.toString());
        }
        return l;
    }

    @Override
    public void invoke(XPocketProcess xpocketProcess) throws Throwable {

        String[] args = xpocketProcess.getArgs();
        StringBuilder sb = new StringBuilder();
        if (args == null || args.length == 0) {
            try {
                for (String bn : names(TJContext.INSTANCE)) {
                    sb.append("\t* ").append(bn).append(lineSeparator);
                }
            } catch (Exception e) {
                sb.append("complete - Error receiving bean names from JMX Server" + lineSeparator);
            }
        } else if (args.length == 1) {
           MBeanInfo info = TJContext.INSTANCE.getServer().getMBeanInfo(new ObjectName(args[0]));
           sb.append(" @|cyan ").append(args[0]).append(": ").append("|@ ").append(lineSeparator).append("  * ")
                   .append(info == null ? null : info.toString())
                   .append(lineSeparator);
           sb.append(" @|cyan ").append("Attributes : ").append("|@ ").append(lineSeparator);
           for(MBeanAttributeInfo attr : info.getAttributes()) {
               sb.append("  * ").append(attr.toString()).append(lineSeparator);
           }
        } else {
            sb.append(TJContext.INSTANCE.getServer().getAttribute(new ObjectName(args[0]), args[1])).append(lineSeparator);
        }
        xpocketProcess.output(sb.toString());
        xpocketProcess.end();
    }

    @Override
    public boolean isAvailableNow(String cmd) {
        return JconsolePlugin.attachStatus;
    }
}
