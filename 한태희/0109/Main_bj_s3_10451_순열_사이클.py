def dfs(node, e, v):
    v[node] = True
    next = e[node]
    if not v[next]:
        dfs(next, e, v)

def slove_순열사이클(N, arr):
    e = {}
    for i in range(len(arr)):
        src = i + 1
        dst = arr[i]
        e[src] = dst
    
    v = []
    for i in range(0, N+1):
        v.append(False)

    cnt = 0
    for i in range(1, N+1):
        if not v[i]:
            dfs(i, e, v)
            cnt +=1
    
    return cnt
    
if __name__ == "__main__":
    T = int(input())
    for tc in range(1, T+1):
        N = int(input())
        st = input()
        arr = list(map(int, st.split()))

        print(slove_순열사이클(N, arr))
