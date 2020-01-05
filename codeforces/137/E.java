import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Arrays;
import java.util.TreeMap;
import java.util.Map.Entry;
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
        ELastChance solver = new ELastChance();
        solver.solve(1, in, out);
        out.close();
    }

    static class ELastChance {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            String str = s.next();
            int n = str.length();
            HashSet<Character> hs = new HashSet<>(Arrays.asList('a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U'));
            int[] a = new int[n];
            for (int i = 0; i < n; i++) {
                if (hs.contains(str.charAt(i)))
                    a[i] = -1;
                else
                    a[i] = 2;
            }
            TreeMap<Integer, Integer> tm = new TreeMap<>();
            tm.put(0, -1);
            int pre = 0, max = 0, count = 0;
            for (int i = 0; i < n; i++) {
                pre += a[i];
                if (tm.floorEntry(pre) != null) {
                    int can = i - tm.floorEntry(pre).getValue();
                    if (max < can) {
                        max = can;
                        count = 1;
                    } else if (max == can)
                        count++;
                }
                if (tm.firstKey() > pre)
                    tm.put(pre, i);
            }
            if (max != 0)
                w.println(max + " " + count);
            else
                w.println("No solution");
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

