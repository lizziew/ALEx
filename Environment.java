
public class Environment {

	int dimensions; 
	Item[][] stuff;

	public Environment(){
		stuff = new Item[dimensions][dimensions];
	}
	
	public Environment copy(){
		Environment rtn = new Environment();
		for (int i = 0; i<dimensions; i++){
			for (int j = 0; j<dimensions; j++){
				rtn.addItem(i,j,stuff[i][j]);
			}
		}
		return rtn;
	}
	
	public void addItem(int x, int y,Item it){
		stuff[x][y] = it;
	}
	
}
