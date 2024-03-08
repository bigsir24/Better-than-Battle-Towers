package jamdoggie.betterbattletowers.entity;

import com.mojang.nbt.CompoundTag;
import jamdoggie.betterbattletowers.BetterBattleTowers;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.monster.EntityMonster;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.sound.SoundType;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;

import java.util.List;

public class EntityGolem extends EntityMonster
{
	private String texture;

	public EntityGolem(World world, int i)
	{
		super(world);
		texture = "/mob/golem/golemdormant.png";
		moveSpeed = 0.35F;
		attackStrength = 8;
		health = 150 + 150 * i;
		setSize(1.6F, 3.4F);
		xRot = 0.0F;
		field_21481_field_20158_dormant = 1;
		pathToEntity = 0;
		field_21479_field_20159_growl = 0;
		fireImmune = true;
		field_21480_field_20161_drops = 2 + 2 * i;
		moveTo(x, y, z, 0.0F, 0.0F);
	}

	public EntityGolem(World world)
	{
		super(world);
		texture = "/mob/golem/golem.png";
		moveSpeed = 0.35F;
		attackStrength = 8;
		health = 300;
		setSize(1.6F, 3.4F);
		xRot = 0.0F;
		field_21481_field_20158_dormant = 0;
		pathToEntity = 0;
		field_21479_field_20159_growl = 0;
		fireImmune = true;
		field_21480_field_20161_drops = 1;
		moveTo(x, y, z, 0.0F, 0.0F);
	}

	@Override
	public String getDefaultEntityTexture()
	{
		return getEntityTexture();
	}

	@Override
	public String getEntityTexture()
	{
		return "assets/" + BetterBattleTowers.MOD_ID + "/textures" + texture;
	}

	@Override
	public void remove()
	{
		if(health <= 0 || world.difficultySetting == 0)
		{
			super.remove();
		}
	}

	@Override
	public void onDeath(Entity entity)
	{
		if(scoreValue > 0 && entity != null)
		{
			entity.awardKillScore(this, scoreValue);
		}

		if(!world.isClientSide)
		{
			int i = field_21480_field_20161_drops - random.nextInt(2);
			for(int j = 0; j < i; j++)
			{
				spawnAtLocation(Item.diamond.id, 1);
			}

			i = random.nextInt(4) + 9;
			for(int k = 0; k < i; k++)
			{
				spawnAtLocation(Block.slabStonePolished.id, 1);
			}

		}

		world.sendTrackedEntityStatusUpdatePacket(this, (byte)3);
	}

	public void knockBack(Entity entity, int i, double d, double d1)
	{
		moveSpeed = 0.35F + (float)((double)(450 - health) / 1750D);
		if(random.nextInt(5) == 0)
		{
			// ORIGINAL: motionX, motionZ, motionY
			xd *= 1.5D;
			zd *= 1.5D;
			yd += 0.60000002384185791D;
		}
		pathToEntity = 150;
	}

	protected void func_21475_func_20155_look_()
	{
		if(field_21481_field_20158_dormant == 1)
		{
			EntityPlayer entityplayer = world.getClosestPlayerToEntity(this, 6D);
			if(entityplayer != null && canEntityBeSeen(entityplayer))
			{
				field_21481_field_20158_dormant = 0;
				world.playSoundEffect(SoundType.CAVE_SOUNDS, x, y, z, "ambient.cave.cave", 0.7F, 1.0F);
				world.playSoundAtEntity(this, "golemawaken", getSoundVolume() * 2.0F, ((random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F) * 1.8F);
				texture = "/mob/golem/golem.png";
				pathToEntity = 175;
			}
		} else
		{
			List list = world.getEntitiesWithinAABBExcludingEntity(this, bb.expand(6D, 6D, 6D));
			boolean flag = false;
			int i = 0;
			do
			{
				if(i >= list.size())
				{
					break;
				}
				Entity entity = (Entity)list.get(i);
				if(entity == entityToAttack)
				{
					flag = true;
					break;
				}
				i++;
			} while(true);
			if(!flag && entityToAttack != null || field_21479_field_20159_growl == 1)
			{
				pathToEntity--;
			} else
			{
				pathToEntity = 175;
			}
		}
	}

	@Override
	public void tick()
	{
		if(field_21481_field_20158_dormant == 0)
		{
			if(pathToEntity <= 0 && field_21479_field_20159_growl == 0)
			{
				if(field_21479_field_20159_growl == 0 && (entityToAttack instanceof EntityPlayer) && world.getClosestPlayerToEntity(this, 24D) == null)
				{
					entityToAttack = null;
				} else
				if(!func_21476_func_20154_happyMan())
				{
					world.playSoundAtEntity(this, "golemspecial", getSoundVolume() * 2.0F, ((random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F) * 1.8F);
					yd += 0.90000000000000002D; // ORIGINAL: motionY
					field_21479_field_20159_growl = 1;
				} else
				{
					pathToEntity = 150;
				}
			} else
			if((pathToEntity <= -30 || onGround) && field_21479_field_20159_growl == 1)
			{
				if(health <= 425)
				{
					health += 25;
				}
				world.createExplosion(this, x, y, z, 4.5F + (float)(field_21480_field_20161_drops - 2) / 4F);
				pathToEntity = 125;
				field_21479_field_20159_growl = 0;
			}
			super.tick();
		}
		func_21475_func_20155_look_();
	}

	@Override
	public boolean save(CompoundTag nbttagcompound)
	{
		boolean canSave = super.save(nbttagcompound);

		if (canSave)
		{
			nbttagcompound.putByte("isDormant", (byte)field_21481_field_20158_dormant);
			nbttagcompound.putByte("hasGrowled", (byte)field_21479_field_20159_growl);
			nbttagcompound.putByte("rageCounter", (byte)pathToEntity);
			nbttagcompound.putByte("Drops", (byte)field_21480_field_20161_drops);
		}

		return canSave;
	}

	@Override
	public void load(CompoundTag nbttagcompound)
	{
		super.load(nbttagcompound);
		field_21481_field_20158_dormant = nbttagcompound.getByte("isDormant") & 0xff;
		field_21479_field_20159_growl = nbttagcompound.getByte("hasGrowled") & 0xff;
		pathToEntity = nbttagcompound.getByte("rageCounter") & 0xff;
		field_21480_field_20161_drops = nbttagcompound.getByte("Drops") & 0xff;
		moveSpeed = 0.35F + (float)((double)(450 - health) / 1750D);
		if(field_21481_field_20158_dormant == 1)
		{
			texture = "/mob/golem/golemdormant.png";
		} else
		{
			texture = "/mob/golem/golem.png";
		}
		attackStrength = 8;
	}

	protected boolean func_21476_func_20154_happyMan()
	{
		return false; // IDK wtf this code was for so he's just going to be ANGRY ALL THE TIME!!!!!!
					  // My assumption is this was for compatibility with some mod.

		/*int i = -1;
		int j = 0;
		try
		{
			Field field = (net.minecraft.src.EntityLiving.class).getDeclaredField("team");
			i = field.getInt(this);
			Field field1 = (net.minecraft.src.EntityLiving.class).getDeclaredField("team");
			j = field1.getInt(super.playerToAttack);
		}
		catch(Exception exception)
		{
			if(!(exception instanceof SecurityException) && !(exception instanceof NoSuchFieldException))
			{
				if(!(exception instanceof IllegalAccessException));
			}
		}
		return i == j;*/
	}

	@Override
	protected void attackEntity(Entity entity, float f)
	{
		if((double)f < 3D && entity.bb.maxY > bb.minY && entity.bb.minY < bb.maxY)
		{
			entity.hurt(this, attackStrength, DamageType.COMBAT);
		}
		if(onGround)
		{
			double d = entity.x - x;
			double d1 = entity.z - z;
			float f1 = MathHelper.sqrt_double(d * d + d1 * d1);
			xd = (d / (double)f1) * 0.5D * 0.20000000192092895D + xd * 0.20000000098023224D;
			zd = (d1 / (double)f1) * 0.5D * 0.10000000192092896D + zd * 0.20000000098023224D;
		} else
		{
			super.attackEntity(entity, f);
		}
	}

	@Override
	public String getLivingSound()
	{
		if(field_21481_field_20158_dormant == 0)
		{
			return "golem";
		} else
		{
			return null;
		}
	}

	@Override
	protected String getHurtSound()
	{
		return "golemhurt";
	}

	@Override
	protected String getDeathSound()
	{
		return "golemdeath";
	}

	@Override
	protected int getDropItemId()
	{
		return Item.brickClay.id;
	}

	private int field_21481_field_20158_dormant;
	private int pathToEntity;
	private int field_21479_field_20159_growl;
	private int field_21480_field_20161_drops;
	public Entity field_21478_field_20156_b;
	public int field_21477_field_20160_c;
}
