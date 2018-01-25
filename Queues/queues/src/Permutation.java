
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
	public static void main(String[] args) {
		RandomizedQueue<String> RQ = new RandomizedQueue<String>();
		int num = Integer.parseInt(args[0]);
		while (!StdIn.isEmpty()) {
			String str = StdIn.readString();
			RQ.enqueue(str);
		}
		for (String s : RQ) {
			if (num == 0)
				break;
			StdOut.println(s);
			num--;
		}
	}
}
