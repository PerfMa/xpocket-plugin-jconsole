package com.perfma.xpocket.plugin.jconsole.cli.handler;


/**
 * @author xinxian
 **/
public interface MxBeanNames {

    String MEMORY = "java.lang:type=Memory";
    String THREAD = "java.lang:type=Threading";
    String CLASSLOADING = "java.lang:type=ClassLoading";
    String RUNTING = "java.lang:type=Runtime";
    String OS = "java.lang:type=OperatingSystem";
    String CODE_CACHE = "Code Cache";
    String PS_OLD_GEN = "PS Old Gen";
    String PS_EDEN_SPACE = "PS Eden Space";
    String PS_SURVIOR_SPACE = "PS Survivor Space";
    String METASPACE = "Metaspace";
    String CCS = "Compressed Class Space";

}
