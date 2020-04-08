import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
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
        ENewYearParties solver = new ENewYearParties();
        solver.solve(1, in, out);
        out.close();
    }

    static class ENewYearParties {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt();
            int[] f = new int[n + 1];
            for (int i = 0; i < n; i++)
                f[s.nextInt()]++;
            int[] c = new int[n + 2];
            for (int i = 1; i <= n; i++) {
                int count = f[i];
                if (count > 0 && c[i - 1] == 0) {
                    c[i - 1] = 1;
                    count--;
                }
                if (count > 0 && c[i] == 0) {
                    c[i] = 1;
                    count--;
                }
                if (count > 0 && c[i + 1] == 0) {
                    c[i + 1] = 1;
                    count--;
                }
            }
            int max = 0;
            for (int i = 0; i <= n + 1; i++)
                max += c[i];
            Arrays.fill(c, 0);
            int min = 0;
            for (int i = 1; i <= n; i++) {
                if (f[i] == 0)
                    continue;
                if (c[i - 1] == 1 || c[i] == 1)
                    continue;
                c[i + 1] = 1;
                min++;
            }
            w.println(min + " " + max);
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

