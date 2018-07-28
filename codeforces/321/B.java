import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;

/* spar5h */

public class cf1 implements Runnable{    
	
	public void run() {

		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
	
		int n = s.nextInt(), m = s.nextInt();
		
		ArrayList<Integer> atk = new ArrayList<Integer>();
		ArrayList<Integer> def = new ArrayList<Integer>();
		
		for(int i = 0; i < n; i++) {
			
			if(s.next().equals("ATK"))
				atk.add(s.nextInt());
			else
				def.add(s.nextInt());
		}
		
		long res = 0;
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		for(int i = 0; i < m; i++)
			list.add(s.nextInt());
		
		Collections.sort(atk);
		Collections.sort(def);
		Collections.sort(list);
		
		outer:
		for(int k = 1; k <= Math.min(atk.size(), list.size()); k++) {
			
			long can = 0;
			
			for(int i = 0; i < k; i++) {
				
				if(list.get(m - i - 1) < atk.get(k - i - 1))
					continue outer;
				
				can += list.get(m - i - 1) - atk.get(k - i - 1);
			}
			
			res = Math.max(res, can);
		}
		
		boolean check = true;
		
		outer2: 
		for(int i = 0; i < def.size(); i++) {
			
			for(int j = 0; j < list.size(); j++) {
				
				if(list.get(j) > def.get(i)) {
					list.remove(j); continue outer2;
				}
			}
			
			check = false;
		}
		
		if(list.size() < atk.size())
			check = false;
		
		long can = 0;
		
		if(check) {
			
			for(int i = 0; i < atk.size(); i++) {
				
				if(list.get(list.size() - 1) < atk.get(atk.size() - i - 1)) {
					check = false; break;
				}
				
				can += list.get(list.size() - 1) - atk.get(atk.size() - i - 1);
				list.remove(list.size() - 1);
			}
 		}
		
		if(check) {
			
			for(int i = 0; i < list.size(); i++)
				can += list.get(i);
			
			res = Math.max(res,  can);
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
		new Thread(null, new cf1(),"cf1",1<<26).start();
	}
}   