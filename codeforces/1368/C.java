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
        CEvenPicture solver = new CEvenPicture();
        solver.solve(1, in, out);
        out.close();
    }

    static class CEvenPicture {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt();
            if (n == 1) {
                w.println(7);
                w.println(-1 + " " + 0);
                w.println(0 + " " + 0);
                w.println(1 + " " + 0);
                w.println(-1 + " " + 1);
                w.println(0 + " " + 1);
                w.println(1 + " " + -1);
                w.println(0 + " " + -1);
                return;
            }
            ArrayList<Integer> x = new ArrayList<>();
            ArrayList<Integer> y = new ArrayList<>();

            if (n % 2 == 0) {
                for (int i = -2; i <= 2 * n; i++) {
                    x.add(i);
                    y.add(0);
                    x.add(i);
                    y.add(4);
                }
                for (int i = 1; i <= 3; i++) {
                    x.add(-2);
                    y.add(i);
                    x.add(2 * n);
                    y.add(i);
                }
                for (int i = 0; i < n; i++) {
                    x.add(2 * i);
                    y.add(-1);
                    x.add(2 * i);
                    y.add(-2);
                    x.add(2 * i);
                    y.add(1);
                    x.add(2 * i);
                    y.add(2);
                }
                for (int i = 0; i < n / 2; i++) {
                    x.add(4 * i + 1);
                    y.add(2);
                    x.add(4 * i + 1);
                    y.add(-2);
                }
            } else {
                for (int i = -2; i <= 2 * n - 1; i++) {
                    x.add(i);
                    y.add(0);
                }
                for (int i = 2; i <= 3; i++) {
                    x.add(2 * n - 2);
                    y.add(i);
                }
                for (int i = 1; i <= 3; i++) {
                    x.add(-2);
                    y.add(i);
                }
                for (int i = -2; i <= 2 * n - 2; i++) {
                    x.add(i);
                    y.add(4);
                }
                for (int i = 0; i < n; i++) {
                    x.add(2 * i);
                    y.add(1);
                    x.add(2 * i);
                    y.add(-1);
                    if (i < n - 1) {
                        x.add(2 * i);
                        y.add(2);
                        x.add(2 * i);
                        y.add(-2);
                    }
                }
                for (int i = 0; i < n / 2; i++) {
                    x.add(4 * i + 1);
                    y.add(2);
                    x.add(4 * i + 1);
                    y.add(-2);
                }
                x.add(2 * n - 1);
                y.add(-1);
            }

            w.println(x.size());
            for (int i = 0; i < x.size(); i++) {
                w.println(x.get(i) + " " + y.get(i));
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

