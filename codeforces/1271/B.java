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
        BBlocks solver = new BBlocks();
        solver.solve(1, in, out);
        out.close();
    }

    static class BBlocks {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt();
            String str = s.next();
            char[] a = str.toCharArray();
            ArrayList<Integer> res = new ArrayList<>();
            int i = 0;
            while (i < n - 1) {
                if (a[i] == 'W' && a[i + 1] == 'W') {
                    res.add(i);
                    a[i] = 'B';
                    a[i + 1] = 'B';
                    i += 2;
                } else if (a[i] == 'W' && a[i + 1] == 'B') {
                    res.add(i);
                    a[i] = 'B';
                    a[i + 1] = 'W';
                    i += 1;
                } else
                    i++;
            }
            boolean check = true;
            for (int j = 0; j < n; j++)
                if (a[j] == 'W')
                    check = false;
            if (check) {
                w.println(res.size());
                for (int j : res)
                    w.print((j + 1) + " ");
                w.println();
                return;
            }
            res.clear();
            a = str.toCharArray();
            i = 0;
            while (i < n - 1) {
                if (a[i] == 'B' && a[i + 1] == 'B') {
                    res.add(i);
                    a[i] = 'W';
                    a[i + 1] = 'W';
                    i += 2;
                } else if (a[i] == 'B' && a[i + 1] == 'W') {
                    res.add(i);
                    a[i] = 'W';
                    a[i + 1] = 'B';
                    i += 1;
                } else
                    i++;
            }
            check = true;
            for (int j = 0; j < n; j++)
                if (a[j] == 'B')
                    check = false;
            if (check) {
                w.println(res.size());
                for (int j : res)
                    w.print((j + 1) + " ");
                w.println();
                return;
            }
            w.println(-1);
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

