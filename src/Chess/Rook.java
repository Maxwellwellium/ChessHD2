package Chess;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Rook extends Piece {
    int classID = 3;
    public int getClassID() {
        return classID;
    }
    public Rook(boolean white, Square square) throws IOException {
        super(white, square);
        if (white) {
            this.image = ImageIO.read(Objects.requireNonNull(Constants.class.getResource("/Assets/Pieces/White/rook.png")));
        } else {
            this.image = ImageIO.read(Objects.requireNonNull(Constants.class.getResource("/Assets/Pieces/Black/rook.png")));
        }
    }

    public Object[] Movements() {
        ArrayList<Integer> validMovesList = new ArrayList<>(rookMovements());
        return validMovesList.toArray();
    }
}
