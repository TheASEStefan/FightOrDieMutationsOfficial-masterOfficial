package net.teamabyssalofficial.entity.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.teamabyssalofficial.fight_or_die.FightOrDieMutations;
import net.teamabyssalofficial.registry.EntityRegistry;

@Mod.EventBusSubscriber(modid = FightOrDieMutations.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class LayerRendererRegisterClass {
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(AssimilatedAdventurerModel.LAYER_LOCATION, AssimilatedAdventurerModel::createBodyLayer);
    }
    @SubscribeEvent
    public static void registerRenderers(final EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityRegistry.ASSIMILATED_ADVENTURER.get(), AssimilatedAdventurerRenderer::new);
    }
}
