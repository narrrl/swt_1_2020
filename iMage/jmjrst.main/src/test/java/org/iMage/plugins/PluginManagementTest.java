package org.iMage.plugins;

import org.junit.Assert;
import org.junit.Test;

public class PluginManagementTest {

    /**
     * Tests {@link PluginManagement#getPlugins()}
     * the return of it should be an ordered Iterable of
     * {@link TestPlugin}{@link TestPlugin2} {@link TestPlugin3} {@link TestPlugin4}.
     */
    @Test
    public final void sortTest() {
        StringBuilder out = new StringBuilder();
        PluginManagement.getPlugins().forEach(out::append);
        Assert.assertEquals(out.toString(),"abc paraAmount: -2987074"
                + "abc paraAmount: 0"
                + "abc paraAmount: 2987074"
                + "abcd paraAmount: 0");
    }

}
