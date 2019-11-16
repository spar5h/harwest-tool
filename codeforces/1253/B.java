import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashSet;
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
        BSillyMistake solver = new BSillyMistake();
        solver.solve(1, in, out);
        out.close();
    }

    static class BSillyMistake {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt();
            int[] a = new int[n];
            for (int i = 0; i < n; i++)
                a[i] = s.nextInt();
            int l = 0;
            HashSet<Integer> in = new HashSet<>();
            HashSet<Integer> done = new HashSet<>();
            boolean check = true;
            ArrayList<Integer> res = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                if (a[i] > 0) {
                    if (done.contains(a[i])) {
                        check = false;
                        break;
                    }
                    done.add(a[i]);
                    in.add(a[i]);
                } else {
                    if (!in.contains(-a[i])) {
                        check = false;
                        break;
                    }
                    in.remove(-a[i]);
                }
                if (in.size() == 0) {
                    res.add(i - l + 1);
                    l = i + 1;
                    done.clear();
                }
            }
            if (in.size() != 0)
                check = false;
            if (check) {
                w.println(res.size());
                for (int i : res)
                    w.print(i + " ");
                w.println();
            } else
                w.println(-1);

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

