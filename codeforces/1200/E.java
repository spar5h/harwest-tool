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
        ECompressWords solver = new ECompressWords();
        solver.solve(1, in, out);
        out.close();
    }

    static class ECompressWords {
        int getVal(char c) {
            if ('a' <= c && c <= 'z')
                return c - 'a';
            if ('A' <= c && c <= 'Z')
                return c - 'A' + 26;
            return c - '0' + 52;
        }

        public void solve(int testNumber, InputReader s, PrintWriter w) {
            long[] preHash1, preHash2, sufHash1, sufHash2, m1Pow, m2Pow;

            int n = s.nextInt();
            int k = (int) 1e6 + 5;
            StringBuffer res = new StringBuffer("");
            long m1 = 1000000093, m2 = 1000050001, p = 62;
            m1Pow = new long[k];
            m1Pow[0] = 1;
            m2Pow = new long[k];
            m2Pow[0] = 1;
            for (int i = 1; i < k; i++) {
                m1Pow[i] = m1Pow[i - 1] * p % m1;
                m2Pow[i] = m2Pow[i - 1] * p % m2;
            }
            String[] a = new String[n];
            for (int i = 0; i < n; i++)
                a[i] = s.next();
            for (int j = 0; j < a[0].length(); j++)
                res.append(a[0].charAt(j));
            for (int i = 1; i < n; i++) {
                long suf1 = 0, pre1 = 0, suf2 = 0, pre2 = 0;
                int ex = -1;
                for (int j = 0; j < Math.min(a[i].length(), res.length()); j++) {
                    suf1 = (suf1 + getVal(res.charAt(res.length() - 1 - j)) * m1Pow[j] % m1) % m1;
                    pre1 = (pre1 * p % m1 + getVal(a[i].charAt(j))) % m1;
                    suf2 = (suf2 + getVal(res.charAt(res.length() - 1 - j)) * m2Pow[j] % m2) % m2;
                    pre2 = (pre2 * p % m2 + getVal(a[i].charAt(j))) % m2;
                    //w.println(i + " " + j + " :: " + suf + " " + pre);
                    if (suf1 == pre1 && suf2 == pre2)
                        ex = j;
                }
                for (int j = ex + 1; j < a[i].length(); j++)
                    res.append(a[i].charAt(j));
            }
            w.println(res.toString());
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

