package nl.tue.s2id90.group42;

import java.util.Vector;

public class BitBoardMove extends Vector<Long>
{
	public Long value;
	
	public BitBoardMove(long... coords)
	{
		for (long coord : coords)
			add(coord);
	}
	
	public BitBoardMove(BitBoardMove m)
	{
		for (long coord : m)
			add(coord);
	}

	public BitBoardMove clone()
	{
		BitBoardMove newmove = new BitBoardMove();
		for (long c : this)
			newmove.add(c);
		return newmove;
	}
	
	public String toString()
	{
		if (size() == 2)
		{
			return String.format("%d-%d", get(0) + 1, get(1) + 1);
		}
		else
		{
			String result = "";
			for (int i = 0; i < size(); i += 2)
			{
				result += get(i) + 1;
				if (i < (size() - 1))
					result += "x";
			}
			return result;
		}
	}

	public long applyMine(long mine)
	{
		long b = 1L << get(0);
		if ((mine & b) != 0)
		{
			mine ^= b;
			mine ^= 1L << lastElement();
		}
		return mine;
	}

	public long applyHis(long his)
	{
		if (size() > 2)
			for (int i = 1; i < size(); i += 2)
			{
				long b = 1L << get(i);
				if ((his & b) != 0)
					his ^= 1L << get(i);
			}
		return his;
	}
	
	public long applyKings(long kings, long crownrow)
	{
		kings = applyMine(applyHis(kings));

		long b = 1L << lastElement();
		if ((crownrow & b) != 0)
			return kings | b;

		return kings;
	}
}
