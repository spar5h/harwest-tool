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
        CZeroOne solver = new CZeroOne();
        solver.solve(1, in, out);
        out.close();
    }

    static class CZeroOne {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            char[] a = s.next().toCharArray();
            int n = a.length;
            int[] f = new int[2];
            int[] check = new int[4];
            for (int i = 0; i < n; i++)
                if (a[i] != '?')
                    f[a[i] - '0']++;
            if (f[1] < (n + 1) / 2)
                check[0] = 1;
            if (f[1] < (n + 1) / 2 && f[0] < (n / 2)) {
                if (a[n - 1] != '0')
                    check[1] = 1;
                if (a[n - 1] != '1')
                    check[2] = 1;
            }
            if (f[0] < n / 2)
                check[3] = 1;
            if (f[1] == (n + 1) / 2 && f[0] <= n / 2) {
                int i0 = (a[n - 1] != '1') ? 0 : 1;
                check[2 * (1 - i0) + i0] = 1;
            }
            if (f[1] <= (n + 1) / 2 && f[0] == n / 2) {
                int i0 = (a[n - 1] != '0') ? 1 : 0;
                check[2 * (1 - i0) + i0] = 1;
            }

            for (int i = 0; i < 4; i++) {
                if (check[i] == 1) {
                    String str = Integer.toBinaryString(i);
                    while (str.length() != 2)
                        str = "0" + str;
                    w.println(str);
                }
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

        public String nextString() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }
            StringBuilder res = new StringBuilder();
            do {
                if (Character.isValidCodePoint(c)) {
                    res.appendCodePoint(c);
                }
                c = read();
            } while (!isSpaceChar(c));
            return res.toString();
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

        public String next() {
            return nextString();
        }

        public interface SpaceCharFilter {
            public boolean isSpaceChar(int ch);

        }

    }
}

