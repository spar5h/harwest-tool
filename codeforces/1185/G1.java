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
        G1PlaylistForPolycarpEasyVersion solver = new G1PlaylistForPolycarpEasyVersion();
        solver.solve(1, in, out);
        out.close();
    }

    static class G1PlaylistForPolycarpEasyVersion {
        long mod = (long) 1e9 + 7;

        public void solve(int testNumber, InputReader s, PrintWriter w) {
            int n = s.nextInt(), T = s.nextInt();
            int[] t = new int[n + 1];
            int[] g = new int[n + 1];
            int[] f = new int[4];
            for (int i = 1; i <= n; i++) {
                t[i] = s.nextInt();
                g[i] = s.nextInt();
                f[g[i]]++;
            }
            long[] fact = new long[n + 1];
            fact[0] = 1;
            for (int i = 1; i <= n; i++)
                fact[i] = fact[i - 1] * i % mod;
            long[][][][] perm = new long[f[1] + 1][f[2] + 1][f[3] + 1][3 + 1];
            long[][][] sumPerm = new long[f[1] + 1][f[2] + 1][f[3] + 1];
            perm[0][0][0][0] = 1;
            for (int a = 0; a <= f[1]; a++) {
                for (int b = 0; b <= f[2]; b++) {
                    for (int c = 0; c <= f[3]; c++) {
                        if (a - 1 >= 0)
                            perm[a][b][c][1] = (sumPerm[a - 1][b][c] - perm[a - 1][b][c][1] + mod) % mod;
                        if (b - 1 >= 0)
                            perm[a][b][c][2] = (sumPerm[a][b - 1][c] - perm[a][b - 1][c][2] + mod) % mod;
                        if (c - 1 >= 0)
                            perm[a][b][c][3] = (sumPerm[a][b][c - 1] - perm[a][b][c - 1][3] + mod) % mod;
                        for (int i = 0; i <= 3; i++)
                            sumPerm[a][b][c] = (sumPerm[a][b][c] + perm[a][b][c][i]) % mod;
                    }
                }
            }
            long[][][][][] dp = new long[n + 1][f[1] + 1][f[2] + 1][f[3] + 1][T + 1];
            dp[0][0][0][0][0] = 1;
            for (int i = 1; i <= n; i++) {
                for (int a = 0; a <= f[1]; a++) {
                    for (int b = 0; b <= f[2]; b++) {
                        for (int c = 0; c <= f[3]; c++) {
                            for (int j = 0; j <= T; j++) {
                                dp[i][a][b][c][j] = dp[i - 1][a][b][c][j];
                                if (j - t[i] >= 0) {
                                    if (g[i] == 1 && a > 0) {
                                        dp[i][a][b][c][j] = (dp[i][a][b][c][j] + dp[i - 1][a - 1][b][c][j - t[i]]) % mod;
                                    } else if (g[i] == 2 && b > 0) {
                                        dp[i][a][b][c][j] = (dp[i][a][b][c][j] + dp[i - 1][a][b - 1][c][j - t[i]]) % mod;
                                    } else if (g[i] == 3 && c > 0) {
                                        dp[i][a][b][c][j] = (dp[i][a][b][c][j] + dp[i - 1][a][b][c - 1][j - t[i]]) % mod;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            long res = 0;
            for (int a = 0; a <= f[1]; a++)
                for (int b = 0; b <= f[2]; b++)
                    for (int c = 0; c <= f[3]; c++)
                        res = (res + dp[n][a][b][c][T] * sumPerm[a][b][c] % mod * fact[a] % mod * fact[b] % mod * fact[c] % mod) % mod;
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

