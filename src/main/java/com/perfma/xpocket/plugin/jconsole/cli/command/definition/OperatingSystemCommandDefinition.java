package com.perfma.xpocket.plugin.jconsole.cli.command.definition;

import com.perfma.xpocket.plugin.jconsole.cli.JconsolePlugin;
import com.perfma.xpocket.plugin.jconsole.cli.TJContext;
import com.perfma.xpocket.plugin.jconsole.cli.handler.MxBeanHandler;
import com.perfma.xpocket.plugin.jconsole.cli.util.StringFormat;
import com.perfma.xlab.xpocket.spi.command.AbstractXPocketCommand;
import com.perfma.xlab.xpocket.spi.command.CommandInfo;
import com.perfma.xlab.xpocket.spi.process.XPocketProcess;

import java.lang.management.OperatingSystemMXBean;

/**
 * @author xinxian
 * @create 2020-09-18 11:17
 **/
@CommandInfo(name = "os", usage = "os info")
public class OperatingSystemCommandDefinition extends AbstractXPocketCommand {

    @Override
    public void invoke(XPocketProcess xpocketProcess) throws Throwable {
        String lineSeparator = System.getProperty("line.separator");
        final OperatingSystemMXBean operatingSystemMXBean = MxBeanHandler.getInstance(TJContext.INSTANCE.getServer()).getOperatingSystemMXBean();
        StringBuilder sb = new StringBuilder();
        if (operatingSystemMXBean != null) {
            sb.append("OS Info").append(lineSeparator);
            sb.append(" @|cyan ").append(StringFormat.align("Name")).append(" |@ =      ").append("@|yellow ").append(operatingSystemMXBean.getName()).append(" |@ ").append(lineSeparator);
            sb.append(" @|cyan ").append(StringFormat.align("AvailableProcessors")).append(" |@ =      ").append("@|yellow ").append(operatingSystemMXBean.getAvailableProcessors()).append(" |@ ").append(lineSeparator);
            sb.append(" @|cyan ").append(StringFormat.align("Arch")).append(" |@ =      ").append("@|yellow ").append(operatingSystemMXBean.getArch()).append(" |@ ").append(lineSeparator);
            sb.append(" @|cyan ").append(StringFormat.align("Version")).append(" |@ =      ").append("@|yellow ").append(operatingSystemMXBean.getVersion()).append(" |@ ").append(lineSeparator);
        } else {
            sb.append("Runtime Info Is Empty").append(lineSeparator);
        }
        xpocketProcess.output(sb.toString());
        xpocketProcess.end();
    }

    @Override
    public boolean isAvailableNow(String cmd) {
        return JconsolePlugin.attachStatus;
    }
}
