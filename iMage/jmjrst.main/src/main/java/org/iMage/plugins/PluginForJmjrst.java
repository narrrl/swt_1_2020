package org.iMage.plugins;

/**
 * Abstract parent class for plug-ins for JMJRST
 *
 * @author Dominik Fuchss
 */
public abstract class PluginForJmjrst implements Comparable<PluginForJmjrst> {

  /**
   * Returns the name of this plug-in
   *
   * @return the name of the plug-in
   */
  public abstract String getName();

  /**
   * Returns the number of parameters of this plug-in
   *
   * @return the number of parameters of this plug-in
   */
  public abstract int getNumberOfParameters();

  /**
   * JMJRST pushes the main application to every subclass - so plug-ins are allowed to look at Main
   * as well.
   *
   * @param main
   *     JMJRST main application
   */
  public abstract void init(org.jis.Main main);

  /**
   * Runs plug-in
   */
  public abstract void run();

  /**
   * Returns whether the plug-in can be configured or not
   *
   * @return true if the plug-in can be configured.
   */
  public abstract boolean isConfigurable();

  /**
   * Open a configuration dialogue.
   */
  public abstract void configure();

  /**
   * Compares two hashes by subtracting this hashcode with the others hashcode.
   * if both have the same name, the parameter count gets compared.
   * @param otherPlugin the compared object
   * @return subtracted hash
   */
  @Override
  public int compareTo(PluginForJmjrst otherPlugin) {
    if (this == otherPlugin) {
      return 0;
    }
    if (otherPlugin == null) {
      return this.hashCode();
    }
    if (this.getName().equals(otherPlugin.getName())) {
      return this.getNumberOfParameters() - otherPlugin.getNumberOfParameters();
    }
    return this.hashCode() - otherPlugin.hashCode();
  }

  /**
   * Hashcode is the hash of the name string {@link String#hashCode()}
   * @return hash for this object
   */
  @Override
  public int hashCode() {
    return this.getName().hashCode();
  }

  /**
   * Compares the input obj with this.
   * @param obj the compared object
   * @return if obj equals this
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (obj == this) {
      return true;
    }
    if (obj.getClass() == this.getClass()) {
      PluginForJmjrst plugin = (PluginForJmjrst) obj;
      return this.compareTo(plugin) == 0;
    }
    return false;
  }
}
