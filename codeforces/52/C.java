import java.util.*;
import java.lang.*;
import java.math.*;
import java.io.*;
 
/* spar5h */
 
public class codechef implements Runnable {    
	
	static long wt, tree[], lazy[], a[];
	static int l, r;
	
	static void build(int n, int nL, int nR) {
		
		if(nL == nR) {
			tree[n] = a[nL]; return;
		}
		
		build(2 * n, nL, (nL + nR) / 2);
		build(2 * n + 1, (nL + nR) / 2 + 1, nR);
		
		tree[n] = Math.min(tree[2 * n], tree[2 * n + 1]);
	}
	
	static void update(int n, int nL, int nR) {
		
		if(lazy[n] != 0) {
			
			tree[n] += lazy[n];
			
			if(nL != nR) {
				lazy[2 * n] += lazy[n];
				lazy[2 * n + 1] += lazy[n];
			}
			
			lazy[n] = 0;
		}
		
		if(nL > r || nR < l)
			return;
		
		if(l <= nL && nR <= r) {
			
			tree[n] += wt;
			
			if(nL != nR) {
				lazy[2 * n] += wt;
				lazy[2 * n + 1] += wt;
			}
			
			return;
		}
		
		update(2 * n, nL, (nL + nR) / 2);
		update(2 * n + 1, (nL + nR) / 2 + 1, nR);
		
		tree[n] = Math.min(tree[2 * n], tree[2 * n + 1]);
	}
	
	static long query(int n, int nL, int nR) {
		
		if(lazy[n] != 0) {
			
			tree[n] += lazy[n];
			
			if(nL != nR) {
				lazy[2 * n] += lazy[n];
				lazy[2 * n + 1] += lazy[n];
			}
			
			lazy[n] = 0;
		}
		
		if(nL > r || nR < l)
			return Long.MAX_VALUE;
		
		if(l <= nL && nR <= r)
			return tree[n];
		
		return Math.min(query(2 * n, nL, (nL + nR) / 2), query(2 * n + 1, (nL + nR) / 2 + 1, nR));
	}
	
	public void run() {
		
		BufferedReader s = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter w = new PrintWriter(System.out);
		
		int N = 0;
		
		try {
			N = Integer.parseInt(s.readLine());
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String str = "";
		
		try {
			str = s.readLine();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		StringTokenizer st = new StringTokenizer(str);
		
		a = new long[N + 1];
		
		for(int i = 1; i <= N; i++)
			a[i] = Long.parseLong(st.nextToken());
		
		int n = (int)Math.pow(2,  (int)Math.ceil(Math.log(N) / Math.log(2)) + 1) - 1;
		
		tree = new long[n + 1];
		lazy = new long[n + 1];
		
		build(1, 1, N);
		
		int q = 0;
		try {
			q = Integer.parseInt(s.readLine());
		} catch (NumberFormatException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		while(q-- > 0) {
			
			try {
				str = s.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			st = new StringTokenizer(str);
			
			if(st.countTokens() == 3) {
				
				int tempL = Integer.parseInt(st.nextToken()) + 1;
				int tempR = Integer.parseInt(st.nextToken()) + 1;
				wt = Integer.parseInt(st.nextToken());
				
				if(tempL > tempR) {
					
					l = tempL; r = N;
					update(1, 1, N);
					l = 1; r = tempR;
					update(1, 1, N);
				}
				
				else {
					
					l = tempL; r = tempR;
					update(1, 1, N);
				}
				
			}
			
			else {
				
				int tempL = Integer.parseInt(st.nextToken()) + 1;
				int tempR = Integer.parseInt(st.nextToken()) + 1;
				long res;
				
				if(tempL > tempR) {
					
					l = tempL; r = N;
					res = query(1, 1, N);
					l = 1; r = tempR;
					res = Math.min(query(1, 1, N), res);
				}
				
				else {
					
					l = tempL; r = tempR;
					res = query(1, 1, N);
				}
				
				w.println(res);
			}
		}
		
		w.close();
		
	}
	
	public static void main(String args[]) throws Exception
	{
		new Thread(null, new codechef(),"codechef",1<<26).start();
	}   
} 