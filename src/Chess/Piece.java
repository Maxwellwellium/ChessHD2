package Chess;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import org.apache.commons.lang3.ArrayUtils;

public class Piece {
    boolean moved;
    boolean white;
    BufferedImage image;
    Square square;

    public Piece(boolean white, Square square) {
        this.white = white;
        this.square = square; //determines col & row
        this.moved = false;
    }

    public Object[] Movements() {
        ArrayList<Integer> validMovesList = new ArrayList<>();


        return validMovesList.toArray();
    }

    public ArrayList<Integer> pawnMovements() {
        ArrayList<Integer> validMovesList = new ArrayList<>(4);

        String currentCol = this.square.getCol();
        int currentColInt = ArrayUtils.indexOf(Constants.ALPHA, currentCol);
        int currentRow = this.square.getRow();

        int direction;
        if (this.white) {
            direction = 1;
        } else {
            direction = -1;
        }

        if (currentRow != 8) {
            int m1 = coordsToIndex(currentCol, (currentRow + direction)); //square directly in front of pawn
            if (Board.masterBoard[m1].piece == null) {
                validMovesList.add(m1);
            }
            if ((currentRow != 7) && (!moved)) {
                int m2 = coordsToIndex(currentCol, (currentRow + (2 * direction))); //2 squares directly in front of pawn
                if ((Board.masterBoard[m1].piece == null) && (Board.masterBoard[m2].piece == null)) {
                    validMovesList.add(m2);
                }
            }
            if (!Objects.equals(Constants.ALPHA[currentColInt], "a")) {
                int m3 = coordsToIndex(Constants.ALPHA[currentColInt - 1], (currentRow + direction));
                if (Board.masterBoard[m3].piece == null) {
                    validMovesList.add(m3);
                }
            }
            if (!Objects.equals(Constants.ALPHA[currentColInt], "h")) {
                int m4 = coordsToIndex(Constants.ALPHA[currentColInt + 1], (currentRow + direction));
                if (Board.masterBoard[m4].piece == null) {
                    validMovesList.add(m4);
                }
            }
        }
        return validMovesList;
    }

    public int coordsToIndex(String col, int row) {
        try {
            int alphaOffset = Arrays.asList(Constants.ALPHA).indexOf(col);
            return (Constants.NUM_REVERSED[row - 1] * 8) - 8 + alphaOffset;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Not all moves on board");
            return -1;
        }
    }
    public int coordsToIndex(String col, String row) {
        try {
            int alphaOffset = Arrays.asList(Constants.ALPHA).indexOf(col);
            return (Constants.NUM_REVERSED[Integer.parseInt(row)-1] * 8) - 8 + alphaOffset;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Not all moves on board");
            return -1;
        }
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
