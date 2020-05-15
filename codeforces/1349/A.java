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
        AOracAndLCM solver = new AOracAndLCM();
        solver.solve(1, in, out);
        out.close();
    }

    static class AOracAndLCM {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt();
            int[] f = new int[(int) 2e5 + 1];
            for (int i = 0; i < n; i++)
                f[s.nextInt()]++;
            int[] prime = new int[(int) 2e5 + 1];
            Arrays.fill(prime, 1);
            prime[0] = 0;
            prime[1] = 0;
            int[] count = new int[(int) 2e5 + 1];
            for (int i = 2; i <= (int) 2e5; i++) {
                count[i] += f[i];
                for (int j = 2; (long) i * j <= (int) 2e5; j++) {
                    prime[i * j] = 0;
                    count[i] += f[i * j];
                }
            }
            long res = 1;
            for (int i = 2; i <= (int) 2e5; i++) {
                if (prime[i] == 0)
                    continue;
                int best = 1;
                for (long j = i; j <= (int) 2e5; j *= i) {
                    int k = (int) j;
                    if (count[k] >= n - 1)
                        best = k;
                    else
                        break;
                }
                res *= best;
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

