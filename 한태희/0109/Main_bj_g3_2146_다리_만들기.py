import sys
sys.setrecursionlimit(99999)

dx = [-1, 1, 0, 0]
dy = [0, 0, -1, 1]
    
def makeGroup(N, x, y, arr, groupName):
    arr[y][x] = groupName
    for k in range(4):
        nx = x + dx[k]
        ny = y + dy[k]
        if 0<=nx<N and 0<=ny<N and arr[ny][nx] == 1:
            makeGroup(N, nx, ny, arr, groupName)

def slove_다리만들기(N, arr):

    groupName = 2
    for y in range(N):
        for x in range(N):
            if arr[y][x] == 1:
                makeGroup(N, x, y, arr, groupName)
                groupName +=1
    
    ret = 99999999999

    for y1 in range(N):
        for x1 in range(N):

            if arr[y1][x1]==0:
                continue

            for y2 in range(N):
                for x2 in range(N):

                    if arr[y2][x2]==0:
                        continue

                    if y1 > y2:
                        continue
                    if y1 == y2 and x1 > x2:
                        continue
                    if arr[y1][x1] == arr[y2][x2]:
                        continue

                    length = abs(x1-x2) + abs(y1-y2) -1
                    ret = min(ret, length)

    return ret
    
if __name__ == "__main__":
    N = int(input())
    
    ## arr 설명
    ## 0: 바다 1: 이름이 없는 섬 2보다 큰 정수: 그 번호를 이름으로 가지는 섬
    arr = [list(map(int, input().split())) for _ in range(N)]
    
    print(slove_다리만들기(N, arr))
