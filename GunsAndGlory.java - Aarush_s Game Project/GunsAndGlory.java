/*
Aarush Umap
GunsAndGlory.java
4.23.19
REAL FILE
Version 5.23
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
	ShopPage shopPage;
	InstructionsPage instructionsPage;
	Image sprite;
	CardLayout cl;
	boolean kim;
	Timer refreshTimer;



	public void addComponentToPane(Container pane) //adds componenets(classes in this case) to CardLayout
	{
		sprite = new ImageIcon("rogue_like_run_Animation_1_1.png").getImage();
		startPage = new StartPage();
	  	startPage.addComponentToPane();
	  	playGame = new PlayGame();
	  	playGame.addComponentToPane();
		shopPage = new ShopPage();
		shopPage.addComponentToPane();
		instructionsPage = new InstructionsPage();
		instructionsPage.addComponentToPane();
		

		cards = new JPanel(new CardLayout());

	    cards.add(startPage, "Card with startPage");
	    cards.add(playGame, "Card with playGame");
	    cards.add(shopPage, "Card with shopPage");
	    cards.add(instructionsPage, "Card with instructionsPage");
	    
	    pane.add(cards, BorderLayout.CENTER);
	}

class InstructionsPage extends JPanel implements ActionListener
{

	public void addComponentToPane()
	{
		setLayout(null);
		JButton backButton = new JButton("Back");
		backButton.setBackground(Color.BLUE);
		backButton.setForeground(Color.BLACK);
		backButton.addActionListener(this);
		add(backButton);
		backButton.setBounds(10,10,70,50);
	}
	
	public void actionPerformed(ActionEvent evt)
	{
		if(evt.getActionCommand().equals("Back"))
			cl.show(cards, "Card with startPage");
	}

	public void paintComponent(Graphics g)
	{
		g.drawImage(new ImageIcon("instructions.png").getImage(),0,0,getWidth(),getHeight(), null);
	}
	
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
	  	title.setFont(new Font("VCR OSD Mono", Font.PLAIN, 40));

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
	  	b1.setFont(new Font("VCR OSD Mono", Font.PLAIN, 25));
	        b2 = new JButton("Character Select");
	        b2.setFont(new Font("VCR OSD Mono", Font.PLAIN, 25));
	        b3 = new JButton("How to play");
	        b3.setFont(new Font("VCR OSD Mono", Font.PLAIN, 25));

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
		
		cl = (CardLayout)(cards.getLayout());
	    if (evt.getActionCommand().equals("Play"))
	    {
	    	cl.show(cards, "Card with playGame");
	    	playGame.grabFocus();
	    }
	    if (evt.getActionCommand().equals("Character Select"))
	    {
	    	cl.show(cards, "Card with shopPage");
	    }
	    if(evt.getActionCommand().equals("How to play"))
	    {
	    	cl.show(cards, "Card with instructionsPage");
	    }
	    
	}
}


class PlayGame extends JPanel implements KeyListener//card for PlayGame
{
	int xPos,yPos, bulletNum, countFromFloor;
	int rect1x, rect2x, rect1y, rect2y, rect1w, rect2w, rect1h, rect2h;
	ArrayList<Integer> bulletX, bulletCount;
	Player player;	
	Bullet[] bullets = new Bullet[10];
	int hitCount = 0;
	double healthPercent;
	int health;
	boolean addBullet, isRightFace, isShooting, isJumping, isRunning, isFalling, fallenOff, bossLvl = false;
	Image character = null, bulletGif = null, bulletFlipped, theGif = null;;
	Level1 level1 = null;
	Platform[] pf = new Platform[2];
	int planeCount = 1000;
	int planeCount2 = 1000;
	BadGuy[] bg = new BadGuy[22];

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
		bulletGif = new ImageIcon("bullet.gif").getImage();
        bulletFlipped = new ImageIcon("bulletFlipped.gif").getImage();
        refreshTimer = new Timer(10, player);
		refreshTimer.start();
		countFromFloor = 0;

		level1 = new Level1(this);
		level1.isActive = true;

		bg[0] = new BadGuy(650, 500, 100); //sets bad guy locations
		bg[1] = new BadGuy(300, 400, 50);
		
		bg[2] = new BadGuy(200, 350, 150);
		bg[3] = new BadGuy(600, 350, 150);
		
		bg[4] = new BadGuy(300, 500, 40);
		bg[5] = new BadGuy(600, 370, 150);
		
		bg[6] = new BadGuy(200, 300, 150);
		bg[7] = new BadGuy(600, 450, 50);
		
		bg[8] = new BadGuy(100, 450, 250);
		bg[9] = new BadGuy(600, 500, 50);
		
		bg[10] = new BadGuy(200, 340, 0);
		bg[11] = new BadGuy(600, 500, 50);
		
		bg[12] = new BadGuy(400, 440, 100);
		bg[13] = new BadGuy(700, 370, 50);
		
		bg[14] = new BadGuy(300, 350, 150);
		bg[15] = new BadGuy(580, 450, 250);
		
		bg[16] = new BadGuy(300, 350, 50);
		bg[17] = new BadGuy(700, 400, 100);
		
		bg[18] = new BadGuy(200, 340, 150);
		bg[19] = new BadGuy(460, 340, 250);

		bg[20] = new BadGuy(160, 480, 650/2);
		bg[21] = new BadGuy(160, 480, 650/2);

		theGif = new ImageIcon("mBOLoZc.gif").getImage();
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
		

		if(level1.countStage == 0)
		{
			player.xPos = 0;
			level1.countStage = 1;
		}



		//create logic for stages and levels as well as bad guys

		if (level1.countStage == 1) {



			for(int k = 0; k <= 1; k++)	
			{
	 			if(bg[k].bgIsAlive)
				{	
		 			if(bg[k].count<=bg[k].dist && !bg[k].turned)
					{
						g.drawImage(bg[k].drone, bg[k].xPos, bg[k].yPos, 50,50, null);
						bg[k].count++;
						bg[k].xPos++;
						if(bg[k].count == bg[k].dist){
							bg[k].turned = true;
							bg[k].count++;
						}
					}
					else if(bg[k].count>bg[k].dist || bg[k].turned)
					{
						g.drawImage(bg[k].drone,bg[k].xPos, bg[k].yPos, 50,50, null);
						bg[k].count--;
						bg[k].xPos--;
						if(bg[k].count == 0)
							bg[k].turned =false;
					}
					bg[k].explosionCount = 1;
					if(bg[k] != null &&
					bg[k].xPos <= player.xPos+50 &&
					bg[k].xPos+50 >= player.xPos &&
					bg[k].yPos <= player.yPos+50 &&
					bg[k].yPos + 50 >= player.yPos)
					{
						player.xPos = 0;
						level1.countStage = 1;
					}
					}
				

				else
				{

					if(bg[k].explosionCount == 1)
						g.drawImage(new ImageIcon("enemy-explosion-1.png").getImage(), bg[k].xPos, bg[k].yPos, 50, 50, null);
					else if(bg[k].explosionCount == 2)
						g.drawImage(new ImageIcon("enemy-explosion-2.png").getImage(), bg[k].xPos, bg[k].yPos, 50, 50, null);
					else if(bg[k].explosionCount == 3)
						g.drawImage(new ImageIcon("enemy-explosion-3.png").getImage(), bg[k].xPos, bg[k].yPos, 50, 50, null);
					else if(bg[k].explosionCount == 4)
						g.drawImage(new ImageIcon("enemy-explosion-4.png").getImage(), bg[k].xPos, bg[k].yPos, 50, 50, null);
					else if(bg[k].explosionCount == 5)
						g.drawImage(new ImageIcon("enemy-explosion-5.png").getImage(), bg[k].xPos, bg[k].yPos, 50, 50, null);
					else if(bg[k].explosionCount == 6)
						g.drawImage(new ImageIcon("enemy-explosion-6.png").getImage(), bg[k].xPos, bg[k].yPos, 50, 50, null);

					bg[k].explosionCount++;
				}
			}





			pf[0] = new Platform(300, 450, 100, 150);
			rect1x = pf[0].px1-50; rect1y = pf[0].py-50; rect1w = pf[0].px2; rect1h = pf[0].ph;
			pf[1] = new Platform(490, 390, 50, 210);	
			rect2x = pf[1].px1-50; rect2y = pf[1].py-50; rect2w = pf[1].px2; rect2h = pf[1].ph;
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


			if(planeCount >= -250)
			{
				g.drawImage(new ImageIcon("plane.png").getImage(), planeCount, 100, 250, 63, null);
				planeCount-=2;
			}

			for(int b = 2; b < 4; b++)	
			{
	 			if(bg[b].bgIsAlive)
				{	
		 			if(bg[b].count<=bg[b].dist && !bg[b].turned)
					{
						g.drawImage(bg[b].drone, bg[b].xPos, bg[b].yPos, 50,50, null);
						bg[b].count++;
						bg[b].xPos++;
						if(bg[b].count == bg[b].dist){
							bg[b].turned = true;
							bg[b].count++;
						}
					}
					else if(bg[b].count>bg[b].dist || bg[b].turned)
					{
						g.drawImage(bg[b].drone,bg[b].xPos, bg[b].yPos, 50,50, null);
						bg[b].count--;
						bg[b].xPos--;
						if(bg[b].count == 0)
							bg[b].turned =false;
					}

					if(bg[b] != null &&
					bg[b].xPos <= player.xPos+50 &&
					bg[b].xPos+50 >= player.xPos &&
					bg[b].yPos <= player.yPos+50 &&
					bg[b].yPos + 50 >= player.yPos)
					{
						player.xPos = 0;
						level1.countStage = 1;
					}

					bg[b].explosionCount = 1;
				}

				else
				{
					if(bg[b].explosionCount == 1)
						g.drawImage(new ImageIcon("enemy-explosion-1.png").getImage(), bg[b].xPos, bg[b].yPos, 50, 50, null);
					else if(bg[b].explosionCount == 2)
						g.drawImage(new ImageIcon("enemy-explosion-2.png").getImage(), bg[b].xPos, bg[b].yPos, 50, 50, null);
					else if(bg[b].explosionCount == 3)
						g.drawImage(new ImageIcon("enemy-explosion-3.png").getImage(), bg[b].xPos, bg[b].yPos, 50, 50, null);
					else if(bg[b].explosionCount == 4)
						g.drawImage(new ImageIcon("enemy-explosion-4.png").getImage(), bg[b].xPos, bg[b].yPos, 50, 50, null);
					else if(bg[b].explosionCount == 5)
						g.drawImage(new ImageIcon("enemy-explosion-5.png").getImage(), bg[b].xPos, bg[b].yPos, 50, 50, null);
					else if(bg[b].explosionCount == 6)
						g.drawImage(new ImageIcon("enemy-explosion-6.png").getImage(), bg[b].xPos, bg[b].yPos, 50, 50, null);

					bg[b].explosionCount++;
				}
			}



			pf[0] = new Platform(200, 400, 200, 200);
			rect1x = pf[0].px1-50; rect1y = pf[0].py-50; rect1w = pf[0].px2; rect1h = pf[0].ph;
			pf[1] = new Platform(600, 400, 200, 200);	
			rect2x = pf[1].px1-50; rect2y = pf[1].py-50; rect2w = pf[1].px2; rect2h = pf[1].ph;
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

			for(int c = 4; c < 6; c++)	
			{
	 			if(bg[c].bgIsAlive)
				{	
		 			if(bg[c].count<=bg[c].dist && !bg[c].turned)
					{
						g.drawImage(bg[c].drone, bg[c].xPos, bg[c].yPos, 50,50, null);
						bg[c].count++;
						bg[c].xPos++;
						if(bg[c].count == bg[c].dist){
							bg[c].turned = true;
							bg[c].count++;
						}
					}
					else if(bg[c].count>bg[c].dist || bg[c].turned)
					{
						g.drawImage(bg[c].drone,bg[c].xPos, bg[c].yPos, 50,50, null);
						bg[c].count--;
						bg[c].xPos--;
						if(bg[c].count == 0)
							bg[c].turned =false;
					}



					if(bg[c] != null &&
					bg[c].xPos <= player.xPos+50 &&
					bg[c].xPos+50 >= player.xPos &&
					bg[c].yPos <= player.yPos+50 &&
					bg[c].yPos + 50 >= player.yPos)
					{
						player.xPos = 0;
						level1.countStage = 1;
					}



					bg[c].explosionCount = 1;
				}

				else
				{

					if(bg[c].explosionCount == 1)
						g.drawImage(new ImageIcon("enemy-explosion-1.png").getImage(), bg[c].xPos, bg[c].yPos, 50, 50, null);
					else if(bg[c].explosionCount == 2)
						g.drawImage(new ImageIcon("enemy-explosion-2.png").getImage(), bg[c].xPos, bg[c].yPos, 50, 50, null);
					else if(bg[c].explosionCount == 3)
						g.drawImage(new ImageIcon("enemy-explosion-3.png").getImage(), bg[c].xPos, bg[c].yPos, 50, 50, null);
					else if(bg[c].explosionCount == 4)
						g.drawImage(new ImageIcon("enemy-explosion-4.png").getImage(), bg[c].xPos, bg[c].yPos, 50, 50, null);
					else if(bg[c].explosionCount == 5)
						g.drawImage(new ImageIcon("enemy-explosion-5.png").getImage(), bg[c].xPos, bg[c].yPos, 50, 50, null);
					else if(bg[c].explosionCount == 6)
						g.drawImage(new ImageIcon("enemy-explosion-6.png").getImage(), bg[c].xPos, bg[c].yPos, 50, 50, null);
					bg[c].explosionCount++;
				}
			}


			pf[0] = new Platform(400, 500, 140, 100);
			rect1x = pf[0].px1-50; rect1y = pf[0].py-50; rect1w = pf[0].px2; rect1h = pf[0].ph;
			pf[1] = new Platform(600, 420, 200, 280);	
			rect2x = pf[1].px1-50; rect2y = pf[1].py-50; rect2w = pf[1].px2; rect2h = pf[1].ph;
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

		else if (level1.countStage == 4) {

			for(int d = 6; d < 8; d++)	
			{
				if(bg[d].bgIsAlive)
				{	
					if(bg[d].count<=bg[d].dist && !bg[d].turned)
					{
					g.drawImage(bg[d].drone, bg[d].xPos, bg[d].yPos, 50,50, null);
					bg[d].count++;
					bg[d].xPos++;
					if(bg[d].count == bg[d].dist){
						bg[d].turned = true;
						bg[d].count++;
					}
				}
				else if(bg[d].count>bg[d].dist || bg[d].turned)
				{
					g.drawImage(bg[d].drone,bg[d].xPos, bg[d].yPos, 50,50, null);
					bg[d].count--;
					bg[d].xPos--;
					if(bg[d].count == 0)
						bg[d].turned =false;
					}


					if(bg[d] != null &&
					bg[d].xPos <= player.xPos+50 &&
					bg[d].xPos+50 >= player.xPos &&
					bg[d].yPos <= player.yPos+50 &&
					bg[d].yPos + 50 >= player.yPos)
					{
						player.xPos = 0;
						level1.countStage = 1;
					}

					bg[d].explosionCount = 1;
				}


				else
				{

					if(bg[d].explosionCount == 1)
						g.drawImage(new ImageIcon("enemy-explosion-1.png").getImage(), bg[d].xPos, bg[d].yPos, 50, 50, null);
					else if(bg[d].explosionCount == 2)
						g.drawImage(new ImageIcon("enemy-explosion-2.png").getImage(), bg[d].xPos, bg[d].yPos, 50, 50, null);
					else if(bg[d].explosionCount == 3)
						g.drawImage(new ImageIcon("enemy-explosion-3.png").getImage(), bg[d].xPos, bg[d].yPos, 50, 50, null);
					else if(bg[d].explosionCount == 4)
						g.drawImage(new ImageIcon("enemy-explosion-4.png").getImage(), bg[d].xPos, bg[d].yPos, 50, 50, null);
					else if(bg[d].explosionCount == 5)
						g.drawImage(new ImageIcon("enemy-explosion-5.png").getImage(), bg[d].xPos, bg[d].yPos, 50, 50, null);
					else if(bg[d].explosionCount == 6)
						g.drawImage(new ImageIcon("enemy-explosion-6.png").getImage(), bg[d].xPos, bg[d].yPos, 50, 50, null);
					bg[d].explosionCount++;
				}
			}


			pf[0] = new Platform(200, 350, 200, 300);
			rect1x = pf[0].px1-50; rect1y = pf[0].py-50; rect1w = pf[0].px2; rect1h = pf[0].ph;
			pf[1] = new Platform(600, 500, 100, 100);	
			rect2x = pf[1].px1-50; rect2y = pf[1].py-50; rect2w = pf[1].px2; rect2h = pf[1].ph;
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

		else if (level1.countStage == 5) {


			for(int e = 8; e < 10; e++)	
			{
				if(bg[e].bgIsAlive)
				{	
					if(bg[e].count<=bg[e].dist && !bg[e].turned)
					{
					g.drawImage(bg[e].drone, bg[e].xPos, bg[e].yPos, 50,50, null);
					bg[e].count++;
					bg[e].xPos++;
					if(bg[e].count == bg[e].dist){
						bg[e].turned = true;
						bg[e].count++;
					}
				}
					else if(bg[e].count>bg[e].dist || bg[e].turned)
					{
						g.drawImage(bg[e].drone,bg[e].xPos, bg[e].yPos, 50,50, null);
						bg[e].count--;
						bg[e].xPos--;
						if(bg[e].count == 0)
							bg[e].turned =false;
					}


					if(bg[e] != null &&
					bg[e].xPos <= player.xPos+50 &&
					bg[e].xPos+50 >= player.xPos &&
					bg[e].yPos <= player.yPos+50 &&
					bg[e].yPos + 50 >= player.yPos)
					{
						player.xPos = 0;
						level1.countStage = 1;
					}

					bg[e].explosionCount = 1;
				}


				else
				{

					if(bg[e].explosionCount == 1)
						g.drawImage(new ImageIcon("enemy-explosion-1.png").getImage(), bg[e].xPos, bg[e].yPos, 50, 50, null);
					else if(bg[e].explosionCount == 2)
						g.drawImage(new ImageIcon("enemy-explosion-2.png").getImage(), bg[e].xPos, bg[e].yPos, 50, 50, null);
					else if(bg[e].explosionCount == 3)
						g.drawImage(new ImageIcon("enemy-explosion-3.png").getImage(), bg[e].xPos, bg[e].yPos, 50, 50, null);
					else if(bg[e].explosionCount == 4)
						g.drawImage(new ImageIcon("enemy-explosion-4.png").getImage(), bg[e].xPos, bg[e].yPos, 50, 50, null);
					else if(bg[e].explosionCount == 5)
						g.drawImage(new ImageIcon("enemy-explosion-5.png").getImage(), bg[e].xPos, bg[e].yPos, 50, 50, null);
					else if(bg[e].explosionCount == 6)
						g.drawImage(new ImageIcon("enemy-explosion-6.png").getImage(), bg[e].xPos, bg[e].yPos, 50, 50, null);
					bg[e].explosionCount++;
				}
			}

			pf[0] = new Platform(100, 500, 300, 100);
			rect1x = pf[0].px1-50; rect1y = pf[0].py-50; rect1w = pf[0].px2; rect1h = pf[0].ph;
			pf[1] = new Platform(470, 390, 50, 210);	
			rect2x = pf[1].px1-50; rect2y = pf[1].py-50; rect2w = pf[1].px2; rect2h = pf[1].ph;
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

		else if(level1.countStage == 6)
		{
			if(planeCount2 >= -250)
			{
				g.drawImage(new ImageIcon("kimPlane.png").getImage(), planeCount2, 100, 250, 63, null);
				planeCount2-=2;
				if(planeCount2 <= player.xPos+50 &&
				planeCount2+250 >= player.xPos &&
				100 <= player.yPos &&
				163 >= player.yPos)
				{
					level1.countStage =73;
				}
			}

			for(int e = 10; e < 12; e++)	
			{
				if(bg[e].bgIsAlive)
				{	
					if(bg[e].count<=bg[e].dist && !bg[e].turned)
					{
						g.drawImage(bg[e].drone, bg[e].xPos, bg[e].yPos, 50,50, null);
						bg[e].count++;
						bg[e].xPos++;
						if(bg[e].count == bg[e].dist){
							bg[e].turned = true;
							bg[e].count++;
						}
					}
					else if(bg[e].count>bg[e].dist || bg[e].turned)
					{
						g.drawImage(bg[e].drone,bg[e].xPos, bg[e].yPos, 50,50, null);
						bg[e].count--;
						bg[e].xPos--;
						if(bg[e].count == 0)
							bg[e].turned =false;
					}


					if(bg[e] != null &&
					bg[e].xPos <= player.xPos+50 &&
					bg[e].xPos+50 >= player.xPos &&
					bg[e].yPos <= player.yPos+50 &&
					bg[e].yPos + 50 >= player.yPos)
					{
						player.xPos = 0;
						level1.countStage = 6;
					}


					bg[e].explosionCount = 1;
				}


				else
				{

					if(bg[e].explosionCount == 1)
						g.drawImage(new ImageIcon("enemy-explosion-1.png").getImage(), bg[e].xPos, bg[e].yPos, 50, 50, null);
					else if(bg[e].explosionCount == 2)
						g.drawImage(new ImageIcon("enemy-explosion-2.png").getImage(), bg[e].xPos, bg[e].yPos, 50, 50, null);
					else if(bg[e].explosionCount == 3)
						g.drawImage(new ImageIcon("enemy-explosion-3.png").getImage(), bg[e].xPos, bg[e].yPos, 50, 50, null);
					else if(bg[e].explosionCount == 4)
						g.drawImage(new ImageIcon("enemy-explosion-4.png").getImage(), bg[e].xPos, bg[e].yPos, 50, 50, null);
					else if(bg[e].explosionCount == 5)
						g.drawImage(new ImageIcon("enemy-explosion-5.png").getImage(), bg[e].xPos, bg[e].yPos, 50, 50, null);
					else if(bg[e].explosionCount == 6)
						g.drawImage(new ImageIcon("enemy-explosion-6.png").getImage(), bg[e].xPos, bg[e].yPos, 50, 50, null);
					bg[e].explosionCount++;
				}
			}

			pf[0] = new Platform(200, 390, 50, 210);
			rect1x = pf[0].px1-50; rect1y = pf[0].py-50; rect1w = pf[0].px2; rect1h = pf[0].ph;
			pf[1] = new Platform(500, 390, 50, 210);	
			rect2x = pf[1].px1-50; rect2y = pf[1].py-50; rect2w = pf[1].px2; rect2h = pf[1].ph;
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
		}

		if(level1.countStage == 7)
		{
			for(int f = 12; f < 14; f++)	
			{
				if(bg[f].bgIsAlive)
				{	
					if(bg[f].count<=bg[f].dist && !bg[f].turned)
					{
					g.drawImage(bg[f].drone, bg[f].xPos, bg[f].yPos, 50,50, null);
					bg[f].count++;
					bg[f].xPos++;
					if(bg[f].count == bg[f].dist){
						bg[f].turned = true;
						bg[f].count++;
					}
				}
				else if(bg[f].count>bg[f].dist || bg[f].turned)
				{
					g.drawImage(bg[f].drone,bg[f].xPos, bg[f].yPos, 50,50, null);
					bg[f].count--;
					bg[f].xPos--;
					if(bg[f].count == 0)
						bg[f].turned =false;
					}




					if(bg[f] != null &&
					bg[f].xPos <= player.xPos+50 &&
					bg[f].xPos+50 >= player.xPos &&
					bg[f].yPos <= player.yPos+50 &&
					bg[f].yPos + 50 >= player.yPos)
					{
						player.xPos = 0;
						level1.countStage = 6;
					}


					bg[f].explosionCount = 1;
				}


				else
				{

					if(bg[f].explosionCount == 1)
						g.drawImage(new ImageIcon("enemy-explosion-1.png").getImage(), bg[f].xPos, bg[f].yPos, 50, 50, null);
					else if(bg[f].explosionCount == 2)
						g.drawImage(new ImageIcon("enemy-explosion-2.png").getImage(), bg[f].xPos, bg[f].yPos, 50, 50, null);
					else if(bg[f].explosionCount == 3)
						g.drawImage(new ImageIcon("enemy-explosion-3.png").getImage(), bg[f].xPos, bg[f].yPos, 50, 50, null);
					else if(bg[f].explosionCount == 4)
						g.drawImage(new ImageIcon("enemy-explosion-4.png").getImage(), bg[f].xPos, bg[f].yPos, 50, 50, null);
					else if(bg[f].explosionCount == 5)
						g.drawImage(new ImageIcon("enemy-explosion-5.png").getImage(), bg[f].xPos, bg[f].yPos, 50, 50, null);
					else if(bg[f].explosionCount == 6)
						g.drawImage(new ImageIcon("enemy-explosion-6.png").getImage(), bg[f].xPos, bg[f].yPos, 50, 50, null);
					bg[f].explosionCount++;
				}
			}

			pf[0] = new Platform(400, 490, 150, 110);
			rect1x = pf[0].px1-50; rect1y = pf[0].py-50; rect1w = pf[0].px2; rect1h = pf[0].ph;
			pf[1] = new Platform(700, 420, 100, 180);	
			rect2x = pf[1].px1-50; rect2y = pf[1].py-50; rect2w = pf[1].px2; rect2h = pf[1].ph;
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
		

		else if(level1.countStage == 8)
		{
			for(int f = 14; f < 16; f++)	
			{
				if(bg[f].bgIsAlive)
				{	
					if(bg[f].count<=bg[f].dist && !bg[f].turned)
					{
					g.drawImage(bg[f].drone, bg[f].xPos, bg[f].yPos, 50,50, null);
					bg[f].count++;
					bg[f].xPos++;
					if(bg[f].count == bg[f].dist){
						bg[f].turned = true;
						bg[f].count++;
					}
					}
					else if(bg[f].count>bg[f].dist || bg[f].turned)
					{
					g.drawImage(bg[f].drone,bg[f].xPos, bg[f].yPos, 50,50, null);
					bg[f].count--;
					bg[f].xPos--;
					if(bg[f].count == 0)
						bg[f].turned =false;
					}


					if(bg[f] != null &&
					bg[f].xPos <= player.xPos+50 &&
					bg[f].xPos+50 >= player.xPos &&
					bg[f].yPos <= player.yPos+50 &&
					bg[f].yPos + 50 >= player.yPos)
					{
						player.xPos = 0;
						level1.countStage = 6;
					}



					bg[f].explosionCount = 1;
				}


				else
				{

					if(bg[f].explosionCount == 1)
						g.drawImage(new ImageIcon("enemy-explosion-1.png").getImage(), bg[f].xPos, bg[f].yPos, 50, 50, null);
					else if(bg[f].explosionCount == 2)
						g.drawImage(new ImageIcon("enemy-explosion-2.png").getImage(), bg[f].xPos, bg[f].yPos, 50, 50, null);
					else if(bg[f].explosionCount == 3)
						g.drawImage(new ImageIcon("enemy-explosion-3.png").getImage(), bg[f].xPos, bg[f].yPos, 50, 50, null);
					else if(bg[f].explosionCount == 4)
						g.drawImage(new ImageIcon("enemy-explosion-4.png").getImage(), bg[f].xPos, bg[f].yPos, 50, 50, null);
					else if(bg[f].explosionCount == 5)
						g.drawImage(new ImageIcon("enemy-explosion-5.png").getImage(), bg[f].xPos, bg[f].yPos, 50, 50, null);
					else if(bg[f].explosionCount == 6)
						g.drawImage(new ImageIcon("enemy-explosion-6.png").getImage(), bg[f].xPos, bg[f].yPos, 50, 50, null);
					bg[f].explosionCount++;
				}
			}

			pf[0] = new Platform(300, 400, 200, 200);
			rect1x = pf[0].px1-50; rect1y = pf[0].py-50; rect1w = pf[0].px2; rect1h = pf[0].ph;
			pf[1] = new Platform(580, 500, 300, 100);	
			rect2x = pf[1].px1-50; rect2y = pf[1].py-50; rect2w = pf[1].px2; rect2h = pf[1].ph;
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
		
		else if(level1.countStage == 9)
		{
			for(int f = 16; f < 18; f++)	
			{
				if(bg[f].bgIsAlive)
				{	
					if(bg[f].count<=bg[f].dist && !bg[f].turned)
					{
					g.drawImage(bg[f].drone, bg[f].xPos, bg[f].yPos, 50,50, null);
					bg[f].count++;
					bg[f].xPos++;
					if(bg[f].count == bg[f].dist){
						bg[f].turned = true;
						bg[f].count++;
					}
					}
					else if(bg[f].count>bg[f].dist || bg[f].turned)
					{
					g.drawImage(bg[f].drone,bg[f].xPos, bg[f].yPos, 50,50, null);
					bg[f].count--;
					bg[f].xPos--;
					if(bg[f].count == 0)
						bg[f].turned =false;
					}



					if(bg[f] != null &&
					bg[f].xPos <= player.xPos+50 &&
					bg[f].xPos+50 >= player.xPos &&
					bg[f].yPos <= player.yPos+50 &&
					bg[f].yPos + 50 >= player.yPos)
					{
						player.xPos = 0;
						level1.countStage = 6;
					}



					bg[f].explosionCount = 1;
				}


				else
				{

					if(bg[f].explosionCount == 1)
						g.drawImage(new ImageIcon("enemy-explosion-1.png").getImage(), bg[f].xPos, bg[f].yPos, 50, 50, null);
					else if(bg[f].explosionCount == 2)
						g.drawImage(new ImageIcon("enemy-explosion-2.png").getImage(), bg[f].xPos, bg[f].yPos, 50, 50, null);
					else if(bg[f].explosionCount == 3)
						g.drawImage(new ImageIcon("enemy-explosion-3.png").getImage(), bg[f].xPos, bg[f].yPos, 50, 50, null);
					else if(bg[f].explosionCount == 4)
						g.drawImage(new ImageIcon("enemy-explosion-4.png").getImage(), bg[f].xPos, bg[f].yPos, 50, 50, null);
					else if(bg[f].explosionCount == 5)
						g.drawImage(new ImageIcon("enemy-explosion-5.png").getImage(), bg[f].xPos, bg[f].yPos, 50, 50, null);
					else if(bg[f].explosionCount == 6)
						g.drawImage(new ImageIcon("enemy-explosion-6.png").getImage(), bg[f].xPos, bg[f].yPos, 50, 50, null);
					bg[f].explosionCount++;
				}
			}

			pf[0] = new Platform(300, 400, 100, 200);
			rect1x = pf[0].px1-50; rect1y = pf[0].py-50; rect1w = pf[0].px2; rect1h = pf[0].ph;
			pf[1] = new Platform(700, 450, 200, 150);	
			rect2x = pf[1].px1-50; rect2y = pf[1].py-50; rect2w = pf[1].px2; rect2h = pf[1].ph;
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
		
		else if(level1.countStage == 10)
		{
			for(int f = 18; f < 20; f++)	
			{
				if(bg[f].bgIsAlive)
				{	
					if(bg[f].count<=bg[f].dist && !bg[f].turned)
					{
					g.drawImage(bg[f].drone, bg[f].xPos, bg[f].yPos, 50,50, null);
					bg[f].count++;
					bg[f].xPos++;
					if(bg[f].count == bg[f].dist){
						bg[f].turned = true;
						bg[f].count++;
					}
					}
					else if(bg[f].count>bg[f].dist || bg[f].turned)
					{
					g.drawImage(bg[f].drone,bg[f].xPos, bg[f].yPos, 50,50, null);
					bg[f].count--;
					bg[f].xPos--;
					if(bg[f].count == 0)
						bg[f].turned =false;
					}


					if(bg[f] != null &&
					bg[f].xPos <= player.xPos+50 &&
					bg[f].xPos+50 >= player.xPos &&
					bg[f].yPos <= player.yPos+50 &&
					bg[f].yPos + 50 >= player.yPos)
					{
						player.xPos = 0;
						level1.countStage = 6;
					}

					bg[f].explosionCount = 1;
				}


				else
				{

					if(bg[f].explosionCount == 1)
						g.drawImage(new ImageIcon("enemy-explosion-1.png").getImage(), bg[f].xPos, bg[f].yPos, 50, 50, null);
					else if(bg[f].explosionCount == 2)
						g.drawImage(new ImageIcon("enemy-explosion-2.png").getImage(), bg[f].xPos, bg[f].yPos, 50, 50, null);
					else if(bg[f].explosionCount == 3)
						g.drawImage(new ImageIcon("enemy-explosion-3.png").getImage(), bg[f].xPos, bg[f].yPos, 50, 50, null);
					else if(bg[f].explosionCount == 4)
						g.drawImage(new ImageIcon("enemy-explosion-4.png").getImage(), bg[f].xPos, bg[f].yPos, 50, 50, null);
					else if(bg[f].explosionCount == 5)
						g.drawImage(new ImageIcon("enemy-explosion-5.png").getImage(), bg[f].xPos, bg[f].yPos, 50, 50, null);
					else if(bg[f].explosionCount == 6)
						g.drawImage(new ImageIcon("enemy-explosion-6.png").getImage(), bg[f].xPos, bg[f].yPos, 50, 50, null);
					bg[f].explosionCount++;
				}
			}

			pf[0] = new Platform(200, 390, 200, 210);
			rect1x = pf[0].px1-50; rect1y = pf[0].py-50; rect1w = pf[0].px2; rect1h = pf[0].ph;
			pf[1] = new Platform(460, 390, 300, 210);	
			rect2x = pf[1].px1-50; rect2y = pf[1].py-50; rect2w = pf[1].px2; rect2h = pf[1].ph;
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

		else if(level1.countStage == 73)
		{
			bossLvl =true;

			healthPercent = ((300.00-hitCount)/300.00);
			health = (int)(600*healthPercent);
			g.setColor(Color.RED);
			g.fillRect(200,50, 600, 25);
			g.setColor(Color.GREEN);
			g.fillRect(200,50, health, 25);


			for(int f = 20; f < 22; f++)	
			{
				if(bg[f].bgIsAlive)
				{	
					if(bg[f].count<=bg[f].dist && !bg[f].turned)
					{
					g.drawImage(bg[f].me, bg[f].xPos, bg[f].yPos, 70,70, null);
					bg[f].count++;
					bg[f].xPos+=2;
					if(bg[f].count == bg[f].dist){
						bg[f].turned = true;
						bg[f].count++;
					}
					}
					else if(bg[f].count>bg[f].dist || bg[f].turned)
					{
					g.drawImage(bg[f].me,bg[f].xPos, bg[f].yPos, 70,70, null);
					bg[f].count--;
					bg[f].xPos-=2;
					if(bg[f].count == 0)
						bg[f].turned =false;
					}


					if(bg[f] != null &&
					bg[f].xPos <= player.xPos+50 &&
					bg[f].xPos+70 >= player.xPos &&
					bg[f].yPos <= player.yPos+50 &&
					bg[f].yPos + 70 >= player.yPos)
					{
						player.xPos = 150;
						level1.countStage = 73;
					}

					bg[f].explosionCount = 1;
				}


				else
				{

					if(bg[f].explosionCount >= 1)
						g.drawImage(new ImageIcon("enemy-explosion-1.png").getImage(), bg[f].xPos, bg[f].yPos, 90, 90, null);
					else if(bg[f].explosionCount >= 3)
						g.drawImage(new ImageIcon("enemy-explosion-2.png").getImage(), bg[f].xPos, bg[f].yPos, 90, 90, null);
					else if(bg[f].explosionCount >= 5)
						g.drawImage(new ImageIcon("enemy-explosion-3.png").getImage(), bg[f].xPos, bg[f].yPos, 90, 90, null);
					else if(bg[f].explosionCount >= 7)
						g.drawImage(new ImageIcon("enemy-explosion-4.png").getImage(), bg[f].xPos, bg[f].yPos, 90, 90, null);
					else if(bg[f].explosionCount >= 9)
						g.drawImage(new ImageIcon("enemy-explosion-5.png").getImage(), bg[f].xPos, bg[f].yPos, 90, 90, null);
					else if(bg[f].explosionCount == 12)
						g.drawImage(new ImageIcon("enemy-explosion-6.png").getImage(), bg[f].xPos, bg[f].yPos, 90, 90, null);
					bg[f].explosionCount++;
				}
			}

			pf[0] = new Platform(0, 0, 100, 600);
			rect1x = pf[0].px1-50; rect1y = pf[0].py-50; rect1w = pf[0].px2; rect1h = pf[0].ph;
			pf[1] = new Platform(900, 0, 100, 600);	
			rect2x = pf[1].px1-50; rect2y = pf[1].py-50; rect2w = pf[1].px2; rect2h = pf[1].ph;
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
		else if(level1.countStage == 72 || level1.countStage == 74)
		{
			level1.countStage = 73;
		}

		player.drawPlayer(g, sprite); 

		if(level1.countStage == 11 && level1.countStage != 73)
		{
			
			g.drawImage(new ImageIcon("game-over.png").getImage(), 0, 0, getWidth(), getHeight(), this);
			g.setColor(new Color(210,157,0));
			g.setFont(new Font("VCR OSD Mono", Font.PLAIN, 50));
			//g.drawString("THANKS FOR PLAYING :)", 200,200);
			g.drawImage(sprite, 450,250,100,100, null);
			player.xPos=500;
			player.yPos = 2000;
		}

		for(int i = 0; bullets.length > i; i++) //bullet animation
		{
			if(bullets[i] != null)
			{
				if(bullets[i].bRight){
					g.drawImage(bulletGif,bullets[i].bx, bullets[i].by, 25, 25, this);
					bullets[i].bx += 15;
					
					if(bullets[i].bx >= getWidth())
						bullets[i] = null;
					//if(((bullets[i].bx == rect1x && bullets[i].bRight) || (bullets[i].bx == rect1w && !bullets[i].bRight)) || (bullets[i].bx == rect2x && bullets[i].bRight) || (bullets[i].bx == rect2w && !bullets[i].bRight))
					//	bullets[i] = null;

				} else {
					g.drawImage(bulletFlipped,bullets[i].bx, bullets[i].by, 25, 25, this);
					bullets[i].bx -= 15;

					if(bullets[i].bx <= 0)
						bullets[i] = null;

				}	
				
				if(level1.countStage ==1)
				{
				for(int j = 0; 2 > j; j++){
					if(bg[j] != null && bullets[i] != null)
					{
						if(bullets[i].bx >= bg[j].xPos &&
						bullets[i].bx <= (bg[j].xPos + 50) &&
						bullets[i].by >= bg[j].yPos &&
						bullets[i].by <= (bg[j].yPos + 50))
						{
							bg[j].bgIsAlive = false;
							bullets[i] = null;
						}
					}
				}
				}

				else if(level1.countStage ==2)
				{
				for(int j = 2; 4> j; j++){
					if(bg[j] != null && bullets[i] != null)
					{
						if(bullets[i].bx >= bg[j].xPos &&
						bullets[i].bx <= (bg[j].xPos + 50) &&
						bullets[i].by >= bg[j].yPos &&
						bullets[i].by <= (bg[j].yPos + 50))
						{
							bg[j].bgIsAlive = false;
							bullets[i] = null;
						}
					}

				}
				}
				
				else if(level1.countStage ==3)
				{
				for(int j = 4; 6> j; j++){
					if(bg[j] != null && bullets[i] != null)
					{
						if(bullets[i].bx >= bg[j].xPos &&
						bullets[i].bx <= (bg[j].xPos + 50) &&
						bullets[i].by >= bg[j].yPos &&
						bullets[i].by <= (bg[j].yPos + 50))
						{
							bg[j].bgIsAlive = false;
							bullets[i] = null;
						}
					}
				}
				}

				else if(level1.countStage ==4)
				{
				for(int j = 6; 8> j; j++){
					if(bg[j] != null && bullets[i] != null)
					{
						if(bullets[i].bx >= bg[j].xPos &&
						bullets[i].bx <= (bg[j].xPos + 50) &&
						bullets[i].by >= bg[j].yPos &&
						bullets[i].by <= (bg[j].yPos + 50))
						{
							bg[j].bgIsAlive = false;
							bullets[i] = null;
						}
					}
				}
				}

				else if(level1.countStage ==5)
				{
				for(int j = 8; 10> j; j++){
					if(bg[j] != null && bullets[i] != null)
					{
						if(bullets[i].bx >= bg[j].xPos &&
						bullets[i].bx <= (bg[j].xPos + 50) &&
						bullets[i].by >= bg[j].yPos &&
						bullets[i].by <= (bg[j].yPos + 50))
						{
							bg[j].bgIsAlive = false;
							bullets[i] = null;
						}
					}
				}
				}

				else if(level1.countStage ==6)
				{
				for(int j = 10; 12> j; j++){
					if(bg[j] != null && bullets[i] != null)
					{
						if(bullets[i].bx >= bg[j].xPos &&
						bullets[i].bx <= (bg[j].xPos + 50) &&
						bullets[i].by >= bg[j].yPos &&
						bullets[i].by <= (bg[j].yPos + 50))
						{
							bg[j].bgIsAlive = false;
							bullets[i] = null;
						}
					}
				}
				}
				else if(level1.countStage ==7)
				{
				for(int j = 12; 14> j; j++){
					if(bg[j] != null && bullets[i] != null)
					{
						if(bullets[i].bx >= bg[j].xPos &&
						bullets[i].bx <= (bg[j].xPos + 50) &&
						bullets[i].by >= bg[j].yPos &&
						bullets[i].by <= (bg[j].yPos + 50))
						{
							bg[j].bgIsAlive = false;
							bullets[i] = null;
						}
					}
				}
				}
				else if(level1.countStage ==8)
				{
				for(int j = 14; 16> j; j++){
					if(bg[j] != null && bullets[i] != null)
					{
						if(bullets[i].bx >= bg[j].xPos &&
						bullets[i].bx <= (bg[j].xPos + 50) &&
						bullets[i].by >= bg[j].yPos &&
						bullets[i].by <= (bg[j].yPos + 50))
						{
							bg[j].bgIsAlive = false;
							bullets[i] = null;
						}
					}
				}
				}
				else if(level1.countStage ==9)
				{
				for(int j = 16; 18> j; j++){
					if(bg[j] != null && bullets[i] != null)
					{
						if(bullets[i].bx >= bg[j].xPos &&
						bullets[i].bx <= (bg[j].xPos + 50) &&
						bullets[i].by >= bg[j].yPos &&
						bullets[i].by <= (bg[j].yPos + 50))
						{
							bg[j].bgIsAlive = false;
							bullets[i] = null;
						}
					}
				}
				}
				else if(level1.countStage ==10)
				{
				for(int j = 18; 20> j; j++){
						if(bg[j] != null && bullets[i] != null)
						{
							if(bullets[i].bx >= bg[j].xPos &&
							bullets[i].bx <= (bg[j].xPos + 50) &&
							bullets[i].by >= bg[j].yPos &&
							bullets[i].by <= (bg[j].yPos + 50))
							{
								bg[j].bgIsAlive = false;
								bullets[i] = null;
							}
						}
					}
				}
				else if(level1.countStage ==73)
				{
				for(int j = 20; 22> j; j++){
					if(bg[j] != null && bullets[i] != null)
					{

						if(bullets[i].bx >= bg[j].xPos &&
						bullets[i].bx <= (bg[j].xPos + 90) &&
						bullets[i].by >= bg[j].yPos &&
						bullets[i].by <= (bg[j].yPos + 90))
						{
							bullets[i] = null;
							hitCount++;
							
						}

						if(hitCount >= 300)
						{
							bg[j].bgIsAlive = false;
							bullets[i] = null;
							level1.countStage = 6;
						}
					}
				}
				}
				for(int a = 0; pf.length > a; a++){
					if(bullets[i] != null && pf[a] != null)
					{
						if(bullets[i].bx >= (pf[a].px1) &&
						bullets[i].bx <= (pf[a].px2) &&
						bullets[i].by >= pf[a].py &&
						bullets[i].by <= (600))
						{
							bullets[i] = null;
						}
					}
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

		if(e.getKeyCode() == KeyEvent.VK_ENTER)
		{
			cl.show(cards, "Card with startPage");
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
		int spriteWidth = 50;
		int spriteHeight = 50;


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

		public void drawPlayer(Graphics g, Image character) //draws character sprite
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
			g.setColor(Color.BLACK);
			g.fillRect(px1, py, pw, ph);
		}
	}
	class Level1 //draws the stage 
	{
		Image stageOdd = null, stageEven = null, theGif = null;
		boolean isActive = false;
		int countStage;
		PlayGame pg = null;
		

		public Level1(PlayGame playGame) //constructor sets up panel size and images
		{
			countStage = 1; 
			stageOdd = new ImageIcon("stage1odd.png").getImage();
			stageEven = new ImageIcon("stage1even.png").getImage();
			pg = playGame;
		}

		public void drawLevel(Graphics g) //takes in JPanel's g and draws like paintComponent 
		{
			if(countStage >10 && countStage != 73 && countStage != 11) {
				isActive = false;
			}
			
			if(countStage < 11)
			{
				if(countStage%2 != 1) {
					g.drawImage(stageOdd, 0, 0, pg.getWidth(), pg.getHeight(), null);
				}
				else 
				{
					g.drawImage(stageEven, 0, 0, pg.getWidth(), pg.getHeight(), null);
				}
			}
			else
			{
				g.drawImage(new ImageIcon("angryKim-2.png").getImage(),0,0,1000,764,null);
			}

			if(countStage >= 6)
			{
				stageOdd = new ImageIcon("stage2odd.jpg").getImage();
				stageEven = new ImageIcon("stage2even.jpg").getImage();
				for(int x = 0; x <= 1024; x+=32){
					g.drawImage(new ImageIcon("floor.png").getImage(), x, 550, 32, 50, null);
				}
			}
			else if(countStage <= 5)
			{
				stageOdd = new ImageIcon("stage1odd.png").getImage();
				stageEven = new ImageIcon("stage1even.png").getImage();
			}
		}
	}

	class BadGuy //creates bad guy image
	{
		int dist, count=0 ,xPos,yPos, explosionCount;
		Image drone = null, me = null;
		boolean bgIsAlive= true, turned= false;
		
		public BadGuy(int x, int y, int walkTo) //sets bad guy locations
		{
			xPos = x;
			yPos = y;
			dist = walkTo;
			drone = new ImageIcon("drone-1.png").getImage();
			me = new ImageIcon("me.png").getImage();
		}
	}
}

class ShopPage extends JPanel implements ActionListener //creates a select a character panel
{	
	Image bkgrnd = null;
	ImageIcon pirateDood;
	ImageIcon randomGuy;
	ImageIcon minotaur;
	ImageIcon rogue;

	public void addComponentToPane() //creates buttons and sends you to start
	{
		setSize(1000,600);
		setOpaque(false);
		setLayout(new GridLayout(2,2));
		bkgrnd = new ImageIcon("scrollingScreen.gif").getImage();
		//bkgrnd = new ImageIcon("background-2.gif").getImage();

		pirateDood = new ImageIcon("pirateDoodbutBig.png");
		randomGuy = new ImageIcon("randomGuybutBig.png");
		minotaur = new ImageIcon("minotaurbutBig.png");
		rogue = new ImageIcon("rogue_like_run_Animation_1_1butBig.png");

		JButton pirateButton = new JButton(pirateDood);
		pirateButton.setOpaque(false);
		pirateButton.setBorderPainted(false);
		pirateButton.setContentAreaFilled(false);
		pirateButton.setActionCommand("Pirate");
		JButton randomButton = new JButton(randomGuy);
		randomButton.setOpaque(false);
		randomButton.setBorderPainted(false);
                randomButton.setContentAreaFilled(false);
		randomButton.setActionCommand("Random");
		JButton minotaurButton = new JButton(minotaur);
		minotaurButton.setOpaque(false);
		minotaurButton.setBorderPainted(false);
		minotaurButton.setContentAreaFilled(false);
		minotaurButton.setActionCommand("Minotaur");
		JButton rogueButton = new JButton(rogue);
		rogueButton.setOpaque(false);
		rogueButton.setBorderPainted(false);
		rogueButton.setContentAreaFilled(false);
		rogueButton.setActionCommand("Rogue");

		pirateButton.addActionListener(this);
		randomButton.addActionListener(this);
		minotaurButton.addActionListener(this);
		rogueButton.addActionListener(this);

		add(pirateButton); add(rogueButton);
		add(minotaurButton); add(randomButton);

	}

	public void paintComponent(Graphics g) //draws background
	{
		super.paintComponent(g);
		g.drawImage(bkgrnd, 0, 0, 1000, 600, this);
	}

	public void actionPerformed(ActionEvent evt) //sets sprite and takes you back to start page
	{
		if(evt.getActionCommand().equals("Pirate"))
		{
			sprite = new ImageIcon("pirateDood.png").getImage();
			cl.show(cards, "Card with startPage");
		}
		else if(evt.getActionCommand().equals("Random"))
		{
			sprite = new ImageIcon("randomGuy.png").getImage();
			cl.show(cards, "Card with startPage");
		}
		else if(evt.getActionCommand().equals("Minotaur"))
		{
			sprite = new ImageIcon("minotaur.png").getImage();
			cl.show(cards, "Card with startPage");
		}
		else
		{
			sprite = new ImageIcon("rogue_like_run_Animation_1_1.png").getImage();
			cl.show(cards, "Card with startPage");
		}
	}
}
}