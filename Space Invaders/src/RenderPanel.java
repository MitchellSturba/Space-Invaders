import javax.swing.*;
import java.awt.*;

public class RenderPanel extends JPanel {

    @Override
    public void paintComponent(Graphics g) {

        SpaceInvaders s = SpaceInvaders.spaceInvaders;
        Font myFont = new Font ("Times New Roman", 1, 40);
        g.setFont(myFont);
        super.paintComponent(g);
            if (s.over) {
                g.setColor(Color.white);
                g.drawString("GAME OVER", s.panel.getWidth()/2 - 130,s.panel.getHeight()/2 - 100);
                if (s.hearts <= 0) {
                    g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
                    g.drawString("YOU DIED", s.panel.getWidth()/2 - 75,s.panel.getHeight()/2 - 55);
                }
                g.drawString("R to restart",s.panel.getWidth()/2 - 70,s.panel.getHeight()/2 - 10);
            }else {
                if (s.win) {
                    g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
                    g.setColor(Color.white);
                    g.drawString("YOU WIN!", s.panel.getWidth()/2 - 50,s.panel.getHeight()/2 - 20);
                }
                Font m = new Font("Helvetica", 1, 15);
                g.setFont(m);
                g.setColor(Color.white);
                g.drawString("Score: "+ s.score,20,30);
                g.setColor(Color.green);

                //shots fired
                g.setColor(Color.RED);
                for (int i = 0; i < 8; i++) {
                    g.fillOval(s.bolts[i].x,s.bolts[i].y,10,30);
                }

                //baracades
                for (int i = 0; i < 10; i++) {
                    for (int j = 0; j < 5; j++) {
                        g.setColor(Color.green);
                        g.fillRect(s.barracades[i][j].x,s.barracades[i][j].y,10,10);
                        g.fillRect(s.barracades2[i][j].x,s.barracades2[i][j].y,10,10);
                        g.fillRect(s.barracades3[i][j].x,s.barracades3[i][j].y,10,10);
                    }
                }

                //tank
                g.drawImage(s.tank,s.player.x,s.player.y,120,50,null);
                //tank hitbox
                //g.drawRect(s.player.x + 30,s.player.y,60,50);


                g.setColor(Color.white);
                g.fillOval(s.enemybolt.x,s.enemybolt.y,10,20);

                for (int i = 0; i < s.enemiesH; i++) {
                    for (int j = 0; j < s.enemiesW;  j++) {
                        g.drawImage(s.invaders,s.Enemies[i][j].x,s.Enemies[i][j].y,60,55,null);
                    }
                }

                //render hearts
                for (int q = 0; q < s.hearts; q++) {
                    g.drawImage(s.PlayerHeartImage, 32, q * 30 + 45,30,30,null);
                }
            }
    }


}