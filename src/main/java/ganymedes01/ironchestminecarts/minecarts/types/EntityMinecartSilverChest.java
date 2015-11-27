package ganymedes01.ironchestminecarts.minecarts.types;

import cpw.mods.ironchest.IronChestType;
import ganymedes01.ironchestminecarts.minecarts.EntityMinecartIronChestAbstract;
import net.minecraft.world.World;

public class EntityMinecartSilverChest extends EntityMinecartIronChestAbstract {

	public EntityMinecartSilverChest(World world) {
		super(world);
	}

	public EntityMinecartSilverChest(World world, double x, double y, double z) {
		super(world, x, y, z);
	}

	@Override
	public IronChestType type() {
		return IronChestType.SILVER;
	}
}