import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Arrays;
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
        CXenonsAttackOnTheGangs solver = new CXenonsAttackOnTheGangs();
        solver.solve(1, in, out);
        out.close();
    }

    static class CXenonsAttackOnTheGangs {
        ArrayList<Integer>[] adj;
        int[][] val;
        long[][] dp;
        long[][] sub;
        int n;

        int dfs(int x, int p, HashSet<Integer> hs) {
            int ret = 1;
            hs.remove(x);
            for (int y : adj[x]) {
                if (y != p)
                    ret += dfs(y, x, hs);
            }
            return ret;
        }

        void dfs2(int x, int p, HashSet<Integer> hs) {
            hs.add(x);
            for (int y : adj[x]) {
                if (y != p)
                    dfs2(y, x, hs);
            }
        }

        void recur(int u, int v) {
            if (dp[val[u][v]][v] == -1)
                recur(val[u][v], v);
            if (dp[u][val[v][u]] == -1)
                recur(u, val[v][u]);
            dp[u][v] = dp[val[u][v]][v] + (n - sub[u][val[u][v]]) * (n - sub[v][val[v][u]]);
            dp[u][v] = Math.max(dp[u][val[v][u]] + (n - sub[v][val[v][u]]) * (n - sub[u][val[u][v]]), dp[u][v]);
        }

        public void solve(int testNumber, InputReader s, PrintWriter w) {
            //too slow
            n = s.nextInt();
            adj = new ArrayList[n];
            for (int i = 0; i < n; i++)
                adj[i] = new ArrayList<>();
            for (int i = 0; i < n - 1; i++) {
                int u = s.nextInt() - 1, v = s.nextInt() - 1;
                adj[u].add(v);
                adj[v].add(u);
            }
            val = new int[n][n];
            sub = new long[n][n];
            dp = new long[n][n];
            for (int i = 0; i < n; i++)
                Arrays.fill(dp[i], -1);
            HashSet<Integer> hs = new HashSet<>();
            for (int j = 0; j < n; j++)
                hs.add(j);
            for (int i = 0; i < n; i++) {
                for (int j : adj[i]) {
                    sub[i][j] = dfs(j, i, hs);
                    dp[i][j] = sub[i][j] * (n - sub[i][j]);
                    for (int k : hs)
                        val[j][k] = i;
                    dfs2(j, i, hs);
                }
            }
            long res = 0;
            for (int i = 0; i < n; i++) {
                for (int j = i + 1; j < n; j++) {
                    if (dp[i][j] == -1)
                        recur(i, j);
                    res = Math.max(dp[i][j], res);
                }
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

