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
        EPolycarpAndSnakes solver = new EPolycarpAndSnakes();
        solver.solve(1, in, out);
        out.close();
    }

    static class EPolycarpAndSnakes {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int t = s.nextInt();
            while (t-- > 0) {
                int n = s.nextInt(), m = s.nextInt();
                char[][] a = new char[n][m];
                for (int i = 0; i < n; i++)
                    a[i] = s.next().toCharArray();
                int[] f = new int[26];
                Pair[] first = new Pair[26];
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < m; j++) {
                        if (a[i][j] == '.')
                            continue;
                        if (f[a[i][j] - 'a'] == 0)
                            first[a[i][j] - 'a'] = new Pair(i, j);
                        f[a[i][j] - 'a']++;
                    }
                }
                Pair[] second = new Pair[26];
                boolean check = true;
                for (int k = 0; k < 26; k++) {
                    if (first[k] == null)
                        continue;
                    int xi = first[k].i, xj = first[k].j;
                    if (f[k] == 1) {
                        second[k] = new Pair(xi, xj);
                        continue;
                    }
                    Pair right = null, down = null;
                    for (int j = m - 1; j > xj; j--) {
                        if (a[xi][j] == (char) (k + 'a')) {
                            right = new Pair(xi, j);
                            break;
                        }
                    }
                    if (right != null) {
                        int count = 2;
                        for (int j = xj + 1; j < right.j; j++) {
                            if (a[xi][j] == '.' || a[xi][j] < (char) (k + 'a'))
                                check = false;
                            else if (a[xi][j] == (char) (k + 'a'))
                                count++;
                        }
                        if (count < f[k])
                            check = false;
                        second[k] = right;
                        continue;
                    }
                    for (int i = n - 1; i > xi; i--) {
                        if (a[i][xj] == (char) (k + 'a')) {
                            down = new Pair(i, xj);
                            break;
                        }
                    }
                    if (down != null) {
                        int count = 2;
                        for (int i = xi + 1; i < down.i; i++) {
                            if (a[i][xj] == '.' || a[i][xj] < (char) (k + 'a'))
                                check = false;
                            else if (a[i][xj] == (char) (k + 'a'))
                                count++;
                        }
                        if (count < f[k])
                            check = false;
                        second[k] = down;
                        continue;
                    }

                    check = false;
                }

                if (check) {
                    w.println("YES");
                    int count = 0;
                    for (int i = 0; i < 26; i++)
                        if (f[i] > 0)
                            count = i + 1;
                    w.println(count);
                    for (int i = 0; i < count; i++) {
                        if (first[i] != null)
                            w.println((first[i].i + 1) + " " + (first[i].j + 1) + " " + (second[i].i + 1) + " " + (second[i].j + 1));
                        else
                            w.println((first[count - 1].i + 1) + " " + (first[count - 1].j + 1) + " " + (second[count - 1].i + 1) + " " + (second[count - 1].j + 1));

                    }
                } else
                    w.println("NO");
            }
        }

        class Pair {
            int i;
            int j;

            Pair(int i, int j) {
                this.i = i;
                this.j = j;
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

