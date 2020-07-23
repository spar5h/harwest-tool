import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.io.IOException;
import java.util.ArrayList;
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
        BUnmerge solver = new BUnmerge();
        solver.solve(1, in, out);
        out.close();
    }

    static class BUnmerge {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int t = s.nextInt();
            while (t-- > 0) {
                int n = s.nextInt();
                int[] a = new int[2 * n];
                for (int i = 0; i < 2 * n; i++)
                    a[i] = s.nextInt();
                ArrayList<Integer> b = new ArrayList<>();
                int i = 0;
                while (i < 2 * n) {
                    int j = i;
                    while (j < 2 * n && a[i] >= a[j])
                        j++;
                    b.add(j - i);
                    i = j;
                }
                int m = b.size();
                int[][] dp = new int[m + 1][n + 1];
                dp[0][0] = 1;
                for (i = 1; i <= m; i++) {
                    for (int j = 0; j < Math.min(n + 1, b.get(i - 1)); j++)
                        dp[i][j] = dp[i - 1][j];
                    for (int j = b.get(i - 1); j <= n; j++) {
                        dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - b.get(i - 1)]);
                    }
                }
                if (dp[m][n] == 1)
                    w.println("YES");
                else
                    w.println("NO");
            }
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

