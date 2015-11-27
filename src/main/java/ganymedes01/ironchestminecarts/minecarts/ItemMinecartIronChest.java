package ganymedes01.ironchestminecarts.minecarts;

import cpw.mods.ironchest.IronChestType;
import net.minecraft.block.BlockRailBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemMinecart;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemMinecartIronChest extends ItemMinecart {

	public final IronChestType type;

	public ItemMinecartIronChest(IronChestType type) {
		super(0);
		this.type = type;
	}

	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if (BlockRailBase.func_150051_a(world.getBlock(x, y, z))) {
			if (!world.isRemote) {
				EntityMinecartIronChestAbstract entityminecart = EntityMinecartIronChestAbstract.makeMinecart(world, x + 0.5F, y + 0.5F, z + 0.5F, type);

				if (stack.hasDisplayName())
					entityminecart.setMinecartName(stack.getDisplayName());

				world.spawnEntityInWorld(entityminecart);
			}

			stack.stackSize--;
			return true;
		} else
			return false;
	}
}