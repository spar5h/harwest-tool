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
        CFixedPointRemoval solver = new CFixedPointRemoval();
        solver.solve(1, in, out);
        out.close();
    }

    static class CFixedPointRemoval {
        int[] tree;

        void update(int n, int nL, int nR, int idx, int v) {
            if (nL > idx || nR < idx)
                return;
            if (nL == idx && nR == idx) {
                tree[n] += v;
                return;
            }
            update(2 * n + 1, nL, (nL + nR) / 2, idx, v);
            update(2 * n + 2, (nL + nR) / 2 + 1, nR, idx, v);
            tree[n] = tree[2 * n + 1] + tree[2 * n + 2];
        }

        int query(int n, int nL, int nR, int l, int r) {
            if (nL > r || nR < l)
                return 0;
            if (l <= nL && nR <= r)
                return tree[n];
            return query(2 * n + 1, nL, (nL + nR) / 2, l, r) + query(2 * n + 2, (nL + nR) / 2 + 1, nR, l, r);
        }

        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt(), q = s.nextInt();
            int[] a = new int[n];
            for (int i = 0; i < n; i++)
                a[i] = s.nextInt() - 1;
            tree = new int[4 * n];
            int[] st = new int[n];
            for (int i = 0; i < n; i++) {
                if (i - a[i] >= 0) {
                    int v = i - a[i];
                    if (v != 0) {
                        int l = 0, r = i - 1;
                        int ans = -1;
                        while (l <= r) {
                            int m = (l + r) / 2;
                            if (query(0, 0, n - 1, m, i - 1) >= v) {
                                ans = m;
                                l = m + 1;
                            } else
                                r = m - 1;
                        }
                        st[i] = ans;
                    } else
                        st[i] = i;
                } else
                    st[i] = -1;
                if (st[i] != -1)
                    update(0, 0, n - 1, st[i], 1);
            }
            ArrayList<Integer>[] adj = new ArrayList[n];
            for (int i = 0; i < n; i++)
                adj[i] = new ArrayList<>();
            Arrays.fill(tree, 0);
            for (int i = 0; i < n; i++) {
                if (st[i] >= 0) {
                    update(0, 0, n - 1, i, 1);
                    adj[st[i]].add(i);
                }
            }
            int[] res = new int[q];
            ArrayList<Pair>[] que = new ArrayList[n];
            for (int i = 0; i < n; i++)
                que[i] = new ArrayList<>();
            for (int i = 0; i < q; i++) {
                int l = s.nextInt(), r = n - 1 - s.nextInt();
                que[l].add(new Pair(i, r));
            }
            for (int i = 0; i < n; i++) {
                for (Pair p : que[i]) {
                    res[p.i] = query(0, 0, n - 1, i, p.r);
                }
                for (int x : adj[i]) {
                    update(0, 0, n - 1, x, -1);
                }
            }
            for (int i = 0; i < q; i++)
                w.println(res[i]);
        }

        class Pair {
            int i;
            int r;

            Pair(int i, int r) {
                this.i = i;
                this.r = r;
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

