/*
 * cf.cpp
 *
 *  Created on: Jun 10, 2018
 *      Author: Sparsh Sanchorawala
 */

#include <bits/stdc++.h>
using namespace std;

typedef long long int ll;
#define pb push_back

/*
struct kn {

	ll pow, co;

	 bool operator<(const kn& rhs) const {
		 return co < rhs.co;
	 }
};
*/

int main() {

	ios_base::sync_with_stdio(false);
	cin.tie(NULL);

	ll r, g; cin>>r>>g;

	ll dp[r + 1], dp2[r + 1];

	for(ll i = 0; i <= r; i++)
		dp[i] = 0;

	if(r >= 1)
		dp[1] = 1;

	if(g >= 1)
		dp[0] = 1;

	ll mod = (ll)1e9 + 7;

	for(ll i = 2; i * (i + 1) / 2 <= (r + g); i++) {

		for(ll j = 0; j <= r; j++)
			dp2[j] = 0;

		ll l2 = min(r, i * (i + 1) / 2);

		for(ll j = 0; j + i <= l2; j++)
				dp2[j + i] = (dp2[j + i] + dp[j]) % mod;

		ll l1 = min(r, i * (i - 1) / 2); l2 = max((ll)0, i * (i + 1) / 2 - g);

		for(ll j = l1; j >= l2; j--)
				dp2[j] = (dp2[j] + dp[j]) % mod;

		for(ll j = 0; j <= r; j++)
			dp[j] = dp2[j];
	}

	ll res = 0;

	for(ll i = 0; i <= r; i++)
		res = (res + dp[i]) % mod;

	cout<<res<<"\n";

	return 0;
}

