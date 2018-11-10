import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
import static java.lang.Math.*;

/* spar5h */

public class cf1 implements Runnable{    
	
	static int k = 20;
	static long[] pow2 = new long[k];
	static int[][] a, tree, lazy;
	
	static void build(int n, int nL, int nR) {
		
		if(nL == nR) {
			
			for(int i = 0; i < k; i++)
				tree[n][i] = a[nL][i];
			
			return;
		}
		
		build(2 * n + 1, nL, (nL + nR) / 2);
		build(2 * n + 2, (nL + nR) / 2 + 1, nR);
		
		for(int i = 0; i < k; i++)
			tree[n][i] = tree[2 * n + 1][i] + tree[2 * n + 2][i];
	}
	
	static long query(int n, int nL, int nR, int l, int r) {
		
		for(int i = 0; i < k; i++) {
			
			if(lazy[n][i] == 1) {
				
				tree[n][i] = nR - nL + 1 - tree[n][i];
			
				if(nL != nR) {
					lazy[2 * n + 1][i] ^= 1;
					lazy[2 * n + 2][i] ^= 1;
				}
				
				lazy[n][i] = 0;
			}
		}
		
		if(nR < l || r < nL)
			return 0;
		
		long res = 0;
		
		if(l <= nL && nR <= r) {
			
			for(int i = 0; i < k; i++)
				res += pow2[i] * tree[n][i];
			
			return res;
		}
		
		res += query(2 * n + 1, nL, (nL + nR) / 2, l, r) + query(2 * n + 2, (nL + nR) / 2 + 1, nR, l, r);
		
		for(int i = 0; i < k; i++)
			tree[n][i] = tree[2 * n + 1][i] + tree[2 * n + 2][i];
		
		return res;
	}
	
	static void update(int n, int nL, int nR, int l, int r, int[] x) {
		
		for(int i = 0; i < k; i++) {
			
			if(lazy[n][i] == 1) {
				
				tree[n][i] = nR - nL + 1 - tree[n][i];
			
				if(nL != nR) {
					lazy[2 * n + 1][i] ^= 1;
					lazy[2 * n + 2][i] ^= 1;
				}
				
				lazy[n][i] = 0;
			}
		}
		
		if(nR < l || r < nL)
			return;
		
		if(l <= nL && nR <= r) {
			
			for(int i = 0; i < k; i++) {
				
				if(x[i] == 0)
					continue;
				
				tree[n][i] = nR - nL + 1 - tree[n][i];
				
				if(nL != nR) {
					lazy[2 * n + 1][i] ^= 1;
					lazy[2 * n + 2][i] ^= 1;
				}	
			}
			
			return;
		}
		
		update(2 * n + 1, nL, (nL + nR) / 2, l, r, x);
		update(2 * n + 2, (nL + nR) / 2 + 1, nR, l, r, x);
		
		for(int i = 0; i < k; i++)
			tree[n][i] = tree[2 * n + 1][i] + tree[2 * n + 2][i];
	}
	
	public void run() {
		
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		pow2[0] = 1;
		
		for(int i = 1; i < k; i++)
			pow2[i] = 2 * pow2[i - 1];
		
		int N = s.nextInt();
		
		a = new int[N][20];
		
		for(int i = 0; i < N; i++) {
			
			int x = s.nextInt(), c = 0;
			
			while(x > 0) {
				a[i][c] = x % 2; x /= 2; c++;
			}
		}
		
		int n = (int)pow(2, (int)ceil(log(N) / log(2)) + 1);
		tree = new int[n][k];
		lazy = new int[n][k];
		
		build(0, 0, N - 1);
		
		int q = s.nextInt();
		
		while(q-- > 0) {
			
			int t = s.nextInt(), l = s.nextInt() - 1, r = s.nextInt() - 1;
			
			if(t == 2) {
				
				int temp = s.nextInt(), c = 0;
				
				int[] x = new int[k];
				
				while(temp > 0) {
					x[c] = temp % 2; temp /= 2; c++;
				}
				
				update(0, 0, N - 1, l, r, x);
			}
			
			else 
				w.println(query(0, 0, N - 1, l, r));
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