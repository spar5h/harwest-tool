import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
 
/* spar5h */ 

public class cf2 implements Runnable{    
	
 static boolean isPrime(long x) {
    	
    	if(x == 2)
    		return true;
    	
    	if(x == 3)
    		return true;
    	
    	if(x % 2 == 0)
    		return false;
    	
    	if(x % 3 == 0)
    		return false;
    	
    	long d = 4;
    	
    	for(int i = 5; i * i <= x; i+=d) {
    		
    		if(x % i == 0)
    			return false;
    		
    		d = 6 - d;
    	}
    	
    	return true;
    }
	
	public void run() {
	    
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int[] spf = new int[1000001]; spf[1] = 1;
		
		for(int i = 2; i <= 1000000; i++)
			spf[i] = i;
		
		for(int i = 2; i <= 1000000; i++) {
			
			if(spf[i] != i)
				continue;
			
			for(int j = i; (long)i * (long)j <= 1000000; j++)
				if(spf[i * j] == i * j)
					spf[i * j] = i;
		}
		
		int x2 = s.nextInt();
		
		ArrayList<Integer> list = new ArrayList<Integer>();
		
		int temp = x2;
		
		while(temp > 1) {
			
			list.add(spf[temp]); 
			
			int temp2 = spf[temp];
			
			while(temp > 1 && temp % temp2 == 0)
				temp /= temp2;
		}
		
		int min = x2;
		
		for(int i = 0; i < list.size(); i++) {
			
			for(int x1 = x2; x1 >= 3; x1--) {
				
				if((int)Math.ceil((double)x1 / list.get(i)) != x2 / list.get(i))
						break;
				
				//w.println("x1: " + x1 + " " + list.get(i));
				
				if(isPrime(x1))
					continue;
				
				ArrayList<Integer> list2 = new ArrayList<Integer>();
				
				temp = x1;
				
				while(temp > 1) {
					
					list2.add(spf[temp]); 
					
					int temp2 = spf[temp];
					
					while(temp > 1 && temp % temp2 == 0)
						temp /= temp2;
				}
				
				for(int j = 0; j < list2.size(); j++) {
					//w.println("x0: " + ((x1 / list2.get(j) - 1) * list2.get(j) + 1) + " " + list2.get(j));
					min = Math.min(min, (x1 / list2.get(j) - 1) * list2.get(j) + 1);
				}	
			}
		}
		
		w.println(min);
		
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
		new Thread(null, new cf2(),"cf2",1<<26).start();
	}
}   

