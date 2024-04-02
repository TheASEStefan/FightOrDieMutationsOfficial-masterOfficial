package net.teamabyssalofficial.fdmcommands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.teamabyssalofficial.registry.WorldDataRegistry;

public class ShowPhaseCommand {
    public static void register(CommandDispatcher<CommandSourceStack> commandDispatcher) {
        commandDispatcher.register(Commands.literal("phase").requires((permission) -> {
            return permission.hasPermission(3);
        }).then(Commands.literal("show").executes((context -> ShowPhaseCommand.showPhase(context.getSource())))));
    }

    public static int showPhase(CommandSourceStack source) {
        WorldDataRegistry worldDataRegistry = WorldDataRegistry.getWorldDataRegistry(source.getLevel());
        source.sendSuccess(() -> Component.literal("The current evolution phase is: " + worldDataRegistry.getPhase()), false);

        return 0;
    }
}
