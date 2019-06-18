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
        CLittleElephantAndLCM solver = new CLittleElephantAndLCM();
        solver.solve(1, in, out);
        out.close();
    }

    static class CLittleElephantAndLCM {
        long mod = (long) 1e9 + 7;

        long modExp(long x, long y) {
            if (y == 0)
                return 1;
            long hf = modExp(x, y / 2);
            return hf * hf % mod * ((y & 1) == 1 ? x : 1) % mod;
        }

        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int m = (int) 1e5;
            int[] pre = new int[m + 1];
            int n = s.nextInt();
            for (int i = 1; i <= n; i++)
                pre[s.nextInt()]++;
            for (int i = 1; i <= m; i++)
                pre[i] += pre[i - 1];
            ArrayList<Integer>[] div = new ArrayList[m + 1];
            for (int i = 1; i <= m; i++)
                div[i] = new ArrayList<>();
            for (int i = 1; i <= m; i++)
                for (int j = 1; (long) i * j <= m; j++)
                    div[i * j].add(i);
            long res = 0;
            for (int i = 1; i <= m; i++) {
                long add = 1;
                int curr = 1;
                for (int j = 0; j < div[i].size() - 1; j++) {
                    add = add * modExp(curr, pre[div[i].get(j + 1) - 1] - pre[div[i].get(j) - 1]) % mod;
                    curr++;
                }
                add = add * ((modExp(curr, pre[m] - pre[div[i].get(div[i].size() - 1) - 1]) - modExp(curr - 1, pre[m] - pre[div[i].get(div[i].size() - 1) - 1]) + mod) % mod) % mod;
                res = (res + add) % mod;
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

