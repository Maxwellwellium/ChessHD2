package Chess;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Knight extends Piece {
    public Knight(boolean white, Square square) throws IOException {
        super(white, square);
        if (white) {
            this.image = ImageIO.read(Objects.requireNonNull(Constants.class.getResource("/Assets/Pieces/White/knight.png")));
        } else {
            this.image = ImageIO.read(Objects.requireNonNull(Constants.class.getResource("/Assets/Pieces/Black/knight.png")));
        }
    }
}
