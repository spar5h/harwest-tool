import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.AbstractCollection;
import java.util.PriorityQueue;
import java.util.InputMismatchException;
import java.io.IOException;
import java.util.Comparator;
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
        DFishes solver = new DFishes();
        solver.solve(1, in, out);
        out.close();
    }

    static class DFishes {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt(), m = s.nextInt(), r = s.nextInt(), k = s.nextInt();
            int[] a = new int[n];
            for (int i = 0; i < (n + 1) / 2; i++) {
                a[i] = (i != 0) ? Math.min(a[i - 1] + 1, Math.min(r, n - r + 1)) : 1;
                a[n - 1 - i] = a[i];
            }
            int[] b = new int[m];
            for (int i = 0; i < (m + 1) / 2; i++) {
                b[i] = (i != 0) ? Math.min(b[i - 1] + 1, Math.min(r, m - r + 1)) : 1;
                b[m - 1 - i] = b[i];
            }
            int fn = 0, fm = 0;
            for (int i = 0; i < n; i++)
                fn = Math.max(a[i], fn);
            for (int i = 0; i < m; i++)
                fm = Math.max(b[i], fm);
            long[] fa = new long[fn + 1];
            long[] fb = new long[fm + 1];
            for (int i = 0; i < n; i++)
                fa[a[i]]++;
            for (int i = 0; i < m; i++)
                fb[b[i]]++;
            long res = 0;
            int[] curr = new int[fn + 1];
            Arrays.fill(curr, fm);
            PriorityQueue<Pair> pq = new PriorityQueue<>(new Comparator<Pair>() {

                public int compare(Pair o1, Pair o2) {
                    long v1 = (long) o1.r * o1.c;
                    long v2 = (long) o2.r * o2.c;
                    if (v1 > v2)
                        return -1;
                    if (v1 < v2)
                        return 1;
                    return 0;
                }
            });
            for (int i = 1; i <= fn; i++)
                pq.add(new Pair(i, curr[i]));
            while (!pq.isEmpty() && k > 0) {
                Pair p = pq.poll();
                curr[p.r]--;
                if (curr[p.r] > 0)
                    pq.add(new Pair(p.r, curr[p.r]));
                res += Math.min(k, fa[p.r] * fb[p.c]) * p.r * p.c;
                k -= Math.min(k, fa[p.r] * fb[p.c]);
            }
            w.println((double) res / ((long) (n - r + 1) * (m - r + 1)));
        }

        class Pair {
            int r;
            int c;

            Pair(int r, int c) {
                this.r = r;
                this.c = c;
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

