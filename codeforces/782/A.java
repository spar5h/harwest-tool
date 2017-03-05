/* package codechef; // don't place package name! */

import java.util.*;
import java.lang.*;
import java.io.*;

public class Solution
{
	public static void main (String[] args) throws java.lang.Exception
	
	{
	    
	    Scanner s = new Scanner(System.in);
	    
	    int n = s.nextInt();
	    
	    int c = 0;
	    int max = 0;
	    int[] a = new int[100001];
	    
	    for(int i = 0; i < 2*n; i++) {
	        
	        int x = s.nextInt();
	        a[x]++;
	        
	        if(a[x] == 2)
	            c--;
	        else
	            c++;
	            
	        if(max < c)
	            max = c;
	    }
		
		System.out.println(max);
	}
}
