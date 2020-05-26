package org.iMage.plugins;

import org.jis.Main;

public class TestPlugin extends PluginForJmjrst {
    final String name;
    final int para;

    TestPlugin(final String name, final int para) {
        this.name = name;
        this.para = para;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getNumberOfParameters() {
        return this.para;
    }

    @Override
    public void init(Main main) {
        // what ever
    }

    @Override
    public void run() {
        // what ever
    }

    @Override
    public boolean isConfigurable() {
        return false;
    }

    @Override
    public void configure() {
      // what ever
    }

    @Override
    public String toString() {
        return this.getName() + " paraAmount: " + this.getNumberOfParameters();
    }
}
