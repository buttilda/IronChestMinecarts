package ganymedes01.ironchestminecarts.minecarts;

import ganymedes01.ironchestminecarts.IronChestMinecarts;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.minecart.MinecartInteractEvent;

import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;

import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.ironchest.IronChest;
import cpw.mods.ironchest.IronChestType;

@SuppressWarnings("all")
public abstract class EntityMinecartIronChestAbstract extends EntityMinecartChest {

    private ItemStack[] inventory = new ItemStack[getSizeInventory()];
    private static final Map<IronChestType, Class<? extends EntityMinecartIronChestAbstract>> map = new HashMap<IronChestType, Class<? extends EntityMinecartIronChestAbstract>>();

    static
    {
        try
        {
            Class<?> clss = EntityMinecartIronChestAbstract.class;
            String name = clss.getCanonicalName();
            name = name.substring(0, name.lastIndexOf('.'));
            for (ClassInfo clazzInfo : ClassPath.from(clss.getClassLoader()).getTopLevelClasses(name + "." + "types"))
            {
                Class<? extends Entity> clazz = (Class<? extends Entity>) clazzInfo.load();
                if (clss.isAssignableFrom(clazz))
                {
                    IronChestType type = ((EntityMinecartIronChestAbstract) clazz.getConstructor(World.class).newInstance((World) null)).type();
                    EntityRegistry.registerModEntity(clazz, "minecart_" + type.name().toLowerCase(), type.ordinal(), IronChestMinecarts.instance, 64, 1, true);
                    map.put(type, (Class<? extends EntityMinecartIronChestAbstract>) clazz);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static Set<IronChestType> registeredTypes()
    {
        return map.keySet();
    }

    public static EntityMinecartIronChestAbstract makeMinecart(World world, double x, double y, double z, IronChestType type)
    {
        try
        {
            Class<? extends EntityMinecartIronChestAbstract> cls = map.get(type);
            return cls.getConstructor(World.class, double.class, double.class, double.class).newInstance(world, x, y, z);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public EntityMinecartIronChestAbstract(World world)
    {
        super(world);
    }

    public EntityMinecartIronChestAbstract(World world, double x, double y, double z)
    {
        super(world, x, y, z);
    }

    public abstract IronChestType type();

    @Override
    public void killMinecart(DamageSource p_94095_1_)
    {
        this.setDead();
        ItemStack minecartt = new ItemStack(Items.minecart, 1);

        if (this.func_95999_t() != null)
        {
            minecartt.setStackDisplayName(this.func_95999_t());
        }

        this.entityDropItem(minecartt, 0.0F);

        for (int i = 0; i < this.getSizeInventory(); ++i)
        {
            ItemStack stack = this.getStackInSlot(i);

            if (stack != null)
            {
                float f = this.rand.nextFloat() * 0.8F + 0.1F;
                float f1 = this.rand.nextFloat() * 0.8F + 0.1F;
                float f2 = this.rand.nextFloat() * 0.8F + 0.1F;

                while (stack.stackSize > 0)
                {
                    int j = this.rand.nextInt(21) + 10;

                    if (j > stack.stackSize)
                    {
                        j = stack.stackSize;
                    }

                    stack.stackSize -= j;
                    EntityItem entityitem = new EntityItem(this.worldObj, this.posX + (double) f, this.posY + (double) f1, this.posZ + (double) f2, new ItemStack(stack.getItem(), j,
                            stack.getItemDamage()));
                    float f3 = 0.05F;
                    entityitem.motionX = (double) ((float) this.rand.nextGaussian() * f3);
                    entityitem.motionY = (double) ((float) this.rand.nextGaussian() * f3 + 0.2F);
                    entityitem.motionZ = (double) ((float) this.rand.nextGaussian() * f3);
                    this.worldObj.spawnEntityInWorld(entityitem);
                }
            }
        }

        entityDropItem(new ItemStack(func_145817_o(), 1, type().ordinal()), 0.0F);
    }

    @Override
    public int getSizeInventory()
    {
        IronChestType type = type();
        return type.getRowCount() * type.getRowLength();
    }

    @Override
    public Block func_145817_o()
    {
        return IronChest.ironChestBlock;
    }

    @Override
    public int getDefaultDisplayTileData()
    {
        return type().ordinal();
    }

    public boolean interactFirst(EntityPlayer player)
    {
        if (MinecraftForge.EVENT_BUS.post(new MinecartInteractEvent(this, player)))
        {
            return true;
        }

        if (!worldObj.isRemote)
        {
            player.openGui(IronChestMinecarts.instance, 99, worldObj, hashCode(), 0, 0);
        }

        return true;
    }

    public ItemStack getStackInSlot(int slot)
    {
        return this.inventory[slot];
    }

    public ItemStack decrStackSize(int slot, int size)
    {
        if (this.inventory[slot] != null)
        {
            ItemStack itemstack;

            if (this.inventory[slot].stackSize <= size)
            {
                itemstack = this.inventory[slot];
                this.inventory[slot] = null;
                return itemstack;
            }
            else
            {
                itemstack = this.inventory[slot].splitStack(size);

                if (this.inventory[slot].stackSize == 0)
                {
                    this.inventory[slot] = null;
                }

                return itemstack;
            }
        }
        else
        {
            return null;
        }
    }

    public ItemStack getStackInSlotOnClosing(int slot)
    {
        if (this.inventory[slot] != null)
        {
            ItemStack itemstack = this.inventory[slot];
            this.inventory[slot] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
    }

    public void setInventorySlotContents(int slot, ItemStack stack)
    {
        this.inventory[slot] = stack;

        if (stack != null && stack.stackSize > this.getInventoryStackLimit())
        {
            stack.stackSize = this.getInventoryStackLimit();
        }
    }

    protected void writeEntityToNBT(NBTTagCompound nbt)
    {
        if (this.hasDisplayTile())
        {
            nbt.setBoolean("CustomDisplayTile", true);
            nbt.setInteger("DisplayTile", this.func_145820_n().getMaterial() == Material.air ? 0 : Block.getIdFromBlock(this.func_145820_n()));
            nbt.setInteger("DisplayData", this.getDisplayTileData());
            nbt.setInteger("DisplayOffset", this.getDisplayTileOffset());
        }

        String entityName = func_95999_t();
        if (entityName != null && entityName.length() > 0)
        {
            nbt.setString("CustomName", entityName);
        }

        NBTTagList nbttaglist = new NBTTagList();

        for (int i = 0; i < this.inventory.length; ++i)
        {
            if (this.inventory[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte) i);
                this.inventory[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        nbt.setTag("Items", nbttaglist);
    }

    protected void readEntityFromNBT(NBTTagCompound nbt)
    {
        if (nbt.getBoolean("CustomDisplayTile"))
        {
            this.func_145819_k(nbt.getInteger("DisplayTile"));
            this.setDisplayTileData(nbt.getInteger("DisplayData"));
            this.setDisplayTileOffset(nbt.getInteger("DisplayOffset"));
        }

        if (nbt.hasKey("CustomName", 8) && nbt.getString("CustomName").length() > 0)
        {
            this.setMinecartName(nbt.getString("CustomName"));
        }

        NBTTagList nbttaglist = nbt.getTagList("Items", 10);
        this.inventory = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i)
        {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound1.getByte("Slot") & 255;

            if (j >= 0 && j < this.inventory.length)
            {
                this.inventory[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }
    }
}