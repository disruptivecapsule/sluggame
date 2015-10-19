import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;


@SuppressWarnings("serial")
public class GameCourt extends JPanel {
	
	// the state of the game logic
	private Snake snake1; // the first Snake, keyboard control
	private Snake snake2; // the second Snake, keyboard control
	private Cherry cherry; // the Cherry, spawned randomly
	public boolean over = false;
	public boolean paused;
	public boolean multiPlayer = false;
	
	// Update interval for timer, in milliseconds
	public static final int INTERVAL = 40;
	
	// Game constants
	public static final int COURT_WIDTH = 800;
	public static final int COURT_HEIGHT = 700;
	
	
	//Status keeping
	public int time;
	public int score1;
	public int score2;
	public String highScore = "";
	private JLabel status;
	private int maxScore;
	private String winner;
	

	
	public void startGame() {
		if (!multiPlayer) {
			//initiate Single Player game
			over = false;
			paused = false;
			time = 0;
			score1 = 0;
			status.setText("Cherry-hunting...");
			snake1 = new Snake();
			snake1.head = new Point(0, -1);
			snake1.snakeParts.clear();
			snake1.direction = Direction.DOWN;
			cherry = new Cherry();
			cherry.spawn();
			// Make sure that this component has the keyboard focus
			requestFocusInWindow();
		} else {
			//initiate Two Players game
			over = false;
			paused = false;
			time = 0;
			score1 = 0;
			score2 = 0;
			status.setText("Cherry-hunting...");
			snake1 = new Snake();
			snake1.head = new Point(0, -1);
			snake1.snakeParts.clear();
			snake1.direction = Direction.DOWN;
			snake2 = new Snake();
			snake2.head = new Point(79, 70);
			snake2.snakeParts.clear();
			snake2.direction = Direction.UP;
			cherry = new Cherry();
			cherry.spawn();
			// Make sure that this component has the keyboard focus
			requestFocusInWindow();
		}
	}
	
	
	public GameCourt(JLabel status) {
		setBorder(BorderFactory.createLineBorder(Color.WHITE));

		// The timer is an object which triggers an action periodically
		// with the given INTERVAL. One registers an ActionListener with
		// this timer, whose actionPerformed() method will be called
		// each time the timer triggers. We define a helper method
		// called tick() that actually does everything that should
		// be done in a single timestep.
		Timer timer = new Timer(INTERVAL, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tick();
			}
		});
		timer.start(); // MAKE SURE TO START THE TIMER!

		// Enable keyboard focus on the court area.
		// When this component has the keyboard focus, key
		// events will be handled by its key listener.
		setFocusable(true);
		
		// This key listener allows the square to move as long
		// as an arrow key is pressed, by changing the square's
		// velocity accordingly. (The tick method below actually
		// moves the square.)
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if ((e.getKeyCode() == KeyEvent.VK_LEFT) && snake1.direction != Direction.RIGHT) {
					snake1.direction = Direction.LEFT;
				}
				if ((e.getKeyCode() == KeyEvent.VK_RIGHT) && snake1.direction != Direction.LEFT) {
					snake1.direction = Direction.RIGHT;
				}
				if ((e.getKeyCode() == KeyEvent.VK_UP) && snake1.direction != Direction.DOWN) {
					snake1.direction = Direction.UP;
				}
				if ((e.getKeyCode() == KeyEvent.VK_DOWN) && snake1.direction != Direction.UP) {
					snake1.direction = Direction.DOWN;
				}
				if ((e.getKeyCode() == KeyEvent.VK_A) && snake2.direction != Direction.RIGHT) {
					snake2.direction = Direction.LEFT;
				}
				if ((e.getKeyCode() == KeyEvent.VK_D) && snake2.direction != Direction.LEFT) {
					snake2.direction = Direction.RIGHT;
				}
				if ((e.getKeyCode() == KeyEvent.VK_W) && snake2.direction != Direction.DOWN) {
					snake2.direction = Direction.UP;
				}
				if ((e.getKeyCode() == KeyEvent.VK_S) && snake2.direction != Direction.UP) {
					snake2.direction = Direction.DOWN;
				}
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					if (over) {
						startGame();
					} else {
						paused = !paused;
						repaint();
					}
				}
			}
		});
		
		this.status = status;
	}
	
	/**
	 * This method is called every time the timer defined in the constructor
	 * triggers.
	 */
	void tick() {
		if (!multiPlayer) {
		if (!over && !paused) {
			time++;

			snake1.snakeParts.add(new Point(snake1.head.x, snake1.head.y));
			
			if (snake1.hitWall()) {
				over = true;
				checkScore();
				status.setText("Game over!");
			} 
			
			if (snake1.head.equals(cherry.pos)) {
				score1 += 10;
				snake1.tailLength++;
				cherry.spawn();
			}
			
			snake1.move();
//			System.out.println("cherry x y:" + cherry.pos.x + cherry.pos.y);
//			
//			System.out.println("head x y:" + snake.head.x + snake.head.y);
		
		// update the display
		repaint();
		}
		} else {
			if (!over && !paused) {
				time++;

				snake1.snakeParts.add(new Point(snake1.head.x, snake1.head.y));
				snake2.snakeParts.add(new Point(snake2.head.x, snake2.head.y));
				
				if (snake1.hitWall()) {
					over = true;
					checkScore();
					status.setText("Game over! " + winner + " wins!");
//					System.out.println("head1 x y:" + snake1.head.x + snake1.head.y);
				} 
				
				if (snake1.head.equals(cherry.pos)) {
					score1 += 10;
					snake1.tailLength++;
					cherry.spawn();
				}
				
				if (snake2.hitWall()) {
					over = true;
					checkScore();
					status.setText("Game over! " + winner + " wins!");
//					System.out.println("head2 x y:" + snake2.head.x + snake2.head.y);
				} 
				
				if (snake2.head.equals(cherry.pos)) {
					score2 += 10;
					snake2.tailLength++;
					cherry.spawn();
				}
				
				for (Point point : snake1.snakeParts)
				{
					if (snake2.head.equals(point))
					{
						over = true;
						checkScore();
						status.setText("Game over! " + winner + " wins!");
					}
				}
				
				for (Point point : snake2.snakeParts)
				{
					if (snake1.head.equals(point))
					{
						over = true;
						checkScore();
						status.setText("Game over! " + winner + " wins!");
					}
				}
				
				snake1.move();
				snake2.move();
//				System.out.println("cherry x y:" + cherry.pos.x + cherry.pos.y);
//				
//				System.out.println("head x y:" + snake.head.x + snake.head.y);
			
			// update the display
			repaint();
		}
		}
	}

	
	//Get high score and player name with high score from txt file
	public String getHighScore() {
		//format: "playerName:score"
		FileReader readFile = null;
		BufferedReader br = null;
		try {
			readFile = new FileReader("highScore.txt");
			br = new BufferedReader(readFile);
			return br.readLine();
		} catch (Exception e) {
			return "Nobody:0";
		} finally {
			try {
				if (br != null) {
				br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	//Check if a new high score if created; 
	//if yes, store the new high score into the high score txt file
	public void checkScore(){
		if (multiPlayer) {
			if (score1 > score2) {
				maxScore = score1;
				winner = "Player 1"; 
			} else if (score2 > score1) {
				maxScore = score2;
				winner = "Player 2";
			} else {
				maxScore = score1;
				winner = "No one";
			}
			
		} else {
			maxScore = score1;
			winner = "Player 1";
		}
		if (maxScore > Integer.parseInt(highScore.split(":")[1])){
			String name = 
					JOptionPane.showInputDialog(
							winner + ", you set a new high score. What's your name?");
			highScore = name + ":" + maxScore;
			File scorefile = new File("highScore.txt");
			if (!scorefile.exists()) {
				try {
					scorefile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			FileWriter writeFile = null;
			BufferedWriter bw = null;
			try {
				writeFile = new FileWriter(scorefile);
				bw = new BufferedWriter(writeFile);
				bw.write(this.highScore);
			} catch (Exception e) {
				//errors
			} finally {
				if (bw != null){
					try {
						bw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		if (!multiPlayer) {
			super.paintComponent(g);
			final Color BABYBLUE = new Color(230216173);
			g.setColor(BABYBLUE);
			g.fillRect(0, 0, 800, 700);
			
			snake1.draw(g);
			cherry.draw(g);
			
			if (highScore.equals("")) {
				//initialize highScore
				highScore = getHighScore(); 
			}
			
			String string = "Score: " + score1 + 
							", Length Snake: " + snake1.tailLength + 
							", Time: " + time / 20 + 
							", High Score: " + highScore;
			
			g.setColor(Color.white);
			g.drawString(string, (int) (getWidth() / 2 - string.length() * 3f), 20);
			
			string = "Paused!";
			if (paused && !over)
			{
				g.drawString(string, (int) (getWidth() / 2 - string.length() * 2.5f), 
						(int) getHeight() / 2);
			}
		} else {
			super.paintComponent(g);
			final Color BABYBLUE = new Color(230216173);
			g.setColor(BABYBLUE);
			g.fillRect(0, 0, 800, 700);
			
			snake1.draw(g);
			snake2.draw(g);
			cherry.draw(g);
			
			if (highScore.equals("")) {
				//initialize highScore
				highScore = getHighScore(); 
			}
			
			String string = "Score 1: " + score1 + 
							", Score 2: " + score2 +
							", Length Snake 1: " + snake1.tailLength + 
							", Length Snake 2: " + snake2.tailLength +
							", Time: " + time / 20 + 
							", High Score: " + highScore;
			
			g.setColor(Color.white);
			g.drawString(string, (int) (getWidth() / 2 - string.length() * 3f), 20);
			
			string = "Paused!";
			if (paused && !over)
			{
				g.drawString(string, (int) (getWidth() / 2 - string.length() * 2.5f), 
						(int) getHeight() / 2);
			}
		}
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(COURT_WIDTH, COURT_HEIGHT);
	}
	
}
