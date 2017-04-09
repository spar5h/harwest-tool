import java.util.*;

public class dank{
    
    public static void main(String[] args) {
        
        Scanner s = new Scanner(System.in);
        
        int n = s.nextInt();
        
        long x = (long)Math.pow(2, n + 1) - 2;
        
        System.out.println(x);
    }
}