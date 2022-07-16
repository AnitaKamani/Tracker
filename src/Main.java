import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class Main {
	public static int m = 8, n = 9;
	public static int[][] matrix = new int[m][n]; // 0=empty, 1=miner, 2=stone, 3=wood, 4=gold, 5=seen
	// default values of the random, if you reset the random they will become these
	// values
	public static final int stone_default = 19, wood_default = 10, gold_default = 7, seed_default = -1;
	public static int stone, wood, gold, seed;

	public static float[][] heuristic = new float[m][n];
	public static boolean start_game = false;
	public static int ways_iterator = 0;
	public static int wait = 0;
	static boolean[][] seen = new boolean[m][n];
	public static Square[][] squares = new Square[m][n];
	static LinkedList<Square> goals = new LinkedList<Square>();
	static priorityQ queqe = new priorityQ();
	static Square miner = new Square(0, 0, 1);
	public static LinkedList<Square> ways = new LinkedList<Square>();
	public static boolean waiting = false;
	public static String goal_list = "";

	public static void main(String[] args) {
		Game_Frame g = new Game_Frame();
	}

	public static void reset_matrix() {
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				matrix[i][j] = 0;
	}

	public static void print_matrix() {
		System.out.println("\n");
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++)
				System.out.print(matrix[i][j] + " ");
			System.out.println("\n");
		}
	}

	public static void random(int stone_r, int wood_r, int gold_r, int seed_r) {
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++)
				matrix[i][j] = 0;
		}
		/*
		 * if obstacles=0 then there are random amount of them
		 * 
		 * if seed=1 or seed=-1 : the seed is not fixed (lesser or greater seed is
		 * fixed)
		 * 
		 * if seed>1 and obstacles!=0 everything is fixed as determined
		 * 
		 * if seed<-1 and obstacles!=0 the amount of obstacles is random BUT bound to
		 * the amount
		 */
		ArrayList<Integer> numbers = new ArrayList();
		for (int i = 0; i < m * n; i++) {
			numbers.add(i);
		}
		Random rd = new Random();
		if (seed_r != 1 && seed_r != -1)
			rd = new Random(seed_r);
		Collections.shuffle(numbers, rd);
		int[][] arr = null;
		int max = m * n - 2;
		// finding the amount of each obstacle
		if (stone_r == 0 && wood_r == 0 && gold_r == 0) {
			stone_r = rd.nextInt(max);
			max -= stone_r;
			wood_r = rd.nextInt(max);
			max -= wood_r;
			gold_r = rd.nextInt(max);
			max -= gold_r;
		} else if (seed_r < 0) {
			stone_r = rd.nextInt(Math.min(max - 2, stone_r + 1));
			max -= stone_r;
			wood_r = rd.nextInt(Math.min(max - 2, wood_r + 1));
			max -= wood_r;
			gold_r = rd.nextInt(Math.min(max - 2, gold_r + 1));
			max -= gold_r;
		}
		int total = 1 + stone_r + wood_r + gold_r;
		arr = new int[2][total];
		for (int i = 0; i < arr[0].length; i++) {
			arr[0][i] = numbers.get(i);
		}
		for (int i = 0; i < total; i++)
			arr[1][i] = arr[0][i] % n;
		for (int i = 0; i < total; i++)
			arr[0][i] = arr[0][i] / n;
		miner.i = arr[0][0];
		miner.j = arr[1][0];
		for (int i = 0; i < total; i++) {
			if (i == 0)
				matrix[arr[0][i]][arr[1][i]] = 1;
			else if (i <= stone_r)
				matrix[arr[0][i]][arr[1][i]] = 2;
			else if (i <= stone_r + wood_r)
				matrix[arr[0][i]][arr[1][i]] = 3;
			else
				matrix[arr[0][i]][arr[1][i]] = 4;
		}
		int gold_counts = 0;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++)
				if (matrix[i][j] == 4)
					gold_counts++;
		}
		if (gold_counts == 0 && gold != 0) {
			Main.random(Main.stone, Main.wood, Main.gold, Main.seed);
		}

	}

	//
	public static void a_star() {
		// declare squars
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++) {
				squares[i][j] = new Square(i, j, matrix[i][j]);
			}
		// reset goal list
		goals = new LinkedList<Square>();
		ways_iterator = 0;
		// find goals in matrix
		find_gold();
		// aculate all the astars of goals
		for (int i = 0; i < goals.size(); i++) {
			ways = new LinkedList<Square>();
			unseen();
			astar(miner, i);
		}
		// sort goals
		sort_goal();
		if (goals.size() == 0) {
			Main.start_game = false;
		}
		goal_list = "";
		for (int i = 0; i < goals.size(); i++) {
			goal_list += ("sorted goalds " + goals.get(i).i + "," + goals.get(i).j + " = " + goals.get(i).Astar + "\n");
		}
		System.out.println(goal_list);
	}

	public static void steps() {
		int j = ways_iterator;
		if (goals.size() == 0) {
			Main.start_game = false;
		} else {
			// astar ways to the goals
			if (j == 0) {
				ways = new LinkedList<Square>();
				unseen();
				astar(miner, 0);
				System.out.println("new ways");
			}
			if (j < ways.size()) {
				// if there is a rock in next step
				if (matrix[ways.get(j).i][ways.get(j).j] == 2) {
					if (wait == 0) {
						wait = 5 + 1;
						waiting = true;
					}
					// if there a wood in next step
				} else if (matrix[ways.get(j).i][ways.get(j).j] == 3) {
					if (wait == 0) {
						wait = 2 + 1;
						waiting = true;
					}
				} // move miner
				if (wait <= 2) {
					wait = 0;
					matrix[miner.i][miner.j] = 5;
					miner.i = ways.get(j).i;
					miner.j = ways.get(j).j;
					matrix[miner.i][miner.j] = 1;
					print_matrix();
					j++;
				}
			}
			if (j >= ways.size()) {
				j = 0;
				a_star();
			}
			ways_iterator = j;
		}
	}

	// this method searches and finds all the goals , the it adds them to a list
	public static void find_gold() {
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++)
				if (squares[i][j].type == 4)
					goals.add(square_clown(squares[i][j]));
		}
	}

	// copy square
	private static Square square_clown(Square square) {
		Square squ = new Square(square.i, square.j, square.type);
		return squ;
	}

	// this method implements astar algorithm on our kth goal
	public static void astar(Square pointer, int k) {
		queqe = new priorityQ();
		Square goal = goals.get(k);
		boolean at_goal = false; // at_goal becomes true whenever we see the goal
		while (at_goal == false) {
			// check up
			if (pointer.i > 0) {
				if (seen[pointer.i - 1][pointer.j] == false) {
					squares[pointer.i - 1][pointer.j].father = pointer;
					squares[pointer.i - 1][pointer.j].calculatH(goal);
					squares[pointer.i - 1][pointer.j].calculatg();
					squares[pointer.i - 1][pointer.j].calculatAstar();
					queqe.enqueue(squares[pointer.i - 1][pointer.j]);
					seen[pointer.i - 1][pointer.j] = true;
				}
			}
			// check down
			if (pointer.i < m - 1) {
				if (seen[pointer.i + 1][pointer.j] == false) {
					squares[pointer.i + 1][pointer.j].father = pointer;
					squares[pointer.i + 1][pointer.j].calculatH(goal);
					squares[pointer.i + 1][pointer.j].calculatg();
					squares[pointer.i + 1][pointer.j].calculatAstar();
					queqe.enqueue(squares[pointer.i + 1][pointer.j]);
					seen[pointer.i + 1][pointer.j] = true;
				}
			}
			// check left
			if (pointer.j > 0) {
				if (seen[pointer.i][pointer.j - 1] == false) {
					squares[pointer.i][pointer.j - 1].father = pointer;
					squares[pointer.i][pointer.j - 1].calculatH(goal);
					squares[pointer.i][pointer.j - 1].calculatg();
					squares[pointer.i][pointer.j - 1].calculatAstar();
					queqe.enqueue(squares[pointer.i][pointer.j - 1]);
					seen[pointer.i][pointer.j - 1] = true;
				}
			}
			// check right
			if (pointer.j < n - 1) {
				if (seen[pointer.i][pointer.j + 1] == false) {
					squares[pointer.i][pointer.j + 1].father = pointer;
					squares[pointer.i][pointer.j + 1].calculatH(goal);
					squares[pointer.i][pointer.j + 1].calculatg();
					squares[pointer.i][pointer.j + 1].calculatAstar();
					queqe.enqueue(squares[pointer.i][pointer.j + 1]);
					seen[pointer.i][pointer.j + 1] = true;
				}
			}
			pointer = queqe.dequeue();
			// Game_Frame.buttons[pointer.i][pointer.j].setText(Float.toString(squares[pointer.i][pointer.j].Astar));
			// if we found goal
			if (pointer.i == goal.i && pointer.j == goal.j) {
				at_goal = true;
				goals.get(k).Astar = pointer.Astar;
				// find the way to kth goal :we put ancestors of goal in "ways" and this list
				// becomes our way to the goal .
				Square temp = pointer;
				while (temp != miner) {
					ways.addFirst(temp);
					temp = temp.father;
				}
			}
		}
	}

	// this method sorts goals list using bubble sort
	public static void sort_goal() {
		int i, j;
		for (i = 0; i < goals.size() - 1; i++)
			// Last i elements are already in place
			for (j = 0; j < goals.size() - i - 1; j++)
				if (goals.get(j).Astar > goals.get(j + 1).Astar) {
					// swap
					Square temp;
					temp = goals.get(j);
					goals.set(j, goals.get(j + 1));
					goals.set(j + 1, temp);
				}
	}

	// this method is used when we want to recalculate astar and we need all the
	// squares become unseen again
	public static void unseen() {
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++) {
				seen[i][j] = false;
			}
	}
}
