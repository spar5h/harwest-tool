import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
 
/* spar5h */
 
public class cf4 implements Runnable{    
	
	static class pair {
		
		int i, w;
		
		public pair(int i, int w) {
			this.i = i; this.w = w;
		}
	}
	
	static class comp implements Comparator<pair> {
		
		public int compare(pair x, pair y) {
			
			if(x.w < y.w)
				return -1;
			
			if(x.w > y.w)
				return 1;
			
			return 0;
		}
	}
	
	static ArrayList<Integer>[] adj;
	static int[] color, freq;
	
	static int dsu(int c1, int c2) {
		
		freq[adj[c1].size()]--;
		freq[adj[c2].size()]--;
		freq[adj[c1].size() + adj[c2].size()]++;
		
		if(adj[c1].size() < adj[c2].size()) {
			int temp = c1; c1 = c2; c2 = temp;
		}
		
		for(int i = 0; i < adj[c2].size(); i++) {
			adj[c1].add(adj[c2].get(i)); color[adj[c2].get(i)] = c1;
		}
		
		return adj[c1].size();
	}
	
	public void run() {
		
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int n = s.nextInt();
		
		HashSet<Integer> hs = new HashSet<Integer>();
		
		ArrayList<pair> dList = new ArrayList<pair>();
		ArrayList<Integer> kList = new ArrayList<Integer>();
		
		for(int i = 1; i <= n; i++) {
			
			int x = s.nextInt();
			
			hs.add(x + 1);
			dList.add(new pair(i, x));
		}
		
		Collections.sort(dList, new comp());
		
		for(int i : hs)
			kList.add(i);
		
		Collections.sort(kList);
		
		adj = new ArrayList[n + 1];
		
		for(int i = 1; i <= n; i++) 
			adj[i] = new ArrayList<Integer>();
		
		freq = new int[n + 1];
		
		color = new int[n + 1]; 
		
		int j = 0;
		int currColor = 1;
		
		int resSize = 1;
		int resVal = (int)1e9 + 1;
		int prev = 1;
		
		for(int i = 0; i < kList.size(); i++) {
			
			int key = kList.get(i);
			
			int val = 0;
			
			while(j < dList.size() && dList.get(j).w < key) {
				
				int xi = dList.get(j).i;
				int xw = dList.get(j).w;
				
				color[xi] = currColor;
				adj[currColor].add(xi);
				freq[1]++;
				
				prev = 1;
				
				if(xi - 1 >= 1 && color[xi - 1] != 0) 
					prev = dsu(color[xi], color[xi - 1]);
				
				if(xi + 1 <= n && color[xi + 1] != 0)
					prev = dsu(color[xi], color[xi + 1]);
				
				currColor++;
				j++;
			}
			
			if(prev * freq[prev] == j) {
				
				if(resSize < freq[prev] || (resSize == freq[prev] && resVal > key)) {
					resSize = freq[prev]; resVal = key;
				}
			}
		}
		
		w.println(resVal);

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
		new Thread(null, new cf4(),"cf4",1<<26).start();
	}
}   