import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
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
        E2WeightsDivisionHardVersion solver = new E2WeightsDivisionHardVersion();
        solver.solve(1, in, out);
        out.close();
    }

    static class E2WeightsDivisionHardVersion {
        ArrayList<Integer>[] adj;
        Edge[] edge;
        long[] sub;
        long[] wt;
        int[] c;

        void dfs(int x, int p) {
            for (int i : adj[x]) {
                int y = edge[i].other(x);
                if (y != p) {
                    wt[y] = edge[i].w;
                    c[y] = edge[i].c;
                    dfs(y, x);
                    sub[x] += sub[y];
                }
            }
            if (sub[x] == 0)
                sub[x] = 1;
        }

        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int t = s.nextInt();
            while (t-- > 0) {
                int n = s.nextInt();
                long k = s.nextLong();
                adj = new ArrayList[n];
                for (int i = 0; i < n; i++)
                    adj[i] = new ArrayList<>();
                edge = new Edge[n - 1];
                for (int i = 0; i < n - 1; i++) {
                    edge[i] = new Edge(s.nextInt() - 1, s.nextInt() - 1, s.nextInt(), s.nextInt());
                    adj[edge[i].u].add(i);
                    adj[edge[i].v].add(i);
                }
                sub = new long[n];
                wt = new long[n];
                c = new int[n];
                dfs(0, -1);
                ArrayList<Long>[] a = new ArrayList[2];
                a[0] = new ArrayList<>();
                a[1] = new ArrayList<>();
                long curr = 0;
                for (int i = 1; i < n; i++) {
                    long x = wt[i];
                    curr += wt[i] * sub[i];
                    while (x > 0) {
                        a[c[i] - 1].add((x - x / 2) * sub[i]);
                        x /= 2;
                    }
                }
                Collections.sort(a[0]);
                Collections.sort(a[1]);
                long cost = Long.MAX_VALUE;
                long req = curr - k;
                if (req <= 0) {
                    w.println(0);
                    continue;
                }
                long sum0 = 0;
                int r = -1;
                for (int i = 0; i < a[0].size() && sum0 < req; i++) {
                    sum0 += a[0].get(a[0].size() - 1 - i);
                    r++;
                }
                if (sum0 >= req) {
                    cost = Math.min(r + 1, cost);
                }
                long sum1 = 0;
                for (int l = 0; l < a[1].size(); l++) {
                    sum1 += a[1].get(a[1].size() - 1 - l);
                    while (r >= 0 && sum0 + sum1 - a[0].get(a[0].size() - 1 - r) >= req) {
                        sum0 -= a[0].get(a[0].size() - 1 - r);
                        r--;
                    }
                    if (sum0 + sum1 >= req) {
                        cost = Math.min(2 * (l + 1) + r + 1, cost);
                    }
                }
                w.println(cost);
            }


        }

        class Edge {
            int u;
            int v;
            int w;
            int c;

            Edge(int u, int v, int w, int c) {
                this.u = u;
                this.v = v;
                this.w = w;
                this.c = c;
            }

            int other(int x) {
                if (x == u)
                    return v;
                return u;
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

        public long nextLong() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = read();
            }
            long res = 0;
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

