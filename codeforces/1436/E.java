import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.io.IOException;
import java.util.TreeSet;
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
        EComplicatedComputations solver = new EComplicatedComputations();
        solver.solve(1, in, out);
        out.close();
    }

    static class EComplicatedComputations {
        int[] tree;

        void update(int n, int nL, int nR, int idx, int v) {
            if (nR < idx || idx < nL)
                return;
            if (nL == idx && nR == idx) {
                tree[n] = v;
                return;
            }
            update(2 * n + 1, nL, (nL + nR) / 2, idx, v);
            update(2 * n + 2, (nL + nR) / 2 + 1, nR, idx, v);
            tree[n] = Math.min(tree[2 * n + 1], tree[2 * n + 2]);
        }

        int query(int n, int nL, int nR, int l, int r) {
            if (nR < l || r < nL)
                return Integer.MAX_VALUE;
            if (l <= nL && nR <= r)
                return tree[n];
            return Math.min(query(2 * n + 1, nL, (nL + nR) / 2, l, r), query(2 * n + 2, (nL + nR) / 2 + 1, nR, l, r));
        }

        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt();
            int[] a = new int[n];
            for (int i = 0; i < n; i++)
                a[i] = s.nextInt() - 1;
            int[] c = new int[n];
            int[] other = new int[n];
            Arrays.fill(c, -1);
            for (int i = 0; i < n; i++) {
                if (c[a[i]] != -1)
                    other[i] = c[a[i]] + 1;
                else
                    other[i] = 0;
                c[a[i]] = i;
            }
            ArrayList<Integer>[] query = new ArrayList[n];
            for (int i = 0; i < n; i++)
                query[i] = new ArrayList<>();
            for (int i = 1; i < n; i++) {
                if (other[i] <= i - 1)
                    query[i - 1].add(other[i]);
            }
            for (int i = 0; i < n; i++) {
                if (c[a[i]] == -1)
                    continue;
                if (c[a[i]] + 1 < n)
                    query[n - 1].add(c[a[i]] + 1);
            }
            query[n - 1].add(0);
            TreeSet<Integer> ts = new TreeSet<>();
            for (int i = 0; i <= n + 1; i++)
                ts.add(i);
            for (int i = 0; i < n; i++) {
                if (a[i] != 0)
                    ts.remove(0);
                else
                    ts.remove(1);
            }
            tree = new int[4 * n];
            Arrays.fill(tree, -1);
            for (int r = 0; r < n; r++) {
                update(0, 0, n - 1, a[r], r);
                for (int l : query[r]) {
                    int ans = 0;
                    int left = 0, right = n - 1;
                    while (left <= right) {
                        int mid = (left + right) / 2;
                        if (query(0, 0, n - 1, 0, mid) >= l) {
                            ans = mid + 1;
                            left = mid + 1;
                        } else
                            right = mid - 1;
                    }
                    ts.remove(ans);
                }
            }
            w.println(ts.first() + 1);
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

