package ganymedes01.ironchestminecarts.minecarts.types;

import ganymedes01.ironchestminecarts.minecarts.EntityMinecartIronChestAbstract;
import net.minecraft.world.World;
import cpw.mods.ironchest.IronChestType;

public class EntityMinecartCrystalChest extends EntityMinecartIronChestAbstract {

	public EntityMinecartCrystalChest(World world) {
		super(world);
	}

	public EntityMinecartCrystalChest(World world, double x, double y, double z) {
		super(world, x, y, z);
	}

	@Override
	public IronChestType type() {
		return IronChestType.CRYSTAL;
	}
}