package Chess;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Cameleater extends Piece {
    public Cameleater(boolean white, Square square) throws IOException {
        super(white, square);
        if (white) {
            this.image = ImageIO.read(Objects.requireNonNull(Constants.class.getResource("/Assets/Pieces/White/cameleater.png")));
        } else {
            this.image = ImageIO.read(Objects.requireNonNull(Constants.class.getResource("/Assets/Pieces/Black/cameleater.png")));
        }
    }
    public Object[] Movements() {
        ArrayList<Integer> validMovesList = new ArrayList<>();
        validMovesList.addAll(camelMovements());
        validMovesList.addAll(knightMovements());

        return validMovesList.toArray();
    }
}
