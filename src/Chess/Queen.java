package Chess;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Queen extends Piece {
    public Queen(boolean white, Square square) throws IOException {
        super(white, square);
        if (white) {
            this.image = ImageIO.read(Objects.requireNonNull(Constants.class.getResource("/Assets/Pieces/White/queen.png")));
        } else {
            this.image = ImageIO.read(Objects.requireNonNull(Constants.class.getResource("/Assets/Pieces/Black/queen.png")));
        }
    }
    public Object[] Movements() {
        ArrayList<Integer> validMovesList = new ArrayList<>();
        validMovesList.addAll(rookMovements());
        validMovesList.addAll(bishopMovements());

        return validMovesList.toArray();
    }
}
