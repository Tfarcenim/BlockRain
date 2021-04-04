package tfar.blockrain;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

@Config(modid = BlockRain.MODID)
public class RainConfig {

    @Config.Name("block_list")
    public static String[] blocks = new String[]{"minecraft:dirt=0"};

    @Config.Name("count")
    public static int count = 5;

    @Config.Name("radius")
    public static int radius = 128;

    @Config.Ignore
    public static List<IBlockState> states = new ArrayList<>();

    public static void parse() {
        states.clear();
        for (String s : blocks) {
            String[] parts = s.split("=");
            Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(parts[0]));
            int meta = Integer.parseInt(parts[1]);
            IBlockState state = block.getStateFromMeta(meta);
            states.add(state);
        }
    }
}
