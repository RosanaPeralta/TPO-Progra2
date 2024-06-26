package org.example.Ejercicio2_6.model;

import org.example.model.normal.StaticSet;

import java.util.Random;

public class StaticSuperSet implements SuperSet {

    private int[] array;
    private int count;

    public StaticSuperSet() {
        this.array = new int[10000];
        this.count = 0;
    }

    public void add(int a) {
        if (count == 10000) {
            throw new RuntimeException("Límite de elementos alcanzado");
        }
        for (int i = 0; i < count; i++) {
            if (this.array[i] == a) {
                return;
            }
        }
        this.array[count] = a;
        this.count++;
    }

    public void remove(int a) {
        for (int i = 0; i < count; i++) {
            if (this.array[i] == a) {
                this.array[i] = this.array[count - 1];
                count--;
                return;
            }
        }
    }

    public boolean isEmpty() {
        return this.count == 0;
    }

    public int choose() {
        if (this.count == 0) {
            throw new RuntimeException("No se puede elegir un valor de un conjunto vacio");
        }
        return this.array[new Random().nextInt(this.count)];
    }


    public boolean isSubSet(StaticSet set) {
        boolean subconjunto = true;
        StaticSet aux = new StaticSet();
        while (!set.isEmpty()) {
            boolean found = false;
            int value = set.choose();
            for (int j = 0; j < count; j++) {
                if (array[j] == value) {
                    found = true;
                    aux.add(value);
                    set.remove(value);
                }
            }
            if (!found) {
                subconjunto = false;
                break;
            }
        }
        while (!aux.isEmpty()) {
            int value = aux.choose();
            set.add(value);
            aux.remove(value);
        }
        return subconjunto;
    }

    @Override
    public StaticSet complement(StaticSet set) {
        if (!this.isSubSet(set)) {
            throw new RuntimeException("El set ingresado no es subconjunto del superconjunto");
        }
        StaticSet complement = new StaticSet();
        StaticSet aux1 = new StaticSet();
        StaticSet aux2 = new StaticSet();

        while (!this.isEmpty()) {
            boolean match = false;
            int valueS = this.choose();
            while (!set.isEmpty()) {
                int value = set.choose();
                if (valueS == value) {
                    match = true;
                    break;
                }
                aux2.add(value);
                set.remove(value);
            }
            while (!aux2.isEmpty()) {
                int value = aux2.choose();
                set.add(value);
                aux2.remove(value);
            }
            if (!match) {
                complement.add(valueS);
            }
            aux1.add(valueS);
            this.remove(valueS);
        }
        while (!aux1.isEmpty()) {
            int valueS = aux1.choose();
            this.add(valueS);
            aux1.remove(valueS);
        }
        return complement;
    }
}
