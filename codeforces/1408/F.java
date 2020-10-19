import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
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
        FTwoDifferent solver = new FTwoDifferent();
        solver.solve(1, in, out);
        out.close();
    }

    static class FTwoDifferent {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt();
            ArrayList<Pair> q = new ArrayList<>();

            int[] f = new int[14];
            ArrayList<Integer>[] a = new ArrayList[14];
            int msb = -1;
            int x = 0;
            for (int i = 13; i >= 0; i--) {
                a[i] = new ArrayList<>();
                if (((n >> i) & 1) == 1) {
                    f[i] = 1;
                    msb = Math.max(i, msb);
                    int v = (1 << i);
                    for (int j = 1; j <= i; j++) {
                        for (int k = 0; k < v; k += (1 << j)) {
                            for (int l = 0; l < 1 << (j - 1); l++) {
                                q.add(new Pair(x + k + l, x + k + l + (1 << (j - 1))));
                            }
                        }
                    }
                    for (int j = 0; j < (1 << i); j++)
                        a[i].add(x + j);
                    x += 1 << i;
                }
            }

            int c = 0;
            for (int i = 0; i < msb; i++) {
                if (a[i].size() == 0)
                    continue;
                if (a[i].size() == (1 << i)) {
                    if (i == msb - 1)
                        continue;
                    for (int j = 0; j < (1 << i); j++) {
                        a[i + 1].add(a[i].get(j));
                        a[i + 1].add(c);
                        q.add(new Pair(c, a[i].get(j)));
                        c++;
                    }
                } else {
                    for (int j = 0; j < (1 << i); j++) {
                        a[i + 1].add(a[i].get(j));
                        a[i + 1].add(a[i].get(j + (1 << i)));
                        q.add(new Pair(a[i].get(j), a[i].get(j + (1 << i))));
                    }
                }
            }

            w.println(q.size());
            for (Pair p : q) {
                w.println((p.i + 1) + " " + (p.j + 1));
            }
        }

        class Pair {
            int i;
            int j;

            Pair(int i, int j) {
                this.i = i;
                this.j = j;
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

