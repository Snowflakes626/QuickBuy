package secRound;

import java.util.HashSet;

/*Q：编程题1
 * 在微服务的架构下，公司内部会有非常多的独立服务。
 * 服务之间可以相互调用，往往大型应用调用链条很长，如果出现循环依赖将出现非常恶劣的影响。
 * 对于一个具体应用，已知各个服务的调用关系（即依赖关系），请判断是否存在循环调用。
 * 输入：
 * 一组服务依赖关系list，('A', 'B') 表示 A 会调用 B 服务
 * service_relations = [('A', 'B'), ('A', 'C'), ('B', 'D'), ('D', 'A')]
 * 由于存在 A - B - D - A 故存在循环依赖，返回True；反之如果不存在，返回False
 * Follow up：
 * 1. 如果有多个环，请都检测出来
 * 2. 返回每个环中的服务名**/
public class no3 {
    static class UnionFind {
        int[] father;

        public UnionFind() {
            father = new int[26];
            for(int i = 0; i < 26; i++)
                father[i] = i;
        }

        public int find(int i) {
            if (father[i] == i)
                return i;
            else
                return find(father[i]);
        }

        public void union(int i, int j) {
            int ifa = find(i);
            int jfa = find(j);
            father[jfa] = ifa;
        }
    }


    public static void main(String[] args) {
        char[][] service_relations = new char[4][2];
        service_relations[0][0] = 'A';
        service_relations[0][1] = 'B';
        service_relations[1][0] = 'A';
        service_relations[1][1] = 'C';
        service_relations[2][0] = 'B';
        service_relations[2][1] = 'D';
        service_relations[3][0] = 'D';
        service_relations[3][1] = 'A';
        int i = process(service_relations);
        System.out.println("i = " + i);
    }

    public static int process(char[][] sr){
        UnionFind uf = new UnionFind();
        int count = 0;
        for(int i = 0; i < sr.length; i++){
            int fir = uf.find(sr[i][0] - 'A');
            System.out.println("fir = " + fir);
            int sec = uf.find(sr[i][1] - 'A');
            System.out.println("sec = " + sec);
            if(fir == sec) {
                count++;
                doPrint(uf, sr[i][0] - 'A', sr[i][1] - 'A');
            }
            else
                uf.union(fir, sec);

        }
        return count;
    }

    public static void doPrint(UnionFind uf, int i, int j){
        int tpi = i;
        int tpj = j;
        HashSet<Integer> set = new HashSet<>();
        do{
            set.add(i);
            i = uf.father[i];
        }while(uf.father[i] != i);
        set.add(uf.father[i]);
        while(!set.contains(j)){
            j = uf.father[j];
        }
        while(tpi != j){
            System.out.println((char)('A' + tpi));
            tpi = uf.father[tpi];
        }
        while(tpj != j){
            tpj = uf.father[tpj];
            System.out.println((char)('A' + tpj));
        }
    }

}
