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
	
	static ArrayList<Integer>[] adj, pathNum;
	static int[] vis, paths;
	static int n, d, count;
	static int[] parent, level;
	static Queue<Integer> queue;
	
	public void run() {
	    
	    InputReader s = new InputReader(System.in);
	    PrintWriter w = new PrintWriter(System.out);
	    
	    n = s.nextInt();
	    int k = s.nextInt();
	    d = s.nextInt();
	    
	    int[] p = new int[n + 1];
	    ArrayList<Integer> pList = new ArrayList<Integer>(); 
	    
	    for(int i = 0; i < k; i++) {
	    	
	    	int x = s.nextInt();
	    	
	    	if(p[x] == 1)
	    		continue;
	    	
	    	p[x] = 1;
	    	pList.add(x);
	    }
	    
	    adj = new ArrayList[n + 1];
	    pathNum = new ArrayList[n + 1];
	    
	    for(int i = 1; i <= n; i++) {
	    	adj[i] = new ArrayList<Integer>();
	    	pathNum[i] = new ArrayList<Integer>();
	    }	
	    
	    
	    for(int i = 1; i <= n - 1; i++) {
	    	
	    	int n1 = s.nextInt();
	    	int n2 = s.nextInt();
	    	
	    	adj[n1].add(n2);
	    	adj[n2].add(n1);
	    	
	    	pathNum[n1].add(i);
	    	pathNum[n2].add(i);
	    }
	    
	    vis = new int[n + 1];
	    paths = new int[n];
	    count = n - 1;
	    
	    queue = new LinkedList<Integer>();
		level = new int[n + 1];
		Arrays.fill(level, -1);
		
		for(int i = 0; i < pList.size(); i++) {
			
			int v = pList.get(i);
			
			vis[v] = 1;
			level[v] = 0;
			queue.add(v);
		}
		
		while(!queue.isEmpty()) {
			
			int x = queue.poll();
			
			if(level[x] == d)
				break;
			
			for(int i = 0; i < adj[x].size(); i++) {
				
				int y = adj[x].get(i);
				int q = pathNum[x].get(i);
				
				if(vis[y] == 0) {
					
					vis[y] = 1;
					paths[q] = 1;
					count--;
					queue.add(y);
				}
	
			}
			
		}	
		
	    w.println(count);
	    
	    for(int i = 1; i <= n - 1; i++) {
	    	
	    	if(paths[i] == 0)
	    		w.print(i + " ");
	    }
	    
	    w.close();
	}
}