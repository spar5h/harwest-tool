#include <bits/stdc++.h>
using namespace std;

typedef long long int ll;
#define pb push_back

struct Node {
	struct Node *sub[2];
};
	
struct Node *getNode() {

	struct Node *ret = new Node;
	ret->sub[0] = NULL;
	ret->sub[1] = NULL;
	return ret;
}

void insert(struct Node *curr, ll x, int idx) {
	
	if(idx == -1)
		return;
	
	//cout<<((ll)1 << idx)<<"\n";
	
	int bit = (int)((x >> idx) & 1);
	
	if(curr->sub[bit] == NULL)
		curr->sub[bit] = getNode();
	
	insert(curr->sub[bit], x, idx - 1);
}

ll query(struct Node *curr, ll x, int idx) {
	
	if(idx == -1)
		return 0;
	
	int bit = (int)((x >> idx) & 1);
		
	if(curr->sub[bit ^ 1] == NULL) 
		return bit * ((ll)1 << idx) + query(curr->sub[bit], x, idx - 1);
	
	else 
		return (bit ^ 1) * ((ll)1 << idx) + query(curr->sub[bit ^ 1], x, idx - 1);
}

int main() {

	ios_base::sync_with_stdio(false);
	cin.tie(NULL);

	int n;
	cin>>n;
		
	ll a[n], pre[n], suf[n];
	cin>>a[0];
	pre[0] = a[0];
	
	for(int i = 1; i < n; i++) {
		cin>>a[i];
		pre[i] = pre[i - 1] ^ a[i];
	}
	
	suf[n - 1] = a[n - 1];
	
	for(int i = n - 2; i >= 0; i--)
		suf[i] = suf[i + 1] ^ a[i];
	
	ll ans = 0;
	
	struct Node *root = getNode();
	
	insert(root, 0, 39);
	
	for(int i = n - 1; i >= 0; i--) {
		ans = max(query(root, pre[i], 39) ^ pre[i], ans);
		insert(root, suf[i], 39);
	}
	
	ans = max(query(root, 0, 39), ans);
	
	cout<<ans<<"\n";

	return 0;
}