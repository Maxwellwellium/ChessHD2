import java.awt.image.BufferedImage;

public class Pawn extends Piece {
    BufferedImage image = Constants.pieceIMG;

    public Pawn(boolean white, Square square) {
        super(white, square);
    }


}
