import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
import static java.lang.Math.*;

/* spar5h */

public class cf3 implements Runnable{    
	
	static class pair {

		int i, j;

		pair(int i, int j) {
			this.i = i; this.j = j;
		}
	}

	public void run() {
	    
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int n = s.nextInt(), m = s.nextInt(), k = s.nextInt();

		long[] c = new long[n + 1];

		for(int i = 1; i <= n; i++)
			c[i] = s.nextLong();

		int mapCount = 0;

		HashMap<Long, Integer> hm = new HashMap<Long, Integer>();

		ArrayList<pair>[] edge = new ArrayList[m];

		for(int i = 0; i < m; i++) {

			int u = s.nextInt(), v = s.nextInt(); 

			if(hm.get(c[u] ^ c[v]) == null) {
				hm.put(c[u] ^ c[v], mapCount); edge[mapCount] = new ArrayList<pair>(); mapCount++;
			}

			edge[hm.get(c[u] ^ c[v])].add(new pair(u, v));
		}

		long mod = (long)1e9 + 7, res = 0;
		long[] pow2 = new long[1000000 + 1]; pow2[0] = 1;

		for(int i = 1; i <= 1000000; i++)
			pow2[i] = (pow2[i - 1] * 2) % mod;

		ArrayList<Integer>[] adj = new ArrayList[n + 1];

		int[] vis = new int[n + 1];

		Queue<Integer> q = new LinkedList<Integer>();

		for(int i = 0; i < mapCount; i++) {

			HashSet<Integer> hs = new HashSet<Integer>();

			for(int j = 0; j < edge[i].size(); j++) {
				hs.add(edge[i].get(j).i); hs.add(edge[i].get(j).j);
			}

			for(int j : hs) {
				adj[j] = new ArrayList<Integer>(); vis[j] = 0;
			}

			for(int j = 0; j < edge[i].size(); j++) {
				
				int u = edge[i].get(j).i, v = edge[i].get(j).j;
				adj[u].add(v); adj[v].add(u);
			}

			long val = 1;
			int tot = 0;

			for(int j : hs) {

				if(vis[j] == 1)
					continue;

				q.add(j); vis[j] = 1;
 
				while(!q.isEmpty()) {

					int x = q.poll(); tot++;

					for(int l = 0; l < adj[x].size(); l++) {

						int y = adj[x].get(l);

						if(vis[y] == 0) {
							q.add(y); vis[y] = 1; 
						}
					}
				}

				val = (val * 2) % mod;
			}
        
			val = (val * pow2[n - tot]) % mod;
			res = (res + val) % mod;
		}

		res = (res + pow2[n] * ((pow2[k] - hm.size()) % mod) % mod) % mod;

		w.println(res);

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
		new Thread(null, new cf3(),"cf3",1<<26).start();
	}
}