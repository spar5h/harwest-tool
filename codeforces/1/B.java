import java.util.Scanner;
import java.io.*;
import java.util.*;
import java.math.*;
import java.lang.*;
import static java.lang.Math.*;
 
public class Check2 implements Runnable
{
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
		new Thread(null, new Check2(),"CHECK2",1<<26).start();
	}
 
	public void run() 
	{

		InputReader s=new InputReader(System.in);
		PrintWriter w=new PrintWriter(System.out);
		
		int n = Integer.parseInt(s.next());
		
		for(int j = 0; j < n; j++) {
		    
		    String str = s.next();
		    String ans = "";
		    
		    if(system(str) == 1) {
		        
		        int x = 0;
		        
		        for(int i = 0; i < str.length(); i++) {
		            
		            if(str.charAt(i) < 'A') {
		                x = i;
		                break;
		            }  
		        }
		        
		        String s1 = str.substring(0, x), r = str.substring(x); 
		        int c = 0;
		        
		        for(int i = s1.length() - 1; i >= 0; i--) {
		            
		            c += (s1.charAt(i) - 'A'+ 1) * (int)Math.pow(26, s1.length() - 1 - i);
		        }
		        
		        ans = "R" + r + "C" + Integer.toString(c);
		        
		        w.println(ans);   
		    }
		    
		    else {
		        
		        int x = 0;
		        
		        for(int i = 0; i < str.length(); i++) {
		            
		            if(str.charAt(i) == 'C') {
		                x = i;
		                break;
		            }  
		        }
		        
		        String r = str.substring(1, x);
		        int numC = Integer.parseInt(str.substring(x + 1));
		        String c = "";
		        
		        while(numC > 0) {
		            
		            c = (char)((numC - 1) % 26 + 'A') + c;
		            numC = (numC - 1) / 26;
		            
		        }
		        
		        w.println(c + r);
		    }
		}
		    
		w.close();    
	}
	
	public static int system(String str) {
	    
	    int c = 0;
	    
	    for(int i = 0; i < str.length(); i++) {
	        
	        
	       char ch = str.charAt(i);
	       
	       if(c % 2 == 0) {
	           
	            if(ch >= 'A')
	                c++;
	       }   
	       
	       else {
	           
	           if(ch < 'A')
	                c++;
	       }
	       
	       if(c > 2)
	            return 2;
	           
	    }
	    
	    return 1;
	}
	
}