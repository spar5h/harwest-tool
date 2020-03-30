import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
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
        BPINCodes solver = new BPINCodes();
        solver.solve(1, in, out);
        out.close();
    }

    static class BPINCodes {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int t = s.nextInt();
            while (t-- > 0) {
                int n = s.nextInt();
                HashMap<String, Integer> hm = new HashMap<>();
                int cnt = 0;
                ArrayList<String> res = new ArrayList<>();
                char[][] c = new char[n][];
                for (int i = 0; i < n; i++) {
                    c[i] = s.next().toCharArray();
                    if (!hm.containsKey(String.valueOf(c[i])))
                        hm.put(String.valueOf(c[i]), 0);
                    hm.put(String.valueOf(c[i]), hm.get(String.valueOf(c[i])) + 1);
                }
                for (int i = 0; i < n; i++) {
                    if (hm.get(String.valueOf(c[i])) > 1) {
                        cnt++;
                        outer:
                        for (int j = 0; j < 4; j++) {
                            for (int k = 0; k < 10; k++) {
                                char temp = c[i][j];
                                c[i][j] = (char) ('0' + k);
                                if (!hm.containsKey(String.valueOf(c[i]))) {
                                    c[i][j] = temp;
                                    hm.put(String.valueOf(c[i]), hm.get(String.valueOf(c[i])) - 1);
                                    c[i][j] = (char) ('0' + k);
                                    hm.put(String.valueOf(c[i]), 1);
                                    break outer;
                                }
                                c[i][j] = temp;
                            }
                        }
                    }
                }
                w.println(cnt);
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < 4; j++)
                        w.print(c[i][j]);
                    w.println();
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

