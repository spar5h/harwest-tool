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
        E1TreesAndQueries solver = new E1TreesAndQueries();
        solver.solve(1, in, out);
        out.close();
    }

    static class E1TreesAndQueries {
        ArrayList<Integer>[] adj;
        int[] tin;
        int[] tout;
        int[] d;
        int[][] anc;
        int time;
        int h;

        void dfs(int x, int p) {
            d[x] = 0;
            if (p != x)
                d[x] = d[p] + 1;
            tin[x] = time++;
            anc[x][0] = p;
            for (int i = 1; i < h; i++)
                anc[x][i] = anc[anc[x][i - 1]][i - 1];
            for (int i = 0; i < adj[x].size(); i++)
                if (adj[x].get(i) != p)
                    dfs(adj[x].get(i), x);
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

        int dist(int u, int v) {
            int lca = lca(u, v);
            return d[u] + d[v] - 2 * d[lca];
        }

        public void solve(int testNumber, InputReader s, PrintWriter w) {

            int n = s.nextInt();

            //init
            h = (int) (Math.log(n) / Math.log(2)) + 1;
            tin = new int[n];
            tout = new int[n];
            anc = new int[n][h];
            d = new int[n];
            time = 0;
            adj = new ArrayList[n];
            for (int i = 0; i < n; i++)
                adj[i] = new ArrayList<Integer>();

            for (int i = 0; i < n - 1; i++) {
                int u = s.nextInt() - 1, v = s.nextInt() - 1;
                adj[u].add(v);
                adj[v].add(u);
            }

            dfs(0, 0);

            int q = s.nextInt();
            while (q-- > 0) {
                int x = s.nextInt() - 1, y = s.nextInt() - 1, a = s.nextInt() - 1, b = s.nextInt() - 1, k = s.nextInt();
                int odd = Integer.MAX_VALUE, even = Integer.MAX_VALUE;
                int v = dist(a, b);
                if (v % 2 == 0)
                    even = Math.min(v, even);
                else
                    odd = Math.min(v, odd);
                v = dist(a, x) + 1 + dist(y, b);
                if (v % 2 == 0)
                    even = Math.min(v, even);
                else
                    odd = Math.min(v, odd);
                v = dist(a, y) + 1 + dist(x, b);
                if (v % 2 == 0)
                    even = Math.min(v, even);
                else
                    odd = Math.min(v, odd);
                if (k % 2 == 0) {
                    if (k >= even)
                        w.println("YES");
                    else
                        w.println("NO");
                } else {
                    if (k >= odd)
                        w.println("YES");
                    else
                        w.println("NO");
                }
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

