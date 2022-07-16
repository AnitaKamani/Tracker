import java.util.LinkedList;

public class priorityQ {
	public static LinkedList<Square> queue;

	public priorityQ() {
		queue = new LinkedList<Square>();
	}

	// remove first
	public Square dequeue() {
		if (queue.isEmpty())
			return null;
		Square first = queue.get(0);
		queue.remove(0);
		return first;
	}

	// add square
	public void enqueue(Square sq) {
		queue.add(sq);
		sort();
	}

	public void print() {
		for (int i = 0; i < queue.size(); i++)
			System.out.println(queue.get(i).i + " " + queue.get(i).j);
	}

	// bubble sort
	void sort() {
		int i, j;
		for (i = 0; i < queue.size() - 1; i++)
			// Last i elements are already in place
			for (j = 0; j < queue.size() - i - 1; j++)
				if (queue.get(j).Astar > queue.get(j + 1).Astar) {
					// swap
					Square temp;
					temp = queue.get(j);
					queue.set(j, queue.get(j + 1));
					queue.set(j + 1, temp);
				}
	}

	public int getsize() {
		return queue.size();
	}

	public Square get(int i) {
		return queue.get(i);
	}
}