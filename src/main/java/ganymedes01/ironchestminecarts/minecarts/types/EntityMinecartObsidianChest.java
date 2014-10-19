package ganymedes01.ironchestminecarts.minecarts.types;

import ganymedes01.ironchestminecarts.minecarts.EntityMinecartIronChestAbstract;
import net.minecraft.world.World;
import cpw.mods.ironchest.IronChestType;

public class EntityMinecartObsidianChest extends EntityMinecartIronChestAbstract {

	public EntityMinecartObsidianChest(World world) {
		super(world);
	}

	public EntityMinecartObsidianChest(World world, double x, double y, double z) {
		super(world, x, y, z);
	}

	@Override
	public IronChestType type() {
		return IronChestType.OBSIDIAN;
	}
}