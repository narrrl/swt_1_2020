package org.iMage.plugins;

import org.jis.Main;

public class TestPlugin3 extends PluginForJmjrst {

    @Override
    public String getName() {
        return "abcd";
    }

    @Override
    public int getNumberOfParameters() {
        return 0;
    }

    @Override
    public void init(Main main) {

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

    @Override
    public String toString() {
        return this.getName() + " paraAmount: " + this.getNumberOfParameters();
    }
}
