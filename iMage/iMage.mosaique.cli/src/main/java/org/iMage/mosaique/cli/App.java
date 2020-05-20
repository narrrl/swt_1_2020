package org.iMage.mosaique.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.iMage.mosaique.MosaiqueEasel;
import org.iMage.mosaique.base.BufferedArtImage;
import org.iMage.mosaique.rectangle.RectangleArtist;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

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
    // Don't touch...
    CommandLine cmd = null;
    try {
      cmd = App.doCommandLineParsing(args);
    } catch (ParseException e) {
      System.err.println("Wrong command line arguments given: " + e.getMessage());
      System.exit(1);
    }
    // ...this!

    BufferedImage source;
    File pictureFolder;
    File out;
    int hTile;
    int wTile;
    try {
      source = ImageIO.read(new File(cmd.getOptionValue(CMD_OPTION_INPUT_IMAGE)));
      pictureFolder = new File(cmd.getOptionValue(CMD_OPTION_INPUT_TILES_DIR));
      hTile = Integer.parseInt(cmd.getOptionValue(CMD_OPTION_TILE_H));
      wTile = Integer.parseInt(cmd.getOptionValue(CMD_OPTION_TILE_W));
      out = new File(cmd.getOptionValue(CMD_OPTION_OUTPUT_IMAGE));
    } catch (IOException | NumberFormatException e) {
      System.err.println(e.getMessage());
      return;
    }
    ArrayList<BufferedArtImage> images = new ArrayList<>();
    for (File f : Objects.requireNonNull(pictureFolder.listFiles())) {
      try {
        images.add(new BufferedArtImage(ImageIO.read(f)));
      } catch (IOException e) {
        System.err.println(e.getMessage());
        return;
      }
    }
    MosaiqueEasel easel = new MosaiqueEasel();
    RectangleArtist artist = new RectangleArtist(images, wTile, hTile);
    BufferedImage outImage = easel.createMosaique(source, artist);
    try {
      ImageIO.write(outImage, "png", out);
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }

  }

  /**
   * Parse and check command line arguments
   *
   * @param args
   *          command line arguments given by the user
   * @return CommandLine object encapsulating all options
   * @throws ParseException
   *           if wrong command line parameters or arguments are given
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
