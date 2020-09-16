package com.grillo78.beychunks.events;

import com.grillo78.beychunks.Reference;
import com.grillo78.beychunks.capabilities.*;
import com.grillo78.beychunks.commands.*;
import com.grillo78.beycraft.capabilities.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BucketItem;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunk;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonEvents {

    @SubscribeEvent
    public static void setup(final FMLCommonSetupEvent event) {
        CapabilityManager.INSTANCE.register(ITeams.class, new TeamsStorage(), new TeamsFactory());
        CapabilityManager.INSTANCE.register(IClaimed.class, new ClaimedStorage(), new ClaimedFactory());
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class SpecialEvents {

        @SubscribeEvent
        public static void onWorldAttachCapabilities(AttachCapabilitiesEvent<World> event){
            event.addCapability(new ResourceLocation(Reference.MOD_ID, "teams"), new TeamsProvider());
        }

        @SubscribeEvent
        public static void onChunkAttachCapabilities(AttachCapabilitiesEvent<Chunk> event){
            event.addCapability(new ResourceLocation(Reference.MOD_ID, "claimed"), new ClaimedProvider());
        }

        @SubscribeEvent
        public static void onBlockClicked(PlayerInteractEvent.RightClickBlock event){
            IWorld world = event.getWorld();
            if (!world.isRemote()) {
                PlayerEntity player = event.getPlayer();
                IChunk iChunk = world.getChunk(event.getPos());
                if(iChunk instanceof Chunk){
                    ((Chunk)iChunk).getCapability(ClaimedProvider.CLAIMED_CAPABILITY).ifPresent(h->{
                        if(h.isClaimed() && world instanceof World){
                            ((World)world).getCapability(TeamsProvider.TEAMS_CAPABILITY).ifPresent(i->{
                                for (Team team : i.getTeams()){
                                    if(team.getUuid().equals(h.getUUID()) && !team.getUuidMembers().contains(player.getUniqueID())){
                                        event.setCanceled(true);
                                    }
                                }
                            });
                        }
                    });
                }
            }
        }

        @SubscribeEvent
        public static void onItemRightClick(PlayerInteractEvent.RightClickItem event){
            if(event.getItemStack().getItem() instanceof BucketItem){
                IWorld world = event.getWorld();
                if (!world.isRemote()) {
                    PlayerEntity player = event.getPlayer();
                    IChunk iChunk = world.getChunk(event.getPos());
                    if(iChunk instanceof Chunk){
                        ((Chunk)iChunk).getCapability(ClaimedProvider.CLAIMED_CAPABILITY).ifPresent(h->{
                            if(h.isClaimed() && world instanceof World){
                                ((World)world).getCapability(TeamsProvider.TEAMS_CAPABILITY).ifPresent(i->{
                                    for (Team team : i.getTeams()){
                                        if(team.getUuid().equals(h.getUUID()) && !team.getUuidMembers().contains(player.getUniqueID())){
                                            event.setCanceled(true);
                                        }
                                    }
                                });
                            }
                        });
                    }
                }
            }
        }

        @SubscribeEvent
        public static void onBlockBreak(BlockEvent.BreakEvent event) {
            IWorld world = event.getWorld();
            if (!world.isRemote()) {
                PlayerEntity player = event.getPlayer();
                IChunk iChunk = world.getChunk(event.getPos());
                if(iChunk instanceof Chunk){
                    ((Chunk)iChunk).getCapability(ClaimedProvider.CLAIMED_CAPABILITY).ifPresent(h->{
                        if(h.isClaimed() && world instanceof World){
                            ((World)world).getCapability(TeamsProvider.TEAMS_CAPABILITY).ifPresent(i->{
                                for (Team team : i.getTeams()){
                                    if(team.getUuid().equals(h.getUUID()) && !team.getUuidMembers().contains(player.getUniqueID())){
                                        event.setCanceled(true);
                                    }
                                }
                            });
                        }
                    });
                }
            }
        }

        @SubscribeEvent
        public static void onCommandRegistry(RegisterCommandsEvent event) {
            ClaimChunkCommand.register(event.getDispatcher());
            CreateTeamCommand.register(event.getDispatcher());
            AcceptTeamCommand.register(event.getDispatcher());
            InviteTeamCommand.register(event.getDispatcher());
            LeaveTeamCommand.register(event.getDispatcher());
        }
    }
}
