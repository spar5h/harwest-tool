import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;

/* Name of the class has to be "Main" only if the class is public. */
public class codeforces implements Runnable
{    
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
		new Thread(null, new codeforces(),"codeforces",1<<26).start();
	}
	
	public void run() {
		
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int n = s.nextInt();
		int v = s.nextInt();
		
		ArrayList<Integer>[] adj = new ArrayList[n + 1];
		
		for(int i = 1; i <= n; i++)
			adj[i] = new ArrayList<Integer>();
		
		for(int i = 0; i < n - 1; i++) {
			
			int n1 = s.nextInt();
			int n2 = s.nextInt();
			
			adj[n1].add(n2);
			adj[n2].add(n1);
		}
		
		Queue<Integer> q = new LinkedList<Integer>();
		
		int[] level = new int[n + 1];
		Arrays.fill(level, -1);
		
		level[1] = 0;
		q.add(1);
		
		while(!q.isEmpty()) {
			
			int x = q.peek();
			
			for(int i = 0; i < adj[x].size(); i++) {
				
				int y = adj[x].get(i);
				
				if(level[y] == -1) {
					
					level[y] = level[x] + 1;
					q.add(y);
				}
			}
			
			q.remove();
		}
		
		int[] level2 = new int[n + 1];
		Arrays.fill(level2, -1);
		
		level2[v] = 0;
		q.add(v);

		while(!q.isEmpty()) {
			
			int x = q.peek();
			
			for(int i = 0; i < adj[x].size(); i++) {
				
				int y = adj[x].get(i);
				
				if(y == 1)
					continue;
				
				if(level2[y] == -1) {
					
					level2[y] = level2[x] + 1;
					q.add(y);
				}
			}
			
			q.remove();
		}
		
		int ans = 0;
		
		for(int i = 1; i <= n; i++) {
			
			if(level2[i] >= 0 && level2[i] < level[i] && level[i] > ans)
				ans = level[i];
		}
		
		w.println(ans * 2);
		
		w.close();
	}
	   
}