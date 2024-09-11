/**
 * @author rbk, sa
 * Binary search tree (starter code)
 **/

// replace package name with your netid
package jxp230045;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class BinarySearchTree<T extends Comparable<? super T>> implements Iterable<T> {
    static class Entry<T> {
        T element;
        Entry<T> left, right;

        public Entry(T x, Entry<T> left, Entry<T> right) {
            this.element = x;
            this.left = left;
            this.right = right;
        }
    }

    Entry<T> root;
    int size;
    // define stack
    Stack<Entry<T>> stack;

    public BinarySearchTree() {
        root = null;
        size = 0;
        stack = new Stack<>();
    }

    private Entry<T> find(T x, Entry<T> t){
        if(t == null || t.element.equals(x)){
            return t;
        }
        while(true){
            if(t.element.compareTo(x) > 0){
                if(t.left == null){
                    break;
                }
                stack.push(t);
                t = t.left;
            }
            else if(t.element.compareTo(x) < 0){
                if (t.right == null) {
                    break;
                }
                stack.push(t);
                t = t.right;
            }
            else {
                break;
            }
        }
        return t;
    }

    public Entry<T> find(T x){
        stack.clear();
        return find(x, root);
    }

    /**
     * TO DO: Is x contained in tree?
     */

    public boolean contains(T x) {
        Entry<T> t = find(x);
        return t != null && t.element.equals(x);
    }

    /**
     * TO DO: Add x to tree.
     * If tree contains a node with same key, replace element by x.
     * Returns true if x is a new element added to tree.
     */
    public boolean add(T x) {
        if (size == 0) {
            root = new Entry<>(x, null, null);
            size++;
            return true;
        }

        Entry<T> t = find(x);
        if (t.element.compareTo(x) > 0) {
            t.left = new Entry<>(x, null, null);
        }
        else if (t.element.compareTo(x) < 0){
            t.right = new Entry<>(x, null, null);
        }
        else {
            return false;
        }
        size++;
        return true;
    }

    /**
     * TO DO: Remove x from tree.
     * Return x if found, otherwise return null
     */

    public T remove(T x) {
        if (size == 0) {
            return null;
        }

        Entry<T> t = find(x);
        if(t==null || !t.element.equals(x)){
            return null;
        }
        // 1 child
        if(t.left == null || t.right == null){
            splice(t);
        }
        // 2 children
        else{
            stack.push(t);
            Entry<T> minRight = find(x, t.right);
            t.element = minRight.element;
            splice(minRight);
        }
        size--;
        return t.element;
    }

    private void splice(Entry<T> t){
        Entry<T> parent = null;
        if(stack != null && !stack.isEmpty()){
            parent = stack.peek();
        }
        Entry<T> child = (t.left == null) ? t.right : t.left;
        if(parent == null){
            root = child;
        }
        else if(parent.left == t){
            parent.left = child;
        }
        else{
            parent.right = child;
        }
    }

    public Iterator<T> iterator(){
        return null;
    }

    public static void main(String[] args) throws FileNotFoundException {
        BinarySearchTree<Long> bst = new BinarySearchTree<>();
        Scanner sc;
        if (args.length > 0) {
            File file = new File(args[0]);
            sc = new Scanner(file);
        } else {
            sc = new Scanner(System.in);
        }
        String operation = "";
        long operand = 0;
        int modValue = 999983;
        long result = 0;
        // Initialize the timer
        Timer timer = new Timer();

        while (!((operation = sc.next()).equals("End"))) {
            switch (operation) {
                case "Add": {
                    operand = sc.nextInt();
                    if (bst.add(operand)) {
                        result = (result + 1) % modValue;
                    }
                    break;
                }
                case "Remove": {
                    operand = sc.nextInt();
                    if (bst.remove(operand) != null) {
                        result = (result + 1) % modValue;
                    }
                    break;
                }
                case "Contains": {
                    operand = sc.nextInt();
                    if (bst.contains(operand)) {
                        result = (result + 1) % modValue;
                    }
                    break;
                }
            }
        }

        // End Time
        timer.end();

        System.out.println(result);
        System.out.println(timer);
    }

    public void printTree() {
        System.out.print("[" + size + "]");
        printTree(root);
        System.out.println();
    }

    // Inorder traversal of tree
    void printTree(Entry<T> node) {
        if (node != null) {
            printTree(node.left);
            System.out.print(" " + node.element);
            printTree(node.right);
        }
    }

}
