import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.InputMismatchException;
import java.io.IOException;
import java.util.Queue;
import java.util.LinkedList;
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
        CFriendsAndGifts solver = new CFriendsAndGifts();
        solver.solve(1, in, out);
        out.close();
    }

    static class CFriendsAndGifts {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt();
            int[] in = new int[n];
            int[] out = new int[n];
            int[] a = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = s.nextInt() - 1;
                if (a[i] == -1)
                    continue;
                in[a[i]] = 1;
                out[i] = 1;
            }
            Queue<Integer> give = new LinkedList<>();
            Queue<Integer> take = new LinkedList<>();
            Queue<Integer> both = new LinkedList<>();
            for (int i = 0; i < n; i++) {
                if (in[i] == 0 && out[i] == 1)
                    take.add(i);
                if (in[i] == 1 && out[i] == 0)
                    give.add(i);
                if (in[i] == 0 && out[i] == 0)
                    both.add(i);
            }

            while (!give.isEmpty() && !both.isEmpty()) {
                a[give.poll()] = both.peek();
                give.add(both.poll());
            }
            while (!give.isEmpty()) {
                a[give.poll()] = take.poll();
            }
            ArrayList<Integer> cycle = new ArrayList<>();
            while (!both.isEmpty())
                cycle.add(both.poll());
            for (int i = 0; i < cycle.size(); i++) {
                int x = cycle.get(i);
                int y = cycle.get((i + 1) % cycle.size());
                a[x] = y;
            }
            for (int i = 0; i < n; i++)
                w.print(a[i] + 1 + " ");
            w.println();
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

