package Chess;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Pawn extends Piece {
    public Pawn(boolean white, Square square) throws IOException {
        super(white, square);
        if (white) {
            this.image = ImageIO.read(Objects.requireNonNull(Constants.class.getResource("/Assets/Pieces/White/pawn.png")));
        } else {
            this.image = ImageIO.read(Objects.requireNonNull(Constants.class.getResource("/Assets/Pieces/Black/pawn.png")));
        }
    }

    public Object[] Movements() {
        ArrayList<Integer> validMovesList = new ArrayList<>(pawnMovements());
        return validMovesList.toArray();
    }


}
