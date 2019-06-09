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
        EArthurAndBrackets solver = new EArthurAndBrackets();
        solver.solve(1, in, out);
        out.close();
    }

    static class EArthurAndBrackets {
        int[][] dp;
        int[] l;
        int[] r;

        void recur(int i, int len, int ex, char[] a) {
            if (len == 0) {
                a[ex] = '(';
                a[ex + 1] = ')';
                return;
            }
            if (len > 0 && dp[i + 1][len - 1] == 1 && l[i] <= 2 * len + 1 && 2 * len + 1 <= r[i]) {
                a[ex] = '(';
                a[ex + 2 * len + 1] = ')';
                recur(i + 1, len - 1, ex + 1, a);
                return;
            }

            for (int j = 0; j < len; j++) {
                if ((dp[i][j] & dp[i + j + 1][len - j - 1]) == 1) {
                    recur(i, j, ex, a);
                    recur(i + j + 1, len - j - 1, ex + 2 * (j + 1), a);
                    return;
                }
            }
        }

        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt();
            l = new int[n];
            r = new int[n];
            for (int i = 0; i < n; i++) {
                l[i] = s.nextInt();
                r[i] = s.nextInt();
            }
            dp = new int[n][n];
            for (int i = 0; i < n; i++)
                if (l[i] == 1)
                    dp[i][0] = 1;
            for (int j = 1; j < n; j++) {
                for (int i = 0; i < n - j; i++) {
                    if (dp[i + 1][j - 1] == 1 && l[i] <= 2 * j + 1 && 2 * j + 1 <= r[i])
                        dp[i][j] = 1;
                    for (int k = 0; k < j; k++)
                        dp[i][j] = Math.max(dp[i][k] & dp[i + k + 1][j - k - 1], dp[i][j]);
                }
            }
            if (dp[0][n - 1] == 1) {
                char[] a = new char[2 * n];
                recur(0, n - 1, 0, a);
                for (int i = 0; i < 2 * n; i++)
                    w.print(a[i]);
                w.println();
            } else
                w.println("IMPOSSIBLE");
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

