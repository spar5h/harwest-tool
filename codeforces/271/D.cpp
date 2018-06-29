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

struct node {

	struct node *sub[26];
};

struct node *getNode() {

	struct node *x =  new node;

    for(int i = 0; i < 26; i++)
    	x->sub[i] = NULL;

    return x;
}

string str;

void insert(node *x, int s, int e, int i) {

	if(s + i >= e)
		return;

	if(x->sub[str[s + i] - 'a'] == NULL)
		x->sub[str[s + i] - 'a'] = getNode();

	insert(x->sub[str[s + i] - 'a'], s, e, i + 1);
}

ll dfs(node *x) {

	ll res = 0;

	for(int i = 0; i < 26; i++)
		if(x->sub[i] != NULL)
			res += 1 + dfs(x->sub[i]);

	return res;
}

int main() {

	ios_base::sync_with_stdio(false);
	cin.tie(NULL);

	string check; int k; cin>>str>>check>>k;

	int n = str.length();

	struct node *root = getNode();

	for(int i = 0; i < n; i++) {

		int tempK = 0, pos = n;

		for(int j = i; j < n; j++) {

			tempK += 1 - (check[str[j] - 'a'] - '0');

			if(tempK > k) {
				pos = j; break;
			}
		}

		insert(root, i, pos, 0);
	}

	cout<<dfs(root)<<"\n";

	return 0;
}

