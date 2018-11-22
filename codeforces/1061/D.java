import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
import static java.lang.Math.*;

/* spar5h */

public class cf4 implements Runnable{    
	
	static class pair {
		
		int i; long w;
		
		pair(int i, long w) {
			this.i = i; this.w = w;
		}
	}
	
	static class comp implements Comparator<pair> {
		
		public int compare(pair x, pair y) {
			
			if(x.w > y.w)
				return -1;
			
			if(x.w < y.w)
				return 1;
			
			return 0;
		}
	}
	
	static void addMap(TreeMap<Integer, ArrayList<Integer>> hm, int x, int i) {
		
		if(hm.get(x) == null)
			hm.put(x, new ArrayList<Integer>());
		
		hm.get(x).add(i);
	}
	
	static void subMap(TreeMap<Integer, ArrayList<Integer>> hm, int x, int i) {
		
		if(hm.get(x) == null)
			hm.put(x, new ArrayList<Integer>());
		
		hm.get(x).add(-i);
	}
	
	public void run() {
		
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int n = s.nextInt();
		long x = s.nextLong(), y = s.nextLong();
		
		PriorityQueue<pair> pq = new PriorityQueue<pair>(new comp());
		
		TreeMap<Integer, ArrayList<Integer>> hm = new TreeMap<Integer, ArrayList<Integer>>();
		
		int[] l = new int[n + 1];
		
		for(int i = 1; i <= n; i++) {
			
			l[i] = s.nextInt();
			int r = s.nextInt();
			
			addMap(hm, l[i], i);
			subMap(hm, r + 1, i);
		}
		
		long mod = (long)1e9 + 7;
		
		long totalCost = 0;
		
		int[] vis = new int[n + 1];
		int[] val = new int[n + 1];
		long[] cost = new long[n + 1];
		
		for(Map.Entry<Integer, ArrayList<Integer>> e : hm.entrySet()) {
			
			int pos = e.getKey();
			ArrayList<Integer> temp = e.getValue();
			
			for(int i = 0; i < temp.size(); i++) {
				
				if(temp.get(i) < 0) {

					pq.add(new pair(-temp.get(i), pos - 1));
					vis[val[-temp.get(i)]] = 0;
					
					cost[-temp.get(i)] = (cost[-temp.get(i)] + (pos - l[-temp.get(i)] - 1) * y % mod) % mod;
				}
			}
			
			for(int i = 0; i < temp.size(); i++) {
				
				if(temp.get(i) < 0)
					continue;
				
				while(!pq.isEmpty() && vis[val[pq.peek().i]] == 1)
					pq.poll();
				
				if(pq.isEmpty() || y * (pos - pq.peek().w) >= x) {
					vis[temp.get(i)] = 1; 
					val[temp.get(i)] = temp.get(i);
					cost[temp.get(i)] = (cost[temp.get(i)] + x) % mod;
				}
				else {
					vis[val[pq.peek().i]] = 1;
					val[temp.get(i)] = val[pq.peek().i]; 
					cost[temp.get(i)] = (cost[temp.get(i)] + y * (pos - pq.peek().w) % mod) % mod;
				}
				
			}

		}
		
		for(int i = 1; i <= n; i++) 
			totalCost = (cost[i] + totalCost) % mod;
		
		w.println(totalCost);
		
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
		new Thread(null, new cf4(),"cf4",1<<26).start();
	}
}   