package org.iMage.plugins;

import org.jis.Main;
import org.kohsuke.MetaInfServices;

import javax.swing.*;
import java.util.List;
import java.util.stream.Collectors;

@MetaInfServices(PluginForJmjrst.class)
public class JavaCrashCourse extends PluginForJmjrst {

  private Main main;
  private List<String> javaVersions;
  private final String name = "JavaCrashCourse";

  public JavaCrashCourse() {
    javaVersions = List.of("8", "9", "10", "11", "12", "13", "14");
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public int getNumberOfParameters() {
    return javaVersions.size();
  }

  @Override
  public void init(Main main) {
    this.main = main;
    System.out.println("Found " + javaVersions.size() + " Java versions since Java 8");
  }

  @Override
  public void run() {
    String version = javaVersions == null || javaVersions.isEmpty() //
        ? "" : javaVersions.get((int) (Math.random() * javaVersions.size()));

    String output = switch (version) {
      case "14" -> "Keeping updated";
      case "8", "9", "10", "11", "12", "13" -> "Running late";
      default -> getName() + '(' + getNumberOfParameters() + ')';
    };
    System.out.println(output);
  }

  @Override
  public boolean isConfigurable() {
    return true;
  }

  @Override
  public void configure() {
    String versions = this.javaVersions.stream().map(s -> "Java " + s)
        .collect(Collectors.joining(",\n"));
    JOptionPane.showMessageDialog(this.main, //
        "All Java Versions since 8:\n\n" + versions, //
        "Java Crash Course", JOptionPane.INFORMATION_MESSAGE);
  }
}
