import java.util.*;

public class Solution {

	public static void main(String[] args) {
		
		Scanner s = new Scanner(System.in);
		
		long n = s.nextLong();
		long m = s.nextLong();
		long a = s.nextLong();
		
		System.out.println(((n - 1)/a + 1) * ((m - 1)/a + 1));
		
	}

}