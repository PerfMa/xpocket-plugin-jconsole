package com.perfma.xpocket.plugin.jconsole.cli.handler;

import javax.management.MBeanServerConnection;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import java.io.IOException;
import java.lang.management.*;
import java.util.*;

import static java.lang.management.ManagementFactory.MEMORY_POOL_MXBEAN_DOMAIN_TYPE;
import static java.lang.management.ManagementFactory.newPlatformMXBeanProxy;

/**
 * @author xinxian
 **/
public class MxBeanHandler {

    private MBeanServerConnection serverConnection;

    public static Map<String,MemoryPool> memoryPoolMap = new HashMap<String, MemoryPool>();

    private static volatile MxBeanHandler instance;

    public static MxBeanHandler getInstance(MBeanServerConnection serverConnection) {
        if (instance == null) {
            synchronized (MxBeanHandler.class) {
                if (instance == null) {
                    instance = new MxBeanHandler(serverConnection);
                }
            }
        }
        return instance;
    }

    public MxBeanHandler(MBeanServerConnection serverConnection) {
        this.serverConnection = serverConnection;
    }

    public RuntimeMXBean getRuntimeMxBean() throws IOException {
        return ManagementFactory.newPlatformMXBeanProxy(serverConnection, ManagementFactory.RUNTIME_MXBEAN_NAME, RuntimeMXBean.class);
    }

    public OperatingSystemMXBean getOperatingSystemMXBean() throws IOException {
        return ManagementFactory.newPlatformMXBeanProxy(serverConnection, ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME, OperatingSystemMXBean.class);
    }

    public ThreadMXBean getThreadMXBean() throws IOException {
        return ManagementFactory.newPlatformMXBeanProxy(serverConnection, ManagementFactory.THREAD_MXBEAN_NAME, ThreadMXBean.class);
    }

    public ClassLoadingMXBean getClassLoadingMxbean() throws IOException {
        return ManagementFactory.newPlatformMXBeanProxy(serverConnection, ManagementFactory.CLASS_LOADING_MXBEAN_NAME, ClassLoadingMXBean.class);
    }

    public MemoryMXBean getMemoryMxbean() throws IOException {
        return ManagementFactory.newPlatformMXBeanProxy(serverConnection, ManagementFactory.MEMORY_MXBEAN_NAME, MemoryMXBean.class);
    }

    public Collection<MemoryPoolMXBean> getMemoryPoolMXBeans(String pid) throws IOException {
        return getPlatformMxBeans(MemoryPoolMXBean.class, MEMORY_POOL_MXBEAN_DOMAIN_TYPE, pid);
    }

    private <T> Collection<T> getPlatformMxBeans(Class<T> mxBeansClass, String mxBeansClassName, String pid) throws IOException {
        Set<T> collectorMXBeans = null;
        ObjectName objectName = null;
        try {
            objectName = new ObjectName(mxBeansClassName + ",*");
        } catch (MalformedObjectNameException e) {
            // should not reach here
            throw new RuntimeException(e);
        }

        if (objectName != null) {
            Set<ObjectName> mbeans = serverConnection.queryNames(objectName, null);
            if (mbeans != null && !mbeans.isEmpty()) {
                collectorMXBeans = new HashSet<T>();
                Iterator<ObjectName> iterator = mbeans.iterator();
                while (iterator.hasNext()) {
                    ObjectName on = iterator.next();
                    String ObName = mxBeansClassName +
                            ",name=" + on.getKeyProperty("name");
                    T mBean = newPlatformMXBeanProxy(serverConnection, ObName,
                            mxBeansClass);
                    collectorMXBeans.add(mBean);
                }
            }
        }

        return collectorMXBeans;
    }

    public void buildMemoryPools(String pid) {
        Collection<MemoryPoolMXBean> memoryPoolMxBeans = null;
        try {
            memoryPoolMxBeans = getMemoryPoolMXBeans(pid);
            for (MemoryPoolMXBean memoryPoolMxBean : memoryPoolMxBeans) {
                MemoryPool temp = MemoryPool.builder()
                        .name(memoryPoolMxBean.getName())
                        .type(memoryPoolMxBean.getType().name())
                        .used(memoryPoolMxBean.getUsage().getUsed())
                        .commited(memoryPoolMxBean.getUsage().getCommitted())
                        .max(memoryPoolMxBean.getUsage().getMax())
                        .build();
                memoryPoolMap.put(temp.getName(),temp);
            }
        } catch (Exception e) {
        }
    }
}
