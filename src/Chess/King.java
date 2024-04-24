package Chess;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class King extends Piece {
    int classID = 0;
    public int getClassID() {
        return classID;
    }

    public King(boolean white, Square square) throws IOException {
        super(white, square);
        if (white) {
            this.image = ImageIO.read(Objects.requireNonNull(Constants.class.getResource("/Assets/Pieces/White/king.png")));
        } else {
            this.image = ImageIO.read(Objects.requireNonNull(Constants.class.getResource("/Assets/Pieces/Black/king.png")));
        }
    }
    public Object[] Movements() {
        ArrayList<Integer> validMovesList = new ArrayList<>(kingMovements());
        //no moves if it's not the piece's turn
        if (this.white != Board.isPlayWhite()) {
            validMovesList.clear();
        }
        return validMovesList.toArray();
    }
}
