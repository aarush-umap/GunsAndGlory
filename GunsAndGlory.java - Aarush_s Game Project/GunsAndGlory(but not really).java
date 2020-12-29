/*
Aarush Umap
GunsAndGlory.java
4.23.19
REAL FILE
Version 5.13
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.ArrayList;

public class GunsAndGlory //main class
{
	
	public static final int SCREEN_HEIGHT=500;

	public static void main(String[] args) //main method
	{
		GunsAndGlory gng = new GunsAndGlory();
		gng.createAndShowGUI();
	}
	  private void createAndShowGUI() //creates frame and calls Cards class
	  {
	    JFrame frame = new JFrame("GunsAndGlory");
	    frame.setSize(1000, 600);
	    frame.setResizable(false);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    Cards demo = new Cards();
	    demo.addComponentToPane(frame.getContentPane());  
	    frame.setVisible(true);
	  }
}
	 
class Cards extends JPanel // sets up the CardLayout
{
	JPanel cards;
	StartPage startPage;
	PlayGame playGame;

	public void addComponentToPane(Container pane) //adds componenets(classes in this case) to CardLayout
	{
		startPage = new StartPage();
	  	startPage.addComponentToPane();
	  	playGame = new PlayGame();
	  	playGame.addComponentToPane();

	  	JPanel shopPage = new JPanel();
	  	JPanel instructionsPage = new JPanel();

		cards = new JPanel(new CardLayout());

	    cards.add(startPage, "Card with startPage");
	    cards.add(playGame, "Card with playGame");
	    cards.add(shopPage, "Card with shopPage");
	    cards.add(instructionsPage, "Card with instructionsPage");
	    
	    pane.add(cards, BorderLayout.CENTER);
	}


class StartPage extends JPanel implements ActionListener //card for StartPage
{
	JButton b1, b2, b3;

	Image backgroundPic = null;

	public void addComponentToPane() //sets background and buttons
	{

	  	setBackground(Color.RED);
	  	setOpaque(false);

		setLayout(new BorderLayout());
	  	JLabel title = new JLabel("Guns & Glory", JLabel.CENTER);


	  	//puts in blank spaces to make BorderLayout center fit well
	  	JLabel jl1 = new JLabel();
	  	jl1.setPreferredSize(new Dimension(1000,600/4));
	  	jl1.setOpaque(false);
		JLabel jl2 = new JLabel();
	  	jl2.setPreferredSize(new Dimension(1000,600/4));
	  	jl2.setOpaque(false);
	  	JLabel jl3 = new JLabel();
	  	jl3.setPreferredSize(new Dimension(1000/4,600));
	  	jl3.setOpaque(false);
	  	JLabel jl4 = new JLabel();
	  	jl4.setPreferredSize(new Dimension(1000/4,600));
	  	jl4.setOpaque(false);

	  	b1 = new JButton("Play");
	    b2 = new JButton("Shop");
	    b3 = new JButton("How to play");

	    b1.addActionListener(this);
	    b2.addActionListener(this);
	    b3.addActionListener(this);

	    JPanel centerStartPage = new JPanel();
	    centerStartPage.setBackground(Color.BLUE);
	    centerStartPage.setOpaque(false);

	    centerStartPage.setLayout(new GridLayout(4,1));
	    centerStartPage.add(title);
	    centerStartPage.add(b1);
	    centerStartPage.add(b2);
	    centerStartPage.add(b3);

	    add(jl1, BorderLayout.NORTH);
	    add(jl2, BorderLayout.SOUTH);
	    add(jl3, BorderLayout.WEST);
	    add(jl4, BorderLayout.EAST);
		add(centerStartPage, BorderLayout.CENTER);


		backgroundPic = new ImageIcon("japanesecity.gif").getImage();

	}

	public void paintComponent(Graphics g) //paints background
	{
		super.paintComponent(g);
		g.drawImage(backgroundPic, 0, 0, getWidth(), getHeight(), this);
		
	}

	public void actionPerformed(ActionEvent evt) //for changing cards
	{
		
		CardLayout cl = (CardLayout)(cards.getLayout());
	    if (evt.getActionCommand().equals("Play"))
	    {
	    	cl.show(cards, "Card with playGame");
	    	playGame.grabFocus();
	    }
	    if (evt.getActionCommand().equals("Shop"))
	    {
	    	cl.show(cards, "Card with shopPage");
	    }
	    if(evt.getActionCommand().equals("How to play"))
	    {
	    	cl.show(cards, "Card with instructionsPage");
	    }
	    
	}
}
}

class PlayGame extends JPanel implements KeyListener //card for PlayGame
{
	int xPos,yPos, bulletNum, countFromFloor;
	int rect1x, rect2x, rect1y, rect2y, rect1w, rect2w, rect1h, rect2h;
	ArrayList<Integer> bulletX, bulletCount;
	Player player;	
	Bullet[] bullets = new Bullet[10];
	Timer refreshTimer;
	boolean addBullet, isRightFace, isShooting, isJumping, isRunning, isFalling, fallenOff;
	Image character = null, bulletGif = null, bulletFlipped;
	Level1 level1 = null;
	Platform[] pf = new Platform[2];

	public void addComponentToPane() //acts as a constructor and creates panel
	{
		setSize(1000,600);
		addBullet = false;
		isRunning = false;
		isRightFace = true;
		isShooting = false;
		isJumping = false;
		isFalling = false;
		fallenOff = false;
 		player = new Player();
 		addKeyListener(this);
 		setFocusable(true);
        requestFocusInWindow();
        bulletX = new ArrayList<Integer>();
        bulletCount = new ArrayList<Integer>();
        character = new ImageIcon("rogue_like_run_Animation_1_1.png").getImage();
		bulletGif = new ImageIcon("bullet.gif").getImage();
        bulletFlipped = new ImageIcon("bulletFlipped.gif").getImage();
        refreshTimer = new Timer(10, player);
		refreshTimer.start();
		countFromFloor = 0;

		level1 = new Level1(this);
		level1.isActive = true;
	}

	public void paintComponent(Graphics g) //paints character and bullet
	{
		super.paintComponent(g);

		if(isJumping || fallenOff) //adds or subtracts yPos to animate jump
		{
			if(player.count < 21)
			{
				player.yPos-=10;
				player.count++;
				isFalling = false;
				//System.out.println("yPos = " + player.yPos);
			}
			else if(player.count == 21)
			{
				isFalling = true;
				player.yPos+=10;
				player.count++;
			}
			else if(player.count < 42 && isFalling)
			{
				// isFalling = true;
				player.yPos+=10;
				player.count++;
				
				//System.out.println("yPos = " + player.yPos);
			}
			else if(player.yPos != 500)
			{
				player.count = 42 - countFromFloor;
			}
			else if( player.yPos == 500)
			{
				isFalling = false;
				isJumping = false;
			}
		}

		if(isRunning) //adds or subtracts xPos to animate running
		{
			if(isRightFace)
			{
				player.xPos += 5;
				if(player.xPos >= getWidth())
				{
					level1.countStage++;
					player.xPos =0;
				}
			} else {
				player.xPos -= 5;
				if(player.xPos <= 0)
				{
					level1.countStage--;
					player.xPos = 999;
				}
			}
		}	



		if (level1.isActive) //background is set and changed
		{
			level1.drawLevel(g);
		}

		if (level1.countStage == 1) {
			pf[0] = new Platform(300, 450, 100, 150);
			rect1x = pf[0].px1-50; rect1y = pf[0].py-50; rect1w = pf[0].px2-20; rect1h = pf[0].ph;
			pf[1] = new Platform(490, 390, 50, 210);	
			rect2x = pf[1].px1-50; rect2y = pf[1].py-50; rect2w = pf[1].px2-20; rect2h = pf[1].ph;
		 	pf[0].drawPlatform(g);
		 	pf[1].drawPlatform(g);
				
			//System.out.println("Hey look ma i made it");

			//System.out.println("count: " + player.count);
			countFromFloor = ((500-player.yPos)/10);
			
			if((player.xPos >= rect1x && player.xPos <= rect1w) && (player.yPos == rect1y) && (isFalling))
			{
				isJumping = false;
				isFalling = false;
				player.yPos = rect1y;
				countFromFloor = ((500-player.yPos)/10);
				player.count = 42-countFromFloor;
			}
			else if(player.xPos < rect1x)
			{
				// countFromFloor = ((500-player.yPos)/10);
				// player.count = 42 - countFromFloor;
			 	fallenOff = true;
			 	isFalling = true;
			}
			else if ( player.xPos > rect1w && player.xPos < rect2x) 
			{
			 		isFalling = true;
			 		fallenOff = true;
			}
			else if (player.yPos == 500) {
			 		isFalling = false;
			}

			if(player.xPos >= rect1x && player.xPos<=rect1w && player.xPos < rect2x && player.yPos > rect1y)
			{
				if(isRightFace){
					player.xPos = rect1x;
					//System.out.println("isRightFace is true");
				}
				else{
					player.xPos = rect1w;
				}
			}

			if((player.xPos >= rect2x && player.xPos <= rect2w) && (player.yPos == rect2y) && (isFalling))
			{
				isJumping = false;
				isFalling = false;
				player.yPos = rect2y;
				countFromFloor = ((500-player.yPos)/10);
				player.count = 42-countFromFloor;
			}
			else if(player.xPos > rect1w && player.xPos < rect2x)
			{
				// countFromFloor = ((500-player.yPos)/10);
				// player.count = 42 - countFromFloor;
			 	fallenOff = true;
			 	isFalling = true;
			}
			else if (player.xPos > rect1w && player.xPos > rect2w) 
			{
			 		isFalling = true;
			 		fallenOff = true;
			}
			else if (player.yPos == 500) {
			 		isFalling = false;
			}

			if(player.xPos >= rect2x && player.xPos<=rect2w && player.yPos > rect2y)
			{
				if(isRightFace)
					player.xPos = rect2x;
				else
					player.xPos = rect2w;
			}			
		}
		else if (level1.countStage == 2) {
			pf[0] = new Platform(200, 400, 200, 200);
			rect1x = pf[0].px1-50; rect1y = pf[0].py-50; rect1w = pf[0].px2-20; rect1h = pf[0].ph;
			pf[1] = new Platform(600, 400, 200, 200);	
			rect2x = pf[1].px1-50; rect2y = pf[1].py-50; rect2w = pf[1].px2-20; rect2h = pf[1].ph;
		 	pf[0].drawPlatform(g);
		 	pf[1].drawPlatform(g);
				
			//System.out.println("Hey look ma i made it");

			//System.out.println("count: " + player.count);
			countFromFloor = ((500-player.yPos)/10);
			
			if((player.xPos >= rect1x && player.xPos <= rect1w) && (player.yPos == rect1y) && (isFalling))
			{
				isJumping = false;
				isFalling = false;
				player.yPos = rect1y;
				countFromFloor = ((500-player.yPos)/10);
				player.count = 42-countFromFloor;
			}
			else if(player.xPos < rect1x)
			{
				// countFromFloor = ((500-player.yPos)/10);
				// player.count = 42 - countFromFloor;
			 	fallenOff = true;
			 	isFalling = true;
			}
			else if ( player.xPos > rect1w && player.xPos < rect2x) 
			{
			 		isFalling = true;
			 		fallenOff = true;
			}
			else if (player.yPos == 500) {
			 		isFalling = false;
			}

			if(player.xPos >= rect1x && player.xPos<=rect1w && player.xPos < rect2x && player.yPos > rect1y)
			{
				if(isRightFace){
					player.xPos = rect1x;
					//System.out.println("isRightFace is true");
				}
				else{
					player.xPos = rect1w;
				}
			}



			if((player.xPos >= rect2x && player.xPos <= rect2w) && (player.yPos == rect2y) && (isFalling))
			{
				isJumping = false;
				isFalling = false;
				player.yPos = rect2y;
				countFromFloor = ((500-player.yPos)/10);
				player.count = 42-countFromFloor;
			}
			else if(player.xPos > rect1w && player.xPos < rect2x)
			{
				// countFromFloor = ((500-player.yPos)/10);
				// player.count = 42 - countFromFloor;
			 	fallenOff = true;
			 	isFalling = true;
			}
			else if (player.xPos > rect1w && player.xPos > rect2w) 
			{
			 		isFalling = true;
			 		fallenOff = true;
			}
			else if (player.yPos == 500) {
			 		isFalling = false;
			}

			if(player.xPos >= rect2x && player.xPos<=rect2w && player.yPos > rect2y)
			{
				if(isRightFace)
					player.xPos = rect2x;
				else
					player.xPos = rect2w;
			}			
		}
		else if (level1.countStage == 3) {
			pf[0] = new Platform(400, 500, 190, 100);
			rect1x = pf[0].px1-50; rect1y = pf[0].py-50; rect1w = pf[0].px2-20; rect1h = pf[0].ph;
			pf[1] = new Platform(600, 420, 200, 280);	
			rect2x = pf[1].px1-50; rect2y = pf[1].py-50; rect2w = pf[1].px2-20; rect2h = pf[1].ph;
		 	pf[0].drawPlatform(g);
		 	pf[1].drawPlatform(g);
				
			//System.out.println("Hey look ma i made it");

			//System.out.println("count: " + player.count);
			countFromFloor = ((500-player.yPos)/10);
			
			if((player.xPos >= rect1x && player.xPos <= rect1w) && (player.yPos == rect1y) && (isFalling))
			{
				isJumping = false;
				isFalling = false;
				player.yPos = rect1y;
				countFromFloor = ((500-player.yPos)/10);
				player.count = 42-countFromFloor;
			}
			else if(player.xPos < rect1x)
			{
				// countFromFloor = ((500-player.yPos)/10);
				// player.count = 42 - countFromFloor;
			 	fallenOff = true;
			 	isFalling = true;
			}
			else if ( player.xPos > rect1w && player.xPos < rect2x) 
			{
			 		isFalling = true;
			 		fallenOff = true;
			}
			else if (player.yPos == 500) {
			 		isFalling = false;
			}

			if(player.xPos >= rect1x && player.xPos<=rect1w && player.xPos < rect2x && player.yPos > rect1y)
			{
				if(isRightFace){
					player.xPos = rect1x;
					//System.out.println("isRightFace is true");
				}
				else{
					player.xPos = rect1w;
				}
			}



			if((player.xPos >= rect2x && player.xPos <= rect2w) && (player.yPos == rect2y) && (isFalling))
			{
				isJumping = false;
				isFalling = false;
				player.yPos = rect2y;
				countFromFloor = ((500-player.yPos)/10);
				player.count = 42-countFromFloor;
			}
			else if(player.xPos > rect1w && player.xPos < rect2x)
			{
				// countFromFloor = ((500-player.yPos)/10);
				// player.count = 42 - countFromFloor;
			 	fallenOff = true;
			 	isFalling = true;
			}
			else if (player.xPos > rect1w && player.xPos > rect2w) 
			{
			 		isFalling = true;
			 		fallenOff = true;
			}
			else if (player.yPos == 500) {
			 		isFalling = false;
			}

			if(player.xPos >= rect2x && player.xPos<=rect2w && player.yPos > rect2y)
			{
				if(isRightFace)
					player.xPos = rect2x;
				else
					player.xPos = rect2w;
			}			
		}



		player.drawPlayer(g);

		for(int i = 0; bullets.length > i; i++) //bullet animation
		{
			if(bullets[i] != null)
			{
				if(bullets[i].bRight){
					g.drawImage(bulletGif,bullets[i].bx, bullets[i].by, 25, 25, this);
					bullets[i].bx += 15;
					
					if(bullets[i].bx >= getWidth())
						bullets[i] = null;

				} else {
					g.drawImage(bulletFlipped,bullets[i].bx, bullets[i].by, 25, 25, this);
					bullets[i].bx -= 15;

					if(bullets[i].bx <= 0)
						bullets[i] = null;

				}
			}
		}
	}

	public void keyPressed(KeyEvent e) //movement keys
	{
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			isRightFace = false;
			isRunning = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			isRightFace = true;
			isRunning = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_UP)
		{
			if(!isJumping)
			{
				player.jump();
				isJumping = true;
			}
		}
	}
	public void keyTyped(KeyEvent e) //key for shooting bullets
	{
		if(e.getKeyChar() == ' ')
		{
			isShooting = true;
			player.shoot();
		}
		
	}
	public void keyReleased(KeyEvent e) //to know when player is not moving
	{
		if(e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			isRunning = false;
		}
	}

	class Player implements ActionListener //creates instance of player
	{
		int count; 
		int xPos = 100, yPos = 500;
		int spriteWidth = 80;
		int spriteHeight = 80;

		public void shoot() //method for shooting if spacebar is pressed 
		{
			addBullet = true;
			isShooting = true;
			bulletCount.add(0);
			bulletNum++;
			bulletX.add(xPos + 100);

			Bullet bullet = null;

			if (isRightFace) 
			{
				bullet = new Bullet(xPos + 25, yPos+25);
			}
			else
			{	
				bullet = new Bullet(xPos, yPos + 25);
			}

			for (int i = 0; i < bullets.length; i++) 
			{
				if (bullets[i] == null) 
				{
					bullets[i] = bullet;
					break;
				}
			}
		}

		public void drawPlayer(Graphics g) //draws character sprite
		{
			g.drawImage(character, xPos, yPos, spriteWidth, spriteHeight, null); 
		}

		public void jump() //method for jumping with up key
		{
			count = 0;
		}


		public void actionPerformed(ActionEvent e) //called by timer and makes character jump
		{
			repaint();		
		}	
	}

	class Bullet //creates instance of bullet and takes the x & y position for the start of the bullet
	{
		int bx, by;
		boolean bRight = isRightFace;

		public Bullet(int x, int y) 
		{
			this.bx = x;
			this.by = y;
		}
	}
	class Platform //creates instance of platform and takes the x & y position + height for the start of the platform
	{
		int px1, px2, py, pw, ph;
		

		public Platform(int x, int y, int width, int height) //constructor to set up dimensions
		{
			this.px1 = x;
			this.py = y;
			this.pw = width;
			this.ph = height;
			px2 = px1 + pw;
		}

		public void drawPlatform(Graphics g) //draws platform
		{
			g.fillRect(px1, py, pw, ph);
		}
	}
	class Level1 //draws the stage 
	{
		Image stageOdd = null, stageEven = null;
		boolean isActive = false;
		int countStage;
		PlayGame pg = null;
		

		public Level1(PlayGame playGame) //constructor sets up panel size and images
		{
			countStage =1; 
			stageOdd = new ImageIcon("stage1odd.png").getImage();
			stageEven = new ImageIcon("stage1even.png").getImage();
			pg = playGame;
		}

		public void drawLevel(Graphics g) //takes in JPanel's g and draws like paintComponent 
		{
			if(countStage >5 || countStage == 0) {
				isActive = false;
			}

			if(countStage%2 != 1) {
				g.drawImage(stageOdd, 0, 0, pg.getWidth(), pg.getHeight(), null);
			}
			else 
			{
				g.drawImage(stageEven, 0, 0, pg.getWidth(), pg.getHeight(), null);
			}
		}
	}
}