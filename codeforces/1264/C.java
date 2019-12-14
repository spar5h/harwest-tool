import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.io.IOException;
import java.util.TreeSet;
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
        CBeautifulMirrorsWithQueries solver = new CBeautifulMirrorsWithQueries();
        solver.solve(1, in, out);
        out.close();
    }

    static class CBeautifulMirrorsWithQueries {
        long mod = 998244353;
        long[] tree;
        long[] lazy;
        long[] pre;

        long modExp(long x, long y) {
            if (y == 0)
                return 1;
            if (y == 1)
                return x % mod;
            long ret = modExp(x, y / 2);
            ret = ret * ret % mod;
            if (y % 2 == 1)
                ret = ret * (x % mod) % mod;
            return ret;
        }

        void ini() {
            int n = pre.length;
            int sz = (int) Math.pow(2, (int) Math.ceil(Math.log(n) / Math.log(2)) + 1);
            tree = new long[sz];
            lazy = new long[sz];
            Arrays.fill(lazy, 1);
            build(0, 0, n - 1);
        }

        void build(int n, int nL, int nR) {
            if (nL == nR) {
                tree[n] = modExp(pre[pre.length - 1], mod - 2);
                if (nL - 1 >= 0)
                    tree[n] = tree[n] * pre[nL - 1] % mod;
                return;
            }
            build(2 * n + 1, nL, (nL + nR) / 2);
            build(2 * n + 2, (nL + nR) / 2 + 1, nR);
            tree[n] = (tree[2 * n + 1] + tree[2 * n + 2]) % mod;
        }

        void pushLazy(int n, int nL, int nR) {
            tree[n] = lazy[n] * tree[n] % mod;
            if (nL != nR) {
                lazy[2 * n + 1] = lazy[2 * n + 1] * lazy[n] % mod;
                lazy[2 * n + 2] = lazy[2 * n + 2] * lazy[n] % mod;
            }
            lazy[n] = 1;
        }

        long query(int n, int nL, int nR, int l, int r) {
            pushLazy(n, nL, nR);
            if (nR < l || r < nL)
                return 0;
            if (l <= nL && nR <= r)
                return tree[n];
            long ret = (query(2 * n + 1, nL, (nL + nR) / 2, l, r) + query(2 * n + 2, (nL + nR) / 2 + 1, nR, l, r)) % mod;
            tree[n] = (tree[2 * n + 1] + tree[2 * n + 2]) % mod;
            return ret;
        }

        void update(int n, int nL, int nR, int l, int r, long x) {
            pushLazy(n, nL, nR);
            if (nR < l || r < nL)
                return;
            if (l <= nL && nR <= r) {
                lazy[n] = x;
                pushLazy(n, nL, nR);
                return;
            }
            update(2 * n + 1, nL, (nL + nR) / 2, l, r, x);
            update(2 * n + 2, (nL + nR) / 2 + 1, nR, l, r, x);
            tree[n] = (tree[2 * n + 1] + tree[2 * n + 2]) % mod;
        }

        public void solve(int testNumber, InputReader s, PrintWriter w) {

            long inv100 = modExp(100, mod - 2);
            int n = s.nextInt(), q = s.nextInt();
            long[] p = new long[n];
            for (int i = 0; i < n; i++)
                p[i] = s.nextInt() * inv100 % mod;
            pre = new long[n];
            pre[0] = p[0];
            for (int i = 1; i < n; i++)
                pre[i] = pre[i - 1] * p[i] % mod;
            ini();
            TreeSet<Integer> ts = new TreeSet<>();
            ts.add(0);
            ts.add(n);
            while (q-- > 0) {
                int u = s.nextInt() - 1;
                int l = -1, r = -1;
                if (ts.contains(u)) {
                    ts.remove(u);
                    l = u;
                    r = ts.higher(u) - 1;
                    long val = pre[r];
                    if (l - 1 >= 0)
                        val = val * modExp(pre[l - 1], mod - 2) % mod;
                    l = ts.lower(u);
                    r = u - 1;
                    update(0, 0, n - 1, l, r, modExp(val, mod - 2));
                } else {
                    l = u;
                    r = ts.higher(u) - 1;
                    long val = pre[r];
                    if (l - 1 >= 0)
                        val = val * modExp(pre[l - 1], mod - 2) % mod;
                    l = ts.lower(u);
                    r = u - 1;
                    update(0, 0, n - 1, l, r, val);
                    ts.add(u);
                }
                w.println(query(0, 0, n - 1, 0, n - 1));
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

