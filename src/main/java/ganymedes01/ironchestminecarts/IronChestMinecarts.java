package ganymedes01.ironchestminecarts;

import ganymedes01.ironchestminecarts.lib.Reference;
import ganymedes01.ironchestminecarts.minecarts.EntityMinecartIronChestAbstract;
import ganymedes01.ironchestminecarts.minecarts.ItemMinecartChestRenderer;
import ganymedes01.ironchestminecarts.minecarts.ItemMinecartIronChest;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.ironchest.IronChest;
import cpw.mods.ironchest.IronChestType;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, dependencies = Reference.DEPENDENCIES)
public class IronChestMinecarts {

    @Instance(Reference.MOD_ID)
    public static IronChestMinecarts instance;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        OreDictionary.registerOre("chestEnder", Blocks.ender_chest);
        OreDictionary.registerOre("chestTrapped", Blocks.trapped_chest);
        OreDictionary.registerOre("chestWood", Blocks.chest);

        for (IronChestType type : EntityMinecartIronChestAbstract.registeredTypes())
        {
            String name = type.name().toLowerCase();

            // Item registering
            Item minecart = new ItemMinecartIronChest(type).setUnlocalizedName(Reference.MOD_ID + ".minecart_chest_" + name).setTextureName(Reference.MOD_ID + ":minecart_chest_" + name);
            GameRegistry.registerItem(minecart, "minecart_chest_" + name);

            // Recipe
            ItemStack chest = new ItemStack(IronChest.ironChestBlock, 1, type.ordinal());
            if (type == IronChestType.DIRTCHEST9000)
                name = "dirt";
            String ore = "chest" + name.substring(0, 1).toUpperCase() + name.substring(1);
            OreDictionary.registerOre(ore, chest);
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(minecart), "x", "y", 'x', ore, 'y', Items.minecart));

            // Rendering
            if (event.getSide() == Side.CLIENT)
                MinecraftForgeClient.registerItemRenderer(minecart, new ItemMinecartChestRenderer());
        }
    }
}