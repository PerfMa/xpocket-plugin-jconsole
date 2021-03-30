package com.perfma.xpocket.plugin.jconsole.cli.command.definition;

import com.perfma.xpocket.plugin.jconsole.cli.JconsolePlugin;
import com.perfma.xpocket.plugin.jconsole.cli.TJContext;
import com.perfma.xpocket.plugin.jconsole.cli.handler.MxBeanHandler;
import com.perfma.xpocket.plugin.jconsole.cli.util.StringFormat;
import com.perfma.xlab.xpocket.spi.command.AbstractXPocketCommand;
import com.perfma.xlab.xpocket.spi.command.CommandInfo;
import com.perfma.xlab.xpocket.spi.process.XPocketProcess;

import java.lang.management.ThreadMXBean;

/**
 * @author xinxian
 **/
@CommandInfo(name = "thread", usage = "Thread")
public class ThreadCommandDefinition extends AbstractXPocketCommand {

    @Override
    public void invoke(XPocketProcess xpocketProcess) throws Throwable {
        String lineSeparator = System.getProperty("line.separator");
        final ThreadMXBean threadMxBean = MxBeanHandler.getInstance(TJContext.INSTANCE.getServer()).getThreadMXBean();
        StringBuilder sb = new StringBuilder();
        if (threadMxBean != null) {
            sb.append("Thread Info").append(lineSeparator);
            sb.append(" @|cyan ").append(StringFormat.align("CurrentThreadCpuTime")).append(" |@ =      ").append("@|yellow ").append(threadMxBean.getCurrentThreadCpuTime()).append(" |@ ").append(lineSeparator);
            sb.append(" @|cyan ").append(StringFormat.align("CurrentThreadUserTime")).append(" |@ =      ").append("@|yellow ").append(threadMxBean.getCurrentThreadUserTime()).append(" |@ ").append(lineSeparator);
            sb.append(" @|cyan ").append(StringFormat.align("DaemonThreadCount")).append(" |@ =      ").append("@|yellow ").append(threadMxBean.getDaemonThreadCount()).append(" |@ ").append(lineSeparator);
            sb.append(" @|cyan ").append(StringFormat.align("PeakThreadCount")).append(" |@ =      ").append("@|yellow ").append(threadMxBean.getPeakThreadCount()).append(" |@ ").append(lineSeparator);
            sb.append(" @|cyan ").append(StringFormat.align("ThreadCount")).append(" |@ =      ").append("@|yellow ").append(threadMxBean.getThreadCount()).append(" |@ ").append(lineSeparator);
            sb.append(" @|cyan ").append(StringFormat.align("TotalStartedThreadCount")).append(" |@ =      ").append("@|yellow ").append(threadMxBean.getTotalStartedThreadCount()).append(" |@ ").append(lineSeparator);
        } else {
            sb.append("Ps Thread Info Is Empty").append(lineSeparator);
        }
        xpocketProcess.output(sb.toString());
        xpocketProcess.end();
    }

    @Override
    public boolean isAvailableNow(String cmd) {
        return JconsolePlugin.attachStatus;
    }
}
