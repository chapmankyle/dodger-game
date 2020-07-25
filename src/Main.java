import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Class representing the main frame for the game.
 */
public class Main extends JFrame {

	private static final long serialVersionUID = 1L;

	protected DecimalFormat df = new DecimalFormat("#.#");
	protected List<Double> times = new ArrayList<>(); // record all times

	private DrawCanvas canvas;
	private JPanel pauseScreen;

	private Car player;

	private Line[] lines;
	private Traffic traffic1, traffic2;

	protected int speedA;
	protected int speedB;
	protected int first, second;

	protected int jumpHeight;
	protected int tim, sec, i, pauseHit, countA, countB;
	protected int ync = 0; // y - yes, n - no, c - cancel

	protected String time;
	protected String playerPath = "./res/blue.png";
	protected String trafficPath = "./res/orange.png";
	protected double currentTime;
	protected double bestTime = 0;
	protected boolean beat = false;
	protected boolean paused = true;

	protected BufferedImage img;
	protected JLabel lblBg;

	/**
	 * Constructor for the main game.
	 */
	public Main() {
		init();

		makeTrafficA();
		makeTrafficB();

		prepareCanvas();

		// adding KeyListener
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent evt) {
				if (evt.getKeyCode() == KeyEvent.VK_ESCAPE || evt.getKeyCode() == KeyEvent.VK_P) {
					pause(true);
					pauseHit++;
				} else if (!paused) {
					switch (evt.getKeyCode()) {
						case KeyEvent.VK_W:
						case KeyEvent.VK_UP:
							move(Consts.MOVES.UP);
							break;
						case KeyEvent.VK_S:
						case KeyEvent.VK_DOWN:
							move(Consts.MOVES.DOWN);
							break;
					}
				}
			}
		});

		// preparing the frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocation(0, 0);
		setTitle("Dodge The Traffic");
		pack(); // pack all the components into frame
		setVisible(true);
		setResizable(false);
		requestFocus();

		start();
	}

	/**
	 * Initializes the relevant variables.
	 */
	private void init() {
		// set speeds of different cars
		this.speedA = 2;
		this.speedB = 1;

		// create player
		this.player = new Car(
			Consts.PLAYER_WIDTH,
			Consts.CANVAS_HEIGHT / 2 - (Consts.PLAYER_HEIGHT / 2),
			Consts.PLAYER_WIDTH,
			Consts.PLAYER_HEIGHT,
			0,
			this.playerPath
		);

		int x = 0;
		int width = Consts.LINE_WIDTH;
		int height = Consts.LINE_HEIGHT;

		// give each line a specific y value
		int[] ys = new int[]{
			Consts.CANVAS_HEIGHT / 3 - (Consts.LINE_HEIGHT / 2),
			(int) (Consts.CANVAS_HEIGHT / 1.5) - (Consts.LINE_HEIGHT / 2),
			0,
			Consts.CANVAS_HEIGHT - Consts.LINE_HEIGHT,
		};

		// create lines
		this.lines = new Line[4];
		for (int i = 0; i < this.lines.length; i++) {
			this.lines[i] = new Line(x, ys[i], width, height);
		}

		// set the height that a car can jump
		this.jumpHeight = this.lines[1].getY() - this.lines[0].getY();
	}

	/**
	 * Prepares the canvas for drawing.
	 */
	private void prepareCanvas() {
		// set canvas size
		this.canvas = new DrawCanvas();
		this.canvas.setPreferredSize(
			new Dimension(Consts.CANVAS_WIDTH, Consts.CANVAS_HEIGHT)
		);

		// add canvas to frame
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		cp.add(this.canvas, BorderLayout.CENTER);

		// add pause screen
		this.pauseScreen = new JPanel();
		this.pauseScreen.setSize(Consts.PAUSE_WIDTH, Consts.PAUSE_HEIGHT);
		this.pauseScreen.setBackground(Color.CYAN);
		this.pauseScreen.setLocation(
			(Consts.CANVAS_WIDTH / 2) - (Consts.PAUSE_WIDTH / 2),
			(Consts.CANVAS_HEIGHT / 2) - (Consts.PAUSE_HEIGHT / 2)
		);
	}

	private void makeTrafficA() {
		first = (int) (Math.random() * 3) + 1;

		int x = Consts.CANVAS_WIDTH;
		int y = Consts.CANVAS_HEIGHT / 2 - (Consts.PLAYER_HEIGHT / 2);
		int width = player.width;
		int height = player.height;

		if (countA >= 4) {
			countA = 0;
			speedA += (int) (Math.random() * 2) + 1;
		}

		if (speedA >= 10) {
			speedA = 10;
		}

		switch (first) {
		case 1:
			traffic1 = new Traffic(x, y - jumpHeight, width, height, speedA,
					trafficPath);
			break;
		case 2:
			traffic1 = new Traffic(x, y, width, height, speedA, trafficPath);
			break;
		case 3:
			traffic1 = new Traffic(x, y + jumpHeight, width, height, speedA,
					trafficPath);
			break;
		}
	}

	/**
	 * Moves a player in the specified direction.
	 * @param direction The direction to move.
	 */
	private void move(Consts.MOVES direction) {
		int lastY = this.player.getY();

		if (direction == Consts.MOVES.UP) {
			// out of bounds
			if (lastY - jumpHeight < 0) {
				return;
			}

			// move up
			this.player.setY(lastY - jumpHeight);
		}

		if (direction == Consts.MOVES.DOWN) {
			// out of bounds
			if (lastY + jumpHeight > Consts.CANVAS_HEIGHT) {
				return;
			}

			// move down
			this.player.setY(lastY + jumpHeight);
		}

		canvas.repaint(
			this.player.getX(),
			lastY,
			this.player.getWidth(),
			this.player.getHeight()
		);

		canvas.repaint(
			this.player.getX(),
			this.player.getY(),
			this.player.getWidth(),
			this.player.getHeight()
		);
	}

	private boolean isHit(Car c1, Traffic c2) {
		return c2.getTrafficBounds().intersects(c1.getBounds());
	}


	/**
	 * Toggles the pause flag.
	 * @param isPressed {@code true} if the paused button is pressed, {@code false} otherwise.
	 */
	private void pause(boolean isPressed) {
		this.paused = isPressed;
	}


	private void makeTrafficB() {
		second = (int) (Math.random() * 3) + 1;

		int x = Consts.CANVAS_WIDTH;
		int y = Consts.CANVAS_HEIGHT / 2 - (Consts.PLAYER_HEIGHT / 2);
		int width = player.width;
		int height = player.height;

		if (countB >= 3) {
			countB = 0;
			speedB += (int) (Math.random() * 3) + 1;

			while (speedB == speedA) {
				speedB += (int) (Math.random() * 3) + 1;
			}
		}

		if (speedB >= 10) {
			speedB = 10;
		}

		switch (second) {
		case 1:
			traffic2 = new Traffic(x, y - jumpHeight, width, height, speedB,
					trafficPath);
			break;
		case 2:
			traffic2 = new Traffic(x, y, width, height, speedB, trafficPath);
			break;
		case 3:
			traffic2 = new Traffic(x, y + jumpHeight, width, height, speedB,
					trafficPath);
			break;
		}
	}

	private void start() {
		tim = 0;
		sec = 0;
		times.add(0.0);
		i = 0;

		Thread thr = new Thread() {
			@Override
			public void run() {
				while (ync == 0) {
					makeTrafficA();
					makeTrafficB();
					player.x = Consts.PLAYER_WIDTH;
					player.y = (Consts.CANVAS_HEIGHT / 2)
							- (Consts.PLAYER_HEIGHT / 2);
					sec = 0;
					beat = false;
					countA = 0;
					countB = 0;
					while (true) {
						update();
						repaint();

						if (!paused) {
							try {
								Thread.sleep(5);
								tim += 5;
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}

						boolean hit = isHit(player, traffic1)
								|| isHit(player, traffic2);
						if (hit) {
							break;
						}

						if (tim >= 1000) {
							tim = 0;
							sec++;
						}

					}

					time = sec + "." + (tim / 100);
					currentTime = Double.parseDouble(time);
					times.add(currentTime);
					i++;

					boolean zero = false;
					if (bestTime == 0) {
						zero = true;
					}

					if (currentTime > bestTime) {
						bestTime = currentTime;
						beat = true;
					}

					if (beat && !zero) {
						JOptionPane.showMessageDialog(
								null,
								"You BEAT your best time!\t\t \n\nNEW BEST TIME: "
										+ bestTime
										+ " sec\nPrevious time: "
										+ times.get(i - 1)
										+ " sec");
					} else {
						if (sec >= 60) {
							JOptionPane.showMessageDialog(null,
									"You lasted:\n"
											+ (sec / 60)
											+ " min "
											+ (sec % 60)
											+ " sec");
						} else {
							JOptionPane.showMessageDialog(null,
									"You lasted:\n" + time
											+ " seconds");
						}
					}
					ync = JOptionPane.showConfirmDialog(null,
							"Have another go?\n "); // yes - 0, no
											// - 1, cancel
											// - 2
					speedA = 2;
					speedB = 1;
				}

				System.exit(0);
			}
		};
		thr.start();
	}

	private void update() {
		if (paused) {
			addPause();

			if (pauseHit != 1) {
				pauseHit = 0;
				paused = false;
				int a = traffic1.x;
				int b = traffic2.x;

				a -= speedA;
				b -= speedB;
				checkTraffic();

				if (a + traffic1.width <= 0) {
					a = Consts.CANVAS_WIDTH + 2;
					makeTrafficA();
					countA++;
					// spdA += //(int)(Math.random()*3);
				}
				if (b + traffic2.width <= 0) {
					b = Consts.CANVAS_WIDTH + 2;
					makeTrafficB();
					countB++;
					// spdB += (int)(Math.random()*3);
				}

				traffic1.x = a;
				traffic2.x = b;

				if (pauseScreen != null) {
					destroyPause();
				}
			}
		} else {
			int a = traffic1.x;
			int b = traffic2.x;

			a -= speedA;
			b -= speedB;
			checkTraffic();

			if (a + traffic1.width <= 0) {
				a = Consts.CANVAS_WIDTH + 2;
				makeTrafficA();
				countA++;
				// spdA += (int)(Math.random()*3);
			}
			if (b + traffic2.width <= 0) {
				b = Consts.CANVAS_WIDTH + 2;
				makeTrafficB();
				countB++;
				// spdB += (int)(Math.random()*3);
			}

			traffic1.x = a;
			traffic2.x = b;

			if (pauseScreen != null) {
				destroyPause();
			}
		}
	}

	public void checkTraffic() {
		if (traffic1.y == traffic2.y) {
			if (traffic1.x < traffic2.x) {
				switch (traffic2.y) {
				case 85:
					traffic2.y = 325;
					break;
				case 325:
					traffic2.y = 85;
					break;
				case 565:
					traffic2.y = 325;
					break;
				}
			} else if (traffic2.x < traffic1.x) {
				switch (traffic1.y) {
				case 85:
					traffic1.y = 325;
					break;
				case 325:
					traffic1.y = 85;
					break;
				case 565:
					traffic1.y = 325;
					break;
				}
			}

		}
	}

	/**
	 * Adds the pause screen to the frame.
	 */
	public void addPause() {
		this.canvas.add(this.pauseScreen);
	}

	/**
	 * Removes the pause screen from the frame.
	 */
	public void destroyPause() {
		this.canvas.remove(this.pauseScreen);
		this.canvas.repaint();
	}

	/**
	 * Class representing the canvas on which to draw the components.
	 */
	class DrawCanvas extends JPanel {
		private static final long serialVersionUID = 1L;

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			setBackground(Consts.CANVAS_BG_COLOR);

			player.paint(g);

			for (int i = 0; i < lines.length; i++) {
				lines[i].paint(g);
			}

			traffic1.paint(g);
			traffic2.paint(g);
		}
	}

	/**
	 * Main function.
	 *
	 * @param args The command-line arguments.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new Main(); // start the game
			}
		});
	}
}
