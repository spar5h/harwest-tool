import java.util.*;
import java.io.*;

/* spar5h */

public class swagdaddy implements Runnable { 
	 
	static int c, l, r;
	static int tree[][], lazy[];
	static char[] a;
	static StringBuffer res;
	
	static void build(int n, int nL, int nR) {
		
		if(nL == nR) {
			tree[n][a[nL] - 'a'] = 1; return;
		}
		
		build(2 * n, nL, (nL + nR) / 2);
		build(2 * n + 1, (nL + nR) / 2 + 1, nR);
		
		for(int i = 0; i < 26; i++)
			tree[n][i] = tree[2 * n][i] + tree[2 * n + 1][i];
	}
	
	static void update(int n, int nL, int nR) {
		
		if(lazy[n] != -1) {
			
			tree[n][lazy[n]] = nR - nL + 1;
			
			if(nL != nR) {
				lazy[2 * n] = lazy[n];
				lazy[2 * n + 1] = lazy[n];
			}
			
			lazy[n] = -1;
		}
		
		if(l > nR || nL > r || l > r)
			return;
		
		if(l <= nL && nR <= r) {
	
			tree[n] = new int[26];
			tree[n][c] = nR - nL + 1;
			
			if(nL != nR) {
				lazy[2 * n] = c;
				lazy[2 * n + 1] = c;
			}
		
			return;
		}
		
		update(2 * n, nL, (nL + nR) / 2);
		update(2 *n + 1, (nL + nR) / 2 + 1, nR);
		
		for(int i = 0; i < 26; i++)
			tree[n][i] = tree[2 * n][i] + tree[2 * n + 1][i];
	}
	
	static int query(int n, int nL, int nR) {

		if(lazy[n] != -1) {
			
			tree[n] = new int[26];
			tree[n][lazy[n]] = nR - nL + 1;
			
			if(nL != nR) {
				lazy[2 * n] = lazy[n];
				lazy[2 * n + 1] = lazy[n];
			}
			
			lazy[n] = -1;
		}
		
		if(l > nR || nL > r || l > r)
			return 0;
			
		if(l <= nL && nR <= r)
			return tree[n][c];
			
		int res = query(2 * n, nL, (nL + nR) / 2) + query(2 * n + 1, (nL + nR) / 2 + 1, nR);
		
		for(int i = 0; i < 26; i++)
			tree[n][i] = tree[2 * n][i] + tree[2 * n + 1][i];
		
		return res;
	}
	
	static void ans(int n, int nL, int nR) {
		
		if(lazy[n] != -1) {
			
			tree[n] = new int[26];
			tree[n][lazy[n]] = nR - nL + 1;
			
			if(nL != nR) {
				lazy[2 * n] = lazy[n];
				lazy[2 * n + 1] = lazy[n];
			}
			
			lazy[n] = -1;
		}
		
		if(nL == nR) {
			
			for(int i = 0; i < 26; i++)
				if(tree[n][i] == 1)
					res.append((char)('a' + i));
			
			return;
		}
		
		ans(2 * n, nL, (nL + nR) / 2);
		ans(2 * n + 1, (nL + nR) / 2 + 1, nR);
		
		for(int i = 0; i < 26; i++)
			tree[n][i] = tree[2 * n][i] + tree[2 * n + 1][i];
	}
	
	public void run() {
		
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int N = s.nextInt();
		int q = s.nextInt();
		
		a = (" " + s.next()).toCharArray();
		
		int n = (int)Math.pow(2, (int)Math.ceil(Math.log(N) / Math.log(2)) + 1) - 1;
		tree = new int[n + 1][26];
		lazy = new int[n + 1]; Arrays.fill(lazy, -1);
		
		build(1, 1, N);
		
		while(q-- > 0) {
			
			int tL = s.nextInt();
			int tR = s.nextInt();
			int k = s.nextInt();
				
			l = tL; r = tR;
			
			int[] b = new int[26];
				
			for(int i = 0; i <  26; i++) {
				c = i; b[i] = query(1, 1, N);
			}
				
			int prevL = tL;
			
			if(k == 1) {
			
				for(int i = 0; i < 26; i++) {
					
					l = prevL; r = prevL + b[i] - 1; c = i;
					
					update(1, 1, N);
					
					prevL = r + 1;
				}
				
			}
			
			else {
				
				for(int i = 25; i >= 0; i--) {
					
					l = prevL; r = prevL + b[i] - 1; c = i;
					
					update(1, 1, N);
					
					prevL = r + 1;
				}
				
			}
		}
		
		res = new StringBuffer("");
		
		ans(1, 1, N);
		
		w.println(res);
		
		w.close();
	}
	
	
	static class InputReader
	{
		private InputStream stream;
		private byte[] buf = new byte[1024];
		private int curChar;
		private int numChars;
		private SpaceCharFilter filter;
		
		public InputReader(InputStream stream)
		{
			this.stream = stream;
		}
		
		public int read()
		{
			if (numChars==-1) 
				throw new InputMismatchException();
			
			if (curChar >= numChars)
			{
				curChar = 0;
				try 
				{
					numChars = stream.read(buf);
				}
				catch (IOException e)
				{
					throw new InputMismatchException();
				}
				
				if(numChars <= 0)				
					return -1;
			}
			return buf[curChar++];
		}
	 
		public String nextLine()
		{
			BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
			String str = "";
            try
            {
                str = br.readLine();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            return str;
		}
		public int nextInt()
		{
			int c = read();
			
			while(isSpaceChar(c)) 
				c = read();
			
			int sgn = 1;
			
			if (c == '-') 
			{
				sgn = -1;
				c = read();
			}
			
			int res = 0;
			do 
			{
				if(c<'0'||c>'9') 
					throw new InputMismatchException();
				res *= 10;
				res += c - '0';
				c = read();
			}
			while (!isSpaceChar(c)); 
			
			return res * sgn;
		}
		
		public long nextLong() 
		{
			int c = read();
			while (isSpaceChar(c))
				c = read();
			int sgn = 1;
			if (c == '-') 
			{
				sgn = -1;
				c = read();
			}
			long res = 0;
			
			do 
			{
				if (c < '0' || c > '9')
					throw new InputMismatchException();
				res *= 10;
				res += c - '0';
				c = read();
			}
			while (!isSpaceChar(c));
				return res * sgn;
		}
		
		public double nextDouble() 
		{
			int c = read();
			while (isSpaceChar(c))
				c = read();
			int sgn = 1;
			if (c == '-') 
			{
				sgn = -1;
				c = read();
			}
			double res = 0;
			while (!isSpaceChar(c) && c != '.') 
			{
				if (c == 'e' || c == 'E')
					return res * Math.pow(10, nextInt());
				if (c < '0' || c > '9')
					throw new InputMismatchException();
				res *= 10;
				res += c - '0';
				c = read();
			}
			if (c == '.') 
			{
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
		
		public String readString() 
		{
			int c = read();
			while (isSpaceChar(c))
				c = read();
			StringBuilder res = new StringBuilder();
			do 
			{
				res.appendCodePoint(c);
				c = read();
			} 
			while (!isSpaceChar(c));
			
			return res.toString();
		}
	 
		public boolean isSpaceChar(int c) 
		{
			if (filter != null)
				return filter.isSpaceChar(c);
			return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
		}
	 
		public String next() 
		{
			return readString();
		}
		
		public interface SpaceCharFilter 
		{
			public boolean isSpaceChar(int ch);
		}
	}
    
	public static void main(String args[]) throws Exception
	{
		new Thread(null, new swagdaddy(),"swagdaddy",1<<26).start();
	}
	   
} 