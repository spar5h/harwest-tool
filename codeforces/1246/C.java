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
        CRockIsPush solver = new CRockIsPush();
        solver.solve(1, in, out);
        out.close();
    }

    static class CRockIsPush {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt(), m = s.nextInt();
            char[][] a = new char[n][];
            for (int i = 0; i < n; i++)
                a[i] = s.next().toCharArray();
            if (n == 1 && m == 1) {
                w.println(1);
                return;
            }
            long[][][] dp = new long[2][n][m];
            int c = 0;
            for (int i = 0; i < n; i++)
                if (a[i][0] == 'R')
                    c++;
            long[][][] pre = new long[2][n][m];
            for (int i = 0; i < n - c; i++) {
                dp[0][i][0] = 1;
                pre[1][i][0] = 1;
            }
            c = 0;
            for (int j = 0; j < m; j++)
                if (a[0][j] == 'R')
                    c++;
            for (int j = 0; j < m - c; j++) {
                dp[1][0][j] = 1;
                pre[0][0][j] = 1;
            }
            int[][][] idx = new int[2][n + 1][m + 1];
            for (int i = 1; i < n; i++) {
                c = 0;
                idx[1][i][c] = m - 1;
                for (int j = m - 1; j >= 0; j--) {
                    if (a[i][j] == 'R') {
                        c++;
                        idx[1][i][c] = j - 1;
                    }
                }
                while (c + 1 <= m) {
                    c++;
                    idx[1][i][c] = -1;
                }
            }
            for (int j = 1; j < m; j++) {
                c = 0;
                idx[0][c][j] = n - 1;
                for (int i = n - 1; i >= 0; i--) {
                    if (a[i][j] == 'R') {
                        c++;
                        idx[0][c][j] = i - 1;
                    }
                }
                while (c + 1 <= n) {
                    c++;
                    idx[0][c][j] = -1;
                }
            }
            long mod = (long) 1e9 + 7;
            for (int i = 1; i < n; i++) {
                for (int j = 1; j < m; j++) {
                    int allow = m - j;
                    int id = idx[1][i][allow];
                    if (id < j) {
                        dp[1][i][j] = pre[1][i][j - 1];
                        if (id != -1)
                            dp[1][i][j] = (dp[1][i][j] - pre[1][i][id] + mod) % mod;
                    }
                    allow = n - i;
                    id = idx[0][allow][j];
                    if (id < i) {
                        dp[0][i][j] = pre[0][i - 1][j];
                        if (id != -1)
                            dp[0][i][j] = (dp[0][i][j] - pre[0][id][j] + mod) % mod;
                    }
                    pre[1][i][j] = (dp[0][i][j] + pre[1][i][j - 1]) % mod;
                    pre[0][i][j] = (dp[1][i][j] + pre[0][i - 1][j]) % mod;
                }
            }
            w.println((dp[0][n - 1][m - 1] + dp[1][n - 1][m - 1]) % mod);
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

