/*
package secRound;/*Q：编程题3
 * 在微服务的架构下，公司内部会有非常多的独立服务。
 * 服务之间可以相互调用，往往大型应用调用链条很多也很长，我们需要找出耗时最大的链条进行优化。（假设服务同时调用其依赖的下游服务）
 * 例如：
 * A服务依赖B服务，平均调用延迟100ms，记为(A, B, 100)
 * 其他依赖和延迟如下：
 * (A, C, 200)
 * (A, F, 100)
 * (B, D, 100)
 * (D, E, 50)
 * (C, G, 300)
 * 那么服务A有三条调用链：A-B-D-E，A-C-G，A-F，平均延迟250，500，100
 * 延迟最大的调用链是A-C-G，延迟为500ms
 * 输入：
 * [(A, B, 100), (A, C, 200), (A, F, 100), (B, D, 100), (D, E, 50), (C, G, 300)]
 * 输出：
 * 500
 * PS：输入可以硬编码，无需实现字符串解析*/

/*
public class no4 {
    public static void main(String[] args) {
        int[][] inf = new int[5][3];
        inf[0][0] = 0; inf[0][1] = 2; inf[0][2] = 200;
        inf[1][0] = 0; inf[1][1] = 5; inf[0][2] = 100;
        inf[2][0] = 1; inf[2][1] = 3; inf[2][2] = 100;
        inf[3][0] = 3; inf[3][1] = 4; inf[3][2] = 50;
        inf[4][0] = 2; inf[4][1] = 6; inf[4][2] = 300;
        int i = process(inf);
        System.out.println("i = " + i);
    }

    public static int process(int[][] inf){
        int[][] nums = new int[26][26];
        for(int i = 0; i < inf.length; i++)
            nums[inf[i][0]][inf[i][1]] = inf[i][2];
        int max = DFS(nums, 0);
    }

    public static int DFS(int[][] nums, int sum){

    }
}
*/