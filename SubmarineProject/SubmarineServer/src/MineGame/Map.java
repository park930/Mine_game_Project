package MineGame;

import java.util.*;

public class Map {
	int width;	
	int num_mine;
	int[][] mineMap;
	int[][] displayMap;
	HashMap<Integer, Integer> minePosition;
	HashMap<Integer, Integer> notMinePosition;

	private ArrayList<Integer> enableButton;
	private ArrayList<Integer> disableButton;
	private ArrayList<Integer> findMineList;
	private HashSet<Integer> mineCheck;
	private HashMap<Integer, Integer> mineHintMap;

	public Map(int width, int num_mine) {
		this.width = width;
		this.num_mine = num_mine;

		enableButton = new ArrayList<>();
		disableButton = new ArrayList<>();
		findMineList = new ArrayList<>();
		notMinePosition = new HashMap<>();
		mineCheck = new HashSet<>();
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
		
		// create mines
		System.out.println("Create  "+num_mine+"  mines");
		Random r = new Random();
		minePosition = new HashMap<>(); 
		for (int i = 0; i < num_mine; i++) {
			int position = r.nextInt(width * width);
			while (minePosition.containsValue(position))   // check repetition
				position = r.nextInt(width * width);			
			minePosition.put(i, position);
			mineCheck.add(position);
		}

		// 지뢰 없는 곳은 자신 기준으로 8x8 주변에 몇개의 지뢰가 있는지 확인
		for (int i=0; i<width; i++) {
			for(int j=0;j<width;j++){
				if (mineCheck.contains(i*width+j))continue;
				// 8x8 범위 확인
				int cnt=0;
				System.out.print("주변 확인 : ");
				for(int a=-1;a<2;a++){
					for(int b=-1;b<2;b++){
						int x = i+a; int y = j+b;
						if (x<0 || y<0 || x>=width || y>=width) continue;
						int check = x*width+y;
						if (mineCheck.contains(check)){
							System.out.print(check+"="+"o ");
							cnt++;
						} else {
							System.out.print(check+"="+"x ");
						}
					}
				}

				System.out.println("\n"+(i*width+j)+"번쨰에 주변 마인은 "+cnt+"개");
				// 다 셋으면, map에 넣기
				notMinePosition.put(i*width+j,cnt);

			}


		}
		
		// deploy mines
		System.out.println("mine positions");
		for (int i = 0; i < num_mine; i++) {
			int x = minePosition.get(i) / width;
			int y = minePosition.get(i) % width;
			System.out.println(x+", "+y);
			mineMap[x][y] = 1;
		}
		
		printMap(mineMap);
		
	}
		
	public int checkMine(int x, int y) {
		int pos = (x*width) + y;
		
		if (minePosition.containsValue(pos)) {
			//System.out.println("   Find mine at ("+x+", "+y+")");		
			return pos;
		}
		else { 
			//System.out.println("   No mine at ("+x+", "+y+")");
			return -1;
		}
		
	}

	public int checkMine(int pos) {
		if (minePosition.containsValue(pos)) {
			return pos;
		}
		else {
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
//		printMap(displayMap);
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

	public void putMineHint(int choice) {
		int surroundMineNum = notMinePosition.get(choice);
		System.out.println("     "+choice+"위치에 근처 마인 갯수:"+surroundMineNum);
		mineHintMap.put(choice,surroundMineNum);
	}
}
