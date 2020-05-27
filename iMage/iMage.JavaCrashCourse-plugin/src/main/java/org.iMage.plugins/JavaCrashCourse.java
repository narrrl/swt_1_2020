package org.iMage.plugins;

import org.jis.Main;

import java.util.List;

public class JavaCrashCourse extends PluginForJmjrst {
    private static final  List<String> RELEASES = List.of(new String[]{"Java SE 8 (LTS)", "JAVA SE 9", "JAVA SE 10",
            "JAVA SE 11 (LTS)", "JAVA SE 13", "JAVA SE 14"});

    @Override
    public String getName() {
        return "JavaCrashCourse";
    }

    @Override
    public int getNumberOfParameters() {
        return 0;
    }

    /**
     * Prints the amount of java versions since Java 8 (inclusive)
     * @param main main
     */
    @Override
    public void init(Main main) {
        System.out.printf("Found %s Java versions since Java 8", RELEASES.size());
    }

    @Override
    public void run() {

    }

    @Override
    public boolean isConfigurable() {
        return false;
    }

    @Override
    public void configure() {

    }
}
