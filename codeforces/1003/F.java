import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
 
/* spar5h */
 
public class cf6 implements Runnable{    
	
	public void run() {
	    
		Scanner s = new Scanner(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int n = Integer.parseInt(s.nextLine());
		
		StringTokenizer st = new StringTokenizer(s.nextLine());
		
		HashMap<String, Integer> hm = new HashMap<String, Integer>();
		
		int[] txt = new int[n]; 
		int[] size = new int[n];
		
		int val = 0, i = 0;
		
		while(st.hasMoreTokens()) {
			
			String temp = st.nextToken();
			
			size[i] = temp.length();
			
			if(hm.get(temp) == null) {
				hm.put(temp, val); txt[i] = val; val++;
			}
			else
				txt[i] = hm.get(temp);
			
			i++;
		}
		
		int res = 0;
		
		for(int s_i = 0; s_i < n; s_i++) {
			
			for(int s_j = s_i; s_j < n; s_j++) {
				
				int m = s_j - s_i + 1;
				
				int[] pat = new int[m];
				
				int sz = 0;
				
				for(i = 0; i < m; i++) {
					pat[i] = txt[s_i + i]; sz += size[s_i + i];
				}
				
				int[] lps = new int[m]; 
				int len = 0; i = 1;
				
				while(i < m) {
					
					if(pat[i] == pat[len]) {
						len++; lps[i] = len; i++;
					}
					
					else if(len != 0)
						len = lps[len - 1];
					
					else
						i++;
				}
				
				i = 0; int j = 0, count = 0;
				
				while(i < n) {
					
					if(txt[i] == pat[j]) {
						i++; j++;
					}
					
					else if(j != 0)
						j = lps[j - 1];
					
					else
						i++;
					
					if(j == m)  {
						j = 0; count++;
					}
					
				}
				
				if(count <= 1)
					continue;
			
				res = Math.max(count * (sz - m) + count * (m - 1), res);
			}

		}
	
		int sum = n - 1;
		
		for(i = 0; i < n; i++)
			sum += size[i];
		
		w.println(sum - res);
		
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