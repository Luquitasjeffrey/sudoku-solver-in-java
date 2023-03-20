import java.util.ArrayList; 
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text file

class sudoku{
	public int[][] grid=new int[9][9];
	
	public sudoku(String txt) {
		int j=0;
		for(int i=0; i<txt.length(); i++) {
			switch(txt.charAt(i)) {
				case '0':
					grid[j/9][j%9]=0;
					j++;
					break;
				case '1':
					grid[j/9][j%9]=1;
					j++;
					break;
				case '2':
					grid[j/9][j%9]=2;
					j++;
					break;
				case '3':
					grid[j/9][j%9]=3;
					j++;
					break;
				case '4':
					grid[j/9][j%9]=4;
					j++;
					break;
				case '5':
					grid[j/9][j%9]=5;
					j++;
					break;
				case '6':
					grid[j/9][j%9]=6;
					j++;
					break;
				case '7':
					grid[j/9][j%9]=7;
					j++;
					break;
				case '8':
					grid[j/9][j%9]=8;
					j++;
					break;
				case '9':
					grid[j/9][j%9]=9;
					j++;
					break;
				default:
					break;
			}
		}
	}
	
	private sudoku(sudoku s) {
		for(int i=0; i<9; i++) {
			for(int j=0; j<9; j++) {
				this.grid[i][j]=s.grid[i][j];
			}
		}
	}
	
	public ArrayList<Integer> valid_for_pos(int x, int y){
		ArrayList<Integer> ret=new ArrayList<>();
		boolean still_valid[]=new boolean[10];
		
		for(int i=0; i<10; i++) still_valid[i]=true;
		
		for(int i=0; i<9; i++) {
			still_valid[this.grid[i][x]]=false;
		}
		
		for(int j=0; j<9; j++) {
			still_valid[this.grid[y][j]]=false;
		}
		
		int startx=x-x%3;
		int starty=y-y%3;
		
		for(int i=0; i<3; i++) {
			for(int j=0; j<3; j++) {
				still_valid[this.grid[i+starty][j+startx]]=false;
			}
		}
		
		for(int i=1; i<10; i++) {
			if(!still_valid[i]) {
				continue;
			}
			ret.add(i);
		}
		return ret;
	}
	
	private class pos{
		public int x, y;
		public boolean is_solved;
		public ArrayList<Integer> valid_moves;
	}
	
	
	private pos get_best_move() {
		pos ret=new pos();
		boolean doitagain;
		boolean issolved;
		do {
			doitagain=false;
			issolved=true;
			int best=9;
			for(int i=0; i<9; i++) {
				for(int j=0; j<9; j++) {
					if(this.grid[i][j]!=0) {
						continue;
					}
					issolved=false;
					ArrayList<Integer> valids=this.valid_for_pos(j, i);
					if(valids.size()==1) {
						doitagain=true;
						this.grid[i][j]=valids.get(0).intValue();
						continue;
					}
					if(valids.size()==0) {
						return null;
					}
					//else
					if(valids.size()<best) {
						best=valids.size();
						ret.x=j;
						ret.y=i;
						ret.valid_moves=valids;
					}
				}
			}
			
		}while(doitagain);
		ret.is_solved=issolved;
		return ret;
	}
	
	private void cpy(sudoku s) {
		for(int i=0; i<9; i++) {
			for(int j=0; j<9; j++) {
				this.grid[i][j]=s.grid[i][j];
			}
		}
	}
	
	public boolean solve() {
		pos bests=this.get_best_move();
		if(bests==null) {
			return false;
		}
		if(bests.is_solved) {
			return true; 
		}
		
		//recursive case
		for(int i=0; i<bests.valid_moves.size(); i++) {
			sudoku s=new sudoku(this);
			s.grid[bests.y][bests.x]=bests.valid_moves.get(i).intValue();
			if(s.solve()) {
				this.cpy(s);
				return true;
			}
		}
		
		return false;
	}
	
	public void print() {
		System.out.printf("\n");
		for(int i=0; i<9; i++) {
			for(int j=0; j<9; j++) {
				System.out.print(" "+this.grid[i][j]);
				if(j%3==2) {
					System.out.printf(" ");
				}
			}
			
			String prt;
			if(i%3==2) {
				prt="\n\n";
			}
			else {
				prt="\n";
			}
			System.out.printf("%s", prt);
		}
	}
	
}



public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		File input=new File(args[0]);
		Scanner readder=new Scanner(input);
		String data="";
		while(readder.hasNextLine()) {
			String append=readder.nextLine();
			data+=append;
		}
				
		sudoku s=new sudoku(data);
		s.print();
		
		System.out.printf("\n");
		
		s.solve();
		
		
		s.print();
		
		System.out.printf("\n");
		
		readder.close();
		
		
	}
}
