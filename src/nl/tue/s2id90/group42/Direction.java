package nl.tue.s2id90.group42;

public enum Direction
{
	
        //Moves NW
        // 0 0 0 0 0
        //0 1 1 1 1
        // 1 1 1 1 1
        //0 1 1 1 1
        // 1 1 1 1 1
        //0 1 1 1 1
        // 1 1 1 1 1
        //0 1 1 x 1
        // 1 1 1 x 1
        //0 1 1 1 x
        //Jump NW
        // 0 0 0 0 0
        //0 0 0 0 0
        // 0 1 1 1 1
        //0 1 1 1 1
        // 0 1 1 1 1
        //0 1 1 1 1
        // 0 1 1 1 1
        //0 1 1 x 1
        // 0 1 1 1 1
        //0 1 1 1 x
	
        //Moves NE
        // 0 0 0 0 0 
        //1 1 1 1 1 
        // 1 1 1 1 0 
        //1 1 1 1 1 
        // 1 1 1 1 0 
        //1 1 1 1 1 
        // 1 1 1 1 0 
        //1 x 1 1 1
        // x 1 1 1 0 
        //x 1 1 1 1
        //Jumps NE
        // 0 0 0 0 0 
        //0 0 0 0 0 
        // 1 1 1 1 0 
        //1 1 1 1 0 
        // 1 1 1 1 0 
        //1 1 1 1 0 
        // 1 1 1 1 0 
        //1 x 1 1 0
        // 1 1 1 1 0 
        //x 1 1 1 0
	
        //Moves SW
        // 1 1 1 1 x
        //0 1 1 1 x
        // 1 1 1 x 1
        //0 1 1 1 1
        // 1 1 1 1 1
        //0 1 1 1 1
        // 1 1 1 1 1
        //0 1 1 1 1
        // 1 1 1 1 1
        //0 0 0 0 0
        //Jumpes SW
        // 0 1 1 1 x
        //0 1 1 1 1
        // 0 1 1 x 1
        //0 1 1 1 1
        // 0 1 1 1 1
        //0 1 1 1 1
        // 0 1 1 1 1
        //0 1 1 1 1
        // 0 0 0 0 0
        //0 0 0 0 0
	
        //Moves SE
        // x 1 1 1 0 
        //1 x 1 1 1 
        // 1 x 1 1 0 
        //1 1 1 1 1 
        // 1 1 1 1 0 
        //1 1 1 1 1 
        // 1 1 1 1 0 
        //1 1 1 1 1 
        // 1 1 1 1 0 
        //0 0 0 0 0
        //Jumpes SE
        // x 1 1 1 0 
        //1 1 1 1 0 
        // 1 x 1 1 0 
        //1 1 1 1 0 
        // 1 1 1 1 0 
        //1 1 1 1 0 
        // 1 1 1 1 0 
        //1 1 1 1 0 
        // 0 0 0 0 0 
        //0 0 0 0 0
        
        NW(0b00000011111111101111111110111111111011111111101111L, 0b00000000000111101111011110111101111011110111101111L, 6, 5, 0, 0, 11, 0), 
        NE(0b00000111111111011111111101111111110111111111011111L, 0b00000000001111011110111101111011110111101111011110L, 5, 4, 0, 0, 9, 0), 
        SW(0b11111011111111101111111110111111111011111111100000L, 0b01111011110111101111011110111101111011110000000000L, 0, 0, 4, 5, 0, 9), 
        SE(0b11110111111111011111111101111111110111111111000000L, 0b11110111101111011110111101111011110111100000000000L, 0, 0, 5, 6, 0, 11); 

	public long moves; //Moves bit mask
	public long jumps; //Jump bit mask
	public long move_shl_e; //Move left even bit shift
	public long move_shl_o; //Move left odd bit shift
	public long move_shr_e; //Move right even bit shift
	public long move_shr_o; //Move right odd bit shift
	public long jump_shl; //Jump left bit shift
	public long jump_shr; //Jump right bit shift

	Direction(long m, long j, long mle, long mlo, long mre, long mro, long jl, long jr)
	{
            moves = m;
            jumps = j;
            move_shl_e = mle;
            move_shr_e = mre;
            move_shl_o = mlo;
            move_shr_o = mro;
            jump_shl = jl;
            jump_shr = jr;
	}
}
