import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;

/* spar5h */

public class codeforces implements Runnable
{    

	
	public void run() {
		
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		String str = s.next();
		int n = str.length();
		
		int[] a = new int[n];
		for(int i = 0; i < n; i++)
			if(str.charAt(i) == '?')
				a[i] = 26;
			else
				a[i] = str.charAt(i) - 'A';
			
			
		int[] count = new int[27];
		int c = 0;
		int l = 0;
		ArrayList<Integer> list = new ArrayList<Integer>();
		int ansStart = -1;
		
		if(n >= 26) {
			
			for(int i = 0; i < 26; i++) {
				
				if(count[a[i]] == 0 && a[i] < 26)
					c++;
				
				if(a[i] == 26)
					list.add(i);
				
				count[a[i]]++;
			}
				
			if(c == 26 - count[26])	{
				
				ansStart = 0;
				int x = 0;
				int y = 0;
					
				while(x < list.size() && y < 26) {
						
					if(count[y] == 0) {
						
						a[list.get(x)] = y;  
						x++;
					}
						
					y++;
				}
				
			}
			
			if(ansStart == -1) {
				
				while(l + 26 < n) {
					
					count[a[l]]--;
					
					if(count[a[l]] == 0 && a[l] < 26)
						c--;
					
					if(a[l] == 26)
						list.remove(0);
					
					if(count[a[l + 26]] == 0 && a[l + 26] < 26)
						c++;
					
					if(a[l + 26] == 26)
						list.add(l + 26);
					
					count[a[l + 26]]++;
					
					if(c == 26 - count[26])	{
						
						ansStart = l + 1;
						int x = 0;
						int y = 0;
							
						while(x < list.size() && y < 26) {
								
							if(count[y] == 0) {
								
								a[list.get(x)] = y;  
								x++;
							}
								
							y++;
						}
						
					}
					
					l++;
					
					if(ansStart != -1)
						break;
				}
			}
			
		}
		
		if(ansStart == -1)
			w.println(-1);
		else {
			
			for(int i = 0; i < ansStart; i++) {
				if(a[i] == 26)
					w.print('A');
				else
					w.print((char)(a[i] + 'A'));
			}	
			
			for(int i = ansStart; i < ansStart + 26; i++)
				w.print((char)(a[i] + 'A'));
			
			for(int i = ansStart + 26; i < n; i++) {
				if(a[i] == 26)
					w.print('A');
				else
					w.print((char)(a[i] + 'A'));
			}	
			
			w.println();
		}
		
		w.close();
	}
	
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
	   
}