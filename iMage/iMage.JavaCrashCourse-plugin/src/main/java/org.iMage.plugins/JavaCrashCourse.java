package org.iMage.plugins;

import org.jis.Main;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import java.util.List;
import java.util.Random;
import org.kohsuke.MetaInfServices;

@MetaInfServices
public class JavaCrashCourse extends PluginForJmjrst {
    private static final List<String> RELEASES = List.of(new String[]{JavaCrashCourse.JAVA_8, JavaCrashCourse.JAVA_9,
            JavaCrashCourse.JAVA_10, JavaCrashCourse.JAVA_11, JavaCrashCourse.JAVA_12, JavaCrashCourse.JAVA_13,
            JavaCrashCourse.JAVA_14});
    private static final String JAVA_8 = "Java SE 8 (LTS)";
    private static final String JAVA_9 = "Java SE 9";
    private static final String JAVA_10 = "Java SE 10";
    private static final String JAVA_11 = "Java SE 11 (LTS)";
    private static final String JAVA_12 = "Java SE 12";
    private static final String JAVA_13 = "Java SE 13";
    private static final String JAVA_14 = "Java SE 14";

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
        Random rand = new Random();
        String randVersion = RELEASES.get(rand.nextInt(RELEASES.size() - 1));
        switch (randVersion) {
            case JAVA_14 -> System.out.print("Keeping updated");
            case JAVA_8, JAVA_9, JAVA_10, JAVA_11, JAVA_12, JAVA_13 -> System.out.println("Running late");
            default -> System.out.println(this.getName() + "(" + this.getNumberOfParameters() + ")");
        }
    }

    @Override
    public boolean isConfigurable() {
        return true;
    }

    /**
     * I have no idea what the task wants from me, but here we go a info message with the java versions:
     */
    @Override
    public void configure() {
        Runnable r = () -> {
            StringBuilder text = new StringBuilder();
            RELEASES.forEach(str -> text.append(str).append("\n"));
            JOptionPane.showMessageDialog(null, String.format(text
                    .toString().substring(0, text.length() - 1), 200, 200),
                    "JavaCrashCourse plug-in configuration", JOptionPane.INFORMATION_MESSAGE);
        };
        SwingUtilities.invokeLater(r);
    }
}
