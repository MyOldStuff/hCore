package me.hellsing.hCore;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

public class EntityUtil
{

    // Reflection related
    private static Method getHandle;
    private static Field fieldKiller;
    private static Field fieldLastDamage;

    public static List<Entity> getNearbyEntities(Location loc, double radius)
    {
        List<Entity> entities = loc.getWorld().getEntities();

        Iterator<Entity> entity = entities.iterator();

        while (entity.hasNext())
        {
            Entity nextEntity = entity.next();

            if (nextEntity.getLocation().distanceSquared(loc) > (radius * radius))
                entity.remove();
        }

        return entities;
    }

    public static void setKiller(Player player, Player killer, int timeInTicks)
    {
        try
        {
            // Get the getHandle method
            if (getHandle == null)
                getHandle = Class.forName(Bukkit.getServer().getClass().getPackage().getName() + ".entity.CraftPlayer").getDeclaredMethod("getHandle");

            // Get the EntityPlayer for both the killer and victim
            Object playerHandle = getHandle.invoke(player);
            Object killerHandle = getHandle.invoke(killer);

            // Get both the killer and lastDamage fields
            if (fieldKiller == null || fieldLastDamage == null)
            {
                Class<?> entityLiving = Class.forName(playerHandle.getClass().getPackage().getName() + ".EntityLiving");

                fieldKiller = entityLiving.getDeclaredField("killer");
                fieldLastDamage = entityLiving.getDeclaredField("lastDamageByPlayerTime");
                fieldLastDamage.setAccessible(true);
            }

            // Set the killer
            fieldKiller.set(playerHandle, killerHandle);

            // Set the time the killer is set for in ticks
            fieldLastDamage.set(playerHandle, timeInTicks);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("deprecation")
    public static void clearWholeInventory(Player player)
    {
        player.getInventory().clear();
        player.getInventory().setArmorContents(new ItemStack[4]);
        player.updateInventory();
    }


}
