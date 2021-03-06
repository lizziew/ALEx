
public class Environment {

	int dimensions;
	Item[][] stuff;

	
	public Environment(int dimen){
		dimensions = dimen;
		stuff = new Item[dimensions][dimensions];
	}
	
	public Environment copy(){
		Environment rtn = new Environment(dimensions);
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

	public Item removeItem(int x, int y)
	{
		Item i = stuff[x][y];
		stuff[x][y] = null;
		return i;
	}
	
	public Item[][] getStuff(){
		return stuff;
	}
	
}
