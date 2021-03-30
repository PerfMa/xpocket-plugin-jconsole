package com.perfma.xpocket.plugin.jconsole.cli.command.definition;

import com.perfma.xpocket.plugin.jconsole.cli.JconsolePlugin;
import com.perfma.xpocket.plugin.jconsole.cli.TJContext;
import com.perfma.xpocket.plugin.jconsole.cli.handler.MxBeanHandler;
import com.perfma.xpocket.plugin.jconsole.cli.util.StringFormat;
import com.perfma.xlab.xpocket.spi.command.AbstractXPocketCommand;
import com.perfma.xlab.xpocket.spi.command.CommandInfo;
import com.perfma.xlab.xpocket.spi.process.XPocketProcess;

import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

/**
 * @author xinxian
 * @create 2020-09-17 11:33
 **/
@CommandInfo(name = "memory", usage = "memory info")
public class MemoryCommandDefinition extends AbstractXPocketCommand {

    @Override
    public void invoke(XPocketProcess xpocketProcess) throws Throwable {
        String lineSeparator = System.getProperty("line.separator");
        final MemoryMXBean memoryMxbean = MxBeanHandler.getInstance(TJContext.INSTANCE.getServer()).getMemoryMxbean();
        StringBuilder sb = new StringBuilder();
        if (memoryMxbean != null) {
            final MemoryUsage heapMemoryUsage = memoryMxbean.getHeapMemoryUsage();
            final MemoryUsage nonHeapMemoryUsage = memoryMxbean.getNonHeapMemoryUsage();
            sb.append("HeapMemoryUsage").append(lineSeparator);
            sb.append(" @|cyan ").append(StringFormat.align("init")).append(" |@ =      ").append("@|yellow ").append(heapMemoryUsage.getInit()).append(" |@ ").append(lineSeparator);
            sb.append(" @|cyan ").append(StringFormat.align("used")).append(" |@ =      ").append("@|yellow ").append(heapMemoryUsage.getUsed()).append(" |@ ").append(lineSeparator);
            sb.append(" @|cyan ").append(StringFormat.align("committed")).append(" |@ =      ").append("@|yellow ").append(heapMemoryUsage.getCommitted()).append(" |@ ").append(lineSeparator);
            sb.append(" @|cyan ").append(StringFormat.align("max")).append(" |@ =      ").append("@|yellow ").append(heapMemoryUsage.getMax()).append(" |@ ").append(lineSeparator);

            sb.append("NonHeapMemoryUsage").append(lineSeparator);
            sb.append(" @|cyan ").append(StringFormat.align("init")).append(" |@ =      ").append("@|yellow ").append(nonHeapMemoryUsage.getInit()).append(" |@ ").append(lineSeparator);
            sb.append(" @|cyan ").append(StringFormat.align("used")).append(" |@ =      ").append("@|yellow ").append(nonHeapMemoryUsage.getUsed()).append(" |@ ").append(lineSeparator);
            sb.append(" @|cyan ").append(StringFormat.align("committed")).append(" |@ =      ").append("@|yellow ").append(nonHeapMemoryUsage.getCommitted()).append(" |@ ").append(lineSeparator);
            sb.append(" @|cyan ").append(StringFormat.align("max")).append(" |@ =      ").append("@|yellow ").append(nonHeapMemoryUsage.getMax()).append(" |@ ").append(lineSeparator);

        } else {
            sb.append("Memory Is Empty").append(lineSeparator);
        }
        xpocketProcess.output(sb.toString());
        xpocketProcess.end();
    }

    @Override
    public boolean isAvailableNow(String cmd) {
        return JconsolePlugin.attachStatus;
    }
}
