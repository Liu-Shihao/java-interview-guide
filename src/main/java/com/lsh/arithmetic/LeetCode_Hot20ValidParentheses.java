package com.lsh.arithmetic;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * 20. 有效的括号
 * https://leetcode.cn/problems/valid-parentheses/description/?envType=study-plan-v2&envId=top-100-liked
 * 
 * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。
 * s 仅由括号 '()[]{}' 组成
 * 有效字符串需满足：
 * 
 * 左括号必须用相同类型的右括号闭合。
 * 左括号必须以正确的顺序闭合。
 * 每个右括号都有一个对应的相同类型的左括号。
 * 
 */
public class LeetCode_Hot20ValidParentheses {

    /**
     * 最先出现的左括号最后匹配
     * 一旦有先进后出的顺序可以用栈 Stack
     */
    public boolean isValid(String s) {
        // 如果字符串长度为奇数，则直接返回 false
        if (s.length() % 2 == 1) {
            return false;
        }
        //右括号为 key，左括号为 value
        Map<Character, Character> pairs = new HashMap<Character, Character>() {{
            put(')', '(');
            put(']', '[');
            put('}', '{');
        }};
        
        // Stack<Character> stack = new Stack<>();
        // 使用 Deque 替代 Stack 的实现
        // 更推荐使用这种方式，因为性能更好
        Deque<Character> stack = new LinkedList<Character>();

        //栈处理逻辑：
        //1.遇到左括号，直接压入栈中；
        //2.遇到右括号，则判断栈顶元素是否是左括号，如果是，则弹出栈顶元素，否则返回 false
        for (char c : s.toCharArray()) {
            if (pairs.containsKey(c)) {
                //如果当前字符是右括号，则需要判断栈顶元素是否是左括号
                if(stack.isEmpty() || stack.peek() != pairs.get(c)){
                    //如果栈为空，或者栈顶元素不等于当前元素的左括号，则返回 false
                    return false;
                }
                //如果栈顶元素等于当前元素的左括号，则弹出栈顶元素
                stack.pop();
            }else{
                //如果当前元素是左括号，则压入栈中
                stack.push(c);
            }
        }
        return stack.isEmpty();
        
    }

    public static void main(String[] args) {
        //在 Java 的 Deque 中，add、push 和 offer 都是添加元素的方法
        // add() 方法在队尾插入元素，如果队列已满则抛出异常（队列用法）
        // offer() 方法在队尾插入元素，如果队列已满则返回 false （队列用法）
        // push() 方法在队首插入元素，如果队列已满则抛出异常  （栈用法）
        // pop() 方法从队首移除元素，如果队列为空则抛出异常（栈用法）
        // poll() 方法从队首移除元素，如果队列为空则返回 null （队列用法）
        // peek() 方法返回队首元素，但不移除它，如果队列为空则返回 null

        Deque<Integer> stack = new LinkedList<Integer>();
        //队列用法，先进先出：当只使用 offer() 和 poll() 时，它就是队列（FIFO）
        stack.offer(1);
        stack.offer(2);
        stack.offer(3);
        System.out.println(stack.poll());//1
        System.out.println(stack.poll());//2
        System.out.println(stack.poll());//3

        //栈用法，先进后出：当只使用 push() 和 pop() 时，它就是栈（LIFO）
        stack.push(4);
        stack.push(5);
        stack.push(6);
        System.out.println(stack.pop());//6
        System.out.println(stack.pop());//5
        System.out.println(stack.pop());//4

    }

    
}