/*
 * cf.cpp
 *
 *  Created on: Jun 10, 2018
 *      Author: Sparsh Sanchorawala
 */

#include <bits/stdc++.h>
using namespace std;

typedef long long ll;
#define pb push_back

/*
struct kn {

	ll pow, co;

	 bool operator<(const kn& rhs) const {
		 return co < rhs.co;
	 }
};
*/

ll gcd (ll a, ll b) {

	if(b == 0)
		return a;

	return(gcd(b, a % b));
}

int dp[700 + 1][700 + 1][2];
ll gcdVal[700 + 1][700 + 1];

void dfs(int l, int r, int dir) {
	
	dp[l][r][dir] = 0;
	
	int root;

	if(dir == 0)
		root = r + 1;
	else
		root = l - 1;

	for(int x = l; x <= r; x++) {

		if(gcdVal[x][root] == 1)
			continue;

		if(x != l && dp[l][x - 1][0] == -1)
			dfs(l, x - 1, 0);

		if(x != r && dp[x + 1][r][1] == -1)
			dfs(x + 1, r, 1);

		if((x == l || dp[l][x - 1][0] == 1) && (x == r || dp[x + 1][r][1] == 1)) {
			dp[l][r][dir] = 1; break;
		}
	}
}

int main() {

	ios_base::sync_with_stdio(false);
	cin.tie(NULL);

	int n; cin>>n;

	ll a[n + 1];

	for(int i = 1; i <= n; i++)
		cin>>a[i];

	for(int i = 1; i <= n; i++)
		for(int j = 1; j <= n; j++)
			gcdVal[i][j] = gcd(a[i], a[j]);

	for(int i = 1; i <= n; i++)
		for(int j = 1; j <= n; j++)
			for(int k = 0; k < 2; k++)
				dp[i][j][k] = -1;

	bool res = false;

	for(int i = 1; i <= n; i++) {

		if(i != 1 && dp[1][i - 1][0] == -1)
			dfs(1, i - 1, 0);

		if(i != n && dp[i + 1][n][1] == -1)
			dfs(i + 1, n, 1);

		if((i == 1 || dp[1][i - 1][0] == 1) && (i == n || dp[i + 1][n][1] == 1)) {
			res = true; break;
		}
	}

	if(res)
		cout<<"Yes\n";
	else
		cout<<"No\n";

	return 0;
}

