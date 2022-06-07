package com.entisy.techniq.core.util.entisy.betterLists;

public class Triplet<A, B, C> {

    private SimpleList<A> as = new SimpleList<>();
    private SimpleList<B> bs = new SimpleList<>();
    private SimpleList<C> cs = new SimpleList<>();

    public A getAFromB(B b) {
        for (int i = 0; i < bs.size(); i++) {
            if (b.equals(bs.get(i))) {
                return as.get(i);
            }
        }
        return null;
    }

    public A getAFromC(C c) {
        for (int i = 0; i < cs.size(); i++) {
            if (c.equals(cs.get(i))) {
                return as.get(i);
            }
        }
        return null;
    }

    public B getBFromA(A a) {
        for (int i = 0; i < as.size(); i++) {
            if (a.equals(as.get(i))) {
                return bs.get(i);
            }
        }
        return null;
    }

    public B getBFromC(C c) {
        for (int i = 0; i < cs.size(); i++) {
            if (c.equals(cs.get(i))) {
                return bs.get(i);
            }
        }
        return null;
    }

    public C getCFromA(A a) {
        for (int i = 0; i < as.size(); i++) {
            if (a.equals(as.get(i))) {
                return cs.get(i);
            }
        }
        return null;
    }

    public C getCFromB(B b) {
        for (int i = 0; i < bs.size(); i++) {
            if (b.equals(bs.get(i))) {
                return cs.get(i);
            }
        }
        return null;
    }

    public void append(A a, B b, C c) {
        as.append(a);
        bs.append(b);
        cs.append(c);
    }
}
