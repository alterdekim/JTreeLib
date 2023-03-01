package com.alterdekim.jtree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BinaryTree<E> extends JTree<E> {

    private E key;
    private BinaryTree<E> left = null;
    private BinaryTree<E> right = null;
    private BinaryTree<E> parent;

    public BinaryTree( E key ) {
        this.key = key;
        this.parent = null;
    }

    public BinaryTree( E key, BinaryTree<E> parent ) {
        this.key = key;
        this.parent = parent;
    }

    @Override
    void setParent( JTree<E> parent ) {
        this.parent = (BinaryTree<E>) parent;
    }

    @Override
    public boolean hasParent() {
        return this.parent != null;
    }

    @Override
    public E getKey() {
        return this.key;
    }

    @Override
    public BinaryTree<E> getParent() {
        return this.parent;
    }

    private ArrayList<E> getNonEndPoints(BinaryTree<E> node ) {
        ArrayList<E> ends = new ArrayList<>();
        Iterator<JTree<E>> it = iterator();
        while( it.hasNext() ) {
            JTree<E> n = it.next();
            if( n.size() > 0 ) {
                ends.add(n.getKey());
                ends.addAll(getNonEndPoints((BinaryTree<E>) n));
            }
        }
        return ends;
    }

    @Override
    public List<E> getNonEndPoints( boolean ignoreFirst ) {
        ArrayList<E> ends = new ArrayList<>();
        Iterator<JTree<E>> it = iterator();
        while( it.hasNext() ) {
            JTree<E> n = it.next();
            if( n.size() > 0 ) {
                if( !ignoreFirst ) {
                    ends.add(n.getKey());
                }
                ends.addAll(getNonEndPoints((BinaryTree<E>)n));
            }
        }
        return ends;
    }

    private ArrayList<E> getEndPoints( BinaryTree<E> node ) {
        ArrayList<E> ends = new ArrayList<>();
        Iterator<JTree<E>> it = iterator();
        while( it.hasNext() ) {
            JTree<E> n = it.next();
            if( n.size() == 0 ) {
                ends.add(n.getKey());
            } else {
                ends.addAll(getEndPoints((BinaryTree<E>)n));
            }
        }
        return ends;
    }

    @Override
    public List<E> getEndPoints() {
        ArrayList<E> ends = new ArrayList<>();
        Iterator<JTree<E>> it = iterator();
        while( it.hasNext() ) {
            JTree<E> n = it.next();
            if( n.size() == 0 ) {
                ends.add(n.getKey());
            } else {
                ends.addAll(getEndPoints((BinaryTree<E>)n));
            }
        }
        return ends;
    }

    public BinaryTree<E> getLeft() {
        return this.left;
    }

    public BinaryTree<E> getRight() {
        return this.right;
    }

    @Override
    public BinaryTree<E> getChild( int index ) {
         return index == 0 ? left : right;
    }

    @Override
    public int size() {
        return 2;
    }

    @Override
    public int recursiveSize() {
        int s = size();
        Iterator<JTree<E>> it = iterator();
        while( it.hasNext() ) {
            JTree<E> n = it.next();
            s += n.recursiveSize();
        }
        return s;
    }

    @Override
    public boolean isEmpty() {
        return left == null && right == null;
    }

    @Override
    public boolean contains(JTree<E> o) {
        return left.equals((BinaryTree<E>) o) || right.equals((BinaryTree<E>) o);
    }

    @Override
    public boolean containsRecursive(JTree<E> o) {
        boolean s = contains(o);
        s = left.containsRecursive(left) || s;
        s = right.containsRecursive(right) || s;
        return s;
    }

    @Override
    public boolean containsKey(E key) {
        return left.getKey().equals(key) || right.getKey().equals(key);
    }

    @Override
    public boolean containsKeyRecursive(E key) {
        boolean s = getKey().equals(key);
        s = left.containsKeyRecursive(left.getKey()) || s;
        s = right.containsKeyRecursive(right.getKey()) || s;
        return s;
    }

    @Override
    public Iterator<JTree<E>> iterator() {
        return new Iterator<JTree<E>>() {
            private int i = -1;

            @Override
            public boolean hasNext() {
                return size() > (i+1);
            }

            @Override
            public BinaryTree<E> next() {
                i++;
                return getChild(i);
            }
        };
    }

    @Override
    public boolean addChild(JTree<E> child) {
        if( left == null ) {
            left = (BinaryTree<E>) child;
            return true;
        } else if( right == null ) {
            right = (BinaryTree<E>) child;
            return true;
        }
        return false;
    }

    @Override
    public boolean removeChild(int index) {
        index = index > 1 ? 1 : (index < 0) ? 0 : index;
        if( index == 0 ) {
            left = null;
        } else {
            right = null;
        }
        return true;
    }

    @Override
    public boolean containsAll(List<JTree<E>> c) {
        if( c.size() > 2 ) { return false; }
        return (left.equals(c.get(0)) && right.equals(c.get(1))) || (left.equals(c.get(1)) && right.equals(c.get(0)));
    }

    @Override
    public boolean containsAllRecursive(List<JTree<E>> c) {
        boolean s = containsAll(c);
        s = left.containsAllRecursive(c) || s;
        s = right.containsAllRecursive(c) || s;
        return s;
    }

    @Override
    public boolean addAllChildren(List<JTree<E>> c) {
        if( c.size() > 2 ) { return false; }
        left = (BinaryTree<E>) c.get(0);
        right = (BinaryTree<E>) c.get(1);
        return true;
    }

    @Override
    public boolean removeAll(List<JTree<E>> c) {
        if( c.size() > 2 ) { return false; }
        if( c.contains(left) ) { left = null; }
        if( c.contains(right) ) { right = null; }
        return true;
    }

    @Override
    public boolean retainAll(List<JTree<E>> c) {
        if( c.size() > 2 ) { return false; }
        if( !c.contains(left) ) { left = null; }
        if( !c.contains(right) ) { right = null; }
        return true;
    }

    @Override
    public boolean removeAllRecursive(List<JTree<E>> c) {
        boolean s = removeAll(c);
        s = left.removeAllRecursive(c) || s;
        s = right.removeAllRecursive(c) || s;
        return s;
    }

    @Override
    public boolean retainAllRecursive(List<JTree<E>> c) {
        boolean s = retainAll(c);
        s = left.retainAllRecursive(c) || s;
        s = right.retainAllRecursive(c) || s;
        return s;
    }

    @Override
    public void clear() {
        this.right = null;
        this.left = null;
    }

    @Override
    public boolean isStrict() {
        return true;
    }
}
