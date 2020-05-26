package org.iMage.plugins;


import org.jis.Main;

public class TestPlugin2 extends PluginForJmjrst {
    @Override
    public String getName() {
        return "abc";
    }

    @Override
    public int getNumberOfParameters() {
        return 2987074;
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
