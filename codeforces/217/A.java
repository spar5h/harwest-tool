import java.util.*;
import java.lang.*;
import java.io.*;

/* Name of the class has to be "Main" only if the class is public. */
public class DFS implements Runnable
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
		new Thread(null, new DFS(),"DFS",1<<26).start();
	}
	
	static Stack<point> stack;
    static ArrayList<point>[][] adj;
    static ArrayList<point> pos;
    static int n;
    static boolean vis[][];
    
     static class point{
        
        int i = 0;
        int j = 0;
    }
	
	static void addPoint(int i, int j, int newI, int newJ) {
	    
	     point p = new point();
	     p.i = newI;
	     p.j = newJ;
	     adj[i][j].add(p);
	}
	
	static void stackAdd(int i, int j) {
	    
	    point p = new point();
	    p.i = i;
	     p.j = j;
	    stack.push(p);
	}
    
    static void DFS(point p) {
	    
	    int[][] listCount = new int[1001][1001];
	    
	    stack = new Stack<point>();
	    
	    vis[p.i][p.j] = true;
	    stack.push(p);
	    
	    outerloop:
	    while(!stack.isEmpty()) {
	        
	        int xi = stack.peek().i;
	        int xj = stack.peek().j;
	        
	        while(listCount[xi][xj] < adj[xi][xj].size()) {
	            
	            int yi = adj[xi][xj].get(listCount[xi][xj]).i;
	            int yj = adj[xi][xj].get(listCount[xi][xj]).j;
	            
	            listCount[xi][xj]++;
	            
	            if(!vis[yi][yj]) {
	                
	                stackAdd(yi, yj);
	                vis[yi][yj] = true;
	                xi = yi;
	                xj = yj;
	                    
	                continue;         
	            }
	        }
	        
	        stack.pop();
	    }
	    
	}
	
	public void run() {
	    
	    InputReader s = new InputReader(System.in);
	    PrintWriter w = new PrintWriter(System.out);
	    
	    n = s.nextInt();
		
		pos = new ArrayList<point>();
		adj = new ArrayList[1001][1001];
        vis = new boolean[1001][1001];
	        
	    for(int i = 0; i < n; i++) {
	        
	        int n1 = s.nextInt();
	        int n2 = s.nextInt();
	        
	        adj[n1][n2] = new ArrayList<point>();
	        
	        point p = new point();
	        p.i = n1;
	        p.j = n2;
	        pos.add(p);
	    }
	    
	    for(int i = 0; i < n; i++) {
	        
	        int xi = pos.get(i).i;
	        int xj = pos.get(i).j;
	        
	        for(int j = 0; j < n; j++) {
	            
	            if(i != j) {
	                
	                int yi = pos.get(j).i;
	                int yj = pos.get(j).j;
	                
	                if(xi == yi || xj == yj) 
	                    addPoint(xi, xj, yi, yj);

	                    
	            }
	        }
	    }
	    
	    int ans = -1;
	    
	    for(int i = 0; i < pos.size(); i++) {
	        
	        point p = new point();
	        p.i = pos.get(i).i;
	        p.j = pos.get(i).j;
	        
	        if(!vis[p.i][p.j]) {
	            
	            ans++;
	            
	            DFS(p);
	        }
	    }
	 
	    w.println(ans);
	    
	    w.close();
	}
}