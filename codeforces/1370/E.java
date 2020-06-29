import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
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
        EBinarySubsequenceRotation solver = new EBinarySubsequenceRotation();
        solver.solve(1, in, out);
        out.close();
    }

    static class EBinarySubsequenceRotation {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt();
            char[] a = s.next().toCharArray();
            char[] b = s.next().toCharArray();
            int ca = 0, cb = 0;
            for (int i = 0; i < n; i++) {
                ca += a[i] - '0';
                cb += b[i] - '0';
            }
            if (ca != cb) {
                w.println(-1);
                return;
            }
            ArrayList<Integer> c = new ArrayList<>();
            TreeSet<Integer>[] ts = new TreeSet[2];
            ts[0] = new TreeSet<>();
            ts[1] = new TreeSet<>();
            for (int i = 0; i < n; i++) {
                if (a[i] != b[i]) {
                    c.add(a[i] - '0');
                    ts[a[i] - '0'].add(i);
                }
            }
            int res = 0;
            while (!ts[0].isEmpty()) {
                int p = 0;
                if (ts[0].first() > ts[1].first())
                    p = 1;
                int i = -1;
                while (true) {
                    if (ts[p].higher(i) == null)
                        break;
                    int i1 = ts[p].higher(i);
                    if (ts[p ^ 1].higher(i1) == null)
                        break;
                    int i2 = ts[p ^ 1].higher(i1);
                    ts[p].remove(i1);
                    ts[p ^ 1].remove(i2);
                    i = i2;
                }
                res++;
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

        public String nextString() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }
            StringBuilder res = new StringBuilder();
            do {
                if (Character.isValidCodePoint(c)) {
                    res.appendCodePoint(c);
                }
                c = read();
            } while (!isSpaceChar(c));
            return res.toString();
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

        public String next() {
            return nextString();
        }

        public interface SpaceCharFilter {
            public boolean isSpaceChar(int ch);

        }

    }
}

