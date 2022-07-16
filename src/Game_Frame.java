import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class Game_Frame extends JFrame implements MouseListener, KeyListener {
	public static JFrame frame;
	private JPanel contentPane;
	public static JButton[][] buttons = new JButton[Main.m][Main.n];
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	// Determining screen size according to user screen size
	int screen_height = screenSize.height * 35 / 36;
	int screen_width = screenSize.width;
	// the ration of user screen to the amount of n
	int width_ratio = (int) ((float) screen_width / (float) Main.n);
	// the ration of user screen to the amount of m
	int height_ratio = (int) ((float) screen_height / (float) Main.m);
	int button_dimention = (Main.m < Main.n && (width_ratio * Main.m) < screen_height ? width_ratio : height_ratio);
	int height = button_dimention * Main.m;
	int width = button_dimention * Main.n;
	// final cost of the path
	static int total = 0;
	static ImageIcon[] icons = new ImageIcon[6];
	// main array
	public Square[][] squares = new Square[Main.m][Main.n];

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Game_Frame frame = new Game_Frame();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Game_Frame() {
		reset_random();
		this.setTitle("!!  Press H for help  !!");
		frame = this;
		setFocusable(true);
		setVisible(true);
		addKeyListener(this);
		setResizable(false);
		setFocusTraversalKeysEnabled(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, width, height);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(Color.black);
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		JPanel panel = new JPanel() {
			public void paintComponent(Graphics g) {
				Image img = Toolkit.getDefaultToolkit().getImage(Game_Frame.class.getResource("/pics/background.png"));
				g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
			}
		};
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(Main.m, Main.n, 0, 0));
		// image icons for placing obstacles on the board
		try {
			// miner
			icons[1] = new ImageIcon(ImageIO.read(Game_Frame.class.getResource("/pics/miner.png"))
					.getScaledInstance(button_dimention, button_dimention, Image.SCALE_DEFAULT));
			// stone
			icons[2] = new ImageIcon(ImageIO.read(Game_Frame.class.getResource("/pics/stone.png"))
					.getScaledInstance(button_dimention, button_dimention, Image.SCALE_DEFAULT));
			// wood
			icons[3] = new ImageIcon(ImageIO.read(Game_Frame.class.getResource("/pics/wood.png"))
					.getScaledInstance(button_dimention, button_dimention, Image.SCALE_DEFAULT));
			// gold
			icons[4] = new ImageIcon(ImageIO.read(Game_Frame.class.getResource("/pics/gold.png"))
					.getScaledInstance(button_dimention, button_dimention, Image.SCALE_DEFAULT));
			// empty place
			icons[5] = new ImageIcon(ImageIO.read(Game_Frame.class.getResource("/pics/path.png"))
					.getScaledInstance(button_dimention, button_dimention, Image.SCALE_DEFAULT));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// creating a 2d array of buttons
		for (int i = 0; i < Main.m; i++) {
			for (int j = 0; j < Main.n; j++) {
				JButton button = new JButton("");
				button.setBorder(new LineBorder(Color.decode("#4c413c")));
				button.setForeground(Color.WHITE);
				button.setContentAreaFilled(false);
				button.setOpaque(false);
				button.setFocusable(false);
				panel.add(button);
				button.addMouseListener(this);
				button.setVerticalTextPosition(SwingConstants.CENTER);
				button.setHorizontalTextPosition(SwingConstants.CENTER);
				button.setMargin(new Insets(0, 0, 0, 0));
				buttons[i][j] = button;
			}
		}
		Main.reset_matrix();
		set_miner(0, 0);
		draw_matrix();
		help(0);
	}

	private void draw_matrix() {
		for (int i = 0; i < Main.m; i++) {
			for (int j = 0; j < Main.n; j++) {
				// setting the icons according to the told numbers
				buttons[i][j].setIcon(icons[Main.matrix[i][j]]);
				// set squares
				squares[i][j] = new Square(i, j, Main.matrix[i][j]);
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		JButton b = (JButton) e.getComponent();
		int[] cor = button_cor(b);
		int x = cor[0];
		int y = cor[1];
		// iterating between the options of left click
		if (e.getModifiers() == MouseEvent.BUTTON1_MASK && Main.start_game == false) {
			// if empty put stone
			if (b.getIcon() == icons[0]) {
				b.setIcon(icons[2]);
				Main.matrix[x][y] = 2;
			}
			// if stone put wood
			else if (b.getIcon().equals(icons[2])) {
				b.setIcon(icons[3]);
				Main.matrix[x][y] = 3;
			}
			// if wood put gold
			else if (b.getIcon().equals(icons[3])) {
				b.setIcon(icons[4]);
				Main.matrix[x][y] = 4;
			}
			// if gold then empty the square
			else if (b.getIcon().equals(icons[4])) {
				b.setIcon(icons[0]);
				Main.matrix[x][y] = 0;
			}
			remove_seen();
			calculate();
		}
		// if right clicked we will move the miner to this square no matter what;)
		else if (e.getModifiers() == MouseEvent.BUTTON3_MASK && Main.start_game == false) {
			set_miner(x, y);
			// then we should remove the already seen icons
			remove_seen();
			calculate();
		}
	}

	// removing the seen icons to restart the game
	private void remove_seen() {
		for (int i = 0; i < Main.m; i++) {
			for (int j = 0; j < Main.n; j++) {
				if (Main.matrix[i][j] > 4) {
					Main.matrix[i][j] = 0;
					buttons[i][j].setText("");
				}
			}
		}
		draw_matrix();
	}

	// redrawing the matrix
	private void calculate() {
		Main.print_matrix();
	}

	// finding the coordination of the input button
	private int[] button_cor(JButton b) {
		for (int i = 0; i < Main.m; i++)
			for (int j = 0; j < Main.n; j++) {
				if (buttons[i][j].equals(b))
					return new int[] { i, j };
			}
		return null;
	}

	// moving the miner to the x,y coordinations
	private void set_miner(int x, int y) {
		Main.matrix[Main.miner.i][Main.miner.j] = 5;
		buttons[x][y].setIcon(icons[1]);
		Main.miner.i = x;
		Main.miner.j = y;
		Main.matrix[x][y] = 1;
		draw_matrix();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// press r for reset
		if (e.getKeyCode() == KeyEvent.VK_R) {
			Main.start_game = false;
			reset();
			Main.matrix[0][0] = 1;
			Main.miner.i = 0;
			Main.miner.j = 0;
			Main.print_matrix();
			draw_matrix();
			System.out.println("reset");
		}
		// press enter to start the algorithm
		else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			total = 0;
			Main.waiting = false;
			Main.wait = 0;
			remove_seen_icons();
			set_icons();
			Main.start_game = true;
			Main.a_star();
			System.out.println("enter");
			if (Main.ways_iterator == 0 && Main.wait <= 1) {
				Game_Frame.print_goals(Main.goal_list);
			}
		}
		// press space to forward a step
		else if (e.getKeyCode() == KeyEvent.VK_SPACE && Main.start_game == true) {
			total++;
			if (Main.waiting == false) {
				Main.steps();
				set_icons();
				if (Main.ways_iterator == 0 && Main.wait <= 1) {
					Game_Frame.print_goals(Main.goal_list);
				}
			}
			// in case of facing an obstacle we have to stay in the same place and press
			// space bar for 5 or 3 times to break this obstacle
			if (Main.wait >= 1) {
				Main.wait--;
				if (Main.wait == 1) {
					Main.matrix[Main.ways.get(Main.ways_iterator).i][Main.ways.get(Main.ways_iterator).j] = 0;
					set_icons();
					Main.waiting = false;
					Object[] options = { "OK" };
					JOptionPane.showOptionDialog(frame, "<< the obstacle is broken>>", "", JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
				}
				// here we will show the removing of obstacle news
				else {
					Object[] options = { "OK" };
					JOptionPane.showOptionDialog(frame,
							"!BREAKING AN OBSTACLE!\n\n" + (Main.wait - 1) + " more hits to break\n\n", "",
							JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
					System.out.println("obstacle " + (Main.wait - 1) + " more to go");
				}
			}
			System.out.println("space");
		}
		// press s for setting all random
		else if (e.getKeyCode() == KeyEvent.VK_S) {
			Main.start_game = false;
			reset();
			Main.random(Main.stone, Main.wood, Main.gold, Main.seed);
			Main.print_matrix();
			draw_matrix();
			System.out.println("set");
		}
		// press p for pausing and enable editing
		else if (e.getKeyCode() == KeyEvent.VK_P) {
			Main.start_game = false;
			remove_seen_icons();
			set_icons();
			System.out.println("pause");
		}
		// press h for help dialogue
		else if (e.getKeyCode() == KeyEvent.VK_H) {
			help(1);
		}
	}

	// calling the tutorial option panel for learning and setting the random values
	private void help(int n) {
		tutorial.frame = this;
		tutorial.help(n);
	}

	public static void remove_seen_icons() {
		for (int i = 0; i < Main.m; i++) {
			for (int j = 0; j < Main.n; j++) {
				if (Main.matrix[i][j] == 5) {
					Main.matrix[i][j] = 0;
				}
				buttons[i][j].setText(null);
			}
		}
	}

	// setting the icons for each button according to the Main.matrix values
	public static void set_icons() {
		for (int i = 0; i < Main.m; i++) {
			for (int j = 0; j < Main.n; j++) {
				buttons[i][j].setIcon(icons[Main.matrix[i][j]]);
			}
		}
	}

	// reseting the icon and text of each icon
	private void reset() {
		Main.reset_matrix();
		for (int i = 0; i < Main.m; i++)
			for (int j = 0; j < Main.n; j++) {
				buttons[i][j].setIcon(null);
				buttons[i][j].setText(null);
			}
	}

	// printing the goals or the finished dialogue in an option panel
	public static void print_goals(String goal_list) {
		if (goal_list != "") {
			Object[] options = { "OK" };
			JOptionPane.showOptionDialog(frame, goal_list, "Goal's List", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		} else {
			Object[] options = { "OK" };
			JOptionPane.showOptionDialog(frame, "we have reached all of the goals\ncost : " + total, "Finished",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		}
	}

	// reseting the values of random function the the default ones
	public static void reset_random() {
		Main.seed = Main.seed_default;
		Main.gold = Main.gold_default;
		Main.stone = Main.stone_default;
		Main.wood = Main.wood_default;

	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

}