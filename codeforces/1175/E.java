import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
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
        EMinimalSegmentCover solver = new EMinimalSegmentCover();
        solver.solve(1, in, out);
        out.close();
    }

    static class EMinimalSegmentCover {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt(), m = s.nextInt();
            int k = (int) 5e5 + 1;
            int[] val = new int[k];
            Arrays.fill(val, -1);
            while (n-- > 0) {
                int l = s.nextInt(), r = s.nextInt();
                val[l] = Math.max(r, val[l]);
            }
            for (int i = 1; i < k; i++)
                val[i] = Math.max(val[i - 1], val[i]) >= i ? Math.max(val[i - 1], val[i]) : -1;
            int[] idx = new int[k];
            int j = 0, c = 0;
            ArrayList<Tree> tree = new ArrayList<>();
            ArrayList<Integer> start = new ArrayList<>();
            while (j < k) {
                if (val[j] == -1) {
                    idx[j] = -1;
                    j++;
                    continue;
                }
                int i = j;
                start.add(i);
                while (j < k && j != val[j]) {
                    idx[j] = c;
                    j++;
                }
                idx[j] = c;
                Tree t = new Tree(j - i + 1);
                while (i < j) {
                    t.addEdge(i - start.get(c), val[i] - start.get(c));
                    i++;
                }
                t.buildLCA(j - start.get(c));
                c++;
                j++;
                tree.add(t);
            }
            while (m-- > 0) {
                int x = s.nextInt(), y = s.nextInt();
                if (idx[x] == -1 || idx[y] == -1 || (idx[x] != idx[y])) {
                    w.println(-1);
                    continue;
                }
                Tree t = tree.get(idx[x]);
                x -= start.get(idx[x]);
                y -= start.get(idx[y]);
                w.println(t.getDistance(x, t.getMinAnc(x, y)));
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

    static class Tree {
        int n;
        ArrayList<Integer>[] adj;
        int time;
        int[] tin;
        int[] tout;
        int[] d;
        int[][] anc;

        public Tree(int n) {
            this.n = n;
            adj = new ArrayList[n];
            for (int i = 0; i < n; i++)
                adj[i] = new ArrayList<>();
        }

        public void addEdge(int u, int v) {
            adj[u].add(v);
            adj[v].add(u);
        }

        public void buildLCA(int root) {
            int h = (int) (Math.log(n) / Math.log(2)) + 1;
            anc = new int[h][n];
            tin = new int[n];
            tout = new int[n];
            time = 0;
            d = new int[n];
            dfsLCA(root, root);
        }

        void dfsLCA(int x, int p) {
            tin[x] = time++;
            anc[0][x] = p;
            for (int i = 1; i < anc.length; i++)
                anc[i][x] = anc[i - 1][anc[i - 1][x]];
            for (int y : adj[x]) {
                if (y != p) {
                    d[y] = d[x] + 1;
                    dfsLCA(y, x);
                }
            }
            tout[x] = time++;
        }

        boolean isAnc(int u, int v) {
            return (tin[u] <= tin[v] && tout[u] >= tout[v]);
        }

        public int getLCA(int u, int v) {
            if (isAnc(u, v))
                return u;
            if (isAnc(v, u))
                return v;
            for (int i = anc.length - 1; i >= 0; i--)
                if (!isAnc(anc[i][u], v))
                    u = anc[i][u];
            return anc[0][u];
        }

        public int getDistance(int u, int v) {
            int lca = getLCA(u, v);
            return d[u] + d[v] - 2 * d[lca];
        }

        public int getMinAnc(int u, int v) {
            for (int i = anc.length - 1; i >= 0; i--)
                if (anc[i][u] < v)
                    u = anc[i][u];
            return anc[0][u];
        }

    }
}

