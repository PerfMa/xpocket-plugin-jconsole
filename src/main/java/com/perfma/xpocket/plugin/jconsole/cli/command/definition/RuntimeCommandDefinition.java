package com.perfma.xpocket.plugin.jconsole.cli.command.definition;

import com.perfma.xpocket.plugin.jconsole.cli.JconsolePlugin;
import com.perfma.xpocket.plugin.jconsole.cli.TJContext;
import com.perfma.xpocket.plugin.jconsole.cli.handler.MxBeanHandler;
import com.perfma.xpocket.plugin.jconsole.cli.util.StringFormat;
import com.perfma.xlab.xpocket.spi.command.AbstractXPocketCommand;
import com.perfma.xlab.xpocket.spi.command.CommandInfo;
import com.perfma.xlab.xpocket.spi.process.XPocketProcess;

import java.lang.management.RuntimeMXBean;


/**
 * @author xinxian
 * @create 2020-09-17 16:27
 **/
@CommandInfo(name = "runtime", usage = "Runtime info")
public class RuntimeCommandDefinition extends AbstractXPocketCommand {

    @Override
    public void invoke(XPocketProcess xpocketProcess) throws Throwable {
        String lineSeparator = System.getProperty("line.separator");
        final RuntimeMXBean runtimeMxBean = MxBeanHandler.getInstance(TJContext.INSTANCE.getServer()).getRuntimeMxBean();
        StringBuilder sb = new StringBuilder();
        if (runtimeMxBean != null) {
            sb.append("Runtime Info").append(lineSeparator);
            sb.append(" @|cyan ").append(StringFormat.align("Name")).append(" |@ =      ").append("@|yellow ").append(runtimeMxBean.getName()).append(" |@ ").append(lineSeparator);
            sb.append(" @|cyan ").append(StringFormat.align("Uptime")).append(" |@ =      ").append("@|yellow ").append(runtimeMxBean.getUptime()).append(" |@ ").append(lineSeparator);
            sb.append(" @|cyan ").append(StringFormat.align("BootClassPath")).append(" |@ =      ").append("@|yellow ").append(runtimeMxBean.getBootClassPath()).append(" |@ ").append(lineSeparator);
            sb.append(" @|cyan ").append(StringFormat.align("ClassPath")).append(" |@ =      ").append("@|yellow ").append(runtimeMxBean.getClassPath()).append(" |@ ").append(lineSeparator);
            sb.append(" @|cyan ").append(StringFormat.align("LibraryPath")).append(" |@ =      ").append("@|yellow ").append(runtimeMxBean.getLibraryPath()).append(" |@ ").append(lineSeparator);
            sb.append(" @|cyan ").append(StringFormat.align("ManagementSpecVersion")).append(" |@ =      ").append("@|yellow ").append(runtimeMxBean.getManagementSpecVersion()).append(" |@ ").append(lineSeparator);
            sb.append(" @|cyan ").append(StringFormat.align("SpecName")).append(" |@ =      ").append("@|yellow ").append(runtimeMxBean.getSpecName()).append(" |@ ").append(lineSeparator);
            sb.append(" @|cyan ").append(StringFormat.align("SpecVendor")).append(" |@ =      ").append("@|yellow ").append(runtimeMxBean.getSpecVendor()).append(" |@ ").append(lineSeparator);
            sb.append(" @|cyan ").append(StringFormat.align("SpecVersion")).append(" |@ =      ").append("@|yellow ").append(runtimeMxBean.getSpecVersion()).append(" |@ ").append(lineSeparator);
            sb.append(" @|cyan ").append(StringFormat.align("StartTime")).append(" |@ =      ").append("@|yellow ").append(runtimeMxBean.getStartTime()).append(" |@ ").append(lineSeparator);
            sb.append(" @|cyan ").append(StringFormat.align("VmName")).append(" |@ =      ").append("@|yellow ").append(runtimeMxBean.getVmName()).append(" |@ ").append(lineSeparator);
            sb.append(" @|cyan ").append(StringFormat.align("VmVendor")).append(" |@ =      ").append("@|yellow ").append(runtimeMxBean.getVmVendor()).append(" |@ ").append(lineSeparator);
            sb.append(" @|cyan ").append(StringFormat.align("VmVersion")).append(" |@ =      ").append("@|yellow ").append(runtimeMxBean.getVmVersion()).append(" |@ ").append(lineSeparator);
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
