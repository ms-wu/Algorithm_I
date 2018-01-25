//这个类用于实现最大优先队列和堆排序
public class MaxPQ<Key extends Comparable<Key>> {
	private Key[] pq; // 基于堆的完全二叉树
	private int N = 0; // 数据存储于pq[1..N]中，pq[0]不做使用
	
	@SuppressWarnings("unchecked")
	public MaxPQ(int maxN) {
		pq = (Key[]) new Comparable[maxN + 1];
	}
	
	public void Insert(Key v) { /// 插入二叉树最底部，然后上浮，保持完全二叉树形状
		pq[++N] = v;
		swin(N);
	}
	
	public Key max() { // 根节点即为最大节点
		Key max = pq[1];
		return max;
	}
	
	public Key delMax() {
		Key max = pq[1];
		exch(1, N--);
		pq[N+1] = null; // 防止对象游离
		sink(1);
		return max;
	}
	
	public boolean isEmpty() {
		return N == 0;
	}
	
	public int size() {
		return N;
	}
	
	public boolean less(int i, int j) {
		return pq[i].compareTo(pq[j]) < 0;
	}
	
	public void exch(int i, int j) {
		Key t = pq[i];
		pq[i] = pq[j];
		pq[j] = t;
	}
	
	public void swin(int k) {
		while (k > 1 && less(k/2, k)) {
			exch(k/2, k);
			k = k/2;
		}
	}
	
	public void sink(int k) {
		while (2*k <= N) {
			int j = 2*k;
			if (j < N && less(j, j+1))
				j++;
			if (!less(k, j))
				break;
			exch(k, j);
			k = j;
		}
	}
	
	private static boolean less(Comparable[] pq, int i, int j) {
		return pq[i-1].compareTo(pq[j-1]) < 0;
	}
	
	private static void exch(Object []pq, int i, int j) {
		Object swap = pq[i-1];
		pq[i-1] = pq[j-1];
		pq[j-1] = swap;
	}
	
	private static void sink(Comparable[] a, int k, int N) {
		while (2*k <= N) {
			int j = 2*k;
			if (j < N && less(a, j, j+1)) j++;
			if (!less(a, k, j)) break;
			exch(a, k, j);
			k = j;
		}
	}
	
	public static void sort(Comparable[] pq) {
		int N = pq.length;
		for (int k = N/2; k >= 1; k--) {
			sink(pq, k, N);
		}
		while (N > 1) {
			exch(pq, 1, N--);
			sink(pq, 1, N);
		}
	}
	
	public static void show(Comparable[] pq) {
		for (int i = 0; i < pq.length; i++)
			System.out.println(pq[i]);
	}
	
	public static void main(String[] args) {
		String[] a = {"S","O","R","T","E","X","A","M","P","L","E"};
		MaxPQ.sort(a);
		MaxPQ.show(a);
	}
}
