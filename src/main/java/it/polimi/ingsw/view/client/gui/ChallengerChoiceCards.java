package it.polimi.ingsw.view.client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.utils.ConstantsContainer.MUSIC;
import static it.polimi.ingsw.utils.ConstantsContainer.TEXT;
import static it.polimi.ingsw.view.client.gui.BackgroundButton.backgroundButton;
import static it.polimi.ingsw.view.client.gui.GuiUtils.*;
import static it.polimi.ingsw.view.client.gui.Gui.*;

/**
 * Class that extends JDesktopPane for the build of the pane that make the Challenger choose the gods
 * @author Luigi Scibona
 * @version 1.0
 * @since 2020/06/13
 */

public class ChallengerChoiceCards extends JDesktopPane{

    transient Gui gui;
    transient Board board;
    Dimension frameSize = new Dimension();
    Dimension intFrameSize = new Dimension();
    Dimension cardSize = new Dimension();
    Dimension size30x10 = new Dimension();
    JInternalFrame intFrame;
    JInternalFrame guiIntFrame;
    JButton buttonBackground = new JButton();
    JLabel cover =new JLabel();
    private int count = 0;
    private int chosen = 0;
    private final int numberPlayers;
    private final List<JButton> godChosen = new ArrayList<>();
    MyButton confirm = new MyButton(0);
    MyButton back = new MyButton(1);
    private final List<String> cardsChosen = new ArrayList<>();
    private final List<JButton> godList = new ArrayList<>();
    transient ButtonGodsList constructor;
    transient MP3 click;

    /**
     * Public constructor
     * @param instance Reference to the client GUI class
     * @param instance2 Reference to the Board class instanced by the GUI
     * @param frame Reference to the JInternalFrame where the current JDesktopPane ChallengerChoiceCards will be inserted
     * @param dimensionFrame Size of the JInternalFrame
     * @param numberOfPlayer Number of Players in game
     * @throws IOException if the loading of the inscription or the descriptions of the gods was not successful
     */

    public ChallengerChoiceCards(Gui instance, Board instance2, JInternalFrame frame, Dimension dimensionFrame, Integer numberOfPlayer) throws IOException {

        gui = instance;
        board = instance2;
        guiIntFrame = frame;
        frameSize.setSize(dimensionFrame);
        numberPlayers = numberOfPlayer;
        intFrameSize.setSize(frameSize.getWidth() * 48/100, frameSize.getHeight() * 54/100);
        cardSize.setSize((int) (frameSize.getWidth() * 9/100), (int) (frameSize.getHeight() * 23.15/100));
        size30x10.setSize(frameSize.width * 30 / 100, frameSize.height * 10 / 100);
        click = new MP3(MUSIC + "Click.mp3");

        final int xConst = frameSize.width * 9/100;
        final int yConst = frameSize.height * 24/100;

        constructor = new ButtonGodsList(frameSize, godList);

        intFrame = internalAndBackgroundSetter(this, intFrameSize, buttonBackground);

        setPreferredSize(frameSize);

        JButton chronus = new JButton();
        chronus.setName("chronus");

        setButtonStyle(godList, intFrame, frameSize, intFrameSize, buttonBackground, cover, false, 1);
        buttonStyle();

        JLabel choice;
        if (numberOfPlayer == 2){
            choice = ImageHandler.setImage(TEXT + "choose_2_gods.png", 100, 100, size30x10.width, size30x10.height);
        }
        else{
            choice = ImageHandler.setImage(TEXT + "choose_3_gods.png", 100, 100, size30x10.width, size30x10.height);
        }
        choice.setBounds(((frameSize.width * 50/100) - (size30x10.width / 2)), frameSize.height * 10/100, size30x10.width, size30x10.height);
        add(choice);

        confirm.setBounds((int) (frameSize.width * 31.5/ 100), (frameSize.height * 81 / 100), (int) (getD().getWidth() * 13 / 100), (int) (getD().getHeight() * 5 / 100));
        add(confirm);

        back.setBounds((int) (frameSize.width * 51.5/ 100), (frameSize.height * 81 / 100), (int) (getD().getWidth() * 13 / 100), (int) (getD().getHeight() * 5 / 100));
        add(back);
        back.addActionListener(new Close());


        if(numberOfPlayer == 2){
            addForTwo(xConst, yConst, yConst);
        }
        else{
            addForThree(chronus, xConst, yConst, yConst);
        }

        JButton backBack = backgroundButton(0);
        backBack.setBounds(0, 0, frameSize.width, frameSize.height);
        add(backBack);


    }

    /**
     * Method for adding the ChooseGod mouseAdapter to the JButtons of the cards
     */

    private void buttonStyle(){
         for (JButton button : godList){
             button.addActionListener(new ChooseGod());
         }
     }

    /**
     * Method for positioning the JButton according to parameters
     * @param button JButton to be positioned
     * @param x Position x in the window
     * @param y Position y in the window
     */

     private void buttonPositioning(JButton button, int x, int y){
         button.setBounds(x, y, cardSize.width, cardSize.height);
         add(button);
     }

    /**
     * Method for placing cards if it's a game for two players
     * @param x Position x of the first card in the window
     * @param y Position y of the first card in the window
     * @param yConst Constant of the y position of the first card in the window
     */

     private void addForTwo(int x, int y, int yConst){
         for (JButton button : godList){
            if(count == 0){
                buttonPositioning(button, x, y);
                count++;
            }
            else if(count < 7){
                x = x + frameSize.width * 12/100;
                buttonPositioning(button, x, y);
                count++;
            }
             else{
                if (y == yConst){

                    x = - frameSize.width * 3/100;
                    y = frameSize.height * 49/100;
                }
                x = x + frameSize.width * 12/100;
                buttonPositioning(button, x, y);
            }
         }
     }

    /**
     * Method for placing cards if it's a game for three players
     * @param chronus JButton of the god to be excluded in positioning
     * @param x Position x of the first card in the window
     * @param y Position y of the first card in the window
     * @param yConst Constant of the y position of the first card in the window
     */

     private void addForThree(JButton chronus, int x, int y, int yConst){
         for (JButton button : godList){
             if(count == 0){
                buttonPositioning(button, x, y);
                count++;
            }
            else if(count < 7 && !button.getName().equalsIgnoreCase(chronus.getName())){
                x = x + frameSize.width * 12/100;
                buttonPositioning(button, x, y);
                count++;
            }
            else if(!button.getName().equalsIgnoreCase(chronus.getName())){
                if (y == yConst){

                    x = frameSize.width * 3/100;
                    y = frameSize.height * 49/100;
                }
                x = x + frameSize.width * 12/100;
                buttonPositioning(button, x, y);
            }
         }
     }

    /**
     * Class that implements ActionListener for the choice of the god
     */

    private class ChooseGod implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            click.play();
            JButton c = (JButton)e.getSource();
            if (0 <= chosen && chosen < numberPlayers){

                eliminateMouseClass(c, ColorBorderGodCards.class);
                eliminateActionClass(c, ChooseGod.class);
                c.setBorder(BorderFactory.createLineBorder(Color.red, 4));
                c.setBorderPainted(true);
                addGod(c);
                chosen++;
                c.addActionListener(new RemoveGod());
            }
            if (chosen == numberPlayers && confirm.getActionListeners().length == 0){
                confirm.addActionListener(new Confirm());
            }
        }

        private void addGod(JButton god){

            for (int x = 0; x < godChosen.size(); x++){
                if (godChosen.get(x).getName().compareTo(god.getName()) > 0){
                    godChosen.add(x, god);
                    return;
                }
            }
            godChosen.add(god);
        }
    }

    /**
     * Class that implements ActionListener for removing the god from the choices made so far
     */

    private class RemoveGod implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            click.play();
            JButton c = (JButton)e.getSource();
            eliminateActionClass(c, RemoveGod.class);
            c.setBorder(null);
            c.setBorderPainted(false);
            c.addMouseListener(new ColorBorderGodCards());
            godChosen.remove(c);
            chosen--;
            c.addActionListener(new ChooseGod());
            eliminateActionClass(confirm, ChallengerChoiceCards.Confirm.class);
        }
    }

    /**
     * Class that implements ActionListener for the JButton Close which closes the current JInternalFrame
     */

    private class Close implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            guiIntFrame.setVisible(false);
        }
    }

    /**
     * Class that implements ActionListener for the JButton Confirm which confirms the chosen gods
     */

    private class Confirm implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            for (JButton button : godChosen){
                cardsChosen.add(button.getName());
            }
            board.setCardsChosen(cardsChosen);
            guiIntFrame.setVisible(false);
            board.callChallengerResponse();
            board.buttonChooseCards.setEnabled(false);
        }
    }
}
