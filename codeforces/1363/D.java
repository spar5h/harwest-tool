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
        DGuessTheMaximums solver = new DGuessTheMaximums();
        solver.solve(1, in, out);
        out.close();
    }

    static class DGuessTheMaximums {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int t = s.nextInt();
            while (t-- > 0) {
                int n = s.nextInt(), k = s.nextInt();
                ArrayList<Integer>[] adj = new ArrayList[k];
                int tot = 0;
                for (int i = 0; i < k; i++) {
                    adj[i] = new ArrayList<>();
                    int m = s.nextInt();
                    tot += m;
                    while (m-- > 0) {
                        adj[i].add(s.nextInt());
                    }
                }
                w.print("? " + n + " ");
                for (int i = 1; i <= n; i++) {
                    w.print(i + " ");
                }
                w.println();
                w.flush();
                int max = s.nextInt();
                int left = 0, right = k - 1;
                while (left < right) {
                    int mid = (left + right) / 2;
                    int count = 0;
                    for (int j = left; j <= mid; j++)
                        count += adj[j].size();
                    w.print("? " + count + " ");
                    for (int j = left; j <= mid; j++) {
                        for (int x : adj[j]) {
                            w.print(x + " ");
                        }
                    }
                    w.println();
                    w.flush();
                    int can = s.nextInt();
                    if (can == max) {
                        right = mid;
                    } else {
                        left = mid + 1;
                    }
                }

                w.print("? " + (n - adj[left].size()) + " ");
                int[] dead = new int[n + 1];
                for (int j : adj[left])
                    dead[j] = 1;
                for (int i = 1; i <= n; i++) {
                    if (dead[i] == 1)
                        continue;
                    w.print(i + " ");
                }
                w.println();
                w.flush();
                int other = s.nextInt();
                w.print("! ");
                for (int i = 0; i < k; i++) {
                    if (i == left)
                        w.print(other + " ");
                    else
                        w.print(max + " ");
                }
                w.println();
                w.flush();
                String str = s.next();
                if (!str.equals("Correct"))
                    break;
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

