/*
Aarush Umap
GunsAndGlory.java
4.23.19
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.ArrayList;

public class GunsAndGlory //main class
{
	public static void main(String[] args) //main method
	{
		GunsAndGlory gng = new GunsAndGlory();
		gng.createAndShowGUI();
	}
	  private void createAndShowGUI() //creates frame and calls Cards class
	  {
	    JFrame frame = new JFrame("GunsAndGlory");
	    frame.setSize(1000, 600);
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
	int xPos,yPos, bulletNum;
	ArrayList<Integer> bulletX, bulletCount;
	Player player;	
	Timer upTimer, downTimer, bulletTimer;
	boolean addBullet, isRightFace, isShooting, isJumping;

	public void addComponentToPane() //acts as a constructor and creates panel
	{
		setSize(1000,600);
		addBullet = false;
		isRightFace = true;
		isShooting = false;
		isJumping = false;
		xPos = 500;
		yPos = 300;
 		player = new Player();
 		addKeyListener(this);
 		setFocusable(true);
        requestFocusInWindow();
        bulletX = new ArrayList<Integer>();
        bulletCount = new ArrayList<Integer>();
	}

	public void paintComponent(Graphics g) //paints character and bullet
	{
		super.paintComponent(g);
		g.fillRect(xPos,yPos,100,100);

		if(addBullet)
		{
			System.out.println("addBullet is true");
			for(int i =0; i <= bulletX.size(); i++)
				g.fillOval(bulletX.get(bulletNum-1), yPos+25, 10,10);
		}
	}

	public void keyPressed(KeyEvent e) //movement keys
	{
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			isRightFace = false;
			player.run();
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			isRightFace = true;
			player.run();
		}
		if(e.getKeyCode() == KeyEvent.VK_UP)
		{
			player.jump();
			isJumping = true;
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
			player.stop();
		}
	}

	class Player implements ActionListener //creates instance of player
	{
		int count; 
		Bullet bullet;

		public void shoot() //method for shooting if spacebar is pressed 
		{
			addBullet = true;
			isShooting = true;
			bulletCount.add(0);
			bulletNum++;
			bulletX.add(xPos + 100);
			System.out.println("I'm shooting");
			bullet = new Bullet();
			bullet.makeBullet();
			bulletTimer = new Timer(20, this);
			bulletTimer.start();
		}

		public void jump() //method for jumping with up key
		{
			count = 0;
			upTimer = new Timer(50, this);
			upTimer.start();
		}

		public void run() //method for running with left/right arrow keys
		{
			if (isRightFace) {
				System.out.println("Running to RIGHT");
				xPos += 10;
				repaint();
			} else {
				System.out.println("Running to LEFT");
				xPos -= 10;
				repaint();
			}
		}

		public void stop() //prints when character is stopped
		{
			System.out.println("Stopped Running");
		}

		public void actionPerformed(ActionEvent e) //called by timer and makes character jump
		{
			if(isJumping)
			{
				if(count < 21)
				{
					yPos-=10;
					count++;
					repaint();
				}
				else if(count < 41)
				{
					yPos+=10;
					count++;
					repaint();
				}
				else
				{
					upTimer.stop();
				}
			}		
		}

		class Bullet //creates instance of bullet
		{
			public void makeBullet() //chooses direction of bullet and sends them out
			{
				System.out.println("makeBullet has been called");
				if(isRightFace)
				{
					System.out.println("isRightFace is true");
					System.out.println("value of isShooting " + isShooting);
					if(isShooting)
					{
						addBullet = true;
						if((int)(bulletCount.get(bulletNum-1)) < 21)
						{
							System.out.println("bulletCount is less than 21");
							bulletX.set(bulletNum-1,bulletX.get(bulletNum-1)+9);
							bulletCount.set(bulletNum-1, bulletCount.get(bulletNum-1)+1);
							repaint();
						}

						else
						{
							bulletX.remove(bulletX.get(bulletNum-1));
							bulletTimer.stop();
						}
					}
				}
				else
				{
					if(isShooting)
					{
						addBullet = true;
						if(bulletCount.get(bulletNum-1) < 21)
						{
							bulletX.set(bulletNum-1,bulletX.get(bulletNum - 1)-9);
							bulletCount.set(bulletNum-1, bulletCount.get(bulletNum-1)+1);
							repaint();
						}

						else
						{
							bulletX.remove(bulletX.get(bulletNum-1));
							bulletTimer.stop();
						}
					}
				}
			}
		}
	}
}