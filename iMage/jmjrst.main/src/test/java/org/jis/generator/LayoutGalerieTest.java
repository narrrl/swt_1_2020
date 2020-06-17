package org.jis.generator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.channels.FileLock;
import java.nio.file.Files;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class LayoutGalerieTest {

    private LayoutGalerie galerieUnderTest;

    private static File resourceFolder;

    private File fromFile;
    private File toFile;

    @BeforeClass
    public static void setUpBeforeClass() {
        try {
            resourceFolder = new File(LayoutGalerieTest.class.getResource(File.separator).toURI());

            System.out.println(resourceFolder.getPath());

            resourceFolder.mkdirs();

            assertTrue(resourceFolder.exists());
        } catch (URISyntaxException e) {
            fail(e.getLocalizedMessage());
        }
    }

    @Before
    public void setUp() {
        galerieUnderTest = new LayoutGalerie(null, null);

        fromFile = new File(resourceFolder, "from");
        toFile = new File(resourceFolder, "to");
    }

    private static String getRandomString() {
        byte[] bytes = new byte[10];
        new Random().nextBytes(bytes);
        return new String(bytes);
    }

    @Test
    public final void testCopyFile() {
        String randomString = getRandomString();

        try {
            assertTrue(fromFile.createNewFile());

            Files.writeString(fromFile.toPath(), randomString);

            galerieUnderTest.copyFile(fromFile, toFile);

            assertTrue(toFile.exists());

            String contents = Files.readString(toFile.toPath());

            assertEquals(randomString, contents);
        } catch (IOException e) {
            fail(e.getLocalizedMessage());
        }

    }

    @Test(expected = FileNotFoundException.class)
    public final void testCopyDirectory() throws IOException {
        assertTrue(fromFile.mkdir());

        galerieUnderTest.copyFile(fromFile, toFile);
    }

    @Test(expected = FileNotFoundException.class)
    public final void testCopyNonexistingSourceFile() throws IOException {
        fromFile.delete();

        assertFalse(fromFile.exists());

        galerieUnderTest.copyFile(fromFile, toFile);
    }

    @Test
    public final void testCopyPreexistingTargetFile() {
        String randomSourceString = getRandomString();
        String randomTargetString = getRandomString();

        try {
            assertTrue(fromFile.createNewFile());
            assertTrue(toFile.createNewFile());

            Files.writeString(fromFile.toPath(), randomSourceString);
            Files.writeString(toFile.toPath(), randomTargetString);

            galerieUnderTest.copyFile(fromFile, toFile);

            assertTrue(toFile.exists());

            String contents = Files.readString(toFile.toPath());

            assertEquals(randomSourceString, contents);
        } catch (IOException e) {
            fail(e.getLocalizedMessage());
        }
    }

    @Test
    public final void testCopyNonreadableSourceFile() throws IOException {

        assumeFalse(System.getProperty("os.name").startsWith("Windows"));

        assertTrue(fromFile.createNewFile());

        assertTrue(fromFile.setReadable(false));

        assertThrows(IOException.class, () -> galerieUnderTest.copyFile(fromFile, toFile));
    }

    @Test
    public final void testCopyNonreadableSourceFileWin() throws IOException {

        assumeTrue(System.getProperty("os.name").startsWith("Windows"));

        FileOutputStream out = null;
        FileLock lock = null;

        try {
            assertTrue(fromFile.createNewFile());

            out = new FileOutputStream(fromFile);
            lock = out.getChannel().tryLock();

            assertNotNull(lock);

            assertThrows(IOException.class, () -> galerieUnderTest.copyFile(fromFile, toFile));
        } finally {
            if (out != null) {
                out.close();
            }

            if (lock != null) {
                assertFalse(lock.isValid());
            }
        }
    }

    @Test
    public final void testCopyNonwritableTargetFile() throws IOException {
        assertTrue(fromFile.createNewFile());
        assertTrue(toFile.createNewFile());

        Files.writeString(fromFile.toPath(), getRandomString());

        assertTrue(toFile.setWritable(false));

        assertThrows(IOException.class, () -> galerieUnderTest.copyFile(fromFile, toFile));
    }

    @After
    public void tearDown() {
        if (fromFile.exists()) {
            assertTrue(fromFile.delete());
        }
        if (toFile.exists()) {
            assertTrue(toFile.delete());
        }
    }

}
