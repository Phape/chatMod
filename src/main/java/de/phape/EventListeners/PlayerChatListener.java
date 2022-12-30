package de.phape.EventListeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerChatListener implements Listener {

    @EventHandler
    public void onPlayerChat(AsyncChatEvent event) {
        // Do something with the chat message
        String message = event.signedMessage().message();
        Player player = event.getPlayer();

        // todo: make this deactivateable via cofig, check if it makes more sense to not register this listener in the main class.
        if(message.toLowerCase().startsWith("ich bin ")) {
            postDeineMutterWitz(message, player);
        }
    }

    private void postDeineMutterWitz(String message, Player player) {
        String[] parts = message.split("ich bin ");
        String joke = "Deine Mutter ist " + parts[1];
        TextComponent messageComponent = Component.text(joke);
        Bukkit.getServer().broadcast(messageComponent);
        Bukkit.getLogger().info("Made Deine-Mutter-Witz triggered by " + player.getName() + ": " + joke);
    }
}
