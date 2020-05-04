package org.jis.generator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;

public class LayoutGalerieTest {

	private static final String DEFAULT = "\033[0m";
	public static final String RED = "\033[0;31m";

	private LayoutGalerie galerieUnderTest;

	private File fromFile;
	private File toFile;

	@Before
	public final void createGalerie() {
		galerieUnderTest = new LayoutGalerie(null, null);
	}

	/**
	 * Test method for {@link org.jis.generator.LayoutGalerie#copyFile(File, File)}.
	 */
	@Test
	public final void testCopyFile() throws URISyntaxException {
		try {
			final File resourceFolder = new File(this.getClass().getResource(File.separator).toURI());
			fromFile = new File(resourceFolder, "from");
			toFile = new File(resourceFolder, "to");

			byte[] array = new byte[10];
			new Random().nextBytes(array);
			String randomString = new String(array);

			fromFile.createNewFile();
			Path fromPath = FileSystems.getDefault().getPath(fromFile.getPath());
			Files.writeString(fromPath, randomString);

			galerieUnderTest.copyFile(fromFile, toFile);

			assertTrue(toFile.exists());

			Path toPath = FileSystems.getDefault().getPath(toFile.getPath());
			String contents = Files.readString(toPath);

			assertEquals(randomString, contents);
		} catch (IOException | URISyntaxException e) {
			fail();
		}

	}

	@Test(expected = FileNotFoundException.class)
	public final void testCopyFileOfDirectories() throws URISyntaxException, FileNotFoundException, IOException {
		final File resourceFolder = new File(this.getClass().getResource(File.separator).toURI());
		fromFile = new File(resourceFolder, "from");
		toFile = new File(resourceFolder, "to");
		fromFile.mkdir();
		toFile.mkdir();
		assertTrue(toFile.exists());
		assertTrue(fromFile.exists());
		galerieUnderTest.copyFile(fromFile, toFile);
	}

	@Test(expected = FileNotFoundException.class)
	public final void testCopyFileOfNotExistingFiles() throws URISyntaxException, FileNotFoundException, IOException {
		final File resourceFolder = new File(this.getClass().getResource(File.separator).toURI());
		fromFile = new File(resourceFolder, "from");
		toFile = new File(resourceFolder, "to");
		assertTrue(!toFile.exists());
		assertTrue(!fromFile.exists());
		galerieUnderTest.copyFile(fromFile, toFile);
	}

	@Test
	public final void testCopyFileToExistingFile() {
		try {
			final File resourceFolder = new File(this.getClass().getResource(File.separator).toURI());
			fromFile = new File(resourceFolder, "from");
			toFile = new File(resourceFolder, "to");
			byte[] array = new byte[10];
			new Random().nextBytes(array);
			String randomString = new String(array);
			fromFile.createNewFile();
			toFile.createNewFile();
			Path fromPath = FileSystems.getDefault().getPath(fromFile.getPath());
			Path toPath = FileSystems.getDefault().getPath(toFile.getPath());
			Files.writeString(fromPath, randomString);
			Files.writeString(toPath, randomString);

			galerieUnderTest.copyFile(fromFile, toFile);

			assertTrue(toFile.exists());

			toPath = FileSystems.getDefault().getPath(toFile.getPath());
			String contents = Files.readString(toPath);

			assertEquals(randomString, contents);
		} catch (IOException | URISyntaxException e) {
			fail();
		}
	}

	@Test(expected = IOException.class)
	public final void copyFileLockedSourceFile() throws URISyntaxException, FileNotFoundException, IOException {
		final File resourceFolder = new File(this.getClass().getResource(File.separator).toURI());
		fromFile = new File(resourceFolder, "from");
		toFile = new File(resourceFolder, "to");
		try {
			fromFile.createNewFile();
			toFile.createNewFile();
			assertTrue(fromFile.setReadable(false));
		} catch (IOException e) {
			fail();
		}
		assertTrue(fromFile.exists());
		assertFalse(fromFile.canRead());
		assertTrue(toFile.exists());
		galerieUnderTest.copyFile(fromFile, toFile);
	}

	@After
	public final void cleanUp() {
		fromFile.setReadable(true);
		toFile.setReadable(true);
		fromFile.setWritable(true);
		toFile.setWritable(true);
		if (fromFile != null) {
			if (fromFile.exists() && !fromFile.delete()) {
				System.out.println("[" + LayoutGalerieTest.RED + "ERROR" + LayoutGalerieTest.DEFAULT
						+ "] Couldn't delete fromFile for test!");
			}
			fromFile = null;
		}
		if (toFile != null) {
			if (toFile.exists() && !toFile.delete()) {
				System.out.println("[" + LayoutGalerieTest.RED + "ERROR" + LayoutGalerieTest.DEFAULT
						+ "] Couldn't delete toFile for test!");
			}
			toFile = null;
		}
		galerieUnderTest = null;
	}

}
