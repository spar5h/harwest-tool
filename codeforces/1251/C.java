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
        CMinimizeTheInteger solver = new CMinimizeTheInteger();
        solver.solve(1, in, out);
        out.close();
    }

    static class CMinimizeTheInteger {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int t = s.nextInt();
            while (t-- > 0) {
                char[] a = s.next().toCharArray();
                ArrayList<Integer> x = new ArrayList<>();
                ArrayList<Integer> y = new ArrayList<>();
                int n = a.length;
                for (int i = 0; i < a.length; i++) {
                    if ((a[i] - '0') % 2 == 0) {
                        x.add(a[i] - '0');
                    } else
                        y.add(a[i] - '0');
                }
                int i = 0, j = 0;
                ArrayList<Integer> z = new ArrayList<>();
                while (i < x.size() && j < y.size()) {
                    if (x.get(i).intValue() < y.get(j).intValue()) {
                        z.add(x.get(i));
                        i++;
                    } else {
                        z.add(y.get(j));
                        j++;
                    }
                }
                while (i < x.size()) {
                    z.add(x.get(i));
                    i++;
                }
                while (j < y.size()) {
                    z.add(y.get(j));
                    j++;
                }
                for (int k = 0; k < z.size(); k++)
                    w.print(z.get(k).intValue());
                w.println();
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

