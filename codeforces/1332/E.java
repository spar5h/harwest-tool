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
        EHeightAllTheSame solver = new EHeightAllTheSame();
        solver.solve(1, in, out);
        out.close();
    }

    static class EHeightAllTheSame {
        long mod = 998244353;
        long[][] d;

        long modExp(long x, long y) {
            if (y == 0)
                return 1 % mod;
            long ret = modExp(x, y >> 1);
            ret = ret * ret % mod;
            if ((y & 1) == 1)
                ret = ret * (x % mod) % mod;
            return ret;
        }

        long[][] multiply(long[][] a, long[][] b) {
            long[][] c = new long[2][2];
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++)
                    for (int k = 0; k < 2; k++)
                        c[i][j] = (c[i][j] + a[i][k] * b[k][j] % mod) % mod;
            }
            return c;
        }

        long[][] matExp(long[][] a, long y) {
            if (y == 1)
                return a;
            long[][] ret = matExp(a, y / 2);
            ret = multiply(ret, ret);
            if ((y & 1) == 1)
                ret = multiply(ret, d);
            return ret;
        }

        long value(long y, long p0, long p1) {
            long same = (p0 * p0 % mod + p1 * p1 % mod) % mod;
            long diff = 2 * p0 * p1 % mod;
            d = new long[][]{{same, diff}, {diff, same}};
            long[][] mat = matExp(d, y / 2);
        /*
        for(int i = 0; i < 2; i++) {
            for(int j = 0; j < 2; j++)
                System.out.print(mat[i][j] + " ");
            System.out.println();
        }
        */
            return mat[0][0];
        }

        public void solve(int testNumber, InputReader s, PrintWriter w) {
            long mod = 998244353;
            long n = s.nextLong(), m = s.nextLong(), l = s.nextLong(), r = s.nextLong();
            if ((n * m) % 2 == 1) {
                w.println(modExp(r - l + 1, n * m));
                return;
            }
            long p0 = (r - l + 1) / 2;
            long p1 = (r - l + 1 + 1) / 2;
            w.println(value(n * m, p0, p1));
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

