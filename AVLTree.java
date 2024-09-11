
/** Starter code for AVL Tree
 */
 
// replace package name with your netid
package jxp230045;

import java.util.Comparator;
import java.util.Stack;

public class AVLTree<T extends Comparable<? super T>> extends BinarySearchTree<T> {
    static class Entry<T> {
        T element;
		Entry<T> left, right;
		int height;
        public Entry(T x, Entry<T> left, Entry<T> right) {
            this.element = x;
			this.left = left;
			this.right = right;
            height = 0;
        }
    }

	Entry<T> root;
	int size;
	Stack<Entry<T>> stack;

	private int height(Entry<T> t){
		return t == null ? -1 : t.height;
	}

	private int neededBalance(Entry<T> t){
		return (t == null) ? 0 : height(t.right) - height(t.left);
	}

    public AVLTree() {
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

	public Entry<T> findAVL(T x){
		stack.clear();
		return find(x, root);
	}

	public boolean contains(T x) {
		Entry<T> t = findAVL(x);
		return t != null && t.element.equals(x);
	}

	// TO DO
	public boolean add(T x){
		Entry<T> temp = findAVL(x);
		if(temp == null){
			root = new Entry<>(x, null, null);
			return true;
		}
		if(temp.element.equals(x)){
			return false;
		}
		if (temp.element.compareTo(x) > 0) {
			temp.left = new Entry<>(x, null, null);
		}
		else {
			temp.right = new Entry<>(x, null, null);
		}
		while(!stack.isEmpty()){
			balance(stack.peek());
			stack.pop();
		}
		return true;
	}

	public void updateHeight(Entry<T> t){
		t.height = Math.max(height(t.left), height(t.right)) + 1;
	}

	public Entry<T> balance(Entry<T> t){
		updateHeight(t);
		int balance = neededBalance(t);
		if(balance < -1){
			if(height(t.left.left) > height(t.left.right)){
				t = rightRotate(t);
			}
			else{
				t.left = leftRotate(t.left);
				t = rightRotate(t);
			}
		}
		else if (balance > 1){
			if(height(t.right.right) > height(t.right.left)){
				t = leftRotate(t);
			}
			else{
				t.right = rightRotate(t.right);
				t = leftRotate(t);
			}
		}
		return t;
	}

	public Entry<T> rightRotate(Entry<T> t){
		Entry<T> child = t.left;
		if(child != null){
			t.left = child.right;
			child.right = t;
			updateHeight(t);
			updateHeight(child);
		}
		return child;
	}

	public Entry<T> leftRotate(Entry<T> t){
		Entry<T> child = t.right;
		if(child != null){
			t.right = child.left;
			child.left = t;
			updateHeight(t);
			updateHeight(child);
		}
		return child;
	}

	//Optional. Complete for extra credit
	@Override
    public T remove(T x) {
		return super.remove(x);
    }
	
	/** TO DO
	 *	verify if the tree is a valid AVL tree, that satisfies 
	 *	all conditions of BST, and the balancing conditions of AVL trees. 
	 *	In addition, do not trust the height value stored at the nodes, and
	 *	heights of nodes have to be verified to be correct.  Make your code
	 *  as efficient as possible. HINT: Look at the bottom-up solution to verify BST
	*/

	public boolean isValidBST(Entry<T> t, T min, T max){
		if(t == null){
			return true;
		}
		if((min != null && t.element.compareTo(min) <= 0) || (max != null && t.element.compareTo(max) >= 0)){
			return false;
		}
		return (isValidBST(t.left, min, t.element) && isValidBST(t.right, t.element, max));
	}
	public boolean isValidAVL(Entry<T> t){
		if(t == null){
			return true;
		}
		if(Math.abs(height(t.left) - height(t.right)) > 1){
			return false;
		}
		return (isValidAVL(t.left) && isValidAVL(t.right));
	}

	boolean verify(){
		return (isValidBST(root, null, null) && isValidAVL(root));
	}
}

