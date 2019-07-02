import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
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
        DExtraElement solver = new DExtraElement();
        solver.solve(1, in, out);
        out.close();
    }

    static class DExtraElement {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt();
            Pair[] a = new Pair[n];
            for (int i = 0; i < n; i++)
                a[i] = new Pair(i, s.nextLong());
            Arrays.sort(a, new Comp());
            if (n <= 3) {
                w.println(a[0].i + 1);
                return;
            }
            //answer = 1
            boolean check = true;
            for (int i = 2; i < n; i++)
                if (a[i].w - a[i - 1].w != a[2].w - a[1].w)
                    check = false;
            if (check) {
                w.println(a[0].i + 1);
                return;
            }
            //answer = 2
            check = true;
            for (int i = 3; i < n; i++)
                if (a[i].w - a[i - 1].w != a[2].w - a[0].w)
                    check = false;
            if (check) {
                w.println(a[1].i + 1);
                return;
            }
            //answer = n - 1
            check = true;
            for (int i = 1; i < n - 2; i++)
                if (a[i].w - a[i - 1].w != a[n - 1].w - a[n - 3].w)
                    check = false;
            if (check) {
                w.println(a[n - 2].i + 1);
                return;
            }
            //answer = n
            check = true;
            for (int i = 1; i < n - 1; i++)
                if (a[i].w - a[i - 1].w != a[1].w - a[0].w)
                    check = false;
            if (check) {
                w.println(a[n - 1].i + 1);
                return;
            }
            int pre = 0;
            while (pre + 1 < n && a[pre + 1].w - a[pre].w == a[1].w - a[0].w)
                pre++;
            int suf = n - 1;
            while (suf - 1 >= 0 & a[suf].w - a[suf - 1].w == a[n - 1].w - a[n - 2].w)
                suf--;
            if (suf - pre == 2 && a[1].w - a[0].w == a[n - 1].w - a[n - 2].w && a[1].w - a[0].w == a[suf].w - a[pre].w)
                w.println(a[pre + 1].i + 1);
            else
                w.println(-1);
        }

        class Pair {
            int i;
            long w;

            Pair(int i, long w) {
                this.i = i;
                this.w = w;
            }

        }

        class Comp implements Comparator<Pair> {
            public int compare(Pair a, Pair b) {
                if (a.w < b.w)
                    return -1;
                if (a.w > b.w)
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

        public long nextLong() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = read();
            }
            long res = 0;
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

