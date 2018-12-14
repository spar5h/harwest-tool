import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
import static java.lang.Math.*;

/* spar5h */

public class cf1 implements Runnable{    
	
	class Treap {
	
    	Random r = new Random();
    	
    	class Node {
    		
    		int key = 1, pri, rev = 0;
    		long val, sum;
    		Node left, right;
    		
    		Node(long val) {
    			this.val = val; sum = val;
    			this.pri = r.nextInt(Integer.MAX_VALUE);
    		}
    	}
    	
    	class Pair {
    		
    		Node left, right;
    		
    		Pair() {}
    		
    		Pair(Node left, Node right) {
    			this.left = left; this.right = right;
    		}
    	}
    	
    	Node root;
    	
    	int nodeKey(Node x) {
    		
    		if(x == null)
    			return 0;
    		
    		return x.key;
    	}
    	
    	void updateKey(Node x) {
    		
    		x.key = 1;
    		
    		if(x.left != null) 
    			x.key += x.left.key;
    			
    		if(x.right != null) 
    			x.key += x.right.key;
    	}
    	
    	void pushRev(Node x) {
    		
    		if(x.rev == 0)
    			return;
    		
    		if(x.left != null)
    			x.left.rev ^= 1;
    		
    		if(x.right != null)
    			x.right.rev ^= 1;
    		
    		x.rev = 0;
    		
    		Node temp = x.left;
    		x.left = x.right;
    		x.right = temp;
    	}
    	
    	Pair split(Node x, int key) {
    		
    		if(x == null)
    			return new Pair();
    		
    		pushRev(x);
    		
    		if(key <= nodeKey(x.left)) {
    			Pair pair = split(x.left, key);
    			x.left = null;
    			updateKey(x);
    			return new Pair(pair.left, meld(pair.right, x));
    		}
    		
    		else {
    			Pair pair = split(x.right, key - nodeKey(x.left) - 1);
    			x.right = null;
    			updateKey(x);
    			return new Pair(meld(x, pair.left), pair.right);
    		}
    	}
    	
    	Node meld(Node x, Node y) {
    		
    		if(x == null)
    			return y;
    		
    		if(y == null)
    			return x;
    		
    		pushRev(x); pushRev(y);
    		
    		if(x.pri <= y.pri) { 
    			x.right = meld(x.right, y); 
    			updateKey(x);
    			return x;
    		}
    		
    		else {
    			y.left = meld(x, y.left);
    			updateKey(y);
    			return y;
    		}
    	}
    
    	long searchCall(int key) {
    		return search(root, key);
    	}
    	
    	long search(Node x, int key) {
    		
    		if(x == null)
    			return -1; //not present
    		
    		pushRev(x);
    		
    		if(key < nodeKey(x.left)) 
    			return search(x.left, key);
    		
    		else if(key > nodeKey(x.left)) 
    			return search(x.right, key - nodeKey(x.left) - 1);
    		
    		return x.val;
    	}
    	
    	void insertCall(int key, long val) {
    		root = insert(root, key, val);
    	}
    	
    	Node insert(Node x, int key, long val) {
    		Pair pair = split(x, key);
    		Node y = new Node(val);
    		return meld(meld(pair.left, y), pair.right);
    	}
    	
    	void deleteCall(int key) {
    		root = delete(root, key);
    	}
    	
    	Node delete(Node x, int key) {
    		
    		if(x == null)
    			return x;
    		
    		pushRev(x);
    		
    		if(key < nodeKey(x.left)) 
    			x.left = delete(x.left, key);
    		
    		else if(key > nodeKey(x.left)) 
    			x.right = delete(x.right, key - nodeKey(x.left) - 1);
    		
    		else
    			return meld(x.left, x.right);
    		
    		updateKey(x);
    		return x;
    	}
    	
    	void rangeReversal(int l, int r) {
    		
    		Pair p1 = split(root, l);
    		Pair p2 = split(p1.right, r - l + 1);
    		
    		p2.left.rev ^= 1;
    		
    		root = meld(p1.left, meld(p2.left, p2.right));
    	}
    	
    	void rangeShift(int l, int r) {
    		
    		Pair p1 = split(root, l);
    		Pair p2 = split(p1.right, r - l + 1);
    		Pair p3 = split(p2.left, r - l);
    		
    		root = meld(p1.left, meld(meld(p3.right, p3.left), p2.right));
    	}
    }
	
	public void run() {
		
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		Treap treap = new Treap();
		
		int n = s.nextInt(), q = s.nextInt(), m = s.nextInt();
		
		for(int i = 0; i < n; i++)
			treap.insertCall(i, s.nextLong());
		
		while(q-- > 0) {
			
			if(s.nextInt() == 1)
				treap.rangeShift(s.nextInt() - 1, s.nextInt() - 1);
			else
				treap.rangeReversal(s.nextInt() - 1, s.nextInt() - 1);
		}
		
		while(m-- > 0) 
			w.print(treap.searchCall(s.nextInt() - 1) + " ");
		
		w.println();
		
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