import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashSet;
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
        FEhabAndTheBigFinale solver = new FEhabAndTheBigFinale();
        solver.solve(1, in, out);
        out.close();
    }

    static class FEhabAndTheBigFinale {
        int time;
        int h;
        int[] tin;
        int[] tout;
        int[] dp;
        int[] dist;
        int[][] anc;
        HashSet<Integer>[] adj;

        void lcaDfs(int x, int p) {
            tin[x] = time++;
            anc[x][0] = p;
            for (int i = 1; i < h; i++)
                anc[x][i] = anc[anc[x][i - 1]][i - 1];
            for (int y : adj[x]) {
                if (y != p) {
                    dist[y] = dist[x] + 1;
                    lcaDfs(y, x);
                }
            }
            tout[x] = time++;
        }

        boolean isAnc(int u, int v) {
            if (tin[u] <= tin[v] && tout[v] <= tout[u])
                return true;
            return false;
        }

        int lca(int u, int v) {
            if (isAnc(u, v))
                return u;
            if (isAnc(v, u))
                return v;
            for (int i = h - 1; i >= 0; i--)
                if (!isAnc(anc[u][i], v))
                    u = anc[u][i];
            return anc[u][0];
        }

        int dLca(int u, int v) {
            int lca = lca(u, v);
            return dist[u] + dist[v] - 2 * dist[lca];
        }

        int getCentroid(int x) {
            dfs(x, x);
            int n = dp[x], p = x;
            while (true) {
                int idx = -1;
                for (int y : adj[x])
                    if (y != p && dp[y] > n / 2)
                        idx = y;
                if (idx == -1)
                    break;
                p = x;
                x = idx;
            }
            return x;
        }

        int dfs(int x, int p) {
            dp[x] = 1;
            for (int y : adj[x])
                if (y != p)
                    dp[x] += dfs(y, x);
            return dp[x];
        }

        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt();
            adj = new HashSet[n];
            for (int i = 0; i < n; i++)
                adj[i] = new HashSet<>();
            for (int i = 0; i < n - 1; i++) {
                int u = s.nextInt() - 1, v = s.nextInt() - 1;
                adj[u].add(v);
                adj[v].add(u);
            }
            dp = new int[n];
            h = (int) (Math.log(n) / Math.log(2)) + 1;
            tin = new int[n];
            tout = new int[n];
            anc = new int[n][h];
            dist = new int[n];
            time = 0;
            lcaDfs(0, 0);
            int root = 0;
            w.println("d " + (root + 1));
            w.flush();
            int dRoot = s.nextInt();
            while (dRoot != 0) {
                int centroid = getCentroid(root);
                w.println("d " + (centroid + 1));
                w.flush();
                int dCentroid = s.nextInt();
                if (dCentroid == 0) {
                    root = centroid;
                    break;
                }
                int d = dLca(root, centroid);
                if (dRoot == dCentroid + d) {
                    w.println("s " + (centroid + 1));
                    w.flush();
                    root = s.nextInt() - 1;
                    dRoot = dCentroid - 1;
                    adj[root].remove(centroid);
                } else {
                    for (int y : adj[centroid])
                        adj[y].remove(centroid);
                }
            }
            w.println("! " + (root + 1));
            w.flush();
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

