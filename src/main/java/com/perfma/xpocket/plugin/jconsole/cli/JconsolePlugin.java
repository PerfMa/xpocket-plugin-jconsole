package com.perfma.xpocket.plugin.jconsole.cli;

import com.perfma.xlab.xpocket.spi.AbstractXPocketPlugin;
import com.perfma.xlab.xpocket.spi.context.SessionContext;
import com.perfma.xlab.xpocket.spi.process.XPocketProcess;


/**
 * @author xinxian
 * @create 2020-09-14 16:08
 **/
public class JconsolePlugin extends AbstractXPocketPlugin {

    private static final String LOGO = "      _    ____    ___    _   _   ____     ___    _       _____ \n" +
                                       "     | |  / ___|  / _ \\  | \\ | | / ___|   / _ \\  | |     | ____|\n" +
                                       "  _  | | | |     | | | | |  \\| | \\___ \\  | | | | | |     |  _|  \n" +
                                       " | |_| | | |___  | |_| | | |\\  |  ___) | | |_| | | |___  | |___ \n" +
                                       "  \\___/   \\____|  \\___/  |_| \\_| |____/   \\___/  |_____| |_____| \n";
    
    private static int pid = -1;

    public static SessionContext context;

    public static boolean attachStatus = false;

    @Override
    public void switchOn(SessionContext context) {
        JconsolePlugin.context = context;
        JconsolePlugin.context.setPid(pid);
    }

    @Override
    public void switchOff(SessionContext context) {
        attachOff();
    }

    public static void attachOn(int pid) {
        if(context != null) {
            attachStatus = true;
            JconsolePlugin.pid = pid;
            context.setPid(pid);
        }
    }

    public static void attachOff() {
        if(context != null) {
            attachStatus = false;
            pid = -1;
            context.setPid(pid);
        }
    }

    @Override
    public void printLogo(XPocketProcess process) {
        process.output(LOGO);
    }
    
    
}
