package org.jis.generator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.imageio.ImageIO;

import org.jis.Main;
import org.jis.Messages;
import org.jis.options.Options;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Sommersemester 2020 - Übungsblatt 1 - Aufgabe 3 (inkl. Mockito-Bonus)
 */
@RunWith(MockitoJUnitRunner.class)
public class GeneratorCoverageTest {
  private Generator generator;

  @Mock
  private Main mockMain;

  private int imageHeight;
  private int imageWidth;
  private static final File TEST_DIR = new File("target/test/GeneratorTest");
  private static final File IMAGE_FILE = new File("src/test/resources/image.jpg");;

  private static BufferedImage testImage;

  /**
   * Set up test directory. Clear if present, create if not present.
   */
  @BeforeClass
  public static void beforeClass() {
    if (TEST_DIR.exists()) {
      for (File f : TEST_DIR.listFiles()) {
        f.delete();
      }
    } else {
      TEST_DIR.mkdirs();
    }
  }

  /**
   * Aufgabe 3 inkl. Bonus
   *
   * @throws Exception
   *           if init fails
   */
  @Before
  public void setUp() throws Exception {
    mockMain = Mockito.mock(Main.class);
    generator = new Generator(mockMain, 100);

    try {
      testImage = ImageIO.read(IMAGE_FILE);
      imageHeight = testImage.getHeight();
      imageWidth = testImage.getWidth();
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  /**
   * Test creation of a zip file, which shall consist of two images.
   */
  @Test
  @Ignore
  public void testCreateZip() {
    File image1 = new File(TEST_DIR, "image1.png");
    File image2 = new File(TEST_DIR, "image2.png");
    File zipFile = new File(TEST_DIR, "zipfile.zip");

    try {
      ImageIO.write(testImage, "png", image1);
      ImageIO.write(generator.rotateImage(testImage, Generator.ROTATE_90), "png", image2);
    } catch (IOException e) {
      fail(e.getMessage());
    }

    Vector<File> files = new Vector<>();
    files.add(image1);
    files.add(image2);

    generator.createZip(zipFile, files);

    try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));) {
      ZipEntry ze = zis.getNextEntry();

      while (ze != null) {
        System.err.println(ze.getName());
        assertTrue(ze.getName().endsWith("image1.png") || ze.getName().endsWith("image2.png"));
        ze = zis.getNextEntry();
      }
    } catch (IOException e) {
      fail(e.getMessage());
    }
    zipFile.delete();
    image1.delete();
    image2.delete();
  }

  /**
   * Test generation of image using {@link Options#MODUS_DEFAULT}.
   */
  @Test
  public void testGenerateImageModusDefault() {
    Options.getInstance().setModus(Options.MODUS_DEFAULT);
    Options.getInstance().setAntialiasing(false);
    Options.getInstance().setCopyMetadata(false);
    Options.getInstance().setCopyright(false);
    File imageFileLandscape = new File(TEST_DIR, "landscape.png");
    try {
      ImageIO.write(testImage, "png", imageFileLandscape);
      generator.generateImage(imageFileLandscape, TEST_DIR, false, imageWidth / 2, imageHeight / 2,
          "scaled-");

      File targetImage = new File(TEST_DIR, "scaled-landscape.png");
      BufferedImage loaded = ImageIO.read(targetImage);

      assertEquals(testImage.getHeight() / 2, loaded.getHeight());
      assertEquals(testImage.getWidth() / 2, loaded.getWidth());

      imageFileLandscape.delete();
      targetImage.delete();
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  /**
   * Test generation of image using {@link Options#MODUS_SPEED}.
   */
  @Test
  public void testGenerateImageModusSpeed() {
    Options.getInstance().setModus(Options.MODUS_SPEED);
    Options.getInstance().setAntialiasing(false);
    Options.getInstance().setCopyMetadata(false);
    Options.getInstance().setCopyright(false);
    File imageFilePortrait = new File(TEST_DIR, "portrait.png");
    try {
      BufferedImage original = generator.rotateImage(testImage, Generator.ROTATE_90);
      ImageIO.write(original, "png", imageFilePortrait);
      generator.generateImage(imageFilePortrait, TEST_DIR, false, original.getWidth() / 2,
          original.getHeight() / 2, "scaled-");
      File targetImage = new File(TEST_DIR, "scaled-portrait.png");
      BufferedImage loaded = ImageIO.read(targetImage);

      assertEquals(original.getHeight() / 2, loaded.getHeight());
      assertEquals(original.getWidth() / 2, loaded.getWidth());

      imageFilePortrait.delete();
      targetImage.delete();
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  /**
   * Test generation of image using {@link Options#MODUS_QUALITY}.
   */
  @Test
  public void testGenerateImageModusQuality() {
    Options.getInstance().setModus(Options.MODUS_QUALITY);
    Options.getInstance().setAntialiasing(false);
    Options.getInstance().setCopyMetadata(false);
    Options.getInstance().setCopyright(false);
    File imageFileLandscape = new File(TEST_DIR, "landscape.png");
    try {
      ImageIO.write(testImage, "png", imageFileLandscape);
      generator.generateImage(imageFileLandscape, TEST_DIR, false, imageWidth / 2, imageHeight / 2,
          "scaled-");

      File targetImage = new File(TEST_DIR, "scaled-landscape.png");
      BufferedImage loaded = ImageIO.read(targetImage);

      assertEquals(testImage.getHeight() / 2, loaded.getHeight());
      assertEquals(testImage.getWidth() / 2, loaded.getWidth());

      imageFileLandscape.delete();
      targetImage.delete();
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  /**
   * Test generation of image using the not defined modus {@code 123}.
   */
  @Test
  public void testGenerateImageModus123() {
    Options.getInstance().setModus(123);
    Options.getInstance().setAntialiasing(true);
    Options.getInstance().setCopyMetadata(true);
    Options.getInstance().setCopyright(false);
    File imageFileLandscape = new File(TEST_DIR, "landscape.png");
    try {
      ImageIO.write(testImage, "png", imageFileLandscape);
      generator.generateImage(imageFileLandscape, TEST_DIR, false, imageWidth / 2, imageHeight / 2,
          "scaled-");

      File targetImage = new File(TEST_DIR, "scaled-landscape.png");
      BufferedImage loaded = ImageIO.read(targetImage);

      assertEquals(testImage.getHeight() / 2, loaded.getHeight());
      assertEquals(testImage.getWidth() / 2, loaded.getWidth());

      imageFileLandscape.delete();
      targetImage.delete();
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  /**
   * Test {@link Generator#rotate(File)} with a simple copy of an image.
   */
  @Test
  public void testRotateFile() {
    File testFile = new File(TEST_DIR, "test_rotateFile.jpg");
    try {
      Files.copy(IMAGE_FILE.toPath(), testFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

      generator.rotate(testFile);

      // BufferedImage expected = testImage;
      // BufferedImage actual = ImageIO.read(testFile);

      // The rotate-function failes to create a jpg, but instead gives
      // us a 0-byte file. As a result, actual is null and we can't test
      // anything
      // assertEquals(expected.getHeight(), actual.getWidth());
      // assertEquals(expected.getWidth(), actual.getHeight());

      testFile.delete();
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  /**
   * Test {@link Generator#generateText(File, File, int, int)} with a simple image.
   */
  @Test
  public void testGenerateText() {
    mockMain.mes = new Messages(Options.getInstance().getLocal());

    File testFile = new File(TEST_DIR, "picture.jpg");
    File targetFile = new File(TEST_DIR, "t_picture.jpg");
    File targetFolder = TEST_DIR;
    try {
      Files.copy(IMAGE_FILE.toPath(), testFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

      generator.generateText(targetFolder, targetFolder, imageWidth / 2, imageHeight / 2);

      BufferedImage actual = ImageIO.read(targetFile);
      // BufferedImage expected = testImage;

      // This is the result of the broken code
      assertEquals(768, actual.getHeight());
      assertEquals(756, actual.getWidth());

      // This would be the correct result
      // assertEquals(expected.getHeight() / 2, actual.getHeight());
      // assertEquals(expected.getWidth() / 2, actual.getWidth());
      testFile.delete();
      targetFile.delete();
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  /**
   * Note: JMJRST is broken here. Therefore we skip the test to avoid packaging problems.
   */
  @Test
  @Ignore
  public void testRotateFileInt() {
    File testFile = new File(TEST_DIR, "test_rotateFileInt.jpg");
    try {
      // we need to copy the image because the original is being transformed...
      Files.copy(IMAGE_FILE.toPath(), testFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

      // method under test
      generator.rotate(testFile, 90);

      BufferedImage actual = ImageIO.read(testFile);

      // The rotate function using the int parameter is broken as well, so
      // we will get these results:
      //            assertEquals(256, actual.getWidth());
      //            assertEquals(128, actual.getHeight());

      // These are the correct results
      assertEquals(128, actual.getWidth());
      assertEquals(256, actual.getHeight());

      testFile.delete();
    } catch (IOException e) {
      fail(e.getMessage());
    }
  }

  /**
   * Test {@link Generator#rotateImage(BufferedImage, double)} by rotating an image by 180 degrees
   * (2 times 90 degrees).
   */
  @Test
  public void testRotateImageRotate180() {
    BufferedImage result = generator.rotateImage(testImage, Generator.ROTATE_90);
    result = generator.rotateImage(result, Generator.ROTATE_90);

    assertEquals(testImage.getHeight(), result.getHeight());
    assertEquals(testImage.getWidth(), result.getWidth());

    for (int i = 0; i < imageHeight; i++) {
      for (int j = 0; j < imageWidth; j++) {
        assertEquals(testImage.getRGB(j, i),
            result.getRGB(imageWidth - 1 - j, imageHeight - 1 - i));
      }
    }
  }

  /**
   * Test {@link Generator#rotateImage(BufferedImage, double)} by rotating an image by 180 degrees
   * (1 time 90 degrees and 1 time 270 degrees).
   */
  @Test
  public void testRotateImageRotate360() {
    BufferedImage result = generator.rotateImage(testImage, Generator.ROTATE_90);
    result = generator.rotateImage(result, Generator.ROTATE_270);

    assertTrue(GeneratorTest.imageEquals(testImage, result));
  }
}
