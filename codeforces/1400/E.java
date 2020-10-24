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
        EClearTheMultiset solver = new EClearTheMultiset();
        solver.solve(1, in, out);
        out.close();
    }

    static class EClearTheMultiset {
        long[][] dp;
        int[] a;

        void recur(int l, int r, int v) {
            int min = a[l];
            for (int i = l + 1; i <= r; i++)
                min = Math.min(a[i], min);
            long res = min - v;
            int i = l;
            while (i <= r) {
                int j = i;
                while (j <= r && a[j] > min)
                    j++;
                if (j - 1 >= i) {
                    recur(i, j - 1, min);
                    res += dp[i][j - 1];
                }
                i = j + 1;
            }
            dp[l][r] = Math.min(res, r - l + 1);
        }

        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt();
            a = new int[n];
            for (int i = 0; i < n; i++)
                a[i] = s.nextInt();
            dp = new long[n][n];
            for (int i = 0; i < n; i++)
                Arrays.fill(dp[i], -1);
            recur(0, n - 1, 0);
            w.println(dp[0][n - 1]);
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

