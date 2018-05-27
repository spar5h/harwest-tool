import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
 
/* spar5h */
 
public class cf3 implements Runnable{    
	
	static ArrayList<Integer>[] adj;
	static ArrayList<String> list;
	
	static int DFS(int x, int p) {
		
		if(adj[x].size() == 1)
			return x;
		
		int ans = p;
		
		for(int i = 0; i < adj[x].size(); i++) {
			
			int y = adj[x].get(i);
			
			if(y != p) {
				ans = DFS(y, x);
			}
		}
		
		return ans;
	}
	
	public void run() {
	    
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int t = 1;
		
		while(t-- > 0) {
			
			int n = s.nextInt();
			
			adj = new ArrayList[n + 1];
			
			for(int i = 1; i <= n; i++)
				adj[i] = new ArrayList<Integer>();
			
			for(int i = 0; i < n - 1; i++) {
				
				int u = s.nextInt(), v = s.nextInt();
				adj[u].add(v); adj[v].add(u);
			}
			
			int[] freq = new int[n + 1];
			
			for(int i = 1; i <= n; i++)
				freq[adj[i].size()]++;
			
			int x = 0;
			
			for(int i = 3; i <= n; i++)
				x += freq[i];
			
			if(x > 1) {
				w.println("No"); continue;
			}
			
			int index = 1;
			
			for(int i = 1; i <= n; i++) {
				if(adj[i].size() > 2)
					index = i;
			}
			
			list = new ArrayList<String>();
			
			for(int i = 0; i < adj[index].size(); i++) {
				
				int y = adj[index].get(i);
				int z = DFS(y, index);
				list.add(index + " " + z);
			}
			
			w.println("Yes");
			w.println(list.size());
			
			for(int i = 0; i < list.size(); i++)
				w.println(list.get(i));
			
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
		new Thread(null, new cf3(),"cf3",1<<26).start();
	}
}   

