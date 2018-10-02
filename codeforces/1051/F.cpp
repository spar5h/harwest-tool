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
#define N (ll)(1e5 + 5)

struct cpair {
	ll i, j;
};

struct comp {
    bool operator()(const cpair& x, const cpair& y){
        return x.j > y.j;
    }
};

vector<ll> adj[N], adjWt[N];
ll currTime, h, tin[N], tout[N], anc[N][(ll)(log(N) / log(2)) + 1], d[N];

void dfs(ll x, ll p) {

	tin[x] = currTime++;

	anc[x][0] = p;

	for(ll i = 1; i <= h; i++)
		anc[x][i] = anc[anc[x][i - 1]][i - 1];

	for(ll i = 0; i < adj[x].size(); i++)
		if(adj[x][i] != p) {
			d[adj[x][i]] = d[x] + adjWt[x][i]; dfs(adj[x][i], x); }

	tout[x] = currTime++;
}

bool isAnc(ll u, ll v) {

	if(tin[u] <= tin[v] && tout[v] <= tout[u])
		return true;

	return false;
}

ll lca(ll u, ll v) {

	if(isAnc(u, v))
		return u;

	if(isAnc(v, u))
		return v;

	for(ll i = h; i >= 0; i--)
		if(!isAnc(anc[u][i], v))
			u = anc[u][i];

	return anc[u][0];
}

int main() {

	ios_base::sync_with_stdio(false);
	cin.tie(NULL);

	ll n, m, i, j; cin>>n>>m;

	vector<cpair> list;
	ll wt[m];

	for(i = 0; i < m; i++) {
		cpair p; cin>>p.i>>p.j>>wt[i]; list.pb(p);
	}

	ll extra[m];

	for(i = 0; i < m; i++)
		extra[i] = 0;

	ll color[n + 1];

	for(i = 1; i <= n; i++) {
		color[i] = i; adj[i].pb(i);
	}

	ll u, v, x, y, e, sm, lg;

	for(i = 0; i < m; i++) {

		u = list[i].i; v = list[i].j;

		if(color[u] == color[v]) {
			extra[i] = 1; continue;
		}

		sm = color[u]; lg = color[v];

		if(adj[sm].size() > adj[lg].size()) {
			sm = color[v]; lg = color[u];
		}

		for(j = 0; j < adj[sm].size(); j++) {
			x = adj[sm][j];
			adj[lg].pb(x); color[x] = lg;
		}
	}

	h = (ll)(log(n) / log(2));

	for(i = 1; i <= n; i++)
		adj[i].clear();

	for(i = 0; i < m; i++) {

		if(extra[i] == 1)
			continue;

		u = list[i].i; v = list[i].j;

		adj[u].pb(v); adj[v].pb(u);
		adjWt[u].pb(wt[i]); adjWt[v].pb(wt[i]);
	}

	dfs(1, 1);

	unordered_set<ll> hs;

	for(i = 0; i < m; i++) {

		if(extra[i] == 0)
			continue;

		u = list[i].i; v = list[i].j;
		hs.insert(u); hs.insert(v);

		adj[u].pb(v); adj[v].pb(u);
		adjWt[u].pb(wt[i]); adjWt[v].pb(wt[i]);
	}

	ll dsp[hs.size()][n + 1];

	for(i = 0; i < hs.size(); i++)
		for(j = 0; j <= n; j++)
			dsp[i][j] = LLONG_MAX;

	ll c = 0;
	ll vis[n + 1];

	for(auto v : hs) {

		dsp[c][v] = 0;

		priority_queue<cpair, vector<cpair>, comp> pq;

		cpair p; p.i = v; p.j = 0;
		pq.push(p);

		for(i = 1; i <= n; i++)
			vis[i] = 0;

		while(!pq.empty()) {

			x = pq.top().i;
			pq.pop();

			if(vis[x] == 1)
				continue;

			vis[x] = 1;

			for(i = 0; i < adj[x].size(); i++) {

				y = adj[x][i];
				e = adjWt[x][i];

				if(dsp[c][y] > dsp[c][x] + e) {
					dsp[c][y] = dsp[c][x] + e;
					cpair temp; temp.i = y; temp.j = dsp[c][y];
					pq.push(temp);
				}
			}
		}

		c++;
	}

	ll q, anc, res; cin>>q;

	while(q-- > 0) {

		cin>>u>>v;
		anc = lca(u, v);

		res = d[u] + d[v] - 2 * d[anc];

		for(ll i = 0; i < c; i++)
			res = min(dsp[i][u] + dsp[i][v], res);

		cout<<res<<endl;
	}

	return 0;
}
