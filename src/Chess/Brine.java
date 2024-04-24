package Chess;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Brine extends Piece {
    int classID = 9;
    public int getClassID() {
        return classID;
    }
    public Brine(boolean white, Square square) throws IOException {
        super(white, square);
        if (white) {
            this.image = ImageIO.read(Objects.requireNonNull(Constants.class.getResource("/Assets/Pieces/White/brine.png")));
        } else {
            this.image = ImageIO.read(Objects.requireNonNull(Constants.class.getResource("/Assets/Pieces/Black/brine.png")));
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
    public List<?> escapeMovements() {
        ArrayList<Integer> validMovesList = new ArrayList<>(kingMovements());
        return List.of(validMovesList.toArray());
    }
}
