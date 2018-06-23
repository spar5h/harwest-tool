import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
 
/* spar5h */

public class cf5 implements Runnable{    
	
	final static long mod = (long)1e9 + 7; 
	
	public void run() {
	    
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int[] freq = new int[10];
		
		String str = s.next();
		
		for(int i = 0; i < str.length(); i++)
			freq[str.charAt(i) - '0']++;
		
		long[] fact = new long[19 + 1]; fact[0] = 1;
		
		for(int i = 1; i <= 19; i++)
			fact[i] = fact[i - 1] * i;
		
		long res = 0;
		
		long LIM = (long)Math.pow(2, str.length());
		
		int[] check = new int[10];
		int[] val = new int[10];
		
		outer:
		for(int i = 1; i < LIM; i++) {
			
			StringBuilder sb = new StringBuilder(Integer.toBinaryString(i));
			sb.reverse();
			
			while(sb.length() != str.length())
				sb.append(0);
			
			sb.reverse();
			
			Arrays.fill(check, 0);
			Arrays.fill(val, 0);
			
			int pos = 0;
			
			for(int j = 0; j < 10; j++) {
				
				for(int k = 0; k < freq[j]; k++) {
					
					check[j] += sb.charAt(pos + k) - '0'; 
					
					if(sb.charAt(pos + k) == '1') 
						val[j] = k + 1;
				}
				
				pos += freq[j];
			}
			
			for(int j = 0; j < 10; j++)
				if(check[j] != 1 && freq[j] != 0)
					continue outer;
			
			int total = 0;
			
			for(int j = 0; j < 10; j++)
				total += val[j];
			
			for(int j = 1; j < 10; j++) {
				
				if(freq[j] == 0)
					continue;
				
				long add = fact[total - 1];
				
				add /= fact[val[j] - 1];
				
				for(int k = 0; k < 10; k++)
					if(j != k)
						add /= fact[val[k]];
				
				res += add;
			}
			
		}
		
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
		new Thread(null, new cf5(),"cf5",1<<26).start();
	}
}   