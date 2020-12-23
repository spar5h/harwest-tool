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
    static int n, m, a[][], si, sj, t;
    
    static int DFS(int k) {
	    
	    int count = t - k;
	    count--;
	    
	    int[][] vis = new int[n][m];
	    int[][] listCount = new int[n][m];
	    
	    stack = new Stack<point>();
	    
	    vis[si][sj] = 1;
	    a[si][sj] = 2;
	    stackAdd(si, sj);
	    
	    if(count == 0)
	           return 0; 
	    
	    outerloop:
	    while(!stack.isEmpty()) {
	        
	        int xi = stack.peek().i;
	        int xj = stack.peek().j;
	        
	        while(listCount[xi][xj] < adj[xi][xj].size()) {
	            
	            int yi = adj[xi][xj].get(listCount[xi][xj]).i;
	            int yj = adj[xi][xj].get(listCount[xi][xj]).j;
	            listCount[xi][xj]++;
	            
	            if(vis[yi][yj] == 0) {
	                
	                a[yi][yj] = 2;
	                count--;
	                stackAdd(yi, yj);
	                vis[yi][yj] = 1;
	                xi = yi;
	                xj = yj;
	                
	                if(count == 0)
	                    break outerloop;
	                    
	                continue;         
	            }
	        }
	        
	        stack.pop();
	    }
	    
	    return 0;
	}
    
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
	
	public void run() {
	    
	    InputReader s = new InputReader(System.in);
	    PrintWriter w = new PrintWriter(System.out);
	    
	    n = s.nextInt();
		m = s.nextInt();
		int k = s.nextInt();
		
		a = new int[n][m];
		
		t = 0;
		
		for(int i = 0; i < n; i++) {
	        
	        String str = s.next();
	        
	        for(int j = 0; j < m; j++) {
	            
	            if(str.charAt(j) == '#')
	                a[i][j] = 1;
	            else {
	                a[i][j] = 0;
	                t++;
	            }
	        }
	    }
		
		adj = new ArrayList[n][m];
	
	    for(int i = 0; i < n; i++)
	        for(int j = 0; j < m; j++)
	            adj[i][j] = new ArrayList<point>();
	    
	    outerloop:  
	    for(int i = 0; i < n; i++) {
	        
	        for(int j = 0; j < m; j++) {
	            
	           if(a[i][j] == 0) {
	                        
	                si = i;
	                sj = j;
	            
	                break outerloop;
	           }
	        } 
	    }  
	        
	    for(int i = 0; i < n; i++) {
	        
	        for(int j = 0; j < m; j++) {
	            
	           if(a[i][j] == 0) {
	                        
	                si = i;
	                sj = j;
	            
	                if(i > 0 && a[i - 1][j] == 0)
	                    addPoint(i, j, i - 1, j); 
	               
	                if(j > 0 && a[i][j - 1] == 0)
	                    addPoint(i, j, i, j - 1); 
	           
	                if(i < n - 1 && a[i + 1][j] == 0)
	                    addPoint(i, j, i + 1, j); 
	                
	                if(j < m - 1 && a[i][j + 1] == 0)
	                    addPoint(i, j, i, j + 1);
	           }
	                
	        } 
	        
	    }
	    

	    DFS(k);
	    
	   for(int i = 0; i < n; i++) {
	        
	        for(int j = 0; j < m; j++) {
	            
	            if(a[i][j] == 1)
	                w.print("#");
	            else if(a[i][j] == 0)
	                w.print("X");
	            else
	                w.print(".");
	        }
	        
	        w.println();
	    }
	    
	    w.close();
	}
}