import java.awt.image.BufferedImage;

public class Piece {
    boolean moved = false;
    boolean white;
    BufferedImage image;
    Square square;

    public Piece(boolean white, Square square) {
        this.white = white; //determines image used
        this.square = square; //determines col & row

    }

    public boolean isMoved() {
        return moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    public boolean isWhite() {
        return white;
    }

    public void setWhite(boolean white) {
        this.white = white;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public Square getSquare() {
        return square;
    }

    public void setSquare(Square square) {
        this.square = square;
    }

}
