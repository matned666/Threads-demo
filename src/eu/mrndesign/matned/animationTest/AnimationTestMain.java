package eu.mrndesign.matned.animationTest;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AnimationTestMain extends JFrame {

    private static final String FRAME_TITLE = "Animation and threads test";
    private static final String DELETE_SMILEY = "Clear panel";
    private static final String START_ANIMATION = "Start animation";
    private static final int FRAME_HEIGHT = 500;
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_START_X = 200;
    private static final int FRAME_START_Y = 300;
    private static final int BUTTON_COLS = 2;

    private final AnimationPanel animationPanel;

    public AnimationTestMain() {
        this.setTitle(FRAME_TITLE);
        this.setBounds(FRAME_START_X, FRAME_START_Y, FRAME_WIDTH, FRAME_HEIGHT);


        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(0, BUTTON_COLS));

        animationPanel = new AnimationPanel();

        animationPanel.setBackground(Color.BLACK);
        this.getContentPane().add(animationPanel);

        JButton delButton = new JButton(DELETE_SMILEY);
        JButton button = new JButton(START_ANIMATION);
        delButton.addActionListener(e -> stopAnimation());
        button.addActionListener(e -> startAnimation());
        buttonsPanel.add(button);
        buttonsPanel.add(delButton);
        this.getContentPane().add(buttonsPanel, BorderLayout.SOUTH);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

    }

    public static void main(String[] args) {
        new AnimationTestMain().setVisible(true);
    }

    private void stopAnimation() {
        animationPanel.stop();
    }


    void startAnimation() {
        animationPanel.addSmiley();
    }

    private static class AnimationPanel extends JPanel {

        private static final String SMILEYS_GROUP = "smileys group";

        private final ArrayList<Smiley> smileys = new ArrayList<>();
        private final JPanel thisPanel = this;
        private final ThreadGroup threadGroup = new ThreadGroup(SMILEYS_GROUP);

        public void addSmiley() {


            smileys.add(new Smiley(thisPanel));
            Thread thread = new Thread(threadGroup, new AnimationRunnable(smileys.get(smileys.size() - 1)));
            thread.start();
            threadGroup.list();

        }

        public void stop() {
            threadGroup.interrupt();
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (Object smiley : smileys) {
                g.drawImage(Smiley.getImage(), ((Smiley) smiley).getX(), ((Smiley) smiley).getY(), null);
            }
        }

        public class AnimationRunnable implements Runnable {

            private final Smiley smiley;

            public AnimationRunnable(Smiley smiley) {
                this.smiley = smiley;
            }


            @Override
            public void run() {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        this.smiley.move();
                        repaint();

                        Thread.sleep(this.smiley.getSmileySpeed());
                    }
                } catch (InterruptedException e) {
                    smileys.clear();
                    repaint();
                }
            }

        }
    }


}
