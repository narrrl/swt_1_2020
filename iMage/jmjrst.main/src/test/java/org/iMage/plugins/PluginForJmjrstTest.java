package org.iMage.plugins;

import java.util.*;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;
import org.junit.After;
import org.junit.Before;

public class PluginForJmjrstTest {
    LinkedList<TestPlugin> list;

    /**
     * Creates a new list of plugins {@link org.iMage.plugins.TestPlugin} that extends
     * {@link org.iMage.plugins.PluginForJmjrst}.
     */
    @Before
    public final void buildUp() {
        list = new LinkedList<>();
        list.addAll(Arrays.asList(new TestPlugin("abc",0), new TestPlugin("abc",2987074),
                new TestPlugin("abcd",0), new TestPlugin("abc",-2987074)));
    }

    /**
     * Tests {@link org.iMage.plugins.PluginForJmjrst#compareTo(PluginForJmjrst)}
     * and {@link org.iMage.plugins.PluginForJmjrst#hashCode()} by sorting the list
     * and comparing the output with the expected output.
     */
    @Test
    public final void sortTest() {
        StringBuilder output = new StringBuilder();
        list.stream().sorted().collect(Collectors.toList()).forEach(output::append);
        Assert.assertEquals(output.toString(), "abc paraAmount: -2987074"
                + "abc paraAmount: 0"
                + "abc paraAmount: 2987074"
                + "abcd paraAmount: 0");
    }

    /**
     * Removes the list of test plugins.
     */
    @After
    public final void cleanUp() {
        this.list = null;
    }

}
