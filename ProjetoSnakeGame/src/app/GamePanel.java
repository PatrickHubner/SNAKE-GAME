package app;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


import java.util.Random;

public class GamePanel extends JPanel implements ActionListener{

	//DEFININDO TAMANHO DE TELA, ICONES E DELAY DO TIMER.
	static final int SCREEN_WIDHT = 600;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 15;
	static final int GAME_UNITS = (SCREEN_WIDHT*SCREEN_HEIGHT)/UNIT_SIZE;
	static final int DELAY = 100;
	
	//DEFININDO O TAMANHO DOS ICONES PARA POSIÇÃO X E Y.
	final int x[] = new int [GAME_UNITS];
	final int Y[] = new int [GAME_UNITS];
	
	//DEFININDO O TAMANHO INICIAL DO CORPO
	int bodyParts = 6;
	
	//DEFININDO VARIAVEIS PARA A MAÇA
	int applesEaten;
	int appleX;
	int appleY;
	
	
	char direction = 'R';
	boolean running = false;
	Timer timer;
	Random random;
	
	
	
	
	
	GamePanel(){
        random  = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDHT, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }
    public void startGame() {
        newFood();
        running = true;
        timer =new Timer(DELAY, this);
        timer.start();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g) {
        if (running) {
            g.setColor(Color.RED);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
    
            for(int i = 0; i<bodyParts; i++){
                if(i == 0){
                    g.setColor(Color.GREEN);
                    g.fillRect(x[i], Y[i], UNIT_SIZE, UNIT_SIZE);
                }else{
                    g.setColor(new Color(45, 180, 0));
                    g.fillRect(x[i], Y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
        }
        else{
            GameOver(g);
        }
    }
    public void move() {
        for(int i = bodyParts; i>0; i--){
            x[i] = x[i-1]; 
            Y[i] = Y[i-1]; 
        }
        switch(direction){
            case 'U':
                Y[0] = Y[0] - UNIT_SIZE;
                break;
            case 'D':
                Y[0] = Y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }
    public void newFood(){
        appleX =random.nextInt((int)(SCREEN_WIDHT/UNIT_SIZE))*UNIT_SIZE;
        appleY =random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
    }
    public void checkFood(){
        if((x[0]==appleX)&&(Y[0]==appleY)){
            bodyParts++;
            applesEaten++;
            newFood();
        }
    }
    public void checkCollision() {
  
        for(int i= bodyParts; i>0; i--){
            if((x[0] == x[i])&&Y[0]==Y[i]){
                running = false;
            }
        }

        if(x[0]<0){
            running = false;
        }

        if(x[0]>SCREEN_WIDHT){
            running = false;
        }

        if(Y[0]<0){
            running =false;
        }

        if(Y[0]>SCREEN_HEIGHT){
            running =false;
        }
        if (!running) {
            timer.stop();
        }

    }
	public void GameOver(Graphics g) {
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 40));
		FontMetrics metrics1 = getFontMetrics(g.getFont());
		g.drawString("SCORE: " + applesEaten, (SCREEN_WIDHT - metrics1.stringWidth("SCORE: " + applesEaten))/2, g.getFont().getSize());
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free", Font.BOLD, 75));
		FontMetrics metrics2 = getFontMetrics(g.getFont());
		g.drawString("Game Over", (SCREEN_WIDHT - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
	}
	
    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();
            checkFood();
            checkCollision();
        }
        repaint();

    }
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if(direction!='R'){
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction!='L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction!='D'){
                        direction = 'U';
                    }
                case KeyEvent.VK_DOWN:
                    if(direction!='U'){
                        direction = 'D';
                    }
            
                default:
                    break;
            }
        }
    }
}