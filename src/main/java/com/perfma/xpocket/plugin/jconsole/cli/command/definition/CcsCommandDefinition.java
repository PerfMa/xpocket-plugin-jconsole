package com.perfma.xpocket.plugin.jconsole.cli.command.definition;

import com.perfma.xpocket.plugin.jconsole.cli.JconsolePlugin;
import com.perfma.xpocket.plugin.jconsole.cli.TJContext;
import com.perfma.xpocket.plugin.jconsole.cli.handler.MemoryPool;
import com.perfma.xpocket.plugin.jconsole.cli.handler.MxBeanHandler;
import com.perfma.xpocket.plugin.jconsole.cli.handler.MxBeanNames;
import com.perfma.xpocket.plugin.jconsole.cli.util.StringFormat;
import com.perfma.xlab.xpocket.spi.command.AbstractXPocketCommand;
import com.perfma.xlab.xpocket.spi.command.CommandInfo;
import com.perfma.xlab.xpocket.spi.process.XPocketProcess;


/**
 * @author xinxian
 * @create 2020-09-23 11:02
 **/
@CommandInfo(name = "ccs", usage = "Compressed Class Space")
public class CcsCommandDefinition extends AbstractXPocketCommand {

    @Override
    public void invoke(XPocketProcess xpocketProcess) throws Throwable {
        String lineSeparator = System.getProperty("line.separator");
        MxBeanHandler.getInstance(TJContext.INSTANCE.getServer()).buildMemoryPools(TJContext.INSTANCE.getServerURL().toString());
        final MemoryPool ccs = MxBeanHandler.memoryPoolMap.get(MxBeanNames.CCS);
        StringBuilder sb = new StringBuilder();
        if (ccs != null) {
            sb.append("Compressed Class Space").append(lineSeparator);
            sb.append(" @|cyan ").append(StringFormat.align("type")).append(" |@ =      ").append("@|yellow ").append(ccs.getType()).append(" |@ ").append(lineSeparator);
            sb.append(" @|cyan ").append(StringFormat.align("used")).append(" |@ =      ").append("@|yellow ").append(ccs.getUsed()).append(" |@ ").append(lineSeparator);
            sb.append(" @|cyan ").append(StringFormat.align("commited")).append(" |@ =      ").append("@|yellow ").append(ccs.getCommited()).append(" |@ ").append(lineSeparator);
            sb.append(" @|cyan ").append(StringFormat.align("max")).append(" |@ =      ").append("@|yellow ").append(ccs.getMax()).append(" |@ ").append(lineSeparator);
        } else {
            sb.append(" Compressed Class Space Is Empty").append(lineSeparator);
        }
        xpocketProcess.output(sb.toString());
        xpocketProcess.end();
    }

    @Override
    public boolean isAvailableNow(String cmd) {
        return JconsolePlugin.attachStatus;
    }
}
