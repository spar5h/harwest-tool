import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;

/* spar5h */

public class noob implements Runnable{    
	
	static char[] a;
	static node[] tree;
	static int l, r;
	static boolean lazy[];
	
	static class node {
		
		int n4, n7, nds, nis;
		
		node() {}
		
		node(int n4, int n7, int nds, int nis) {
			this.n4 = n4; this.n7 = n7; this.nds = nds; this.nis = nis;
		}
	}
	
	static void build(int n, int nL, int nR) {
		
		if(nL == nR) {
			
			if(a[nL] == '4')
				tree[n] = new node(1, 0, 1, 1);
			else
				tree[n] = new node(0, 1, 1, 1);
			
			return;
		}	
		
		build(2 * n, nL, (nL + nR) / 2);
		build(2 * n + 1, (nL + nR) / 2 + 1, nR);
		
		tree[n] = new node();
		
		tree[n].n4 = tree[2 * n].n4 + tree[2 * n + 1].n4;
		tree[n].n7 = tree[2 * n].n7 + tree[2 * n + 1].n7;
		
		tree[n].nds = Math.max(tree[2 * n].n4 + tree[2 * n + 1].nds, tree[2 * n].nds + tree[2 * n + 1].n7);
		tree[n].nis = Math.max(tree[2 * n].n7 + tree[2 * n + 1].nis, tree[2 * n].nis + tree[2 * n + 1].n4);
	}
	
	static void update(int n, int nL, int nR) {
		
		if(lazy[n]) {
			
			tree[n].n4 ^= tree[n].n7;
			tree[n].n7 ^= tree[n].n4;
			tree[n].n4 ^= tree[n].n7;
			
			tree[n].nds ^= tree[n].nis;
			tree[n].nis ^= tree[n].nds;
			tree[n].nds ^= tree[n].nis;
			
			if(nL != nR) {
				lazy[2 * n] = !lazy[2 * n];
				lazy[2 * n + 1] = !lazy[2 * n + 1];
			}
			
			lazy[n] = false;
		}
		
		if(nL > r || nR < l)
			return;
		
		if(l <= nL && nR <= r) {
			
			tree[n].n4 ^= tree[n].n7;
			tree[n].n7 ^= tree[n].n4;
			tree[n].n4 ^= tree[n].n7;
			
			tree[n].nds ^= tree[n].nis;
			tree[n].nis ^= tree[n].nds;
			tree[n].nds ^= tree[n].nis;
			
			if(nL != nR) {
				lazy[2 * n] = !lazy[2 * n];
				lazy[2 * n + 1] = !lazy[2 * n + 1];
			}
			
			return;
		}
		
		update(2 * n, nL, (nL + nR) / 2);
		update(2 * n + 1, (nL + nR) / 2 + 1, nR);
		
		tree[n].n4 = tree[2 * n].n4 + tree[2 * n + 1].n4;
		tree[n].n7 = tree[2 * n].n7 + tree[2 * n + 1].n7;
		
		tree[n].nds = Math.max(tree[2 * n].n4 + tree[2 * n + 1].nds, tree[2 * n].nds + tree[2 * n + 1].n7);
		tree[n].nis = Math.max(tree[2 * n].n7 + tree[2 * n + 1].nis, tree[2 * n].nis + tree[2 * n + 1].n4);
	}
	
	public void run() {
	    
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
	
		int N = s.nextInt();
		int q = s.nextInt();
		
		a = (" " + s.next()).toCharArray();
		
		int n = (int)Math.pow(2, (int)Math.ceil(Math.log(N) / Math.log(2)) + 1) - 1;
		
		tree = new node[n + 1];
		lazy = new boolean[n + 1];
		
		build(1, 1, N);
		
		while(q-- > 0) {
			
			String str = s.next();
			
			if(str.equals("switch")) {
				
				l = s.nextInt(); r = s.nextInt();
				update(1, 1, N);
			}
			
			else	
				w.println(tree[1].nds);
		}
		
		w.close();
	}
	
	static class InputReader {
		
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
		new Thread(null, new noob(),"noob",1<<26).start();
	}
}   

