#pragma once

#ifndef __clang__
  #pragma GCC optimize ("O3")
#endif

void solve(
  #ifdef GCJ_CASE
    long long case_id
  #endif
);

#include<algorithm>
#include<bitset>
#include<functional>
#include<iomanip>
#include<iostream>
#include<limits>
#include<map>
#include<numeric>
#include<queue>
#include<set>
#include<sstream>
#include<type_traits>
#include<unordered_map>
#include<unordered_set>
#include<vector>
#include<cassert>
#include<climits>
#include<cmath>
#include<cstdio>
#include<cstdlib>
using namespace std;

#include "bTypes.hpp"
#include "bMod.hpp"
