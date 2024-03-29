package net.teamabyssalofficial.registry;

import net.minecraft.world.level.GameRules;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class GameruleRegistry {
    public static final GameRules.Key<GameRules.BooleanValue> CONSTANT_ZERO_EVOLUTION_POINTS = GameRules.register("constantZeroEvolutionPoints", GameRules.Category.UPDATES, GameRules.BooleanValue.create(false));
}
