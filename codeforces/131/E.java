import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
import static java.lang.Math.*;

/* spar5h */

public class cf1 implements Runnable{    
	
	static void addMap(HashMap<Integer, Integer> hm, int x) {
		
		if(hm.get(x) != null)
			hm.put(x,  hm.get(x) + 1);
		else
			hm.put(x,  1);
	}
	
	public void run() {
		
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
		
		int[] res = new int[9];
		
		HashMap<Integer, Integer>[] hm = new HashMap[4];
		
		for(int i = 0; i < 4; i++)
			hm[i] = new HashMap<Integer, Integer>();
		
		int n = s.nextInt(), m = s.nextInt();
		
		int[] xi = new int[m];
		int[] xj = new int[m];
		
		for(int i = 0; i < m; i++) {
			
			xi[i] = s.nextInt(); 
			xj[i] = s.nextInt();
			
			if(hm[0].get(xi[i]) == null)
				hm[0].put(xi[i], hm[0].size());
			
			if(hm[1].get(xi[i] - xj[i]) == null)
				hm[1].put(xi[i] - xj[i], hm[1].size());
			
			if(hm[2].get(xj[i]) == null)
				hm[2].put(xj[i], hm[2].size());
			
			if(hm[3].get(xi[i] + xj[i]) == null)
				hm[3].put(xi[i] + xj[i], hm[3].size());
		}
		
		ArrayList<Integer>[][] a = new ArrayList[4][];
		
		for(int i = 0; i < 4; i++) {
			
			a[i] = new ArrayList[hm[i].size()];
					
			for(int j = 0; j < hm[i].size(); j++)
				a[i][j] = new ArrayList<Integer>();
		}
		
		for(int i = 0; i < m; i++) {
			a[0][hm[0].get(xi[i])].add(xj[i]);
			a[1][hm[1].get(xi[i] - xj[i])].add(xi[i] + xj[i]);
			a[2][hm[2].get(xj[i])].add(xi[i]);
			a[3][hm[3].get(xi[i] + xj[i])].add(xi[i] - xj[i]);
		}
		
		for(int i = 0; i < 4; i++) 
			for(int j = 0; j < hm[i].size(); j++)
				Collections.sort(a[i][j]);
		
		for(int i = 0; i < m; i++) {
			
			int count = 0;
			
			int idx = hm[0].get(xi[i]);
			
			int left = 0, right = a[0][idx].size() - 1;
			int ans = -1;
			
			while(left <= right) {
				
				int mid = (left + right) / 2;
				
				if(a[0][idx].get(mid) <= xj[i]) {
					left = mid + 1; ans = mid;
				}
				else
					right = mid - 1;
			}
			
			if(ans > 0)
				count++;
			
			if(ans != -1 && ans < a[0][idx].size() - 1)
				count++;
			
			idx = hm[1].get(xi[i] - xj[i]);
			
			left = 0; right = a[1][idx].size() - 1;
			ans = -1;
			
			while(left <= right) {
				
				int mid = (left + right) / 2;
				
				if(a[1][idx].get(mid) <= xi[i] + xj[i]) {
					left = mid + 1; ans = mid;
				}
				else
					right = mid - 1;
			}
			
			if(ans > 0)
				count++;
			
			if(ans != -1 && ans < a[1][idx].size() - 1)
				count++;
			
			idx = hm[2].get(xj[i]);
			
			left = 0; right = a[2][idx].size() - 1;
			ans = -1;
			
			while(left <= right) {
				
				int mid = (left + right) / 2;
				
				if(a[2][idx].get(mid) <= xi[i]) {
					left = mid + 1; ans = mid;
				}
				else
					right = mid - 1;
			}
			
			if(ans > 0)
				count++;
			
			if(ans != -1 && ans < a[2][idx].size() - 1)
				count++;
			
			idx = hm[3].get(xi[i] + xj[i]);
			
			left = 0; right = a[3][idx].size() - 1;
			ans = -1;
			
			while(left <= right) {
				
				int mid = (left + right) / 2;
				
				if(a[3][idx].get(mid) <= xi[i] - xj[i]) {
					left = mid + 1; ans = mid;
				}
				else
					right = mid - 1;
			}
			
			if(ans > 0)
				count++;
			
			if(ans != -1 && ans < a[3][idx].size() - 1)
				count++;
			
			res[count]++;
		}
		
		for(int i = 0; i < 9; i++)
			w.print(res[i] + " ");
		
		w.println();
		
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
		new Thread(null, new cf1(),"cf1",1<<26).start();
	}
}   