package com.wiblewobble.chocolatehaven.block.custom;

import com.wiblewobble.chocolatehaven.api.blockentity.AbstractModBaseEntityBlock;
import com.wiblewobble.chocolatehaven.block.entity.cocoafermenter.CocoaFermenterBlockEntity;
import com.wiblewobble.chocolatehaven.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class CocoaFermenterBlock extends AbstractModBaseEntityBlock<CocoaFermenterBlockEntity> {
    //TODO: MAKE A SEPARATE CLASS THAT HANDLES ALL OF THE BLOCKENTITY BOILERPLATE
    private final Class<CocoaFermenterBlockEntity> blockEntity;
    public CocoaFermenterBlock(Class<CocoaFermenterBlockEntity> blockEntity, Properties properties) {
        super(blockEntity, properties);
        this.blockEntity = blockEntity;
    }

    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof CocoaFermenterBlockEntity) {
                ((CocoaFermenterBlockEntity) blockEntity).drops();
            }
        }
        super.onRemove(state,level,pos,newState,isMoving);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CocoaFermenterBlockEntity(pos, state);
    }

    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return createTickerHelper(type, ModBlockEntities.COCOA_FERMENTER.get(), CocoaFermenterBlockEntity::tick);
    }
}
