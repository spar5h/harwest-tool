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
	
	public void run() {
	    
	    InputReader s = new InputReader(System.in);
	    PrintWriter w = new PrintWriter(System.out);
	    
	    /*int n = s.nextInt();
	    
	    int a[] = new int[n];
	    
	    boolean b = false;
	    
	    for(int i = 0; i < n; i++) {
	    	
	    	a[i] = s.nextInt();
	    	
	    	if(a[i] != 0)
	    		b = true;
	    }
	    
	    if(b) {
	    	
	    	w.println("YES");
	    	
	    	int count = 0;
	    	int arrayStart = 0;
	    	for(int i = 0; i < n; i++) {
	    		
	    		if(a[i] == 0) {
	    			
	    			continue;
	    		}
	    	}
	    }
	    
	    else {
	    	
	    	w.println("NO");
	    }
	    */
	    
	    int a[][] = new int[4][4];
	    
	    for(int i = 0; i < 4; i++) {
	    	
	    	String str = s.next();
	    	
	    	for(int j = 0; j < 4; j++)
	    		a[i][j] = str.charAt(j);
	    }
	    
	    boolean b = false;
	    
	    for(int i = 0; i < 4; i++) {
	    	
	    	for(int j = 0; j < 4; j++) {
	    		
	    		if(a[i][j] == '.') {
	    			
	    			if(i >= 2 && a[i - 1][j] == 'x' && a[i - 2][j] == 'x')
	    				b = true;
	    			
	    			if(i >= 1 && i <= 2 && a[i - 1][j] == 'x' && a[i + 1][j] == 'x')
	    				b = true;
	    			
	    			if(i <= 1 && a[i + 1][j] == 'x' && a[i + 2][j] == 'x')
	    				b = true;
	    			
	    			if(j >= 2 && a[i][j - 1] == 'x' && a[i][j - 2] == 'x')
	    				b = true;
	    			
	    			if(j >= 1 && j <= 2 && a[i][j - 1] == 'x' && a[i][j + 1] == 'x')
	    				b = true;
	    			
	    			if(j <= 1 && a[i][j + 1] == 'x' && a[i][j + 2] == 'x')
	    				b = true;
	    			
	    			if(i >= 2 && j >= 2 && a[i - 1][j - 1] == 'x' && a[i - 2][j - 2] == 'x')
	    				b = true;
	    			
	    			if(i <= 1 && j <= 1 && a[i + 1][j + 1] == 'x' && a[i + 2][j + 2] == 'x')
	    				b = true;
	    			
	    			if(i >= 2 && j <= 1 && a[i - 1][j + 1] == 'x' && a[i - 2][j + 2] == 'x')
	    				b = true;
	    			
	    			if(i <= 1 && j >= 2 && a[i + 1][j - 1] == 'x' && a[i + 2][j - 2] == 'x')
	    				b = true;
	    			
	    			if(i <= 2 && i >= 1 && j >= 1 && j <= 2 && a[i - 1][j - 1] == 'x' && a[i + 1][j + 1] == 'x')
	    				b = true;
	    			
	    			if(i <= 2 && i >= 1 && j >= 1 && j <= 2 && a[i - 1][j + 1] == 'x' && a[i + 1][j - 1] == 'x')
	    				b = true;
	    			
	    		}
	    		
	    	}
	    }
	    
	    if(b)
	    	w.println("YES");
	    else
	    	w.println("NO");
	    
	    w.close();
	}
}