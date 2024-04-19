import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Constants {

    public static final String[] PIECES = {"a", "b", "c", "e", "i", "k", "n", "p", "q", "r"};
    public static final String[] ALPHA = {"a", "b", "c", "d", "e", "f", "g", "h"};
    public static final String[] ALPHA_REVERSED = {"h", "g", "f", "e", "d", "c", "b", "a"};
    public static final int[] NUM = {1, 2, 3, 4, 5, 6, 7, 8};
    public static final int[] NUM_REVERSED = {8, 7, 6, 5, 4, 3, 2, 1};
    public static final String[] NUMSTRING = {"1", "2", "3", "4", "5", "6", "7", "8"};

    public static final Color BLACK = new Color(2,8,38); //;
    public static final Color DARKPURPLE = new Color(5,17,85); //
    public static final Color LIGHTPURPLE = new Color(73,19,189); //
    public static final Color DARKPINK = new Color(153,47,124); //
    public static final Color MEDIUMPINK = new Color(191,73,160); //
    public static final Color LIGHTPINK = new Color(186,123,201); //
    public static final Color WHITE = new Color(215,177,224); //
    public static final Color TRANSPARENT = new Color(0,0,0, 0); //

    static BufferedImage selectIMG;
    static BufferedImage attackIMG;
    static BufferedImage pieceIMG;

    static {
        try {
            selectIMG = ImageIO.read(Objects.requireNonNull(Constants.class.getResource("/Assets/select.png")));
            attackIMG = ImageIO.read(Objects.requireNonNull(Constants.class.getResource("/Assets/attack.png")));
            pieceIMG = ImageIO.read(Objects.requireNonNull(Constants.class.getResource("/Assets/piece.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
