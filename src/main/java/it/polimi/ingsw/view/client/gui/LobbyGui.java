package it.polimi.ingsw.view.client.gui;

import it.polimi.ingsw.model.player.Player;

import javax.swing.*;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import static it.polimi.ingsw.utils.ConstantsContainer.TEXT;
import static it.polimi.ingsw.view.client.gui.BackgroundButton.backgroundButton;
import static it.polimi.ingsw.view.client.gui.Board.internalFrameSetUp;
import static it.polimi.ingsw.view.client.gui.Gui.*;

/**
 * Class that build the JDesktopPane for the waiting lobby
 * @author Luigi Scibona
 * @version 1.0
 * @since 2020/06/13
 */

public class LobbyGui{
    Gui gui;
    Dimension frameSize = new Dimension();
    Dimension intFrameSize = new Dimension();
    private final JDesktopPane pane;
    MyButton backButton;
    MyButton confirm;
    JLabel lNumber;
    JLabel nicknameLabel;
    JTextField nickname = new JTextField(20);
    Style colorStyle;
    StyledDocument doc;
    Style numberStyle;
    MutableAttributeSet fontSize;
    StyledDocument numberDoc;
    int num;
    List<Player> players;

    /**
     * Public constructor
     * @param instance Reference to the Board class instanced by the GUI
     * @param dimensionFrame Size of the frame where the current JDesktopPane LobbyGui will be inserted
     * @param numberOfPlayer Number of Players in play
     * @param actualPlayers List of current Players in lobby
     * @throws IOException if the loading of the inscription was not successful
     */

    public LobbyGui(Gui instance, Dimension dimensionFrame, Integer numberOfPlayer, List<Player> actualPlayers) throws IOException {

        frameSize.setSize(dimensionFrame);
        gui = instance;
        num = numberOfPlayer;
        players = actualPlayers;
        pane = new JDesktopPane();
        intFrameSize.setSize(frameSize.getWidth() * 40/100, frameSize.getHeight() * 45/100);
        pane.setPreferredSize(frameSize);
        pane.setLayout(null);

        JInternalFrame popUp = new JInternalFrame("", false, false, false, false);
        confirm = new MyButton(0);
        popUp.add(confirm);
        nicknameLabel = new JLabel("nickname");
        nickname.setBounds((int) (intFrameSize.getWidth() * 40/100), (int) (intFrameSize.getHeight() * 35/100), intFrameSize.width * 20/100,intFrameSize.height * 3/100);
        nickname.setText("Nickname");
        popUp.add(nickname);
        nicknameLabel.setBounds((int) (Gui.getD().getWidth() * 24.5/100), (int) (Gui.getD().getHeight() * 34/100), Gui.getD().width * 15/100,Gui.getD().height * 5/100);
        popUp.add(nicknameLabel);
        popUp.setPreferredSize(intFrameSize);
        internalFrameSetUp(popUp);
        BasicInternalFrameUI bii = (BasicInternalFrameUI) popUp.getUI();
        bii.setNorthPane(null);
        popUp.setVisible(false);
        pane.add(popUp);

        backButton = new MyButton(1);
        backButton.setBounds((int) (getD().getWidth() * 43.5 / 100), (int) (getD().getHeight() * 70 / 100), d.width * 13/100, d.height * 5/100);
        backButton.setEnabled(true);
        backButton.addActionListener(new BackToLogin());
        pane.add(backButton);

        JLabel wait = ImageHandler.setImage(TEXT + "waiting_others_players.png", 100, 100, frameSize.width * 30/100, frameSize.height * 10/100);
        JLabel of = ImageHandler.setImage(TEXT + "of.png", 100, 100, frameSize.width * 5/100, frameSize.height * 5/100);
        JTextPane textPane = new JTextPane();
        JTextPane numberPane = new JTextPane();
        doc = textPane.getStyledDocument();
        numberDoc = numberPane.getStyledDocument();


        wait.setBounds(frameSize.width * 35/100, frameSize.height * 10/100, frameSize.width * 30/100, frameSize.height * 10/100);
        pane.add(wait);


        if (num == 2){
            lNumber = ImageHandler.setImage(TEXT + "2.png", 100, 100, frameSize.width * 5/100, frameSize.height * 5/100);
        }
        else {
            lNumber = ImageHandler.setImage(TEXT + "3.png", 100, 100, frameSize.width * 5/100, frameSize.height * 5/100);
        }


        of.setBounds(frameSize.width * 47/100, frameSize.height * 20/100, frameSize.width * 5/100, frameSize.height * 5/100);
        of.setFont(Gui.getFelixBold());
        pane.add(of);
        lNumber.setBounds(frameSize.width * 52/100, frameSize.height * 20/100, frameSize.width * 5/100, frameSize.height * 5/100);
        pane.add(lNumber);


        textPane.setBounds((frameSize.width * 30/100), frameSize.height * 43/100, frameSize.width * 40/100, frameSize.height * 20/100);
        textPane.setOpaque(false);
        textPane.setFont(Gui.getFelixBold());
        pane.add(textPane);
        colorStyle = textPane.addStyle("colorStyle", null);
        StyleConstants.setAlignment(colorStyle, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), colorStyle, false);

        numberPane.setBounds((frameSize.width * 42/100), frameSize.height * 20/100, frameSize.width * 5/100, frameSize.height * 5/100);
        numberPane.setOpaque(false);
        numberPane.setFont(Gui.getFelixBold());
        pane.add(numberPane);
        fontSize = numberPane.getInputAttributes();
        numberStyle = numberPane.addStyle("colorStyle", null);
        StyleConstants.setAlignment(numberStyle, StyleConstants.ALIGN_CENTER);
        StyleConstants.setForeground(numberStyle, Color.BLACK);
        StyleConstants.setFontSize(fontSize, 30);
        numberDoc.setParagraphAttributes(0, numberDoc.getLength(), numberStyle, false);

        stamp(players);


        JButton backgroundButton = backgroundButton(0);
        backgroundButton.setBounds(0, 0, getD().width, getD().height);
        pane.add(backgroundButton);
    }

    /**
     * Class that implements ActionListener which brings back in the login
     */

    private class BackToLogin implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            gui.backToLogin(false, false);
            gui.onBackCommand();
        }
    }

    /**
     * Method to print the current Players in the lobby
     * @param players List of current Players in the lobby
     */

    public void stamp(List<Player> players){
        clean();
        for (Player player : players){
            if(player.getColor().equals(it.polimi.ingsw.model.player.Color.BLUE)){
                StyleConstants.setForeground(colorStyle, Color.BLUE);
            }
            else if(player.getColor().equals(it.polimi.ingsw.model.player.Color.WHITE)){
                StyleConstants.setForeground(colorStyle, Color.WHITE);
            }
            else {
                StyleConstants.setForeground(colorStyle, Color.MAGENTA);
            }
            try{
                doc.insertString(doc.getLength(), player.getNickName() + "\n \n", colorStyle);
            }catch (BadLocationException e){
               LOGGER.severe("InsertString Failed");
            }
        }
        number(players.size());
    }

    /**
     * Method to clean the TextPane for new printing
     */

    private void clean(){
        try {
            doc.remove(0, doc.getLength());
        } catch (BadLocationException e) {
            LOGGER.severe(e.getMessage());
        }
        try {
            numberDoc.remove(0, numberDoc.getLength());
        } catch (BadLocationException e) {
            LOGGER.severe(e.getMessage());
        }
    }

    /**
     * Method for setting the current number of Players in the lobby
     * @param size Current number of Players
     */

    private void number(int size){
        String value;
        if (size == 1){
            value = "1";
        }
        else if (size == 2){
            value = "2";
        }
        else{
            value = "3";
        }
        try {
            numberDoc.insertString(numberDoc.getLength(), value, fontSize);
        } catch (BadLocationException e) {
            LOGGER.severe(e.getMessage());
        }
    }

    /**
     * Getter for LobbyGui's JDesktopPane
     * @return JDesktopPane of LobbyGui
     */

    public JDesktopPane getPane() {
        return pane;
    }
}
