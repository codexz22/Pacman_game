import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class PacmanGame extends JPanel implements ActionListener, KeyListener {
    private static final int TILE_SIZE = 24;
    private static final int MAP_ROWS = 21;
    private static final int MAP_COLS = 21;
    private static final int SCREEN_W = MAP_COLS * TILE_SIZE;
    private static final int SCREEN_H = MAP_ROWS * TILE_SIZE;
    private static final int FPS = 60;
    private static final int TIMER_DELAY = 1000 / FPS;

    private final int[][] map = {
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,1,1,1,0,1,1,0,1,0,1,1,0,1,1,1,0,1,0,1},
        {1,3,1,0,1,0,1,0,0,0,0,0,1,0,1,0,1,0,1,3,1},
        {1,0,1,0,1,0,1,0,1,1,1,0,1,0,1,0,1,0,1,0,1},
        {1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,1,0,1,0,1,0,0,1,0,0,1,0,1,0,1,0,1,0,1},
        {1,0,1,0,1,0,1,1,0,1,1,0,1,1,1,0,1,0,1,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,1,1,1,1,1,0,1,1,1,1,1,1,0,1,1,1,1,1,1,1},
        {0,0,0,0,0,1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0},
        {1,1,1,1,1,1,0,1,1,1,1,1,1,0,1,1,1,1,1,1,1},
        {1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,1,0,1,0,1,1,0,1,0,1,1,0,1,0,1,0,1,0,1},
        {1,0,1,0,1,0,1,0,0,0,0,0,1,0,1,0,1,0,1,0,1},
        {1,3,1,0,1,0,1,0,1,1,1,0,1,0,1,0,1,0,1,3,1},
        {1,0,1,1,1,0,1,1,0,1,0,1,1,0,1,1,1,0,1,0,1},
        {1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,1,0,1,0,1,0,0,0,0,0,1,0,1,0,1,0,1,0,1},
        {1,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
    };

    private boolean[][] pellets, powerPellet;
    private Pacman pacman;
    private List<Ghost> ghosts;
    private Timer timer;
    private int score = 0, lives = 3;
    private boolean gameOver = false;

    public PacmanGame() {
        setPreferredSize(new Dimension(SCREEN_W, SCREEN_H));
        setBackground(Color.BLACK);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        addKeyListener(this);

        initGame();

        timer = new Timer(TIMER_DELAY, this);
        timer.start();

        addHierarchyListener(e -> {
            if ((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0 && isShowing()) {
                requestFocusInWindow();
            }
        });
    }

    private void initGame() {
        pellets = new boolean[MAP_ROWS][MAP_COLS];
        powerPellet = new boolean[MAP_ROWS][MAP_COLS];
        for (int r = 0; r < MAP_ROWS; r++) {
            for (int c = 0; c < MAP_COLS; c++) {
                pellets[r][c] = (map[r][c] == 0);
                powerPellet[r][c] = (map[r][c] == 3);
            }
        }
        pacman = new Pacman(10, 15);
        ghosts = new ArrayList<>();
        ghosts.add(new Ghost(9, 9, Color.RED));
        ghosts.add(new Ghost(11, 9, Color.CYAN));
        ghosts.add(new Ghost(9, 11, Color.PINK));
        ghosts.add(new Ghost(11, 11, Color.ORANGE));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            pacman.update();
            for (Ghost g : ghosts) g.update();
            checkPellets();
            checkCollisions();
            repaint();
        }
    }

    private void checkPellets() {
        int r = pacman.gridY, c = pacman.gridX;
        if (inBounds(r, c) && pellets[r][c]) { pellets[r][c] = false; score += 10; }
        if (inBounds(r, c) && powerPellet[r][c]) { 
            powerPellet[r][c] = false; score += 50; 
            for (Ghost g: ghosts) g.frighten(); 
        }
    }

    private void checkCollisions() {
        for (Ghost g : ghosts) {
            if (Math.hypot(pacman.x - g.x, pacman.y - g.y) < TILE_SIZE * 0.6) {
                if (g.frightened) { score += 200; g.respawn(); }
                else {
                    lives--;
                    if (lives <= 0) gameOver = true;
                    else { pacman.respawn(); ghosts.forEach(Ghost::respawn); }
                }
            }
        }
    }

    private boolean inBounds(int r, int c){ return r>=0 && r<MAP_ROWS && c>=0 && c<MAP_COLS; }
    private boolean isWall(int r,int c){ return !inBounds(r,c) || map[r][c]==1; }

    @Override
    protected void paintComponent(Graphics g0) {
        super.paintComponent(g0);
        Graphics2D g=(Graphics2D)g0;
        drawMaze(g);
        drawPellets(g);
        pacman.draw(g);
        ghosts.forEach(gg -> gg.draw(g));

        g.setColor(Color.WHITE);
        g.drawString("Score: "+score,10,15);
        g.drawString("Lives: "+lives,SCREEN_W-70,15);

        if(gameOver){
            g.setFont(new Font("Arial",Font.BOLD,20));
            String s="GAME OVER - Press ENTER";
            g.drawString(s,(SCREEN_W-g.getFontMetrics().stringWidth(s))/2,SCREEN_H/2);
        }
    }

    private void drawMaze(Graphics2D g){
        g.setColor(Color.BLUE.darker());
        for(int r=0;r<MAP_ROWS;r++)
            for(int c=0;c<MAP_COLS;c++)
                if(map[r][c]==1)
                    g.fillRect(c*TILE_SIZE,r*TILE_SIZE,TILE_SIZE,TILE_SIZE);
    }

    private void drawPellets(Graphics2D g){
        for(int r=0;r<MAP_ROWS;r++)
            for(int c=0;c<MAP_COLS;c++){
                if(pellets[r][c]){
                    int cx=c*TILE_SIZE+TILE_SIZE/2, cy=r*TILE_SIZE+TILE_SIZE/2;
                    g.setColor(Color.YELLOW); g.fillOval(cx-3,cy-3,6,6);
                } else if(powerPellet[r][c]){
                    int cx=c*TILE_SIZE+TILE_SIZE/2, cy=r*TILE_SIZE+TILE_SIZE/2;
                    g.setColor(Color.ORANGE); g.fillOval(cx-6,cy-6,12,12);
                }
            }
    }

    // ---------------- Pacman ----------------
    private class Pacman{
        double x,y; int gridX,gridY,dx=0,dy=0,nx=0,ny=0; final double speed=2.8;
        Pacman(int gx,int gy){gridX=gx;gridY=gy;x=gx*TILE_SIZE+TILE_SIZE/2.0;y=gy*TILE_SIZE+TILE_SIZE/2.0;}
        void setNextDir(int nx,int ny){this.nx=nx;this.ny=ny;}
        void update(){
            if(!isWall(gridY+ny,gridX+nx)){dx=nx;dy=ny;}
            if(isWall(gridY+dy,gridX+dx)){dx=dy=0;}
            x+=dx*speed; y+=dy*speed;
            int gx=(int)Math.round((x-TILE_SIZE/2.0)/TILE_SIZE);
            int gy=(int)Math.round((y-TILE_SIZE/2.0)/TILE_SIZE);
            if(isWall(gy,gx)){x-=dx*speed;y-=dy*speed;dx=dy=0;}else{gridX=gx;gridY=gy;}
        }
        void draw(Graphics2D g){g.setColor(Color.YELLOW);g.fillOval((int)(x-TILE_SIZE/2),(int)(y-TILE_SIZE/2),TILE_SIZE,TILE_SIZE);}
        void respawn(){gridX=10;gridY=15;x=gridX*TILE_SIZE+TILE_SIZE/2.0;y=gridY*TILE_SIZE+TILE_SIZE/2.0;dx=dy=nx=ny=0;}
    }

    // ---------------- Ghost ----------------
    private class Ghost{
        double x,y; int gridX,gridY,dx,dy; final Color color; final double speed=1.6;
        boolean frightened=false; int frightTimer=0; final int startGX,startGY; final Random rnd=new Random();
        Ghost(int gx,int gy,Color c){startGX=gx; startGY=gy; gridX=gx; gridY=gy; x=gx*TILE_SIZE+TILE_SIZE/2.0; y=gy*TILE_SIZE+TILE_SIZE/2.0; color=c; chooseDir();}
        void chooseDir(){int[]dxs={1,-1,0,0},dys={0,0,1,-1}; List<Integer>opts=new ArrayList<>();
            for(int i=0;i<4;i++) if(!isWall(gridY+dys[i],gridX+dxs[i])) opts.add(i);
            int p=opts.get(rnd.nextInt(opts.size())); dx=dxs[p]; dy=dys[p];}
        void update(){if(frightened){frightTimer--; if(frightTimer<=0) frightened=false;}
            if(rnd.nextInt(12)==0 || isWall(gridY+dy,gridX+dx)) chooseDir();
            double sp=frightened?speed*0.7:speed;
            x+=dx*sp; y+=dy*sp;
            int gx=(int)Math.round((x-TILE_SIZE/2.0)/TILE_SIZE), gy=(int)Math.round((y-TILE_SIZE/2.0)/TILE_SIZE);
            if(isWall(gy,gx)){x-=dx*sp; y-=dy*sp; chooseDir();} else {gridX=gx; gridY=gy;}
        }
        void draw(Graphics2D g){int size=TILE_SIZE; int X=(int)(x-size/2.0),Y=(int)(y-size/2.0);
            g.setColor(frightened?Color.BLUE:color); g.fillRoundRect(X,Y,size,size,size/2,size/2);}
        void frighten(){frightened=true; frightTimer=FPS*6;}
        void respawn(){gridX=startGX; gridY=startGY; x=gridX*TILE_SIZE+TILE_SIZE/2.0; y=gridY*TILE_SIZE+TILE_SIZE/2.0; frightened=false; chooseDir();}
    }

    // ---------------- KeyListener ----------------
    @Override public void keyPressed(KeyEvent e){
        if(gameOver && e.getKeyCode()==KeyEvent.VK_ENTER){score=0;lives=3;gameOver=false;initGame(); return;}
        switch(e.getKeyCode()){
            case KeyEvent.VK_LEFT -> pacman.setNextDir(-1,0);
            case KeyEvent.VK_RIGHT -> pacman.setNextDir(1,0);
            case KeyEvent.VK_UP -> pacman.setNextDir(0,-1);
            case KeyEvent.VK_DOWN -> pacman.setNextDir(0,1);
        }
    }
    @Override public void keyReleased(KeyEvent e){}
    @Override public void keyTyped(KeyEvent e){}

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Pac-Man Full Game");
            PacmanGame game = new PacmanGame();
            frame.add(game);
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            SwingUtilities.invokeLater(game::requestFocusInWindow);
        });
    }
}
