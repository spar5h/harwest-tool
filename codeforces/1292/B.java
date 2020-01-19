import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
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
        BAromasSearch solver = new BAromasSearch();
        solver.solve(1, in, out);
        out.close();
    }

    static class BAromasSearch {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            long x0 = s.nextLong(), y0 = s.nextLong();
            long ax = s.nextLong(), ay = s.nextLong();
            long bx = s.nextLong(), by = s.nextLong();
            ArrayList<Pair> list = new ArrayList<>();
            BigInteger x = BigInteger.valueOf(x0);
            BigInteger y = BigInteger.valueOf(y0);
            BigInteger max = BigInteger.valueOf((long) 2e16 + 5);
            while (x.compareTo(max) < 0 && y.compareTo(max) < 0) {
                list.add(new Pair(x.longValue(), y.longValue()));
                x = x.multiply(BigInteger.valueOf(ax));
                x = x.add(BigInteger.valueOf(bx));
                y = y.multiply(BigInteger.valueOf(ay));
                y = y.add(BigInteger.valueOf(by));
            }
            long xs = s.nextLong(), ys = s.nextLong(), time = s.nextLong();
            long res = 0;
            for (int i = 0; i < list.size(); i++) {
                Pair p = list.get(i);
                BigInteger cost = BigInteger.valueOf(Math.abs(p.x - xs) + Math.abs(p.y - ys));
                if (cost.compareTo(BigInteger.valueOf(time)) > 0)
                    continue;
                long can = 1;
                int j = i - 1;
                while (j >= 0) {
                    cost = cost.add(BigInteger.valueOf(Math.abs(list.get(j + 1).x - list.get(j).x) + Math.abs(list.get(j + 1).y - list.get(j).y)));
                    if (cost.compareTo(BigInteger.valueOf(time)) > 0)
                        break;
                    can++;
                    j--;
                }
                res = Math.max(can, res);
                cost = BigInteger.valueOf(Math.abs(p.x - xs) + Math.abs(p.y - ys));
                can = 1;
                j = i + 1;
                while (j < list.size()) {
                    cost = cost.add(BigInteger.valueOf(Math.abs(list.get(j - 1).x - list.get(j).x) + Math.abs(list.get(j - 1).y - list.get(j).y)));
                    if (cost.compareTo(BigInteger.valueOf(time)) > 0)
                        break;
                    can++;
                    j++;
                }
                res = Math.max(can, res);
            }
            w.println(res);
        }

        class Pair {
            long x;
            long y;

            Pair(long x, long y) {
                this.x = x;
                this.y = y;
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

        public long nextLong() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = read();
            }
            long res = 0;
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

