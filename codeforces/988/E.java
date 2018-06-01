import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
 
public class cf5 implements Runnable{    
	
	public void run() {
	    
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		String str = s.next();
		int n = str.length();
		
		int res = 10000;
		
		//00
		char[] ch = str.toCharArray();
		int d = -1;
		int c = 0;
		
		while(true) {
			
			for(int i = 0; i < n; i++)
				if(ch[i] == '0')
					d = i;
			
			if(d == -1)
				break;
			
			for(int i = d; i < n - 1; i++) {
				char temp = ch[i]; ch[i] = ch[i + 1]; ch[i + 1] = temp; c++; 
			}
			
			d = -1;
			
			for(int i = 0; i < n - 1; i++)
				if(ch[i] == '0')
					d = i;
			
			if(d == -1)
				break;
			
			for(int i = d; i < n - 2; i++) {
				char temp = ch[i]; ch[i] = ch[i + 1]; ch[i + 1] = temp; c++; 
			}
			
			res = Math.min(res, c);
			
			//System.out.println(ch);
			
			break;
		}
		
		//25
		ch = str.toCharArray();
		d = -1;
		c = 0;
		
		while(true) {
			
			for(int i = 0; i < n; i++)
				if(ch[i] == '5')
					d = i;
			
			if(d == -1)
				break;
			
			for(int i = d; i < n - 1; i++) {
				char temp = ch[i]; ch[i] = ch[i + 1]; ch[i + 1] = temp; c++; 
			}
			
			if(ch[0] == '0') {
				
				int d2 = -1;
				
				boolean flag = false;
				
				for(int i = n - 2; i >= 0; i--) {
					
					if(ch[i] != '0' || (flag && ch[i] != '2'))
						d2 = i;
					
					if(ch[i] == '2')
						flag = true;
				}
				
				if(d2 == -1)
					break;
				
				for(int i = d2; i >= 1; i--) {
					char temp = ch[i]; ch[i] = ch[i - 1]; ch[i - 1] = temp; c++;
				}
				
			}
			
			d = -1;
			
			for(int i = 0; i < n - 1; i++)
				if(ch[i] == '2')
					d = i;
			
			if(d == -1)
				break;
			
			for(int i = d; i < n - 2; i++) {
				char temp = ch[i]; ch[i] = ch[i + 1]; ch[i + 1] = temp; c++; 
			}
			
			if(ch[0] == '0') {
				
				int d2 = -1;
				
				for(int i = n - 3; i >= 0; i--)
					if(ch[i] != '0')
						d2 = i;
				
				if(d2 == -1)
					break;
				
				for(int i = d2; i >= 1; i--) {
					char temp = ch[i]; ch[i] = ch[i - 1]; ch[i - 1] = temp; c++;
				}
				
			}
			
			res = Math.min(res, c);
			
			//System.out.println(ch);
			
			break;
		}
		
		//50
		ch = str.toCharArray();
		d = -1;
		c = 0;
		
		while(true) {
			
			for(int i = 0; i < n; i++)
				if(ch[i] == '0')
					d = i;
			
			if(d == -1)
				break;
			
			for(int i = d; i < n - 1; i++) {
				char temp = ch[i]; ch[i] = ch[i + 1]; ch[i + 1] = temp; c++; 
			}
			
			d = -1;
			
			for(int i = 0; i < n - 1; i++)
				if(ch[i] == '5')
					d = i;
			
			if(d == -1)
				break;
			
			for(int i = d; i < n - 2; i++) {
				char temp = ch[i]; ch[i] = ch[i + 1]; ch[i + 1] = temp; c++; 
			}
			
			if(ch[0] == '0') {
				
				int d2 = -1;
				
				for(int i = n - 3; i >= 0; i--)
					if(ch[i] != '0')
						d2 = i;
				
				if(d2 == -1)
					break;
				
				for(int i = d2; i >= 1; i--) {
					char temp = ch[i]; ch[i] = ch[i - 1]; ch[i - 1] = temp; c++;
				}
				
			}
			
			res = Math.min(res, c);
			
			//System.out.println(ch);
			
			break;
		}

		//75
		ch = str.toCharArray();
		d = -1;
		c = 0;
		
		while(true) {
			
			for(int i = 0; i < n; i++)
				if(ch[i] == '5')
					d = i;
			
			if(d == -1)
				break;
			
			for(int i = d; i < n - 1; i++) {
				char temp = ch[i]; ch[i] = ch[i + 1]; ch[i + 1] = temp; c++; 
			}
			
			if(ch[0] == '0') {
				
				int d2 = -1;
				
				boolean flag = false;
				
				for(int i = n - 2; i >= 0; i--) {
					
					if(ch[i] != '0' || (flag && ch[i] != '7'))
						d2 = i;
					
					if(ch[i] == '7')
						flag = true;
				}
				
				if(d2 == -1)
					break;
				
				for(int i = d2; i >= 1; i--) {
					char temp = ch[i]; ch[i] = ch[i - 1]; ch[i - 1] = temp; c++;
				}
				
			}
			
			d = -1;
			
			for(int i = 0; i < n - 1; i++)
				if(ch[i] == '7')
					d = i;
			
			if(d == -1)
				break;
			
			for(int i = d; i < n - 2; i++) {
				char temp = ch[i]; ch[i] = ch[i + 1]; ch[i + 1] = temp; c++; 
			}
			
			if(ch[0] == '0') {
				
				int d2 = -1;
				
				for(int i = n - 3; i >= 0; i--)
					if(ch[i] != '0')
						d2 = i;
				
				if(d2 == -1)
					break;
				
				for(int i = d2; i >= 1; i--) {
					char temp = ch[i]; ch[i] = ch[i - 1]; ch[i - 1] = temp; c++;
				}
				
			}
			
			res = Math.min(res, c);
			
			//System.out.println(ch);
			
			break;
		}
		
		if(res == 10000)
			w.println(-1);
		else
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