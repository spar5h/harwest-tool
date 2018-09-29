import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
import static java.lang.Math.*;

/* spar5h */

public class cf3{  
	
	public static void main(String[] args) {

		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);		
		
		int n = s.nextInt();
		
		int neg = 0, zero = 0, pos = 0;
		
		int[] a = new int[n + 1];
		
		for(int i = 1; i <= n; i++) {
			
			a[i] = s.nextInt(); 
			
			if(a[i] < 0)
				neg++;
			else if(a[i] > 0)
				pos++;
			else
				zero++;
		}
		
		if(neg <= 1 && pos == 0) {
			
			for(int i = 1; i <= n - 1; i++)
				w.println(1 + " " + i + " " + (i + 1));
		}
		
		else {
			
			int[] rem = new int[n + 1];
			
			if(neg % 2 == 1) {
			
				int j = -1;
				
				int min = Integer.MIN_VALUE;
				
				for(int i = 1; i <= n; i++) {
					if(a[i] < 0 && (min == Integer.MIN_VALUE || abs(min) > abs(a[i]))) {
							j = i; min = a[i];
					}	
				}
				
				rem[j] = 1;
			}
			
			for(int i = 1; i <= n; i++)
				if(a[i] == 0)
					rem[i] = 1;
			
			ArrayList<Integer> list = new ArrayList<Integer>();
			
			for(int i = 1; i <= n; i++)
				if(rem[i] == 1)
					list.add(i);
			
			if(list.size() > 0) {
				
				for(int i = 0; i < list.size() - 1; i++)
					w.println(1 + " " + list.get(i) + " " + list.get(i + 1));
				
				w.println(2 + " " + list.get(list.size() - 1));
			}

			list = new ArrayList<Integer>();
			
			for(int i = 1; i <= n; i++)
				if(rem[i] == 0)
					list.add(i);
			
			for(int i = 0; i < list.size() - 1; i++)
				w.println(1 + " " + list.get(i) + " " + list.get(i + 1));
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
    
}