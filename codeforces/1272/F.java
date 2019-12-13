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
        FTwoBracketSequences solver = new FTwoBracketSequences();
        solver.solve(1, in, out);
        out.close();
    }

    static class FTwoBracketSequences {
        char[] a;
        char[] b;
        int n;
        int m;
        int[][][] dp;
        Triple[][][] parent;

        void recur(int i, int j, int k) {
            dp[i][j][k] = (int) 1e5;
            int temp = (int) 1e5;
            int ni = i, nj = j;
            if (a[i] == '(')
                ni--;
            if (b[j] == '(')
                nj--;
            if (k - 1 >= 0) {
                if (dp[ni][nj][k - 1] == -1)
                    recur(ni, nj, k - 1);
                if (dp[ni][nj][k - 1] + 1 < temp) {
                    temp = dp[ni][nj][k - 1] + 1;
                    parent[i][j][k] = new Triple(ni, nj, k - 1);
                }
            }
            ni = i;
            nj = j;
            if (a[i] == ')')
                ni--;
            if (b[j] == ')')
                nj--;
            if (k + 1 <= (n + m)) {
                if (dp[ni][nj][k + 1] == -1)
                    recur(ni, nj, k + 1);
                if (dp[ni][nj][k + 1] + 1 < temp) {
                    temp = dp[ni][nj][k + 1] + 1;
                    parent[i][j][k] = new Triple(ni, nj, k + 1);
                }
            }
            dp[i][j][k] = temp;
        }

        public void solve(int testNumber, InputReader s, PrintWriter w) {
            a = (" " + s.next()).toCharArray();
            b = (" " + s.next()).toCharArray();
            n = a.length - 1;
            m = b.length - 1;
            dp = new int[n + 1][m + 1][(n + m) + 1];
            parent = new Triple[n + 1][m + 1][(n + m) + 1];
            for (int i = 0; i <= n; i++)
                for (int j = 0; j <= m; j++)
                    for (int k = 0; k <= (n + m); k++)
                        dp[i][j][k] = -1;
            dp[0][0][0] = 0;
            recur(n, m, 0);
            int i = n, j = m, k = 0;
            StringBuffer sb = new StringBuffer();
            while (parent[i][j][k] != null) {
                if (parent[i][j][k].k == k + 1)
                    sb.append(')');
                else
                    sb.append('(');
                int ni = parent[i][j][k].i;
                int nj = parent[i][j][k].j;
                int nk = parent[i][j][k].k;
                i = ni;
                j = nj;
                k = nk;
            }
            sb.reverse();
            w.println(sb.toString());
        }

        class Triple {
            int i;
            int j;
            int k;

            Triple(int i, int j, int k) {
                this.i = i;
                this.j = j;
                this.k = k;
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

