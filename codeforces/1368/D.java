import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.InputMismatchException;
import java.io.IOException;
import java.util.Queue;
import java.util.LinkedList;
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
        DANDORAndSquareSum solver = new DANDORAndSquareSum();
        solver.solve(1, in, out);
        out.close();
    }

    static class DANDORAndSquareSum {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt();
            int[] a = new int[n];
            for (int i = 0; i < n; i++)
                a[i] = s.nextInt();

            Queue<Integer>[] q = new LinkedList[20];
            for (int j = 0; j < 20; j++)
                q[j] = new LinkedList<>();
            int[][] dead = new int[20][n];

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < 20; j++) {
                    if (((a[i] >> j) & 1) == 1)
                        q[j].add(i);
                }
            }

            for (int i = 0; i < n; i++) {
                for (int j = 0; j < 20; j++)
                    dead[j][i] = 1;

                for (int j = 0; j < 20; j++) {

                    if (((a[i] >> j) & 1) == 1)
                        continue;

                    while (!q[j].isEmpty() && dead[j][q[j].peek()] == 1)
                        q[j].poll();

                    if (q[j].isEmpty())
                        continue;

                    int idx = q[j].poll();

                    int v1 = a[i] & a[idx];
                    int v2 = a[i] | a[idx];

                    for (int k = 0; k < 20; k++) {
                        if (((a[idx] >> k) & 1) == 1 && ((v1 >> k) & 1) == 0) {
                            dead[k][idx] = 1;
                        }
                    }

                    a[i] = v2;
                    a[idx] = v1;
                }
            }

            long res = 0;
            for (int i = 0; i < n; i++) {
                res += (long) a[i] * a[i];
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

