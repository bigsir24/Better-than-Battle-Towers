package jamdoggie.betterbattletowers.worldgen;

import jamdoggie.betterbattletowers.entity.EntityGolem;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.entity.TileEntityChest;
import net.minecraft.core.block.entity.TileEntityMobSpawner;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;
import net.minecraft.core.world.generate.feature.WorldFeature;

import java.util.Random;

public class WorldGenTower extends WorldFeature
{

	public WorldGenTower()
	{
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k)
	{
		boolean flag = false;
		int l = 0;
		int i1 = 0;
		int j1 = 0;
		boolean flag1 = false;

		for(int k1 = 0; k1 < 32; k1++)
		{
			int l1 = (i + random.nextInt(8)) - random.nextInt(8);
			int i2 = (j + random.nextInt(4)) - random.nextInt(4);
			int j2 = (k + random.nextInt(8)) - random.nextInt(8);

			l = l1;
			i1 = i2;
			j1 = j2;
			boolean flag5 = false;

			if(world.getBlockId(l1, i2, j2) != 0 && world.getBlockId(l1, i2, j2) != Block.sand.id || world.getBlockId(l1, i2 - 1, j2) == 0)
			{
				continue;
			}

			int i3 = -4;

			do
			{
				if(i3 >= 5)
				{
					break;
				}
				int i4 = -4;
				do
				{
					if(i4 >= 5)
					{
						break;
					}
					int l4 = world.getBlockId(l1 + i3, i2, j2 + i4);
					if(l4 != 0 && l4 != Block.layerSnow.id && l4 != Block.flowerYellow.id && l4 != Block.flowerRed.id) // TODO: investigate this. The original source accessed blocksList[], and I removed one that I thought was supposed to point towards logs.
					{
						flag5 = true;
					}
					int l5 = world.getBlockId(l1 + i3, i2 - 1, j2 + i4);
					if(l5 != Block.grass.id && l5 != Block.sand.id && l5 != Block.stone.id)
					{
						flag5 = true;
					}
					if(flag5)
					{
						break;
					}
					i4++;
				} while(true);
				if(flag5)
				{
					break;
				}
				i3++;
			}
			while(true);

			if(flag5)
			{
				continue;
			}
			flag1 = true;
			break;
		}

		if(!flag1)
		{
			return false;
		}

		boolean flag2 = false;
		boolean flag3 = false;
		boolean flag4 = false;
		int k2 = i1 - 6;
		int l2 = random.nextInt(3);

		currentFloor = 1;
		field_22237_field_20341_topFloor = 0;

		for(; k2 < 120; k2 += 7)
		{
			if(k2 + 7 >= 120)
			{
				field_22237_field_20341_topFloor = 1;
			}
			for(int j3 = 0; j3 < 7; j3++)
			{
				if(k2 == i1 - 6 && j3 < 4)
				{
					j3 = 4;
				}

				for(int j4 = -7; j4 < 7; j4++)
				{
					for(int i5 = -7; i5 < 7; i5++)
					{
						int i6 = j4 + l;
						int k6 = j3 + k2;
						int l6 = i5 + j1;

						if(i5 == -7)
						{
							if(j4 > -5 && j4 < 4)
							{
								world.setBlock(i6, k6, l6, getRandomCobbledBlock(l2, random));
							}
							continue;
						}

						if(i5 == -6 || i5 == -5)
						{
							if(j4 == -5 || j4 == 4)
							{
								world.setBlock(i6, k6, l6, getRandomCobbledBlock(l2, random));
								continue;
							}

							if(i5 == -6)
							{
								if(j4 == (j3 + 1) % 7 - 3)
								{
									world.setBlock(i6, k6, l6, Block.stonePolished.id);

									if(j3 == 5)
									{
										world.setBlock(i6 - 7, k6, l6, Block.stonePolished.id);
									}

									if(j3 == 6 && field_22237_field_20341_topFloor == 1)
									{
										world.setBlock(i6, k6, l6, getRandomCobbledBlock(l2, random));
									}

									continue;
								}

								if(j4 < 4 && j4 > -5)
								{
									//world.setBlock(i6, k6, l6, 0);
								}

								continue;
							}

							if(i5 != -5 || j4 <= -5 || j4 >= 5)
							{
								continue;
							}

							if(j3 != 0 && j3 != 6 || j4 != -4 && j4 != 3)
							{
								if(j3 == 5 && (j4 == 3 || j4 == -4))
								{
									world.setBlock(i6, k6, l6, Block.stonePolished.id);
								}
								else
								{
									world.setBlock(i6, k6, l6, getRandomCobbledBlock(l2, random));
								}
							}
							else
							{
								//world.setBlock(i6, k6, l6, 0);
							}

							continue;
						}
						if(i5 == -4 || i5 == -3 || i5 == 2 || i5 == 3)
						{
							if(j4 == -6 || j4 == 5)
							{
								world.setBlock(i6, k6, l6, getRandomCobbledBlock(l2, random));
								continue;
							}

							if(j4 <= -6 || j4 >= 5)
							{
								continue;
							}

							if(j3 == 5)
							{
								world.setBlock(i6, k6, l6, Block.stonePolished.id);
								continue;
							}

							if(world.getBlockId(i6, k6, l6) != 54)
							{
								//world.setBlock(i6, k6, l6, 0);
							}

							continue;
						}

						if(i5 > -3 && i5 < 2)
						{
							if(j4 == -7 || j4 == 6)
							{
								if(j3 < 0 || j3 > 3 || j4 != -7 && j4 != 6 || i5 != -1 && i5 != 0)
								{
									world.setBlock(i6, k6, l6, getRandomCobbledBlock(l2, random));
								}
								else
								{
									//world.setBlock(i6, k6, l6, 0);
								}

								continue;
							}

							if(j4 <= -7 || j4 >= 6)
							{
								continue;
							}

							if(j3 == 5)
							{
								world.setBlock(i6, k6, l6, Block.stonePolished.id);
							}
							else
							{
								//world.setBlock(i6, k6, l6, 0);
							}

							continue;
						}

						if(i5 == 4)
						{
							if(j4 == -5 || j4 == 4)
							{
								world.setBlock(i6, k6, l6, getRandomCobbledBlock(l2, random));
								continue;
							}
							if(j4 <= -5 || j4 >= 4)
							{
								continue;
							}
							if(j3 == 5)
							{
								world.setBlock(i6, k6, l6, Block.stonePolished.id);
							} else
							{
								//world.setBlock(i6, k6, l6, 0);
							}
							continue;
						}

						if(i5 == 5)
						{
							if(j4 == -4 || j4 == -3 || j4 == 2 || j4 == 3)
							{
								world.setBlock(i6, k6, l6, getRandomCobbledBlock(l2, random));
								continue;
							}
							if(j4 <= -3 || j4 >= 2)
							{
								continue;
							}
							if(j3 == 5)
							{
								world.setBlock(i6, k6, l6, Block.stonePolished.id);
							} else
							{
								world.setBlock(i6, k6, l6, getRandomCobbledBlock(l2, random));
							}
							continue;
						}

						if(i5 != 6 || j4 <= -3 || j4 >= 2)
						{
							continue;
						}

						if(j3 < 0 || j3 > 3 || j4 != -1 && j4 != 0)
						{
							world.setBlock(i6, k6, l6, getRandomCobbledBlock(l2, random));
						}
						else
						{
							world.setBlock(i6, k6, l6, getRandomCobbledBlock(l2, random));
						}
					}

				}

			}

			if(currentFloor == 2)
			{
				world.setBlock(l + 3, k2, j1 - 5, getRandomCobbledBlock(l2, random));
				world.setBlock(l + 3, k2 - 1, j1 - 5, getRandomCobbledBlock(l2, random));
			}

			if(field_22237_field_20341_topFloor == 1)
			{
				double d = l;
				double d1 = k2 + 6;
				double d2 = (double)j1 + 0.5D;
				EntityGolem entitygolem = new EntityGolem(world, l2);
				entitygolem.spawnInit();
				entitygolem.moveTo(d, d1, d2, world.rand.nextFloat() * 360F, 0.0F);
				world.entityJoinedWorld(entitygolem);
				System.out.println("Spawned golem at " + d + ", " + d1 + ", " + d2);
			}
			else
			{
				world.setBlockWithNotify(l + 2, k2 + 6, j1 + 2, Block.mobspawner.id);
				TileEntityMobSpawner tileentitymobspawner = (TileEntityMobSpawner)world.getBlockTileEntity(l + 2, k2 + 6, j1 + 2);
				tileentitymobspawner.setMobId(getRandomSpawnerMob(random));
				world.setBlockWithNotify(l - 3, k2 + 6, j1 + 2, Block.mobspawner.id);
				TileEntityMobSpawner tileentitymobspawner1 = (TileEntityMobSpawner)world.getBlockTileEntity(l - 3, k2 + 6, j1 + 2);
				tileentitymobspawner1.setMobId(getRandomSpawnerMob(random));
			}

			world.setBlock(l, k2 + 6, j1 - 3, Block.stonePolished.id);
			world.setBlock(l - 1, k2 + 6, j1 - 3, Block.stonePolished.id);

			if(k2 + 56 >= 120 && currentFloor == 1)
			{
				currentFloor = 2;
			}

			for(int k3 = 0; k3 < 2; k3++)
			{
				world.setBlockWithNotify(l - k3, k2 + 7, j1 - 3, Block.chestLegacy.id);
				TileEntityChest tileEntityChest = (TileEntityChest)world.getBlockTileEntity(l - k3, k2 + 7, j1 - 3);
				for(int j5 = 0; j5 < 1 + k3 + l2; j5++)
				{
					ItemStack itemstack = generateRandomChestLoot(currentFloor, random);
					if(itemstack != null)
					{
						tileEntityChest.setInventorySlotContents(random.nextInt(tileEntityChest.getSizeInventory()), itemstack);
					}
				}

			}

			for(int l3 = 0; l3 < (currentFloor * 4 + l2) - 8 && field_22237_field_20341_topFloor != 1; l3++)
			{
				int k4 = 5 - random.nextInt(12);
				int k5 = k2 + 5;
				int j6 = 5 - random.nextInt(10);
				if(j6 < -2 && k4 < 4 && k4 > -5 && k4 != 1 && k4 != -2)
				{
					continue;
				}
				k4 += l;
				j6 += j1;
				if(world.getBlockId(k4, k5, j6) == Block.stonePolished.id && world.getBlockId(k4, k5 + 1, j6) != Block.mobspawner.id)
				{
					//world.setBlock(k4, k5, j6, 0);
				}
			}

			currentFloor++;
		}

		return true;
	}

	private ItemStack generateRandomChestLoot(int towerLevel, Random random)
	{
		int j = random.nextInt(4);
		if(field_22237_field_20341_topFloor == 1)
		{
			if(j == 0)
			{
				return new ItemStack(Item.toolSwordIron, 1);
			}
			if(j == 1)
			{
				return new ItemStack(Item.toolPickaxeIron, 1);
			}
			if(j == 2)
			{
				return new ItemStack(Item.toolAxeIron, 1);
			}
			if(j == 3)
			{
				return new ItemStack(Item.toolShovelIron, 1);
			} else
			{
				return null;
			}
		}

		if(towerLevel == 1)
		{
			if(j == 0)
			{
				return new ItemStack(Item.stick, random.nextInt(5) + 2);
			}
			if(j == 1)
			{
				return new ItemStack(Item.seedsWheat, random.nextInt(5) + 2);
			}
			if(j == 2)
			{
				return new ItemStack(Block.cobbleStone, random.nextInt(5) + 4);
			}
			if(j == 3)
			{
				return new ItemStack(Block.sand, random.nextInt(5) + 4);
			} else
			{
				return null;
			}
		}

		if(towerLevel == 2)
		{
			if(j == 0)
			{
				return new ItemStack(Item.coal, random.nextInt(3) + 6);
			}
			if(j == 1)
			{
				return new ItemStack(Item.toolPickaxeWood, 1);
			}
			if(j == 2)
			{
				return new ItemStack(Block.planksOak, random.nextInt(3) + 4);
			}
			if(j == 3)
			{
				return new ItemStack(Block.wool, random.nextInt(3) + 6);
			} else
			{
				return null;
			}
		}

		if(towerLevel == 3)
		{
			if(j == 0)
			{
				return new ItemStack(Item.featherChicken, random.nextInt(3) + 6);
			}
			if(j == 1)
			{
				return new ItemStack(Item.foodBread, 1);
			}
			if(j == 2)
			{
				return new ItemStack(Block.glass, random.nextInt(3) + 5);
			}
			if(j == 3)
			{
				return new ItemStack(Block.mushroomBrown, random.nextInt(3) + 3);
			} else
			{
				return null;
			}
		}

		if(towerLevel == 4)
		{
			if(j == 0)
			{
				return new ItemStack(Item.string, random.nextInt(3) + 2);
			}
			if(j == 1)
			{
				return new ItemStack(Item.toolSwordStone, 1);
			}
			if(j == 2)
			{
				return new ItemStack(Block.torchCoal, random.nextInt(3) + 5);
			}
			if(j == 3)
			{
				return new ItemStack(Block.mushroomRed, random.nextInt(3) + 3);
			} else
			{
				return null;
			}
		}

		if(towerLevel == 5)
		{
			if(j == 0)
			{
				return new ItemStack(Block.stairsPlanksOak, random.nextInt(3) + 5);
			}
			if(j == 1)
			{
				return new ItemStack(Item.toolBow, 1);
			}
			if(j == 2)
			{
				return new ItemStack(Block.brickClay, random.nextInt(3) + 5);
			}
			if(j == 3)
			{
				return new ItemStack(Item.armorBootsChainmail, 1);
			} else
			{
				return null;
			}
		}

		if(towerLevel == 6)
		{
			if(j == 0)
			{
				return new ItemStack(Block.ladderOak, random.nextInt(3) + 9);
			}
			if(j == 1)
			{
				return new ItemStack(Item.toolFirestrikerSteel, 1);
			}
			if(j == 2)
			{
				return new ItemStack(Block.glowstone, random.nextInt(3) + 5);
			}
			if(j == 3)
			{
				return new ItemStack(Item.armorHelmetChainmail, 1);
			} else
			{
				return null;
			}
		}

		if(towerLevel == 7)
		{
			if(j == 0)
			{
				return new ItemStack(Block.pumpkinCarvedActive, random.nextInt(3) + 9);
			}
			if(j == 1)
			{
				return new ItemStack(Item.bucketLava, 1);
			}
			if(j == 2)
			{
				return new ItemStack(Block.rail, random.nextInt(5) + 9);
			}
			if(j == 3)
			{
				return new ItemStack(Item.armorLeggingsChainmail, 1);
			} else
			{
				return null;
			}
		}

		if(towerLevel == 8)
		{
			if(j == 0)
			{
				return new ItemStack(Block.tnt, random.nextInt(3) + 3);
			}
			if(j == 1)
			{
				return new ItemStack(Item.toolHoeDiamond, 1);
			}
			if(j == 2)
			{
				return new ItemStack(Block.obsidian, random.nextInt(3) + 6);
			}
			if(j == 3)
			{
				return new ItemStack(Item.armorChestplateChainmail, 1);
			} else
			{
				return null;
			}
		}

		if(random.nextInt(4) == 0)
		{
			return new ItemStack(Item.diamond, random.nextInt(3) + 3);
		}

		if(random.nextInt(4) == 1)
		{
			return new ItemStack(Item.ingotIron, random.nextInt(3) + 3);
		}

		if(random.nextInt(4) == 2)
		{
			return new ItemStack(Item.diamond, random.nextInt(4) + 6);
		}

		if(random.nextInt(4) == 3)
		{
			return new ItemStack(Item.ingotIron, random.nextInt(4) + 6);
		}
		else
		{
			return null;
		}
	}

	private String getRandomSpawnerMob(Random random)
	{
		int i = random.nextInt(5);

		if(i == 0)
		{
			return "Skeleton";
		}
		if(i == 1)
		{
			return "Zombie";
		}
		if(i == 2)
		{
			return "Zombie";
		}
		if(i == 3)
		{
			return "Spider";
		}
		if(i == 4)
		{
			return "Zombie";
		} else
		{
			return "Spider";
		}
	}

	private int getRandomCobbledBlock(int i, Random random)
	{
		if(i == 0)
		{
			return Block.cobbleStone.id;
		}
		if(i == 1)
		{
			if(random.nextInt(3) == 0)
			{
				return Block.cobbleStone.id;
			} else
			{
				return Block.cobbleStoneMossy.id;
			}
		} else
		{
			return Block.cobbleStoneMossy.id;
		}
	}
	private int currentFloor;
	private int field_22237_field_20341_topFloor;

}
