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
        EMessengerSimulator solver = new EMessengerSimulator();
        solver.solve(1, in, out);
        out.close();
    }

    static class EMessengerSimulator {
        int[] tree;
        int[] lazy;
        int m;

        void build(int n, int nL, int nR) {
            if (nL == nR) {
                if (nL >= m)
                    tree[n] = nL - m;
                return;
            }
            build(2 * n + 1, nL, (nL + nR) / 2);
            build(2 * n + 2, (nL + nR) / 2 + 1, nR);
            tree[n] = Math.max(tree[2 * n + 1], tree[2 * n + 2]);
        }

        void update(int n, int nL, int nR, int l, int r) {
            pushLazy(n, nL, nR);
            if (nR < l || r < nL)
                return;
            if (l <= nL && nR <= r) {
                lazy[n]++;
                pushLazy(n, nL, nR);
                return;
            }
            update(2 * n + 1, nL, (nL + nR) / 2, l, r);
            update(2 * n + 2, (nL + nR) / 2 + 1, nR, l, r);
            tree[n] = Math.max(tree[2 * n + 1], tree[2 * n + 2]);
        }

        void pupdate(int n, int nL, int nR, int idx) {
            pushLazy(n, nL, nR);
            if (nR < idx || idx < nL)
                return;
            if (nL == idx && nR == idx) {
                tree[n] = 0;
                return;
            }
            pupdate(2 * n + 1, nL, (nL + nR) / 2, idx);
            pupdate(2 * n + 2, (nL + nR) / 2 + 1, nR, idx);
            tree[n] = Math.max(tree[2 * n + 1], tree[2 * n + 2]);
        }

        int query(int n, int nL, int nR, int idx) {
            pushLazy(n, nL, nR);
            if (nR < idx || idx < nL)
                return Integer.MIN_VALUE;
            if (nL == idx && nR == idx)
                return tree[n];
            int r1 = query(2 * n + 1, nL, (nL + nR) / 2, idx);
            int r2 = query(2 * n + 2, (nL + nR) / 2 + 1, nR, idx);
            tree[n] = Math.max(tree[2 * n + 1], tree[2 * n + 2]);
            return Math.max(r1, r2);
        }

        void pushLazy(int n, int nL, int nR) {
            tree[n] += lazy[n];
            if (nL != nR) {
                lazy[2 * n + 1] += lazy[n];
                lazy[2 * n + 2] += lazy[n];
            }
            lazy[n] = 0;
        }

        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt();
            m = s.nextInt();
            int[] pos = new int[n];
            int[] rmin = new int[n];
            for (int i = 0; i < n; i++) {
                rmin[i] = i;
                pos[i] = m + i;
            }
            int[] rmax = new int[n];
            int k = n + m;
            tree = new int[1 << (int) (Math.ceil(Math.log(k) / Math.log(2)) + 1)];
            lazy = new int[1 << (int) (Math.ceil(Math.log(k) / Math.log(2)) + 1)];
            build(0, 0, k - 1);
            for (int i = 1; i <= m; i++) {
                int x = s.nextInt() - 1;
                rmin[x] = 0;
                rmax[x] = Math.max(query(0, 0, k - 1, pos[x]), rmax[x]);
                update(0, 0, k - 1, 0, pos[x] - 1);
                pos[x] = m - i;
                pupdate(0, 0, k - 1, m - i);
            }

            for (int i = 0; i < n; i++)
                rmax[i] = Math.max(query(0, 0, k - 1, pos[i]), rmax[i]);

            for (int i = 0; i < n; i++)
                w.println((rmin[i] + 1) + " " + (rmax[i] + 1));
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

