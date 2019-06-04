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
        DEhabAndTheExpectedXORProblem solver = new DEhabAndTheExpectedXORProblem();
        solver.solve(1, in, out);
        out.close();
    }

    static class DEhabAndTheExpectedXORProblem {
        public void solve(int testNumber, InputReader s, PrintWriter w) {

            int n = s.nextInt(), x = s.nextInt();

            if (x >= (1 << n))
                x = 0;

            int[] vis = new int[(1 << n)];
            vis[0] = 1;
            vis[x] = 1;

            ArrayList<Integer> a = new ArrayList<Integer>();
            int pre = 0;

            int j = 1;

            while (j < (1 << n)) {

                while (j < (1 << n) && vis[j] == 1)
                    j++;

                if (j == (1 << n))
                    break;

                a.add(pre ^ j);
                pre = j;
                vis[j] = 1;
                vis[j ^ x] = 1;
                j++;
            }

            w.println(a.size());

            for (int i : a)
                w.print(i + " ");

            w.println();
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

