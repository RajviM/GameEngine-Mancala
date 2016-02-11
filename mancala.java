import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;





public class mancala {
	public static final int MAX = Integer.MAX_VALUE;
	public static final int MIN = Integer.MIN_VALUE;
	static int task;
	static int my_player;
	static int cut_off_depth;
	static int [] board_state;
	static List<String> input =new ArrayList<String>();
	static int mancala_position_1;
	static int mancala_position_2;
	static int board_length;
	static int max_eval = Integer.MIN_VALUE;
	static int max_pos;
	static int [] final_board_state;
	static int unique=0;
	static int unique_pos=0;
	static int extra_move_on=0;
	static Stack <Node> result;
	static PrintWriter pw_traverse;
	static PrintWriter pw_nextstate;
	
	 
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//System.out.println("running..");
		
		
		String path1="traverse_log.txt";
		String path2="next_state.txt";
		try {
			pw_traverse=new PrintWriter(path1);
			pw_nextstate=new PrintWriter(path2);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String filename=args[0];
		readFile(filename);
		readInputFromFile(input);
		//pw_traverse.println(Arrays.toString(board_state));
		
		switch(task)
		{
		case 1:
			greedy();
			break;
		case 2:
			mini_max();
			break;
		case 3:
			alpha_beta();
			break;
		}
		
		pw_nextstate.close();
		pw_traverse.close();
		//System.out.println("Done");

	}

	private static void alpha_beta() {
		// TODO Auto-generated method stub
		unique=0;
		result= new Stack<Node>();
		final_board_state=new int[board_length*2+2];
		pw_traverse.println("Node,Depth,Value,Alpha,Beta");
		Node root= new Node(-1, -1, "root", true,getValueOfNode(true),0,++unique,Integer.MIN_VALUE,Integer.MAX_VALUE);
		//pw_traverse.println(root);
		pw_traverse.println(root);
		
		play_alpha_beta(board_state,my_player,0,root);
		Node final_result=null;
		if(!result.isEmpty())
			 final_result= result.pop();
		while(!result.isEmpty())
		{
			Node t = result.pop();
			if(t.getValue()>=final_result.getValue())
				final_result=t;
			
		}
	
		correctFormatprint(final_result.getTemp_board());
	}

	private static void correctFormatprint(int[] temp_board) {
		// TODO Auto-generated method stub
		
		
		String x="";
		for(int i=0;i<board_length;i++)
			x=x+temp_board[mancala_position_2-i-1]+" ";
		pw_nextstate.println(x.trim());
		
		x="";
		for(int i=0;i<board_length;i++)
			x=x+temp_board[i]+" ";
		pw_nextstate.println(x.trim());
		pw_nextstate.println(temp_board[mancala_position_2]);
		pw_nextstate.println(temp_board[mancala_position_1]);
		
		
	}

	private static return_values play_alpha_beta(int[] board_state2, int my_player2, int depth, Node node) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
				depth++;
				//not right
				if(depth>cut_off_depth)
					return new return_values(node.getValue(),node.isMax_node()?node.getTemp_alpha():node.getTemp_beta());
				int [] initial_board = new int [board_state2.length];
				int x=allZero(board_state2);
				if(x!=-1)
				{
					if(x==3)
					{
						initial_board=transferOpponent(board_state2, 1);
						initial_board=transferOpponent(initial_board, 2);
					}
					else
					initial_board=transferOpponent(board_state2, x);
					
						//System.out.println(printboard(initial_board)+"  "+eval(initial_board));
					return new return_values(eval(initial_board),node.isMax_node()?node.getTemp_alpha():node.getTemp_beta());
					
				}
				
				System.arraycopy(board_state2, 0, initial_board,0, board_state.length); 
				for(int i=0;i<board_length;i++)
				{
					//print the first move
					int pos=getPosition(i,my_player2);
					System.arraycopy(initial_board, 0, board_state2,0, board_state.length);
					int [] temp_board = new int [board_state2.length];
					if(!isValid(board_state2,pos))
						continue;
						
					boolean temp;
					
						temp = toggle(node.isMax_node());
					
						
						Node current_node=new Node(pos, my_player2, getNode(pos, my_player2),temp, getValueOfNode(temp),depth,++unique,node.getAlpha(),node.getBeta());
						
						
						return_move m = move(board_state2,current_node);
						temp_board=m.getTempBoard();
						
						int extra_move=m.getExtraMove();
						return_values rv = m.getRv();
						
						
						if(current_node.getDepth()==cut_off_depth)
						{
							current_node.setTerminal_node(true);
							current_node.setValue(eval(temp_board),temp_board);
							
						}
						
						//not right
						int temp_cut_off_depth=cut_off_depth;
						if(extra_move==0)
						{
							node.setCheck(node.getCheck()+1);
							if(!current_node.isTerminal_node())
							pw_traverse.println(current_node);
							if(depth==1)
							{
								current_node.setTemp_board(temp_board);
								
							}
							
							int f=allZero(m.getTemp_board());
							if(f!=-1)
							{
								current_node.setTerminal_node(true);
								current_node.setValue(eval(m.getTemp_board()), m.getTemp_board());
								temp_cut_off_depth=depth;
							}
							
							if(current_node.isTerminal_node())
								pw_traverse.println(current_node);
							if(temp_cut_off_depth==1)
							{
								result.add(current_node);
							}
							
								
							// check for extra move to avoid unnecssary children
							//printing current node
							
							
							//making child node of current node
							int child_value;
							if(current_node.isTerminal_node())
							child_value= current_node.getValue();
							else
								child_value=getValueOfNode(current_node.isMax_node());
							
							Node n = new Node(pos, my_player2, getNode(pos,my_player2), current_node.isMax_node(),child_value, depth,unique,node.getAlpha(),node.getBeta());
								if(depth==1)
									 {
										n.setTemp_board(temp_board);
										
									 }
								
								rv=play_alpha_beta(temp_board, togglePlayer(my_player2), depth,n);
							
									
						}
						
						node.setValue(rv.getReturn_eval_value(),initial_board);
						//if node is min node, update beat 
						//if node is max node update alpha
						
						// if current node is terminal 
						if(current_node.isTerminal_node())
						{
							if(node.isMax_node() )
								node.setTemp_alpha(rv.getReturn_eval_value());
							else
								node.setTemp_beta(rv.getReturn_eval_value());
						}
						else
						{
							if(node.isMax_node() )
								node.setTemp_alpha(rv.getAlpha_beta());
							else
								node.setTemp_beta(rv.getAlpha_beta());
						}
						
							
					
						//printing parent
						pw_traverse.println(node);
						
						if(node.getDepth()==1 && node.getCount()==0)
						{
							
							//pw_traverse.println("  "+printboard(node.getTemp_board())+"  "+return_eval_value);
							if(node.getTemp_board()!=null)
							{
								if(result.isEmpty())
								{
									result.add(node);
									unique_pos=node.getUnique();
									
								}
								else
								{
									if(node.getUnique()==unique_pos)
										result.pop();
									
									result.add(node);
									
									unique_pos=node.getUnique();
								}
								
								
							}
						}
						
						if(violated(node))
						{
							
							break;
						}
						
						
					
				}
				//pw_traverse.println("Node returned :"+node);
				node.setExplored(true);
				
				return new return_values(node.getValue(), node.isMax_node()?node.getTemp_alpha():node.getTemp_beta());
				
			}
	

	private static boolean violated(Node node) {
		// TODO Auto-generated method stub
		if(node.getAlpha()!=node.getTemp_alpha())
		return true;
		if(node.getBeta()!=node.getTemp_beta())
			return true;
		return false;
	}

	private static void mini_max() {
		// TODO Auto-generated method 'stub
		unique=0;
		result= new Stack<Node>();
		
		final_board_state=new int[board_length*2+2];
		Node root= new Node(-1, -1, "root", true,getValueOfNode(true),0,++unique);
		pw_traverse.println("Node,Depth,Value");
		pw_traverse.println(root);
		
		
		play_minimax(board_state,my_player,0,root);
		root.setExplored(true);
		
		Node final_result=null;
		if(!result.isEmpty())
			 final_result= result.pop();
		while(!result.isEmpty())
		{
			Node t = result.pop();
			if(t.getValue()>=final_result.getValue())
				final_result=t;
			
		}
		correctFormatprint(final_result.getTemp_board());
		
		
		
	}


	private static int getValueOfNode(boolean b) {
		// TODO Auto-generated method stub
		if(b)
			return Integer.MIN_VALUE;
		else
			return Integer.MAX_VALUE;
		
	}

	private static int play_minimax(int[] board_state2, int my_player2, int depth, Node node) {
		// TODO Auto-generated method stub
		depth++;
		//not right
		if(depth>cut_off_depth)
			return node.getValue();
		int [] initial_board = new int [board_state2.length];
		int x=allZero(board_state2);
		if(x!=-1)
		{
			if(x==3)
			{
				initial_board=transferOpponent(board_state2, 1);
				initial_board=transferOpponent(initial_board, 2);
			}
			else
			initial_board=transferOpponent(board_state2, x);
			
			return eval(initial_board);
			
		}
		
		System.arraycopy(board_state2, 0, initial_board,0, board_state.length); 
		for(int i=0;i<board_length;i++)
		{
			//print the first move
			
			int pos=getPosition(i,my_player2);
			System.arraycopy(initial_board, 0, board_state2,0, board_state.length);
			int [] temp_board = new int [board_state2.length];
			if(!isValid(board_state2,pos))
				continue;
				
			boolean temp;
			
				temp = toggle(node.isMax_node());
			
				
				Node current_node=new Node(pos, my_player2, getNode(pos, my_player2),temp, getValueOfNode(temp),depth,++unique);
				
				return_move m = move_minimax(board_state2,current_node);
				temp_board=m.getTempBoard();
				
				int extra_move=m.getExtraMove();
				int return_eval_value=m.getReturn_eval_value();
				
				if(current_node.getDepth()==cut_off_depth)
				{
					current_node.setTerminal_node(true);
					current_node.setValue(eval(temp_board),temp_board);
					
				}
				
				
				//not right
				int temp_cut_off_depth=cut_off_depth;
				
				
				
				if(extra_move==0)
				{
					// check for extra move to avoid unnecssary children
					//printing current node
					node.setCheck(node.getCheck()+1);
					if(!current_node.isTerminal_node())
					pw_traverse.println(current_node);
					if(depth==1)
					{
						current_node.setTemp_board(temp_board);
						
					}
					
					int f=allZero(m.getTemp_board());
					if(f!=-1)
					{
						current_node.setTerminal_node(true);
						current_node.setValue(eval(m.getTemp_board()), m.getTemp_board());
						temp_cut_off_depth=depth;
					}
					
					if(current_node.isTerminal_node())
						pw_traverse.println(current_node);
					if(temp_cut_off_depth==1)
					{
						result.add(current_node);
					}
					
						
					//making child node of current node
					int child_value;
					if(current_node.isTerminal_node())
					child_value= current_node.getValue();
					else
						child_value=getValueOfNode(current_node.isMax_node());
					
					Node n = new Node(pos, my_player2, getNode(pos,my_player2), current_node.isMax_node(),child_value, depth,unique);
						if(depth==1)
							 {
								n.setTemp_board(temp_board);
								//pw_traverse.println(printboard(temp_board));
							 }
						
						return_eval_value=play_minimax(temp_board, togglePlayer(my_player2), depth,n);
						
						node.setValue(return_eval_value,initial_board);
						
						
							
						
					
				
				}
				else
				{
					//cz it is already printed
					
					node.setValue(return_eval_value,initial_board);
					node.setExplored(true);
					
					
				}
					
			
				//printing parent
				pw_traverse.println(node);
				
				if(node.getDepth()==1 && node.getCount()==0)
				{
					
					//pw_traverse.println("  "+printboard(node.getTemp_board())+"  "+return_eval_value);
					if(node.getTemp_board()!=null)
					{
						if(result.isEmpty())
						{
							result.add(node);
							unique_pos=node.getUnique();
							
						}
						else
						{
							if(node.getUnique()==unique_pos)
								result.pop();
							
							result.add(node);
							
							unique_pos=node.getUnique();
						}
						
					}
				}
				
				
			
		}
		//pw_traverse.println("Node returned :"+node);
		node.setExplored(true);
		return node.getValue();
		
	}

	
	

	private static int eval(int[] board_state2) {
		// TODO Auto-generated method stub
		int temp=board_state2[mancala_position_1]-board_state2[mancala_position_2];
		if(my_player==1)
			return temp;
		else
			return -temp;
		
	}

	private static boolean toggle(boolean max_node) {
		// TODO Auto-generated method stub
		if(max_node)
		return false;
		else
			return true;
	}

	private static String getNode(int pos, int my_player2) {
		// TODO Auto-generated method stub
		if(pos==-1)
			return "root";
		if(my_player2==1)
		{
			return "B"+(pos+2);
		}
		return "A"+(mancala_position_2-pos+1);
	}

	private static int togglePlayer(int my_player2) {
		// TODO Auto-generated method stub
		if(my_player2==1)
		return 2;
		else
			return 1;
	}

	private static boolean isValid(int[] board_state2, int pos) {
		// TODO Auto-generated method stub
		if(board_state2[pos]==0)
		return false;
		else
			return true;
	}

	private static int getPosition(int i, int my_player2) {
		// TODO Auto-generated method stub
		if(my_player2==1)
		return  i;
		else
			return mancala_position_2-i-1;
	}

	private static return_move move_minimax(int[] board_state2, Node move_node) {
		// TODO Auto-generated method stub
		int my_player2 = move_node.getMy_player();
		int position = move_node.getPosition();
		int depth = move_node.getDepth();
		int stones = board_state2[position];
		int eval_return_value=0;
		
		
		//int temp_board_state[]=new int[board_state.length];
		//System.arraycopy(board_state, 0, temp_board_state, 0, board_state.length);
		board_state2[position]=0;
		int extra_move=0;
		int initial_position=position;
		
		while(stones!=0)
		{
			
			position=increment(position);
			if(mancala_position_2==position && my_player2==1)
				position=increment(position);
			else if(mancala_position_1==position && my_player2==2)
				position=increment(position);
			
			board_state2[position]+=1;
			
			if(snatch(board_state2,stones,position,my_player2))
			{
				//snatch
				//pw_traverse.println("check");
				//pw_traverse.println("before snatch"+Arrays.toString(temp_board_state));
				if(my_player2==1)
				{
					board_state2[mancala_position_1]+=board_state2[mancala_position_2-position-1];
					board_state2[mancala_position_1]+=1;
					board_state2[mancala_position_2-position-1]=0;
					board_state2[position]=0;
				}
				else
				{
					int p1_pos=board_length*2-position;
					board_state2[mancala_position_2]+=board_state2[p1_pos];
					board_state2[mancala_position_2]+=1;
					board_state2[p1_pos]=0;
					board_state2[position]=0;
				}
				
				//pw_traverse.println("after snatch"+Arrays.toString(temp_board_state));
			}
			
			if(checkExtraMove(stones,mancala_position_2,my_player2,position))
			{
				//Extra Move.
				extra_move=1;
				
				move_node.setLeaf(false);
				move_node.setMax_node(toggle(move_node.isMax_node()));
				//seems redundant
				move_node.setTerminal_node(false);
				pw_traverse.println(move_node);
				
				//seems redundant
				move_node.setValue(eval(board_state2),board_state2);
				int x = allZero(board_state2);
				
				if(x!=-1)
				{
					move_node.setTerminal_node(true);
					extra_move=0;
				}
				
				Node n1 = new Node(initial_position, my_player2, getNode(initial_position, my_player2), move_node.isMax_node(),getValueOfNode(move_node.isMax_node()), depth,unique);
				
				n1.setCount(1);
				if(x==-1)
				eval_return_value=play_minimax(board_state2, my_player2, depth-1, n1);
				
				n1.setExplored(true);
				
				
			}
			
			stones--;
		}
		int x = allZero(board_state2);
		if(x!=-1)
		{
			if(x==3)
			{
				board_state2=transferOpponent(board_state2, 1);
				board_state2=transferOpponent(board_state2, 2);
			}
			else
				board_state2=transferOpponent(board_state2, x);
		}
		
		return new return_move(board_state2, extra_move,eval_return_value);
	}
	
private static return_move move(int[] board_state2, Node move_node) {
		// TODO Auto-generated method stub
		int my_player2 = move_node.getMy_player();
		int position = move_node.getPosition();
		int depth = move_node.getDepth();
		int stones = board_state2[position];
		return_values rv = null;
		
		
		//int temp_board_state[]=new int[board_state.length];
		//System.arraycopy(board_state, 0, temp_board_state, 0, board_state.length);
		board_state2[position]=0;
		int extra_move=0;
		int initial_position=position;
		
		while(stones!=0)
		{
			
			position=increment(position);
			if(mancala_position_2==position && my_player2==1)
				position=increment(position);
			else if(mancala_position_1==position && my_player2==2)
				position=increment(position);
			
			board_state2[position]+=1;
			
			if(snatch(board_state2,stones,position,my_player2))
			{
				//snatch
				//pw_traverse.println("check");
				//pw_traverse.println("before snatch"+Arrays.toString(temp_board_state));
				if(my_player2==1)
				{
					board_state2[mancala_position_1]+=board_state2[mancala_position_2-position-1];
					board_state2[mancala_position_1]+=1;
					board_state2[mancala_position_2-position-1]=0;
					board_state2[position]=0;
				}
				else
				{
					int p1_pos=board_length*2-position;
					board_state2[mancala_position_2]+=board_state2[p1_pos];
					board_state2[mancala_position_2]+=1;
					board_state2[p1_pos]=0;
					board_state2[position]=0;
				}
				
				//pw_traverse.println("after snatch"+Arrays.toString(temp_board_state));
			}
			
			if(checkExtraMove(stones,mancala_position_2,my_player2,position))
			{
				//Extra Move.
				extra_move=1;
				
				move_node.setLeaf(false);
				move_node.setMax_node(toggle(move_node.isMax_node()));
				//seems redundant
				move_node.setTerminal_node(false);
				pw_traverse.println(move_node);
				
				//seems redundant
				move_node.setValue(eval(board_state2),board_state2);
				int x = allZero(board_state2);
				
				if(x!=-1)
				{
					move_node.setTerminal_node(true);
					extra_move=0;
				}
				
				Node n1 = new Node(initial_position, my_player2, getNode(initial_position, my_player2), move_node.isMax_node(),getValueOfNode(move_node.isMax_node()), depth,unique,move_node.getAlpha(),move_node.getBeta());	
				
				n1.setCount(1);
				if(x==-1)
				rv=play_alpha_beta(board_state2, my_player2, depth-1, n1);
				
				n1.setExplored(true);
				
				
			}
			
			stones--;
		}
		int x = allZero(board_state2);
		if(x!=-1)
		{
			if(x==3)
			{
				board_state2=transferOpponent(board_state2, 1);
				board_state2=transferOpponent(board_state2, 2);
			}
			else
				board_state2=transferOpponent(board_state2, x); 
			
		}
		
		return new return_move(board_state2, extra_move,rv);
	}

	private static boolean checkExtraMove(int stones, int mancala_position_22, int my_player2,int position) {
		// TODO Auto-generated method stub
		if(stones==1 && position==mancala_position_1 && my_player2==1)
			return true;
		if(stones==1 && position==mancala_position_2 && my_player2==2)
			return true;
			
		return false;
	}

	private static String printboard(int[] board_state2) {
		// TODO Auto-generated method stub
		return Arrays.toString(board_state2);
	}

	private static int increment(int position) {
		// TODO Auto-generated method stub
		return (position+1)% board_state.length;
	}

	private static int play(int[] board_state2, int my_player, int depth, String parent) {
		// TODO Auto-generated method stub
		if(depth>cut_off_depth)
			return -1;
		//prune tree when all zero
		int check=0;
		if(depth<0)
		{
			//check if it is a branch due to extra move
			check =1;
			depth=depth+10;
		}
		int pos;
		if(my_player==1)
			pos=0;
		else
			pos=mancala_position_2-1;
		
		int state_board[]=new int[board_state.length];
		System.arraycopy(board_state2, 0, state_board, 0, board_state.length);
		if(my_player==1)
			pos=0;
		else
			pos=mancala_position_2-1;
		int x = board_length;
		
		while(x!=0)
		{
			
			if(board_state2[pos]==0)
			{
				x--;
				pos=my_player==1?pos+1:pos-1;
				continue;
			}
			
				int y = Integer.MIN_VALUE;
				if(mancala.my_player==my_player)
					y=Integer.MAX_VALUE;
				
				
			
				print_move(pos,my_player,depth,y);
			state_board=move(pos,board_state2,my_player,depth);
			
			
			//if(state_board.length!=1)
			//pw_traverse.println(Arrays.toString(state_board)+" depth "+depth+" player"+my_player);
			
		
			
			//now player 2 turn
			//make sure board is updated.
			//check depth too. - done
			int return_value=0;
			y=999;
			if(state_board.length!=1)
			{
				
				return_value = play(state_board,my_player==1?2:1,depth+1,my_player==1?("B"+(pos+2)):("A"+(mancala_position_2-pos+1)));
				 y = state_board[mancala_position_1]-state_board[mancala_position_2];
				y=my_player==1?y:-y;
				
			}
			
			

			
			if(return_value!=-1)
			print_move(pos,my_player,depth,8888);
			pw_traverse.println(parent);
			
			pos=my_player==1?pos+1:pos-1;
			
			//if(check!=1 && depth==1)
				//pw_traverse.println("root,0");
			
			x--;
		}
		
		
		
		return 0;
		
		
		
	}

	private static void print_move(int pos, int my_player, int depth, int y) {
		// TODO Auto-generated method stub
		
		if(my_player==1)
		{
			
			pw_traverse.println("B"+(pos+2)+","+depth+","+y);
		}
		else
		{ 
			pos=mancala_position_2-pos;
			pw_traverse.println("A"+(pos+1)+","+depth+","+y);
		}
	}

	private static void greedy() {
		// TODO Auto-generated method stub
		
		final_board_state=new int[board_length*2+2];
		cut_off_depth=1;
		mini_max();
		
		
	}

	

	private static int[] move(int pos, int[] board_state,int my_player,int depth) {
		// TODO Auto-generated method stub
		// assume we are player 1
		//pw_traverse.println(Arrays.toString(board_state)+" position "+pos);
		//print_move(pos,my_player,depth,9);
		
		int stones = board_state[pos];
		int temp_board_state[]=new int[board_state.length];
		System.arraycopy(board_state, 0, temp_board_state, 0, board_state.length);
		temp_board_state[pos]=0;
		int extra_move=0;
		while(stones!=0)
		{
			pos= (pos+1)% board_state.length;
			if(mancala_position_2==pos && my_player==1)
				pos= (pos+1)% board_state.length;
			else if(mancala_position_1==pos && my_player==2)
				pos= (pos+1)% board_state.length;
			
			temp_board_state[pos]+=1;
			
			if(snatch(temp_board_state,stones,pos,my_player))
			{
				//snatch
				//pw_traverse.println("check");
				//pw_traverse.println("before snatch"+Arrays.toString(temp_board_state));
				if(my_player==1)
				{
					temp_board_state[mancala_position_1]+=temp_board_state[mancala_position_2-pos-1];
					temp_board_state[mancala_position_1]+=1;
					temp_board_state[mancala_position_2-pos-1]=0;
					temp_board_state[pos]=0;
				}
				else
				{
					int p1_pos=board_length*2-pos;
					temp_board_state[mancala_position_2]+=temp_board_state[p1_pos];
					temp_board_state[mancala_position_2]+=1;
					temp_board_state[p1_pos]=0;
					temp_board_state[pos]=0;
				}
				
				//pw_traverse.println("after snatch"+Arrays.toString(temp_board_state));
			}
			
			if(stones==1 && pos==mancala_position_1 && my_player==1)
			{
				//Extra Move.
				extra_move=1;
				play(temp_board_state, my_player, depth,my_player==1?("B"+(pos+1)):("A"+(mancala_position_2-pos+2)));
				return new int[1];
				
				
			}
			else if(stones==1 && pos==mancala_position_2 && my_player==2)
			{
				extra_move=1;
				play(temp_board_state, my_player, depth,my_player==1?("B"+(pos+1)):("A"+(mancala_position_2-pos+3)));
				return new int[1]; //minimax
			}
			else extra_move=0;
				
			stones--;
		}
		
		//pw_traverse.println(Arrays.toString(temp_board_state)+" in the end ");
		
		
		return evaluation(temp_board_state,extra_move);
		
		
	}

	private static boolean snatch(int[] temp_board_state, int stones, int pos, int my_player) {
		// TODO Auto-generated method stub
		
		if(temp_board_state[pos]==1 && stones == 1)
			{
			if(my_player==1)
			{
				if(pos!=mancala_position_1 && pos<mancala_position_1)
					return true;
			}
			else
			{

				if(pos!=mancala_position_2 && pos>mancala_position_1)
					return true;
			}
			
			}
		return false;
	}

	private static int[] evaluation(int[] temp_board_state,int extra_move) {
		// TODO Auto-generated method stub
		
		if(allZero(temp_board_state)!=-1)
		{
			
			temp_board_state=transferOpponent(temp_board_state,my_player);
			
		}
			
		int y = temp_board_state[mancala_position_1]-temp_board_state[mancala_position_2];
		y=my_player==1?y:-y;
		//pw_traverse.println("in eval "+Arrays.toString(temp_board_state)+" max _eval"+max_eval+" EX  "+extra_move);
		
		//	if(y>max_eval && extra_move==0) - greedy
			{
				
				//System.arraycopy(temp_board_state, 0, final_board_state, 0, board_state.length);
				//max_eval=y;
				
			}
			return temp_board_state;
		
		}
			
				
	

	private static int[] transferOpponent(int[] temp_board_state,int my_player) {

		// TODO Auto-generated method stub
		int sum=0;
		
		if(my_player==1)
		{
			for(int i=0;i<board_length;i++)
			{
			sum+=temp_board_state[i+mancala_position_1+1];
			temp_board_state[i+mancala_position_1+1]=0;
			}
		temp_board_state[mancala_position_2]+=sum;	
		}
		else
		{
			for(int i=0;i<board_length;i++)
			{
			sum+=temp_board_state[i];
			temp_board_state[i]=0;
			}
		temp_board_state[mancala_position_1]+=sum;	
		}
		return temp_board_state;
	}

	private static int allZero(int[] temp_board_state) {
		// TODO Auto-generated method stub
		
		int my_play=0;
		int player=-1;
		
			for(int i=0;i<board_length;i++)
				if(temp_board_state[i]==0)
					my_play++;
			
			if(my_play==board_length)
				player=1;
			
			my_play=0;
			
			for(int i=0;i<board_length;i++)
				if(temp_board_state[i+mancala_position_1+1]==0)
					my_play++;	
			if(my_play==board_length)
			{
				if(player==1)
					player=3;
				else
					player=2;
			}
			
		
		return player;
	}

	private static void readInputFromFile(List<String> input) {
		// TODO Auto-generated method stub
		
		task=Integer.parseInt(input.get(0));
		my_player=Integer.parseInt(input.get(1));
		cut_off_depth=Integer.parseInt(input.get(2));
		String board_game_2 [] = input.get(3).split("\\s+");
		String board_game_1 [] = input.get(4).split("\\s+");
		int mancala2=Integer.parseInt(input.get(5));
		int mancala1=Integer.parseInt(input.get(6));
		board_length=board_game_2.length;
		board_state=new int[board_length*2+2];
		
		int i = 0;
		for(String x : board_game_1)
			board_state[i++]=Integer.parseInt(x);
		board_state[i]=mancala1;
		mancala_position_1=i;
		i=(board_length*2+2)-1;
		mancala_position_2=i;
		board_state[i--]=mancala2;
		for(String x : board_game_2)
			board_state[i--]=Integer.parseInt(x);
		
	}

	private static void readFile(String filename) {
		// TODO Auto-generated method stub
	  	  String line = null;
	 	  FileReader fileReader;
		try {
			fileReader = new FileReader(filename);
			BufferedReader bufferedReader =new BufferedReader(fileReader);
	        while((line = bufferedReader.readLine()) != null)
	          	input.add(line);
	        bufferedReader.close();  
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
           
		
	}

}
