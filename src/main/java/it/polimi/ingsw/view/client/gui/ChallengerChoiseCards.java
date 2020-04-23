package it.polimi.ingsw.view.client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.view.client.gui.Gui.*;

public class ChallengerChoiseCards extends JPanel{

    Dimension frameSize = new Dimension();
    Dimension intFrameSize = new Dimension();
    Dimension cardSize = new Dimension();
    JFrame intFrame;
    ArrayList<JButton> buttons = new ArrayList<>();
    JLabel power = new JLabel();
    private int count = 0;
    private int chosen = 0;
    private int numberPlayers;
    List<String> godChosen = new ArrayList<>();
    JButton confirm = confirmButtonCreate();

    public ChallengerChoiseCards(Dimension frame, Integer numberOfPlayer) throws IOException {

        frameSize.setSize(frame);
        numberPlayers = numberOfPlayer;
        intFrameSize.setSize(frameSize.getWidth() * 40/100, frameSize.getHeight() * 40/100);
        final int xconst =  (int) (frameSize.width * 9/100);
        final int yconst = (int) frameSize.height * 24/100;
        int x = xconst;
        int y = yconst;

        intFrame = new JFrame();

        cardSize.setSize((int) (frameSize.getWidth() * 9/100), (int) (frameSize.getHeight() * 23.15/100)); //(9, 22)
        setPreferredSize(frameSize);
        setLayout(null);


        JButton apollo = new JButton();
        apollo.setName("Apollo");
        buttons.add(apollo);
        JButton artemis = new JButton();
        artemis.setName("Artemis");
        buttons.add(artemis);
        JButton athena = new JButton();
        athena.setName("Athena");
        buttons.add(athena);
        JButton atlas = new JButton();
        atlas.setName("Atlas");
        buttons.add(atlas);
        JButton chronus = new JButton();
        chronus.setName("Chronus");
        buttons.add(chronus);
        JButton demeter = new JButton();
        demeter.setName("Demeter");
        buttons.add(demeter);
        JButton hepha = new JButton();
        hepha.setName("Hephaestus");
        buttons.add(hepha);
        JButton hera = new JButton();
        hera.setName("Hera");
        buttons.add(hera);
        JButton hestia = new JButton();
        hestia.setName("Hestia");
        buttons.add(hestia);
        JButton hypnus = new JButton();
        hypnus.setName("Hypnus");
        buttons.add(hypnus);
        JButton mino = new JButton();
        mino.setName("Minotaur");
        buttons.add(mino);
        JButton pan = new JButton();
        pan.setName("Pan");
        buttons.add(pan);
        JButton prome = new JButton();
        prome.setName("Prometheus");
        buttons.add(prome);
        JButton zeus = new JButton();
        zeus.setName("Zeus");
        buttons.add(zeus);

        buttonStyle();

        JLabel lapollo = ImageHandler.setImage("src/main/resources/Graphics/gods/apollo.png", 100, 100, cardSize.width, cardSize.height);
        JLabel lartemis = ImageHandler.setImage("src/main/resources/Graphics/gods/artemis.png", 100, 100, cardSize.width, cardSize.height);
        JLabel lathena = ImageHandler.setImage("src/main/resources/Graphics/gods/athena.png", 100, 100, cardSize.width, cardSize.height);
        JLabel latlas = ImageHandler.setImage("src/main/resources/Graphics/gods/atlas.png", 100, 100, cardSize.width, cardSize.height);
        JLabel lchronus = ImageHandler.setImage("src/main/resources/Graphics/gods/chronus.png", 100, 100, cardSize.width, cardSize.height);
        JLabel ldemeter = ImageHandler.setImage("src/main/resources/Graphics/gods/demeter.png", 100, 100, cardSize.width, cardSize.height);
        JLabel lhepha = ImageHandler.setImage("src/main/resources/Graphics/gods/hephaestus.png", 100, 100, cardSize.width, cardSize.height);
        JLabel lhera = ImageHandler.setImage("src/main/resources/Graphics/gods/hera.png", 100, 100, cardSize.width, cardSize.height);
        JLabel lhestia = ImageHandler.setImage("src/main/resources/Graphics/gods/hestia.png", 100, 100, cardSize.width, cardSize.height);
        JLabel lhypnus = ImageHandler.setImage("src/main/resources/Graphics/gods/hypnus.png", 100, 100, cardSize.width, cardSize.height);
        JLabel lmino = ImageHandler.setImage("src/main/resources/Graphics/gods/minotaur.png", 100, 100, cardSize.width, cardSize.height);
        JLabel lpan = ImageHandler.setImage("src/main/resources/Graphics/gods/pan.png", 100, 100, cardSize.width, cardSize.height);
        JLabel lprome = ImageHandler.setImage("src/main/resources/Graphics/gods/prometheus.png", 100, 100, cardSize.width, cardSize.height);
        JLabel lzeus = ImageHandler.setImage("src/main/resources/Graphics/gods/zeus.png", 100, 100, cardSize.width, cardSize.height);

        apollo.setIcon(lapollo.getIcon());
        artemis.setIcon(lartemis.getIcon());
        athena.setIcon(lathena.getIcon());
        atlas.setIcon(latlas.getIcon());
        chronus.setIcon(lchronus.getIcon());
        demeter.setIcon(ldemeter.getIcon());
        hepha.setIcon(lhepha.getIcon());
        hera.setIcon(lhera.getIcon());
        hestia.setIcon(lhestia.getIcon());
        hypnus.setIcon(lhypnus.getIcon());
        mino.setIcon(lmino.getIcon());
        pan.setIcon(lpan.getIcon());
        prome.setIcon(lprome.getIcon());
        zeus.setIcon(lzeus.getIcon());

        JLabel choise;
        if (numberOfPlayer == 2){
            choise = ImageHandler.setImage("src/main/resources/Graphics/Texts/choose_2_gods.png", 100, 100, frameSize.width * 30 / 100, frameSize.height * 10 / 100);
        }
        else{
            choise = ImageHandler.setImage("src/main/resources/Graphics/Texts/choose_3_gods.png", 100, 100, frameSize.width * 30 / 100, frameSize.height * 10 / 100);
        }
        choise.setBounds(frameSize.width * 35/100, frameSize.height * 10/100, frameSize.width * 30/100, frameSize.height * 10/100);
        add(choise);

        for (int z = 0; z < confirm.getActionListeners().length; z++){
            if (confirm.getActionListeners()[z].getClass().equals(Gui.ChangePanel.class))
                confirm.removeActionListener(confirm.getActionListeners()[z]);
        }
        add(confirm);


        if(numberOfPlayer == 2){
            addForTwo(x, y, yconst);
        }
        else{
            addForThree(chronus, x, y, yconst);
        }

        JButton back = backgroundButton();
        add(back);
    }

     private void buttonStyle(){
         for (JButton button : buttons){
             button.setOpaque(false);
             button.setContentAreaFilled(false);
             button.setFocusPainted(false);
             //button.setBorderPainted(false);
             button.addMouseListener(new ColorBorder());
             button.addMouseListener(new ShowPowerRight());
             button.addActionListener(new ChooseGod());
         }
     }

     private void buttonPositioning(JButton button, int x, int y){
         button.setBounds(x, y, cardSize.width, cardSize.height);
         this.add(button);
     }

     private void addForTwo(int x, int y, int yconst){
         for (JButton button : buttons){
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
                if (y == yconst){

                    x = - frameSize.width * 3/100;
                    y = frameSize.height * 49/100;
                }
                x = x + frameSize.width * 12/100;
                buttonPositioning(button, x, y);
            }
         }
     }

     private void addForThree(JButton chronus, int x, int y, int yconst){
         for (JButton button : buttons){
             if(count == 0){
                buttonPositioning(button, x, y);
                count++;
            }
            else if(count < 7 && !button.equals(chronus)){
                x = x + frameSize.width * 12/100;
                buttonPositioning(button, x, y);
                count++;
            }
            else if(!button.equals(chronus)){
                if (y == yconst){

                    x = frameSize.width * 3/100;
                    y = frameSize.height * 49/100;
                }
                x = x + frameSize.width * 12/100;
                buttonPositioning(button, x, y);
            }
         }
     }

    private static class ColorBorder implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {/*override unnecessary*/}

        @Override
        public void mousePressed(MouseEvent e) {/*override unnecessary*/}

        @Override
        public void mouseReleased(MouseEvent e) {/*override unnecessary*/}

        @Override
        public void mouseEntered(MouseEvent e) {
            JButton c = (JButton)e.getSource();
            c.setBorder(BorderFactory.createLineBorder(Color.yellow, 3));
            c.setBorderPainted(true);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            JButton c = (JButton)e.getSource();
            c.setBorderPainted(false);
        }
    }

    private class ShowPowerRight implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {/*override unnecessary*/}

        @Override
        public void mousePressed(MouseEvent e) {/*override unnecessary*/}

        @Override
        public void mouseReleased(MouseEvent e) {/*override unnecessary*/}

        @Override
        public void mouseEntered(MouseEvent e) {
            JButton c = (JButton)e.getSource();
            if (c.getX() < frameSize.width * 50/100 && c.getY() < frameSize.height * 40/100) {
                intFrame.setBounds((int) ((frameSize.width * 13 / 100) + c.getX()), (int) ((frameSize.height * 25.575 / 100)), intFrameSize.width, intFrameSize.height);
            }
            else if (c.getX() >= frameSize.width * 50/100 && c.getY() < frameSize.height * 40/100){
                intFrame.setBounds((int) (c.getX() - (frameSize.width * 38 / 100)), (int) ((frameSize.height * 25.575 / 100)), intFrameSize.width, intFrameSize.height);
            }
            else if (c.getX() < frameSize.width * 50/100 && c.getY() >= frameSize.height * 40/100){
                intFrame.setBounds((int) ((frameSize.width * 13 / 100) + c.getX()), (int) ((frameSize.height * 50.575 / 100)), intFrameSize.width, intFrameSize.height);
            }
            else
                intFrame.setBounds((int) (c.getX() - (frameSize.width * 38 / 100)), (int) ((frameSize.height * 50.575 / 100)), intFrameSize.width, intFrameSize.height);
            power.setText(null);
            power.setText("questo è il potere di " + c.getName());
            power.setBounds((intFrameSize.width * 40 / 100), (int) ((frameSize.height * 45 / 100)), intFrameSize.width * 20/100, intFrameSize.height * 10/100);
            intFrame.add(power);
            intFrame.setVisible(true);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            intFrame.setVisible(false);
        }
    }



    private class ChooseGod implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton c = (JButton)e.getSource();
            if (0 <= chosen && chosen < numberPlayers){

                for (int y = 0; y < c.getMouseListeners().length; y++){
                    if (c.getMouseListeners()[y].getClass().equals(ColorBorder.class))
                        c.removeMouseListener(c.getMouseListeners()[y]);
                }
                for (int y = 0; y < c.getActionListeners().length; y++){
                    if (c.getActionListeners()[y].getClass().equals(ChooseGod.class))
                        c.removeActionListener(c.getActionListeners()[y]);
                }
                c.setBorder(BorderFactory.createLineBorder(Color.red, 4));
                c.setBorderPainted(true);
                godChosen.add(c.getName());
                chosen++;
                c.addActionListener(new RemoveGod());
            }
            if (chosen == numberPlayers && confirm.getActionListeners().length == 0){
                confirm.addActionListener(new Gui.ChangePanel());
            }
        }
    }
    private class RemoveGod implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton c = (JButton)e.getSource();

            for (int y = 0; y < c.getActionListeners().length; y++){
                if (c.getActionListeners()[y].getClass().equals(RemoveGod.class))
                    c.removeActionListener(c.getActionListeners()[y]);
            }
            c.setBorder(null);
            c.setBorderPainted(false);
            c.addMouseListener(new ColorBorder());
            godChosen.remove(c.getName());
            chosen--;
            c.addActionListener(new ChooseGod());

            for (int z = 0; z < confirm.getActionListeners().length; z++){
                if (confirm.getActionListeners()[z].getClass().equals(Gui.ChangePanel.class))
                    confirm.removeActionListener(confirm.getActionListeners()[z]);
            }

        }
    }

    private boolean inChoosen(String name){
        for (String god : godChosen){
            if (god.equalsIgnoreCase(name)){
                return true;
            }
        }
        return false;
    }
}
