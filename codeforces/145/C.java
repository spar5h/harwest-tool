import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 *
 * @author Sparsh Sanchorawala
 */
public class Main {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        PrintWriter out = new PrintWriter(outputStream);
        CLuckySubsequence solver = new CLuckySubsequence();
        solver.solve(1, in, out);
        out.close();
    }

    static class CLuckySubsequence {
        long mod = (long) 1e9 + 7;
        long[] fact;
        long[] ifact;

        void addMap(HashMap<Integer, Integer> hm, int x) {
            if (hm.get(x) != null)
                hm.put(x, hm.get(x) + 1);
            else
                hm.put(x, 1);
        }

        long modExp(long x, long y) {
            if (y == 0)
                return 1;
            long hf = modExp(x, y / 2);
            return hf * hf % mod * ((y & 1) == 1 ? x : 1) % mod;
        }

        long nCr(int n, int r) {
            if (n < r || r < 0)
                return 0L;
            return fact[n] * ifact[n - r] % mod * ifact[r] % mod;
        }

        public void solve(int testNumber, InputReader s, PrintWriter w) {
            fact = new long[(int) 1e5 + 1];
            ifact = new long[(int) 1e5 + 1];
            fact[0] = 1;
            ifact[0] = 1;
            for (int i = 1; i <= (int) 1e5; i++) {
                fact[i] = fact[i - 1] * i % mod;
                ifact[i] = modExp(fact[i], mod - 2);
            }
            int n = s.nextInt(), k = s.nextInt();
            int unlucky = 0;
            HashMap<Integer, Integer> hm = new HashMap<Integer, Integer>();
            while (n-- > 0) {
                int x = s.nextInt(), temp = x;
                while (temp > 0) {
                    if (!(temp % 10 == 4 || temp % 10 == 7)) {
                        x = -1;
                        break;
                    }
                    temp /= 10;
                }
                if (x != -1)
                    addMap(hm, x);
                else
                    unlucky++;
            }
            int[] count = new int[hm.size()];
            int m = 0;
            for (int i : hm.values()) {
                count[m] = i;
                m++;
            }
            long[] dp = new long[m + 1];
            dp[0] = 1;
            for (int i = 0; i < m; i++) {
                long[] temp = new long[m + 1];
                for (int j = 0; j <= m; j++) {
                    temp[j] = dp[j];
                    dp[j] = 0;
                }
                dp[0] = temp[0];
                for (int j = 1; j <= m; j++)
                    dp[j] = (temp[j] + temp[j - 1] * count[i] % mod) % mod;
            }
            long res = 0;
            for (int i = 0; i <= m; i++)
                res = (res + dp[i] * nCr(unlucky, k - i) % mod) % mod;
            w.println(res);
        }

    }

    static class InputReader {
        private InputStream stream;
        private byte[] buf = new byte[1024];
        private int curChar;
        private int numChars;
        private InputReader.SpaceCharFilter filter;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        public int read() {
            if (numChars == -1) {
                throw new InputMismatchException();
            }
            if (curChar >= numChars) {
                curChar = 0;
                try {
                    numChars = stream.read(buf);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }
                if (numChars <= 0) {
                    return -1;
                }
            }
            return buf[curChar++];
        }

        public int nextInt() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = read();
            }
            int res = 0;
            do {
                if (c < '0' || c > '9') {
                    throw new InputMismatchException();
                }
                res *= 10;
                res += c - '0';
                c = read();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        public boolean isSpaceChar(int c) {
            if (filter != null) {
                return filter.isSpaceChar(c);
            }
            return isWhitespace(c);
        }

        public static boolean isWhitespace(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

        public interface SpaceCharFilter {
            public boolean isSpaceChar(int ch);

        }

    }
}

