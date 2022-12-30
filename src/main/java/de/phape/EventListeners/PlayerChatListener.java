package de.phape.EventListeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class PlayerChatListener implements Listener {

    private final Plugin plugin;

    public PlayerChatListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChat(AsyncChatEvent event) {
        // Do something with the chat message
        String message = event.signedMessage().message();
        Player player = event.getPlayer();

        // todo: make this deactivateable via cofig, check if it makes more sense to not register this listener in the main class.
        if(message.toLowerCase().startsWith("ich bin ")) {
            Bukkit.getScheduler().runTask(plugin, new PostDeineMutterWitzRunnable(message, player));
        }
    }

    /**
     * This class basically capsules a method that was called "postDeineMutterWitz".
     * It implements the Runnable interface so that it's run() method can be scheduled by the bukkit scheduler.
     * Using the scheduler means that the message is being posted in the next Minecraft tick if not defined otherwise.
     * This is used as kind of workaround because the server answer to the Player chat message would appear before
     * the Player chat message in minecraft chat. This might be because of the event hook.
     */
    private static class PostDeineMutterWitzRunnable implements Runnable {

        String message;
        Player player;
        PostDeineMutterWitzRunnable(String message, Player player) {
            this.message = message;
            this.player = player;
        }
        @Override
        public void run() {
            String[] parts = message.split("ich bin ");
            String joke = "Deine Mutter ist " + parts[1];

            String serverNameInMinecraftChat = "Server"; // the name that the message should appear to come from
            TextComponent component = Component.text(joke).color(NamedTextColor.GOLD).decorate(TextDecoration.BOLD);
            component = Component.text("[" + serverNameInMinecraftChat + "] ").color(NamedTextColor.GRAY).decorate(TextDecoration.BOLD).append(component); // prepend the server name to the component
            Bukkit.getServer().broadcast(component);
            Bukkit.getLogger().info("Made Deine-Mutter-Witz triggered by " + player.getName() + ": " + joke);
        }
    }
}
