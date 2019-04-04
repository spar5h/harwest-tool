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
			
			long sum, lazy = flag;
			
			Node() {}
			
			Node(long sum) {
				this.sum = sum;
			}
			
			Node left, right;
		}
		
		long getSum(Node node) {
			
			if(node == null)
				return 0;
			
			return node.sum;
		}
		
		Node build(int nL, int nR, long[] a) {
			
			if(nL == nR) {
				Node node = new Node(a[nL]);
				return node;
			}
			
			Node node = new Node();
			node.left = build(nL, (nL + nR) / 2, a);
			node.right = build((nL + nR) / 2 + 1, nR, a);
			node.sum = getSum(node.left) + getSum(node.right);
			return node;
		}
		
		SegmentTree(long[] a) {
			n = a.length;
			root = build(0, n - 1, a);
		}
		
		void pushLazy(Node node, int nL, int nR) {
			
			if(node.lazy == flag)
				return;
			
			node.sum = node.lazy * (nR - nL + 1);
			
			if(node.left != null)
				node.left.lazy = node.lazy;
			
			if(node.right != null)
				node.right.lazy = node.lazy;
			
			node.lazy = flag;
		}
		
		long query(int l, int r) {
			return queryUtil(root, 0, n - 1, l, r);
		}
		
		long queryUtil(Node node, int nL, int nR, int l, int r) {
			
			pushLazy(node, nL, nR);
			
			if(nR < l || r < nL)
				return 0;
			
			if(l <= nL && nR <= r)
				return node.sum;
			
			long ret = queryUtil(node.left, nL, (nL + nR) / 2, l, r);
			ret += queryUtil(node.right, (nL + nR) / 2 + 1, nR, l, r);
			node.sum = node.left.sum + node.right.sum;
			return ret;
		}
		
		void update(int l, int r, long x) {
			updateUtil(root, 0, n - 1, l, r, x);
		}
		
		void updateUtil(Node node, int nL, int nR, int l, int r, long x) {
			
			pushLazy(node, nL, nR);
			
			if(nR < l || r < nL)
				return;
			
			if(l <= nL && nR <= r) {
				node.lazy = x;
				pushLazy(node, nL, nR);
				return;
			}
			
			updateUtil(node.left, nL, (nL + nR) / 2, l, r, x);
			updateUtil(node.right, (nL + nR) / 2 + 1, nR, l, r, x);
			node.sum = node.left.sum + node.right.sum;
		}
	}
	
	int binarySearch(int l, long x, SegmentTree tree) {
		
		int r = tree.n - 1, ret = l;
		
		while(l <= r) {
			
			int mid = (l + r) / 2;
			
			if(tree.query(mid, mid) <= x) {
				ret = mid; l = mid + 1;
			}
			else
				r = mid - 1;
				
		}
		
		return ret;
	}
	
	public void run() {
		
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int n = s.nextInt();
		
		long[] a = new long[n];
		
		for(int i = 0; i < n; i++)
			a[i] = s.nextLong();
		
		long[] k = new long[n];
		
		for(int i = 1; i < n; i++)
			k[i] = s.nextLong();
		
		for(int i = 1; i < n; i++)
			k[i] += k[i - 1];
		
		for(int i = 0; i < n; i++)
			a[i] -= k[i];
		
		for(int i = 1; i < n; i++)
			k[i] += k[i - 1];
		
		SegmentTree tree = new SegmentTree(a);
		
		int q = s.nextInt();
		
		while(q-- > 0) {
			
			char t = s.next().charAt(0);
			
			if(t == '+') {
				int l = s.nextInt() - 1;
				long x = s.nextLong() + tree.query(l, l);
				int r = binarySearch(l, x, tree);
				tree.update(l, r, x);
			}
			
			else {
				int  l = s.nextInt() - 1, r = s.nextInt() - 1;
				long res = tree.query(l, r) + k[r];
				if(l - 1 >= 0)
					res -= k[l - 1];
				w.println(res);	
			}
		}
		
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