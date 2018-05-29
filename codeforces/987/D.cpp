#include <bits/stdc++.h>
using namespace std;
 
typedef long long int ll;

int main() {
    
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    
    ll n, m, k, S;
    
    cin>>n>>m>>k>>S;
    
    vector<ll> adj[n + 1], color[k + 1], res[n + 1];
    
    ll x;
    
    for(ll i = 1; i <= n; i++) {
    	cin>>x; color[x].push_back(i);
    }
    
    ll n1, n2;
    
    for(ll i = 0; i < m; i++) {
   		cin>>n1>>n2; adj[n1].push_back(n2); adj[n2].push_back(n1);
   	}
    
    ll level[n + 1];
    
    for(ll i = 1; i <= k; i++) {
    	
    	queue<ll> q;
    	
    	for(ll j = 1; j <= n; j++)
    		level[j] = -1;
    		
    	for(ll j = 0; j < color[i].size(); j++) {
			level[color[i][j]] = 0; q.push(color[i][j]);
		}
			
		while(!q.empty()) {
				
			ll x = q.front(); q.pop();
				
			for(int j = 0; j < adj[x].size(); j++) {
					
				if(level[adj[x][j]] == -1) {
					level[adj[x][j]] = level[x] + 1; q.push(adj[x][j]);
				}	
			}
		}
			
		for(ll j = 1; j <= n; j++)
			res[j].push_back(level[j]);
    }
    
	ll ans;
		
	for(ll i = 1; i <= n; i++) {
			
		sort(res[i].begin(), res[i].end());
			
		ans = 0;
			
		for(ll j = 0; j < S; j++)
			ans += res[i][j];
			
		cout<<ans<<" ";
	}
		
	cout<<"\n";
		
    return 0;
}