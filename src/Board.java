import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Board {
    Square[] masterBoard = new Square[64];

    Piece[] pieceList;

    JPanel[] imagePieceList = new JPanel[64];
    JPanel[] imageSelectList = new JPanel[64];
    JPanel[] imageAttackList = new JPanel[64];

    JLabel[] imagePieceLabels = new JLabel[64];
    JLabel[] imageSelectLabels = new JLabel[64];
    JLabel[] imageAttackLabels = new JLabel[64];

    Square selectedSquare = null;
    int selectedSquareNumber = -1;

    public Square[] getMasterBoard() {
        return masterBoard;
    }
    public void setMasterBoard(Square[] masterBoard) {
        this.masterBoard = masterBoard;
    }
    public void printMasterBoard() {
        for (Square square : masterBoard) {
            square.printSquare();
        }
    }

    public JLayeredPane draw_board() {
        JPopupMenu.setDefaultLightWeightPopupEnabled(false);
        ToolTipManager.sharedInstance().setLightWeightPopupEnabled(false);
        //create the board panel and set its size
        JLayeredPane gameBoard = new JLayeredPane();
        gameBoard.setLayout(null);
        gameBoard.setBounds(0, 0, 800, 800);
        JPanel gameBoardButtons = new JPanel(new GridLayout(8, 8));
        JPanel imagePiece = new JPanel(new GridLayout(8, 8));
        JPanel imageSelect = new JPanel(new GridLayout(8, 8));
        JPanel imageAttack = new JPanel(new GridLayout(8, 8));

        setDimensions(gameBoard);
        setDimensions(gameBoardButtons);
        setDimensions(imagePiece);
        setDimensions(imageSelect);
        setDimensions(imageAttack);

        boolean white = true;
        int squareNumber = 0;
        for (int n : Constants.NUM_REVERSED) {
            for (String s : Constants.ALPHA) {
                //creates square object
                Square square = new Square(s, n);
                masterBoard[squareNumber] = square; //add square to board array

                BufferedImage selectIMG = null;
                BufferedImage attackIMG = null;
                BufferedImage pieceIMG = null;
                try {
                    selectIMG = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Assets/select.png")));
                    attackIMG = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Assets/attack.png")));
                    pieceIMG = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Assets/piece.png")));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                JLabel imageSelectLabel = setBoardPanels(imageSelect);
                imageSelectLabels[squareNumber] = imageSelectLabel;

                JLabel imageAttackLabel = setBoardPanels(imageAttack, attackIMG);
                imageAttackLabels[squareNumber] = imageAttackLabel;

                JLabel imagePieceLabel = setBoardPanels(imagePiece, pieceIMG);
                imagePieceLabels[squareNumber] = imagePieceLabel;


                JButton squareButton = square.getButton();
                squareButton.setMinimumSize(new Dimension(100, 100)); //sets button size
                squareButton.setPreferredSize(new Dimension(100, 100)); //sets button size, both functions are needed
                squareButton.setFont(new Font("Constantia", Font.PLAIN, 12)); //sets button font
                squareButton.setText(square.getCol()+square.getRow()); //sets text to square's chess notation ID
                squareButton.setHorizontalAlignment(SwingConstants.LEFT); //aligns text to top left corner
                squareButton.setVerticalAlignment(SwingConstants.TOP);
                squareButton.setBorderPainted(false);
                squareButton.setFocusPainted(false);
                squareButton.setContentAreaFilled(false);
                squareButton.setIconTextGap(0);
                //sets button colors
                if (white) {
                //    squareButton.setBackground(Constants.WHITE);
                //    squareButton.setForeground(Constants.BLACK);
                    white = false;
                } else {
                //    squareButton.setBackground(Constants.BLACK);
                //    squareButton.setForeground(Constants.WHITE);
                    white = true;
                }
                squareButton.setOpaque(false);
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
                        square.printSquare();

                        updateSelected(finalSquareNumber);
                        selectedSquare = square;
                        selectedSquareNumber = finalSquareNumber;

                    }});

                squareNumber += 1;
            }
            white = !white; //offsets every loop to create checkerboard pattern
        }
        gameBoard.add(gameBoardButtons, 0);
        gameBoard.add(imageSelect, 1);
        gameBoard.add(imageAttack, 2);
        gameBoard.add(imagePiece, 3);

        gameBoardButtons.setOpaque(false);
        imagePiece.setOpaque(false);
        imageSelect.setOpaque(false);
        imageAttack.setOpaque(false);
        gameBoardButtons.setBounds(0, 0, 800, 800);
        imageSelect.setBounds(0, 0, 800, 800);
        imageAttack.setBounds(0, 0, 800, 800);
        imagePiece.setBounds(0, 0, 800, 800);

        gameBoard.repaint();
        gameBoard.revalidate();

        return gameBoard;
    }
    public void setDimensions(JPanel panel) {
        panel.setMinimumSize(new Dimension(800, 800)); //sets button size
        panel.setPreferredSize(new Dimension(800, 800)); //sets button size, both functions are needed
    }
    public void setDimensions(JLayeredPane pane) {
        pane.setMinimumSize(new Dimension(800, 800)); //sets button size
        pane.setPreferredSize(new Dimension(800, 800)); //sets button size, both functions are needed
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
    public JLabel setBoardPanels(JPanel panel, Color black, Color white) {
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

    public void clearIcons(JLabel[] list) {
        for (JLabel label : list) {
            label.setIcon(null);
        }
    }
    public void updateSelected(int newSelectedNumber) {
        if (newSelectedNumber == selectedSquareNumber) {
            imageSelectLabels[selectedSquareNumber].setIcon(null);
            selectedSquareNumber = -1;
            System.out.println(selectedSquareNumber);
            return;
        }
        if (selectedSquareNumber != -1) {
            imageSelectLabels[selectedSquareNumber].setIcon(null);
            System.out.println(selectedSquareNumber);
        }
        imageSelectLabels[newSelectedNumber].setIcon(new ImageIcon(Constants.selectIMG));
    }

    public void updateBoardPanels(JPanel panel) {

    }

    public void detectSquare(int col, int row) {

    }
}
