package com.perfma.xpocket.plugin.jconsole.cli.command.definition;

import com.perfma.xpocket.plugin.jconsole.cli.JconsolePlugin;
import com.perfma.xpocket.plugin.jconsole.cli.TJContext;
import com.perfma.xpocket.plugin.jconsole.cli.handler.MxBeanHandler;
import com.perfma.xpocket.plugin.jconsole.cli.util.StringFormat;
import com.perfma.xlab.xpocket.spi.command.AbstractXPocketCommand;
import com.perfma.xlab.xpocket.spi.command.CommandInfo;
import com.perfma.xlab.xpocket.spi.process.XPocketProcess;

import java.lang.management.ClassLoadingMXBean;

/**
 * @author xinxian
 * @create 2020-09-17 16:12
 **/
@CommandInfo(name = "class", usage = "ClassLoading info")
public class ClassLoadCommandDefinition extends AbstractXPocketCommand {

    @Override
    public void invoke(XPocketProcess xpocketProcess) throws Throwable {
        String lineSeparator = System.getProperty("line.separator");
        final ClassLoadingMXBean classLoadingMxbean = MxBeanHandler.getInstance(TJContext.INSTANCE.getServer()).getClassLoadingMxbean();
        StringBuilder sb = new StringBuilder();
        if (classLoadingMxbean != null) {
            sb.append("ClassLoading").append(lineSeparator);
            sb.append(" @|cyan ").append(StringFormat.align("TotalLoadedClassCount")).append(" |@ =      ").append("@|yellow ").append(classLoadingMxbean.getTotalLoadedClassCount()).append(" |@ ").append(lineSeparator);
            sb.append(" @|cyan ").append(StringFormat.align("LoadedClassCount")).append(" |@ =      ").append("@|yellow ").append(classLoadingMxbean.getLoadedClassCount()).append(" |@ ").append(lineSeparator);
            sb.append(" @|cyan ").append(StringFormat.align("UnloadedClassCount")).append(" |@ =      ").append("@|yellow ").append(classLoadingMxbean.getUnloadedClassCount()).append(" |@ ").append(lineSeparator);
        } else {
            sb.append("Ps Old Gen Is Empty").append(lineSeparator);
        }
        xpocketProcess.output(sb.toString());
        xpocketProcess.end();
    }
    @Override
    public boolean isAvailableNow(String cmd) {
        return JconsolePlugin.attachStatus;
    }
}
