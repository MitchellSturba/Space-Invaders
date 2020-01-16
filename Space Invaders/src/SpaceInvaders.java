import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;
import java.awt.image.BufferedImage;
import java.util.Random;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SpaceInvaders implements ActionListener, KeyListener {
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    public JFrame jframe = new JFrame();
    public RenderPanel panel = new RenderPanel();
    public static SpaceInvaders spaceInvaders;
    public int width = dim.width, height = 800;
    public Timer tim = new Timer(10,this);

    public BufferedImage invaders, tank, PlayerHeartImage;

    public int ticks = 0, speed = 1;

    public int enemiesH = 8, enemiesW = 6;
    public Point[][] Enemies = new Point[enemiesH][enemiesW];

    public boolean win = false, right = false, left = false, moveright = true, moveleft = false, movedown = false, over = false, Efired = false;
    public Point player, enemybolt = new Point(-50,-50);
    public Point[] bolts = new Point[8];
    public int boltindex = 0, score = 0, hearts = 3;

    public Point[][] barracades = new Point[10][5];
    public Point[][] barracades2 = new Point[10][5];
    public Point[][] barracades3 = new Point[10][5];

    public SpaceInvaders() {
        player = new Point(width/2 - 140, height-150);

        try {
            invaders = ImageIO.read(getClass().getResource("invader.png"));
            tank = ImageIO.read(getClass().getResource("tank.png"));
            PlayerHeartImage = ImageIO.read(getClass().getResource("heart.png"));
        }catch (IOException e) {
            e.printStackTrace();
        }

        //initialize bolts
        for (int z = 0; z < 8; z++) {
            bolts[z] = new Point();
        }

        //initialze enemies
        for (int i = 0; i < enemiesH; i++) {
            for (int j = 0; j < enemiesW;  j++) {
                Enemies[i][j] = new Point(i * 80 + 80, j * 40 + 30);
            }
        }

        //sets baracades
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 5;  j++) {
                barracades[i][j] = new Point(i * 10 + 200,j * 10 + 550);
                barracades2[i][j] = new Point(-100,j* 10+ 550);
                barracades3[i][j] = new Point(-100,j*10 + 550);
            }
        }
        //shape baracades
        barracades[0][0].y = -10;
        barracades[9][0].y = -10;
        barracades[0][1].y = -10;
        barracades[1][0].y = -10;
        barracades[9][1].y = -10;
        barracades[8][0].y = -10;

        barracades2[0][0].y = -10;
        barracades2[9][0].y = -10;
        barracades2[0][1].y = -10;
        barracades2[1][0].y = -10;
        barracades2[9][1].y = -10;
        barracades2[8][0].y = -10;

        barracades3[0][0].y = -10;
        barracades3[9][0].y = -10;
        barracades3[0][1].y = -10;
        barracades3[1][0].y = -10;
        barracades3[9][1].y = -10;
        barracades3[8][0].y = -10;


        jframe.setTitle("SpaceInvaders");
        jframe.setSize(width- 200,height);
        jframe.setLocationRelativeTo(null);
        panel.setBackground(Color.black);
        jframe.setVisible(true);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.addKeyListener(this);
        jframe.add(panel);

        tim.start();

    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        ticks++;
        panel.repaint();

        if (hearts == 0) {
            over = true;
        }
        if (over) {
            for (int i = 0; i < enemiesH; i++) {
                for (int j = 0; j < enemiesW; j++) {
                    Enemies[i][j].x = 0;
                    Enemies[i][j].y = 0;
                }
            }
            speed = 0;
            moveright = false;
        }

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 5; j++) {
                barracades2[i][j].x = i *10 + jframe.getWidth()/2 - 15;
                barracades3[i][j].x = i *10 + jframe.getWidth() - 250;
            }
        }

        if (right && !left) {
            player.x += 5;
        }
        if (left && !right) {
            player.x -= 5;
        }

        //bolt speed
        for (int x = 0; x < 8; x++) {
            bolts[x].y -= 10;
        }
        //shots fired
        bolts[boltindex].x = player.x + 55;
        bolts[boltindex].y = player.y + 15;
        for (int i = 0; i < enemiesH; i++) {
            for (int j = 0; j < enemiesW; j++) {
                for (int z = 0; z < 8; z++) {
                    if (bolts[z].y >= Enemies[i][j].y && bolts[z].y <= Enemies[i][j].y +30&& bolts[z].x >= Enemies[i][j].x && bolts[z].x <= Enemies[i][j].x + 50) {
                        Enemies[i][j].y -= 100000;
                        Enemies[i][j].x = Enemies[enemiesH/2][enemiesW/2].x;
                        bolts[z].y -= 1000;
                        score += 10;
                    }
                }
                if (bolts[boltindex].y < 0) {
                    bolts[boltindex].y = -40;
                }
            }
        }


        //baracade collision from player bolt
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 5; j++) {
                for (int z = 0; z < 8; z++) {
                    if (bolts[z].x >= barracades[i][j].x - 9 && bolts[z].x <= barracades[i][j].x + 9 && bolts[z].y >= barracades[i][j].y && bolts[z].y <= barracades[i][j].y+10) {
                        barracades[i][j].y = -15;
                        bolts[z].y -= 1000;
                    }
                    //baracade 2
                    if (bolts[z].x >= barracades2[i][j].x  - 9 && bolts[z].x <= barracades2[i][j].x + 9 && bolts[z].y >= barracades2[i][j].y && bolts[z].y <= barracades2[i][j].y+10) {
                        barracades2[i][j].y = -15;
                        bolts[z].y -= 1000;
                    }
                    //baracade 3
                    if (bolts[z].x >= barracades3[i][j].x - 9  && bolts[z].x <= barracades3[i][j].x + 9 && bolts[z].y >= barracades3[i][j].y && bolts[z].y <= barracades3[i][j].y+10) {
                        barracades3[i][j].y = -15;
                        bolts[z].y -= 1000;
                    }

                    //from enemy bolt
                    if (enemybolt.y <= barracades[i][j].y +10 && enemybolt.y >= barracades[i][j].y && enemybolt.x <= barracades[i][j].x + 10 && enemybolt.x >= barracades[i][j].x) {
                        barracades[i][j].y = -15;
                        enemybolt.y += height;
                        Efired = false;
                    }
                    if (enemybolt.y <= barracades2[i][j].y +10 && enemybolt.y >= barracades2[i][j].y && enemybolt.x <= barracades2[i][j].x + 10 && enemybolt.x >= barracades2[i][j].x) {
                        barracades2[i][j].y = -15;
                        enemybolt.y += height;
                        Efired = false;
                    }
                    if (enemybolt.y <= barracades3[i][j].y +10 && enemybolt.y >= barracades3[i][j].y && enemybolt.x <= barracades3[i][j].x + 10 && enemybolt.x >= barracades3[i][j].x) {
                        barracades3[i][j].y = -15;
                        enemybolt.y += height;
                        Efired = false;
                    }
                }

            }
        }


        //enemies firing at player
        Random rand = new Random();
        Efired = false;
        enemybolt.y += 11;
        for (int i = 0; i < enemiesH; i++) {
            for (int j = 0; j < enemiesW; j++) {
                    int r = rand.nextInt(100);

                    if (enemybolt.y < height) {
                        Efired = true;
                    }

                    //collision minus a heart
                    if (enemybolt.y <= player.y + 50 && enemybolt.y >= player.y && enemybolt.x >= player.x + 30 && enemybolt.x <= player.x + 90) {
                        enemybolt.y += 200;
                        hearts--;
                    }

                    if (r % 25 == 0 && !Efired && Enemies[i][j].y > 0) {
                        Efired = true;
                        enemybolt.x = Enemies[i][j].x + 30;
                        enemybolt.y = Enemies[i][j].y + 30;
                }

            }
        }

        //below is the enemies moving
        if (ticks % 2 == 0) {
            if (moveright && !over) {
                for (int i = 0; i < enemiesH; i++) {
                    for (int j = 0; j < enemiesW; j++) {
                        Enemies[i][j].x += speed;

                        if (Enemies[i][j].x >= jframe.getWidth() - 100) {
                            moveright = false;
                            moveleft = true;
                            movedown = true;
                        }
                    }
                }
            }
            if (moveleft && !over) {
                for (int i = 0; i < enemiesH; i++) {
                    for (int j = 0; j< enemiesW; j++) {

                        Enemies[i][j].x -= speed;

                        if (Enemies[i][j].x <= 50) {
                            moveleft = false;
                            moveright = true;
                            movedown = true;
                        }
                    }
                }
            }
        }
        if (movedown && !over) {
            for (int i = 0; i < enemiesH; i++) {
                for (int j = 0; j< enemiesW; j++) {
                    Enemies[i][j].y += 15;
                    if (Enemies[i][j].y >= height - 320) {
                        over = true;
                        break;
                    }
                }
            }
            movedown = false;
            speed++;
        }


        //below is so player can jump from left of the screen to right and vice verca
        if (player.x < -50) {
            player.x = jframe.getWidth();
        }
        if (player.x > jframe.getWidth() + 50) {
            player.x = -25;
        }

        //win
        if (score >= 480) {
            win = true;
        }

    }

    @Override
    public void keyPressed(KeyEvent g) {

        int i = g.getKeyCode();

        if (i == KeyEvent.VK_RIGHT  || i == KeyEvent.VK_D) {
            right = true;
        }
        if (i == KeyEvent.VK_LEFT || i == KeyEvent.VK_A) {
            left = true;
        }
        if (i == KeyEvent.VK_SPACE || i == KeyEvent.VK_UP || i == KeyEvent.VK_W) {
            if (boltindex >= 7) {
                boltindex = 0;
            }
            boltindex++;
        }
        if (i == KeyEvent.VK_R && over) {
            hearts = 3;
            win = false;
            right = false;
            left = false;
            moveright = true;
            moveleft = false;
            movedown = false;
            over = false;
            Efired = false;
            ticks = 0;
            for (int n = 0; n < enemiesH; n++) {
                for (int j = 0; j < enemiesW;  j++) {
                    Enemies[n][j].x = n * 80 + 81;
                    Enemies[n][j].y = j * 40 + 30;
                }
            }
            speed = 1;
            moveright = true;
            for (int z = 0; z < 10; z++) {
                for (int j = 0; j < 5; j++) {
                    barracades[z][j].x = z*10 + 200;
                    barracades[z][j].y = j*10 + 550;
                    barracades2[z][j].x = z *10 + jframe.getWidth()/2 - 15;
                    barracades2[z][j].y = j*10 + 550;
                    barracades3[z][j].x = z *10 + jframe.getWidth() - 250;
                    barracades3[z][j].y = j*10 + 550;
                }
            }

            barracades[0][0].y = -10;
            barracades[9][0].y = -10;
            barracades[0][1].y = -10;
            barracades[1][0].y = -10;
            barracades[9][1].y = -10;
            barracades[8][0].y = -10;

            barracades2[0][0].y = -10;
            barracades2[9][0].y = -10;
            barracades2[0][1].y = -10;
            barracades2[1][0].y = -10;
            barracades2[9][1].y = -10;
            barracades2[8][0].y = -10;

            barracades3[0][0].y = -10;
            barracades3[9][0].y = -10;
            barracades3[0][1].y = -10;
            barracades3[1][0].y = -10;
            barracades3[9][1].y = -10;
            barracades3[8][0].y = -10;
            score = 0;
        }
        if (i == KeyEvent.VK_0) {
            speed += 5;
        }
        if (i == KeyEvent.VK_9) {
            speed -= 5;
        }
        if (i == KeyEvent.VK_MINUS) {
            hearts--;
        }
        if (i == KeyEvent.VK_EQUALS) {
            hearts++;
        }

    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        int i = e.getKeyCode();

        if (i == KeyEvent.VK_RIGHT || i == KeyEvent.VK_D ) {
            right = false;
        }
        if (i == KeyEvent.VK_LEFT  || i == KeyEvent.VK_A){
            left = false;
        }
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
        if (right && !left) {
            player.x += 5;
        }
        if (left && !right) {
            player.x -= 5;
        }
    }


    public static void main(String[] args) {
        spaceInvaders = new SpaceInvaders();
    }
}
