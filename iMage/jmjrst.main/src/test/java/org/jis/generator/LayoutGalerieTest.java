package org.jis.generator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
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

	@After
	public final void cleanUp() {
		boolean fromDeleted = fromFile.delete();
		boolean toDeleted = toFile.delete();
		galerieUnderTest = null;
		if (!fromDeleted) {
			System.out.println("[" + LayoutGalerieTest.RED + "ERROR" + LayoutGalerieTest.DEFAULT
					+ "] Couldn't delete file created for the test!");
		}
		if (!toDeleted) {
			System.out.println("[" + LayoutGalerieTest.RED + "ERROR" + LayoutGalerieTest.DEFAULT
					+ "] Couldn't delete the copied file!");
		}
	}

}
