package pe.chalk.bukkit.ladderfuel;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import pe.chalk.bukkit.chestoverflow.ItemHelper;
import pe.chalk.bukkit.chestoverflow.ItemSortEvent;

import java.util.Arrays;
import java.util.Map;

public final class LadderFuel extends JavaPlugin implements Listener {
    public static final int MAX_STACK_SIZE = 43;

    @Override
    public void onEnable() {
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onItemSort(ItemSortEvent event) {
        if (event.isCancelled()) return;

        final ItemStack[] contents = event.getContents();
        if (Arrays.stream(contents).anyMatch(stack -> stack.getType() != Material.LADDER)) return;

        final int totalAmount = Arrays.stream(contents).mapToInt(ItemStack::getAmount).sum();
        event.setContents(ItemHelper.generateStacks(Map.entry(contents[0], totalAmount), MAX_STACK_SIZE).toArray(ItemStack[]::new));
    }
}
