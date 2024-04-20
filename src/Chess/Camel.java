package Chess;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class Camel extends Piece {
    public Camel(boolean white, Square square) throws IOException {
        super(white, square);
        if (white) {
            this.image = ImageIO.read(Objects.requireNonNull(Constants.class.getResource("/Assets/Pieces/White/camel.png")));
        } else {
            this.image = ImageIO.read(Objects.requireNonNull(Constants.class.getResource("/Assets/Pieces/Black/camel.png")));
        }
    }

    public Object[] Movements() {
        ArrayList<Integer> validMovesList = new ArrayList<>(camelMovements());
        return validMovesList.toArray();
    }
}
