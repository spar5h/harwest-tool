/* spar5h */

#include <bits/stdc++.h>
using namespace std;
 
#define long long long int
#define pb push_back
#define N 100005

void initIO(){
    #ifndef ONLINE_JUDGE
    freopen("in.txt" , "r", stdin);
    freopen("out.txt", "w", stdout);
    #else
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    #endif
}

int main() {
		 
	initIO();

	int n; cin>>n;
	int c2 = 0;
	int tempVal = n;
	while(tempVal > 0) {
		c2++; tempVal /= 2;
	}
    int c3 = 2;

    long pow2[c2];
    pow2[0] = 1;
    for (int i = 1; i < c2; i++)
        pow2[i] = pow2[i - 1] * 2;

    long f[c2][c3];

    for (int j = 0; j < c2; j++) {
        f[j][0] = n / pow2[j];
        f[j][1] = n / (pow2[j] * 3);
    }

    long dp[c2][c3];
    for(int j = 0; j < c2; j++) {
    	dp[j][0] = 0;
    	dp[j][1] = 0;
    }

    dp[c2 - 1][0] = 1;
    if (pow2[c2 - 2] * 3 <= n)
        dp[c2 - 2][1] = 1;

    long temp[c2][c3];

    long mod = (long) 1e9 + 7;

    for (int i = 1; i < n; i++) {

        for (int j = 0; j < c2; j++) {
            temp[j][0] = dp[j][0];
            temp[j][1] = dp[j][1];
            dp[j][0] = 0;
            dp[j][1] = 0;
        }

        for (int j = 0; j < c2 - 1; j++) {
            dp[j][0] = (dp[j][0] + temp[j][0] * (f[j][0] - i) % mod) % mod;
            dp[j][0] = (dp[j][0] + temp[j + 1][0] * (f[j][0] - f[j + 1][0]) % mod) % mod;
            dp[j][0] = (dp[j][0] + temp[j][1] * (f[j][0] - f[j][1]) % mod) % mod;
            dp[j][1] = (dp[j][1] + temp[j][1] * (f[j][1] - i) % mod) % mod;
            dp[j][1] = (dp[j][1] + temp[j + 1][1] * (f[j][1] - f[j + 1][1]) % mod) % mod;
        }

        dp[c2 - 1][0] = (dp[c2 - 1][0] + temp[c2 - 1][0] * (f[c2 - 1][0] - i) % mod) % mod;
        dp[c2 - 1][0] = (dp[c2 - 1][0] + temp[c2 - 1][1] * (f[c2 - 1][0] - f[c2 - 1][1]) % mod) % mod;
        dp[c2 - 1][1] = (dp[c2 - 1][1] + temp[c2 - 1][1] * (f[c2 - 1][1] - i) % mod) % mod;
    }

    cout<<(dp[0][0]);

	return 0;
}