
#include <bits/stdc++.h>
using namespace std;

typedef long long int ll;

/* spar5h */

int vis[1 << 22], check[1 << 22];
ll mask =( 1 << 22) - 1;

void DFS(ll x) {

	vis[x] = 1;

	for(int i = 0; i < 22; i++)
		if((x & (1 << i)) != 0 && vis[x ^ (1 << i)] == 0)
			DFS(x ^ (1 << i));

	if(check[x] == 1 && vis[x ^ mask] == 0)
		DFS(x ^ mask);
}

int main() {

	ios_base::sync_with_stdio(false);
	cin.tie(NULL);

	ll n; cin>>n;

	for(ll i = 0; i < (1 << 22); i++) {
		vis[i] = 0; check[i] = 0;
	}

	ll m; cin>>m;

	ll temp;

	while(m--) {
		cin>>temp; check[temp] = 1;
	}

	ll res = 0;

	for(int i = 0; i < (1 << 22); i++) {

		if(check[i] == 0 || vis[i] == 1)
			continue;

		DFS(i ^ mask);
		res++;
	}

	cout<<res<<"\n";

	return 0;
}


