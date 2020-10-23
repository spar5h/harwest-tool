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
        FReverseAndSwap solver = new FReverseAndSwap();
        solver.solve(1, in, out);
        out.close();
    }

    static class FReverseAndSwap {
        int[] a;
        int[] rev;
        int[] swap;
        long[] tree;

        void build(int n, int nL, int nR) {
            if (nL == nR) {
                tree[n] = a[nL];
                return;
            }
            build(2 * n + 1, nL, (nL + nR) / 2);
            build(2 * n + 2, (nL + nR) / 2 + 1, nR);
            tree[n] = tree[2 * n + 1] + tree[2 * n + 2];
        }

        long query(int n, int k, int cr, int l, int r) {
            if (r - l + 1 == (1 << k))
                return tree[n];
            cr ^= rev[k];
            int left = 2 * n + 1, right = 2 * n + 2;
            if ((swap[k] ^ cr) == 1) {
                left = 2 * n + 2;
                right = 2 * n + 1;
            }
            int m = 1 << (k - 1);
            if (r < m) {
                return query(left, k - 1, cr, l, r);
            }
            if (l >= m) {
                return query(right, k - 1, cr, l - m, r - m);
            }
            return query(left, k - 1, cr, l, m - 1) + query(right, k - 1, cr, 0, r - m);
        }

        void update(int n, int k, int cr, int i, int v) {
            if (k == 0) {
                tree[n] = v;
                return;
            }
            cr ^= rev[k];
            int left = 2 * n + 1, right = 2 * n + 2;
            if ((swap[k] ^ cr) == 1) {
                left = 2 * n + 2;
                right = 2 * n + 1;
            }
            int m = 1 << (k - 1);
            if (i < m)
                update(left, k - 1, cr, i, v);
            else
                update(right, k - 1, cr, i - m, v);
            tree[n] = tree[2 * n + 1] + tree[2 * n + 2];
        }

        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int k = s.nextInt(), q = s.nextInt();
            int n = 1 << k;
            a = new int[n];
            for (int i = 0; i < n; i++)
                a[i] = s.nextInt();
            tree = new long[2 * n];
            build(0, 0, n - 1);
            rev = new int[k + 1];
            swap = new int[k + 1];
            while (q-- > 0) {
                int t = s.nextInt();
                if (t == 1)
                    update(0, k, 0, s.nextInt() - 1, s.nextInt());
                else if (t == 2)
                    rev[s.nextInt()] ^= 1;
                else if (t == 3)
                    swap[s.nextInt() + 1] ^= 1;
                else
                    w.println(query(0, k, 0, s.nextInt() - 1, s.nextInt() - 1));
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

