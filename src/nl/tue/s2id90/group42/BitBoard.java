package nl.tue.s2id90.group42;

import nl.tue.s2id90.draughts.DraughtsState;

public class BitBoard
{
	public long player1;
	public long player2;
	public long kings;
	
	public BitBoard()
	{
		set(0b00000000000000000000000000000011111111111111111111L, 0b11111111111111111111000000000000000000000000000000L, 0b00000000000000000000000000000000000000000000000000L);

	}
	
	public BitBoard(long p1, long p2, long k)
	{
		set(p1, p2, k);
	}

	public BitBoard(DraughtsState s){
            int[] pieces = s.getPieces();
            for (int i = 1; i != pieces.length; i++){
                if(pieces[i] == DraughtsState.WHITEPIECE){
                    player2 += Math.pow(2, i-1);
                } else if(pieces[i] == DraughtsState.BLACKPIECE){
                    player1 += Math.pow(2, i-1);
                } else if(pieces[i] == DraughtsState.WHITEKING){
                    player2 += Math.pow(2, i-1);
                    kings += Math.pow(2, i-1);
                } else if(pieces[i] == DraughtsState.BLACKKING){
                    player1 += Math.pow(2, i-1);
                    kings += Math.pow(2, i-1);
                }
            }
	}

	public void set(long p1, long p2, long k)
	{
		player1 = p1;
		player2 = p2;
		kings = k;
	}
	
	public void set(BitBoard b)
	{
		set(b.player1, b.player2, b.kings);
	}
	
	public String toString()
	{
		String result = "";
		for (int i = 49; i >= 0; i--)
		{
			BitBoardState state = getState(i);
			result += (i % 10) == 9 ? "  " : "";
			if (state != BitBoardState.EMPTY)
				result += (getPlayer(i) == BitBoardPlayer.PLAYER1 ? "1" : "2")
					   + (state == BitBoardState.KING ? "k"	: "p");
			else
				result += "__";
			result += (i % 5) == 0 ? "\n" : "  ";
		}
		return result;
	}
	
	public BitBoardPlayer getPlayer(long coord)
	{
		long b = 1L << coord;
		return ((player1 & b) == b) ? BitBoardPlayer.PLAYER1 
			 : ((player2 & b) == b) ? BitBoardPlayer.PLAYER2
			 : null;
	}
	
	public BitBoardState getState(long coord)
	{
		long b = 1L << coord;
		return ((kings & b) != 0) ? BitBoardState.KING
			 : ((player1 | player2) & b) != 0 ? BitBoardState.PIECE
			 : BitBoardState.EMPTY;
	}
}
