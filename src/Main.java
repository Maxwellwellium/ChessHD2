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
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;


public class Main extends JFrame{

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
        try {
            soundPlayer.playSound("Assets/bach.wav", true);
        } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
            throw new RuntimeException(ex);
        }
        ////////////////////////////////////////////////////////

        BufferedImage I_background = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Assets/background.png")));
        BufferedImage I_logo = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Assets/logo.png")));
        BufferedImage trueIMG = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Assets/true.png")));
        BufferedImage falseIMG = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Assets/false.png")));
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

        JButton gamePlayFlip_button = new JButton(); //flips which side's turn it is
        JButton gameGenFlip_button = new JButton(); //flips which side's pieces are generated
        gamePlayFlip_button.setIcon(new ImageIcon(trueIMG));
        gameGenFlip_button.setIcon(new ImageIcon(trueIMG));
        JLabel gamePlayFlip_label = new JLabel("White to Play");
        JLabel gameGenFlip_label = new JLabel("Generate White");
        JPanel gamePlayFlip_panel = new JPanel(new GridBagLayout());
        JPanel gameGenFlip_panel = new JPanel(new GridBagLayout());
        JLabel gameTurnCounter = new JLabel("Turn 0");

        //board setters
        JButton gameGenNew_button = new JButton(); //generates a new blank chess board
        JButton gameGenReg_button = new JButton(); //generates a new regular chess board
        JButton gameC1_button = new JButton(); //
        JButton gameC2_button = new JButton(); //
        JButton gameC3_button = new JButton(); //
        JButton gameC4_button = new JButton(); //
        JButton gameC5_button = new JButton(); //
        JButton gameC6_button = new JButton(); //
        JButton gameC7_button = new JButton(); //
        JButton gameC8_button = new JButton(); //

        JLabel gameGenNew_label = new JLabel("Clear"); //
        JLabel gameGenReg_label = new JLabel("Chess"); //
        JLabel gameC1_label = new JLabel("16 Pawns"); //
        JLabel gameC2_label = new JLabel("Skirmish"); //
        JLabel gameC3_label = new JLabel("Pawn Endgame"); //
        JLabel gameC4_label = new JLabel("Corner Rook"); //
        JLabel gameC5_label = new JLabel("Camel Eater"); //
        JLabel gameC6_label = new JLabel("Sittuyin"); //
        JLabel gameC7_label = new JLabel("Horde"); //
        JLabel gameC8_label = new JLabel("Brine"); //

        JPanel gameNewReg_panel = new JPanel(new GridBagLayout());
        JPanel game12_panel = new JPanel(new GridBagLayout());
        JPanel game34_panel = new JPanel(new GridBagLayout());
        JPanel game56_panel = new JPanel(new GridBagLayout());
        JPanel game78_panel = new JPanel(new GridBagLayout());

        gamePlayFlip_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Play Sound
                try {
                    soundPlayer.playSound("Assets/menuSwitch.wav", false);
                } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
                    throw new RuntimeException(ex);
                }
                boolean newState = !Board.isPlayWhite(); //toggles
                Board.setPlayWhite(!Board.isPlayWhite());
                if (newState) {
                    gamePlayFlip_label.setText("White to Play");
                    gamePlayFlip_button.setIcon(new ImageIcon(trueIMG));
                } else {
                    gamePlayFlip_label.setText("Black to Play");
                    gamePlayFlip_button.setIcon(new ImageIcon(falseIMG));
                }
            }
        });
        board.getTurnLabel().addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                boolean newState = !Board.isPlayWhite();
                if (newState) {
                    gamePlayFlip_label.setText("Black to Play");
                    gamePlayFlip_button.setIcon(new ImageIcon(falseIMG));
                } else {
                    gamePlayFlip_label.setText("White to Play");
                    gamePlayFlip_button.setIcon(new ImageIcon(trueIMG));
                }
            }
        });
        gameGenFlip_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Play Sound
                try {
                    soundPlayer.playSound("Assets/menuSwitch.wav", false);
                } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
                    throw new RuntimeException(ex);
                }
                boolean newState = !board.isGenWhite(); //toggles
                board.setGenWhite(newState);

                if (newState) {
                    gameGenFlip_label.setText("Generate White");
                    gameGenFlip_button.setIcon(new ImageIcon(trueIMG));
                } else {
                    gameGenFlip_label.setText("Generate Black");
                    gameGenFlip_button.setIcon(new ImageIcon(falseIMG));
                }
            }
        });


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
        flipButtonSetup(gamePlayFlip_button, gamePlayFlip_panel);
        flipButtonSetup(gameGenFlip_button, gameGenFlip_panel);
        flipButtonSetup(gamePlayFlip_label, gamePlayFlip_panel);
        flipButtonSetup(gameGenFlip_label, gameGenFlip_panel);

        universalButtonSetup(board.getTurnLabel(), gameButtons);
        universalButtonSetup(gamePlayFlip_panel, gameButtons);
        universalButtonSetup(gameGenFlip_panel, gameButtons);
        gamePlayFlip_panel.setBackground(Constants.BLACK); //override universal color
        gameGenFlip_panel.setBackground(Constants.BLACK); //override universal color
        universalButtonSetup(gameEnterPiece, gameButtons);

        challengeButtonSetup(gameGenNew_button, gameGenNew_label, gameGenReg_button, gameGenReg_label, gameNewReg_panel);
        challengeButtonSetup(gameC1_button, gameC1_label, gameC2_button, gameC2_label, game12_panel);
        challengeButtonSetup(gameC3_button, gameC3_label, gameC4_button, gameC4_label, game34_panel);
        challengeButtonSetup(gameC5_button, gameC5_label, gameC6_button, gameC6_label, game56_panel);
        challengeButtonSetup(gameC7_button, gameC7_label, gameC8_button, gameC8_label, game78_panel);
        challengeButtonSetup(gameNewReg_panel, gameButtons);
        challengeButtonSetup(game12_panel, gameButtons);
        challengeButtonSetup(game34_panel, gameButtons);
        challengeButtonSetup(game56_panel, gameButtons);
        challengeButtonSetup(game78_panel, gameButtons);

        JButton[] challengelist = new JButton[8];
        challengelist[0] = gameC1_button;
        challengelist[1] = gameC2_button;
        challengelist[2] = gameC3_button;
        challengelist[3] = gameC4_button;
        challengelist[4] = gameC5_button;
        challengelist[5] = gameC6_button;
        challengelist[6] = gameC7_button;
        challengelist[7] = gameC8_button;
        int x = 0;

        for (JButton button : challengelist) {
            x += 1;
            int finalX = x;
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Play Sound
                    try {
                        soundPlayer.playSound("Assets/menuSwitch.wav", false);
                    } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
                        throw new RuntimeException(ex);
                    }
                    System.out.println("beginning challenge: " + finalX);
                    try {
                        board.setBoard(finalX);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            });
        }

        universalButtonSetup(gameBack_button, gameButtons);

        addChangeListener(gameEnterPiece, e -> {
            try {
                boolean reset = board.determineInput(gameEnterPiece.getText());
                if (reset) {
                    gameEnterPiece.setText(null); //reset text after successful input
                }
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
                //resets board for new game
                board.resetBoard();
                remove(screenMain);
                add(screenGame);
                revalidate();
                repaint();
                //enable ability to resume current game
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
                board.resetBoard();
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
                try {
                    board.setBoard(0);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
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
    public void flipButtonSetup(JButton component, JPanel panel) {
        component.setBackground(Constants.BLACK); //sets button background color
        component.setForeground(Constants.WHITE); //set button font color
        component.setMinimumSize(new Dimension(40, 20));
        component.setMaximumSize(new Dimension(40, 20));
        component.setPreferredSize(new Dimension(40, 20)); //sets button size
        component.setFont(new Font("Constantia", Font.BOLD, 30)); //sets button font
        component.setBorderPainted(false);
        component.setFocusPainted(false);
        component.setContentAreaFilled(false);
        component.setIconTextGap(0);

        //sets button placement constraints within button JPanel
        GridBagConstraints constraint = new GridBagConstraints();
        constraint.gridx = 0; //button placement aligned with leftmost column
        constraint.gridy = GridBagConstraints.RELATIVE; //button placement aligned underneath previous button
        constraint.insets = new Insets(30, 0, 30, 0); //padding between button
        panel.add(component, constraint); //adds button to panel
    }
    public void flipButtonSetup(JLabel label, JPanel panel) {
        label.setBackground(Constants.BLACK); //sets button background color
        label.setForeground(Constants.WHITE); //set button font color
        label.setMinimumSize(new Dimension(230, 80));
        label.setMaximumSize(new Dimension(230, 80));
        label.setPreferredSize(new Dimension(230, 80)); //sets button size
        label.setFont(new Font("Constantia", Font.BOLD, 30)); //sets button font

        //sets button placement constraints within button JPanel
        GridBagConstraints constraint = new GridBagConstraints();
        constraint.gridx = GridBagConstraints.RELATIVE; //button placement aligned with leftmost column
        constraint.gridy = 0; //button placement aligned underneath previous button
        constraint.insets = new Insets(15, 15, 0, 0); //padding between button
        panel.add(label, constraint); //adds button to panel
    }

    public void challengeButtonSetup(JButton button1, JLabel label1, JButton button2, JLabel label2, JPanel panel) throws IOException {
        BufferedImage challengeIMG = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Assets/challengeButton.png")));
        button1.setBackground(Constants.BLACK); //sets button background color
        button1.setForeground(Constants.WHITE); //set button font color
        button1.setMinimumSize(new Dimension(30, 20));
        button1.setMaximumSize(new Dimension(30, 20));
        button1.setPreferredSize(new Dimension(30, 20)); //sets button size
        button1.setBorderPainted(false);
        button1.setFocusPainted(false);
        button1.setContentAreaFilled(false);
        button1.setIconTextGap(0);
        button1.setIcon(new ImageIcon(challengeIMG));

        button2.setBackground(Constants.BLACK); //sets button background color
        button2.setForeground(Constants.WHITE); //set button font color
        button2.setMinimumSize(new Dimension(30, 20));
        button2.setMaximumSize(new Dimension(30, 20));
        button2.setPreferredSize(new Dimension(30, 20)); //sets button size
        button2.setBorderPainted(false);
        button2.setFocusPainted(false);
        button2.setContentAreaFilled(false);
        button2.setIconTextGap(0);
        button2.setIcon(new ImageIcon(challengeIMG));

        //sets button placement constraints within button JPanel
        GridBagConstraints constraint = new GridBagConstraints();
        constraint.gridx = GridBagConstraints.RELATIVE; //button placement aligned with leftmost column
        constraint.gridy = 0; //button placement aligned underneath previous button
        constraint.insets = new Insets(10, 15, 10, 0); //padding between button

        panel.add(button1, constraint); //adds button to panel
        panel.add(label1, challengeButtonSetup(label1));
        panel.add(button2, constraint); //adds button to panel
        panel.add(label2, challengeButtonSetup(label2));
    }
    public GridBagConstraints challengeButtonSetup(JLabel label) {
        label.setBackground(Constants.BLACK); //sets button background color
        label.setForeground(Constants.WHITE); //set button font color
        label.setMinimumSize(new Dimension(110, 40));
        label.setMaximumSize(new Dimension(110, 40));
        label.setPreferredSize(new Dimension(110, 40)); //sets button size
        label.setFont(new Font("Constantia", Font.BOLD, 20)); //sets button font

        //sets button placement constraints within button JPanel
        GridBagConstraints constraint = new GridBagConstraints();
        constraint.gridx = GridBagConstraints.RELATIVE; //button placement aligned with leftmost column
        constraint.gridy = 0; //button placement aligned underneath previous button
        constraint.insets = new Insets(15, 8, 0, 0); //padding between button
        return constraint;
    }
    public void challengeButtonSetup(JPanel panel, JPanel game) {
        panel.setBackground(Constants.BLACK); //sets button background color
        panel.setForeground(Constants.WHITE); //set button font color
        panel.setMinimumSize(new Dimension(350, 40));
        panel.setMaximumSize(new Dimension(350, 40));
        panel.setPreferredSize(new Dimension(350, 40)); //sets button size
        //sets button placement constraints within button JPanel
        GridBagConstraints constraint = new GridBagConstraints();
        constraint.gridx = 0; //button placement aligned with leftmost column
        constraint.gridy = GridBagConstraints.RELATIVE; //button placement aligned underneath previous button
        constraint.insets = new Insets(10, 0, 0, 0); //padding between button
        game.add(panel, constraint); //adds button to panel
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