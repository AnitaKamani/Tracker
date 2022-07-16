public class Square {
	int i;
	int j;
	float h;
	float g;
	float Astar;
	int type; // 0=empty, 1=miner, 2=stone, 3=wood, 4=gold, 5=seen
	Square father;

	public Square(int i, int j, int type) {
		this.i = i;
		this.j = j;
		this.type = type;
	}

	public void calculatH(Square goal) {
		int x = i - goal.i;
		int y = j - goal.j;
		h = (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}

	public void calculatg() {
		g = father.g + 1;
		if (type == 2)
			g += 5;
		if (type == 3)
			g += 2;
	}

	public void calculatAstar() {
		Astar = h + g;
	}

	public void print() {
		System.out.println(i + "  " + j);
	}
}