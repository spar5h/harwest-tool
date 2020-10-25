import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
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
        ETwoTypesOfSpells solver = new ETwoTypesOfSpells();
        solver.solve(1, in, out);
        out.close();
    }

    static class ETwoTypesOfSpells {
        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt();
            TreeMap<Integer, Integer> tm = new TreeMap<>();
            long sum = 0, ex = 0;
            int low = 0;
            int light = 0;
            int lightUsed = 0;
            for (int i = 0; i < n; i++) {
                int t = s.nextInt(), x = s.nextInt();
                if (x > 0) {
                    sum += x;
                    tm.put(x, t);
                    //add fire
                    if (t == 0) {
                        if (light > 0 && x > low) {
                            ex -= low;
                            ex += x;
                            lightUsed -= tm.get(low);
                            low = tm.higherKey(low);
                        }
                    }
                    //add lightning
                    else {
                        light++;
                        if (light == 1) {
                            low = tm.lastKey();
                            ex += low;
                            lightUsed += tm.get(low);
                        } else if (x > low) {
                            ex += x;
                            lightUsed++;
                        } else {
                            low = tm.lowerKey(low);
                            ex += low;
                            lightUsed += tm.get(low);
                        }
                    }
                } else {
                    x *= -1;
                    sum -= x;
                    tm.remove(x);
                    //remove fire
                    if (t == 0) {
                        if (x >= low && light > 0) {
                            ex -= x;
                            low = tm.lowerKey(low);
                            ex += low;
                            lightUsed += tm.get(low);
                        }
                    }
                    //remove lightning
                    else {
                        light--;
                        if (x > low) {
                            ex -= x;
                            lightUsed--;
                        } else if (x == low) {
                            ex -= x;
                            lightUsed--;
                            if (light > 0) {
                                low = tm.higherKey(x);
                            } else {
                                low = 0;
                            }
                        } else {
                            ex -= low;
                            lightUsed -= tm.get(low);
                            if (light > 0) {
                                low = tm.higherKey(low);
                            } else {
                                low = 0;
                            }
                        }
                    }
                }
                long res = sum + ex;
                if (light > 0 && light == lightUsed) {
                    res -= low;
                    if (tm.lowerKey(low) != null)
                        res += tm.lowerKey(low);
                }
                w.println(res);
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

