package com.markrusev.twodeetank;

import javax.swing.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class GamePlay extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//player1 variables
	private ImageIcon playerOneTank;
	private int player1X=200;
	private int player1Y=500;
	private boolean player1up=true;
	private boolean player1down=false;
	private boolean player1right=false;
	private boolean player1left=false;
	private boolean player1Shoot=false;
	private int player1Score=0;
	private int player1Lives=5;
	private String shootDirection1= " ";
	
	
	
	//player2 variables
	private ImageIcon playerTwoTank;	
	private int player2X = 400;
	private int player2Y = 500;	
	private boolean player2right = false;
	private boolean player2left = false;
	private boolean player2down = false;
	private boolean player2up = true;
	private int player2Score = 0;
	private int player2Lives = 5;
	private boolean player2Shoot = false;
	private String shootDirection2 = "";
	
	private Brick bricks;
	private Timer timer;
	private int delay=10;
	private boolean play=true;
	
	private Tank1Listener firstListener;
	private Tank2Listener secondListener;
	
	private Tank1Bullet player1bullet = null;
	private Tank2Bullet player2bullet = null;
	
	public GamePlay() {
		bricks = new Brick();
		firstListener = new Tank1Listener();
		secondListener = new Tank2Listener();
		setFocusable(true);
		//addKeyListener(this);
		addKeyListener(firstListener);
		addKeyListener(secondListener);
		setFocusTraversalKeysEnabled(false);
        timer=new Timer(delay,this);
		timer.start();
	}
	
	
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		// play background
				g.setColor(Color.black);
				g.fillRect(0, 0, 650, 600);
				
				// right side background
				g.setColor(Color.DARK_GRAY);
				g.fillRect(660, 0, 140, 600);
				
				// draw solid bricks
				bricks.drawSolids(this, g);
				
				// draw Breakable bricks	
				bricks.draw(this, g);
				
				if(play)
				{
					// draw player 1
					if(player1up) 
					   playerOneTank=new ImageIcon("player1_tank_up.png");
					else if(player1down) 
						 playerOneTank=new ImageIcon("player1_tank_down.png");
					else if(player1right) 
						 playerOneTank=new ImageIcon("player1_tank_right.png");
					else if(player1left) 
						 playerOneTank=new ImageIcon("player1_tank_left.png");
						
					playerOneTank.paintIcon(this, g, player1X, player1Y);
					
					// draw player 2
					if(player2up)
						playerTwoTank=new ImageIcon("player2_tank_up.png");	
					else if(player2down)
						playerTwoTank=new ImageIcon("player2_tank_down.png");
					else if(player2right)
						playerTwoTank=new ImageIcon("player2_tank_right.png");
					else if(player2left)
						playerTwoTank=new ImageIcon("player2_tank_left.png");
								
					playerTwoTank.paintIcon(this, g, player2X, player2Y);
					
					//checking shooting direction
					if(player1bullet != null && player1Shoot)
					{
						if(shootDirection1.equals(""))
						{
							if(player1up)
							{					
								shootDirection1 = "up";
							}
							else if(player1down)
							{					
								shootDirection1 = "down";
							}
							else if(player1right)
							{				
								shootDirection1 = "right";
							}
							else if(player1left)
							{			
								shootDirection1 = "left";
							}
						}
						else
						{
							player1bullet.move(shootDirection1);
							player1bullet.draw(g);
						}
					
						//if a bullet hits tank2
						if(new Rectangle(player1bullet.getX(), player1bullet.getY(), 10, 10)
						.intersects(new Rectangle(player2X, player2Y, 50, 50)))
						{
							player1Score += 10;
							player2Lives -= 1;
							player1bullet = null;
							player1Shoot = false;
							shootDirection1 = "";
						}
						//calling the both methods to check if the tank is over a brick
						if(bricks.checkCollision(player1bullet.getX(), player1bullet.getY())
								|| bricks.checkSolidCollision(player1bullet.getX(), player1bullet.getY()))
						{
							player1bullet = null;
							player1Shoot = false;
							shootDirection1 = "";				
						}
			
						if(player1bullet.getY() < 1 
								|| player1bullet.getY() > 580
								|| player1bullet.getX() < 1
								|| player1bullet.getX() > 630)
						{
							player1bullet = null;
							player1Shoot = false;
							shootDirection1 = "";
						}
					}
					//checking tank2 shooting direction
					if(player2bullet != null && player2Shoot)
					{
						if(shootDirection2.equals(""))
						{
							if(player2up)
							{					
								shootDirection2 = "up";
							}
							else if(player2down)
							{					
								shootDirection2 = "down";
							}
							else if(player2right)
							{				
								shootDirection2 = "right";
							}
							else if(player2left)
							{			
								shootDirection2 = "left";
							}
						}
						else
						{
							player2bullet.move(shootDirection2);
							player2bullet.draw(g);
						}
						
						//if a bullet from tank2 hits an enemy 
						if(new Rectangle(player2bullet.getX(), player2bullet.getY(), 10, 10)
						.intersects(new Rectangle(player1X, player1Y, 50, 50)))
						{
							player2Score += 10;
							player1Lives -= 1;
							player2bullet = null;
							player2Shoot = false;
							shootDirection2 = "";
						}
						//calling both methods
						if(bricks.checkCollision(player2bullet.getX(), player2bullet.getY())
								|| bricks.checkSolidCollision(player2bullet.getX(), player2bullet.getY()))
						{
							player2bullet = null;
							player2Shoot = false;
							shootDirection2 = "";				
						}
						
						if(player2bullet.getY() < 1 
								|| player2bullet.getY() > 580
								|| player2bullet.getX() < 1
								|| player2bullet.getX() > 630)
						{
							player2bullet = null;
							player2Shoot = false;
							shootDirection2 = "";
						}
					}
				}
			
				
				// the scores 		
				g.setColor(Color.white);
				g.setFont(new Font("serif",Font.BOLD, 15));
				g.drawString("Scores", 700,30);
				g.drawString("Player 1:  "+player1Score, 670,60);
				g.drawString("Player 2:  "+player2Score, 670,90);
				
				g.drawString("Lives", 700,150);
				g.drawString("Player 1:  "+player1Lives, 670,180);
				g.drawString("Player 2:  "+player2Lives, 670,210);
				
				if(player1Lives == 0)
				{
					g.setColor(Color.white);
					g.setFont(new Font("serif",Font.BOLD, 60));
					g.drawString("Game Over", 200,300);
					g.drawString("Player 2 Won", 180,380);
					play = false;
					g.setColor(Color.white);
					g.setFont(new Font("serif",Font.BOLD, 30));
					g.drawString("(Space to Restart)", 230,430);
				}
				else if(player2Lives == 0)
				{
					g.setColor(Color.white);
					g.setFont(new Font("serif",Font.BOLD, 60));
					g.drawString("Game Over", 200,300);
					g.drawString("Player 1 Won", 180,380);
					g.setColor(Color.white);
					g.setFont(new Font("serif",Font.BOLD, 30));
					g.drawString("(Space to Restart)", 230,430);
					play = false;
				}
				
				g.dispose();
			}

			
			@Override
			public void actionPerformed(ActionEvent e) {
				timer.start();
			
				repaint();
			}
			
			private class Tank1Listener implements KeyListener{
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode()== KeyEvent.VK_SPACE && (player1Lives == 0 || player2Lives == 0))
					{
						bricks = new Brick();
						player1X = 200;
						player1Y = 550;	
						player1right = false;
						player1left = false;
						player1down = false;
						player1up = true;	
						
						player2X = 400;
						player2Y = 550;	
						player2right = false;
						player2left = false;
						player2down = false;
						player2up = true;	
						
						player1Score = 0;
						player1Lives = 5;
						player2Score = 0;
						player2Lives = 5;
						play = true;
						repaint();
					}
					if(e.getKeyCode()== KeyEvent.VK_U)
					{
						if(!player1Shoot)
						{
							if(player1up)
							{					
								player1bullet = new Tank1Bullet(player1X + 20, player1Y);
							}
							else if(player1down)
							{					
								player1bullet = new Tank1Bullet(player1X + 20, player1Y + 40);
							}
							else if(player1right)
							{				
								player1bullet = new Tank1Bullet(player1X + 40, player1Y + 20);
							}
							else if(player1left)
							{			
								player1bullet = new Tank1Bullet(player1X, player1Y + 20);
							}
							
							player1Shoot = true;
						}
					}
					if(e.getKeyCode()== KeyEvent.VK_W)
					{
						player1right = false;
						player1left = false;
						player1down = false; 
						player1up = true;		
						
						if(player1Y > 10)
							player1Y-=10;

					}
					if(e.getKeyCode()== KeyEvent.VK_A)
					{
						player1right = false;
						player1left = true;
						player1down = false;
						player1up = false;
						
						if(player1X > 10)
							player1X-=10;
					}
					if(e.getKeyCode()== KeyEvent.VK_S)
					{
						player1right = false;
						player1left = false;
						player1down = true;
						player1up = false;
						
						if(player1Y < 540)
							player1Y+=10;
					}
					if(e.getKeyCode()== KeyEvent.VK_D)
					{
						player1right = true;
						player1left = false;
						player1down = false;
						player1up = false;
						
						if(player1X < 590)
							player1X+=10;
					}
					
				}
				public void keyReleased(KeyEvent e) {
					
				}
				public void keyTyped(KeyEvent e) {
					
				}
			}
			
			private class Tank2Listener implements KeyListener{
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode()== KeyEvent.VK_M)
					{
						if(!player2Shoot)
						{
							if(player2up)
							{					
								player2bullet = new Tank2Bullet(player2X + 20, player2Y);
							}
							else if(player2down)
							{					
								player2bullet = new Tank2Bullet(player2X + 20, player2Y + 40);
							}
							else if(player2right)
							{				
								player2bullet = new Tank2Bullet(player2X + 40, player2Y + 20);
							}
							else if(player2left)
							{			
								player2bullet = new Tank2Bullet(player2X, player2Y + 20);
							}
							
							player2Shoot = true;
						}
					}
					if(e.getKeyCode()== KeyEvent.VK_UP)
					{
						player2right = false;
						player2left = false;
						player2down = false;
						player2up = true;		
						
						if(!(player2Y < 10))
							player2Y-=10;

					}
					if(e.getKeyCode()== KeyEvent.VK_LEFT)
					{
						player2right = false;
						player2left = true;
						player2down = false;
						player2up = false;
						
						if(!(player2X < 10))
							player2X-=10;
					}
					if(e.getKeyCode()== KeyEvent.VK_DOWN)
					{
						player2right = false;
						player2left = false;
						player2down = true;
						player2up = false;
						
						if(!(player2Y > 540))
							player2Y+=10;
					}
					if(e.getKeyCode()== KeyEvent.VK_RIGHT)
					{
						player2right = true;
						player2left = false;
						player2down = false;
						player2up = false;
						
						if(!(player2X > 590))
							player2X+=10;
					}
					
				}
				public void keyReleased(KeyEvent e) {
					
				}
				public void keyTyped(KeyEvent e) {
					
				}
			}
	}
	
	

	
	
	


