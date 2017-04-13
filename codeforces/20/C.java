import java.util.*;
import java.lang.*;
import java.io.*;

/* Name of the class has to be "Main" only if the class is public. */
 public class Dijkstra implements Runnable
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
		new Thread(null, new Dijkstra(),"dijkstra",1<<26).start();
	}
	
	public static ArrayList<point>[] adj;
	public static int n, e, v;
	public static long[] d;
	public static int[] parent;
	static PriorityQueue<point> pq ;
	static PrintWriter w;
	static boolean b;
	      
	static class point{
	    
	    int i = 0;
	    long w = 0;
	}
	
	static class DistanceComparator implements Comparator<point> {
		
	    public int compare(point x, point y) {
	    	
	        if (x.w < y.w)
	            return -1;
	        
	        if (x.w > y.w)
	            return 1;
	        
	        return 0;
	    }
	}
	
	static void addEdge(int n1, int n2, long w) {
			
	    	point p = new point();
	    	p.i = n2;
	    	p.w = w;
			adj[n1].add(p);
	} 
	
	static void pqAdd(int n, long w) {
		
		 point p = new point();
	     p.i = n;
	     p.w = w;
	     pq.add(p);
	}
	  
	static void printPath(int parent[], int j)
	{
		
	    if (parent[j] == -1) {
	    	
	    	w.print(j + " ");
	        return;
	    }
	    
	    printPath(parent, parent[j]);
	 
	    w.print(j + " ");
	}
	
	static void DSP(int start, int end) {
        
		parent = new int[n + 1];
		Arrays.fill(parent, -1);
		
    	d = new long[n + 1];
    	Arrays.fill(d, Long.MAX_VALUE);	
       
        Comparator<point> comp = new DistanceComparator();
        pq = new PriorityQueue<point>(11, comp);
        
        d[start] = 0;
        int x = start;
        pqAdd(start, 0);
        
        while(!pq.isEmpty()) {
        	
        	x = pq.peek().i;
        	
        	if(x == end)
        		break;
        		
     
            for (int j = 0; j < adj[x].size(); j++) {
               
                int y = adj[x].get(j).i; 
                long w = adj[x].get(j).w;
     
                if (d[y] > d[x] + w)  {
                    
                    d[y] = d[x] + w;
                    pqAdd(y, d[y]);
                    parent[y] = x;
                }
            }
            
            pq.remove();
        }
        
    }
	    
	public void run() {
	    
	    InputReader s = new InputReader(System.in);
	    w = new PrintWriter(System.out);
	   
        n = s.nextInt();
	    e = s.nextInt();
	    
	    adj = new ArrayList[n + 1];
        
    	for(int i = 1; i <= n; i++) 
        	adj[i] = new ArrayList<point>();
	     
        for(int i = 0; i < e; i++) {
       	 
       	 	int n1 = s.nextInt();
       	 	int n2 = s.nextInt();
       	 	long pl = s.nextLong();
       	 
       	 	addEdge(n1, n2, pl);
       	 	addEdge(n2, n1, pl);
        }
        
        DSP(1, n);
	    
        if(parent[n] != -1)
        	printPath(parent, n);
        else
        	w.println(-1);
        
	    w.close();
	}
}