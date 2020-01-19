import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.io.IOException;
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
        ANEKOsMazeGame solver = new ANEKOsMazeGame();
        solver.solve(1, in, out);
        out.close();
    }

    static class ANEKOsMazeGame {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt(), q = s.nextInt();
            int[][] a = new int[2][n];
            int c = 0;
            while (q-- > 0) {
                int i = s.nextInt() - 1, j = s.nextInt() - 1;
                if (a[i][j] == 1) {
                    if (j - 1 >= 0 && a[i ^ 1][j - 1] == 1)
                        c--;
                    if (a[i ^ 1][j] == 1)
                        c--;
                    if (j + 1 < n && a[i ^ 1][j + 1] == 1)
                        c--;
                } else {
                    if (j - 1 >= 0 && a[i ^ 1][j - 1] == 1)
                        c++;
                    if (a[i ^ 1][j] == 1)
                        c++;
                    if (j + 1 < n && a[i ^ 1][j + 1] == 1)
                        c++;
                }
                a[i][j] ^= 1;
                if (c == 0)
                    w.println("Yes");
                else
                    w.println("No");
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

