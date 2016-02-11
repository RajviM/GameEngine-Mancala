class return_move
{
	private int[] temp_board;
	private int extra_move;
	private int return_eval_value;
	private return_values rv;
	
	public int[] getTemp_board() {
		return temp_board;
	}


	public int getExtra_move() {
		return extra_move;
	}


	public return_values getRv() {
		return rv;
	}


	public return_move(int[] temp_board, int extra_move, return_values rv) {
		super();
		this.temp_board = temp_board;
		this.extra_move = extra_move;
		this.rv = rv;
	}


	public int getReturn_eval_value() {
		return return_eval_value;
	}

	
	public return_move(int[] temp_board, int extra_move, int return_eval_value) {
		super();
		this.temp_board = temp_board;
		this.extra_move = extra_move;
		this.return_eval_value = return_eval_value;
	}
	
	public int[] getTempBoard()
	{
		return temp_board;
	}
	public int getExtraMove()
	{
		return extra_move;
	}
	
}
