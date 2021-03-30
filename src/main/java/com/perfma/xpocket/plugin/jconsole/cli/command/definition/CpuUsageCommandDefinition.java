package com.perfma.xpocket.plugin.jconsole.cli.command.definition;

import com.perfma.xpocket.plugin.jconsole.cli.JconsolePlugin;
import com.perfma.xpocket.plugin.jconsole.cli.TJContext;
import com.perfma.xlab.xpocket.spi.command.AbstractXPocketCommand;
import com.perfma.xlab.xpocket.spi.command.CommandInfo;
import com.perfma.xlab.xpocket.spi.process.XPocketProcess;

import javax.management.MBeanServerConnection;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;


/**
 * @author xinxian
 **/
@CommandInfo(name = "cpu_usage", usage = "cpu usage info")
public class CpuUsageCommandDefinition extends AbstractXPocketCommand {

    private long prevUpTime, prevProcessCpuTime;

    @Override
    public void invoke(XPocketProcess xpocketProcess) throws Throwable {
        String lineSeparator = System.getProperty("line.separator");
        StringBuilder sb = new StringBuilder();
        sb.append("@|white cpuUsage: |@");
        final Float cpuInfo = getCPUInfo(TJContext.INSTANCE.getServer());
        sb.append("  @|blue ").append(cpuInfo.toString()).append(" |@").append(lineSeparator);
        xpocketProcess.output(sb.toString());
        xpocketProcess.end();
    }


    public Float getCPUInfo(MBeanServerConnection serverConnection) {

        try {
            RuntimeMXBean runtimeMxBean = ManagementFactory
                    .newPlatformMXBeanProxy(serverConnection, ManagementFactory.RUNTIME_MXBEAN_NAME, RuntimeMXBean.class);
            com.sun.management.OperatingSystemMXBean sunOperatingSystemMxBean = ManagementFactory
                    .newPlatformMXBeanProxy(serverConnection, ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME, com.sun.management.OperatingSystemMXBean.class);
            final OperatingSystemMXBean operatingSystemMxBean = ManagementFactory
                    .newPlatformMXBeanProxy(serverConnection, ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME, OperatingSystemMXBean.class);
            if (prevUpTime > 0L && runtimeMxBean.getUptime() > prevUpTime) {
                // elapsedCpu is in ns and elapsedTime is in ms.
                long elapsedCpu = sunOperatingSystemMxBean.getProcessCpuTime() - prevProcessCpuTime;
                long elapsedTime = runtimeMxBean.getUptime() - prevUpTime;
                // cpuUsage could go higher than 100% because elapsedTime
                // and elapsedCpu are not fetched simultaneously. Limit to
                // 99% to avoid Plotter showing a scale from 0% to 200%.
                float cpuUsage =
                        Math.min(99F,
                                elapsedCpu / (elapsedTime * 10000F * operatingSystemMxBean.getAvailableProcessors()));

               return Math.max(0F, cpuUsage);
            }
            this.prevUpTime = runtimeMxBean.getUptime();
            this.prevProcessCpuTime = sunOperatingSystemMxBean.getProcessCpuTime();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0.0f;
    }

    @Override
    public boolean isAvailableNow(String cmd) {
        return JconsolePlugin.attachStatus;
    }
}
