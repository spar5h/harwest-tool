import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.AbstractMap;
import java.util.TreeMap;
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
        C2BalancedRemovalsHarder solver = new C2BalancedRemovalsHarder();
        solver.solve(1, in, out);
        out.close();
    }

    static class C2BalancedRemovalsHarder {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt();
            TreeMap<Integer, TreeMap<Integer, TreeMap<Integer, Integer>>> tm = new TreeMap<>();
            for (int i = 0; i < n; i++) {
                int x = s.nextInt(), y = s.nextInt(), z = s.nextInt();
                if (tm.get(x) == null)
                    tm.put(x, new TreeMap<>());
                if (tm.get(x).get(y) == null)
                    tm.get(x).put(y, new TreeMap<>());
                tm.get(x).get(y).put(z, i);
            }
            while (!tm.isEmpty()) {
                int x = tm.firstKey();
                int y = tm.get(x).firstKey();
                int z = tm.get(x).get(y).firstKey();
                int i = tm.get(x).get(y).get(z);
                tm.get(x).get(y).remove(z);
                int j = -1;
                if (tm.get(x).get(y).size() > 0) {
                    int z2 = tm.get(x).get(y).firstKey();
                    j = tm.get(x).get(y).get(z2);
                    tm.get(x).get(y).remove(z2);
                    if (tm.get(x).get(y).size() == 0)
                        tm.get(x).remove(y);
                    if (tm.get(x).size() == 0)
                        tm.remove(x);
                    w.println((i + 1) + " " + (j + 1));
                    continue;
                }
                tm.get(x).remove(y);
                if (tm.get(x).size() > 0) {
                    int y2 = 0;
                    if (tm.get(x).ceilingEntry(y) != null) {
                        y2 = tm.get(x).ceilingKey(y);
                    } else {
                        y2 = tm.get(x).lowerKey(y);
                    }
                    int z2 = 0;
                    if (tm.get(x).get(y2).ceilingEntry(z) != null) {
                        z2 = tm.get(x).get(y2).ceilingKey(z);
                    } else {
                        z2 = tm.get(x).get(y2).lowerKey(z);
                    }
                    j = tm.get(x).get(y2).get(z2);
                    tm.get(x).get(y2).remove(z2);
                    if (tm.get(x).get(y2).size() == 0)
                        tm.get(x).remove(y2);
                    if (tm.get(x).size() == 0)
                        tm.remove(x);
                    w.println((i + 1) + " " + (j + 1));
                    continue;
                }
                tm.remove(x);
                int x2 = 0;
                if (tm.ceilingEntry(x) != null) {
                    x2 = tm.ceilingKey(x);
                } else {
                    x2 = tm.lowerKey(x);
                }
                int y2 = 0;
                if (tm.get(x2).ceilingEntry(y) != null) {
                    y2 = tm.get(x2).ceilingKey(y);
                } else {
                    y2 = tm.get(x2).lowerKey(y);
                }
                int z2 = 0;
                if (tm.get(x2).get(y2).ceilingEntry(z) != null) {
                    z2 = tm.get(x2).get(y2).ceilingKey(z);
                } else {
                    z2 = tm.get(x2).get(y2).lowerKey(z);
                }
                j = tm.get(x2).get(y2).get(z2);
                tm.get(x2).get(y2).remove(z2);
                if (tm.get(x2).get(y2).size() == 0)
                    tm.get(x2).remove(y2);
                if (tm.get(x2).size() == 0)
                    tm.remove(x2);
                w.println((i + 1) + " " + (j + 1));
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

