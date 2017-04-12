import java.util.*;
import java.lang.*;
import java.io.*;

/* Name of the class has to be "Main" only if the class is public. */
 public class codeforces implements Runnable {
    
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
	
	static ArrayList<Integer>[] adj;
	
	public void run() {
	    
	    InputReader s = new InputReader(System.in);
	    PrintWriter w = new PrintWriter(System.out);
	    
	    int n = s.nextInt();
	    
	    int weight[] = new int[n + 1];
	    
	    for(int i = 1; i <= n; i++)
	    	weight[i] = s.nextInt();
	    
	    adj = new ArrayList[n + 1];
	    
	    for(int i = 1; i <= n; i++)
	    	adj[i] = new ArrayList<Integer>();
	    
	    for(int i = 0; i < n - 1; i++) {
	    	
	    	int n1 = s.nextInt();
	    	int n2 = s.nextInt();
	    	
	    	adj[n1].add(n2);
	    	adj[n2].add(n1);
	    }
	    
	    int max = -Integer.MAX_VALUE;
	    int maxCount = 0, maxCount2 = 0;
	    
	    for(int i = 1; i <= n; i++)
	    	if(max < weight[i])
	    		max = weight[i];
	    
	    for(int i = 1; i <= n; i++) {
	    	
	    	if(weight[i] == max)
	    		maxCount++;
	    		
	    	if(weight[i] == max - 1)
	    	    maxCount2++;
	    }
	    
	    
	    int ans = max;
	    
	    for(int i = 1; i <= n; i++) {
	    	
	    	int count = 0;
	    	int count2 = 0;
	    	int val = max;
	    	
	    	
	    	if(weight[i] == max) {
	    		count++;
	    		val = max - 2;
	    	}	
	    	
	    	for(int j = 0; j < adj[i].size(); j++) {
	    		
	    		if(weight[adj[i].get(j)] == max) {
	    			
	    			val = max - 1;
	    			count++;
	    		}
	    		
	    		else if(weight[adj[i].get(j)] == max -1) {
	    		    
	    		    count2++;
	    		}
	    	}
	    	
	    	if(count != maxCount)
	    		val = max;
	    	else if(count2 != maxCount2)
	    	    val = max - 1;
	    	
	    	if(ans > val)
	    		ans = val;
	    }
	    
	    ans += 2;
	    
	    w.println(ans);
	    
	    w.close();
	}
}