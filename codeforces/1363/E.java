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
        ETreeShuffling solver = new ETreeShuffling();
        solver.solve(1, in, out);
        out.close();
    }

    static class ETreeShuffling {
        long[] dp;
        boolean[] valid;
        boolean[] vis;
        long[] a;
        int[] b;
        int[] c;
        int[] fb;
        int[] fc;
        int[][] change;
        ArrayList<Integer>[] adj;

        void dfs(int x) {
            vis[x] = true;
            dp[x] = 0;
            fb[x] += b[x];
            fc[x] += c[x];
            if ((b[x] ^ c[x]) == 1) {
                change[x][b[x]]++;
            }
            for (int y : adj[x]) {
                if (!vis[y]) {
                    a[y] = Math.min(a[x], a[y]);
                    dfs(y);
                    fb[x] += fb[y];
                    fc[x] += fc[y];
                    change[x][0] += change[y][0];
                    change[x][1] += change[y][1];
                    dp[x] += dp[y];
                }
            }
            int com = Math.min(change[x][0], change[x][1]);
            dp[x] += 2 * com * a[x];
            change[x][0] -= com;
            change[x][1] -= com;
        }

        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt();
            adj = new ArrayList[n];
            a = new long[n];
            b = new int[n];
            c = new int[n];
            int xb = 0, xc = 0;
            for (int i = 0; i < n; i++) {
                a[i] = s.nextInt();
                b[i] = s.nextInt();
                xb += b[i];
                c[i] = s.nextInt();
                xc += c[i];
            }
            for (int i = 0; i < n; i++) {
                adj[i] = new ArrayList<>();
            }
            for (int i = 0; i < n - 1; i++) {
                int u = s.nextInt() - 1, v = s.nextInt() - 1;
                adj[u].add(v);
                adj[v].add(u);
            }
            if (xb != xc) {
                w.println("-1");
                return;
            }
            vis = new boolean[n];
            valid = new boolean[n];
            valid[0] = true;
            dp = new long[n];
            fb = new int[n];
            fc = new int[n];
            change = new int[n][2];
            dfs(0);
            w.println(dp[0]);
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

