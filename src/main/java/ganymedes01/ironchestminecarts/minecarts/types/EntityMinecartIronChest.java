package ganymedes01.ironchestminecarts.minecarts.types;

import cpw.mods.ironchest.IronChestType;
import ganymedes01.ironchestminecarts.minecarts.EntityMinecartIronChestAbstract;
import net.minecraft.world.World;

public class EntityMinecartIronChest extends EntityMinecartIronChestAbstract {

	public EntityMinecartIronChest(World world) {
		super(world);
	}

	public EntityMinecartIronChest(World world, double x, double y, double z) {
		super(world, x, y, z);
	}

	@Override
	public IronChestType type() {
		return IronChestType.IRON;
	}
}