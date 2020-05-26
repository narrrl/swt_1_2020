package org.iMage.plugins;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Knows all available plug-ins and is responsible for using the service loader API to detect them.
 *
 * @author Dominik Fuchss
 */
public final class PluginManagement {

  /**
   * No constructor for utility class.
   */
  private PluginManagement() {
    throw new IllegalAccessError();
  }

  /**
   * Return an {@link Iterable} Object with all available {@link PluginForJmjrst PluginForJmjrsts}
   * sorted alphabetically according to their name. In case of equally named Plugins sort them by
   * the number of parameters they have (less first).
   *
   * @return an {@link Iterable} Object containing all available plug-ins
   */
  public static Iterable<PluginForJmjrst> getPlugins() {
    Iterable<PluginForJmjrst> iterable = () -> ServiceLoader.load(PluginForJmjrst.class).iterator();
    return StreamSupport.stream(iterable.spliterator(), false).sorted().collect(Collectors.toList());

    // ArrayList<PluginForJmjrst> plugins = new ArrayList<>();
    // ServiceLoader.load(PluginForJmjrst.class).iterator().forEachRemaining(plugins::add);
    // return plugins.stream().sorted().collect(Collectors.toList());
  }

}
