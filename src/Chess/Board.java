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
import java.util.Random;
import java.util.Vector;

public class Board {
    static Square[] masterBoard = new Square[64];
    JLabel[] imagePieceLabels = new JLabel[64];
    JLabel[] imageSelectLabels = new JLabel[64];
    JLabel[] imageAttackLabels = new JLabel[64];
    Square selectedSquare = null;
    Piece selectedPiece = null;
    int selectedSquareNumber = -1;
    boolean genWhite = true;
    static boolean playWhite = true;
    public int turn = 0;
    JLabel turnLabel = new JLabel("Turn 0");
    public JLabel getTurnLabel() {
        return turnLabel;
    }

    public boolean isGenWhite() {
        return genWhite;
    }
    public void setGenWhite(boolean genWhite) {
        this.genWhite = genWhite;
    }
    public static boolean isPlayWhite() {
        return playWhite;
    }
    public static void setPlayWhite(boolean playWhite) {
        Board.playWhite = playWhite;
    }
    public static Square[] getMasterBoard() {
        return masterBoard;
    }
    public void setMasterBoard(Square[] masterBoard) {
        Board.masterBoard = masterBoard;
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

    public void resetBoard() {
        selectedSquare = null;
        selectedPiece = null;
        selectedSquareNumber = -1;
        for (Square square : masterBoard) {
            square.setPiece(null);
        }
        clearIcons(imageAttackLabels);
        clearIcons(imagePieceLabels);
        clearIcons(imageSelectLabels);
        System.out.println("Board cleared");
    }
    public void setBoard(boolean regular) throws IOException {
        resetBoard();

        //BLACK SIDE
        //pawns
        for (String s : Constants.ALPHA) {
            spawnPiece("p", s, 7, false);
        }
        //rooks
        spawnPiece("r", "a", 8, false);
        spawnPiece("r", "h", 8, false);
        //knights
        spawnPiece("n", "b", 8, false);
        spawnPiece("n", "g", 8, false);
        //bishops
        spawnPiece("b", "c", 8, false);
        spawnPiece("b", "f", 8, false);
        //queen and king
        spawnPiece("q", "d", 8, false);
        spawnPiece("k", "e", 8, false);
        //WHITE SIDE
        //pawns
        for (String s : Constants.ALPHA) {
            spawnPiece("p", s, 2, true);
        }
        //rooks
        spawnPiece("r", "a", 1, true);
        spawnPiece("r", "h", 1, true);
        //knights
        spawnPiece("n", "b", 1, true);
        spawnPiece("n", "g", 1, true);
        //bishops
        spawnPiece("b", "c", 1, true);
        spawnPiece("b", "f", 1, true);
        //queen and king
        spawnPiece("q", "d", 1, true);
        spawnPiece("k", "e", 1, true);
    }
    public void setBoard() {

    }
    public boolean determineInput(String string) throws IOException {
        char[] inputs = string.toCharArray();

        if (string.length() == 3) {
            String piece = String.valueOf(inputs[0]).toLowerCase();
            String column = String.valueOf(inputs[1]).toLowerCase();
            String row = String.valueOf(inputs[2]).toLowerCase();
            if (!(Arrays.asList(Constants.PIECES).contains(piece))) {
                System.out.println("Piece Not Recognized!");
                return false;
            }
            if (!(Arrays.asList(Constants.ALPHA).contains(column))) {
                System.out.println("Not a Valid File!");
                return false;
            }
            if (!(Arrays.asList(Constants.NUMSTRING).contains(row))) {
                System.out.println("Not a Valid Rank!");
                return false;
            }
            System.out.println("Placing piece at coords");

            spawnPiece(piece, column, Integer.parseInt(row), genWhite);
            return true;
        }
        return false;
    }
    public void spawnPiece(String type, String col, int row, boolean color) throws IOException {
        int masterIndex = coordsToIndex(col, row);

        Piece piece = switch (type) {
            case "a", "A" -> new Amazon(color, masterBoard[masterIndex]);
            case "b", "B" -> new Bishop(color, masterBoard[masterIndex]);
            case "c", "C" -> new Camel(color, masterBoard[masterIndex]);
            case "e", "E" -> new Cameleater(color, masterBoard[masterIndex]);
            case "k", "K" -> new King(color, masterBoard[masterIndex]);
            case "n", "N" -> new Knight(color, masterBoard[masterIndex]);
            case "p", "P" -> new Pawn(color, masterBoard[masterIndex]);
            case "q", "Q" -> new Queen(color, masterBoard[masterIndex]);
            case "r", "R" -> new Rook(color, masterBoard[masterIndex]);
            case null, default -> new Brine(color, masterBoard[masterIndex]);
        };

        boolean exists = checkSpawnSquare(piece, masterIndex);
        if (!exists) {
            //spawn piece on board
            masterBoard[masterIndex].setPiece(piece);
            imagePieceLabels[masterIndex].setIcon(new ImageIcon(piece.getImage()));
            System.out.println(piece.getClass() + " created successfully at index " + masterIndex);
        } else {
            //delete the piece
            masterBoard[masterIndex].setPiece(null);
            imagePieceLabels[masterIndex].setIcon(null);
            System.out.println(piece.getClass() + " deleted at " + masterIndex);
        }
    }
    public boolean checkSpawnSquare(Piece piece, int masterIndex) {
        if (masterBoard[masterIndex].getPiece() != null) {
            if (masterBoard[masterIndex].getPiece().getClass() == piece.getClass()) {
                return masterBoard[masterIndex].getPiece().isWhite() == piece.isWhite();
            }
        }
        return false;
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
    public void updateAttack(Piece piece) {
        clearIcons(imageAttackLabels);
        if (selectedPiece != null) {
            for (Object i : selectedPiece.Movements()) {
                int attackSquareIndexes = (int) i;
                if (attackSquareIndexes != -1) {
                    imageAttackLabels[attackSquareIndexes].setIcon(new ImageIcon(Constants.attackIMG)); //set icon of new selected square
                }
            }
        } else {
            for (JLabel label : imageAttackLabels) {
                label.setIcon(null);
            }
        }
    }
    public void updateSelected(int newSelectedNumber, Square square) {
        //if the same square is clicked multiple times, toggle whether it's selected or not
        if (newSelectedNumber == selectedSquareNumber) {
            if (selectedSquareNumber != -1) {
                //unselects the square
                imageSelectLabels[selectedSquareNumber].setIcon(null); //reset icon
                selectedSquareNumber = -1; //reset selected square number
                selectedSquare = null; //deselect square
                selectedPiece = null; //deselect piece
                updateAttack(selectedPiece); //update valid moves overlay
                System.out.println("square unselected");
            } else {
                //reselects the square
                imageSelectLabels[newSelectedNumber].setIcon(new ImageIcon(Constants.selectIMG)); //set icon of new selected square
                selectedSquare = square; //update variables
                selectedPiece = selectedSquare.piece; //select piece
                updateAttack(selectedPiece); //update valid moves overlay
                selectedSquareNumber = newSelectedNumber; //update variables
                System.out.println("Selected " + selectedSquare.col + selectedSquare.row);
            }
            return;
        }
        //if different square is clicked, make it the new selected square
        if (selectedSquareNumber != -1) {
            imageSelectLabels[selectedSquareNumber].setIcon(null); //reset icon of old selected square
        }

        //if in attacklist
        if (imageAttackLabels[newSelectedNumber].getIcon() != null) {

            if (masterBoard[newSelectedNumber].piece != null) {
                //brine dodging movement, due to randomness brine has 1/9 chance not to dodge
                if (masterBoard[newSelectedNumber].piece.getClass() == Brine.class) {
                    int escapeChoice = new Random().nextInt(masterBoard[newSelectedNumber].piece.escapeMovements().size()); //choose escape square
                    int escapeSquare = (int) masterBoard[newSelectedNumber].piece.escapeMovements().get(escapeChoice);
                    captureSound(masterBoard[selectedSquareNumber], masterBoard[newSelectedNumber], !(escapeSquare == selectedSquareNumber));
                    masterBoard[escapeSquare].piece = masterBoard[newSelectedNumber].piece; //put brine on escape square
                    imagePieceLabels[escapeSquare].setIcon(new ImageIcon(masterBoard[newSelectedNumber].piece.getImage())); //set brine image
                    }

                //cameleater logic
                if ((masterBoard[selectedSquareNumber].piece.getClass() == Knight.class
                        || masterBoard[selectedSquareNumber].piece.getClass() == Camel.class)
                        && masterBoard[newSelectedNumber].piece.getClass() == Cameleater.class) {
                    captureSound(masterBoard[selectedSquareNumber], masterBoard[newSelectedNumber], false);
                } else {
                    if (masterBoard[newSelectedNumber].piece.getClass() != Brine.class) {
                        captureSound(masterBoard[selectedSquareNumber], masterBoard[newSelectedNumber], false);
                    }
                    masterBoard[newSelectedNumber].piece = selectedPiece;
                    imagePieceLabels[newSelectedNumber].setIcon(new ImageIcon(selectedPiece.getImage()));
                    //update square of piece
                    selectedPiece.square = masterBoard[newSelectedNumber];
                    selectedPiece.moved = true;
                }
            } else {
                //move sound goes here
                masterBoard[newSelectedNumber].piece = selectedPiece;
                imagePieceLabels[newSelectedNumber].setIcon(new ImageIcon(selectedPiece.getImage()));
                //update square of piece
                selectedPiece.square = masterBoard[newSelectedNumber];
                selectedPiece.moved = true;
            }
            //old squares piece is null
            masterBoard[selectedSquareNumber].piece = null;
            imagePieceLabels[selectedSquareNumber].setIcon(null);
            //deselect square
            selectedSquare = null;
            selectedSquareNumber = -1;
            //deselect piece
            selectedPiece = null;
            //update valid moves overlay
            updateAttack(selectedPiece);
            clearIcons(imageAttackLabels);
            System.out.println("Piece moved, Square unselected");
            playWhite = !playWhite;
            turn += 1;
            turnLabel.setText("Turn: " + turn);
            //playWhite = !playWhite;

        } else {
            //if not in attacklist
            imageSelectLabels[newSelectedNumber].setIcon(new ImageIcon(Constants.selectIMG)); //set icon of new selected square
            selectedSquare = square; //update variables
            selectedPiece = selectedSquare.piece; //deselect piece
            updateAttack(selectedPiece); //update valid moves overlay
            selectedSquareNumber = newSelectedNumber; //update variables
            System.out.println("Selected " + selectedSquare.col + selectedSquare.row);
        }
    }
    public void captureSound(Square startSquare, Square endSquare, boolean briney) {
        SoundPlayer soundPlayer = new SoundPlayer();
        String soundFile = "Assets/defaultcapture.wav";

        if (endSquare.piece != null) {
            Piece capturer = startSquare.piece;
            Piece captured = endSquare.piece;

            if (capturer.getClass() == King.class) {
                soundFile = switch (captured.getClassID()) {
                    //case 0 -> "Assets/defaultcapture.wav"; // King
                    //case 1 -> "Assets/defaultcapture.wav"; // Amazon
                    //case 2 -> "Assets/defaultcapture.wav"; // Queen
                    //case 3 -> "Assets/defaultcapture.wav"; // Rook
                    //case 4 -> "Assets/defaultcapture.wav"; // Camel Eater
                    //case 5 -> "Assets/defaultcapture.wav"; // Camel
                    //case 6 -> "Assets/defaultcapture.wav"; // Knight
                    //case 7 -> "Assets/defaultcapture.wav"; // Bishop
                    //case 8 -> "Assets/defaultcapture.wav"; // Pawn
                    case 9 -> { // Brine
                        if (briney) {yield "Assets/dodgesuccess.wav";}
                        else { yield "Assets/dodgefail.wav";}}
                    default -> "Assets/defaultcapture.wav";
                };
            } else if (capturer.getClass() == Amazon.class) {
                soundFile = switch (captured.getClassID()) {
                    //case 0 -> "Assets/defaultcapture.wav"; // King
                    //case 1 -> "Assets/defaultcapture.wav"; // Amazon
                    //case 2 -> "Assets/defaultcapture.wav"; // Queen
                    //case 3 -> "Assets/defaultcapture.wav"; // Rook
                    //case 4 -> "Assets/defaultcapture.wav"; // Camel Eater
                    //case 5 -> "Assets/defaultcapture.wav"; // Camel
                    //case 6 -> "Assets/defaultcapture.wav"; // Knight
                    //case 7 -> "Assets/defaultcapture.wav"; // Bishop
                    //case 8 -> "Assets/defaultcapture.wav"; // Pawn
                    case 9 -> { // Brine
                        if (briney) {yield "Assets/dodgesuccess.wav";}
                        else { yield "Assets/dodgefail.wav";}}
                    default -> "Assets/defaultcapture.wav";
                };
            } else if (capturer.getClass() == Queen.class) {
                soundFile = switch (captured.getClassID()) {
                    //case 0 -> "Assets/defaultcapture.wav"; // King
                    //case 1 -> "Assets/defaultcapture.wav"; // Amazon
                    //case 2 -> "Assets/defaultcapture.wav"; // Queen
                    //case 3 -> "Assets/defaultcapture.wav"; // Rook
                    //case 4 -> "Assets/defaultcapture.wav"; // Camel Eater
                    //case 5 -> "Assets/defaultcapture.wav"; // Camel
                    //case 6 -> "Assets/defaultcapture.wav"; // Knight
                    //case 7 -> "Assets/defaultcapture.wav"; // Bishop
                    //case 8 -> "Assets/defaultcapture.wav"; // Pawn
                    case 9 -> { // Brine
                        if (briney) {yield "Assets/dodgesuccess.wav";}
                        else { yield "Assets/dodgefail.wav";}}
                    default -> "Assets/defaultcapture.wav";
                };
            } else if (capturer.getClass() == Rook.class) {
                soundFile = switch (captured.getClassID()) {
                    //case 0 -> "Assets/defaultcapture.wav"; // King
                    //case 1 -> "Assets/defaultcapture.wav"; // Amazon
                    //case 2 -> "Assets/defaultcapture.wav"; // Queen
                    //case 3 -> "Assets/defaultcapture.wav"; // Rook
                    //case 4 -> "Assets/defaultcapture.wav"; // Camel Eater
                    //case 5 -> "Assets/defaultcapture.wav"; // Camel
                    //case 6 -> "Assets/defaultcapture.wav"; // Knight
                    //case 7 -> "Assets/defaultcapture.wav"; // Bishop
                    //case 8 -> "Assets/defaultcapture.wav"; // Pawn
                    case 9 -> { // Brine
                        if (briney) {yield "Assets/dodgesuccess.wav";}
                        else { yield "Assets/dodgefail.wav";}}
                    default -> "Assets/defaultcapture.wav";
                };
            } else if (capturer.getClass() == Cameleater.class) {
                soundFile = switch (captured.getClassID()) {
                    //case 0 -> "Assets/defaultcapture.wav"; // King
                    //case 1 -> "Assets/defaultcapture.wav"; // Amazon
                    //case 2 -> "Assets/defaultcapture.wav"; // Queen
                    //case 3 -> "Assets/defaultcapture.wav"; // Rook
                    //case 4 -> "Assets/defaultcapture.wav"; // Camel Eater
                    case 5 -> "Assets/cameleater_eats.wav"; // Camel
                    case 6 -> "Assets/cameleater_eats.wav"; // Knight
                    //case 7 -> "Assets/defaultcapture.wav"; // Bishop
                    //case 8 -> "Assets/defaultcapture.wav"; // Pawn
                    case 9 -> { // Brine
                        if (briney) {yield "Assets/dodgesuccess.wav";}
                        else { yield "Assets/cameleater_eats.wav";}}
                    default -> "Assets/defaultcapture.wav";
                };
            } else if (capturer.getClass() == Camel.class) {
                soundFile = switch (captured.getClassID()) {
                    //case 0 -> "Assets/defaultcapture.wav"; // King
                    //case 1 -> "Assets/defaultcapture.wav"; // Amazon
                    //case 2 -> "Assets/defaultcapture.wav"; // Queen
                    //case 3 -> "Assets/defaultcapture.wav"; // Rook
                    case 4 -> "Assets/cameleater_eats.wav"; // Camel Eater
                    //case 5 -> "Assets/defaultcapture.wav"; // Camel
                    //case 6 -> "Assets/defaultcapture.wav"; // Knight
                    //case 7 -> "Assets/defaultcapture.wav"; // Bishop
                    //case 8 -> "Assets/defaultcapture.wav"; // Pawn
                    case 9 -> { // Brine
                        if (briney) {yield "Assets/dodgesuccess.wav";}
                        else { yield "Assets/dodgefail.wav";}}
                    default -> "Assets/defaultcapture.wav";
                };
            } else if (capturer.getClass() == Knight.class) {
                soundFile = switch (captured.getClassID()) {
                    //case 0 -> "Assets/defaultcapture.wav"; // King
                    //case 1 -> "Assets/defaultcapture.wav"; // Amazon
                    //case 2 -> "Assets/defaultcapture.wav"; // Queen
                    //case 3 -> "Assets/defaultcapture.wav"; // Rook
                    case 4 -> "Assets/cameleater_eats.wav"; // Camel Eater
                    //case 5 -> "Assets/defaultcapture.wav"; // Camel
                    //case 6 -> "Assets/defaultcapture.wav"; // Knight
                    //case 7 -> "Assets/defaultcapture.wav"; // Bishop
                    //case 8 -> "Assets/defaultcapture.wav"; // Pawn
                    case 9 -> { // Brine
                        if (briney) {yield "Assets/dodgesuccess.wav";}
                        else { yield "Assets/dodgefail.wav";}}
                    default -> "Assets/defaultcapture.wav";
                };
            } else if (capturer.getClass() == Bishop.class) {
                soundFile = switch (captured.getClassID()) {
                    //case 0 -> "Assets/defaultcapture.wav"; // King
                    //case 1 -> "Assets/defaultcapture.wav"; // Amazon
                    //case 2 -> "Assets/defaultcapture.wav"; // Queen
                    //case 3 -> "Assets/defaultcapture.wav"; // Rook
                    //case 4 -> "Assets/defaultcapture.wav"; // Camel Eater
                    //case 5 -> "Assets/defaultcapture.wav"; // Camel
                    //case 6 -> "Assets/defaultcapture.wav"; // Knight
                    //case 7 -> "Assets/defaultcapture.wav"; // Bishop
                    //case 8 -> "Assets/defaultcapture.wav"; // Pawn
                    case 9 -> { // Brine
                        if (briney) {yield "Assets/dodgesuccess.wav";}
                        else { yield "Assets/dodgefail.wav";}}
                    default -> "Assets/defaultcapture.wav";
                };
            } else if (capturer.getClass() == Pawn.class) {
                soundFile = switch (captured.getClassID()) {
                    //case 0 -> "Assets/defaultcapture.wav"; // King
                    //case 1 -> "Assets/defaultcapture.wav"; // Amazon
                    //case 2 -> "Assets/defaultcapture.wav"; // Queen
                    //case 3 -> "Assets/defaultcapture.wav"; // Rook
                    //case 4 -> "Assets/defaultcapture.wav"; // Camel Eater
                    //case 5 -> "Assets/defaultcapture.wav"; // Camel
                    //case 6 -> "Assets/defaultcapture.wav"; // Knight
                    //case 7 -> "Assets/defaultcapture.wav"; // Bishop
                    //case 8 -> "Assets/defaultcapture.wav"; // Pawn
                    case 9 -> { // Brine
                        if (briney) {yield "Assets/dodgesuccess.wav";}
                        else { yield "Assets/dodgefail.wav";}}
                    default -> "Assets/defaultcapture.wav";
                };
            } else { // Brine
                soundFile = switch (captured.getClassID()) {
                    //case 0 -> "Assets/defaultcapture.wav"; // King
                    //case 1 -> "Assets/defaultcapture.wav"; // Amazon
                    //case 2 -> "Assets/defaultcapture.wav"; // Queen
                    //case 3 -> "Assets/defaultcapture.wav"; // Rook
                    //case 4 -> "Assets/defaultcapture.wav"; // Camel Eater
                    //case 5 -> "Assets/defaultcapture.wav"; // Camel
                    //case 6 -> "Assets/defaultcapture.wav"; // Knight
                    //case 7 -> "Assets/defaultcapture.wav"; // Bishop
                    //case 8 -> "Assets/defaultcapture.wav"; // Pawn
                    case 9 -> { // Brine
                        if (briney) {yield "Assets/dodgesuccess.wav";}
                        else { yield "Assets/dodgefail.wav";}}
                    default -> "Assets/defaultcapture.wav";
                };
            }
            try {
                soundPlayer.playSound(soundFile, false);
            } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
                throw new RuntimeException(ex);
            }
        }
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
