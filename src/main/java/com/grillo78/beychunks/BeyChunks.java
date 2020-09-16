package com.grillo78.beychunks;

import com.grillo78.beychunks.events.CommonEvents;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Reference.MOD_ID)
public class BeyChunks
{
    // Directly reference a log4j logger.
    public static final Logger LOGGER = LogManager.getLogger();

    public BeyChunks() {
    }
}
