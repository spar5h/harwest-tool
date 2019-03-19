import java.util.*;
import java.io.*;
import java.math.BigInteger;

import static java.lang.Math.*;
 
/* spar5h */
 
public class cf1 implements Runnable {   
	
	static void addMap(int curr, HashMap<Integer, Integer> map, HashMap<Integer, Integer>[] hm, int j) {
		
		int prev = 0;
		
		if(map.get(curr) != null)
			prev = map.get(curr);
		
		int val = 0;
		
		if(hm[j].get(curr) != null)
			val = hm[j].get(curr);
		
		if(prev + 1 <= val)
			return;
		
		hm[j].put(curr, prev + 1);
	}
	
	public void run() {
		
		InputReader s = new InputReader(System.in);
		PrintWriter w = new PrintWriter(System.out);
	
		int n = s.nextInt();
		
		int[] a = new int[n];
		
		HashMap<Integer, Integer>[] hm = new HashMap[n];
		
		for(int i = 0; i < n; i++) {
			a[i] = s.nextInt();
			hm[i] = new HashMap<Integer, Integer>();
		}
		
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		
		for(int i = 0; i < n; i++) {
			
			int curr = 0;
			
			for(int j = i; j < n; j++) {
				curr += a[j];
				addMap(curr, map, hm, j);
			}
			
			for(Map.Entry<Integer, Integer> e : hm[i].entrySet()) {
				
				if(map.get(e.getKey()) != null && map.get(e.getKey()) >= e.getValue())
					continue;
				
				map.put(e.getKey(), e.getValue());
			}
		}
		
		int key = -1;
		int value = 0;
		
		for(Map.Entry<Integer, Integer> e : map.entrySet()) {
			
			if(e.getValue() > value) {
				key = e.getKey(); value = e.getValue();
			}
		}
		
		w.println(value);
		
		int prev = -1;
		
		for(int i = 0; i < n; i++) {
			
			int curr = 0;
			
			for(int j = i; j > prev; j--) {
				
				curr += a[j];
				
				if(curr == key) {
					w.println((j + 1) + " " + (i + 1));
					prev = i;
					break;
				}
			}
		}
		
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