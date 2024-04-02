package net.teamabyssalofficial.fdmcommands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.teamabyssalofficial.registry.WorldDataRegistry;

public class ShowScoreCommand {
    public static void register(CommandDispatcher<CommandSourceStack> commandDispatcher) {
        commandDispatcher.register(Commands.literal("score").requires((permission) -> {
            return permission.hasPermission(3);
        }).then(Commands.literal("show").executes((context -> ShowScoreCommand.showScore(context.getSource())))));
    }

    public static int showScore(CommandSourceStack source) {
        WorldDataRegistry worldDataRegistry = WorldDataRegistry.getWorldDataRegistry(source.getLevel());
        source.sendSuccess(() -> Component.literal("The current evolution score is: " + worldDataRegistry.getScore()), false);

        return 0;
    }
}
