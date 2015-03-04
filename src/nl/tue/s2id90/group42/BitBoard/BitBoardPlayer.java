package nl.tue.s2id90.group42.BitBoard;

import java.awt.Color;

public enum BitBoardPlayer
{ 
	PLAYER1(0b11111000000000000000000000000000000000000000000000L, new BitBoardDirection[] { BitBoardDirection.NE, BitBoardDirection.NW, BitBoardDirection.SE, BitBoardDirection.SW })
	{
            public long mine(BitBoard b) { return b.player1; }

            public long his(BitBoard b) { return b.player2; }

            public boolean canMove(BitBoardDirection d) { return d == BitBoardDirection.NE || d == BitBoardDirection.NW; }

            public void applyMove(BitBoard b, BitBoardMove m)
            {
                b.player1 = m.applyMine(b.player1);
                b.player2 = m.applyHis(b.player2);
                b.kings = m.applyKings(b.kings, crownrow);
            }
	},
	
	PLAYER2(0b00000000000000000000000000000000000000000000011111L, new BitBoardDirection[] { BitBoardDirection.SW, BitBoardDirection.SE, BitBoardDirection.NW, BitBoardDirection.NE })
	{
            public long mine(BitBoard b) { return b.player2; }

            public long his(BitBoard b) { return b.player1; }

            public boolean canMove(BitBoardDirection d) { return d == BitBoardDirection.SE || d == BitBoardDirection.SW; }

            public void applyMove(BitBoard b, BitBoardMove m)
            {
                b.player1 = m.applyHis(b.player1);
                b.player2 = m.applyMine(b.player2);
                b.kings = m.applyKings(b.kings, crownrow);
            }
	};
	
	static {
            PLAYER1.other = PLAYER2;
            PLAYER2.other = PLAYER1;
	}
	
	public long crownrow;
	public BitBoardPlayer other;
	public BitBoardDirection[] directions;
	
	BitBoardPlayer(long cr, BitBoardDirection[] d)
	{
            crownrow = cr;
            directions = d;
	}
	
	public abstract long mine(BitBoard b);
	public abstract long his(BitBoard b);
	public abstract boolean canMove(BitBoardDirection d);
	public abstract void applyMove(BitBoard b, BitBoardMove m);
};
