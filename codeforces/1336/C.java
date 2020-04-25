import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
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
        CKaaviAndMagicSpell solver = new CKaaviAndMagicSpell();
        solver.solve(1, in, out);
        out.close();
    }

    static class CKaaviAndMagicSpell {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            char[] a = s.next().toCharArray();
            int n = a.length;
            char[] b = s.next().toCharArray();
            int m = b.length;
            long mod = 998244353;
            long[][] dp = new long[n][n];
            for (int i = 0; i < n; i++) {
                if (i >= m || a[0] == b[i])
                    dp[i][i] = 2;

            }
            for (int len = 2; len <= n; len++) {
                for (int i = 0; i < n - len + 1; i++) {
                    if (i >= m || a[len - 1] == b[i])
                        dp[i][i + len - 1] = (dp[i][i + len - 1] + dp[i + 1][i + len - 1]) % mod;
                    if (i + len - 1 >= m || a[len - 1] == b[i + len - 1])
                        dp[i][i + len - 1] = (dp[i][i + len - 1] + dp[i][i + len - 2]) % mod;
                }
            }
            long res = 0;
            for (int len = m; len <= n; len++) {
                res = (res + dp[0][len - 1]) % mod;
            }
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

        public String nextString() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }
            StringBuilder res = new StringBuilder();
            do {
                if (Character.isValidCodePoint(c)) {
                    res.appendCodePoint(c);
                }
                c = read();
            } while (!isSpaceChar(c));
            return res.toString();
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

        public String next() {
            return nextString();
        }

        public interface SpaceCharFilter {
            public boolean isSpaceChar(int ch);

        }

    }
}

