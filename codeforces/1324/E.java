import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
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
        ESleepingSchedule solver = new ESleepingSchedule();
        solver.solve(1, in, out);
        out.close();
    }

    static class ESleepingSchedule {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt(), h = s.nextInt(), l = s.nextInt(), r = s.nextInt();
            int[] a = new int[n];
            for (int i = 0; i < n; i++)
                a[i] = s.nextInt();
            int[][] dp = new int[n + 1][h];
            for (int i = 0; i <= n; i++)
                Arrays.fill(dp[i], -1);
            dp[0][0] = 0;
            for (int i = 1; i <= n; i++) {
                for (int j = 0; j < h; j++) {
                    int k = (j - a[i - 1] + h) % h;
                    if (dp[i - 1][k] != -1)
                        dp[i][j] = Math.max(dp[i - 1][k] + ((l <= j && j <= r) ? 1 : 0), dp[i][j]);
                    k = (j - (a[i - 1] - 1) + h) % h;
                    if (dp[i - 1][k] != -1)
                        dp[i][j] = Math.max(dp[i - 1][k] + ((l <= j && j <= r) ? 1 : 0), dp[i][j]);
                }
            }
            int res = 0;
            for (int j = 0; j < h; j++)
                res = Math.max(dp[n][j], res);
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

