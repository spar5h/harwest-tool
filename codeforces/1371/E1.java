import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
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
        E1AsterismEasyVersion solver = new E1AsterismEasyVersion();
        solver.solve(1, in, out);
        out.close();
    }

    static class E1AsterismEasyVersion {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt(), p = s.nextInt();
            Integer[] A = new Integer[n];
            for (int i = 0; i < n; i++)
                A[i] = s.nextInt();
            Arrays.sort(A);
            int[] a = new int[n];
            for (int i = 0; i < n; i++)
                a[i] = A[i];

            ArrayList<Integer> res = new ArrayList<>();

            outer:
            for (int x = 1; x <= 2000; x++) {
                for (int i = 0; i < n; i++) {
                    if (a[i] > x + i)
                        continue outer;
                }
                boolean check = true;
                for (int i = n - 1; i >= 0; i--) {
                    int c = Math.max(0, a[i] - x);
                    int options = i + 1 - c;
                    if (options % p == 0) {
                        check = false;
                    }
                }
                if (check) {
                    res.add(x);
                }
            }
            w.println(res.size());
            for (int i : res)
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

