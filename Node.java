class Node
{
	
	private int position;
	private int my_player;
	private String node_name;
	private int value;
	private boolean max_node;
	private int depth;
	private int check = 0;
	private boolean terminal_node;
	private int[] temp_board;
	boolean leaf ;
	private boolean explored=false;
	private int count;
	private int alpha;
	private int beta;
	private int flag=0;
	private int unique;
	private int temp_alpha;
	private int temp_beta;
	
	public Node(int position, int my_player, String node_name, boolean max_node, int value,int depth, int unique,int alpha,int beta) {
		super();
		this.position = position;
		this.my_player = my_player;
		this.node_name = node_name;
		this.max_node = max_node;
		this.depth = depth;
		this.value=value;
		this.unique=unique;
		leaf=true;
		this.alpha=alpha;
		this.beta=beta;
		this.temp_alpha=alpha;
		this.temp_beta=beta;
		
		flag=1;
		
	}
	public int getTemp_alpha() {
		return temp_alpha;
	}
	public void setTemp_alpha(int temp_alpha) {
		this.temp_alpha = temp_alpha;
		if(this.temp_alpha < alpha)
			this.temp_alpha=alpha;
		if(this.temp_alpha<beta)
			alpha=this.temp_alpha;
	}
	public int getTemp_beta() {
		return temp_beta;
	}
	public void setTemp_beta(int temp_beta) {
		this.temp_beta = temp_beta;
		if(this.temp_beta>beta)
			this.temp_beta=beta;
		if(this.temp_beta>alpha)
			beta=this.temp_beta;
	}
	public int getAlpha() {
		
		return alpha;
	}
	
	public int getBeta() {
		return beta;
	}
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public boolean isExplored() {
		return explored;
	}

	public void setExplored(boolean recently_updated) {
		this.explored = recently_updated;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public Node(int position, int my_player, String node_name, boolean max_node, int value,int depth, int unique) {
		super();
		this.position = position;
		this.my_player = my_player;
		this.node_name = node_name;
		this.max_node = max_node;
		this.depth = depth;
		this.value=value;
		this.unique=unique;
		leaf=true;
		
	}
	public int getUnique() {
		return unique;
	}



	public void setUnique(int unique) {
		this.unique = unique;
	}



	public boolean isTerminal_node() {
		return terminal_node;
	}



	public void setTerminal_node(boolean terminal_node) {
		this.terminal_node = terminal_node;
	}



	public int getDepth() {
		return depth;
	}



	public void setDepth(int depth) {
		this.depth = depth;
	}



	public int returnValue(int x)
	{
		if(max_node)
		{
			if(x>value)
				return x;
			else
				return value;
		}
		else
			if(x<value)
				return x;
			else
				return value;
	}
	
	public boolean isMax_node() {
		return max_node;
	}

	public void setMax_node(boolean max_node) {
		this.max_node = max_node;
		if(max_node)
			value=Integer.MIN_VALUE;
		else
			value=Integer.MAX_VALUE;
	}

	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public int getMy_player() {
		return my_player;
	}
	public void setMy_player(int my_player) {
		this.my_player = my_player;
	}
	public String getNode_name() {
		return node_name;
	}
	public void setNode_name(String node_name) {
		this.node_name = node_name;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value,int [] temp) {
		if(terminal_node)
			this.value=value;
		else
		if(max_node)
		{
			if(value>this.value )
			{
				this.value=value;
				check++;
				
			}
		}
		else
			explored=false;
		if(!max_node)
		{
			if(value<this.value)
			{
				this.value=value;
				check++;
			
			}
		}
		else	
		explored=false;
		
	}
	public int getCheck() {
		return check;
	}



	public void setCheck(int check) {
		this.check = check;
	}



	public void setTemp_board(int[] temp_board) {
		this.temp_board=new int[temp_board.length];
		System.arraycopy(temp_board, 0, this.temp_board, 0, temp_board.length);
		
	}



	public int[] getTemp_board() {
		return temp_board;
	}



	public String toString()
	{
		String node_type=max_node?"MAX":"MIN";
		if(flag==0)
		 return node_name+","+depth+","+getVal(value);
		else
			return node_name+","+depth+","+getVal(value)+","+getVal(alpha)+","+getVal(beta)+" "+unique;
		
	}



	private String getVal(int value2) {
		// TODO Auto-generated method stub
		if(value2==Integer.MAX_VALUE)
			return "Infinity";
		else if(value2==Integer.MIN_VALUE)
			return "-Infinity";
		else return value2+"";
		
	}
	
}
