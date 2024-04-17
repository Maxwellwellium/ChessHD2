import javax.swing.*;

public class Square {
    JButton button;
    String col;
    int row;
    Piece piece;

    public Square(String col, int row) {
        this.button = new JButton();
        this.col = col;
        this.row = row;
        this.piece = null;
    }

    public JButton getButton() {
        return button;
    }

    public void setButton(JButton square) {
        this.button = square;
    }

    public String getCol() {
        return col;
    }

    public void setCol(String col) {
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public void printSquareInfo() {
        if (this.piece != null) {
            System.out.println(this.piece+" at "+this.col+this.row);
        } else {
            System.out.println("No Piece at "+this.col+this.row);
        }
    }
    public void printSquare() {
        System.out.println(this.col+this.row);
    }
}
