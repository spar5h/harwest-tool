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
        CPerfectTriples solver = new CPerfectTriples();
        solver.solve(1, in, out);
        out.close();
    }

    static class CPerfectTriples {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int t = s.nextInt();
            while (t-- > 0) {
                long n = s.nextLong() - 1;
                long x = n / 3;
                long[] f = new long[3];
                int msb = 0;
                long c = 0;
                for (int i = 0; i <= 61; i += 2) {
                    long add = 1L << i;
                    if (c + add <= x) {
                        c += add;
                        msb += 2;
                    } else
                        break;
                }
                long temp = c;
                f[0] = 1L << msb;
                for (int i = msb - 1; i >= 0; i--) {
                    long add = 1L << i;
                    if (c + add <= x) {
                        c += add;
                        f[0] += add;
                    }
                }
                int[] d = {0, 2, 3, 1};
                c = temp;
                f[1] = 1L << (msb + 1);
                for (int i = msb - 2; i >= 0; i -= 2) {
                    long add = 1L << i;
                    int k = 0;
                    for (int j = 0; j < 4; j++) {
                        if (c + add <= x) {
                            c += add;
                            k++;
                        }
                    }
                    f[1] += add * d[k];
                }
                f[2] = f[0] ^ f[1];
                w.println(f[(int) (n % 3)]);
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

