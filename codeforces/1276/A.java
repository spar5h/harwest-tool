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
        AAsSimpleAsOneAndTwo solver = new AAsSimpleAsOneAndTwo();
        solver.solve(1, in, out);
        out.close();
    }

    static class AAsSimpleAsOneAndTwo {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int t = s.nextInt();
            while (t-- > 0) {
                char[] a = s.next().toCharArray();
                char[] s1 = "twone".toCharArray();
                int[] res = new int[a.length];
                for (int i = 0; i < a.length - 4; i++) {
                    boolean check = true;
                    for (int j = 0; j < 5; j++) {
                        if (a[i + j] != s1[j] || res[i + j] == 1)
                            check = false;
                    }
                    if (check) {
                        res[i + 2] = 1;
                        i = i + 2;
                    }
                }
                char[] s2 = "one".toCharArray();
                for (int i = 0; i < a.length - 2; i++) {
                    boolean check = true;
                    for (int j = 0; j < 3; j++) {
                        if (a[i + j] != s2[j] || res[i + j] == 1)
                            check = false;
                    }
                    if (check) {
                        res[i + 1] = 1;
                        i = i + 1;
                    }
                }
                char[] s3 = "two".toCharArray();
                for (int i = 0; i < a.length - 2; i++) {
                    boolean check = true;
                    for (int j = 0; j < 3; j++) {
                        if (a[i + j] != s3[j] || res[i + j] == 1)
                            check = false;
                    }
                    if (check) {
                        res[i + 1] = 1;
                        i = i + 1;
                    }
                }
                int count = 0;
                for (int i = 0; i < a.length; i++)
                    if (res[i] == 1)
                        count++;
                w.println(count);
                for (int i = 0; i < a.length; i++)
                    if (res[i] == 1)
                        w.print(i + 1 + " ");
                w.println();
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

