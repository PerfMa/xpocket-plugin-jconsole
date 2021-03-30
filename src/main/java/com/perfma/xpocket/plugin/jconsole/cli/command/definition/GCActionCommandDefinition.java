package com.perfma.xpocket.plugin.jconsole.cli.command.definition;

import com.perfma.xlab.xpocket.spi.command.AbstractXPocketCommand;
import com.perfma.xlab.xpocket.spi.command.CommandInfo;
import com.perfma.xlab.xpocket.spi.process.XPocketProcess;
import com.perfma.xpocket.plugin.jconsole.cli.JconsolePlugin;
import com.perfma.xpocket.plugin.jconsole.cli.TJContext;
import com.perfma.xpocket.plugin.jconsole.cli.handler.MxBeanHandler;
import java.lang.management.MemoryMXBean;

/**
 *
 * @author gongyu <yin.tong@perfma.com>
 */
@CommandInfo(name = "gc", usage = "execute a system gc")
public class GCActionCommandDefinition extends AbstractXPocketCommand {

    @Override
    public void invoke(XPocketProcess xpocketProcess) throws Throwable {
        final MemoryMXBean memoryMxbean = MxBeanHandler.getInstance(TJContext.INSTANCE.getServer()).getMemoryMxbean();
        memoryMxbean.gc();
        xpocketProcess.end();
    }

    @Override
    public boolean isAvailableNow(String cmd) {
        return JconsolePlugin.attachStatus;
    }
    
}
