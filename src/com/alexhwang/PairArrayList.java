package com.alexhwang;

import java.util.ArrayList;

public class PairArrayList extends ArrayList {
	public boolean comparePairs(Pair a, Pair b) {
		return a.getA() == b.getA() && a.getB() == b.getB();
	}

	public boolean has(Pair b) {
		for (int i = 0; i < this.size(); i++) {
			Pair a = (Pair) this.get(i);
			if (comparePairs((Pair) this.get(i), b)) {
				return true;
			}
		}
		return false;
	}
}
