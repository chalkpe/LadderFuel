package pe.chalk.bukkit.ladderfuel;

import org.bstats.bukkit.Metrics;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import pe.chalk.bukkit.chestoverflow.ItemHelper;
import pe.chalk.bukkit.chestoverflow.ItemSortEvent;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

public final class LadderFuel extends JavaPlugin implements Listener {
    public static final int MAX_STACK_SIZE = 43;

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
        new Metrics(this, 17520);
    }

    @EventHandler
    public void onItemSort(ItemSortEvent event) {
        if (event.isCancelled()) return;

        final var contents = Arrays.stream(event.getContents()).filter(Objects::nonNull).toList();
        if (contents.isEmpty() || contents.stream().anyMatch(stack -> stack.getType() != Material.LADDER)) return;

        final int totalAmount = contents.stream().mapToInt(ItemStack::getAmount).sum();
        event.setContents(ItemHelper.generateStacks(Map.entry(contents.get(0), totalAmount), MAX_STACK_SIZE).toArray(ItemStack[]::new));
    }
}
