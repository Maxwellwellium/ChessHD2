package Chess;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;

public class Board {
    Square[] masterBoard = new Square[64];

    JPanel[] imagePieceList = new JPanel[64];
    JPanel[] imageSelectList = new JPanel[64];
    JPanel[] imageAttackList = new JPanel[64];

    JLabel[] imagePieceLabels = new JLabel[64];
    JLabel[] imageSelectLabels = new JLabel[64];
    JLabel[] imageAttackLabels = new JLabel[64];

    Square selectedSquare = null;
    Piece selectedPiece = null;
    int selectedSquareNumber = -1;

    public Square[] getMasterBoard() {
        return masterBoard;
    }
    public void setMasterBoard(Square[] masterBoard) {
        this.masterBoard = masterBoard;
    }
    public void printMasterBoard() {
        for (Square square : masterBoard) {
            square.printSquareInfo();
        }
    }

    public JLayeredPane draw_board() {
        JPopupMenu.setDefaultLightWeightPopupEnabled(false);
        ToolTipManager.sharedInstance().setLightWeightPopupEnabled(false);
        //create the board panel and set its size
        JLayeredPane gameBoard = new JLayeredPane();
        gameBoard.setLayout(null);
        JPanel gameBoardButtons = new JPanel(new GridLayout(8, 8));
        JPanel imageSelect = new JPanel(new GridLayout(8, 8));
        JPanel imageAttack = new JPanel(new GridLayout(8, 8));
        JPanel imagePiece = new JPanel(new GridLayout(8, 8));
        JPanel checkerBoard = new JPanel(new GridLayout(8, 8));

        setDimensions(gameBoard, 8);
        setDimensions(gameBoardButtons, 8);
        setDimensions(imageSelect, 8);
        setDimensions(imageAttack, 8);
        setDimensions(imagePiece, 8);
        setDimensions(checkerBoard, 8);

        boolean white = true;
        int squareNumber = 0;
        for (int n : Constants.NUM_REVERSED) {
            for (String s : Constants.ALPHA) {
                //creates square object
                Square square = new Square(s, n);
                masterBoard[squareNumber] = square; //add square to board array

                JLabel imageSelectLabel = setBoardPanels(imageSelect);
                imageSelectLabels[squareNumber] = imageSelectLabel;
                JLabel imageAttackLabel = setBoardPanels(imageAttack);
                imageAttackLabels[squareNumber] = imageAttackLabel;
                JLabel imagePieceLabel = setBoardPanels(imagePiece);
                imagePieceLabels[squareNumber] = imagePieceLabel;

                white = setCheckerboard(checkerBoard, white, Constants.BLACK, Constants.WHITE);

                JButton squareButton = setButton(square);
                gameBoardButtons.add(squareButton); //adds button to JPanel
                int finalSquareNumber = squareNumber;
                squareButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        SoundPlayer soundPlayer = new SoundPlayer();
                        // Play Sound
                        try {
                            soundPlayer.playSound("Assets/squareSelect.wav", false);
                        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
                            throw new RuntimeException(ex);
                        }

                        updateSelected(finalSquareNumber, square); // update the selected square
                    }});
                squareNumber += 1;
            }
            white = !white; //offsets every loop to create checkerboard pattern
        }
        //add all JPanels as layers to the gameBoard layered pane. smallest index is put above higher indexed layers
        gameBoard.add(gameBoardButtons, 0);
        gameBoard.add(imageSelect, 1);
        gameBoard.add(imageAttack, 2);
        gameBoard.add(imagePiece, 3);
        gameBoard.add(checkerBoard, 4);
        return gameBoard;
    }
    public void determineInput(String string) throws IOException {
        char[] inputs = string.toCharArray();

        if (string.length() == 3) {
            if (!(Arrays.asList(Constants.PIECES).contains(String.valueOf(inputs[0])))) {
                System.out.println("Piece Not Recognized!");
                return;
            }
            if (!(Arrays.asList(Constants.ALPHA).contains(String.valueOf(inputs[1])))) {
                System.out.println("Not a Valid File!");
                return;
            }
            if (!(Arrays.asList(Constants.NUMSTRING).contains(String.valueOf(inputs[2])))) {
                System.out.println("Not a Valid Rank!");
                return;
            }
            System.out.println("Placing piece at coords");

            spawnPiece(String.valueOf(inputs[0]), String.valueOf(inputs[1]), Integer.parseInt(String.valueOf(inputs[2])));
        }
    }
    public void spawnPiece(String type, String col, int row) throws IOException {
        int masterIndex = coordsToIndex(col, row);

        Piece piece = switch (type) {
            case "a", "A" -> new Amazon(true, masterBoard[masterIndex]);
            case "b", "B" -> new Bishop(true, masterBoard[masterIndex]);
            case "c", "C" -> new Camel(true, masterBoard[masterIndex]);
            case "e", "E" -> new Cameleater(true, masterBoard[masterIndex]);
            case "k", "K" -> new King(true, masterBoard[masterIndex]);
            case "n", "N" -> new Knight(true, masterBoard[masterIndex]);
            case "p", "P" -> new Pawn(true, masterBoard[masterIndex]);
            case "q", "Q" -> new Queen(true, masterBoard[masterIndex]);
            case "r", "R" -> new Rook(true, masterBoard[masterIndex]);
            case null, default -> new Brine(true, masterBoard[masterIndex]);
        };

        boolean spawned = checkSpawnSquare(piece, masterIndex);

        masterBoard[masterIndex].setPiece(piece); //in future check if piece is here

        imagePieceLabels[masterIndex].setIcon(new ImageIcon(piece.getImage())); // store img differently to refer to dynamically
        System.out.println(piece.getClass() +" created successfully at index " + masterIndex);
    }

    public boolean checkSpawnSquare(Piece piece, int masterIndex) {
        if (masterBoard[masterIndex].getPiece() != null) {
            if (masterBoard[masterIndex].getPiece().getClass() == piece.getClass()) {
                //delete the piece if exists on square
                masterBoard[masterIndex].setPiece(null);
                imagePieceLabels[masterIndex].setIcon(null);
                return false;
            }
            //override previous piece
            //masterBoard[masterIndex].setPiece(piece);
        }
        masterBoard[masterIndex].setPiece(piece);
        return true;
    }

    public int coordsToIndex(String col, int row) {
        int alphaOffset = Arrays.asList(Constants.ALPHA).indexOf(col);
        return (Constants.NUM_REVERSED[row-1] * 8) - 8 + alphaOffset;
    }
    public int coordsToIndex(String col, String row) {
        int alphaOffset = Arrays.asList(Constants.ALPHA).indexOf(col);
        return (Constants.NUM_REVERSED[Integer.parseInt(row)-1] * 8) - 8 + alphaOffset;
    }
    public Vector<Object> indexToCoords(int index) {
        int row = Math.floorDiv(index, 8);
        String col = Constants.ALPHA[index % 8];
        Vector<Object> coords = new Vector<>(2);
        coords.add(col);
        coords.add(row);
        return coords;
    }
    public JButton setButton(Square square) {
        JButton squareButton = square.getButton();
        setDimensions(squareButton, 1);
        squareButton.setFont(new Font("Constantia", Font.PLAIN, 12)); //sets button font
        squareButton.setText(square.getCol()+square.getRow()); //sets text to square's chess notation ID
        squareButton.setForeground(Constants.MEDIUMPINK);
        squareButton.setHorizontalAlignment(SwingConstants.LEFT); //aligns text to top left corner
        squareButton.setVerticalAlignment(SwingConstants.TOP);
        squareButton.setBorderPainted(false);
        squareButton.setFocusPainted(false);
        squareButton.setContentAreaFilled(false);
        squareButton.setIconTextGap(0);
        return squareButton;
    }
    public JLabel setBoardPanels(JPanel panel, BufferedImage image) {
        JPanel subPanel = new JPanel(new GridLayout(1,1));
        JLabel subLabel = new JLabel();
        subLabel.setIcon(new ImageIcon(image));
        subLabel.setOpaque(false);
        subPanel.setOpaque(false);
        subPanel.add(subLabel);
        subPanel.revalidate();
        subPanel.repaint();
        panel.add(subPanel);
        return subLabel;
    }
    public JLabel setBoardPanels(JPanel panel) {
        JPanel subPanel = new JPanel(new GridLayout(1,1));
        JLabel subLabel = new JLabel();
        subLabel.setOpaque(false);
        subPanel.setOpaque(false);
        subPanel.add(subLabel);
        subPanel.revalidate();
        subPanel.repaint();
        panel.add(subPanel);
        return subLabel;
    }
    public boolean setCheckerboard(JPanel panel, boolean color, Color black, Color white) {
        JPanel subPanel = new JPanel(new GridLayout(1,1));
        if (color) {
            subPanel.setBackground(white);
            subPanel.setForeground(black);
            color = false;
        } else {
            subPanel.setBackground(black);
            subPanel.setForeground(white);
            color = true;
        }
        panel.add(subPanel);
        return color;
    }
    public void clearIcons(JLabel[] list) {
        for (JLabel label : list) {
            label.setIcon(null);
        }
    }
    public void updateSelected(int newSelectedNumber, Square square) {
        //if the same square is clicked multiple times, toggle whether it's selected or not
        if (newSelectedNumber == selectedSquareNumber) {
            if (selectedSquareNumber != -1) {
                imageSelectLabels[selectedSquareNumber].setIcon(null); //reset icon
                selectedSquareNumber = -1; //reset selected square number
                selectedSquare = null; //deselect square
                System.out.println("no selected square");
            } else {
                imageSelectLabels[newSelectedNumber].setIcon(new ImageIcon(Constants.selectIMG)); //set icon of new selected square
                selectedSquare = square; //update variables
                selectedSquareNumber = newSelectedNumber; //update variables
                System.out.println("Selected " +selectedSquare.col+selectedSquare.row);
            }
            return;
        }
        //if different square is clicked, make it the new selected square
        if (selectedSquareNumber != -1) {
            imageSelectLabels[selectedSquareNumber].setIcon(null); //reset icon
        }
        imageSelectLabels[newSelectedNumber].setIcon(new ImageIcon(Constants.selectIMG)); //set icon of new selected square
        selectedSquare = square; //update variables
        selectedSquareNumber = newSelectedNumber; //update variables
        System.out.println("Selected " +selectedSquare.col+selectedSquare.row);
    }
    public void setDimensions(JComponent component, int factor) {
        component.setMinimumSize(new Dimension(factor * 100, factor * 100)); //sets button size
        component.setPreferredSize(new Dimension(factor * 100, factor * 100)); //sets button size, both functions are needed
        component.setOpaque(false); //makes component transparent
        if (!(component instanceof JButton)) {
            component.setBounds(0, 0, 800, 800);
        }
    }
}