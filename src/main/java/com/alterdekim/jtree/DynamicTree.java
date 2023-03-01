package com.alterdekim.jtree;

import java.util.*;

public class DynamicTree<E> extends JTree<E> {

    private E key;
    private List<DynamicTree<E>> children;
    private DynamicTree<E> parent;

    public DynamicTree( E key ) {
        this.key = key;
        this.children = new LinkedList<>();
        this.parent = null;
    }

    public DynamicTree( E key, DynamicTree<E> parent ) {
        this.key = key;
        this.children = new LinkedList<>();
        this.parent = parent;
    }

    @Override
    void setParent(JTree<E> p) {
        this.parent = (DynamicTree<E>) p;
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
    public DynamicTree<E> getParent() {
        return this.parent;
    }

    private ArrayList<E> getNonEndPoints( DynamicTree<E> node ) {
        ArrayList<E> ends = new ArrayList<>();
        for( DynamicTree<E> n : node.children ) {
            if( n.size() > 0 ) {
                ends.add(n.getKey());
                ends.addAll(getNonEndPoints(n));
            }
        }
        return ends;
    }

    @Override
    public List<E> getNonEndPoints( boolean ignoreFirst ) {
        ArrayList<E> ends = new ArrayList<>();
        for( DynamicTree<E> n : this.children ) {
            if( n.size() > 0 ) {
                if( !ignoreFirst ) {
                    ends.add(n.getKey());
                }
                ends.addAll(getNonEndPoints(n));
            }
        }
        return ends;
    }

    private ArrayList<E> getEndPoints( DynamicTree<E> node ) {
        ArrayList<E> ends = new ArrayList<>();
        for( DynamicTree<E> n : node.children ) {
            if( n.size() == 0 ) {
                ends.add(n.getKey());
            } else {
                ends.addAll(getEndPoints(n));
            }
        }
        return ends;
    }

    @Override
    public List<E> getEndPoints() {
        ArrayList<E> ends = new ArrayList<>();
        for( DynamicTree<E> n : this.children ) {
            if( n.size() == 0 ) {
                ends.add(n.getKey());
            } else {
                ends.addAll(getEndPoints(n));
            }
        }
        return ends;
    }

    @Override
    public DynamicTree<E> getChild(int index) {
        return this.children.get(index);
    }

    @Override
    public int size() {
        return this.children.size();
    }

    @Override
    public int recursiveSize() {
        int s = this.children.size();
        for( DynamicTree<E> n : this.children ) {
            s += n.recursiveSize();
        }
        return s;
    }

    @Override
    public boolean isEmpty() {
        return this.children.size() == 0;
    }

    @Override
    public boolean contains(JTree<E> o) {
        return this.children.contains(o);
    }

    @Override
    public boolean containsRecursive(JTree<E> o) {
        boolean s = this.children.contains(o);
        for( DynamicTree<E> n : this.children ) {
            s = n.containsRecursive(n) || s;
        }
        return s;
    }

    @Override
    public boolean containsKey(E key) {
        for( DynamicTree<E> node : children ) {
            if( node.getKey().equals(key) ) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsKeyRecursive(E key) {
        boolean s = getKey().equals(key);
        for( DynamicTree<E> n : this.children ) {
            s = n.containsKeyRecursive(n.getKey()) || s;
        }
        return s;
    }

    @Override
    public Iterator<JTree<E>> iterator() {
        return new Iterator<JTree<E>>() {
            private int i = -1;

            @Override
            public boolean hasNext() {
                return children.size() > (i+1);
            }

            @Override
            public DynamicTree<E> next() {
                i++;
                return children.get(i);
            }
        };
    }

    @Override
    public boolean addChild(JTree<E> child) {
        child.setParent(this);
        return this.children.add((DynamicTree<E>) child);
    }

    @Override
    public boolean removeChild(int index) {
        return this.children.remove(index) != null;
    }

    @Override
    public boolean containsAll(List<JTree<E>> c) {
        return this.children.containsAll(c);
    }

    @Override
    public boolean containsAllRecursive(List<JTree<E>> c) {
        boolean s = this.children.containsAll(c);
        for( DynamicTree<E> n : this.children ) {
            s = n.containsAllRecursive(c) || s;
        }
        return s;
    }

    @Override
    public boolean addAllChildren(List<JTree<E>> c) {
        ArrayList<DynamicTree<E>> list = new ArrayList<>();
        c.forEach(n -> list.add((DynamicTree<E>) n));
        return this.children.addAll(list);
    }

    @Override
    public boolean removeAll(List<JTree<E>> c) {
        return this.children.removeAll(c);
    }

    @Override
    public boolean retainAll(List<JTree<E>> c) {
        return this.children.retainAll(c);
    }

    @Override
    public boolean removeAllRecursive(List<JTree<E>> c) {
        boolean s = this.children.removeAll(c);
        for( DynamicTree<E> n : this.children ) {
            s = n.removeAllRecursive(c) || s;
        }
        return s;
    }

    @Override
    public boolean retainAllRecursive(List<JTree<E>> c) {
        boolean s = this.children.retainAll(c);
        for( DynamicTree<E> n : this.children ) {
            s = n.retainAllRecursive(c) || s;
        }
        return s;
    }

    @Override
    public void clear() {
        this.children.clear();
    }

    @Override
    public boolean isStrict() {
        return false;
    }
}
