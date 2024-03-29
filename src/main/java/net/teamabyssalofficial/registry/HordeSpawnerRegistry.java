package net.teamabyssalofficial.registry;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.Fluids;
import net.teamabyssalofficial.config.FightOrDieMutationsConfig;
import net.teamabyssalofficial.entity.categories.Mutated;
import net.teamabyssalofficial.extra.ScreenShakeEntity;


public class HordeSpawnerRegistry {

    private int nextTick;

    public HordeSpawnerRegistry(MinecraftServer server) {
    }

    public int tick(Level level) {
        if (!FightOrDieMutationsConfig.SERVER.enable_horde.get()) {
            return 0;
        }
        else if (WorldDataRegistry.getWorldDataRegistry((ServerLevel) level).getPhase() < 2) {
            return 0;
        }
        else {
            RandomSource random = level.random;
            --this.nextTick;
            if (this.nextTick > 0) {
                return 0;
            } else {
                this.nextTick += FightOrDieMutationsConfig.SERVER.ticksBeforeHordeSpawning.get() +  FightOrDieMutationsConfig.SERVER.additionalRandomizedTicks.get();
                if (level.isNight()) {
                    if (random.nextInt(5) != 0) {
                        return 0;
                    } else {
                        int j = level.players().size();
                        if (j < 1) {
                            return 0;
                        } else {
                            Player player = level.players().get(random.nextInt(j));
                            if (player.isSpectator()) {
                                return 0;
                            } else {
                                int k = (24 + random.nextInt(24)) * (random.nextBoolean() ? -1 : 1);
                                int l = (24 + random.nextInt(24)) * (random.nextBoolean() ? -1 : 1);
                                BlockPos.MutableBlockPos blockpos$mutableblockpos = player.blockPosition().mutable().move(k, 0, l);
                                int i1 = 10;
                                if (!level.hasChunksAt(blockpos$mutableblockpos.getX() - 10, blockpos$mutableblockpos.getZ() - 10, blockpos$mutableblockpos.getX() + 10, blockpos$mutableblockpos.getZ() + 10)) {
                                    return 0;
                                } else {
                                    Holder<Biome> holder = level.getBiome(blockpos$mutableblockpos);
                                    if (holder.is(BiomeTags.WITHOUT_PATROL_SPAWNS)) {
                                        return 0;
                                    } else {
                                        int j1 = 0;
                                        int k1 = FightOrDieMutationsConfig.SERVER.mobSpawnAttempts.get();

                                        for (int l1 = 0; l1 < k1; ++l1) {
                                            ++j1;
                                            blockpos$mutableblockpos.setY(level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, blockpos$mutableblockpos).getY());
                                            if (l1 == 0) {
                                                if (!this.spawnHordeEntity(level, blockpos$mutableblockpos, random, true)) {
                                                    break;
                                                }
                                            } else {
                                                this.spawnHordeEntity(level, blockpos$mutableblockpos, random, false);
                                                player.displayClientMessage(Component.literal("They're coming."), true);
                                                player.level().playSound((Player) null, player.blockPosition(), SoundRegistry.PHASE5.get(), SoundSource.MASTER, 1.5F, 1.0F);
                                            }

                                            blockpos$mutableblockpos.setX(blockpos$mutableblockpos.getX() + random.nextInt(5) - random.nextInt(5));
                                            blockpos$mutableblockpos.setZ(blockpos$mutableblockpos.getZ() + random.nextInt(5) - random.nextInt(5));
                                        }

                                        return j1;
                                    }
                                }
                            }
                        }
                    }
                } else {
                    return 0;
                }
            }
        }
    }

    private boolean spawnHordeEntity(Level level, BlockPos blockPos, RandomSource randomSource, boolean b) {
        BlockState blockstate = level.getBlockState(blockPos);
        BlockState downState = level.getBlockState(blockPos.below());
        if (level.getLightEmission(blockPos) > 1) {
            return false;
        } else if (blockstate.getFluidState().is(Fluids.WATER) || downState.getFluidState().is(Fluids.WATER) ) {
            return false;
        } else {
            Mutated hordeEntity;

                int rand = randomSource.nextInt(4);
                switch (rand) {
                    case 0 -> hordeEntity = EntityRegistry.ASSIMILATED_ADVENTURER.get().create(level);
                    case 1, 2 -> hordeEntity = EntityRegistry.ASSIMILATED_HUMAN.get().create(level);
                    default -> hordeEntity = EntityRegistry.ASSIMILATED_VILLAGER.get().create(level);
                }

            if (hordeEntity != null) {
                if (b) {
                    hordeEntity.setPatrolLeader(true);
                    hordeEntity.findPatrolTarget();
                }
                hordeEntity.setPos((double)blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ());
                hordeEntity.finalizeSpawn((ServerLevel)level, level.getCurrentDifficultyAt(blockPos), MobSpawnType.PATROL, (SpawnGroupData)null, (CompoundTag)null);
                ((ServerLevel)level).addFreshEntityWithPassengers(hordeEntity);
                return true;
            } else {
                return false;
            }
        }
    }


}
