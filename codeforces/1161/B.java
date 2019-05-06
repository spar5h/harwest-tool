import java.util.*;
import java.io.*;
import java.math.BigInteger;
import static java.lang.Math.*;
 
/* spar5h */
 
public class cf2 implements Runnable {   
	
	public void run() {
		
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int n = s.nextInt(), m = s.nextInt();
		
		ArrayList<Integer> fact = new ArrayList<Integer>();
		
		fact.add(1);
		
		for(int i = 2; (long)i * i <= n; i++) {
			
			if(n % i != 0)
				continue;
			
			fact.add(i);
			
			if(n / i != i)
				fact.add(n / i);
		}
		
		ArrayList<Integer>[] adj = new ArrayList[n / 2 + 1];
		
		for(int i = 0; i <= n / 2; i++) 
			adj[i] = new ArrayList<Integer>();
		
		for(int i = 0; i < m; i++) {
			
			int u = s.nextInt() - 1, v = s.nextInt() - 1;			
			
			if((v - u + n) % n <= (u - v + n) % n) 
				adj[(v - u + n) % n].add(u);
			
			if((u - v + n) % n <= (v - u + n) % n) 
				adj[(u - v + n) % n].add(v);
		}
		
		boolean res = false;
		
		int[] count = new int[n];
		int[] map = new int[n];
		Arrays.fill(map, -1);
		
		for(int x : fact) {
			
			boolean check = true;
			
			for(int len = 1; len <= n / 2; len++) {
				
				ArrayList<Integer> set = new ArrayList<Integer>();
				
				for(int i : adj[len]) {
					
					if(map[i % x] == -1) {
						map[i % x] = set.size();
						set.add(i % x);
					}
					
					count[map[i % x]]++;
				}
		
				for(int i : set) {
					
					if(count[map[i]] != n / x)
						check = false;
					
					count[map[i]] = 0;
					map[i] = -1;
				}
			}
			
			if(check) {
				res = true;
				break;
			}	
		}
		
		if(res)
			w.println("Yes");
		else
			w.println("No");
		
		w.close();
	}
	
	static class InputReader {
		
		private InputStream stream;
		private byte[] buf = new byte[1024];
		private int curChar;
		private int numChars;
		private SpaceCharFilter filter;
		
		public InputReader(InputStream stream) {
			this.stream = stream;
		}
		
		public int read() {
			
			if (numChars==-1) 
				throw new InputMismatchException();
			
			if (curChar >= numChars) {
				
				curChar = 0;
				
				try {
					numChars = stream.read(buf);
				}
				catch (IOException e) {
					throw new InputMismatchException();
				}
				
				if(numChars <= 0)				
					return -1;
			}
			
			return buf[curChar++];
		}
		 
		public String nextLine() {
			
			BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
			String str = "";
	        
			try {
	            str = br.readLine();
	        }
	        catch (IOException e) {
	            e.printStackTrace();
	        }
			
	        return str;
		}
		
		public int nextInt() {
		
			int c = read();
			
			while(isSpaceChar(c)) 
				c = read();
			
			int sgn = 1;
			
			if (c == '-') {
				sgn = -1;
				c = read();
			}
			
			int res = 0;
			
			do {
				if(c<'0'||c>'9') 
					throw new InputMismatchException();
				
				res *= 10;
				res += c - '0';
				c = read();
			}
			while (!isSpaceChar(c)); 
			
			return res * sgn;
		}
		
		public long nextLong() {
			
			int c = read();
			
			while (isSpaceChar(c))
				c = read();
			
			int sgn = 1;
			
			if (c == '-') {
				sgn = -1;
				c = read();
			}
			
			long res = 0;
			
			do {
				if (c < '0' || c > '9')
					throw new InputMismatchException();
				
				res *= 10;
				res += c - '0';
				c = read();
			}
			
			while (!isSpaceChar(c));
				return res * sgn;
		}
		
		public double nextDouble() {
			
			int c = read();
			
			while (isSpaceChar(c))
				c = read();
			
			int sgn = 1;
			
			if (c == '-') {
				sgn = -1;
				c = read();
			}
			
			double res = 0;
			
			while (!isSpaceChar(c) && c != '.') {
				
				if (c == 'e' || c == 'E')
					return res * Math.pow(10, nextInt());
				
				if (c < '0' || c > '9')
					throw new InputMismatchException();
				
				res *= 10;
				res += c - '0';
				c = read();
			}
			
			if (c == '.') {
				
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
		
		public String readString() {
			
			int c = read();
			
			while (isSpaceChar(c))
				c = read();
			
			StringBuilder res = new StringBuilder();
			
			do {
				res.appendCodePoint(c);
				c = read();
			} 
			while (!isSpaceChar(c));
			
			return res.toString();
		}
	 
		public boolean isSpaceChar(int c) {
			
			if (filter != null)
				return filter.isSpaceChar(c);
			
			return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
		}
	 
		public String next() {
			return readString();
		}
		
		public interface SpaceCharFilter {
			public boolean isSpaceChar(int ch);
		}
	
	}
 
	public static void main(String args[]) throws Exception {
		new Thread(null, new cf2(),"cf2",1<<26).start();
	}
} 