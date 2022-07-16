import java.awt.GridLayout;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;

public class tutorial {
	// these are the tutorials no comment is needed ,
	// as everything is either a hint, picture or gif itself
	public static JFrame frame;

	public static void help(int n) {
		if (n <= 1) {
			java.net.URL imageURL = Game_Frame.class.getResource("pics/tutorial.gif");
			ImageIcon icon = new ImageIcon(imageURL);
			Object[] options = { "Tutorial", "Random Function", "Close" };
			int o = JOptionPane.showOptionDialog(frame,
					"\nUse these keys and mouse to play\nfor Tutorial information click on \"Tutorial\" button\n\n"
							+ "H = help\n" + "\n" + "R = reset\n" + "S = generate random\n" + "Enter = begin\n"
							+ "space bar = move (only after Enter is pressed)\n" + "P = pause (use during the game)\n"
							+ "\n" + "left click 1st = stone\n" + "left click 2nd = wood\n" + "left click 3rd = gold\n"
							+ "left click 4th = empty\n" + "right click = miner\n" + "\n\n"
							+ "Click on \"Random Function\" to adjust random values" + "\n\n\n",
							"Tutorial", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon, options, options[0]);
			if (o == 0) {
				help(2);
			} else if (o == 1) {
				help(3);
			}
		} else if (n == 2) {
			java.net.URL imageURL = Game_Frame.class.getResource("pics/set.gif");
			ImageIcon icon = new ImageIcon(imageURL);
			Object[] options = { "Next", "Keys", "Random Function", "Close" };
			int o = JOptionPane.showOptionDialog(frame,
					"You can find the keys in the \"Keys\" button \n\n"
							+ "First of all use S key to randomly place obstacle in the game\n" + "\n\n",
							"Tutorial: Set", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon, options,
							options[0]);
			if (o == 0) {
				help(4);
			}
			if (o == 1) {
				help(1);
			} else if (o == 2) {
				help(3);
			}
		} else if (n == 4) {
			java.net.URL imageURL = Game_Frame.class.getResource("pics/mouse.gif");
			ImageIcon icon = new ImageIcon(imageURL);
			Object[] options = { "Next", "Previous", "Keys", "Random Function", "Close" };
			int o = JOptionPane.showOptionDialog(frame, "You can find the keys in the \"Keys\" button \n\n"
					+ "You can also use mouse to set obstacles and miner position\n"
					+ "simply Left Click on a square to iterate between obstacles\n"
					+ "use Right Click to place miner\n" + "\n" + "left click 1st = stone\n" + "left click 2nd = wood\n"
					+ "left click 3rd = gold\n" + "left click 4th = empty\n" + "right click = miner\n" + "\n",
					"Tutorial: Mouse", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon, options,
					options[0]);
			if (o == 0) {
				help(5);
			} else if (o == 1) {
				help(2);
			} else if (o == 2) {
				help(1);
			} else if (o == 3) {
				help(3);
			}
		} else if (n == 5) {
			java.net.URL imageURL = Game_Frame.class.getResource("pics/reset.gif");
			ImageIcon icon = new ImageIcon(imageURL);
			Object[] options = { "Next", "Previous", "Keys", "Random Function", "Close" };
			int o = JOptionPane.showOptionDialog(frame,
					"\nYou can find the keys in the \"Keys\" button \n\n"
							+ "You can use R to reset the obstacle positions\n\n\n",
							"Tutorial: Reset", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon, options,
							options[0]);
			if (o == 0) {
				help(6);
			} else if (o == 1) {
				help(4);
			} else if (o == 2) {
				help(1);
			} else if (o == 3) {
				help(3);
			}
		} else if (n == 6) {
			java.net.URL imageURL = Game_Frame.class.getResource("pics/enter.png");
			ImageIcon icon = new ImageIcon(imageURL);
			Object[] options = { "Next", "Previous", "Keys", "Random Function", "Close" };
			int o = JOptionPane.showOptionDialog(frame, "\nYou can find the keys in the \"Keys\" button \n\n"
					+ "after setting the obstacles in your desired positions press enter to start the calculating\n"
					+ "after you pressed enter you will see a list of found goals sorted like this image" + "\n\n\n"
					+ "IMPORTANT NOTE:\n we will calculate and sort goals after reaching each of them too" + "\n\n\n",
					"Tutorial: Enter", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon, options,
					options[0]);
			if (o == 0) {
				help(7);
			} else if (o == 1) {
				help(5);
			} else if (o == 2) {
				help(1);
			} else if (o == 3) {
				help(3);
			}
		} else if (n == 7) {
			java.net.URL imageURL = Game_Frame.class.getResource("pics/space.gif");
			ImageIcon icon = new ImageIcon(imageURL);
			Object[] options = { "Close", "Previous", "Keys", "Random Function", };
			int o = JOptionPane.showOptionDialog(frame, "\nYou can find the keys in the \"Keys\" button \n\n"
					+ "after pressing enter you can now press space to move according to A-Star calculus\n" + "\n\n\n"
					+ "IMPORTANT NOTE:\n you need to press space 5 time before a stone\nand 2 times before a wood obstacle in order to break it"
					+ "\n\n\n", "Tutorial: Space bar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon,
					options, options[2]);
			if (o == 1) {
				help(6);
			} else if (o == 2) {
				help(1);
			} else if (o == 3) {
				help(3);
			}
		} else if (n == 3) {
			Object[] options = { "Apply", "Reset", "Keys", "Close" };
			// create seed slider
			JSlider seed_slider = new JSlider(JSlider.HORIZONTAL, 0, 50, Math.abs(Main.seed));
			seed_slider.setMinorTickSpacing(1);
			seed_slider.setMajorTickSpacing(5);
			seed_slider.setPaintTicks(true);
			seed_slider.setPaintLabels(true);
			seed_slider.setLabelTable(seed_slider.createStandardLabels(10));
			// create gold slider
			JSlider gold_slider = new JSlider(JSlider.HORIZONTAL, 0, Main.m * Main.n, Main.gold);
			gold_slider.setMinorTickSpacing(1);
			gold_slider.setMajorTickSpacing(5);
			gold_slider.setPaintTicks(true);
			gold_slider.setPaintLabels(true);
			gold_slider.setLabelTable(gold_slider.createStandardLabels(10));
			// create stone slider
			JSlider stone_slider = new JSlider(JSlider.HORIZONTAL, 0, Main.m * Main.n, Main.stone);
			stone_slider.setMinorTickSpacing(1);
			stone_slider.setMajorTickSpacing(5);
			stone_slider.setPaintTicks(true);
			stone_slider.setPaintLabels(true);
			stone_slider.setLabelTable(stone_slider.createStandardLabels(10));
			// create wood slider
			JSlider wood_slider = new JSlider(JSlider.HORIZONTAL, 0, Main.m * Main.n, Main.wood);
			wood_slider.setMinorTickSpacing(1);
			wood_slider.setMajorTickSpacing(5);
			wood_slider.setPaintTicks(true);
			wood_slider.setPaintLabels(true);
			wood_slider.setLabelTable(wood_slider.createStandardLabels(10));
			JPanel myPanel = new JPanel(new GridLayout(20, 1));
			myPanel.add(new JLabel("You can find the keys in the \"Keys\" button"));
			myPanel.add(Box.createVerticalStrut(18)); // a spacer
			myPanel.add(new JLabel("Fixed seed?"));
			// ... Create the buttons.
			JRadioButton noButton = new JRadioButton("NO");
			JRadioButton yesButton = new JRadioButton("YES");
			if (Math.abs(Main.seed) > 1) {
				yesButton.setSelected(true);
				noButton.setSelected(false);
			} else {
				noButton.setSelected(true);
				yesButton.setSelected(false);
			}
			ButtonGroup bgroup = new ButtonGroup();
			bgroup.add(noButton);
			bgroup.add(yesButton);
			JPanel radioPanel = new JPanel();
			radioPanel.setLayout(new GridLayout(1, 2));
			radioPanel.add(noButton);
			radioPanel.add(yesButton);
			myPanel.add(radioPanel);
			//
			myPanel.add(new JLabel("seed: " + Math.abs(Main.seed)));
			myPanel.add(seed_slider);
			myPanel.add(Box.createVerticalStrut(18)); // a spacer
			myPanel.add(new JLabel("Equal or less than these obstacle values?"));
			// ... Create the buttons.
			JRadioButton lButton = new JRadioButton("Less than");
			JRadioButton eButton = new JRadioButton("Equal");
			if (Main.seed < 0) {
				lButton.setSelected(true);
				eButton.setSelected(false);
			} else {
				eButton.setSelected(true);
				lButton.setSelected(false);
			}
			ButtonGroup bgroup2 = new ButtonGroup();
			bgroup2.add(lButton);
			bgroup2.add(eButton);
			JPanel radioPanel2 = new JPanel();
			radioPanel2.setLayout(new GridLayout(1, 2));
			radioPanel2.add(lButton);
			radioPanel2.add(eButton);
			myPanel.add(radioPanel2);
			myPanel.add(Box.createVerticalStrut(10)); // a spacer
			myPanel.add(new JLabel("gold: " + Main.gold));
			myPanel.add(gold_slider);
			// myPanel.add(Box.createHorizontalStrut(15)); // a spacer
			myPanel.add(new JLabel("stone: " + Main.stone));
			myPanel.add(stone_slider);
			// myPanel.add(Box.createHorizontalStrut(15)); // a spacer
			myPanel.add(new JLabel("wood: " + Main.wood));
			myPanel.add(wood_slider);
			myPanel.add(Box.createHorizontalStrut(400)); // a spacer
			int o = JOptionPane.showOptionDialog(frame, myPanel, "Random Function", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			if (o == 0) {
				int seed = 0;
				int gold = 0;
				int stone = 0;
				int wood = 0;
				if (noButton.isSelected()) {
					seed = 1;
				} else {
					seed = seed_slider.getValue();
					if (seed <= 1) {
						seed = 50;
					}
				}
				int mult = 0;
				System.out.println("Done");
				if (lButton.isSelected()) {
					mult = -1;
				} else {
					mult = 1;
				}
				seed = seed * mult;
				gold = gold_slider.getValue();
				stone = stone_slider.getValue();
				wood = wood_slider.getValue();
				Main.seed = seed;
				Main.gold = gold;
				Main.stone = stone;
				Main.wood = wood;
				help(3);
			}
			if (o == 1) {
				Game_Frame.reset_random();
				help(3);
			}
			if (o <= 1) {

				Main.start_game = false;
				Game_Frame.remove_seen_icons();
				Game_Frame.set_icons();
				System.out.println("pause");
			}

			if (o == 2) {
				help(1);
			}
		}
	}
}