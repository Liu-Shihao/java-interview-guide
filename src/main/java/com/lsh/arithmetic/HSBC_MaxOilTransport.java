package com.lsh.arithmetic;
import java.util.*;

/*
 * HSBC : 管道网络最大输油量计算
 * 给定一个由管道组成的网络，每个管道连接两个节点（起始节点和结束节点），并具有一个传输速率，需要计算给定时间内，该网络可以运输的最大油量。
 * 
 * 最大油量等于所有从根节点到叶子节点路径上的最小传输速率之和。
 * num：网络中的节点数量
 * baseR：根节点
 * pipesCon：表示管道连接的二维数组，每行包含三个整数：起始节点，结束节点和传输速率
 * 
 * 管道网络结构如下：
 *    4
 *  / | \
 * 2  6  1
 *      / \
 *     3   5
 * 
 * 输入：
 * 6
 * 4
 * 5 3
 * 4 2 10
 * 4 6 20
 * 4 1 30
 * 1 3 50
 * 1 5 80
 * 
 * 管道 (4) -> (2)：可运输 10 单位油量
 * 管道 (4) -> (6)：可运输 20 单位油量
 * 管道 (4) -> (1) -> (3) 和 (4) -> (1) -> (5)：由于管道 (4)-(1) 的限制，只能运输 30 单位油量
 * 因此，网络的最大运输能力为 10 + 20 + 30 = 60 单位油量。
 * 
 * 算法核心思想：
 * 每条从根节点到叶子节点的路径，最大能运输的油量由路径上最小速率的管道决定
 * 所有叶子路径的最小速率之和，就是整个网络的最大运输能力
 */

public class HSBC_MaxOilTransport {
    public static int oilTransport(int num, int baseR, int[][] pipesCon) {
        //构建邻接表（无向图）,每个节点对应一个列表:
        Map<Integer, List<int[]>> graph = new HashMap<>();
        //遍历输入的管道数组 pipesCon
        for (int[] pipe : pipesCon) {
            int u = pipe[0];//开始节点
            int v = pipe[1];//结束节点
            int rate = pipe[2];//管道速率
            graph.putIfAbsent(u, new ArrayList<>());
            graph.putIfAbsent(v, new ArrayList<>());
            //列表中每个元素是一个长度为2的数组，分别表示相邻节点编号和管道速率。
            graph.get(u).add(new int[]{v, rate});
            graph.get(v).add(new int[]{u, rate});
            //注意，当前节点的所有相邻节点都需要添加，因为是无向图，所以需要双向添加
            //所以，如果当前节点是 1,则相邻节点除了包含了子节点 3 和 5 ，还包含了父节点 4！
            //所以在DFS是，需要判断相邻节点是否是父节点！
        }

        // 从根节点baseR开始DFS，累加所有叶子路径的最小传输率
        int[] totalOil = {0};
        dfs(graph, baseR, -1, Integer.MAX_VALUE, totalOil);
        return totalOil[0];
    }

    /**
     * 
     * 深度优先搜索（Depth-First Search, DFS）是一种用于遍历或搜索树（Tree）和图（Graph）结构的算法。
     * 它的核心思想是尽可能深地探索一条路径，直到无法继续前进时再回溯（Backtrack）并尝试其他分支。
     * 从节点开始递归遍历
     * 对每个节点（除了头节点）递归调用dfs，并更新当前路径的最小速率
     * @param graph
     * @param node  当前节点
     * @param parent  父节点
     * @param currentMin   当前路径的最小传输率
     * @param totalOil 总油量，注意使用int[]而不是int类型！
     * Java中基本类型（如int）是按值传递的，而对象（如数组）是按引用传递的。
     * 使用int[]可以通过引用传递，确保递归过程中所有层共享同一份数据。
     * 使用int[]可以直接在递归中修改共享变量，避免手动合并。
     * 
     * 在DFS递归中，当需要跨层级共享和修改状态时，使用int[]是最简单、直观且高效的方式。它完美解决了Java值传递的限制，同时保持了代码的简洁性。
     * 
     */
    private static void dfs(Map<Integer, List<int[]>> graph, int node, int parent, int currentMin, int[] totalOil) {
        
        boolean isLeaf = true;
        //遍历邻接表，获取每一节管道（开始节点、结束节点和速率）
        for (int[] neighbor : graph.getOrDefault(node, new ArrayList<>())) {
            int nextNode = neighbor[0];//相邻节点
            int rate = neighbor[1];//管道速率
            //相邻节点有可能是父节点，需要排除父节点
            if (nextNode != parent) {
                isLeaf = false;//如果相邻节点不是父节点，则说明当前节点不是叶子节点（存在子节点）。
                //继续向下递归
                dfs(graph, nextNode, node, Math.min(currentMin, rate), totalOil);
            }
        }
        // 如果是叶子节点且非根节点，说明路径已经走完，传输总油量累加当前路径的最小传输率
        if (isLeaf && node != parent) {
            totalOil[0] += currentMin;
        }
    }

    // main方法保持不变（题目要求）
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int num = in.nextInt();
        int baseR = in.nextInt();
        int pipesCon_row = in.nextInt();
        int pipesCon_col = in.nextInt();
        int[][] pipesCon = new int[pipesCon_row][pipesCon_col];
        for (int idx = 0; idx < pipesCon_row; idx++) {
            for (int jdx = 0; jdx < pipesCon_col; jdx++) {
                pipesCon[idx][jdx] = in.nextInt();
            }
        }
        int result = oilTransport(num, baseR, pipesCon);
        System.out.print(result);
    }
}
