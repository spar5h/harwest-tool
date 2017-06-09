import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;

/* spar5h */

public class codechef implements Runnable
{    
	
	static double sqrDist(double x1, double y1, double x2, double y2) {
		
		return (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
	}
	
	public void run() {
		
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int n = s.nextInt();
		
		double[] x = new double[n + 1], y = new double[n + 1], r = new double[n + 1];
		
		for(int i = 1; i <= n; i++) {
			x[i] = s.nextInt(); y[i] = s.nextInt(); r[i] = s.nextInt();
		}
		
		int[] parent = new int[n + 1];
		
		for(int i = 1; i <= n; i++) {
			
			for(int j = 1; j <= n; j++) {
				
				if(i == j)
					continue;
				
				if(r[j] > r[i])
					continue;
				
				if(parent[j] != 0 && r[parent[j]] < r[i])
					continue;
				
				if(sqrDist(x[i], y[i], x[j], y[j]) < r[i] * r[i])	
					parent[j] = i;
				
			}
		}
		
		ArrayList<Integer>[] adj = new ArrayList[n + 1];
		
		for(int i = 1; i <= n; i++)
			adj[i] = new ArrayList<Integer>();
		
		for(int i = 1; i <= n; i++) {
			
			for(int j = 1; j <= n; j++) {
				
				if(parent[j] == i) 	
					adj[i].add(j);
				
			}
		}
		
		/* (Graph Checker)
		 
		for(int i = 1; i <= n; i++) {
			
			w.print(i + " // ");
			
			for(int j = 0; j < adj[i].size(); j++) {
				
				w.print(adj[i].get(j) + " ");
			}
			
			w.println();
		}
		*/
		
		int[] level = new int[n + 1];
		Arrays.fill(level,  -1);
		
		Queue<Integer> q = new LinkedList<Integer>();
		
		double ans = 0;
		
		for(int i = 1; i <= n; i++) {
			
			if(parent[i] != 0)
				continue;
			
			q.add(i);
			level[i] = 0;
			ans += r[i] * r[i];
		}
		

		int lastLevel = 0;
		int factor = 1;
		
		while(!q.isEmpty()) {
			
			int v1 = q.poll();
			
			if(lastLevel != level[v1]) {
				factor *= -1; lastLevel = level[v1];
			}	
			
			for(int j = 0; j < adj[v1].size(); j++) {
				
				int v2 = adj[v1].get(j);
				level[v2] = level[v1] + 1;
				q.add(v2);
				ans += factor * r[v2] * r[v2];
			}
		}
		
		w.printf("%.8f", Math.PI * ans);
		
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
		new Thread(null, new codechef(),"codechef",1<<26).start();
	}
		
}