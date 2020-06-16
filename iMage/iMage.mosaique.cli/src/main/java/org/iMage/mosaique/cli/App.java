package org.iMage.mosaique.cli;

import org.apache.commons.cli.*;
import org.iMage.mosaique.MosaiqueEasel;
import org.iMage.mosaique.base.BufferedArtImage;
import org.iMage.mosaique.rectangle.RectangleArtist;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class parses all command line parameters and creates a mosaique.
 */
public final class App {
  private App() {
    throw new IllegalAccessError();
  }

  private static final String CMD_OPTION_INPUT_IMAGE = "i";
  private static final String CMD_OPTION_INPUT_TILES_DIR = "t";
  private static final String CMD_OPTION_OUTPUT_IMAGE = "o";

  private static final String CMD_OPTION_TILE_W = "w";
  private static final String CMD_OPTION_TILE_H = "h";

  public static void main(String[] args) {
    CommandLine cmd = null;
    try {
      cmd = App.doCommandLineParsing(args);
    } catch (ParseException e) {
      System.err.println("Wrong command line arguments given: " + e.getMessage());
      System.exit(1);
    }

    BufferedImage inputImage = loadInput(cmd);
    List<BufferedArtImage> tiles = loadTiles(cmd);

    int tileW = cmd.hasOption(App.CMD_OPTION_TILE_W) ?
        Integer.parseInt(cmd.getOptionValue(App.CMD_OPTION_TILE_W)) :
        inputImage.getWidth() / 10;

    int tileH = cmd.hasOption(App.CMD_OPTION_TILE_H) ?
        Integer.parseInt(cmd.getOptionValue(App.CMD_OPTION_TILE_H)) :
        inputImage.getHeight() / 10;

    if (tileW <= 0 || tileH <= 0 || tileW > inputImage.getWidth() || tileH > inputImage
        .getHeight()) {
      System.err.println("tileW/H is invalid: " + tileW + "," + tileH);
      System.exit(1);
    }

    MosaiqueEasel me = new MosaiqueEasel();
    RectangleArtist ra = new RectangleArtist(tiles, tileW, tileH);
    BufferedImage outputImage = me.createMosaique(inputImage, ra);

    writeOutput(cmd, outputImage);

  }

  private static BufferedImage loadInput(CommandLine cmd) {
    try {
      String path = cmd.getOptionValue(App.CMD_OPTION_INPUT_IMAGE);
      if (!path.toLowerCase().endsWith(".png") && !path.toLowerCase().endsWith(".jpeg") && !path
          .toLowerCase().endsWith("jpg")) {
        System.err.println("Input is neither PNG nor JPG");
        System.exit(1);
      }
      return ImageIO.read(App.ensureFile(path, false));
    } catch (IOException e) {
      System.err.println(e.getMessage());
      System.exit(1);
      throw new Error("unreachable code");
    }
  }

  private static List<BufferedArtImage> loadTiles(CommandLine cmd) {
    List<BufferedArtImage> tiles = new ArrayList<>();
    try {

      String tileDir = cmd.getOptionValue(App.CMD_OPTION_INPUT_TILES_DIR);

      File directory = App.ensureFile(tileDir, false);
      FileFilter isImage = f -> f.getName().toLowerCase().endsWith(".jpeg") || f.getName()
          .toLowerCase().endsWith(".jpg") || f.getName().toLowerCase().endsWith(".png");

      for (File file : directory.listFiles(isImage)) {
        BufferedImage bi = ImageIO.read(file);
        BufferedArtImage bai = new BufferedArtImage(bi);
        tiles.add(bai);
      }

    } catch (IOException e) {
      System.err.println(e.getMessage());
      System.exit(1);
    }

    if (tiles.size() < 10) {
      System.err.println("Not enough tiles found");
      System.exit(1);
    }

    return tiles;

  }

  private static void writeOutput(CommandLine cmd, BufferedImage outputImage) {
    File output = null;
    try {
      output = App.ensureFile(cmd.getOptionValue(App.CMD_OPTION_OUTPUT_IMAGE), true);
      ImageIO.write(outputImage, "png", output);
    } catch (IOException e) {
      System.err.println("Could not save image: " + e.getMessage());
      System.exit(1);
    }

  }

  /**
   * Ensure that a file exists (or create if allowed by parameter).
   *
   * @param path
   *     the path to the file
   * @param create
   *     indicates whether creation is allowed
   * @return the file
   * @throws IOException
   *     if something went wrong
   */
  private static File ensureFile(String path, boolean create) throws IOException {
    File file = new File(path);
    if (file.exists()) {
      return file;
    }
    if (create) {
      file.createNewFile();
      return file;
    }

    // File not available
    throw new IOException("The specified file does not exist: " + path);
  }

  /**
   * Parse and check command line arguments
   *
   * @param args
   *     command line arguments given by the user
   * @return CommandLine object encapsulating all options
   * @throws ParseException
   *     if wrong command line parameters or arguments are given
   */
  private static CommandLine doCommandLineParsing(String[] args) throws ParseException {
    Options options = new Options();
    Option opt;

    /*
     * Define command line options and arguments
     */
    opt = new Option(App.CMD_OPTION_INPUT_IMAGE, "input-images", true, "path to input image");
    opt.setRequired(true);
    opt.setType(String.class);
    options.addOption(opt);

    opt = new Option(App.CMD_OPTION_INPUT_TILES_DIR, "tiles-dir", true, "path to tiles directory");
    opt.setRequired(true);
    opt.setType(String.class);
    options.addOption(opt);

    opt = new Option(App.CMD_OPTION_OUTPUT_IMAGE, "image-output", true, "path to output image");
    opt.setRequired(true);
    opt.setType(String.class);
    options.addOption(opt);

    opt = new Option(App.CMD_OPTION_TILE_W, "tile-width", true, "the width of a tile");
    opt.setRequired(false);
    opt.setType(Integer.class);
    options.addOption(opt);

    opt = new Option(App.CMD_OPTION_TILE_H, "tile-height", true, "the height of a tile");
    opt.setRequired(false);
    opt.setType(Integer.class);
    options.addOption(opt);

    CommandLineParser parser = new DefaultParser();
    return parser.parse(options, args);
  }

}
