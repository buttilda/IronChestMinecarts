package ganymedes01.ironchestminecarts.gui;

import java.lang.reflect.Constructor;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.ironchest.ContainerIronChest;
import cpw.mods.ironchest.client.GUIChest;
import ganymedes01.ironchestminecarts.minecarts.EntityMinecartIronChestAbstract;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		Entity entity = world.getEntityByID(ID);
		if (entity instanceof EntityMinecartIronChestAbstract) {
			EntityMinecartIronChestAbstract minecart = (EntityMinecartIronChestAbstract) entity;

			return new ContainerIronChest(player.inventory, minecart, minecart.type(), 0, 0);
		}

		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		Entity entity = world.getEntityByID(ID);
		if (entity instanceof EntityMinecartIronChestAbstract) {
			EntityMinecartIronChestAbstract minecart = (EntityMinecartIronChestAbstract) entity;

			try {
				Constructor<GUIChest> constructor = GUIChest.class.getDeclaredConstructor(GUIChest.GUI.class, IInventory.class, IInventory.class);
				constructor.setAccessible(true);
				return constructor.newInstance(GUIChest.GUI.values()[minecart.type().ordinal()], player.inventory, minecart);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		return null;
	}
}