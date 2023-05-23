package com.github.ggnmstr.tanks.dto;

import com.github.ggnmstr.tanks.model.GameParameters;
import com.github.ggnmstr.tanks.util.Direction;

import java.util.ArrayList;
import java.util.List;



public record GameObjects(TankModel mainPlayer, BlockTO base, List<BlockTO> blocks,
                          List<TankModel> enemies, List<Position> bullets) {

    @Override
    public TankModel mainPlayer() {
        return mainPlayer;
    }

    @Override
    public List<BlockTO> blocks() {
        return blocks;
    }

    @Override
    public List<TankModel> enemies() {
        return enemies;
    }

    @Override
    public List<Position> bullets() {
        return bullets;
    }

    @Override
    public BlockTO base() {
        return base;
    }

    public static GameObjects initFromTemplate(char[][] mapTemplate){
        List<BlockTO> blocksTO = new ArrayList<>();
        TankModel tank = null;
        BlockTO base = null;
        for (int i = 0; i < mapTemplate.length; i++){
            for (int j = 0; j < mapTemplate[i].length; j++){
                if (mapTemplate[i][j] == '0') continue;
                if (mapTemplate[i][j] == 't'){
                    BlockTO trees = new BlockTO(new Position(j* GameParameters.BLOCKWIDTH,i*GameParameters.BLOCKHEIGHT
                    ,GameParameters.BLOCKWIDTH,GameParameters.BLOCKHEIGHT),BlockType.TREES);
                    blocksTO.add(trees);
                }
                if (mapTemplate[i][j] == '7'){
                    BlockTO metal = new BlockTO(new Position(j* GameParameters.BLOCKWIDTH,i*GameParameters.BLOCKHEIGHT
                    ,GameParameters.BLOCKWIDTH,GameParameters.BLOCKHEIGHT),BlockType.METAL);
                    blocksTO.add(metal);
                }
                if (mapTemplate[i][j] == '1'){
                    BlockTO brick = new BlockTO(new Position(j* GameParameters.BLOCKWIDTH,i*GameParameters.BLOCKHEIGHT
                    ,GameParameters.BLOCKWIDTH,GameParameters.BLOCKHEIGHT),BlockType.BRICK);
                    blocksTO.add(brick);
                }
                if (mapTemplate[i][j] == '3'){
                    base = new BlockTO(new Position(j*GameParameters.BLOCKWIDTH,i*GameParameters.BLOCKHEIGHT,
                            GameParameters.BLOCKWIDTH,GameParameters.BLOCKHEIGHT),BlockType.BASE);
                    blocksTO.add(base);
                }
//                if (mapTemplate[i][j] == '2'){
//                    EnemySpawnPoint spawnPoint = new EnemySpawnPoint(j*GameParameters.BLOCKWIDTH,i*GameParameters.BLOCKHEIGHT);
//                    enemySpawnPoints.add(spawnPoint);
//                }
                if (mapTemplate[i][j] == '4'){
                    tank = new TankModel(j*GameParameters.BLOCKWIDTH, i * GameParameters.BLOCKHEIGHT,
                            GameParameters.TANKSIZE,GameParameters.TANKSIZE, Direction.DOWN);
                }

            }
        }
        //HELP: Because enemies and bullets only exist after game has started, it is meaningless to init them here
        // So I pass null.
        // I think that model can be initialized with other class than GameObjects,
        // for example change class FieldParameters, so it stores only valuable for initializing field data
        // (like fieldbloks (arraylist), Tank (player), base (instanceof FieldBlock), spawns, etc)
        return new GameObjects(tank,base,blocksTO,null,null);
    }
}
