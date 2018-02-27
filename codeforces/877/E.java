import java.util.*;
import java.io.*;

/* spar5h */

public class swagdaddy implements Runnable { 
	 
	static ArrayList<Integer>[] adj;
	static ArrayList<Integer> path;
	static int[] tree, lazy, start, end, a;
	static int l, r;

	static void DFS(int x) {

		path.add(x);
		start[x] = path.size();
		
		for(int i = 0; i < adj[x].size(); i++)
			DFS(adj[x].get(i));
		
		path.add(x);
		end[x] = path.size();
	}

	static void build(int n, int nL, int nR) {

		if(nL == nR) {
			tree[n] = a[path.get(nL - 1)]; return;
		}

		build(2 * n, nL, (nL + nR) / 2);
		build(2 * n + 1, (nL + nR) / 2 + 1, nR);

		tree[n] = tree[2 * n] + tree[2 * n + 1];
	}

	static void update(int n, int nL, int nR) {

		if(lazy[n] == 1) {

			tree[n] = nR - nL + 1 - tree[n];

			if(nL != nR) {
				lazy[2 * n] ^= 1;
				lazy[2 * n + 1] ^= 1;
			}

			lazy[n] = 0;
		}

		if(nR < l || nL > r)
			return;

		if(l <= nL && nR <= r) {

			tree[n] = nR - nL + 1 - tree[n];

			if(nL != nR) {
				lazy[2 * n] ^= 1;
				lazy[2 * n + 1] ^= 1;
			}

			return;
		}

		update(2 * n, nL, (nL + nR) / 2);
		update(2 * n + 1, (nL + nR) / 2 + 1, nR);

		tree[n] = tree[2 * n] + tree[2 * n + 1];
	}

	static int query(int n, int nL, int nR) {

		if(lazy[n] == 1) {

			tree[n] = nR - nL + 1 - tree[n];

			if(nL != nR) {
				lazy[2 * n] ^= 1;
				lazy[2 * n + 1] ^= 1;
			}

			lazy[n] = 0;
		}

		if(nR < l || nL > r)
			return 0;

		if(l <= nL && nR <= r) 
			return tree[n];

		int res = query(2 * n, nL, (nL + nR) / 2) + query(2 * n + 1, (nL + nR) / 2 + 1, nR);

		tree[n] = tree[2 * n]  + tree[2 * n + 1];

		return res;
	} 

	public void run() {

		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);

		int N = s.nextInt();

		adj = new ArrayList[N + 1];

		for(int i = 1; i <= N; i++)
			adj[i] = new ArrayList<Integer>();

		for(int i = 2; i <= N; i++)
			adj[s.nextInt()].add(i);

		start = new int[N + 1];
		end = new int[N + 1];

		path = new ArrayList<Integer>();

		DFS(1);

		int temp = path.size();
		int n = (int)Math.pow(2, (int)Math.ceil(Math.log(temp) / Math.log(2)) + 1) - 1;

		a = new int[N + 1];

		for(int i = 1; i <= N; i++)
			a[i] = s.nextInt();

		tree = new int[n + 1];
		lazy = new int[n + 1];

		build(1, 1, path.size());

		int q = s.nextInt();

		while(q-- > 0) {

			String str = s.next();
			
			int x = s.nextInt();
			l = start[x];
			r = end[x];
			
			if(str.equals("pow"))
				update(1, 1, path.size());
			else
				w.println(query(1, 1, path.size()) / 2);
		}


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