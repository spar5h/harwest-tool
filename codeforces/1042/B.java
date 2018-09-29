import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
import static java.lang.Math.*;

/* spar5h */

public class cf2{  
	
	public static void main(String[] args) {

		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);		
		
		int n = s.nextInt();
		
		long[] val = new long[7 + 1];
		Arrays.fill(val, -1);
		
		for(int i = 1; i <= n; i++) {
			
			long c = s.nextLong();
			
			char[] temp = s.next().toCharArray();
			
			Arrays.sort(temp);
			
			String str = "";
			
			for(int j = 0; j < temp.length; j++)
				str = str + temp[j];
			
			if(str.contains("A")) {
				
				if(val[1] == -1 || val[1] > c)
					val[1] = c;
			}
			
			if(str.contains("B")) {
				
				if(val[2] == -1 || val[2] > c)
					val[2] = c;
			}
			
			if(str.contains("C")) {
				
				if(val[3] == -1 || val[3] > c)
					val[3] = c;
			}
			
			if(str.contains("AB")) {
				
				if(val[4] == -1 || val[4] > c)
					val[4] = c;
			}
			
			if(str.contains("BC")) {
				
				if(val[5] == -1 || val[5] > c)
					val[5] = c;
			}
			
			if(str.contains("AC")) {
				
				if(val[6] == -1 || val[6] > c)
					val[6] = c;
			}
			
			if(str.contains("ABC")) {
				
				if(val[7] == -1 || val[7] > c)
					val[7] = c;
			}
		}
		
		long res = Long.MAX_VALUE;
		
		if(val[1] != -1 && val[2] != -1 && val[3] != -1)
			res = min(res, val[1] + val[2] + val[3]);
		
		if(val[1] != -1 && val[5] != -1)
			res = min(res, val[1] + val[5]);
		
		if(val[2] != -1 && val[6] != -1)
			res = min(res, val[2] + val[6]);
		
		if(val[3] != -1 && val[4] != -1)
			res = min(res, val[3] + val[4]);
		
		if(val[7] != -1)
			res = min(res, val[7]);
		
		if(res != Long.MAX_VALUE)
			w.println(res);
		else
			w.println(-1);
		
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
    
}