package com.alterdekim.jtree;

public class JTreeUtils {
    private static String recursivePrint( String s, JTree<Integer> n, String prefix ) {
        for( int i = 0; i < n.size(); i++ ) {
            if( !n.isEmpty() ) {
                s += prefix + n.getChild(i).getKey().toString() + "\r\n";
                s = recursivePrint(s, n.getChild(i), prefix + n.getChild(i).getKey().toString() + " -> ");
            }
        }
        return s;
    }

    public static String toStringAll( JTree<Integer> t ) {
        return t.getClass().toString() + " key = " + t.getKey().toString() + "\r\n" + JTreeUtils.recursivePrint("", t, t.getKey() + " -> ");
    }
}
