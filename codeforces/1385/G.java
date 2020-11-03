import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.io.IOException;
import java.util.Collections;
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
        GColumnsSwaps solver = new GColumnsSwaps();
        solver.solve(1, in, out);
        out.close();
    }

    static class GColumnsSwaps {
        int[] dfs(int x, ArrayList<Integer>[] adj, int[] vis, ArrayList<Integer> store) {
            store.add(x);
            vis[x % vis.length] = 1;
            int[] ret = new int[2];
            ret[0] = 1;
            ret[1] = x / vis.length;
            for (int y : adj[x]) {
                if (vis[y % vis.length] == -1) {
                    vis[y % vis.length] = y / vis.length;
                    int[] v = dfs(y, adj, vis, store);
                    ret[0] += v[0];
                    ret[1] += v[1];
                }
            }
            return ret;
        }

        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int t = s.nextInt();
            while (t-- > 0) {
                int n = s.nextInt();
                ArrayList<Integer>[] adj = new ArrayList[2 * n];
                for (int i = 0; i < 2 * n; i++)
                    adj[i] = new ArrayList<>();
                int[] c = new int[n];
                Arrays.fill(c, -1);
                int[] f = new int[n];
                int[] a = new int[2 * n];
                for (int i = 0; i < 2 * n; i++) {
                    a[i] = s.nextInt() - 1;
                    f[a[i]]++;
                    if (c[a[i]] != -1) {
                        int u = c[a[i]], v = i;
                        v = (1 ^ (v / n)) * n + v % n;
                        adj[u].add(v);
                        adj[v].add(u);
                        u = (1 ^ (u / n)) * n + u % n;
                        v = (1 ^ (v / n)) * n + v % n;
                        adj[u].add(v);
                        adj[v].add(u);
                    } else
                        c[a[i]] = i;
                }
                boolean impossible = false;
                for (int i = 0; i < n; i++) {
                    if (f[i] != 2) {
                        impossible = true;
                    }
                }
                if (impossible) {
                    w.println(-1);
                    continue;
                }
                ArrayList<Integer> res = new ArrayList<>();
                int[] vis = new int[n];
                Arrays.fill(vis, -1);
                for (int i = 0; i < n; i++) {
                    ArrayList<Integer> store = new ArrayList<>();
                    int[] b = dfs(i, adj, vis, store);
                    if (b[1] <= b[0] - b[1]) {
                        for (int j : store)
                            if (j / n == 1)
                                res.add(j % n);
                    } else {
                        for (int j : store)
                            if (j / n == 0)
                                res.add(j % n);
                    }
                }
                Collections.sort(res);
                w.println(res.size());
                for (int i : res)
                    w.print((i + 1) + " ");
                w.println();
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

