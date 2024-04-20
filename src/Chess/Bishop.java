package Chess;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Bishop extends Piece {
    public Bishop(boolean white, Square square) throws IOException {
        super(white, square);
        if (white) {
            this.image = ImageIO.read(Objects.requireNonNull(Constants.class.getResource("/Assets/Pieces/White/bishop.png")));
        } else {
            this.image = ImageIO.read(Objects.requireNonNull(Constants.class.getResource("/Assets/Pieces/Black/bishop.png")));
        }
    }
    public Object[] Movements() {
        ArrayList<Integer> validMovesList = new ArrayList<>();
        validMovesList.addAll(bishopMovements());

        return validMovesList.toArray();
    }
}
