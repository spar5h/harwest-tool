import java.util.*;
import java.lang.*;
import java.io.*;

/* Name of the class has to be "Main" only if the class is public. */
 public class Codechef
{
	public static void main (String[] args) throws java.lang.Exception
	{
		// your code goes here
		
		Scanner s = new Scanner(System.in);
		
		int n = s.nextInt();
		int x = s.nextInt();
		
		if(x == 0) {
		    
		    if(((n - 1) / 2) % 3 == 0)
		    System.out.println(1);
		    else if(((n - 1) / 2) % 3 == 1)
		    System.out.println(2);
		    else if(((n - 1) / 2) % 3 == 2)
		    System.out.println(0);

		}
		
		else if (x == 2) {
		    
		    if((n / 2) % 3 == 0)
		    System.out.println(2);
		    else if((n / 2) % 3 == 1)
		    System.out.println(0);
		    else if((n / 2) % 3 == 2)
		    System.out.println(1);
	
		}
		
		else if (x == 1) {
		    
		    if(n % 3 == 0)
		    System.out.println(1);
		    else if(n % 3 == 1)
		    System.out.println(0);
		    else if(n % 3 == 2)
		    System.out.println(2);
		}
	}
}