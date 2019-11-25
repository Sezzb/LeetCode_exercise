package com.zifuchuan;
import java.util.ArrayList;
import java.util.List;

public class SearchSuggestionsSystem {
    private class TrieNode{
        boolean end = false;
        String str = null;
        int count = 0;//测试的字符串可能会有重复的
        TrieNode[] children = new TrieNode[26];//对应26个子节点
    }

    private class Trie{
        TrieNode root = new TrieNode();//私有变量
        public void insert(String[] products){
            for(String str : products){
                insertWord(str); //一个一个字符进行插入
            }
        }
        private void insertWord(String products){
            TrieNode node = root;//14行，从根节点开始插入
            for(char c : products.toCharArray()){//转化成数组
                if(node.children[c - 'a'] == null){
                    node.children[c - 'a'] = new TrieNode();
                }
                node = node.children[c - 'a'];
            }
            //上面的这个for循环是判断有没有子节点，没有就进行创建
            if(node.end != true){
                node.end = true;
                node.str = products;
            }
            //上面的for循环已经创建节点到指责格字符串的末尾
            //只要进行尾部end标志改为true和将放入字符串就行了
            node.count++;//增加存入个数
        }
        public List<List<String>> searchWord(String word){
            List<List<String>> result = new ArrayList<>();
            for(int i = 1; i <= word.length(); i++){
                result.add(search(word.substring(0, i)));
                //因为要进行实时的搜索，所以要对字符串进行切割
            }
            return result;//返回的是长度为3的列表
        }
        private List<String> search(String pattern){
            List<String> result = new ArrayList<>();
            TrieNode node = root;//从根节点开始搜索
            for(char c : pattern.toCharArray()){
                if(node.children[c - 'a'] == null){
                    return result;//如果没有找到就返回空
                }
                node = node.children[c - 'a'];
            }
            Solution(node, result);//解出搜索结果
            return result;
        }
        private void Solution(TrieNode root, List<String> result){
            if(root.end){//这个root和之前出现过的不一样，这个是搜索字符串的结果
                for(int i = 0; i < root.count; i++){
                    result.add(root.str);
                    if(result.size() == 3){
                        return;
                    }
                }//本节点，回溯点
            }
            for(TrieNode node : root.children){
                if(node != null){
                    Solution(node, result);
                    //递归
                }
                if(result.size() == 3){
                    return;
                }
            }
        }
    }

    public List<List<String>> suggestedProducts(String[] products, String searchWord) {
        Trie trie = new Trie();
        trie.insert(products);
        return trie.searchWord(searchWord);
    }
}

