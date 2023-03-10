import java.util.*;

public class Main {
    public static void main(String[] args) {
        Map<String, List<String>> channel = Collections
                .singletonMap("channel", Arrays.asList("onlinebanking"));
        Map<String, List<String>> functionality = Collections
                .singletonMap("functionality", Arrays.asList("alias_administrativo","cobranza","pago_proveedores","cobro_clientes"));
        Map<String, List<String>> currency = Collections
                .singletonMap("currency", Arrays.asList("ars","usd"));

        Set<Map<String,List<String>>> domains = new HashSet<>(Arrays.asList(channel,currency));

        List<String> domainsKeys = new ArrayList<>();
                domains.stream().forEach(d->{
                    domainsKeys.add(d.keySet().toString().replace('[',' ').replace(']',' ').trim());
                });
        //
        System.out.println("cantidad de dominios: "+domains.size());
        System.out.println("nombres de dominios: "+domainsKeys);


        List<List<String>> totalComb = new ArrayList<>();
        for (Map<String,List<String>>  domain : domains) {
            List<String> comb = new ArrayList<>();
            String domainKey = domain.keySet().toString().replace('[',' ').replace(']',' ').trim();
            int valueQty =getValueQty(domain);
            List<String> domainValues = getValuesDomain(domain);
            getValuesCombination(domainValues, valueQty, domainKey, comb);
            totalComb.add(comb);
        }
        //System.out.println("totalComb: "+totalComb);
        cartesianProduct(totalComb, new String[totalComb.size()], 0);
    }

    private static void getValuesCombination(List<String> domainValues, int valueQty, String domainKey, List<String> comb) {
        for(int cantSubValues = 1; cantSubValues< valueQty +1; cantSubValues++) {
            combinationWithoutRepetition(domainKey, comb, domainValues, "",cantSubValues , valueQty,0);
        }
        System.out.println("cant:"+ comb.size());
        System.out.println("combinaciones"+ comb);
    }

    private static void combinationWithoutRepetition(String key,List<String> comb, List<String> values, String act, int subGroup, int valueQty, int y) {
        if (subGroup == 0) {
            comb.add(act);
        } else {
            for (int i = y; i < valueQty; i++) {
                if (!act.contains(values.get(i))) { // Controla que no haya repeticiones
                    if (values.size() != 1)
                        combinationWithoutRepetition(key, comb, values, subGroup == valueQty && i == y ? act + key + "-" + values.get(i) + "," : act + key + "-" + values.get(i), subGroup - 1, valueQty, i + 1);
                    else
                        combinationWithoutRepetition(key, comb, values, act + key + "-" + values.get(i), subGroup - 1, valueQty, i + 1);
                }
            }
        }
    }
    private static List<String> getValuesDomain(Map<String, List<String>> domain){

        return domain.entrySet().stream().findFirst().get().getValue();
    }
    private static int getValueQty(Map<String, List<String>> domain) {
        List<String> list = new ArrayList<>();
        domain.entrySet().stream().forEach(x->x.getValue().stream().forEach(y->list.add(y)));
        int cant = list.size();
        return cant;
    }
    private static void cartesianProduct(List<List<String>> input, String[] current, int k) {
        if (k == input.size()) {
            for (int i = 0; i < k; i++) {
                System.out.print(current[i] + ",");
            }
            System.out.println();
        } else {
            for (int j = 0; j < input.get(k).size(); j++) {
                current[k] = input.get(k).get(j);
                cartesianProduct(input, current, k + 1);
            }
        }
    }
}