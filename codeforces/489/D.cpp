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

int main() {

	ios_base::sync_with_stdio(false);
	cin.tie(NULL);

	ll n, m; cin>>n>>m;
			
	int adj[n + 1][n + 1], count[n + 1][n + 1];
	
	for(ll i = 1; i <= n; i++) {
		for(ll j = 1; j <= n; j++) {
			adj[i][j] = 0; count[i][j] = 0;
		}
	}
	
	ll u, v;
	
	while(m--) {
		cin>>u>>v; adj[u][v] = 1;
	}
	
	for(ll i = 1; i <= n; i++) {
		
		for(ll j = 1; j <= n; j++) {
			
			if(adj[i][j] == 0)
				continue;
			
			for(ll k = 1; k <= n; k++)
				if(adj[k][i] == 1)
					count[k][j]++;
		}
	}
	
	ll res = 0;
	
	for(ll i = 1; i <= n; i++)
		for(ll j = 1; j <= n; j++)
			if(i != j)
				res += count[i][j] * (count[i][j] - 1) / 2;
	
	cout<<res<<"\n";
	
	return 0;
}

