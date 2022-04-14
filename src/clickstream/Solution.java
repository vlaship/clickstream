package clickstream;

import java.util.*;

/*
We have some clickstream data that we gathered on our client's website. Using cookies, we collected snippets of users' anonymized URL histories while
they browsed the site. The histories are in chronological order, and no URL was visited more than once per person.

Write a function that takes two users' browsing histories as input and returns the longest contiguous sequence of URLs that appears in both.

Sample input:

user0 = ["/start", "/green", "/blue", "/pink", "/register", "/orange", "/one/two"]
user1 = ["/start", "/pink", "/register", "/orange", "/red", "a"]
user2 = ["a", "/one", "/two"]
user3 = ["/pink", "/orange", "/yellow", "/plum", "/blue", "/tan", "/red", "/amber", "/HotRodPink", "/CornflowerBlue", "/LightGoldenRodYellow", "/BritishRacingGreen"]
user4 = ["/pink", "/orange", "/amber", "/BritishRacingGreen", "/plum", "/blue", "/tan", "/red", "/lavender", "/HotRodPink", "/CornflowerBlue", "/LightGoldenRodYellow"]
user5 = ["a"]
user6 = ["/pink","/orange","/six","/plum","/seven","/tan","/red", "/amber"]

Sample output:

findContiguousHistory(user0, user1) => ["/pink", "/register", "/orange"]
findContiguousHistory(user0, user2) => [] (empty)
findContiguousHistory(user0, user0) => ["/start", "/green", "/blue", "/pink", "/register", "/orange", "/one/two"]
findContiguousHistory(user2, user1) => ["a"] 
findContiguousHistory(user5, user2) => ["a"]
findContiguousHistory(user3, user4) => ["/plum", "/blue", "/tan", "/red"]
findContiguousHistory(user4, user3) => ["/plum", "/blue", "/tan", "/red"]
findContiguousHistory(user3, user6) => ["/tan", "/red", "/amber"]

n: length of the first user's browsing history
m: length of the second user's browsing history

*/
public class Solution {
    public static void main(String[] argv) {

        String[] user0 = {"/start", "/green", "/blue", "/pink", "/register", "/orange", "/one/two"};
        String[] user1 = {"/start", "/pink", "/register", "/orange", "/red", "a"};
        String[] user2 = {"a", "/one", "/two"};
        String[] user3 = {"/pink", "/orange", "/yellow", "/plum", "/blue", "/tan", "/red", "/amber", "/HotRodPink", "/CornflowerBlue", "/LightGoldenRodYellow", "/BritishRacingGreen"};
        String[] user4 = {"/pink", "/orange", "/amber", "/BritishRacingGreen", "/plum", "/blue", "/tan", "/red", "/lavender", "/HotRodPink", "/CornflowerBlue", "/LightGoldenRodYellow"};
        String[] user5 = {"a"};
        String[] user6 = {"/pink", "/orange", "/six", "/plum", "/seven", "/tan", "/red", "/amber"};

        List<String> max = findContiguousHistory(user0, user1);
        System.out.println(max);

        Map<String, Integer> domainClicks = calculateClicksByDomain(user0);
        printClicksDomain(domainClicks);
    }

    static List<String> findContiguousHistory(String[] user1, String[] user2) {
        List<String> max = new ArrayList<>();
        List<String> seq = new ArrayList<>();
        for (String value : user1) {
            seq.clear();
            for (String s : user2) {
                if (s.equals(value)) {
                    seq.add(value);
                    if (max.size() < seq.size()) {
                        max.clear();
                        max.addAll(seq);
                    }
                } else {
                    //  break;
                }
            }
        }
        return max;
    }

    static void printClicksDomain(Map<String, Integer> domainClicks) {
        domainClicks.forEach((key, value) -> System.out.println(key + " " + value));
    }

    static Map<String, Integer> calculateClicksByDomain(String[] counts) {
        Map<String, Integer> domainClicks = new HashMap<>();
        for (String countLine : counts) {
            String[] clicksDomain = countLine.split(",");
            iterateBySubDomains(domainClicks, clicksDomain[1], Integer.parseInt(clicksDomain[0]));
        }
        return domainClicks;
    }

    static void calculate(Map<String, Integer> domainClicks, String domain, int clicks) {
        int countOfClicks = domainClicks.getOrDefault(domain, 0);
        domainClicks.put(domain, clicks + countOfClicks);
    }

    static void iterateBySubDomains(Map<String, Integer> domainClicks, String domain, int clicks) {
        calculate(domainClicks, domain, clicks);
        if (domain.contains(".")) {
            iterateBySubDomains(domainClicks, removeSubDomain(domain), clicks);
        }
    }

    static String removeSubDomain(String domain) {
        return domain.substring(domain.indexOf(".") + 1);
    }
}