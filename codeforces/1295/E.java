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
        TaskE solver = new TaskE();
        solver.solve(1, in, out);
        out.close();
    }

    static class TaskE {
        long[] tree;
        long[] lazy;
        long[] init;

        void build(int n, int nL, int nR) {
            if (nL == nR) {
                tree[n] = init[nL];
                return;
            }
            build(2 * n + 1, nL, (nL + nR) / 2);
            build(2 * n + 2, (nL + nR) / 2 + 1, nR);
            tree[n] = Math.min(tree[2 * n + 1], tree[2 * n + 2]);
        }

        void pushLazy(int n, int nL, int nR) {
            tree[n] += lazy[n];
            if (nL != nR) {
                lazy[2 * n + 1] += lazy[n];
                lazy[2 * n + 2] += lazy[n];
            }
            lazy[n] = 0;
        }

        long query(int n, int nL, int nR, int l, int r) {
            pushLazy(n, nL, nR);
            if (r < nL || nR < l)
                return Long.MAX_VALUE;
            if (l <= nL && nR <= r)
                return tree[n];
            long ret = Math.min(query(2 * n + 1, nL, (nL + nR) / 2, l, r), query(2 * n + 2, (nL + nR) / 2 + 1, nR, l, r));
            tree[n] = Math.min(tree[2 * n + 1], tree[2 * n + 2]);
            return ret;
        }

        void update(int n, int nL, int nR, int l, int r, long val) {
            pushLazy(n, nL, nR);
            if (r < nL || nR < l)
                return;
            if (l <= nL && nR <= r) {
                lazy[n] += val;
                pushLazy(n, nL, nR);
                return;
            }
            update(2 * n + 1, nL, (nL + nR) / 2, l, r, val);
            update(2 * n + 2, (nL + nR) / 2 + 1, nR, l, r, val);
            tree[n] = Math.min(tree[2 * n + 1], tree[2 * n + 2]);
        }

        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt();
            int[] p = new int[n];
            int[] map = new int[n];
            for (int i = 0; i < n; i++) {
                p[i] = s.nextInt() - 1;
                map[p[i]] = i;
            }
            long[] a = new long[n];
            for (int i = 0; i < n; i++)
                a[i] = s.nextInt();
            init = new long[n];
            init[0] = a[0];
            for (int i = 1; i < n; i++)
                init[i] = init[i - 1] + a[i];
            int sz = 1 << (int) Math.ceil(Math.log(n) / Math.log(2) + 1);
            tree = new long[sz];
            lazy = new long[sz];
            build(0, 0, n - 1);
            long res = query(0, 0, n - 1, 0, n - 2);
            for (int mx = 0; mx < n; mx++) {
                int idx = map[mx];
                if (idx > 0)
                    update(0, 0, n - 1, 0, idx - 1, a[map[mx]]);
                update(0, 0, n - 1, idx, n - 1, -a[map[mx]]);
                res = Math.min(query(0, 0, n - 1, 0, n - 2), res);
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

