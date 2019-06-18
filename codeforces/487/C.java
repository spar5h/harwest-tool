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
        CPrefixProductSequence solver = new CPrefixProductSequence();
        solver.solve(1, in, out);
        out.close();
    }

    static class CPrefixProductSequence {
        boolean isPrime(int n) {
            for (int i = 2; (long) i * i <= n; i++)
                if (n % i == 0)
                    return false;
            return true;
        }

        long modExp(long x, long y, long n) {
            if (y == 0) return 1;
            long hf = modExp(x, y / 2, n);
            return hf * hf % n * ((y & 1) == 1 ? x : 1) % n;
        }

        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt();
            if (n == 1) {
                w.println("YES");
                w.println(1);
                return;
            }
            if (n == 4) {
                w.println("YES");
                w.println(1);
                w.println(3);
                w.println(2);
                w.println(4);
                return;
            }
            if (!isPrime(n)) {
                w.println("NO");
                return;
            }
            w.println("YES");
            w.println(1);
            int a = 1;
            while (a != n - 1) {
                w.println(((a + 1) * modExp(a, n - 2, n) % n));
                a++;
            }
            w.println(n);
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

