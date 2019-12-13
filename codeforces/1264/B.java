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
        BBeautifulSequence solver = new BBeautifulSequence();
        solver.solve(1, in, out);
        out.close();
    }

    static class BBeautifulSequence {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int[] a = new int[4];
            for (int i = 0; i < 4; i++)
                a[i] = s.nextInt();
            StringBuffer sb = new StringBuffer();
            boolean res = true;
            while (a[0] > 1) {
                if (a[1] == 0) {
                    w.println("NO");
                    return;
                }
                a[0]--;
                a[1]--;
                sb.append("0 1 ");
            }
            if (a[0] == 1) {
                sb.append("0 ");
                a[0]--;
            }
            StringBuffer sb3 = new StringBuffer();
            while (a[3] > 1) {
                if (a[2] == 0) {
                    w.println("NO");
                    return;
                }
                a[3]--;
                a[2]--;
                sb3.append("3 2 ");
            }
            if (a[3] == 1) {
                sb3.append("3 ");
                a[3]--;
            }
            StringBuffer sb2 = new StringBuffer();
            while (a[1] > 0 && a[2] > 0) {
                sb2.append("1 2 ");
                a[1]--;
                a[2]--;
            }
            if (sb.length() > 0 && a[1] > 0) {
                sb.reverse();
                sb.append(" 1");
                sb.reverse();
                a[1]--;
            }
            if (sb3.length() > 0 && a[2] > 0) {
                sb3.append("2 ");
                a[2]--;
            }
            if (sb3.length() == 0 && sb2.length() > 0 && a[1] > 0) {
                sb2.append("1 ");
                a[1]--;
            }
            if (sb.length() == 0 && sb2.length() > 0 && a[2] > 0) {
                sb2.reverse();
                sb2.append(" 2");
                sb2.reverse();
                a[2]--;
            }
            if (sb.length() == 0 && sb2.length() == 0 && sb3.length() == 0) {
                if (a[1] == 0 && a[2] == 1) {
                    sb2.append("2 ");
                    a[2]--;
                } else if (a[1] == 1 && a[2] == 0) {
                    sb2.append("1 ");
                    a[1]--;
                }
            }
            if (sb.length() > 0 && sb2.length() == 0) {
                if (a[1] > 0) {
                    sb.append("1 ");
                    a[1]--;
                }
            }
            if (sb3.length() > 0 && sb2.length() == 0) {
                if (a[2] > 0) {
                    sb3.reverse();
                    sb3.append(" 2");
                    sb3.reverse();
                    a[2]--;
                }
            }
            if (a[0] > 0 || a[1] > 0 || a[2] > 0 || a[3] > 0 || (sb.length() > 0 && sb3.length() > 0 && sb2.length() == 0)) {
                w.println("NO");
            } else {
                w.println("YES");
                w.print(sb.toString());
                w.print(sb2.toString());
                w.print(sb3.toString());
                w.println();
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

