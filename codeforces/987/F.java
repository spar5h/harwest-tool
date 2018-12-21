import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
 
/* spar5h */
 
public class cf6 implements Runnable{    
	
	static void DFS(int x) {
		
		vis[x] = 1;
		
		for(int i = 0; i < n; i++)
			if((x & (1 << i)) != 0 && vis[x ^ (1 << i)] == 0)
				DFS(x ^ (1 << i));
		
		if(check[x] == 1 && vis[x ^ mask] == 0)
			DFS(x ^ mask);
	}
	
	static int n, mask;
	static int[] vis, check;
	
	public void run() {
	    
		FastReader s = new FastReader();
		PrintWriter w = new PrintWriter(System.out);
		
		n = s.nextInt();
		
		vis = new int[1 << n];
		check = new int[1 << n];
		
		mask = (1 << n) - 1;
		
		int m = s.nextInt();
		
		while(m-- > 0) 
			check[s.nextInt()] = 1;
		
		int res = 0;
		
		for(int i = 0; i < 1 << n; i++) {
			
			if(check[i] == 0 || vis[i] == 1)
				continue;
			
			DFS(i ^ mask);
			res++;
		}
		
		w.println(res);
		
		w.close();
	}
	
	static class FastReader {
		
		BufferedReader br;
		StringTokenizer st;
		
		public FastReader() {
			br = new BufferedReader(new InputStreamReader(System.in));
		}
		
		String next() {
			
			while(st == null || !st.hasMoreTokens()) {
				
				try {
					st = new StringTokenizer(br.readLine());
				}
				
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			return st.nextToken();
		}
		
		int nextInt() {
			return Integer.parseInt(next());
		}
		
		long nextLong() {
			return Long.parseLong(next());
		}
		
		double nextDouble() {
			return Double.parseDouble(next());
		}
		
		String nextLine() {
			
			String str = "";
			
			try {
				str = br.readLine();
			}
			catch(IOException e) {
				e.printStackTrace();
			}
			
			return str;
		}
	}
    
	public static void main(String args[]) throws Exception
	{
		new Thread(null, new cf6(),"cf6",1<<28).start();
	}
}   