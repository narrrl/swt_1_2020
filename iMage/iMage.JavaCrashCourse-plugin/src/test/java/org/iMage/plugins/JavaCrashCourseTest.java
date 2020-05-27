package org.iMage.plugins;

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

}
