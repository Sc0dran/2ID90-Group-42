package nl.tue.s2id90.group42.BitBoard;

import java.util.HashMap;
import java.util.Vector;

public class BitBoardMoveList extends Vector<BitBoardMove>
{
    protected static class LongMap extends HashMap<Long, Long> {}

    public BitBoardMoveList(BitBoardPlayer player, BitBoard board)
    {
        initialize(player, board);
    }

    public BitBoardMoveList(BitBoardPlayer player, long m, long h, long k)
    {
        initialize(player, m, h, k);
    }

    public BitBoardMoveList()
    {
    }

    public void initialize(BitBoardPlayer player, BitBoard board)
    {
        initialize(player, player.mine(board), player.his(board), board.kings);
    }

    private void initialize(BitBoardPlayer player, long mine, long his, long kings) 
    {
        clear();

        // find captures
        findCaptures(player, null, mine, his, kings);

        // find moves
        if(isEmpty())
            findMoves(player, mine, his, kings);

    }

    protected void findMoves(BitBoardPlayer player, long mine, long his, long kings)
    {
        for (BitBoardDirection dir : player.directions)
        {
            // use pieces or kings depending on player
            long source = (player.canMove(dir) ? mine : mine & kings) & dir.moves; 

            // calculate destination squares
            long dest = (((source & 0b00000111110000011111000001111100000111110000011111L) << dir.move_shl_e) >>> dir.move_shr_e)
                             | (((source & 0b11111000001111100000111110000011111000001111100000L) << dir.move_shl_o) >>> dir.move_shr_o);

            // find origins of empty destination squares
            long orig = (((dest & ~(mine | his) & 0b11111000001111100000111110000011111000001111100000L) >>> dir.move_shl_e) << dir.move_shr_e)
                             | (((dest & ~(mine | his) & 0b00000111110000011111000001111100000111110000011111L) >>> dir.move_shl_o) << dir.move_shr_o);

            // create move object for every origin square
            if (orig != 0){
                for (long coord = 0L; coord < 50L; coord++){
                    if ((orig & (1L << coord)) != 0)
                    {
                        add(new BitBoardMove(coord, coord + (coord % 10L < 5L ? dir.move_shl_e : dir.move_shl_o) - (coord % 10L < 5L ? dir.move_shr_e : dir.move_shr_o)));
                    }
                }
            }
        }
    }

    protected BitBoardMoveList findCaptures(BitBoardPlayer player, Long from, long mine, long his, long kings)
    {
        // initialize this movelist or return a movelist from certain square
        BitBoardMoveList result = from == null 
                                        ? this
                                        : new BitBoardMoveList();

        for (BitBoardDirection dir : player.directions)
        {
            // use only pieces or kings depending on player
            long source = player.canMove(dir) 
                            ? (from == null ? mine : (1L << from))
                            : (from == null ? mine : (1L << from)) & kings;

            // find jump destinations and origins of empty destination squares
            long jumpdest = ((source & dir.jumps) << dir.jump_shl) >>> dir.jump_shr;
            long jumporig = ((jumpdest & ~(mine | his)) >>> dir.jump_shl) << dir.jump_shr;

            // find moves to enemy-occupied squares and their origins
            long captdest = (((source & dir.moves & 0b00000111110000011111000001111100000111110000011111L) << dir.move_shl_e) >>> dir.move_shr_e)
                                     | (((source & dir.moves & 0b11111000001111100000111110000011111000001111100000L) << dir.move_shl_o) >>> dir.move_shr_o);

            long captorig = (((captdest & his & 0b11111000001111100000111110000011111000001111100000L) >>> dir.move_shl_e) << dir.move_shr_e)
                                     | (((captdest & his & 0b00000111110000011111000001111100000111110000011111L) >>> dir.move_shl_o) << dir.move_shr_o);

            long canjump = jumporig & captorig;

            // create move object(s) for every jump origin square
            if (canjump != 0){
                for (long coord = 0; coord < 50L; coord++){
                    if ((canjump & (1L << coord)) != 0)
                    {
                        // calculate capture and destination squares
                        long captsquare = coord % 10L < 5L
                                                   ? coord + dir.move_shl_e - dir.move_shr_e
                                                   : coord + dir.move_shl_o - dir.move_shr_o;
                        long destsquare = coord + dir.jump_shl - dir.jump_shr;

                        // find rest of jump, if jump didn't end on crowning row
                        BitBoardMove move = new BitBoardMove(coord, captsquare, destsquare);
                        BitBoardMoveList rest = ((1L << destsquare) & player.crownrow & ~kings) == 0
                                                  | ((1L << coord) & kings) != 0
                                                  ? findCaptures(player, destsquare, move.applyMine(mine), move.applyHis(his), move.applyKings(kings, player.crownrow))
                                                  : null;

                        // create move object for every way to finish the capture from the origin square
                        if (rest != null && rest.size() > 0)
                            for (BitBoardMove moverest : rest)
                            {
                                BitBoardMove newmove = move.clone();
                                moverest.remove(0);
                                newmove.addAll(moverest);
                                result.add(newmove);
                            }
                        // no way to extend the capture, just add the capture
                        else
                                result.add(move);
                    }
                }
            }
        }

        return result;
    }
}
