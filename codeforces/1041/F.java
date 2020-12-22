import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
import static java.lang.Math.*;

/* spar5h */
 
public class cf6 implements Runnable{    
	
	public void run() {
	    
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int[] pow2 = new int[31]; pow2[0] = 1;
		
		for(int i = 1; i <= 30; i++)
			pow2[i] = pow2[i - 1] * 2;
		
		int n = s.nextInt(); s.nextInt();
		
		int[] a = new int[n];
		
		for(int i = 0; i < n; i++)
			a[i] = s.nextInt();
		
		int m = s.nextInt(); s.nextInt();
		
		int[] b = new int[m];
		
		for(int i = 0; i < m; i++)
			b[i] = s.nextInt();
		
		int res = 0;
		
		for(int k = 1; k <= 30; k++) {
			
			HashMap<Integer, Integer> hm = new HashMap<Integer, Integer>();
			
			for(int i = 0; i < n; i++) {
				
				int val = a[i] % pow2[k];
				
				if(hm.get(val) != null)
					hm.put(val, hm.get(val) + 1);
				else
					hm.put(val, 1);
			}
			
			for(int i = 0; i < m; i++) {
				
				int val = (b[i] - pow2[k - 1] + pow2[k]) % pow2[k];
				
				if(hm.get(val) != null)
					hm.put(val, hm.get(val) + 1);
				else
					hm.put(val, 1);
			}
			
			for(int i : hm.values()) 
				res = max(i, res);
			
		}
		
		HashMap<Integer, Integer> hm = new HashMap<Integer, Integer>();
		
		for(int i = 0; i < n; i++) {
			
			if(hm.get(a[i]) != null)
				hm.put(a[i], hm.get(a[i]) + 1);
			else
				hm.put(a[i], 1);
		}
			
		for(int i = 0; i < m; i++) {
			
			if(hm.get(b[i]) != null)
				hm.put(b[i], hm.get(b[i]) + 1);
			else
				hm.put(b[i], 1);
		}
		
		for(int i : hm.values())
			res = max(i, res);
		
		w.println(res);
		
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
		new Thread(null, new cf6(),"cf6",1<<26).start();
	}
}   