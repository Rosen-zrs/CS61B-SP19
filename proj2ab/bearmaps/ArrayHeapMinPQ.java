package bearmaps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

public class ArrayHeapMinPQ<T> implements ExtrinsicMinPQ<T> {

    private ArrayList<PriorityNode> ArrayPQ;
    private HashMap<T, Integer> Index;

    public ArrayHeapMinPQ() {
        ArrayPQ = new ArrayList<>();
        Index = new HashMap<>();
    }

    @Override
    public boolean contains(T item){
        if (isEtmpy()) {
            return false;
        }
        return Index.containsKey(item);
    }

    @Override
    public void add(T item, double priority) {
        if(contains(item)) {
            throw new IllegalArgumentException();
        }
        ArrayPQ.add(new PriorityNode(item, priority));
        Index.put(item, size() - 1);
        climb(size() - 1);
    }

    @Override
    public T getSmallest() {
        if (size() == 0) {
            throw new NoSuchElementException();
        }
        return ArrayPQ.get(1).getItem();
    }

    @Override
    public T removeSmallest() {
        if (size() == 0) {
            throw new NoSuchElementException();
        }
        PriorityNode p = ArrayPQ.get(size());
        ArrayPQ.remove(size());
        ArrayPQ.set(1, p);
        climb(size());
        return ArrayPQ.get(1).getItem();
    }

    @Override
    public int size() {
        return ArrayPQ.size();
    }

    @Override
    public void changePriority(T item, double priority) {
        if (! contains(item)) {
            throw new NoSuchElementException();
        }
        int i = Index.get(item);
        ArrayPQ.get(i).setPriority(priority);
        climb(i);
    }

    private class PriorityNode {
        private T item;
        private double priority;

        PriorityNode(T e, double p) {
            this.item = e;
            this.priority = p;
        }

        T getItem(){
            return this.item;
        }

        double getPriority() {
            return this.priority;
        }

        void setPriority(double priority) {
            this.priority = priority;
        }
    }

    private boolean isEtmpy(){
        return (size() == 0);
    }

    private int leftChild(int k) {
        return k * 2;
    }

    private int rightChild(int k) {
        return k * 2 + 1;
    }

    private int parent(int k) {
        return k / 2;
    }

    private void climb(int i) {
        if (i == 1) {
            return;
        }
        if (ArrayPQ.get(i).getPriority() < ArrayPQ.get(parent(i)).getPriority()) {
            swap(i);
            climb(parent(i));
        }
    }

    private void swap(int k) {
        PriorityNode swaper = ArrayPQ.get(k);
        ArrayPQ.set(k, ArrayPQ.get(parent(k)));
        ArrayPQ.set(parent(k), swaper);
        Index.put(ArrayPQ.get(k).getItem(), k);
        Index.put(swaper.getItem(), parent(k));
    }

    public void printSimpleHeapDrawing() {
        int depth = ((int) (Math.log(this.size()) / Math.log(2)));
        int level = 0;
        int itemsUntilNext = (int) Math.pow(2, level);
        for (int j = 0; j < depth; j++) {
            System.out.print(" ");
        }

        for (int i = 1; i < this.size(); i++) {
            System.out.printf("%d ", ArrayPQ.get(i).getItem());
            if (i == itemsUntilNext) {
                System.out.println();
                level++;
                itemsUntilNext += Math.pow(2, level);
                depth--;
                for (int j = 0; j < depth; j++) {
                    System.out.print(" ");
                }
            }
        }
        System.out.println();
    }

}
