import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
 
/* spar5h */
 
public class lc7 implements Runnable{    
	
	static int n;
	static String lim;
	static StringBuffer res[], maxRes;
	static boolean resCheck[], maxResCheck;
	
	static void recur(int[] freq, int index) {
		
		if(index == n) {
			maxResCheck = true;
			return;
		}
		
		int[] temp1 = new int[10];
		int[] temp2 = new int[10];
		
		for(int i = 0; i < 10; i++) {
			temp1[i] = freq[i]; temp2[i] = freq[i];
		}
		
		int y = lim.charAt(index) - '0';
		
		if(freq[y] > 0) {
			maxRes.append((char)(y + '0'));
			temp1[y]--; 
			recur(temp1, index + 1);
		}
		
		boolean check = false;
		
		int c = 0;
		
		if(index == 0)
			c = 1;
		
		for(int i = 0; i < index; i++)
			res[index].append(lim.charAt(i));
		
		for(int j = y - 1; j >= c; j--) {
			
			if(temp2[j] > 0) {
				res[index].append((char)(j + '0'));
				temp2[j]--; check = true; break;
			}
		}
			
		if(!check)
			return;
		
		for(int i = index + 1; i < n; i++) {
			
			for(int j = 9; j >= 0; j--) {
				
				if(temp2[j] > 0) {
					res[index].append((char)(j + '0'));
					temp2[j]--; break;
				}
			}
		}
		
		resCheck[index] = true;
	}
	
	public void run() {
	    
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int t = 1;
		
		while(t-- > 0) {
			
			char[] c = s.next().toCharArray();
			
			int[] freq = new int[10];
			
			for(int i = 0; i < c.length; i++)
				freq[c[i] - '0']++;
			
			lim = s.next();
			
			if(lim.length() > c.length) {
				
				lim = ""; int x = c.length;
				
				while(x > 0) {
					lim += "9"; x--;
				}
			}
		
			n = c.length;
			
			resCheck = new boolean[n];
			res = new StringBuffer[n];
			maxRes = new StringBuffer("");
			
			for(int i = 0; i < n; i++)
				res[i] = new StringBuffer("");
			
			recur(freq, 0);
			
			if(maxResCheck == true) {
				w.println(maxRes); continue;
			}
			
			else {
				
				int x = -1;
				
				for(int j = n - 1; j >= 0; j--) {
					
					if(resCheck[j] == true) {
						x = j; break;
					}
				}
				
				if(x >= 0)
					w.println(res[x]);
				else
					w.println();
			}
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
		new Thread(null, new lc7(),"lc7",1<<26).start();
	}
} 