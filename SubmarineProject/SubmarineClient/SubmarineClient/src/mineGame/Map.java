package mineGame;

import java.util.ArrayList;
import java.util.HashMap;

public class Map {
	int width;	
	int num_mine;
	int[][] mineMap;
	int[][] displayMap;
	HashMap<Integer, Integer> minePosition;
	private ArrayList<Integer> enableButton;
	private ArrayList<Integer> disableButton;
	private ArrayList<Integer> findMineList;
	private HashMap<Integer, Integer> mineHintMap;

	public Map(int width, int num_mine) {
		this.width = width;
		this.num_mine = num_mine;

		enableButton = new ArrayList<>();
		disableButton = new ArrayList<>();
		findMineList = new ArrayList<>();
		mineHintMap = new HashMap<>();

		// create map
		System.out.println("Create  "+ width+" X "+ width + "  map");
		mineMap = new int[width][width];
		displayMap = new int[width][width];
		for (int i=0; i<width*width; i++) {
			mineMap[i/width][i%width] = 0;
			displayMap[i/width][i%width] = 0;
			enableButton.add(i);
		}

		printMap(mineMap);
		
	}
		
	public int checkMine(int x, int y) {
		int pos = (x*width) + y;
		
		if (minePosition.containsValue(pos)) {
			System.out.println("   Find mine at ("+x+", "+y+")");		
			return pos;
		}
		else { 
			System.out.println("   No mine at ("+x+", "+y+")");
			return -1;
		}
		
	}

	public void printMap(int[][] a) {
		System.out.println();
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i][0]);
            for (int j = 1; j < a[0].length; j++) 
                System.out.print(" " + a[i][j]);            
            System.out.println();
        }
    }
	
	public void updateMap(int x, int y) {
		displayMap[x][y]=1;
		printMap(displayMap);
	}

	public ArrayList<Integer> getEnableButton() {
		return enableButton;
	}

	public void setEnableButton(ArrayList<Integer> enableButton) {
		this.enableButton = enableButton;
	}

	public ArrayList<Integer> getDisableButton() {
		return disableButton;
	}

	public void setDisableButton(ArrayList<Integer> disableButton) {
		this.disableButton = disableButton;
	}

	public ArrayList<Integer> getFindMineList() {
		return findMineList;
	}

	public void setFindMineList(ArrayList<Integer> findMineList) {
		this.findMineList = findMineList;
	}

	public HashMap<Integer, Integer> getMineHintMap() {
		return mineHintMap;
	}

	public void setMineHintMap(HashMap<Integer, Integer> mineHintMap) {
		this.mineHintMap = mineHintMap;
	}
}
