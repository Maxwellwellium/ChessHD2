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
                if ((Board.masterBoard[m3].piece != null) && (Board.masterBoard[m3].piece.white != this.white)) {
                    validMovesList.add(m3);
                }
            }
            if (!Objects.equals(Constants.ALPHA[currentColInt], "h")) {
                int m4 = coordsToIndex(Constants.ALPHA[currentColInt + 1], (currentRow + direction));
                if ((Board.masterBoard[m4].piece != null) && (Board.masterBoard[m4].piece.white != this.white)) {
                    validMovesList.add(m4);
                }
            }
        }
        System.out.println(validMovesList);
        return validMovesList;
    }

    public ArrayList<Integer> knightMovements() {
        System.out.println("function called");
        ArrayList<Integer> validMovesList = new ArrayList<>(8);
        String currentCol = this.square.getCol();
        int currentColInt = ArrayUtils.indexOf(Constants.ALPHA, currentCol);
        int currentRow = this.square.getRow();

        ArrayList<String> columns = new ArrayList<>(2);
        ArrayList<Integer> rows = new ArrayList<>(2);
        columns.add("x");
        columns.add("x");
        rows.add(-1);
        rows.add(-1);

        if (!Objects.equals(currentCol, "a") && !Objects.equals(currentCol, "b")) {
            columns.set(0, Constants.ALPHA[currentColInt - 2]);
        }
        if (!Objects.equals(currentCol, "g") && !Objects.equals(currentCol, "h")) {
            columns.set(1, Constants.ALPHA[currentColInt + 2]);
        }
        if (currentRow >= 1) {
            rows.set(0, currentRow - 1);
        }
        if (currentRow <= 7) {
            rows.set(1, currentRow + 1);
        }

        for (int n : rows) {
            for (String s : columns) {
                int mx = coordsToIndex(s, n);
                if (mx != -1) {
                    if (Board.masterBoard[mx].piece != null) {
                        if (Board.masterBoard[mx].piece.white != this.white) {
                            validMovesList.add(mx);
                        }
                    } else {
                        validMovesList.add(mx);
                    }
                }
            }
        }

        ArrayList<String> columns2 = new ArrayList<>(2);
        ArrayList<Integer> rows2 = new ArrayList<>(2);
        columns2.add("x");
        columns2.add("x");
        rows2.add(-1);
        rows2.add(-1);

        if (!Objects.equals(currentCol, "a")) {
            columns2.set(0, Constants.ALPHA[currentColInt - 1]);
        }
        if (!Objects.equals(currentCol, "h")) {
            columns2.set(1, Constants.ALPHA[currentColInt + 1]);
        }
        if (currentRow > 1) {
            rows2.set(0, currentRow - 2);
        }
        if (currentRow < 7) {
            rows2.set(1, currentRow + 2);
        }

        for (int n : rows2) {
            for (String s : columns2) {
                int mx = coordsToIndex(s, n);
                if (mx != -1) {
                    if (Board.masterBoard[mx].piece != null) {
                        if (Board.masterBoard[mx].piece.white != this.white) {
                            validMovesList.add(mx);
                        }
                    } else {
                        validMovesList.add(mx);
                    }
                }
            }
        }

        System.out.println(validMovesList);
        return validMovesList;
    }

    public ArrayList<Integer> camelMovements() {
        System.out.println("function called");
        ArrayList<Integer> validMovesList = new ArrayList<>(8);
        String currentCol = this.square.getCol();
        int currentColInt = ArrayUtils.indexOf(Constants.ALPHA, currentCol);
        int currentRow = this.square.getRow();

        ArrayList<String> columns = new ArrayList<>(2);
        ArrayList<Integer> rows = new ArrayList<>(2);
        columns.add("x");
        columns.add("x");
        rows.add(-1);
        rows.add(-1);

        if (!Objects.equals(currentCol, "a") && !Objects.equals(currentCol, "b") && !Objects.equals(currentCol, "c")) {
            columns.set(0, Constants.ALPHA[currentColInt - 3]);
        }
        if (!Objects.equals(currentCol, "f") && !Objects.equals(currentCol, "g") && !Objects.equals(currentCol, "h")) {
            columns.set(1, Constants.ALPHA[currentColInt + 3]);
        }
        if (currentRow >= 1) {
            rows.set(0, currentRow - 1);
        }
        if (currentRow <= 7) {
            rows.set(1, currentRow + 1);
        }

        for (int n : rows) {
            for (String s : columns) {
                int mx = coordsToIndex(s, n);
                if (mx != -1) {
                    if (Board.masterBoard[mx].piece != null) {
                        if (Board.masterBoard[mx].piece.white != this.white) {
                            validMovesList.add(mx);
                        }
                    } else {
                        validMovesList.add(mx);
                    }
                }
            }
        }

        ArrayList<String> columns2 = new ArrayList<>(2);
        ArrayList<Integer> rows2 = new ArrayList<>(2);
        columns2.add("x");
        columns2.add("x");
        rows2.add(-1);
        rows2.add(-1);

        if (!Objects.equals(currentCol, "a")) {
            columns2.set(0, Constants.ALPHA[currentColInt - 1]);
        }
        if (!Objects.equals(currentCol, "h")) {
            columns2.set(1, Constants.ALPHA[currentColInt + 1]);
        }
        if (currentRow > 2) {
            rows2.set(0, currentRow - 3);
        }
        if (currentRow < 6) {
            rows2.set(1, currentRow + 3);
        }

        for (int n : rows2) {
            for (String s : columns2) {
                int mx = coordsToIndex(s, n);
                if (mx != -1) {
                    if (Board.masterBoard[mx].piece != null) {
                        if (Board.masterBoard[mx].piece.white != this.white) {
                            validMovesList.add(mx);
                        }
                    } else {
                        validMovesList.add(mx);
                    }
                }
            }
        }

        System.out.println(validMovesList);
        return validMovesList;
    }
    public boolean iteratorLogic(ArrayList<Integer> arrayList, int index) {
        if (index != -1) {
            if (Board.masterBoard[index].piece == null) {
                arrayList.add(index);
                return false;
            } else {
                if (Board.masterBoard[index].piece.white != this.white) {
                    arrayList.add(index);
                    return true;
                }
                return true;
            }
        } else {
            return true;
        }
    }

    public ArrayList<Integer> rookMovements() {
        ArrayList<Integer> validMovesList = new ArrayList<>(16);
        String currentCol = this.square.getCol();
        int currentColInt = ArrayUtils.indexOf(Constants.ALPHA, currentCol);
        int currentRow = this.square.getRow();


        while (currentRow + 1 <= Constants.NUM.length) {
            currentRow += 1;
            int mx = coordsToIndex(Constants.ALPHA[currentColInt], (currentRow));

            if (iteratorLogic(validMovesList, mx)) {
                break;
            }
        }
        currentRow = this.square.getRow(); //reset

        while (currentRow - 1 >= 0) {
            currentRow -= 1;
            int mx = coordsToIndex(Constants.ALPHA[currentColInt], (currentRow));

            if (iteratorLogic(validMovesList, mx)) {
                break;
            }
        }
        currentRow = this.square.getRow(); //reset

        while (currentColInt - 1 >= 0) {
            currentColInt -= 1;
            currentCol = Constants.ALPHA[currentColInt];
            int mx = coordsToIndex(currentCol, (currentRow));

            if (iteratorLogic(validMovesList, mx)) {
                break;
            }
        }
        currentCol = this.square.getCol(); //reset
        currentColInt = ArrayUtils.indexOf(Constants.ALPHA, currentCol); //reset

        while  (currentColInt + 1 < Constants.ALPHA.length) {
            currentColInt += 1;
            currentCol = Constants.ALPHA[currentColInt];
            int mx = coordsToIndex(currentCol, (currentRow));

            if (iteratorLogic(validMovesList, mx)) {
                break;
            }
        }
        currentCol = this.square.getCol(); //reset
        currentColInt = ArrayUtils.indexOf(Constants.ALPHA, currentCol); //reset

        System.out.println(validMovesList);
        return validMovesList;
    }

    public ArrayList<Integer> bishopMovements() {
        System.out.println("function called");
        ArrayList<Integer> validMovesList = new ArrayList<>(16);
        String currentCol = this.square.getCol();
        int currentColInt = ArrayUtils.indexOf(Constants.ALPHA, currentCol);
        int currentRow = this.square.getRow();

        //top left
        while ((currentRow + 1 <= Constants.NUM.length) && (currentColInt - 1 >= 0)) {
            currentRow += 1;
            currentColInt -= 1;
            currentCol = Constants.ALPHA[currentColInt];
            int mx = coordsToIndex(Constants.ALPHA[currentColInt], (currentRow));

            if (iteratorLogic(validMovesList, mx)) {
                break;
            }
        }
        currentCol = this.square.getCol();
        currentColInt = ArrayUtils.indexOf(Constants.ALPHA, currentCol);
        currentRow = this.square.getRow();
        System.out.println("first loop finished");

        //top right
        while ((currentRow + 1 <= Constants.NUM.length) && (currentColInt + 1 < Constants.ALPHA.length)) {
            currentRow += 1;
            currentColInt += 1;
            currentCol = Constants.ALPHA[currentColInt];
            int mx = coordsToIndex(Constants.ALPHA[currentColInt], (currentRow));

            if (iteratorLogic(validMovesList, mx)) {
                break;
            }
        }
        currentCol = this.square.getCol();
        currentColInt = ArrayUtils.indexOf(Constants.ALPHA, currentCol);
        currentRow = this.square.getRow();
        System.out.println("second loop finished");
        //bottom right
        while ((currentRow - 1 >= 0) && (currentColInt + 1 < Constants.ALPHA.length)) {
            currentRow -= 1;
            currentColInt += 1;
            currentCol = Constants.ALPHA[currentColInt];
            int mx = coordsToIndex(Constants.ALPHA[currentColInt], (currentRow));

            if (iteratorLogic(validMovesList, mx)) {
                break;
            }
        }
        currentCol = this.square.getCol();
        currentColInt = ArrayUtils.indexOf(Constants.ALPHA, currentCol);
        currentRow = this.square.getRow();
        System.out.println("third loop finished");
        //bottom left
        while ((currentRow - 1 >= 0) && (currentColInt - 1 >= 0)) {
            currentRow -= 1;
            currentColInt -= 1;
            currentCol = Constants.ALPHA[currentColInt];
            int mx = coordsToIndex(Constants.ALPHA[currentColInt], (currentRow));

            if (iteratorLogic(validMovesList, mx)) {
                break;
            }
        }
        currentCol = this.square.getCol();
        currentColInt = ArrayUtils.indexOf(Constants.ALPHA, currentCol);
        currentRow = this.square.getRow();
        System.out.println("fourth loop finished");

        System.out.println(validMovesList);
        return validMovesList;
    }
    public int coordsToIndex(String col, int row) {
        if ((Objects.equals(col, "x")) || row == -1) {
            return -1;
        } else {
            try {
                int alphaOffset = Arrays.asList(Constants.ALPHA).indexOf(col);
                return (Constants.NUM_REVERSED[row - 1] * 8) - 8 + alphaOffset;
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Not all moves on board");
                return -1;
            }
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
