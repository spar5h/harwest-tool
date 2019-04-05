import java.util.*;
import java.io.*;
import java.math.BigInteger;
import static java.lang.Math.*;
 
/* spar5h */
 
public class cf1 implements Runnable {   
	
	class SegmentTree {
		
		final long flag = Long.MAX_VALUE;
		
		int n;
		Node root;
		
		class Node {
			
			int sum;
			
			Node() {}
			
			Node(int sum) {
				this.sum = sum;
			}
			
			Node left, right;
		}
		
		int getSum(Node node) {
			
			if(node == null)
				return 0;
			
			return node.sum;
		}
		
		Node build(int nL, int nR) {
			
			if(nL == nR) {
				Node node = new Node(0);
				return node;
			}
			
			Node node = new Node();
			node.left = build(nL, (nL + nR) / 2);
			node.right = build((nL + nR) / 2 + 1, nR);
			node.sum = getSum(node.left) + getSum(node.right);
			return node;
		}
		
		SegmentTree(int n) {
			this.n = n;
			root = build(0, n - 1);
		}
		
		long query(int l, int r) {
			return queryUtil(root, 0, n - 1, l, r);
		}
		
		long queryUtil(Node node, int nL, int nR, int l, int r) {
		
			if(nR < l || r < nL)
				return 0;
			
			if(l <= nL && nR <= r)
				return node.sum;
			
			return queryUtil(node.left, nL, (nL + nR) / 2, l, r) + 
					queryUtil(node.right, (nL + nR) / 2 + 1, nR, l, r);
		}
		
		void update(int k) {
			updateUtil(root, 0, n - 1, k);
		}
		
		void updateUtil(Node node, int nL, int nR, int k) {
			
			if(k < nL || nR < k)
				return;
			
			if(nL == k && k == nR) {
				node.sum = 1;
				return;
			}
			
			updateUtil(node.left, nL, (nL + nR) / 2, k);
			updateUtil(node.right, (nL + nR) / 2 + 1, nR, k);
			node.sum = node.left.sum + node.right.sum;
		}
	}
	
	class Pair {
		
		int i, w;
		
		Pair(int i, int w) {
			this.i = i; this.w = w;
		}
	}
	
	class Comp implements Comparator<Pair> {
		
		public int compare(Pair x, Pair y) {
			
			if(x.w < y.w)
				return -1;
			
			if(x.w > y.w)
				return 1;
			
			return 0;
		}
	}
	
	public void run() {
		
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int n = s.nextInt();
		
		Pair[] pair = new Pair[n];
		
		for(int i = 0; i < n; i++)
			pair[i] = new Pair(i, s.nextInt());
		
		Arrays.sort(pair, new Comp());
		
		SegmentTree treeLT = new SegmentTree(n);
		SegmentTree treeLE = new SegmentTree(n);
	
		int l = 0;
		
		long res = 0;
		
		while(l < n) {
			
			int r = l;
			
			while(r < n && pair[l].w == pair[r].w)	
				r++;
			
			for(int i = l; i < r; i++)
				treeLE.update(pair[i].i);
			
			for(int i = l; i < r; i++) 
				res += (long)(pair[i].i - treeLE.query(0, pair[i].i - 1)) * (treeLT.query(pair[i].i + 1, n - 1));
				
			for(int i = l; i < r; i++) 
				treeLT.update(pair[i].i);
			
			l = r;
		}
		
		w.println(res);
		
 		w.close();
	}
	
	static class InputReader {
		
		private InputStream stream;
		private byte[] buf = new byte[1024];
		private int curChar;
		private int numChars;
		private SpaceCharFilter filter;
		
		public InputReader(InputStream stream) {
			this.stream = stream;
		}
		
		public int read() {
			
			if (numChars==-1) 
				throw new InputMismatchException();
			
			if (curChar >= numChars) {
				
				curChar = 0;
				
				try {
					numChars = stream.read(buf);
				}
				catch (IOException e) {
					throw new InputMismatchException();
				}
				
				if(numChars <= 0)				
					return -1;
			}
			
			return buf[curChar++];
		}
		 
		public String nextLine() {
			
			BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
			String str = "";
	        
			try {
	            str = br.readLine();
	        }
	        catch (IOException e) {
	            e.printStackTrace();
	        }
			
	        return str;
		}
		
		public int nextInt() {
		
			int c = read();
			
			while(isSpaceChar(c)) 
				c = read();
			
			int sgn = 1;
			
			if (c == '-') {
				sgn = -1;
				c = read();
			}
			
			int res = 0;
			
			do {
				if(c<'0'||c>'9') 
					throw new InputMismatchException();
				
				res *= 10;
				res += c - '0';
				c = read();
			}
			while (!isSpaceChar(c)); 
			
			return res * sgn;
		}
		
		public long nextLong() {
			
			int c = read();
			
			while (isSpaceChar(c))
				c = read();
			
			int sgn = 1;
			
			if (c == '-') {
				sgn = -1;
				c = read();
			}
			
			long res = 0;
			
			do {
				if (c < '0' || c > '9')
					throw new InputMismatchException();
				
				res *= 10;
				res += c - '0';
				c = read();
			}
			
			while (!isSpaceChar(c));
				return res * sgn;
		}
		
		public double nextDouble() {
			
			int c = read();
			
			while (isSpaceChar(c))
				c = read();
			
			int sgn = 1;
			
			if (c == '-') {
				sgn = -1;
				c = read();
			}
			
			double res = 0;
			
			while (!isSpaceChar(c) && c != '.') {
				
				if (c == 'e' || c == 'E')
					return res * Math.pow(10, nextInt());
				
				if (c < '0' || c > '9')
					throw new InputMismatchException();
				
				res *= 10;
				res += c - '0';
				c = read();
			}
			
			if (c == '.') {
				
				c = read();
				double m = 1;
				while (!isSpaceChar(c)) 
				{
					if (c == 'e' || c == 'E')
						return res * Math.pow(10, nextInt());
					if (c < '0' || c > '9')
						throw new InputMismatchException();
					m /= 10;
					res += (c - '0') * m;
					c = read();
				}
			}
			
			return res * sgn;
		}
		
		public String readString() {
			
			int c = read();
			
			while (isSpaceChar(c))
				c = read();
			
			StringBuilder res = new StringBuilder();
			
			do {
				res.appendCodePoint(c);
				c = read();
			} 
			while (!isSpaceChar(c));
			
			return res.toString();
		}
	 
		public boolean isSpaceChar(int c) {
			
			if (filter != null)
				return filter.isSpaceChar(c);
			
			return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
		}
	 
		public String next() {
			return readString();
		}
		
		public interface SpaceCharFilter {
			public boolean isSpaceChar(int ch);
		}
	
	}
 
	public static void main(String args[]) throws Exception {
		new Thread(null, new cf1(),"cf1",1<<26).start();
	}
} 