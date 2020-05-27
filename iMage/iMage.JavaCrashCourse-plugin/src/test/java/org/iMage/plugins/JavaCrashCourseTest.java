package org.iMage.plugins;

import org.jis.Main;
import org.junit.Test;

public class JavaCrashCourseTest {

    @Test
    public final void JavaCrashCourseInitTest() {
        PluginManagement.getPlugins().forEach(plug -> {
            if (plug.getName().equals("JavaCrashCourse")) {
                plug.init(null);
            }
        });
    }

    @Test
    public final void JavaCrashCourseConfigureTest() {
        PluginManagement.getPlugins().forEach(plug -> {
            if (plug.getName().equals("JavaCrashCourse")) {
                plug.configure();
            }
        });
    }

}
