import Chess.Board;
import Chess.Constants;
import Chess.Piece;
import Chess.SoundPlayer;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;


public class Main extends JFrame{

    Piece[] pieceList;

    public Main() throws IOException {
        // GENERAL GUI PARAMETERS
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1200, 830); //height offsets top bar
        setLocationRelativeTo(null);
        setTitle("CHESSHD JAVA");
        setIconImage(ImageIO.read(Objects.requireNonNull(getClass().getResource("/Assets/bishop.png"))));
        setResizable(false);
        setVisible(true);
        /////////////////

        Board board = new Board();
        SoundPlayer soundPlayer = new SoundPlayer();

        // BACKGROUND MUSIC
        //try {
        //    soundPlayer.playSound("Assets/bach.wav", true);
        //} catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
        //    throw new RuntimeException(ex);
        //}
        ////////////////////////////////////////////////////////

        BufferedImage I_background = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Assets/background.png")));
        BufferedImage I_logo = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Assets/logo.png")));
        BufferedImage I_bishop = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Assets/bishop.png")));
        // MAJOR UI ELEMENTS
        JPanel screenMain = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(I_background, 0, 0, null);
                g.drawImage(I_logo, 660, 40, null);
            }
        };
        JPanel screenPause = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
            }
        };
        JPanel screenGame = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
            }
        };

        JLayeredPane gameBoard = board.draw_board();
        //JPanel gameBoard = board.draw_board();

        // BUTTON PANELS
        JPanel mainButtons = new JPanel(new GridBagLayout());
        JPanel pauseButtons = new JPanel(new GridBagLayout());
        JPanel gameButtons = new JPanel(new GridBagLayout());
        //Make components transparent
        mainButtons.setOpaque(false);
        pauseButtons.setOpaque(false);
        gameButtons.setOpaque(false);


        // BUTTONS
        JButton mainNew_button = new JButton("Begin New Game"); //sets/resets board and sends player to game screen
        JButton mainResume_button = new JButton("Resume Game"); //sends player to game screen if it exists
        JButton mainSettings_button = new JButton("Settings"); //sends player to pause screen
        JButton mainQuit_button = new JButton("Quit Game"); //quits application

        JButton pauseBackGame_button = new JButton("Back to Game"); //sends player back to game if it exists
        JButton pauseBackMenu_button = new JButton("Back to Menu"); //sends player back to main menu
        JButton pauseQuit_button = new JButton("Quit Game"); //quits application

        JButton gameBack_button = new JButton("Settings"); //sends player to pause screen
        JButton gameGenNew_button = new JButton("Generate Blank Pieces.Board"); //generates a new blank chess board
        JButton gameGenReg_button = new JButton("Generate Chess Pieces.Board"); //generates a new regular chess board

        JFormattedTextField gameEnterPiece = new JFormattedTextField();


        universalButtonSetup(mainNew_button, mainButtons);
        universalButtonSetup(mainResume_button, mainButtons);
        mainResume_button.setEnabled(false); //starts unselected
        universalButtonSetup(mainSettings_button, mainButtons);
        universalButtonSetup(mainQuit_button, mainButtons);
        universalButtonSetup(pauseBackGame_button, pauseButtons);
        pauseBackGame_button.setEnabled(false); //starts unselected
        universalButtonSetup(pauseBackMenu_button, pauseButtons);
        universalButtonSetup(pauseQuit_button, pauseButtons);
        universalButtonSetup(gameEnterPiece, gameButtons);
        universalButtonSetup(gameGenNew_button, gameButtons);
        universalButtonSetup(gameGenReg_button, gameButtons);
        universalButtonSetup(gameBack_button, gameButtons);

        addChangeListener(gameEnterPiece, e -> {
            try {
                board.determineInput(gameEnterPiece.getText());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        // mainNew_Button
        mainNew_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Play Sound
                try {
                    soundPlayer.playSound("Assets/menuSwitch.wav", false);
                } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
                    throw new RuntimeException(ex);
                }

                //function that resets board for future

                remove(screenMain);
                add(screenGame);
                revalidate();
                repaint();

                //enable ability to resume game
                mainResume_button.setEnabled(true);
                pauseBackGame_button.setEnabled(true);
            }
        });
        /////////////////////////////////////////////////////////////
        // mainResume_button
        mainResume_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Play Sound
                try {
                    soundPlayer.playSound("Assets/menuSwitch.wav", false);
                } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
                    throw new RuntimeException(ex);
                }

                remove(screenMain);
                add(screenGame);
                revalidate();
                repaint();
            }
        });
        ////////////////////////////////////////////////////////////////
        // mainSettings_button
        mainSettings_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Play Sound
                try {
                    soundPlayer.playSound("Assets/menuSwitch.wav", false);
                } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
                    throw new RuntimeException(ex);
                }

                remove(screenMain);
                add(screenPause);
                revalidate();
                repaint();
            }
        });
        //////////////////////////////////////////////////////////////////
        // mainQuit_button
        mainQuit_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Play Sound
                try {
                    soundPlayer.playSound("Assets/quit.wav", false);
                } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
                    throw new RuntimeException(ex);
                }

                // put Save method here for future

                System.exit(0);
            }
        });
        //////////////////////////////////////////////////////////////
        // pauseBackGame_button
        pauseBackGame_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Play Sound
                try {
                    soundPlayer.playSound("Assets/menuSwitch.wav", false);
                } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
                    throw new RuntimeException(ex);
                }
                remove(screenPause);
                add(screenGame);
                revalidate();
                repaint();
            }
        });
        ///////////////////////////////////////////////////////////////////
        // pauseBackMenu_button
        pauseBackMenu_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Play Sound
                try {
                    soundPlayer.playSound("Assets/menuSwitch.wav", false);
                } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
                    throw new RuntimeException(ex);
                }
                remove(screenPause);
                add(screenMain);
                revalidate();
                repaint();
            }
        });
        ///////////////////////////////////////////////////////////////////
        // pauseQuit_button
        pauseQuit_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Play Sound
                try {
                    soundPlayer.playSound("Assets/quit.wav", false);
                } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
                    throw new RuntimeException(ex);
                }

                // put Save method here for future

                System.exit(0);
            }
        });
        ///////////////////////////////////////////////////////////////
        // gameBack_button
        gameBack_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Play Sound
                try {
                    soundPlayer.playSound("Assets/menuSwitch.wav", false);
                } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
                    throw new RuntimeException(ex);
                }
                remove(screenGame);
                add(screenPause);
                revalidate();
                repaint();
            }
        });
        //////////////////////////////////////////////////////////////
        // gameGenNew_button
        gameGenNew_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Play Sound
                try {
                    soundPlayer.playSound("Assets/menuSwitch.wav", false);
                } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
                    throw new RuntimeException(ex);
                }
                //reset board as blank
            }
        });
        ////////////////////////////////////////////////////////////////
        // gameGenReg_button
        gameGenReg_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Play Sound
                try {
                    soundPlayer.playSound("Assets/menuSwitch.wav", false);
                } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
                    throw new RuntimeException(ex);
                }
                //reset board as regular chess
            }
        });
        ////////////////////////////////////////////////////////////////

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                // bring up pause menu when esc is pressed
                if (keyCode == KeyEvent.VK_ESCAPE) {
                    // Play Sound
                    try {
                        soundPlayer.playSound("Assets/menuSwitch.wav", false);
                    } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
                        throw new RuntimeException(ex);
                    }
                    //stores whether last screen was the game or the menu
                    boolean prevScreenGame = Arrays.asList(getContentPane().getComponents()).contains(screenGame);

                    if (!(Arrays.asList(getContentPane().getComponents()).contains(screenPause))) {
                        if (prevScreenGame) {
                            remove(screenGame);
                        } else {
                            remove(screenMain);
                        }
                        add(screenPause); // show pause screen
                    } else {
                        remove(screenPause);
                        if (prevScreenGame) {
                            add(screenGame);
                        } else {
                            add(screenMain);
                        }
                    }
                    revalidate();
                    repaint();
                }
            }
        });
        ////////////////////////////////////////////

        GridBagConstraints mainC = new GridBagConstraints();
        mainC.anchor = GridBagConstraints.SOUTHWEST;
        mainC.fill = 8;
        mainC.gridx = 40;
        mainC.gridy = GridBagConstraints.RELATIVE;
        mainC.gridheight = 40;
        mainC.gridwidth = 40;
        mainC.weightx = 10;
        mainC.weighty = 10;
        mainC.insets = new Insets(0, 18, 18, 0);


        screenMain.add(mainButtons, mainC);
        screenPause.add(pauseButtons, mainC);
        screenGame.add(gameButtons, mainC);
        GridBagConstraints gameC = new GridBagConstraints();
        gameC.anchor = GridBagConstraints.EAST;
        screenGame.add(gameBoard, gameC);

        screenGame.setBackground(Constants.BLACK);
        screenPause.setBackground(Constants.BLACK);
        screenMain.setBackground(Constants.BLACK);
        add(screenMain);
        //////////////////////////////////////////////////////////////////////////
        //board.printMasterBoard();

    }

    public void universalButtonSetup(JComponent component, JPanel panel) {
        component.setBackground(Constants.DARKPURPLE); //sets button background color
        component.setForeground(Constants.WHITE); //set button font color
        component.setMinimumSize(new Dimension(350, 80));
        component.setMaximumSize(new Dimension(350, 80));
        component.setPreferredSize(new Dimension(350, 80)); //sets button size
        component.setFont(new Font("Constantia", Font.BOLD, 30)); //sets button font

        //sets button placement constraints within button JPanel
        GridBagConstraints constraint = new GridBagConstraints();
        constraint.gridx = 0; //button placement aligned with leftmost column
        constraint.gridy = GridBagConstraints.RELATIVE; //button placement aligned underneath previous button
        constraint.insets = new Insets(15, 0, 0, 0); //padding between button
        panel.add(component, constraint); //adds button to panel
    }


    //addChangeListener made by Boann @ Stackoverflow (https://stackoverflow.com/questions/3953208/value-change-listener-to-jtextfield)
    public static void addChangeListener(JTextField text, ChangeListener changeListener) {
        Objects.requireNonNull(text);
        Objects.requireNonNull(changeListener);
        DocumentListener dl = new DocumentListener() {
            private int lastChange = 0, lastNotifiedChange = 0;

            @Override
            public void insertUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                lastChange++;
                SwingUtilities.invokeLater(() -> {
                    if (lastNotifiedChange != lastChange) {
                        lastNotifiedChange = lastChange;
                        changeListener.stateChanged(new ChangeEvent(text));
                    }
                });
            }
        };
        text.addPropertyChangeListener("document", (PropertyChangeEvent e) -> {
            Document d1 = (Document)e.getOldValue();
            Document d2 = (Document)e.getNewValue();
            if (d1 != null) d1.removeDocumentListener(dl);
            if (d2 != null) d2.addDocumentListener(dl);
            dl.changedUpdate(null);
        });
        Document d = text.getDocument();
        if (d != null) d.addDocumentListener(dl);
    }

    public static void main(String[] args) throws IOException {
        Main main = new Main();
        main.setVisible(true);
    }
}