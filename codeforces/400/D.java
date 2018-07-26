import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;

/* spar5h */

public class cf1 implements Runnable{    
	
	static class tuple {
		
		int u, v; 
		long w;
		
		tuple(int u, int v, long w) {
			this.u = u; this.v = v; this.w = w;
		}
	}
	
	public void run() {

		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
	
		int n = s.nextInt(), m = s.nextInt(), k = s.nextInt();
		
		int[] c = new int[k + 1];
		
		for(int i = 1; i <= k; i++)
			c[i] = s.nextInt();
		
		ArrayList<Integer>[] adj = new ArrayList[n + 1];
		int[] color = new int[n + 1];
		
		for(int i = 1; i <= n; i++) {
			adj[i] = new ArrayList<Integer>(); adj[i].add(i); color[i] = i;
		}
		
		ArrayList<tuple> edges = new ArrayList<tuple>();
		
		for(int i = 0; i < m; i++)
			edges.add(new tuple(s.nextInt(), s.nextInt(), s.nextLong()));
		
		for(int i = 0; i < m; i++) {
			
			if(edges.get(i).w != 0)
				continue;
			
			int u = edges.get(i).u, v = edges.get(i).v;
			
			int small, big;
			
			if(adj[color[u]].size() < adj[color[v]].size()) {
				small = color[u]; big = color[v];
			}
			
			else {
				small = color[v]; big = color[u];
			}
			
			for(int j = 0; j < adj[small].size(); j++) {
				adj[big].add(adj[small].get(j)); color[adj[small].get(j)] = big;
			}	
		}
		
		int curr = 1;
		boolean res = true;
		
		for(int i = 1; i <= k; i++) {
			
			int key = color[curr];
			
			for(int j = 1; j < c[i]; j++)
				if(key != color[curr + j])
					res = false;
			
			curr += c[i];
		}
		
		if(res) {
			
			w.println("Yes");
			
			curr = 0;
			
			for(int i = 1; i <= k; i++) {
				
				for(int j = 1; j <= c[i]; j++)
					color[curr + j] = i;
				
				curr += c[i];
			}
			
			long[][] d = new long[k + 1][k + 1];
			
			for(int i = 1; i <= k; i++)
				for(int j = 1; j <= k; j++)
					if(i != j) d[i][j] = Long.MAX_VALUE;
			
			for(int i = 0; i < m; i++) {
				
				int u = edges.get(i).u, v = edges.get(i).v;
				long wt = edges.get(i).w;
				
				d[color[u]][color[v]] = Math.min(d[color[u]][color[v]], wt);
				d[color[v]][color[u]] = Math.min(d[color[v]][color[u]], wt);
			}
			
			for(int l = 1; l <= k; l++) 	
				for(int i = 1; i <= k; i++)
					for(int j = 1; j <= k; j++)
						if(d[i][l] != Long.MAX_VALUE && d[l][j] != Long.MAX_VALUE)
							d[i][j] = Math.min(d[i][l] + d[l][j], d[i][j]);
			
			for(int i = 1; i <= k; i++) {
				
				for(int j = 1; j <= k; j++) {
					
					if(d[i][j] != Long.MAX_VALUE)
						w.print(d[i][j] + " ");
					else
						w.print(-1 + " ");
				}
				
				w.println();
			}
		}
		
		else
			w.println("No");
		
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
		new Thread(null, new cf1(),"cf1",1<<26).start();
	}
}   