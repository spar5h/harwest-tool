import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
 
/* spar5h */
 
public class cf3 implements Runnable{    
	
	public void run() {
	    
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int t = 1;
		
		while(t-- > 0) {
			
			double r = s.nextDouble();
			double x1 = s.nextDouble();
			double y1 = s.nextDouble();
			double x2 = s.nextDouble();
			double y2 = s.nextDouble();
			
			double distance = (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
			
			if(distance >= r * r) {
				w.println(x1 + " " + y1 + " " + r); continue;
			}
			
			distance = Math.sqrt(distance);
			double resR = r + distance;
			
			double ax, ay, bx, by;
			
			double resX, resY;
			
			if(x2 - x1 == 0 && y2 - y1 == 0) {
				
				resX = x1 + r;
				resY = y1;
			}
			
			else if(y2 - y1 == 0) {
				
				ax = x2 + resR;
		        ay = y2;
		 
		        bx = x2 - resR;
		        by = y2;
		        
		        double d1 = (x1 - ax) * (x1 - ax) + (y1 - ay) * (y1 - ay);
		        double d2 = (x1 - bx) * (x1 - bx) + (y1 - by) * (y1 - by);
		        
		        if(d1 < d2) {
		        	
		        	resX = ax;
		        	resY = ay;
		        }
		        
		        else {
		        	
		        	resX = bx;
		        	resY = by;
		        }
			}
			
			else if(x2 - x1 == 0) {
				
				ax = x2;
		        ay = y2 + resR;
		 
		        bx = x2;
		        by = y2 - resR;
		        
		        double d1 = (x1 - ax) * (x1 - ax) + (y1 - ay) * (y1 - ay);
		        double d2 = (x1 - bx) * (x1 - bx) + (y1 - by) * (y1 - by);
		        
		        if(d1 < d2) {
		        	
		        	resX = ax;
		        	resY = ay;
		        }
		        
		        else {
		        	
		        	resX = bx;
		        	resY = by;
		        }
		        
			}
			
			else{
				
				double m = (y2 - y1) / (x2 - x1);
				
				double dx = resR / Math.sqrt(1 + (m * m));
		        double dy = m * dx;
		        
		        ax = x2 + dx;
		        ay = y2 + dy;
		        bx = x2 - dx;
		        by = y2 - dy;
		        
		        double d1 = (x1 - ax) * (x1 - ax) + (y1 - ay) * (y1 - ay);
		        double d2 = (x1 - bx) * (x1 - bx) + (y1 - by) * (y1 - by);
		        
		        if(d1 < d2) {
		        	
		        	resX = ax;
		        	resY = ay;
		        }
		        
		        else {
		        	
		        	resX = bx;
		        	resY = by;
		        }
			}
			
		
			w.println((resX + x2) / 2 + " " + (resY + y2) / 2 + " " + resR / 2);
		}
		
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

