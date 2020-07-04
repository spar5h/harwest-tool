import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.io.IOException;
import java.util.Comparator;
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
        EInversionSwapSort solver = new EInversionSwapSort();
        solver.solve(1, in, out);
        out.close();
    }

    static class EInversionSwapSort {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt();
            int[] a = new int[n];
            for (int i = 0; i < n; i++)
                a[i] = s.nextInt();
            Pair[] p = new Pair[n];
            for (int j = 0; j < n; j++)
                p[j] = new Pair(j, a[j]);
            Arrays.sort(p, new Comparator<Pair>() {

                public int compare(Pair p1, Pair p2) {
                    if (p1.w < p2.w)
                        return -1;
                    if (p1.w > p2.w)
                        return 1;
                    if (p1.i < p2.i)
                        return -1;
                    if (p1.i > p2.i)
                        return 1;
                    return 0;
                }
            });
            ArrayList<Pair> res = new ArrayList<>();
            for (int i = n - 1; i >= 0; i--) {
                int j = 0;
                while (j < n && i != p[j].i)
                    j++;
                j++;
                while (j < n) {
                    if (p[j].i >= i) {
                        j++;
                        continue;
                    }
                    int temp = a[i];
                    a[i] = a[p[j].i];
                    a[p[j].i] = temp;
                    res.add(new Pair(p[j].i, i));
                    j++;
                }
            }
            w.println(res.size());
            for (Pair pair : res) {
                w.println((pair.i + 1) + " " + (pair.w + 1));
            }
        }

        class Pair {
            int i;
            int w;

            Pair(int i, int w) {
                this.i = i;
                this.w = w;
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

