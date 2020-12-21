import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.InputMismatchException;
import java.io.IOException;
import java.util.Queue;
import java.util.LinkedList;
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
        ENoGameNoLife solver = new ENoGameNoLife();
        solver.solve(1, in, out);
        out.close();
    }

    static class ENoGameNoLife {
        long mod = 998244353;

        int f(int x) {
            int c = 0;
            while (x > 0) {
                x >>= 1;
                c++;
            }
            return 1 << c;
        }

        long modExp(long x, long y) {
            if (y == 0)
                return 1;
            long ret = modExp(x, y >> 1);
            ret = ret * ret % mod;
            if ((y & 1) == 1) {
                ret = ret * x % mod;
            }
            return ret;
        }

        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt(), m = s.nextInt();

            int[] in = new int[n];
            Edge[] edge = new Edge[m];
            ArrayList<Integer>[] adj = new ArrayList[n];
            for (int i = 0; i < n; i++)
                adj[i] = new ArrayList<>();
            for (int i = 0; i < m; i++) {
                edge[i] = new Edge(s.nextInt() - 1, s.nextInt() - 1);
                in[edge[i].v]++;
                adj[edge[i].u].add(i);
            }

            Queue<Integer> q = new LinkedList<>();
            for (int i = 0; i < n; i++)
                if (in[i] == 0)
                    q.add(i);
            int c = 0;
            int[] topo = new int[n];
            while (!q.isEmpty()) {
                int x = q.poll();
                topo[c] = x;
                c++;
                for (int i : adj[x]) {
                    in[edge[i].v]--;
                    if (in[edge[i].v] == 0)
                        q.add(edge[i].v);
                }
            }

            int[] grundy = new int[n];
            int[] f = new int[m + 1];
            for (int i = n - 1; i >= 0; i--) {
                int x = topo[i];
                for (int j : adj[x]) {
                    int y = edge[j].v;
                    f[grundy[y]]++;
                }
                while (grundy[x] < m && f[grundy[x]] > 0)
                    grundy[x]++;
                for (int j : adj[x]) {
                    int y = edge[j].v;
                    f[grundy[y]]--;
                }
            }

            int k = 0;
            for (int i = 0; i < n; i++)
                k = Math.max(grundy[i], k);
            k = f(k);
            for (int i = 0; i < n; i++) {
                f[grundy[i]]++;
            }

            long[][] a = new long[k][k + 1];
            for (int i = 0; i < k; i++) {
                a[i][i] = n + 1;
                for (int j = 0; j < k; j++) {
                    a[i][i ^ j] = (a[i][i ^ j] - f[j] + mod) % mod;
                }
                if (i != 0)
                    a[i][k] = 1;
            }

            for (int i = 0; i < k; i++) {
                if (a[i][i] == 0) {
                    int sw = -1;
                    for (int l = i + 1; l < k; l++) {
                        if (a[l][i] != 0) {
                            sw = l;
                            break;
                        }
                    }
                    for (int j = i; j <= k; j++) {
                        long temp = a[i][j];
                        a[i][j] = a[sw][j];
                        a[sw][j] = temp;
                    }
                }
                long v = modExp(a[i][i], mod - 2);
                for (int j = i; j <= k; j++)
                    a[i][j] = a[i][j] * v % mod;
                for (int l = 0; l < k; l++) {
                    if (i == l || a[l][i] == 0)
                        continue;
                    v = a[l][i];
                    for (int j = i; j <= k; j++) {
                        a[l][j] = (a[l][j] - (v * a[i][j] % mod) + mod) % mod;
                    }
                }
            }

            w.println(a[0][k]);
        }

        class Edge {
            int u;
            int v;

            Edge(int u, int v) {
                this.u = u;
                this.v = v;
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

