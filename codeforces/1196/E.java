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
        EConnectedComponentOnAChessboard solver = new EConnectedComponentOnAChessboard();
        solver.solve(1, in, out);
        out.close();
    }

    static class EConnectedComponentOnAChessboard {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int q = s.nextInt();
            while (q-- > 0) {
                int[] f = new int[2];
                f[1] = s.nextInt();
                f[0] = s.nextInt();

                int offR = 3, offC = 1;
                if (f[1] > f[0]) {
                    int temp = f[1];
                    f[1] = f[0];
                    f[0] = temp;
                    offC++;
                }

                ArrayList<Pair> list = new ArrayList<>();
                int j = 0;
                while (f[1] > 0) {
                    list.add(new Pair(offR, offC + j));
                    list.add(new Pair(offR, offC + j + 1));
                    f[0]--;
                    f[1]--;
                    j += 2;
                }
                if (f[0] > 0) {
                    list.add(new Pair(offR, offC + j));
                    f[0]--;
                    j++;
                }

                int lim = list.size() / 2;

                for (j = 0; j < lim && f[0] > 0; j++) {
                    list.add(new Pair(offR - 1, offC + 1 + 2 * j));
                    f[0]--;
                    if (f[0] == 0)
                        break;
                    list.add(new Pair(offR + 1, offC + 1 + 2 * j));
                    f[0]--;
                }

                if (f[0] == 0) {
                    w.println("YES");
                    for (Pair p : list) {
                        w.println(p.i + " " + p.j);
                    }
                } else
                    w.println("NO");
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

