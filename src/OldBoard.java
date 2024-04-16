import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class OldBoard {
    Square[] masterBoard = new Square[64];

    Piece[] pieceList;

    JPanel[] imagePieceList = new JPanel[64];
    JPanel[] imageSelectedList = new JPanel[64];
    JPanel[] imageAttackList = new JPanel[64];

    Square selectedSquare = null;

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
        //create the board panel and set its size
        JLayeredPane gameBoard = new JLayeredPane();
        gameBoard.setLayout(null);
        gameBoard.setBounds(0, 0, 800, 800);
        JPanel gameBoardButtons = new JPanel(new GridLayout(8, 8));
        JPanel imagePieces = new JPanel(new GridLayout(8, 8));
        JPanel imageSelect = new JPanel(new GridLayout(8, 8));
        JPanel imageAttack = new JPanel(new GridLayout(8, 8));

        setDimensions(gameBoard);
        setDimensions(gameBoardButtons);
        setDimensions(imagePieces);
        setDimensions(imageSelect);
        setDimensions(imageAttack);


        boolean white = true;
        int squareNumber = 0;
        for (int n : Constants.NUM_REVERSED) {
            for (String s : Constants.ALPHA) {
                //creates square object
                Square square = new Square(s, n);
                masterBoard[squareNumber] = square; //add square to board array


                //JPanel imagePiecePanel = new JPanel();
                //imagePieceList[squareNumber] = imagePiecePanel;
                //imagePieces.add(imagePiecePanel);

                JPanel imageSelectedPanel = new JPanel(new GridLayout(1,1));
                imageSelectedList[squareNumber] = imageSelectedPanel;
                imageSelectedPanel.setBounds(100, 100, 100, 100);
                imageSelectedPanel.setBackground(Constants.MEDIUMPINK);
                imagePieces.add(imageSelectedPanel);
                imageSelect.revalidate();
                imageSelect.repaint();
                gameBoard.repaint();
                gameBoard.revalidate();

                //JPanel imageAttackPanel = new JPanel();
                //imageAttackList[squareNumber] = imageAttackPanel;
                //imagePieces.add(imageAttackPanel);



                JButton squareButton = square.getButton();
                squareButton.setMinimumSize(new Dimension(100, 100)); //sets button size
                squareButton.setPreferredSize(new Dimension(100, 100)); //sets button size, both functions are needed
                squareButton.setFont(new Font("Constantia", Font.PLAIN, 12)); //sets button font
                squareButton.setText(square.getCol()+square.getRow()); //sets text to square's chess notation ID
                squareButton.setHorizontalAlignment(SwingConstants.LEFT); //aligns text to top left corner
                squareButton.setVerticalAlignment(SwingConstants.TOP);
                squareButton.setBorderPainted(false);
                squareButton.setFocusPainted(false);
                squareButton.setIconTextGap(0);
                //sets button colors
                if (white) {
                    squareButton.setBackground(Constants.WHITE);
                    squareButton.setForeground(Constants.BLACK);
                    white = false;
                } else {
                    squareButton.setBackground(Constants.BLACK);
                    squareButton.setForeground(Constants.WHITE);
                    white = true;
                }
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

                        BufferedImage I_select = null;
                        try {
                            I_select = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Assets/testing.png")));
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        //if (selectedSquare != null) {
                        //    selectedSquare.button.setIcon(null);
                        //}

                        selectedSquare = square;
                        //square.button.setIcon(new ImageIcon(I_select));
                        //square.button.setBackground(Constants.MEDIUMPINK);

                        JLabel picLabel = new JLabel();
                        picLabel.setMinimumSize(new Dimension(100, 100)); //sets button size
                        picLabel.setPreferredSize(new Dimension(100, 100)); //sets button size, both functions are needed
                        picLabel.setIcon(new ImageIcon(I_select));

                        picLabel.setHorizontalAlignment(SwingConstants.LEFT);
                        picLabel.setVerticalAlignment(SwingConstants.TOP);
                        imageSelectedList[finalSquareNumber].add(picLabel);
                        imageSelectedList[finalSquareNumber].setBackground(Constants.MEDIUMPINK);
                        imageSelectedList[finalSquareNumber].revalidate();
                        imageSelectedList[finalSquareNumber].repaint();
                        imageSelect.revalidate();
                        imageSelect.repaint();
                        gameBoardButtons.revalidate();
                        gameBoardButtons.repaint();
                        gameBoard.repaint();
                        gameBoard.revalidate();

                    }});
                BufferedImage I_select = null;
                try {
                    I_select = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Assets/testing.png")));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                JLabel picLabel = new JLabel();
                picLabel.setMinimumSize(new Dimension(100, 100)); //sets button size
                picLabel.setPreferredSize(new Dimension(100, 100)); //sets button size, both functions are needed
                picLabel.setIcon(new ImageIcon(I_select));

                picLabel.setHorizontalAlignment(SwingConstants.LEFT);
                picLabel.setVerticalAlignment(SwingConstants.TOP);
                imageSelectedList[finalSquareNumber].add(picLabel);
                imageSelectedList[finalSquareNumber].setBackground(Constants.MEDIUMPINK);
                imageSelectedList[finalSquareNumber].revalidate();
                imageSelectedList[finalSquareNumber].repaint();
                imageSelect.revalidate();
                imageSelect.repaint();


                squareNumber += 1;
            }
            white = !white; //offsets every loop to create checkerboard pattern
        }

        gameBoard.add(gameBoardButtons, 1);
        //gameBoard.add(imagePieces, 2);
        gameBoard.add(imageSelect, 0);
        //gameBoard.add(imageAttack, 0);
        //imagePieces.setOpaque(false);
        imageSelect.setOpaque(false);
        //imageAttack.setOpaque(false);
        imageSelect.setBackground(Constants.MEDIUMPINK);

        BufferedImage I_select = null;
        try {
            I_select = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Assets/testing.png")));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        JLabel picLabel = new JLabel();
        picLabel.setMinimumSize(new Dimension(100, 100)); //sets button size
        picLabel.setPreferredSize(new Dimension(100, 100)); //sets button size, both functions are needed
        picLabel.setIcon(new ImageIcon(I_select));
        imageSelect.add(picLabel);
        imageSelect.revalidate();
        imageSelect.repaint();

        gameBoardButtons.setBounds(0, 0, 800, 800);
        //imagePieces.setBounds(0, 0, 800, 800);
        imageSelect.setBounds(0, 0, 800, 800);
        //imageAttack.setBounds(0, 0, 800, 800);

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

    public void detectSquare(int col, int row) {

    }
}