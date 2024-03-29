package net.teamabyssalofficial.fdmcommands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.teamabyssalofficial.config.FightOrDieMutationsConfig;
import net.teamabyssalofficial.entity.categories.Mutated;
import net.teamabyssalofficial.registry.EntityRegistry;

public class SpawnHordeCommand {

    public static void init() {

    }
    public static void register(CommandDispatcher<CommandSourceStack> commandDispatcher) {
        commandDispatcher.register(Commands.literal("horde").requires((permission) -> {
            return permission.hasPermission(3);
        }).then(Commands.literal("spawn").executes((context -> SpawnHordeCommand.executeSpawn(context.getSource())))));
    }

    public static int executeSpawn(CommandSourceStack source) {
        source.sendSuccess(() -> Component.literal("Spawned a mutation horde."), false);
        int o = FightOrDieMutationsConfig.SERVER.mobSpawnAttempts.get();
        BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos(source.getPosition().x, source.getPosition().y, source.getPosition().z);
        ServerLevel level = source.getLevel();
        RandomSource random = source.getLevel().getRandom();

        for (int l1 = 0; l1 < o; ++l1) {
            mutable.setY(level.getHeightmapPos(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, mutable).getY());
            if (l1 == 0) {
                if (!spawnHordeEntity(level, mutable, random, true)) {
                    break;
                }
            } else {
                spawnHordeEntity(level, mutable, random, false);
            }

            mutable.setX(mutable.getX() + random.nextInt(5) - random.nextInt(5));
            mutable.setZ(mutable.getZ() + random.nextInt(5) - random.nextInt(5));
        }

        return 1;
    }

    private static boolean spawnHordeEntity(Level level, BlockPos blockPos, RandomSource randomSource, boolean b) {
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
