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
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Vector;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;


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
        JButton gameGenNew_button = new JButton("Generate Blank Board"); //generates a new blank chess board
        JButton gameGenReg_button = new JButton("Generate Chess Board"); //generates a new regular chess board


        universalButtonSetup(mainNew_button, mainButtons);
        universalButtonSetup(mainResume_button, mainButtons);
        mainResume_button.setEnabled(false); //starts unselected
        universalButtonSetup(mainSettings_button, mainButtons);
        universalButtonSetup(mainQuit_button, mainButtons);
        universalButtonSetup(pauseBackGame_button, pauseButtons);
        pauseBackGame_button.setEnabled(false); //starts unselected
        universalButtonSetup(pauseBackMenu_button, pauseButtons);
        universalButtonSetup(pauseQuit_button, pauseButtons);
        universalButtonSetup(gameBack_button, gameButtons);
        universalButtonSetup(gameGenNew_button, gameButtons);
        universalButtonSetup(gameGenReg_button, gameButtons);

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

                getContentPane().removeAll();
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

                getContentPane().removeAll();
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

                getContentPane().removeAll();
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
                getContentPane().removeAll();
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
                getContentPane().removeAll();
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
                getContentPane().removeAll();
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
                if ((keyCode == KeyEvent.VK_ESCAPE) && (!screenPause.isVisible())) {
                    // bring up pause menu when esc is pressed
                    // Play Sound
                    try {
                        soundPlayer.playSound("Assets/menuSwitch.wav", false);
                    } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
                        throw new RuntimeException(ex);
                    }

                    getContentPane().removeAll();
                    add(screenPause);
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
        board.printMasterBoard();

    }

    public void universalButtonSetup(JButton button, JPanel panel) {
        button.setBackground(Constants.DARKPURPLE); //sets button background color
        button.setForeground(Constants.WHITE); //set button font color
        button.setPreferredSize(new Dimension(300, 80)); //sets button size
        button.setFont(new Font("Constantia", Font.BOLD, 30)); //sets button font

        //sets button placement constraints within button JPanel
        GridBagConstraints constraint = new GridBagConstraints();
        constraint.gridx = 0; //button placement aligned with leftmost column
        constraint.gridy = GridBagConstraints.RELATIVE; //button placement aligned underneath previous button
        constraint.insets = new Insets(15, 0, 0, 0); //padding between button
        panel.add(button, constraint); //adds button to panel
    }

    public static void main(String[] args) throws IOException {
        Main main = new Main();
        main.setVisible(true);
    }
}