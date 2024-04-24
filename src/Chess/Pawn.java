package Chess;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Pawn extends Piece {
    int classID = 8;
    int lastRow;
    public int getLastRow() {
        return lastRow;
    }

    public int getClassID() {
        return classID;
    }
    public Pawn(boolean white, Square square) throws IOException {
        super(white, square);
        if (white) {
            this.image = ImageIO.read(Objects.requireNonNull(Constants.class.getResource("/Assets/Pieces/White/pawn.png")));
            lastRow = 8;
        } else {
            this.image = ImageIO.read(Objects.requireNonNull(Constants.class.getResource("/Assets/Pieces/Black/pawn.png")));
            lastRow = 1;
        }
    }

    public Object[] Movements() {
        ArrayList<Integer> validMovesList = new ArrayList<>(pawnMovements());
        //no moves if it's not the piece's turn
        if (this.white != Board.isPlayWhite()) {
            validMovesList.clear();
        }
        return validMovesList.toArray();
    }


}
