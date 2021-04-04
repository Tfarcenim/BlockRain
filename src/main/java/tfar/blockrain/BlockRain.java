package tfar.blockrain;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Mod(modid = BlockRain.MODID, name = BlockRain.NAME, version = BlockRain.VERSION)
@Mod.EventBusSubscriber
public class BlockRain
{
    public static final String MODID = "blockrain";
    public static final String NAME = "Block Rain";
    public static final String VERSION = "1.0";

    @EventHandler
    public void init(FMLInitializationEvent event) {
        /*List<Block> ores = new ArrayList<>();
        for (Block block : ForgeRegistries.BLOCKS) {
            if (block.getRegistryName().getPath().contains("ore")) {
                ores.add(block);
            }
        }*/
        RainConfig.parse();
        //RainConfig.states = ores.stream().map(Block::getDefaultState).collect(Collectors.toList());
    }

    @SubscribeEvent
    public static void tickServer(TickEvent.PlayerTickEvent e) {
        if (!e.player.world.isRemote && e.phase == TickEvent.Phase.START) {
            EntityPlayer player = e.player;
            for (int i = 0; i < RainConfig.count;i++) {
                BlockPos playerPos = player.getPosition();
                int xMin = playerPos.getX() - RainConfig.radius;
                //int xMax = playerPos.getX() + RainConfig.radius;
                int zMin = playerPos.getZ() - RainConfig.radius;
                //int zMax = playerPos.getZ() + RainConfig.radius;

                int xRand = xMin + rand.nextInt(2 * RainConfig.radius);
                int zRand = zMin + rand.nextInt(2 * RainConfig.radius);

                BlockPos spawn = new BlockPos(xRand,127,zRand);

                IBlockState state = getRandomBlock();
                player.world.setBlockState(spawn,state);
                EntityFallingBlock fallingBlock = new EntityFallingBlock(player.world,spawn.getX() + 0.5D, spawn.getY(), spawn.getZ() + 0.5D,state);
                fallingBlock.shouldDropItem = false;
                player.world.spawnEntity(fallingBlock);
            }
        }
    }

    @SubscribeEvent
    public static void config(ConfigChangedEvent.OnConfigChangedEvent e) {
        if (e.getModID().equals(MODID))
        RainConfig.parse();
    }

    public static final Random rand = new Random();

    public static IBlockState getRandomBlock() {
        return RainConfig.states.get(rand.nextInt(RainConfig.states.size()));
    }
}
