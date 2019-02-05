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

//invalid variable names:
//tm, time

//calling map[x] will create entry (x, 0)

map<int, int> m;
long a, b;

long recur(int l, int r) {

	if(m.lower_bound(l) == m.end() || m.lower_bound(l)->first > r) 
		return a;

	int curr = l, count = 0;
	
	if(m.find(curr) != m.end())
		count += m[curr];

	while(m.upper_bound(curr) != m.end() && m.upper_bound(curr)->first <= r) {
		curr = m.upper_bound(curr)->first;
		count += m[curr];
	}

	long ret = b * count * (r - l + 1);

	if(l != r) 
		ret = min(recur(l, (l + r) / 2) + recur((l + r) / 2 + 1, r), ret);

	return ret;
}


int main() {
 
	initIO();
    
    int n, k;
    cin>>n>>k>>a>>b;

	while(k--) {
		int x;
		cin>>x;
		m[x - 1]++;
	}

	/*
	for (const auto& e : m) {
    	std::cout << e.first << ": " << e.second <<"\n";
	}
	*/

	cout<<recur(0, (long)pow(2, n) - 1)<<"\n";
    
	return 0;
}