import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
 
/* spar5h */
 
//I hate geometry 

public class cf3 implements Runnable{    
	
	final static long mod = (long)1e9 + 7; 
	final static double err = 1e-9;
	
	public void run() {
	    
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		double[] x1 = new double[4 + 1];
		double[] y1 = new double[4 + 1];
		
		for(int i = 0; i < 4; i++) {
			x1[i] = s.nextDouble(); y1[i] = s.nextDouble();
			x1[4] += x1[i]; y1[4] += y1[i];
		}
		
		x1[4] /= 4; y1[4] /= 4;
		
		double[] x2 = new double[4 + 1];
		double[] y2 = new double[4 + 1];
		
		for(int i = 0; i < 4; i++) {
			x2[i] = s.nextDouble(); y2[i] = s.nextDouble();
			x2[4] += x2[i]; y2[4] += y2[i];
		}
		
		x2[4] /= 4; y2[4] /= 4;
		
		boolean res = false;
		
		for(int i = 0; i <= 4; i++) {
			
			boolean b = true;
			
			if(Math.min(Math.min(x1[0], x1[1]), x1[2]) > x2[i])
				b = false;
			
			if(Math.max(Math.max(x1[0], x1[1]), x1[2]) < x2[i])
				b = false;
			
			if(Math.min(Math.min(y1[0], y1[1]), y1[2]) > y2[i])
				b = false;
			
			if(Math.max(Math.max(y1[0], y1[1]), y1[2]) < y2[i])
				b = false;
			
			if(b)
				res = true;
		}
		
		//rotate by 45 scaled to 1.41:1
		
		for(int i = 0; i <= 4; i++) {
			
			double x = x1[i] - y1[i];
			double y = x1[i] + y1[i];
		
			x1[i] = x; y1[i] = y;
			
			x = x2[i] - y2[i];
			y = x2[i] + y2[i];
		
			x2[i] = x; y2[i] = y;
		}
		
		for(int i = 0; i <= 4; i++) {
			
			boolean b = true;
			
			if(Math.min(Math.min(x2[0], x2[1]), x2[2]) > x1[i])
				b = false;
			
			if(Math.max(Math.max(x2[0], x2[1]), x2[2]) < x1[i])
				b = false;
			
			if(Math.min(Math.min(y2[0], y2[1]), y2[2]) > y1[i])
				b = false;
			
			if(Math.max(Math.max(y2[0], y2[1]), y2[2]) < y1[i])
				b = false;
			
			if(b)
				res = true;
		}
		
		if(res)
			w.println("YES");
		else
			w.println("NO");
		
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

