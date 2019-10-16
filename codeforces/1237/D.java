import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.io.IOException;
import java.util.Comparator;
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
        DBalancedPlaylist solver = new DBalancedPlaylist();
        solver.solve(1, in, out);
        out.close();
    }

    static class DBalancedPlaylist {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt();
            int[] a = new int[n];
            Pair[] p = new Pair[3 * n];
            for (int i = 0; i < n; i++) {
                a[i] = s.nextInt();
                for (int j = 0; j < 3; j++)
                    p[j * n + i] = new Pair(j * n + i, a[i]);
            }
            Arrays.sort(p, new Comp());
            int l = 0, r = 0;
            TreeSet<Integer> ts = new TreeSet<>();
            int[] next = new int[3 * n];
            while (r < 3 * n) {
                while (l < 3 * n && p[l].w < p[r].w / 2 + p[r].w % 2) {
                    ts.add(p[l].i);
                    l++;
                }
                if (ts.higher(p[r].i) != null)
                    next[p[r].i] = ts.higher(p[r].i);
                else
                    next[p[r].i] = Integer.MAX_VALUE;
                r++;
            }
            ts.clear();
            ;
            r = 3 * n - 1;
            while (r >= 0) {
                if (ts.higher(p[r].i) != null) {
                    next[p[r].i] = Math.min(next[ts.higher(p[r].i)], next[p[r].i]);
                }
                ts.add(p[r].i);
                r--;
            }
            for (int i = 0; i < n; i++)
                if (next[i] == Integer.MAX_VALUE)
                    w.print(-1 + " ");
                else
                    w.print(next[i] - i + " ");
            w.println();
        }

        class Pair {
            int i;
            int w;

            Pair(int i, int w) {
                this.i = i;
                this.w = w;
            }

        }

        class Comp implements Comparator<Pair> {
            public int compare(Pair x, Pair y) {
                if (x.w < y.w)
                    return -1;
                if (x.w > y.w)
                    return 1;
                if (x.i < y.i)
                    return -1;
                if (x.i > y.i)
                    return 1;
                return 0;
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

