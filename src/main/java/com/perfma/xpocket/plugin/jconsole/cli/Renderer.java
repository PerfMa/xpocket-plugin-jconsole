package com.perfma.xpocket.plugin.jconsole.cli;

/**
 * How to render a data type
 * @author xinxian
 */
public interface Renderer {

    CharSequence render(TJContext tjContext, Object data);

}
